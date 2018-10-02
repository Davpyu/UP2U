package com.surya.david.up2you;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ApplicationUtility {
    ConnectivityManager cm;
    NetworkInfo info;
    public boolean checkConnection(Context context){
        boolean flag = false;
        try {
            cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = cm.getActiveNetworkInfo();
            if (info.getType() == ConnectivityManager.TYPE_WIFI){
                System.out.println(info.getTypeName());
                flag = true;
            }else if (info.getType() == ConnectivityManager.TYPE_MOBILE){
                System.out.println(info.getTypeName());
                flag = true;
            }
        }catch (Exception exception){
            System.out.println("Exception at network connection..."+exception);
        }
        return flag;
    }
}
