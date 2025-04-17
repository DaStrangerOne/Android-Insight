package com.androidinsight.data.database.entities;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B_\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\tH\u00c6\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00060\tH\u00c6\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060\tH\u00c6\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060\tH\u00c6\u0003Jq\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\tH\u00c6\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020&H\u00d6\u0001J\t\u0010\'\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013\u00a8\u0006("}, d2 = {"Lcom/androidinsight/data/database/entities/CodeAnalysis;", "", "id", "", "apkInfoId", "className", "", "methodName", "permissionUsage", "", "intentOperations", "dataFlowIssues", "securityIssues", "(JJLjava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getApkInfoId", "()J", "getClassName", "()Ljava/lang/String;", "getDataFlowIssues", "()Ljava/util/List;", "getId", "getIntentOperations", "getMethodName", "getPermissionUsage", "getSecurityIssues", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"})
@androidx.room.Entity(tableName = "code_analysis")
public final class CodeAnalysis {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long apkInfoId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String className = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String methodName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> permissionUsage = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> intentOperations = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> dataFlowIssues = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> securityIssues = null;
    
    public CodeAnalysis(long id, long apkInfoId, @org.jetbrains.annotations.NotNull()
    java.lang.String className, @org.jetbrains.annotations.NotNull()
    java.lang.String methodName, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> permissionUsage, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> intentOperations, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> dataFlowIssues, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> securityIssues) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getApkInfoId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getClassName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMethodName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getPermissionUsage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getIntentOperations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getDataFlowIssues() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getSecurityIssues() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.androidinsight.data.database.entities.CodeAnalysis copy(long id, long apkInfoId, @org.jetbrains.annotations.NotNull()
    java.lang.String className, @org.jetbrains.annotations.NotNull()
    java.lang.String methodName, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> permissionUsage, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> intentOperations, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> dataFlowIssues, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> securityIssues) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}