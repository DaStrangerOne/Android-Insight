package com.androidinsight.core;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0082@\u00a2\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0006H\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u0006H\u0002J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0006H\u0002J\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0006H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u001f"}, d2 = {"Lcom/androidinsight/core/ApkProcessor;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "workDir", "Ljava/io/File;", "getWorkDir", "()Ljava/io/File;", "workDir$delegate", "Lkotlin/Lazy;", "copyApkToWorkDir", "uri", "Landroid/net/Uri;", "analysisDir", "(Landroid/net/Uri;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createAnalysisDir", "decompileDex", "Lcom/androidinsight/core/DecompiledCode;", "extractApk", "", "apkFile", "outputDir", "processApk", "Lcom/androidinsight/core/ProcessResult;", "apkUri", "(Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processManifest", "Lcom/androidinsight/core/ManifestInfo;", "manifestFile", "Companion", "app_debug"})
public final class ApkProcessor {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String WORK_DIR = "apk_analysis";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String MANIFEST_FILE = "AndroidManifest.xml";
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy workDir$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.androidinsight.core.ApkProcessor.Companion Companion = null;
    
    public ApkProcessor(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final java.io.File getWorkDir() {
        return null;
    }
    
    /**
     * Process an APK file from a Uri
     * @param apkUri Uri of the APK file to process
     * @return ProcessResult containing analysis information
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object processApk(@org.jetbrains.annotations.NotNull()
    android.net.Uri apkUri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.androidinsight.core.ProcessResult> $completion) {
        return null;
    }
    
    private final java.io.File createAnalysisDir() {
        return null;
    }
    
    private final java.lang.Object copyApkToWorkDir(android.net.Uri uri, java.io.File analysisDir, kotlin.coroutines.Continuation<? super java.io.File> $completion) {
        return null;
    }
    
    private final void extractApk(java.io.File apkFile, java.io.File outputDir) {
    }
    
    private final com.androidinsight.core.ManifestInfo processManifest(java.io.File manifestFile) {
        return null;
    }
    
    private final com.androidinsight.core.DecompiledCode decompileDex(java.io.File analysisDir) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/androidinsight/core/ApkProcessor$Companion;", "", "()V", "MANIFEST_FILE", "", "WORK_DIR", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}