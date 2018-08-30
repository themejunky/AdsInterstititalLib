/*
package ads.interstitialAds;


import android.content.Context;
import android.util.Log;


import com.appnext.ads.interstitial.Interstitial;
import com.appnext.base.Appnext;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.managers.ManagerBase;


*/
/**
 * Created by Alin on 5/21/2017.
 *//*


public class AppnextAdsInterstitial extends ManagerBase {
    public static AppnextAdsInterstitial instance = null;
    private final Context context;
    public Interstitial interstitialAppnext;
    private AdsListenerManager.ListenerLogs listenerLogs;

    public AppnextAdsInterstitial(Context context,String keyAppext,AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
        this.context = context;
        initAppnext(keyAppext);

    }

    //initAppnext(activity,"8106d659-a20b-4640-943b-d6b0aab18d08");
    public void initAppnext(String placementID) {
        Appnext.init(context);
        interstitialAppnext = new Interstitial(context, placementID);
        listenerLogs.logs("Appnext inter: initialized");
        interstitialAppnext.setOnAdClosedCallback(new OnAdClosed() {
            @Override
            public void onAdClosed() {
                listenerLogs.logs("Appnext inter: Closed");
                listenerLogs.isClosedInterAds();
            }
        });
        interstitialAppnext.setOnAdErrorCallback(new OnAdError() {
            @Override
            public void adError(String s) {
                listenerLogs.logs("Appnext inter error: " + s.toString());
            }
        });
        interstitialAppnext.setOnAdLoadedCallback(new OnAdLoaded() {
            @Override
            public void adLoaded(String s) {
                if(listenerAds!=null)listenerAds.loadedInterstitialAds();
                listenerLogs.logs("Appnext inter: is Loaded");
            }
        });
        interstitialAppnext.setBackButtonCanClose(true);
        interstitialAppnext.setMute(true);
        interstitialAppnext.setAutoPlay(true);
        interstitialAppnext.loadAd();

    }

    public void showAppNext() {
        if (interstitialAppnext != null && interstitialAppnext.isAdLoaded()) {
            interstitialAppnext.showAd();
        }
    }

    public boolean isLoadedAppNext() {
        if (interstitialAppnext != null && interstitialAppnext.isAdLoaded()) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized static AppnextAdsInterstitial getInstance(Context context,String keyAppext,AdsListenerManager.ListenerLogs listenerLogs) {
        if (instance == null) instance = new AppnextAdsInterstitial(context,keyAppext,listenerLogs);
        return instance;
    }
}
*/
