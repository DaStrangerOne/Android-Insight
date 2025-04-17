package com.androidinsight.ui.adapters;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u000eB\u0005\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\tH\u0016\u00a8\u0006\u000f"}, d2 = {"Lcom/androidinsight/ui/adapters/MethodAnalysisAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/androidinsight/core/CodeAnalyzer$MethodSignature;", "Lcom/androidinsight/ui/adapters/MethodAnalysisAdapter$ViewHolder;", "()V", "onBindViewHolder", "", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "app_debug"})
public final class MethodAnalysisAdapter extends androidx.recyclerview.widget.ListAdapter<com.androidinsight.core.CodeAnalyzer.MethodSignature, com.androidinsight.ui.adapters.MethodAnalysisAdapter.ViewHolder> {
    
    public MethodAnalysisAdapter() {
        super(null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.androidinsight.ui.adapters.MethodAnalysisAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.androidinsight.ui.adapters.MethodAnalysisAdapter.ViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\tH\u0002R\u0010\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0005\u00a8\u0006\u0012"}, d2 = {"Lcom/androidinsight/ui/adapters/MethodAnalysisAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "error/NonExistentClass", "(Lerror/NonExistentClass;)V", "Lerror/NonExistentClass;", "bind", "", "signature", "Lcom/androidinsight/core/CodeAnalyzer$MethodSignature;", "buildMethodSignature", "", "getAccessLevelString", "accessFlags", "", "getSecurityWarning", "hasSecurityRisk", "", "app_debug"})
    public static final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final error.NonExistentClass binding = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        error.NonExistentClass binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.androidinsight.core.CodeAnalyzer.MethodSignature signature) {
        }
        
        private final java.lang.String buildMethodSignature(com.androidinsight.core.CodeAnalyzer.MethodSignature signature) {
            return null;
        }
        
        private final java.lang.String getAccessLevelString(int accessFlags) {
            return null;
        }
        
        private final boolean hasSecurityRisk(com.androidinsight.core.CodeAnalyzer.MethodSignature signature) {
            return false;
        }
        
        private final java.lang.String getSecurityWarning(com.androidinsight.core.CodeAnalyzer.MethodSignature signature) {
            return null;
        }
    }
}