package vccorp.project.cnnd.vtvnews.main.view;

import android.app.Activity;
import android.app.Application;

import vccorp.project.cnnd.vtvnews.main.utils.AppPreferences;

/**
 * Created by Admin on 3/23/2016.
 */
public class VTVNewsApp extends Application {
    private static VTVNewsApp Instace;

    @Override
    public void onCreate() {
        super.onCreate();
        AppPreferences.init(this);
        Instace = this;

    }

    private static Activity activity;

    public static Activity SplasActivity(){
        return activity;
    }

    public static VTVNewsApp getInstace(){
        return Instace;
    }

    public static void setSplashActivity(Activity mActivity){
        activity = mActivity;
    }
}
