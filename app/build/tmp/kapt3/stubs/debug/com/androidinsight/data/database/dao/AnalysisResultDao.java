package com.androidinsight.data.database.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0016\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\r\u00a8\u0006\u0010"}, d2 = {"Lcom/androidinsight/data/database/dao/AnalysisResultDao;", "", "getAllResults", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/androidinsight/data/database/entities/AnalysisResult;", "getByApkInfoId", "apkInfoId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVulnerableApps", "insert", "analysisResult", "(Lcom/androidinsight/data/database/entities/AnalysisResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "", "app_debug"})
@androidx.room.Dao()
public abstract interface AnalysisResultDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.androidinsight.data.database.entities.AnalysisResult analysisResult, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.androidinsight.data.database.entities.AnalysisResult analysisResult, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM analysis_results WHERE apkInfoId = :apkInfoId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByApkInfoId(long apkInfoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.androidinsight.data.database.entities.AnalysisResult> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM analysis_results ORDER BY analysisDate DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.androidinsight.data.database.entities.AnalysisResult>> getAllResults();
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM analysis_results WHERE securityScore < 70")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.androidinsight.data.database.entities.AnalysisResult>> getVulnerableApps();
}