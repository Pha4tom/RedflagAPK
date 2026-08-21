let pollTimer = null;
let pollInFlight = false;
let currentJobId = null;
let handledJobId = null;
let currentBrowsePath = '/sdcard';
let scanHistory = loadHistory();

const MAX_HISTORY = 20;

function loadHistory() {
    try {
        const parsed = JSON.parse(localStorage.getItem('redflagHistory') || '[]');
        return Array.isArray(parsed) ? parsed : [];
    } catch (_) {
        localStorage.removeItem('redflagHistory');
        return [];
    }
}

function persistHistory() {
    try {
        localStorage.setItem('redflagHistory', JSON.stringify(scanHistory.slice(0, MAX_HISTORY)));
    } catch (err) {
        console.warn('Could not persist scan history:', err);
    }
}

document.addEventListener('DOMContentLoaded', async () => {
    renderHistory();
    await recoverScanState();
});

/* -------------------------------------------------------------------------
 * Scan
 * ---------------------------------------------------------------------- */
async function triggerScan() {
    const pathInput = document.getElementById('apkPath').value.trim();
    const threadsInput = Number.parseInt(document.getElementById('threads').value, 10);

    if (!pathInput) {
        alert('Enter target APK path');
        return;
    }
    if (!Number.isInteger(threadsInput) || threadsInput < 1 || threadsInput > 16) {
        alert('Threads must be a whole number between 1 and 16.');
        return;
    }

    stopPolling();
    setScanUi(true);
    document.getElementById('executionView').classList.remove('hidden');
    document.getElementById('terminalLogs').innerText = '[*] Initializing pipeline...';
    updateStatus('ANALYZING', 'scanning');
    resetStages();

    try {
        const res = await fetch('/api/scan', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ apk_path: pathInput, threads: threadsInput })
        });

        const data = await readJson(res);
        if (!res.ok || data.status !== 'started') {
            throw new Error(data.message || `Scan request failed (${res.status})`);
        }

        currentJobId = data.job_id;
        handledJobId = null;
        createPendingHistory(data.job_id, pathInput);
        startPolling();
        await checkStatus();
    } catch (err) {
        currentJobId = null;
        setScanUi(false);
        updateStatus('SYSTEM READY', 'idle');
        alert('Could not start scan: ' + err.message);
    }
}

async function recoverScanState() {
    try {
        const state = await fetchStatus();
        if (state.running && state.job_id) {
            currentJobId = state.job_id;
            setScanUi(true);
            document.getElementById('executionView').classList.remove('hidden');
            updateStatus('ANALYZING', 'scanning');
            updateLogsAndStages(state);
            startPolling();
        } else if (state.report && state.job_id) {
            // A completed server-side scan may have finished while the browser
            // was closed. Show it, but do not repeatedly save it to history.
            currentJobId = state.job_id;
            finishJob(state);
        }
    } catch (err) {
        console.warn('Could not recover server state:', err);
    }
}

function startPolling() {
    stopPolling();
    pollTimer = setInterval(checkStatus, 750);
}

function stopPolling() {
    if (pollTimer !== null) {
        clearInterval(pollTimer);
        pollTimer = null;
    }
}

async function checkStatus() {
    if (pollInFlight) return;
    pollInFlight = true;

    try {
        const state = await fetchStatus();
        if (currentJobId && state.job_id && state.job_id !== currentJobId) {
            return;
        }

        updateLogsAndStages(state);

        if (!state.running) {
            stopPolling();
            finishJob(state);
        }
    } catch (err) {
        // A temporary network error should not delete history or reset the UI.
        console.warn('Status poll failed:', err);
    } finally {
        pollInFlight = false;
    }
}

async function fetchStatus() {
    const url = currentJobId
        ? `/api/status?job_id=${encodeURIComponent(currentJobId)}`
        : '/api/status';
    const res = await fetch(url, { cache: 'no-store' });
    const data = await readJson(res);
    if (!res.ok) throw new Error(data.message || `Status request failed (${res.status})`);
    return data;
}

async function readJson(response) {
    const text = await response.text();
    try {
        return text ? JSON.parse(text) : {};
    } catch (_) {
        throw new Error(`Server returned invalid JSON (${response.status})`);
    }
}

function updateLogsAndStages(state) {
    const logs = Array.isArray(state.logs) ? state.logs : [];
    const logText = logs.join('\n');
    const logsElem = document.getElementById('terminalLogs');
    logsElem.innerText = logText || '[*] Waiting for pipeline output...';
    logsElem.scrollTop = logsElem.scrollHeight;
    updateStageTracker(logText);
}

function finishJob(state) {
    setScanUi(false);
    updateStatus('SYSTEM READY', 'idle');

    if (state.report) {
        setStageDone('report');
        saveToHistory(state.report, state.job_id);
        renderDashboard(state.report);
    } else if (state.error) {
        setStageError('report');
        document.getElementById('executionView').classList.remove('hidden');
        document.getElementById('resultsDashboard').classList.add('hidden');
        const logs = document.getElementById('terminalLogs');
        if (!logs.innerText.includes(state.error)) {
            logs.innerText += `\n[!] ${state.error}`;
        }
        alert(state.error);
    }

    if (state.job_id) {
        handledJobId = state.job_id;
        currentJobId = state.job_id;
    }
}

function setScanUi(running) {
    document.getElementById('scanBtn').disabled = running;
    document.getElementById('threads').disabled = running;
    document.getElementById('apkPath').disabled = running;
}

/* -------------------------------------------------------------------------
 * File browser
 * ---------------------------------------------------------------------- */
function openBrowser() {
    if (document.getElementById('scanBtn').disabled) return;
    document.getElementById('browserPanel').classList.remove('hidden');
    browseTo(currentBrowsePath);
}

async function browseTo(path) {
    try {
        const res = await fetch(`/api/browse?path=${encodeURIComponent(path)}`, { cache: 'no-store' });
        const data = await readJson(res);
        if (!res.ok || data.error) throw new Error(data.error || `Browse failed (${res.status})`);

        currentBrowsePath = data.current_path;
        document.getElementById('browserPath').innerText = data.current_path;

        const list = document.getElementById('browserList');
        list.innerHTML = '';

        if (data.parent) {
            const upRow = document.createElement('div');
            upRow.className = 'm3-browser-row m3-browser-dir';
            upRow.innerText = '.. (up)';
            upRow.onclick = () => browseTo(data.parent);
            list.appendChild(upRow);
        }

        (data.dirs || []).forEach(dir => {
            const row = document.createElement('div');
            row.className = 'm3-browser-row m3-browser-dir';
            row.textContent = '📁 ' + dir.name;
            row.onclick = () => browseTo(dir.path);
            list.appendChild(row);
        });

        (data.apks || []).forEach(apk => {
            const row = document.createElement('div');
            row.className = 'm3-browser-row m3-browser-apk';
            const sizeMb = (apk.size / 1024 / 1024).toFixed(2);

            const name = document.createElement('span');
            name.textContent = '📦 ' + apk.name;
            const size = document.createElement('span');
            size.className = 'm3-browser-size';
            size.textContent = `${sizeMb} MB`;
            row.append(name, size);

            row.onclick = () => selectApk(apk.path);
            list.appendChild(row);
        });

        if ((!data.dirs || data.dirs.length === 0) && (!data.apks || data.apks.length === 0)) {
            const empty = document.createElement('div');
            empty.className = 'm3-browser-empty';
            empty.textContent = 'Empty folder';
            list.appendChild(empty);
        }
    } catch (err) {
        alert('Browse failed: ' + err.message);
    }
}

function selectApk(path) {
    document.getElementById('apkPath').value = path;
    document.getElementById('browserPanel').classList.add('hidden');
}

/* -------------------------------------------------------------------------
 * Pipeline tracker
 * ---------------------------------------------------------------------- */
function resetStages() {
    document.querySelectorAll('.m3-stage').forEach(el => {
        const label = el.dataset.label || el.innerText.replace(/^\[[^\]]*\]\s*/, '');
        el.dataset.label = label;
        el.className = 'm3-stage pending';
        el.innerText = `[ ] ${label}`;
    });
    setStageActive('prep');
}

function updateStageTracker(logs) {
    const lowerLogs = logs.toLowerCase();

    if (lowerLogs.includes('starting triage')) {
        setStageDone('prep');
        setStageActive('apktool');
    }
    if (lowerLogs.includes('apktool') || lowerLogs.includes('unpacking')) {
        setStageDone('prep');
        setStageActive('apktool');
    }
    if (lowerLogs.includes('jadx') || lowerLogs.includes('decompiling')) {
        setStageDone('apktool');
        setStageActive('jadx');
    }
    if (lowerLogs.includes('static analysis') || lowerLogs.includes('scanning') || lowerLogs.includes('collecting')) {
        setStageDone('jadx');
        setStageActive('static');
    }
    if (lowerLogs.includes('analysis complete') || lowerLogs.includes('report generated')) {
        setStageDone('static');
        setStageActive('report');
    }
}

function setStageActive(id) {
    const el = document.getElementById(`stage-${id}`);
    if (!el || el.classList.contains('done') || el.classList.contains('error')) return;
    const label = el.dataset.label || el.innerText.replace(/^\[[^\]]*\]\s*/, '');
    el.dataset.label = label;
    el.className = 'm3-stage active';
    el.innerText = `[●] ${label}`;
}

function setStageDone(id) {
    const el = document.getElementById(`stage-${id}`);
    if (!el) return;
    const label = el.dataset.label || el.innerText.replace(/^\[[^\]]*\]\s*/, '');
    el.dataset.label = label;
    el.className = 'm3-stage done';
    el.innerText = `[✓] ${label}`;
}

function setStageError(id) {
    const el = document.getElementById(`stage-${id}`);
    if (!el) return;
    const label = el.dataset.label || el.innerText.replace(/^\[[^\]]*\]\s*/, '');
    el.dataset.label = label;
    el.className = 'm3-stage error';
    el.innerText = `[!] ${label}`;
}

/* -------------------------------------------------------------------------
 * Results
 * ---------------------------------------------------------------------- */
function renderDashboard(report) {
    document.getElementById('executionView').classList.add('hidden');
    document.getElementById('resultsDashboard').classList.remove('hidden');

    const meta = report.metadata || {};
    document.getElementById('metaFile').innerText = report.target || 'unknown.apk';
    document.getElementById('metaPackage').innerText = meta.package || 'N/A';
    document.getElementById('metaVersion').innerText = meta.version || 'N/A';
    document.getElementById('metaHash').innerText = meta.sha256 || 'N/A';
    document.getElementById('metaSize').innerText = meta.size ? `${(meta.size / 1024 / 1024).toFixed(2)} MB` : 'N/A';
    document.getElementById('metaTime').innerText = Number.isFinite(Number(report.execution_time_seconds))
        ? `${report.execution_time_seconds}s`
        : 'N/A';

    const flags = Array.isArray(report.flags) ? report.flags : [];
    const counts = { high: 0, medium: 0, low: 0, info: 0 };
    flags.forEach(flag => {
        const severity = normalizeSeverity(flag.severity);
        if (counts[severity] !== undefined) counts[severity]++;
    });

    const statsRow = document.getElementById('statsRow');
    statsRow.innerHTML = `
        <div class="m3-stat-card high"><span class="val">${counts.high}</span><span class="lbl">HIGH</span></div>
        <div class="m3-stat-card med"><span class="val">${counts.medium}</span><span class="lbl">MEDIUM</span></div>
        <div class="m3-stat-card low"><span class="val">${counts.low}</span><span class="lbl">LOW</span></div>
        <div class="m3-stat-card info"><span class="val">${counts.info}</span><span class="lbl">INFO</span></div>
    `;

    const contextCard = document.querySelector('#resultsDashboard .m3-card-elevated');
    if (contextCard) {
        contextCard.classList.remove('severity-high', 'severity-medium');
        const overall = normalizeSeverity(report.overall_severity);
        if (overall === 'high') contextCard.classList.add('severity-high');
        if (overall === 'medium') contextCard.classList.add('severity-medium');
    }

    const container = document.getElementById('findingsContainer');
    container.innerHTML = '';

    if (flags.length === 0) {
        const empty = document.createElement('div');
        empty.style.padding = '15px';
        empty.style.color = 'var(--md-sys-color-on-surface-variant)';
        empty.style.fontFamily = 'var(--font-mono)';
        empty.textContent = 'No suspicious indicators found.';
        container.appendChild(empty);
        return;
    }

    // Never sort report.flags in place. History stores the same report object,
    // so mutating it here can unexpectedly change another view.
    const sortedFlags = [...flags].sort((a, b) => {
        return severityWeight(normalizeSeverity(b.severity)) - severityWeight(normalizeSeverity(a.severity));
    });

    sortedFlags.forEach(flag => {
        const sev = normalizeSeverity(flag.severity);
        const rule = String(flag.rule_id || flag.type || 'Anomaly');
        const file = String(flag.file || 'Unknown Location');
        const detail = String(flag.match || flag.description || flag.permission || 'N/A');

        const item = document.createElement('div');
        item.className = 'finding-item';

        const header = document.createElement('div');
        header.className = 'finding-header';
        header.onclick = () => item.classList.toggle('open');

        const indicator = document.createElement('div');
        indicator.className = `finding-severity sev-${sev}`;
        const title = document.createElement('div');
        title.className = 'finding-title';
        title.textContent = rule;
        const fileEl = document.createElement('div');
        fileEl.className = 'finding-file';
        fileEl.textContent = file.length > 50 ? '...' + file.slice(-47) : file;
        header.append(indicator, title, fileEl);

        const details = document.createElement('div');
        details.className = 'finding-details';
        details.innerHTML = `
            <div class="detail-row">
                <span class="detail-label">Detection Detail</span>
                <span class="detail-value"></span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Extracted Evidence</span>
                <code class="detail-code"></code>
            </div>
            <div class="detail-row">
                <span class="detail-label">Severity Context</span>
                <span class="detail-value severity-context"></span>
            </div>
        `;
        details.querySelector('.detail-value').textContent = `${rule} triggered.`;
        details.querySelector('.detail-code').textContent = detail;
        const context = details.querySelector('.severity-context');
        context.textContent = `${sev.toUpperCase()} RISK LEVEL`;
        context.classList.add(`color-${sev}`);

        item.append(header, details);
        container.appendChild(item);
    });
}

function normalizeSeverity(value) {
    const severity = String(value || 'info').toLowerCase();
    return ['high', 'medium', 'low', 'info'].includes(severity) ? severity : 'info';
}

function severityWeight(severity) {
    return { high: 3, medium: 2, low: 1, info: 0 }[severity] ?? 0;
}

/* -------------------------------------------------------------------------
 * History
 * ---------------------------------------------------------------------- */
function createPendingHistory(jobId, path) {
    const id = String(jobId);
    const filename = String(path).split(/[\\/]/).pop() || 'scanning.apk';
    const entry = {
        id,
        filename,
        severity: 'scanning',
        time: 0,
        data: null,
        scanning: true
    };

    const existingIndex = scanHistory.findIndex(item => item.id === id);
    if (existingIndex >= 0) {
        scanHistory[existingIndex] = { ...scanHistory[existingIndex], ...entry };
    } else {
        scanHistory.unshift(entry);
    }

    scanHistory = scanHistory.slice(0, MAX_HISTORY);
    persistHistory();
    renderHistory();
}

function saveToHistory(report, jobId = null) {
    const id = String(report.scan_id || jobId || `scan_${Date.now()}_${Math.random().toString(16).slice(2)}`);
    const filename = report.target || 'unknown.apk';
    const severity = normalizeSeverity(report.overall_severity);
    const time = Number(report.execution_time_seconds) || 0;

    const entry = {
        id,
        filename,
        severity,
        time,
        data: report,
        scanning: false
    };

    // Update the exact scan instead of treating filename as a primary key.
    // This means scanning the same APK twice creates two legitimate history
    // entries and clicking an old entry never removes the active one.
    const existingIndex = scanHistory.findIndex(h => h.id === id);
    if (existingIndex >= 0) {
        scanHistory[existingIndex] = entry;
    } else {
        scanHistory.unshift(entry);
    }

    scanHistory = scanHistory.slice(0, MAX_HISTORY);
    persistHistory();
    renderHistory();
}

function renderHistory() {
    const list = document.getElementById('historyList');
    if (!list) return;
    list.innerHTML = '';

    if (scanHistory.length === 0) {
        const empty = document.createElement('div');
        empty.className = 'history-empty';
        empty.textContent = 'No scans yet';
        list.appendChild(empty);
        return;
    }

    scanHistory.forEach(entry => {
        const div = document.createElement('div');
        div.className = 'history-item';
        if (entry.id === currentJobId) div.classList.add('active-history');

        const filename = document.createElement('div');
        filename.className = 'history-filename';
        filename.textContent = entry.filename || 'unknown.apk';

        const meta = document.createElement('div');
        meta.className = 'history-meta';

        const severity = document.createElement('span');
        severity.className = entry.scanning ? 'sev-info' : 'sev-' + normalizeSeverity(entry.severity);
        severity.textContent = entry.scanning ? 'SCANNING' : normalizeSeverity(entry.severity).toUpperCase();

        const time = document.createElement('span');
        time.textContent = entry.scanning ? 'in progress' : `${Number(entry.time) || 0}s`;

        meta.append(severity, time);
        div.append(filename, meta);
        div.onclick = () => selectHistoryEntry(entry.id);
        list.appendChild(div);
    });
}

function selectHistoryEntry(id) {
    const entry = scanHistory.find(item => item.id === id);
    if (!entry) return;
    if (entry.scanning) {
        document.getElementById('resultsDashboard').classList.add('hidden');
        document.getElementById('executionView').classList.remove('hidden');
        updateStatus('ANALYZING', 'scanning');
        return;
    }
    if (!entry.data) return;

    // This only changes the displayed report. It does NOT touch scanHistory,
    // the active job, or the server-side scan.
    renderDashboard(entry.data);
    renderHistory();
}

function updateStatus(text, stateClass) {
    const badge = document.getElementById('statusBadge');
    const statusText = document.getElementById('statusText');
    badge.className = `m3-chip m3-chip-${stateClass}`;
    statusText.innerText = text;
}
