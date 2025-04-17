package com.androidinsight.data.database.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\rH\'J\u0016\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/androidinsight/data/database/dao/CodeAnalysisDao;", "", "getByApkInfoId", "", "Lcom/androidinsight/data/database/entities/CodeAnalysis;", "apkInfoId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByClassName", "className", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWithSecurityIssues", "Lkotlinx/coroutines/flow/Flow;", "insert", "codeAnalysis", "(Lcom/androidinsight/data/database/entities/CodeAnalysis;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAll", "", "codeAnalyses", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface CodeAnalysisDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.androidinsight.data.database.entities.CodeAnalysis codeAnalysis, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.androidinsight.data.database.entities.CodeAnalysis> codeAnalyses, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM code_analysis WHERE apkInfoId = :apkInfoId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByApkInfoId(long apkInfoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.androidinsight.data.database.entities.CodeAnalysis>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM code_analysis WHERE className LIKE :className")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByClassName(@org.jetbrains.annotations.NotNull()
    java.lang.String className, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.androidinsight.data.database.entities.CodeAnalysis>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM code_analysis WHERE securityIssues != \'[]\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.androidinsight.data.database.entities.CodeAnalysis>> getWithSecurityIssues();
}