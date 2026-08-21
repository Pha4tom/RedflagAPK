package androidx.core.provider;

import android.content.ContentProviderClient;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.util.Log;
import androidx.collection.LruCache;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import androidx.tracing.Trace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
class FontProvider {
    private static final LruCache<ProviderCacheKey, ProviderInfo> sProviderCache = new LruCache<>(2);
    private static final Comparator<byte[]> sByteArrayComparator = new Comparator() { // from class: androidx.core.provider.FontProvider$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return FontProvider.lambda$static$0((byte[]) obj, (byte[]) obj2);
        }
    };

    private FontProvider() {
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0040 A[Catch: all -> 0x006f, TryCatch #0 {all -> 0x006f, blocks: (B:3:0x0005, B:4:0x000b, B:6:0x0012, B:8:0x001f, B:10:0x0029, B:12:0x002f, B:19:0x0064, B:13:0x0040, B:15:0x004f, B:18:0x005b, B:20:0x0067), top: B:26:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static androidx.core.provider.FontsContractCompat.FontFamilyResult getFontFamilyResult(android.content.Context r10, java.util.List<androidx.core.provider.FontRequest> r11, android.os.CancellationSignal r12) throws android.content.pm.PackageManager.NameNotFoundException {
        /*
            java.lang.String r0 = "FontProvider.getFontFamilyResult"
            androidx.tracing.Trace.beginSection(r0)
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L6f
            r0.<init>()     // Catch: java.lang.Throwable -> L6f
            r1 = 0
        Lb:
            int r2 = r11.size()     // Catch: java.lang.Throwable -> L6f
            r3 = 0
            if (r1 >= r2) goto L67
            java.lang.Object r2 = r11.get(r1)     // Catch: java.lang.Throwable -> L6f
            androidx.core.provider.FontRequest r2 = (androidx.core.provider.FontRequest) r2     // Catch: java.lang.Throwable -> L6f
            int r4 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Throwable -> L6f
            r5 = 31
            r6 = 1
            if (r4 < r5) goto L40
            java.lang.String r4 = r2.getSystemFont()     // Catch: java.lang.Throwable -> L6f
            android.graphics.Typeface r5 = androidx.core.graphics.TypefaceCompat.getSystemFontFamily(r4)     // Catch: java.lang.Throwable -> L6f
            if (r5 == 0) goto L40
            android.graphics.fonts.Font r7 = androidx.core.graphics.TypefaceCompat.guessPrimaryFont(r5)     // Catch: java.lang.Throwable -> L6f
            if (r7 == 0) goto L40
            androidx.core.provider.FontsContractCompat$FontInfo[] r6 = new androidx.core.provider.FontsContractCompat.FontInfo[r6]     // Catch: java.lang.Throwable -> L6f
            androidx.core.provider.FontsContractCompat$FontInfo r8 = new androidx.core.provider.FontsContractCompat$FontInfo     // Catch: java.lang.Throwable -> L6f
            java.lang.String r9 = r2.getVariationSettings()     // Catch: java.lang.Throwable -> L6f
            r8.<init>(r4, r9)     // Catch: java.lang.Throwable -> L6f
            r6[r3] = r8     // Catch: java.lang.Throwable -> L6f
            r0.add(r6)     // Catch: java.lang.Throwable -> L6f
            goto L64
        L40:
            android.content.pm.PackageManager r3 = r10.getPackageManager()     // Catch: java.lang.Throwable -> L6f
            android.content.res.Resources r4 = r10.getResources()     // Catch: java.lang.Throwable -> L6f
            android.content.pm.ProviderInfo r3 = getProvider(r3, r2, r4)     // Catch: java.lang.Throwable -> L6f
            if (r3 != 0) goto L5b
            r4 = 0
            r5 = r4
            androidx.core.provider.FontsContractCompat$FontInfo[] r5 = (androidx.core.provider.FontsContractCompat.FontInfo[]) r5     // Catch: java.lang.Throwable -> L6f
            androidx.core.provider.FontsContractCompat$FontFamilyResult r4 = androidx.core.provider.FontsContractCompat.FontFamilyResult.create(r6, r4)     // Catch: java.lang.Throwable -> L6f
            androidx.tracing.Trace.endSection()
            return r4
        L5b:
            java.lang.String r4 = r3.authority     // Catch: java.lang.Throwable -> L6f
            androidx.core.provider.FontsContractCompat$FontInfo[] r4 = query(r10, r2, r4, r12)     // Catch: java.lang.Throwable -> L6f
            r0.add(r4)     // Catch: java.lang.Throwable -> L6f
        L64:
            int r1 = r1 + 1
            goto Lb
        L67:
            androidx.core.provider.FontsContractCompat$FontFamilyResult r1 = androidx.core.provider.FontsContractCompat.FontFamilyResult.create(r3, r0)     // Catch: java.lang.Throwable -> L6f
            androidx.tracing.Trace.endSection()
            return r1
        L6f:
            r0 = move-exception
            androidx.tracing.Trace.endSection()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.provider.FontProvider.getFontFamilyResult(android.content.Context, java.util.List, android.os.CancellationSignal):androidx.core.provider.FontsContractCompat$FontFamilyResult");
    }

    private static class ProviderCacheKey {
        String mAuthority;
        List<List<byte[]>> mCertificates;
        String mPackageName;

        ProviderCacheKey(String authority, String packageName, List<List<byte[]>> certificates) {
            this.mAuthority = authority;
            this.mPackageName = packageName;
            this.mCertificates = certificates;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ProviderCacheKey)) {
                return false;
            }
            ProviderCacheKey that = (ProviderCacheKey) o;
            return Objects.equals(this.mAuthority, that.mAuthority) && Objects.equals(this.mPackageName, that.mPackageName) && Objects.equals(this.mCertificates, that.mCertificates);
        }

        public int hashCode() {
            return Objects.hash(this.mAuthority, this.mPackageName, this.mCertificates);
        }
    }

    static void clearProviderCache() {
        sProviderCache.evictAll();
    }

    static ProviderInfo getProvider(PackageManager packageManager, FontRequest request, Resources resources) throws PackageManager.NameNotFoundException {
        Trace.beginSection("FontProvider.getProvider");
        try {
            List<List<byte[]>> requestCertificatesList = getCertificates(request, resources);
            ProviderCacheKey cacheKey = new ProviderCacheKey(request.getProviderAuthority(), request.getProviderPackage(), requestCertificatesList);
            ProviderInfo cachedPackageInfo = sProviderCache.get(cacheKey);
            if (cachedPackageInfo != null) {
                return cachedPackageInfo;
            }
            String providerAuthority = request.getProviderAuthority();
            ProviderInfo info = packageManager.resolveContentProvider(providerAuthority, 0);
            if (info == null) {
                throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
            }
            if (!info.packageName.equals(request.getProviderPackage())) {
                throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + request.getProviderPackage());
            }
            PackageInfo packageInfo = packageManager.getPackageInfo(info.packageName, 64);
            List<byte[]> signatures = convertToByteArrayList(packageInfo.signatures);
            Collections.sort(signatures, sByteArrayComparator);
            for (int i = 0; i < requestCertificatesList.size(); i++) {
                List<byte[]> requestSignatures = new ArrayList<>(requestCertificatesList.get(i));
                Collections.sort(requestSignatures, sByteArrayComparator);
                if (equalsByteArrayList(signatures, requestSignatures)) {
                    sProviderCache.put(cacheKey, info);
                    return info;
                }
            }
            Trace.endSection();
            return null;
        } finally {
            Trace.endSection();
        }
    }

    static FontsContractCompat.FontInfo[] query(Context context, FontRequest request, String authority, CancellationSignal cancellationSignal) {
        ContentQueryWrapper queryWrapper;
        int resultCode;
        int italicColumnIndex;
        Uri fileUri;
        int italicColumnIndex2;
        Trace.beginSection("FontProvider.query");
        try {
            ArrayList<FontsContractCompat.FontInfo> result = new ArrayList<>();
            Uri uri = new Uri.Builder().scheme("content").authority(authority).build();
            Uri fileBaseUri = new Uri.Builder().scheme("content").authority(authority).appendPath("file").build();
            Cursor cursor = null;
            ContentQueryWrapper queryWrapper2 = ContentQueryWrapper.make(context, uri);
            try {
                String[] projection = {"_id", FontsContractCompat.Columns.FILE_ID, FontsContractCompat.Columns.TTC_INDEX, FontsContractCompat.Columns.VARIATION_SETTINGS, FontsContractCompat.Columns.WEIGHT, FontsContractCompat.Columns.ITALIC, FontsContractCompat.Columns.RESULT_CODE};
                Trace.beginSection("ContentQueryWrapper.query");
                queryWrapper = queryWrapper2;
                try {
                    try {
                        Cursor cursor2 = queryWrapper.query(uri, projection, "query = ?", new String[]{request.getQuery()}, null, cancellationSignal);
                        Trace.endSection();
                        if (cursor2 != null && cursor2.getCount() > 0) {
                            int resultCodeColumnIndex = cursor2.getColumnIndex(FontsContractCompat.Columns.RESULT_CODE);
                            result = new ArrayList<>();
                            int idColumnIndex = cursor2.getColumnIndex("_id");
                            int fileIdColumnIndex = cursor2.getColumnIndex(FontsContractCompat.Columns.FILE_ID);
                            int ttcIndexColumnIndex = cursor2.getColumnIndex(FontsContractCompat.Columns.TTC_INDEX);
                            int weightColumnIndex = cursor2.getColumnIndex(FontsContractCompat.Columns.WEIGHT);
                            int weight = cursor2.getColumnIndex(FontsContractCompat.Columns.ITALIC);
                            while (cursor2.moveToNext()) {
                                if (resultCodeColumnIndex != -1) {
                                    resultCode = cursor2.getInt(resultCodeColumnIndex);
                                } else {
                                    resultCode = 0;
                                }
                                int ttcIndex = ttcIndexColumnIndex != -1 ? cursor2.getInt(ttcIndexColumnIndex) : 0;
                                if (fileIdColumnIndex == -1) {
                                    long id = cursor2.getLong(idColumnIndex);
                                    italicColumnIndex = weight;
                                    Uri fileUri2 = ContentUris.withAppendedId(uri, id);
                                    fileUri = fileUri2;
                                } else {
                                    italicColumnIndex = weight;
                                    long id2 = cursor2.getLong(fileIdColumnIndex);
                                    fileUri = ContentUris.withAppendedId(fileBaseUri, id2);
                                }
                                int weight2 = weightColumnIndex != -1 ? cursor2.getInt(weightColumnIndex) : 400;
                                int resultCodeColumnIndex2 = resultCodeColumnIndex;
                                int resultCodeColumnIndex3 = italicColumnIndex;
                                if (resultCodeColumnIndex3 != -1) {
                                    italicColumnIndex2 = resultCodeColumnIndex3;
                                    boolean italic = cursor2.getInt(resultCodeColumnIndex3) == 1;
                                    result.add(FontsContractCompat.FontInfo.create(fileUri, ttcIndex, weight2, italic, resultCode));
                                    weight = italicColumnIndex2;
                                    resultCodeColumnIndex = resultCodeColumnIndex2;
                                } else {
                                    italicColumnIndex2 = resultCodeColumnIndex3;
                                }
                                result.add(FontsContractCompat.FontInfo.create(fileUri, ttcIndex, weight2, italic, resultCode));
                                weight = italicColumnIndex2;
                                resultCodeColumnIndex = resultCodeColumnIndex2;
                            }
                        }
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        queryWrapper.close();
                        return (FontsContractCompat.FontInfo[]) result.toArray(new FontsContractCompat.FontInfo[0]);
                    } catch (Throwable th) {
                        th = th;
                        if (0 != 0) {
                            cursor.close();
                        }
                        queryWrapper.close();
                        throw th;
                    }
                } finally {
                }
            } catch (Throwable th2) {
                th = th2;
                queryWrapper = queryWrapper2;
            }
        } finally {
        }
    }

    private static List<List<byte[]>> getCertificates(FontRequest request, Resources resources) {
        if (request.getCertificates() != null) {
            return request.getCertificates();
        }
        int resourceId = request.getCertificatesArrayResId();
        return FontResourcesParserCompat.readCerts(resources, resourceId);
    }

    static /* synthetic */ int lambda$static$0(byte[] l, byte[] r) {
        if (l.length != r.length) {
            return l.length - r.length;
        }
        for (int i = 0; i < l.length; i++) {
            if (l[i] != r[i]) {
                return l[i] - r[i];
            }
        }
        return 0;
    }

    private static boolean equalsByteArrayList(List<byte[]> signatures, List<byte[]> requestSignatures) {
        if (signatures.size() != requestSignatures.size()) {
            return false;
        }
        for (int i = 0; i < signatures.size(); i++) {
            if (!Arrays.equals(signatures.get(i), requestSignatures.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatures) {
        List<byte[]> shaList = new ArrayList<>();
        for (Signature signature : signatures) {
            shaList.add(signature.toByteArray());
        }
        return shaList;
    }

    private interface ContentQueryWrapper {
        void close();

        Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal);

        static ContentQueryWrapper make(Context context, Uri uri) {
            return new ContentQueryWrapperApi24Impl(context, uri);
        }
    }

    private static class ContentQueryWrapperApi16Impl implements ContentQueryWrapper {
        private final ContentProviderClient mClient;

        ContentQueryWrapperApi16Impl(Context context, Uri uri) {
            this.mClient = context.getContentResolver().acquireUnstableContentProviderClient(uri);
        }

        @Override // androidx.core.provider.FontProvider.ContentQueryWrapper
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
            RemoteException e;
            if (this.mClient == null) {
                return null;
            }
            try {
                try {
                    return this.mClient.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
                } catch (RemoteException e2) {
                    e = e2;
                    Log.w("FontsProvider", "Unable to query the content provider", e);
                    return null;
                }
            } catch (RemoteException e3) {
                e = e3;
            }
        }

        @Override // androidx.core.provider.FontProvider.ContentQueryWrapper
        public void close() {
            if (this.mClient != null) {
                this.mClient.release();
            }
        }
    }

    private static class ContentQueryWrapperApi24Impl implements ContentQueryWrapper {
        private final ContentProviderClient mClient;

        ContentQueryWrapperApi24Impl(Context context, Uri uri) {
            this.mClient = context.getContentResolver().acquireUnstableContentProviderClient(uri);
        }

        @Override // androidx.core.provider.FontProvider.ContentQueryWrapper
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
            RemoteException e;
            if (this.mClient == null) {
                return null;
            }
            try {
                try {
                    return this.mClient.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
                } catch (RemoteException e2) {
                    e = e2;
                    Log.w("FontsProvider", "Unable to query the content provider", e);
                    return null;
                }
            } catch (RemoteException e3) {
                e = e3;
            }
        }

        @Override // androidx.core.provider.FontProvider.ContentQueryWrapper
        public void close() {
            if (this.mClient != null) {
                this.mClient.close();
            }
        }
    }
}
