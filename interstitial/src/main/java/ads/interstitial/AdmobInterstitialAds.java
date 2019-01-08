package ads.interstitial;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import ads.ListenerContract;


/**
 * Created by Alin on 4/24/2017.
 */

public class AdmobInterstitialAds  {
    private final Context context;
    private final String numeTag;
    private final Boolean isReloaded;
    public InterstitialAd interstitialAdmob;
    private static AdmobInterstitialAds mInstance = null;
    private ListenerContract.ListenerIntern listener;
    public boolean isAdmobLoaded;
    private AdRequest adRequest;

    public AdmobInterstitialAds(Context context, String nameTag, String keyAdmob, ListenerContract.ListenerIntern listener,Boolean isReloaded){
        this.context = context;
        this.numeTag=nameTag;
        this.listener = listener;
        this.isReloaded = isReloaded;
        initAdmobInterstitial(keyAdmob);
    }


    public void initAdmobInterstitial(String adUnitId ) {
        adRequest = new AdRequest.Builder().build();
        interstitialAdmob = new com.google.android.gms.ads.InterstitialAd(context);
        if (adUnitId != null) {
            interstitialAdmob.setAdUnitId(adUnitId);
            AdListener adListener = new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    Log.d(numeTag,"Admob Interstitial: Closed!");
                    listener.isInterstitialClosed();
                    if(isReloaded){
                        requestNewInterstitialAdmob();
                    }
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Log.d(numeTag,"Admob Interstitial: Failed To Load: "+i);
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                    Log.d(numeTag,"Admob Interstitial: onAdLeftApplication!");
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                    Log.d(numeTag,"Admob Interstitial: onAdOpened!");
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.d(numeTag,"Admob Interstitial: onAdLoaded!");
                    isAdmobLoaded = true;
                    listener.somethingReloaded("admob");

                }
            };
            interstitialAdmob.setAdListener(adListener);
            if(isReloaded){
                requestNewInterstitialAdmob();
            }
        }

    }

    public boolean isLoadedAdmob() {
        Log.d(numeTag,"isLoadedAdmob : " +interstitialAdmob.isLoaded());
        if (interstitialAdmob!=null && interstitialAdmob.isLoaded()) {
            return true;
        } else {
            return false;
        }
    }

    public void showInterstitialAdmob() {
        if (interstitialAdmob!=null && interstitialAdmob.isLoaded()) {
            Log.d(numeTag,"Admob Interstitial: show!");
            interstitialAdmob.show();

        }
    }

    public void requestNewInterstitialAdmob() {
        Log.d(numeTag,"Admob Interstitial: requestNewInterstitialAdmob 1: "+interstitialAdmob);
        interstitialAdmob.loadAd(new AdRequest.Builder().build());
        Log.d(numeTag,"Admob Interstitial: requestNewInterstitialAdmob 2: "+adRequest);


    }






}