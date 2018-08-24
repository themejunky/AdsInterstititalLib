package ads.interstitial;


import android.content.Context;
import android.util.Log;

import com.appnext.ads.interstitial.Interstitial;
import com.appnext.base.Appnext;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;

import ads.ListenerContract;


/**
 * Created by Alin on 5/21/2017.
 */

public class AppnextAdsInterstitial {
    public static AppnextAdsInterstitial instance = null;
    private final Context context;
    private final String nameTag;
    private final ListenerContract.ListenerIntern listener;
    public Interstitial interstitialAppnext;

    public AppnextAdsInterstitial(Context context,String nameTag, String keyAppext, ListenerContract.ListenerIntern listener) {
        this.listener = listener;
        this.nameTag = nameTag;
        this.context = context;
        initAppnext(keyAppext);

    }

    //initAppnext(activity,"8106d659-a20b-4640-943b-d6b0aab18d08");
    public void initAppnext(String placementID) {
        Appnext.init(context);
        interstitialAppnext = new Interstitial(context, placementID);
        Log.d(nameTag,"Appnext inter: initialized");
        interstitialAppnext.setOnAdClosedCallback(new OnAdClosed() {
            @Override
            public void onAdClosed() {
                Log.d(nameTag,"Appnext Interstitial: Closed!");
                listener.isInterstitialClosed();
            }
        });
        interstitialAppnext.setOnAdErrorCallback(new OnAdError() {
            @Override
            public void adError(String s) {
                Log.d(nameTag,"Appnext inter error: " + s.toString());
            }
        });
        interstitialAppnext.setOnAdLoadedCallback(new OnAdLoaded() {
            @Override
            public void adLoaded(String s) {
                listener.somethingReloaded("appnext");
                Log.d(nameTag,"Appnext Interstitial: onAdLoaded!");
            }
        });
        interstitialAppnext.setBackButtonCanClose(true);
        interstitialAppnext.setMute(true);
        interstitialAppnext.setAutoPlay(true);


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

    public synchronized static AppnextAdsInterstitial getInstance(Context context,String nameTag, String keyAppext, ListenerContract.ListenerIntern listener) {
        if (instance == null) instance = new AppnextAdsInterstitial(context,nameTag,keyAppext,listener);
        return instance;
    }


    public void requestNewInterstitialAppnext() {
        interstitialAppnext.loadAd();
    }
}
