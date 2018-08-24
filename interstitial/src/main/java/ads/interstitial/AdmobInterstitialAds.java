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
    public InterstitialAd interstitialAdmob;
    private static AdmobInterstitialAds mInstance = null;
    private ListenerContract.ListenerIntern listener;
    private boolean isAdmobLoaded;



    public AdmobInterstitialAds(Context context, String nameTag, String keyAdmob, ListenerContract.ListenerIntern listener){
        this.context = context;
        this.numeTag=nameTag;
        this.listener = listener;
        initAdmobInterstitial(keyAdmob);
    }


    public void initAdmobInterstitial(String adUnitId ) {

        interstitialAdmob = new com.google.android.gms.ads.InterstitialAd(context);
        if (adUnitId != null) {
            interstitialAdmob.setAdUnitId(adUnitId);
            AdListener adListener = new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    Log.d(numeTag,"Admob Interstitial: Closed!");
                    listener.isInterstitialClosed();
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
           // requestNewInterstitialAdmob();
        }

    }

    public boolean isLoadedAdmob() {
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
        Log.d(numeTag,"Admob Interstitial: requestNewInterstitialAdmob");
        AdRequest adRequest;
        adRequest = new AdRequest.Builder().build();
        interstitialAdmob.loadAd(adRequest);


    }






}