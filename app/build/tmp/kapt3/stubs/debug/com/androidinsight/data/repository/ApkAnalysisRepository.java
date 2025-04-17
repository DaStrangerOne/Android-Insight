package com.androidinsight.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ$\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u00020\u000e2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0017\u00a8\u0006\u0018"}, d2 = {"Lcom/androidinsight/data/repository/ApkAnalysisRepository;", "", "()V", "analyzeApk", "Lcom/androidinsight/data/database/entities/ApkInfo;", "context", "Landroid/content/Context;", "apkFile", "Ljava/io/File;", "(Landroid/content/Context;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractManifestComponents", "", "Lcom/androidinsight/data/database/entities/ManifestComponent;", "saveAnalysisResult", "", "result", "Lcom/androidinsight/data/database/entities/AnalysisResult;", "(Lcom/androidinsight/data/database/entities/AnalysisResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveApkInfo", "apkInfo", "(Lcom/androidinsight/data/database/entities/ApkInfo;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveManifestComponents", "components", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ApkAnalysisRepository {
    
    public ApkAnalysisRepository() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object analyzeApk(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.io.File apkFile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.androidinsight.data.database.entities.ApkInfo> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object extractManifestComponents(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.io.File apkFile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.androidinsight.data.database.entities.ManifestComponent>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveApkInfo(@org.jetbrains.annotations.NotNull()
    com.androidinsight.data.database.entities.ApkInfo apkInfo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveAnalysisResult(@org.jetbrains.annotations.NotNull()
    com.androidinsight.data.database.entities.AnalysisResult result, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveManifestComponents(@org.jetbrains.annotations.NotNull()
    java.util.List<com.androidinsight.data.database.entities.ManifestComponent> components, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}