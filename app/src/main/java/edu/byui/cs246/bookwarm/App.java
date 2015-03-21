package edu.byui.cs246.bookwarm;

import android.app.Application;
import android.content.Context;

/**
 * A class that will statically return the application context
 */
public class App extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }

    public static Context getAppContext() {return App.context;}
}
