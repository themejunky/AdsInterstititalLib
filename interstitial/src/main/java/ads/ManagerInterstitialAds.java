package ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ads.interstitial.AdmobInterstitialAds;
import ads.interstitial.FacebookInterstitialAds;
import utill.LoadingProgressBarActivity;


public class ManagerInterstitialAds extends AppCompatActivity implements ListenerContract.ListenerIntern {
    public static ManagerInterstitialAds instance;
    public  FacebookInterstitialAds facebookInterstitialAdsInterstitial;
    private static AdmobInterstitialAds admobInterstitialAds;
    private String tagName = "infoTagName";
    private ListenerContract.AdsInterstitialListener listener;
    private int next;
    private String action = "testAction";
    public static List<String> whatIsLoadedList = new ArrayList<>();
    public static ListenerContract.NoAdsLoaded noAdsLoadedListener;
    private static List<String> flow = new ArrayList<>();

    private boolean reloadAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

           if(getIntent().getBooleanExtra("fromLoading",false)){
               Log.d(tagName,"onCreate 1");
               part1Interstitial();
               Log.d(tagName,"onCreate 1.1");
           }

    }



    public void setTagName(String tagName){
        this.tagName = tagName;
    }


    public void showInterstitial (Activity activity, boolean isShowLoading, final String action, String textLoading, List<String> flow){
        this.action = action;
        this.flow = flow;

        if (isShowLoading) {
            Intent intent  = new Intent(activity, LoadingProgressBarActivity.class);
            intent.putExtra("textLoading",textLoading);
            intent.putExtra("textAction",action);
            activity.startActivity(intent);
            requestNewInterstitial();
        }else {
            if(whatIsLoadedList.size()>0){
                part1Interstitial();
            }else {
                noAdsLoadedListener.noAdsLoaded(action);
            }

        }

    }


    public void initFacebook(String key,boolean reloadAd,Context context) {
        if (key != null) {
            Log.d(tagName, "initFacebook");
            facebookInterstitialAdsInterstitial = new FacebookInterstitialAds(context, tagName, key, this,reloadAd);
        }

    }

    public void initAdmob(String key,boolean reloadAd,Context context) {
        if (key != null) {
            Log.d(tagName, "initAdmob");
            admobInterstitialAds = new AdmobInterstitialAds(context, tagName, key, this,reloadAd);
        }
    }

   /* public void initAppnext(String key) {
        if (key != null) {
            Log.d(tagName, "initAppnext");
            appnextInterstitialAds = new AppnextAdsInterstitial(context, tagName, key, this);
        }
    }*/


    public void setInterstitialAdsListener(ListenerContract.AdsInterstitialListener adsListener) {
        this.listener = adsListener;
    }

    public void setNoAdsLoadedListener(ListenerContract.NoAdsLoaded noAdsLoadedListener) {
        this.noAdsLoadedListener = noAdsLoadedListener;
    }

    @Override
    public void isInterstitialClosed(String name) {
        Log.d(tagName, "isInterstitialClosed 1: " + name);
        if (listener != null) {
            Log.d(tagName, "isInterstitialClosed 2: " + " " + listener + " " + name);
            listener.afterInterstitialIsClosed(action);
        } else {
            Log.d(tagName, "listener null");
        }
    }

    @Override
    public void somethingReloaded(final String whatIsLoaded) {
        whatIsLoadedList.add(whatIsLoaded);
    }

    public void part1Interstitial() {
        next = -1;
        Log.d(tagName,"part1Interstitial 1");
        runAdds_Part2Interstitial();
        Log.d(tagName,"part1Interstitial 2 ");

    }

    public void requestNewInterstitial() {
        whatIsLoadedList.clear();
        if (admobInterstitialAds != null) {
            admobInterstitialAds.requestNewInterstitialAdmob();
        }
        if (facebookInterstitialAdsInterstitial != null) {
            facebookInterstitialAdsInterstitial.requestNewInterstitialFacebook();
        }
        /*if (appnextInterstitialAds != null) {
            appnextInterstitialAds.requestNewInterstitialAppnext();
        }*/
    }


    public void runAdds_Part2Interstitial() {
        Log.d(tagName,"runAdds_Part2Interstitial:" + flow.size());
        next++;
        if (next < flow.size()) {
            switch (flow.get(next)) {
                case "admob":
                    Log.d(tagName, "Flow Interstitial: ---Admob 1 ---");
                    if (admobInterstitialAds != null && admobInterstitialAds.isLoadedAdmob()) {
                        Log.d(tagName, "Flow Interstitial: ---Admob 2 ---");
                        admobInterstitialAds.showInterstitialAdmob();
                        finish();
                        Log.d(tagName, "Flow Interstitial: ---Admob 3 ---");
                    } else {
                        runAdds_Part2Interstitial();
                    }
                    break;
                case "facebook":
                    Log.d(tagName, "Flow Interstitial: ---Facebook 1 ---");
                    if (facebookInterstitialAdsInterstitial != null && facebookInterstitialAdsInterstitial.isFacebookLoaded()) {
                        Log.d(tagName, "Flow Interstitial: ---Facebook 2 ---");
                        facebookInterstitialAdsInterstitial.showInterstitialFacebook();
                        finish();
                        Log.d(tagName, "Flow Interstitial: ---Facebook 3 ---");
                    } else {
                        runAdds_Part2Interstitial();
                    }
                    break;
                /*case "appnext":
                    Log.d(tagName, "Flow Interstitial: ---Appnext 1 ---");
                    if (appnextInterstitialAds != null && appnextInterstitialAds.isLoadedAppNext()) {
                        Log.d(tagName, "Flow Interstitial: ---Appnext 2 ---");
                        appnextInterstitialAds.showAppNext();
                        Log.d(tagName, "Flow Interstitial: ---Appnext 3 ---");
                    } else {
                        runAdds_Part2Interstitial();
                    }
                    break;*/
                default:
                    Log.d(tagName, "Flow Interstitial: ---Default---");
                    break;
            }
        }
    }

}
