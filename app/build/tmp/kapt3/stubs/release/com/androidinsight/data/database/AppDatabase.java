package com.androidinsight.data.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\u0007"}, d2 = {"Lcom/androidinsight/data/database/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "analysisResultDao", "Lcom/androidinsight/data/database/dao/AnalysisResultDao;", "apkInfoDao", "Lcom/androidinsight/data/database/dao/ApkInfoDao;", "app_release"})
@androidx.room.Database(entities = {com.androidinsight.data.database.entities.ApkInfo.class, com.androidinsight.data.database.entities.AnalysisResult.class}, version = 1, exportSchema = false)
@androidx.room.TypeConverters(value = {com.androidinsight.data.database.Converters.class})
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.androidinsight.data.database.dao.ApkInfoDao apkInfoDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.androidinsight.data.database.dao.AnalysisResultDao analysisResultDao();
}