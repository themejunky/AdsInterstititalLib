package ads.interstitial;

import android.content.Context;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import ads.ListenerContract;


/**
 * Created by Junky2 on 7/20/2018.
 */

public class FacebookInterstitialAds {
    private final Context activity;
    public InterstitialAd interstitialAd;
    private String numeTag;
    private boolean isLoaded;
    private ListenerContract.ListenerIntern listener;
    private boolean noFacebookError=true;

    public FacebookInterstitialAds(Context activity, String nameTag, String keyFacebook, ListenerContract.ListenerIntern listener){
        this.activity=activity;
        this.numeTag=nameTag;
        this.listener=listener;
        initFacebookInterstitial(keyFacebook);
    }


    public void initFacebookInterstitial(String keyFacebook) {
        interstitialAd = new InterstitialAd(activity, keyFacebook);
        interstitialAd.setAdListener(new InterstitialAdListener() {

            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Log.d(numeTag,"Facebook Interstitial: displayed!");

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                interstitialAd.loadAd();
                Log.d(numeTag,"Facebook Interstitial: dismissed!");
                Log.d("dasdas","isInterstitialClosed");
                listener.isInterstitialClosed();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d(numeTag,"Facebook Interstitial: error: "+ adError.getErrorMessage());
                noFacebookError = false;
            }

            @Override
            public void onAdLoaded(Ad ad) {
                isLoaded =true;
                Log.d(numeTag,"Facebook Interstitial: is Loaded");
                listener.somethingReloaded("facebook");
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        //interstitialAd.loadAd();
    }

    public  void showInterstitialFacebook() {

        if (interstitialAd !=null && isLoaded) {
            interstitialAd.show();
            Log.d(numeTag,"Facebook Interstitial: is shown");
        } else {
            Log.d(numeTag,"Facebook Interstitial: show failed");
        }

    }
    public boolean isFacebookLoaded(){
        if(interstitialAd!=null && noFacebookError&& interstitialAd.isAdLoaded()){
            Log.d(numeTag,"Facebook Interstitial: isFacebookLoaded true");
            return true;
        }else {
            android.util.Log.d("TestButton", "isFacebookLoaded false;");
            return false;
        }
    }
    public void requestNewInterstitialFacebook() {
        interstitialAd.loadAd();

    }
}
