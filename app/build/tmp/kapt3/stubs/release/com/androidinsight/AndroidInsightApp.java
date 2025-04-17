package com.androidinsight;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\b\u0010\u0005\u001a\u00020\u0004H\u0016\u00a8\u0006\u0007"}, d2 = {"Lcom/androidinsight/AndroidInsightApp;", "Landroid/app/Application;", "()V", "initializeComponents", "", "onCreate", "Companion", "app_release"})
public final class AndroidInsightApp extends android.app.Application {
    private static com.androidinsight.AndroidInsightApp instance;
    private static com.androidinsight.data.database.AppDatabase database;
    @org.jetbrains.annotations.NotNull()
    public static final com.androidinsight.AndroidInsightApp.Companion Companion = null;
    
    public AndroidInsightApp() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    private final void initializeComponents() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086.\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b@BX\u0086.\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lcom/androidinsight/AndroidInsightApp$Companion;", "", "()V", "<set-?>", "Lcom/androidinsight/data/database/AppDatabase;", "database", "getDatabase", "()Lcom/androidinsight/data/database/AppDatabase;", "Lcom/androidinsight/AndroidInsightApp;", "instance", "getInstance", "()Lcom/androidinsight/AndroidInsightApp;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.androidinsight.AndroidInsightApp getInstance() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.androidinsight.data.database.AppDatabase getDatabase() {
            return null;
        }
    }
}