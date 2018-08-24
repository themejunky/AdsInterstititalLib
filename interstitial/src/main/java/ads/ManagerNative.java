package ads;

import android.content.Context;
import android.util.Log;
import android.view.View;

import ads.nativeAdmob.AdmobNativeAds;

/**
 * Created by Junky2 on 8/23/2018.
 */

public class ManagerNative implements ListenerContract.ListenerLogs {

    AdmobNativeAds admobNativeAds;
    private String nameLog;

    public ManagerNative(Context context, String admobKey,View view){
        admobNativeAds = new AdmobNativeAds(context,admobKey,this,view);
    }

    public void setLogName(String nameLog){
        this.nameLog = nameLog;
    }

    @Override
    public void logs(String logs) {
        Log.d(nameLog,logs);
    }
}
