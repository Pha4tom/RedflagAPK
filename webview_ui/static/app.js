let pollTimer = null;
let scanHistory = JSON.parse(localStorage.getItem('redflagHistory') || '[]');

document.addEventListener('DOMContentLoaded', renderHistory);

async function triggerScan() {
    const pathInput = document.getElementById('apkPath').value.trim();
    const threadsInput = document.getElementById('threads').value;
    
    if (!pathInput) return alert('Enter target APK path');

    // UI Reset
    document.getElementById('scanBtn').disabled = true;
    document.getElementById('resultsDashboard').classList.add('hidden');
    document.getElementById('executionView').classList.remove('hidden');
    document.getElementById('terminalLogs').innerText = '[*] Initializing pipeline...';
    
    updateStatus('ANALYZING', 'scanning');
    resetStages();

    try {
        const res = await fetch('/api/scan', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ apk_path: pathInput, threads: parseInt(threadsInput) })
        });
        const data = await res.json();
        if (data.status === 'started') {
            pollTimer = setInterval(checkStatus, 1000);
        } else {
            alert(data.message);
            updateStatus('SYSTEM READY', 'idle');
            document.getElementById('scanBtn').disabled = false;
        }
    } catch (err) {
        alert('Backend connection failed: ' + err.message);
        document.getElementById('scanBtn').disabled = false;
    }
}

async function checkStatus() {
    const res = await fetch('/api/status');
    const state = await res.json();
    
    // 1. Update Terminal
    const logsElem = document.getElementById('terminalLogs');
    const logText = state.logs.join('\n');
    logsElem.innerText = logText;
    logsElem.scrollTop = logsElem.scrollHeight;

    // 2. Parse logs to update Stage Tracker
    updateStageTracker(logText);

    // 3. Handle Completion
    if (!state.running) {
        clearInterval(pollTimer);
        document.getElementById('scanBtn').disabled = false;
        updateStatus('SYSTEM READY', 'idle');
        
        if (state.report) {
            setStageDone('report');
            saveToHistory(state.report);
            renderDashboard(state.report);
        }
    }
}

/* --- Stage Tracker Logic --- */
function resetStages() {
    document.querySelectorAll('.stage').forEach(el => {
        el.className = 'stage pending';
        el.innerText = el.innerText.replace(/\[.*\]/, '[ ]');
    });
    setStageActive('prep');
}

function updateStageTracker(logs) {
    const lowerLogs = logs.toLowerCase();
    if (lowerLogs.includes('apktool') || lowerLogs.includes('unpacking')) {
        setStageDone('prep');
        setStageActive('apktool');
    }
    if (lowerLogs.includes('jadx') || lowerLogs.includes('decompiling')) {
        setStageDone('apktool');
        setStageActive('jadx');
    }
    if (lowerLogs.includes('static analysis') || lowerLogs.includes('yara') || lowerLogs.includes('regex')) {
        setStageDone('jadx');
        setStageActive('static');
    }
    if (lowerLogs.includes('report generated')) {
        setStageDone('static');
        setStageActive('report');
    }
}

function setStageActive(id) {
    const el = document.getElementById(`stage-${id}`);
    if (el && !el.classList.contains('done')) {
        el.className = 'stage active';
        el.innerText = el.innerText.replace(/\[.*\]/, '[●]');
    }
}

function setStageDone(id) {
    const el = document.getElementById(`stage-${id}`);
    if (el) {
        el.className = 'stage done';
        el.innerText = el.innerText.replace(/\[.*\]/, '[✓]');
    }
}

/* --- Results Rendering --- */
function renderDashboard(report) {
    document.getElementById('executionView').classList.add('hidden');
    document.getElementById('resultsDashboard').classList.remove('hidden');

    // 1. Populate Meta (Fallback to N/A if backend triage.py hasn't added this yet)
    const meta = report.metadata || {};
    document.getElementById('metaFile').innerText = report.target ? report.target.split('/').pop() : 'unknown.apk';
    document.getElementById('metaPackage').innerText = meta.package || 'N/A';
    document.getElementById('metaVersion').innerText = meta.version || 'N/A';
    document.getElementById('metaHash').innerText = meta.sha256 || 'N/A';
    document.getElementById('metaSize').innerText = meta.size ? `${(meta.size/1024/1024).toFixed(2)} MB` : 'N/A';
    document.getElementById('metaTime').innerText = report.execution_time_seconds ? `${report.execution_time_seconds}s` : 'N/A';

    // 2. Count Severities
    const flags = report.flags || [];
    let counts = { high: 0, medium: 0, low: 0, info: 0 };
    
    flags.forEach(f => {
        const s = (f.severity || 'info').toLowerCase();
        if (counts[s] !== undefined) counts[s]++;
    });

    document.getElementById('countHigh').innerText = counts.high;
    document.getElementById('countMedium').innerText = counts.medium;
    document.getElementById('countLow').innerText = counts.low;
    document.getElementById('countInfo').innerText = counts.info;

    // 3. Render Collapsible Findings
    const container = document.getElementById('findingsContainer');
    container.innerHTML = '';

    if (flags.length === 0) {
        container.innerHTML = '<div style="padding: 15px; color: var(--text-muted); font-family: var(--font-mono);">No suspicious indicators found.</div>';
        return;
    }

    // Sort High to Info
    const sevWeight = { high: 3, medium: 2, low: 1, info: 0 };
    flags.sort((a, b) => sevWeight[(b.severity||'info').toLowerCase()] - sevWeight[(a.severity||'info').toLowerCase()]);

    flags.forEach((flag, index) => {
        const sev = (flag.severity || 'info').toLowerCase();
        const rule = flag.rule_id || flag.type || 'Anomaly';
        const file = flag.file || 'Unknown Location';
        const detail = flag.match || flag.description || flag.permission || 'N/A';
        
        const item = document.createElement('div');
        item.className = 'finding-item';
        
        item.innerHTML = `
            <div class="finding-header" onclick="this.parentElement.classList.toggle('open')">
                <div class="finding-severity sev-${sev}"></div>
                <div class="finding-title">${rule}</div>
                <div class="finding-file">${file.length > 50 ? '...'+file.slice(-47) : file}</div>
            </div>
            <div class="finding-details">
                <div class="detail-row">
                    <span class="detail-label">Detection Detail</span>
                    <span class="detail-value">${rule} triggered.</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Extracted Evidence</span>
                    <code class="detail-code">${detail.replace(/</g, "&lt;").replace(/>/g, "&gt;")}</code>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Severity Context</span>
                    <span class="detail-value" style="color: var(--color-${sev === 'medium' ? 'med' : sev}); text-transform: uppercase;">${sev} RISK LEVEL</span>
                </div>
            </div>
        `;
        container.appendChild(item);
    });
}

/* --- History Management --- */
function saveToHistory(report) {
    const filename = report.target ? report.target.split('/').pop() : 'scan_' + Date.now();
    const overall = report.overall_severity || 'LOW';
    const time = report.execution_time_seconds || 0;
    
    const entry = { filename, severity: overall, time, data: report };
    
    // Keep last 10
    scanHistory = [entry, ...scanHistory.filter(h => h.filename !== filename)].slice(0, 10);
    localStorage.setItem('redflagHistory', JSON.stringify(scanHistory));
    renderHistory();
}

function renderHistory() {
    const list = document.getElementById('historyList');
    list.innerHTML = '';
    
    scanHistory.forEach(entry => {
        const div = document.createElement('div');
        div.className = 'history-item';
        div.innerHTML = `
            <div class="history-filename">${entry.filename}</div>
            <div class="history-meta">
                <span style="color: var(--color-${entry.severity.toLowerCase() === 'medium' ? 'med' : entry.severity.toLowerCase()})">${entry.severity.toUpperCase()}</span>
                <span>${entry.time}s</span>
            </div>
        `;
        div.onclick = () => renderDashboard(entry.data);
        list.appendChild(div);
    });
}

function updateStatus(text, stateClass) {
    document.getElementById('statusBadge').className = `status-badge status-${stateClass}`;
    document.getElementById('statusText').innerText = text;
}
