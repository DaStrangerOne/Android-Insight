package com.androidinsight.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019R\u0016\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\r\u00a8\u0006\u001a"}, d2 = {"Lcom/androidinsight/ui/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_currentAnalysis", "Landroidx/lifecycle/MutableLiveData;", "Lcom/androidinsight/data/database/entities/AnalysisResult;", "_processingState", "Lcom/androidinsight/ui/ProcessingState;", "apkProcessor", "Lcom/androidinsight/core/ApkProcessor;", "currentAnalysis", "Landroidx/lifecycle/LiveData;", "getCurrentAnalysis", "()Landroidx/lifecycle/LiveData;", "database", "Lcom/androidinsight/data/database/AppDatabase;", "processingState", "getProcessingState", "calculateSecurityScore", "", "result", "Lcom/androidinsight/core/ProcessResult$Success;", "processApk", "", "uri", "Landroid/net/Uri;", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.androidinsight.core.ApkProcessor apkProcessor = null;
    @org.jetbrains.annotations.NotNull()
    private final com.androidinsight.data.database.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.androidinsight.ui.ProcessingState> _processingState = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.androidinsight.ui.ProcessingState> processingState = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.androidinsight.data.database.entities.AnalysisResult> _currentAnalysis = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.androidinsight.data.database.entities.AnalysisResult> currentAnalysis = null;
    
    public MainViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.androidinsight.ui.ProcessingState> getProcessingState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.androidinsight.data.database.entities.AnalysisResult> getCurrentAnalysis() {
        return null;
    }
    
    public final void processApk(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    private final int calculateSecurityScore(com.androidinsight.core.ProcessResult.Success result) {
        return 0;
    }
}