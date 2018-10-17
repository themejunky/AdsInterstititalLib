package ads.interstitial;

import android.content.Context;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
//import com.facebook.ads.InterstitialAdListener;

import ads.ListenerContract;

/**
 * Created by Junky2 on 7/20/2018.
 */

public class FacebookInterstitialAds {
    private final Context activity;
    private final Boolean isReloaded;
    public InterstitialAd interstitialAd;
    private String numeTag;
    private static boolean isLoaded;
    private ListenerContract.ListenerIntern listener;
    private boolean noFacebookError=true;

    public FacebookInterstitialAds(Context activity, String nameTag, String keyFacebook, ListenerContract.ListenerIntern listener,Boolean isReloaded){
        this.activity=activity;
        this.numeTag=nameTag;
        this.listener=listener;
        this.isReloaded = isReloaded;
        initFacebookInterstitial(keyFacebook);
    }


    public void initFacebookInterstitial(String keyFacebook) {

        interstitialAd = new InterstitialAd(activity, keyFacebook);
        Log.d(numeTag,"Facebook init " + numeTag + " " + interstitialAd);
        Log.d("testing","Facebook interstitial requested");

        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Log.d("testing","Facebook interstitial displayed");
                Log.d(numeTag,"Facebook Interstitial: displayed! " + numeTag + " " + interstitialAd);
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.d(numeTag,"Facebook Interstitial: dismissed!");
                Log.d("dasdas","isInterstitialClosed");
                listener.isInterstitialClosed();


                if(isReloaded){
                    interstitialAd.loadAd();
                }

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d(numeTag,"Facebook Interstitial: error: "+ adError.getErrorMessage());
                noFacebookError = false;
            }

            @Override
            public void onAdLoaded(Ad ad) {
                isLoaded =true;
                Log.d(numeTag,"Facebook Interstitial: is Loaded  " + numeTag + " " + interstitialAd);
                listener.somethingReloaded("facebook");
                //interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

        if(isReloaded){
            interstitialAd.loadAd();
            Log.d(numeTag,"Facebook Interstitial: isReloaded: " +interstitialAd + " " + interstitialAd);
        }
        interstitialAd.loadAd();
    }

    public  void showInterstitialFacebook() {
        if (interstitialAd !=null && isLoaded) {
            Log.d(numeTag,"Facebook Interstitial: is shown: " +interstitialAd);
            interstitialAd.show();

        } else {
            Log.d(numeTag,"Facebook Interstitial: show failed: " + interstitialAd);
        }
    }
    public boolean isFacebookLoaded(){
        Log.d(numeTag,"Facebook Interstitial: isFacebookLoaded :interstitialAd " + interstitialAd +" noFacebookError " +noFacebookError + " isAdLoaded " + interstitialAd.isAdLoaded() + " isLoaded " + isLoaded );
        if(interstitialAd!=null  && (interstitialAd.isAdLoaded()||isLoaded) ){
            Log.d(numeTag,"Facebook Interstitial: isFacebookLoaded true");
            return true;
        }else {
            Log.d(numeTag,"Facebook Interstitial: isFacebookLoaded false");
            return false;
        }
    }
    public void requestNewInterstitialFacebook() {
        interstitialAd.loadAd();

    }

    public boolean isFaceookError(){
        if(noFacebookError){
            return false;
        }else {
            return true;
        }
    }
}
