package ads.interstitialAds;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import ads.AdsListenerManager;
import ads.ManagerBase;


/**
 * Created by Alin on 4/24/2017.
 */

public class AdmobInterstitialAds extends ManagerBase {
    private final Context context;
    public InterstitialAd interstitialAdmob;
    private static AdmobInterstitialAds mInstance = null;
    public static String adUnitId;
    private AdsListenerManager.ListenerLogs listenerLogs;


    public AdmobInterstitialAds(Context context, String adUnitId, AdsListenerManager.ListenerLogs listenerLogs){
        this.context = context;
        this.listenerLogs = listenerLogs;
        initAdmobInterstitial(adUnitId);
    }

    public void setAdmobInterMuted(){
        MobileAds.initialize(context);
        MobileAds.setAppMuted(true);
    }


    public void initAdmobInterstitial(String adUnitId ) {
        interstitialAdmob = new InterstitialAd(context);
        if (adUnitId != null) {
            interstitialAdmob.setAdUnitId(adUnitId);
            listenerLogs.logs("Admob Interstitial: initialized");
            AdListener adListener = new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    if(isReloaded){
                        interstitialAdmob.loadAd(new AdRequest.Builder().addTestDevice("74df1a5b43f90b50dd8ea33699814380").build());
                    }
                    listenerLogs.logs("Admob Interstitial: Closed");
                    listenerLogs.isClosedInterAds();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    listenerLogs.logs("Admob Interstitial: Failed To Load: "+i);
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                    listenerLogs.logs("Admob Interstitial: Lef Application");
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                    listenerLogs.logs("Admob Interstitial: Opened");

                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    listenerLogs.logs("Admob Interstitial: Loaded");
                    if(listenerAds!=null)listenerAds.loadedInterstitialAds();
                }
            };
            interstitialAdmob.setAdListener(adListener);
            requestNewInterstitial();
        }

    }

    public boolean isLoadedAdmob() {
        if (interstitialAdmob!=null && interstitialAdmob.isLoaded()) {
            return true;
        } else {
            return false;
        }
    }

    public void showAdmobAds() {
        if (interstitialAdmob!=null && interstitialAdmob.isLoaded()) {
            listenerLogs.logs("Admob Interstitial: show");
            interstitialAdmob.show();

        }
    }


    public void requestNewInterstitial() {
        AdRequest adRequest;
        adRequest = new AdRequest.Builder().build();
        interstitialAdmob.loadAd(adRequest);
    }

    public synchronized static AdmobInterstitialAds getInstance(Context context, String adUnitId, AdsListenerManager.ListenerLogs listenerLogs) {
        if (mInstance == null) mInstance = new AdmobInterstitialAds(context,adUnitId,listenerLogs);
        return mInstance;

    }


}