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
import ads.interstitial.AppnextAdsInterstitial;
import ads.interstitial.FacebookInterstitialAds;
import utill.LoadingProgressBarActivity;


public class ManagerInterstitialAds extends AppCompatActivity implements ListenerContract.ListenerIntern {
    public static AdmobInterstitialAds admobInterstitialAds;
    public static AppnextAdsInterstitial appnextAdsInterstitial;
    private String tagName = "infoTagName";
    private ListenerContract.AdsInterstitialListener listener;
    private int next;
    private static String action = "testAction";
    public static List<String> whatIsLoadedList = new ArrayList<>();
    public static ListenerContract.NoAdsLoaded noAdsLoadedListener;
    private static List<String> flow = new ArrayList<>();
    private static FacebookInterstitialAds facebookInterstitialAds;
    private String textLoading;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("fromLoading", false)) {
            Log.d(tagName, "onCreate 1");
            finish();
            part1Interstitial();
            Log.d(tagName, "onCreate 1.1");
        } else {
            Log.d(tagName, "onCreate 2");
        }

    }


    public void setTagName(String tagName) {
        this.tagName = tagName;
    }


    public void showInterstitial(Activity activity, FacebookInterstitialAds facebookInterstitialAds, boolean isShowLoading, final String action, String textLoading, List<String> flow) {
        this.action = action;
        this.activity = activity;
        this.flow = flow;
        this.facebookInterstitialAds = facebookInterstitialAds;
        this.textLoading = textLoading;
        Log.d(tagName, "showInterstitial " + facebookInterstitialAds);
        if (isShowLoading) {
            Intent intent = new Intent(activity, LoadingProgressBarActivity.class);
            intent.putExtra("textLoading", textLoading);
            intent.putExtra("textAction", action);
            activity.startActivity(intent);
            requestNewInterstitial(facebookInterstitialAds);
        } else {
            Log.d(tagName, "showInterstitial " + whatIsLoadedList.size()+" "+whatIsLoadedList);
            if (whatIsLoadedList.size() > 0) {
                part1Interstitial();
            } else {
                noAdsLoadedListener.noAdsLoaded(action);
            }

        }
    }

    public FacebookInterstitialAds initFacebook(String key, boolean reloadAd, Context context) {
        return new FacebookInterstitialAds(context, tagName, key, this, reloadAd);
    }


    public void initAdmob(String key, boolean reloadAd, Context context) {
        if (key != null) {
            Log.d(tagName, "initAdmob");
            admobInterstitialAds = new AdmobInterstitialAds(context, tagName, key, this, reloadAd);
        }
    }

    public void initAppnext(String key, boolean reloadAd, Context context) {
        if (key != null) {
            Log.d(tagName, "initAppnext");
            appnextAdsInterstitial = new AppnextAdsInterstitial(context, tagName, key, this, reloadAd);
        }
    }


    public void setInterstitialAdsListener(ListenerContract.AdsInterstitialListener adsListener) {
        this.listener = adsListener;
    }

    public void setNoAdsLoadedListener(ListenerContract.NoAdsLoaded noAdsLoadedListener) {
        this.noAdsLoadedListener = noAdsLoadedListener;
    }

    @Override
    public void isInterstitialClosed() {

        if (listener != null) {
            listener.afterInterstitialIsClosed(action);
        } else {
            Log.d(tagName, "listener null");
        }
    }

    @Override
    public void somethingReloaded(final String whatIsLoaded) {
        Log.d(tagName, "somethingReloaded  " + whatIsLoaded);
        whatIsLoadedList.add(whatIsLoaded);
    }

    public void part1Interstitial() {
        next = -1;
        Log.d(tagName, "part1Interstitial 1");
        runAdds_Part2Interstitial();
        Log.d(tagName, "part1Interstitial 2 ");

    }


    public void requestNewInterstitial(FacebookInterstitialAds facebookInterstitialAds) {
        Log.d(tagName, "whatIsLoadedList.size(): " + whatIsLoadedList.size());
        if (admobInterstitialAds != null) {
            admobInterstitialAds.requestNewInterstitialAdmob();
        }
        if (facebookInterstitialAds != null) {
            Log.d(tagName, "requestNewInterstitial:" + facebookInterstitialAds);
            facebookInterstitialAds.requestNewInterstitialFacebook();
        }
        if (appnextAdsInterstitial != null) {
            appnextAdsInterstitial.requestNewInterstitialAppnext();
        }
    }


    public void runAdds_Part2Interstitial() {
        Log.d(tagName, "runAdds_Part2Interstitial:" + flow.size());
        next++;
        if (next < flow.size()) {
            switch (flow.get(next)) {
                case "admob":
                    Log.d(tagName, "Flow Interstitial: ---Admob 1 --- " + admobInterstitialAds);
                    if (admobInterstitialAds != null && admobInterstitialAds.isLoadedAdmob()) {
                        Log.d(tagName, "Flow Interstitial: ---Admob 2 ---");
                        admobInterstitialAds.showInterstitialAdmob();
                        finish();
                        Log.d(tagName, "Flow Interstitial: ---Admob 3 ---");
                        finish();
                        if (whatIsLoadedList.contains("admob")) {
                            whatIsLoadedList.remove("admob");
                        }
                    } else {
                        Log.d(tagName, "Flow Interstitial: ---Admob 4 ---");
                        runAdds_Part2Interstitial();
                    }
                    break;
                case "facebook":
                    Log.d(tagName, "Flow Interstitial: ---Facebook 1 ---");
                    if (facebookInterstitialAds != null && facebookInterstitialAds.isFacebookLoaded()) {
                        Log.d(tagName, "Flow Interstitial: ---Facebook 2 ---");
                        facebookInterstitialAds.showInterstitialFacebook();
                        if (facebookInterstitialAds.isFaceookError()) {
                            Log.d(tagName, "Flow Interstitial: ---Facebook 2.1- facebook erorrs ---");
                            runAdds_Part2Interstitial();
                        }
                        finish();
                        if (whatIsLoadedList.contains("facebook")) {
                            whatIsLoadedList.remove("facebook");
                        }
                        Log.d(tagName, "Flow Interstitial: ---Facebook 3 ---");
                    } else {
                        Log.d(tagName, "Flow Interstitial: ---Facebook 4 ---");
                        runAdds_Part2Interstitial();
                    }
                    break;
                case "appnext":
                    Log.d(tagName, "Flow Interstitial: ---Appnext 1 ---");
                    if (appnextAdsInterstitial != null && appnextAdsInterstitial.isLoadedAppNext()) {
                        Log.d(tagName, "Flow Interstitial: ---Appnext 2 ---");
                        appnextAdsInterstitial.showAppNext();
                        if (whatIsLoadedList.contains("appnext")) {
                            whatIsLoadedList.remove("appnext");
                        }
                        Log.d(tagName, "Flow Interstitial: ---Appnext 3 ---");
                    } else {
                        Log.d(tagName, "Flow Interstitial: ---Appnext 4 ---");
                        runAdds_Part2Interstitial();
                    }
                    break;
                default:
                    noAdsLoadedListener.noAdsLoaded(action);
                    finish();
                    Log.d(tagName, "Flow Interstitial: ---Default---" + action);
                    break;
            }
        } else {
            Log.d(tagName, "Flow Interstitial: ---Default--Else-" + action);
            noAdsLoadedListener.noAdsLoaded(action);
            finish();
        }
    }

}
