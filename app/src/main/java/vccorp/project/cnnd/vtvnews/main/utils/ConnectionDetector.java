package vccorp.project.cnnd.vtvnews.main.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Admin on 4/4/2016.
 */
public class ConnectionDetector {
    private Context mContext;
    public ConnectionDetector(Context context){
        this.mContext = context;
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info != null)
                for(int i=0;i<info.length;i++)
                    if(info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }
}