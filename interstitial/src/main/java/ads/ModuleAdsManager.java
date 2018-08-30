package ads;

import android.app.Activity;
import android.content.Context;
import ads.AdsListenerManager;
import ads.ManagerBase;
import ads.ManagerInterstitial;
import ads.ManagerNative;

public class ModuleAdsManager extends ManagerBase implements AdsListenerManager.ListenerLogs{

    private ManagerNative managerNative;
    private ManagerInterstitial managerInterstitial;

    /**
     * @param nContext - context
     * @param reloaded - reload ads every time an interstitial is closed
     */
    public void initManagers(Activity nContext, boolean reloaded){
            isReloaded = reloaded;
            //managerNative = ManagerNative.getInstance(nContext);
            managerInterstitial = ManagerInterstitial.getInstance(nContext);
    }

    public ManagerNative getManagerNative() {
            return managerNative;
    }

    public ManagerInterstitial getManagerInterstitial() {
            return managerInterstitial;
    }

    public void setLogName(String nNameLog){
        nameLogs = nNameLog;
    }

    public void setListenerAds(AdsListenerManager.ListenerAds nListenerAds){
        if(nListenerAds!=null) listenerAds = nListenerAds;
    }

}
