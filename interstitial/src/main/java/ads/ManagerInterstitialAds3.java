/*
package ads;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ads.interstitial.AdmobInterstitialAds;
import ads.interstitial.FacebookInterstitialAds;
import themejunky.com.interstitial.R;
import utill.StartProgressBar;


public class ManagerInterstitialAds implements ListenerContract.ListenerIntern {
    private static ManagerInterstitialAds instance;
    private FacebookInterstitialAds facebookInterstitialAdsInterstitial;
    private AdmobInterstitialAds admobInterstitialAds;
    private final String tagName;
    private final Context context;
    private ListenerContract.AdsInterstitialListener listener;
    private int next;
    private String action = "testAction";
 //   private AppnextAdsInterstitial appnextInterstitialAds;
    private List<String> whatIsLoaded = new ArrayList<>();
    private ListenerContract.NoAdsLoaded noAdsLoadedListener;
    private List<String> flow = new ArrayList<>();
    ;
    private int count;
    private int priority = 100;
    private String stringLoaded;
    private int nrShow = 0;
    private int delaymillis;
    private Dialog nDialog;

    public static synchronized ManagerInterstitialAds getInstance(Context context, String tagName) {
        if (instance == null) {
            return new ManagerInterstitialAds(context, tagName);
        } else {
            return instance;
        }
    }

    public ManagerInterstitialAds(Context context, String tagName) {
        this.context = context;
        this.tagName = tagName;
    }


    public void showInterstitialLoading(Context context, boolean isShowLoading, int timeLoadinMillisec, final String action, String textLoading, List<String> flow) {
        this.action = action;
        this.flow = flow;
        Log.d(tagName, "showInterstitialLoading");

        LayoutInflater factory = LayoutInflater.from(context);
        View customView = factory.inflate(R.layout.activity_loading_screen, null);
        nDialog = new Dialog(context, R.style.full_screen_dialog);
        nDialog.setContentView(customView);
        nDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        TextView textView = customView.findViewById(R.id.loadingText);
        ProgressBar progressBar = customView.findViewById(R.id.progressBar);
        ImageView imageView = customView.findViewById(R.id.imageView1);
        RelativeLayout view = customView.findViewById(R.id.containerLoading);

        textView.setText(textLoading);

        if (isShowLoading) {
            new StartProgressBar().startProgressBar(imageView, progressBar, view);
            nDialog.show();
        }

        requestNewInterstitial();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nDialog.dismiss();
                Log.d(tagName, "showInterstitialLoading: " + whatIsLoaded.size() + " whatIsLoaded " + whatIsLoaded);
                if (noAdsLoadedListener != null && whatIsLoaded.size() < 1) {
                    noAdsLoadedListener.noAdsLoaded(action);
                }

            }
        }, timeLoadinMillisec);
    }

    public void initFacebook(String key,Boolean reload) {
        if (key != null) {
            Log.d(tagName, "initFacebook");
            facebookInterstitialAdsInterstitial = new FacebookInterstitialAds(context, tagName, key, this,reload);
        }

    }

    public void initAdmob(String key,Boolean reload) {
        if (key != null) {
            Log.d(tagName, "initAdmob");
            admobInterstitialAds = new AdmobInterstitialAds(context, tagName, key, this,reload);
        }
    }

   */
/* public void initAppnext(String key) {
        if (key != null) {
            Log.d(tagName, "initAppnext");
            appnextInterstitialAds = new AppnextAdsInterstitial(context, tagName, key, this);
        }
    }*//*



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
        isSomeAdLoaded(whatIsLoaded);
        count++;
        Log.d("isSomeAdLoaded", "count " + count);
        if (count == 1) {
            Log.d("isSomeAdLoaded", "wait 3 sec");
            if (nrShow < 1) {
                delaymillis = 1000;
            } else {
                delaymillis = 3000;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(tagName, "showInterstitial " + isSomeAdLoaded(whatIsLoaded));
                    if (flow != null) {
                        if (flow.contains(isSomeAdLoaded(whatIsLoaded))) {
                            showInterstitial("" + isSomeAdLoaded(whatIsLoaded));
                            count = 0;
                        }
                    } else {
                        Log.d(tagName, "Flow is NULL");
                    }
                }
            }, delaymillis);
        }
    }

    public void showInterstitial(String theAd) {
        nrShow++;
        Log.d("sdfgsssssss", "showInterstitial: " + nrShow);
        //   mDialog.dismiss();
        if (flow != null) {
            Log.d(tagName, "theAd " + theAd);
            if (theAd.equals("admob")) {
                Log.d(tagName, "Flow Interstitial: ---Admob 1 ---");
                if (admobInterstitialAds != null && admobInterstitialAds.isLoadedAdmob()) {
                    Log.d(tagName, "Flow Interstitial: ---Admob 2 ---");
                    admobInterstitialAds.showInterstitialAdmob();
                    delayCloseDialog(nDialog);
                    Log.d(tagName, "Flow Interstitial: ---Admob 3 ---");
                } else {
                    Log.d(tagName, "Flow Interstitial: ---Admob 4 is null or not loaded");
                }
            } */
/*else if (theAd.equals("appnext")) {
                Log.d(tagName, "Flow Interstitial: ---Appnext 1 ---");
                if (appnextInterstitialAds != null && appnextInterstitialAds.isLoadedAppNext()) {
                    Log.d(tagName, "Flow Interstitial: ---Appnext 2 ---");
                    appnextInterstitialAds.showAppNext();
                    delayCloseDialog(nDialog);
                    Log.d(tagName, "Flow Interstitial: ---Appnext 3 ---");
                } else {
                    Log.d(tagName, "Flow Interstitial: ---Appnext 4 is null or not loaded");
                }
            } *//*
else if (theAd.equals("facebook")) {
                Log.d(tagName, "Flow Interstitial: ---Facebook 1 ---");
                if (facebookInterstitialAdsInterstitial != null && facebookInterstitialAdsInterstitial.isFacebookLoaded()) {
                    Log.d(tagName, "Flow Interstitial: ---Facebook 2 ---");
                    facebookInterstitialAdsInterstitial.showInterstitialFacebook();
                    delayCloseDialog(nDialog);
                    Log.d(tagName, "Flow Interstitial: ---Facebook 3 ---");
                } else {
                    Log.d(tagName, "Flow Interstitial: ---Facebook 4 is null or not loaded");
                }
            }
        }

    }

    public void delayCloseDialog(final Dialog nDialog) {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nDialog.dismiss();
            }
        }, 2000);
    }

    public void showInterstitialWithoutLoading(List<String>flow,String action) {
        this.action = action;
        this.flow = flow;
        if (flow != null && action != null) {
            part1Interstitial();
        }
    }

    private void part1Interstitial() {
        next = -1;
        runAdds_Part2Interstitial();
    }

    public void requestNewInterstitial() {
        whatIsLoaded.clear();
        Log.d(tagName, "----requestNewInterstitial----");
        if (admobInterstitialAds != null) {
            admobInterstitialAds.requestNewInterstitialAdmob();
        }
        if (facebookInterstitialAdsInterstitial != null) {
            facebookInterstitialAdsInterstitial.requestNewInterstitialFacebook();
        }
        */
/*if (appnextInterstitialAds != null) {
            appnextInterstitialAds.requestNewInterstitialAppnext();
        }*//*


    }

    public void runAdds_Part2Interstitial() {
        next++;
        if (next < flow.size()) {
            switch (flow.get(next)) {
                case "admob":
                    Log.d(tagName, "Flow Interstitial: ---Admob 1 ---");
                    if (admobInterstitialAds != null && admobInterstitialAds.isLoadedAdmob()) {
                        Log.d(tagName, "Flow Interstitial: ---Admob 2 ---");
                        admobInterstitialAds.showInterstitialAdmob();
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
                        Log.d(tagName, "Flow Interstitial: ---Facebook 3 ---");
                    } else {
                        runAdds_Part2Interstitial();
                    }
                    break;
                */
/*case "appnext":
                    Log.d(tagName, "Flow Interstitial: ---Appnext 1 ---");
                    if (appnextInterstitialAds != null && appnextInterstitialAds.isLoadedAppNext()) {
                        Log.d(tagName, "Flow Interstitial: ---Appnext 2 ---");
                        appnextInterstitialAds.showAppNext();
                        Log.d(tagName, "Flow Interstitial: ---Appnext 3 ---");
                    } else {
                        runAdds_Part2Interstitial();
                    }
                    break;*//*

                default:
                    Log.d(tagName, "Flow Interstitial: ---Default---");
                    break;
            }
        }
    }


    public String isSomeAdLoaded(String theAd) {
        if (!whatIsLoaded.contains(theAd)) {
            whatIsLoaded.add(theAd);
        } else {
            //do nothing, ad already loaded
        }

        for (int i = 0; i < whatIsLoaded.size(); i++) {
            try {
                if (priority != 0) {
                    if (whatIsLoaded.get(i).equals(flow.get(0))) {
                        //Log.d(tagName, "ahaha " + whatIsLoaded.get(i) + " is pos 0");
                        stringLoaded = whatIsLoaded.get(i);
                        priority = 0;
                    }

                    if (priority != 1) {
                        if (whatIsLoaded.get(i).equals(flow.get(1))) {
                            //Log.d(tagName, "ahaha " + whatIsLoaded.get(i) + " is pos 1");
                            stringLoaded = whatIsLoaded.get(i);
                            priority = 1;
                        }

                        if (priority != 2) {
                            if (whatIsLoaded.get(i).equals(flow.get(2))) {
                                //Log.d(tagName, "ahaha " + whatIsLoaded.get(i) + " is pos 2");
                                stringLoaded = whatIsLoaded.get(i);
                                priority = 2;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.d(tagName, "error: " + e.getMessage());
            }

        }

        Log.d(tagName, "whatIsLoaded" + whatIsLoaded + " flow " + flow + " higher priority ad is " + stringLoaded);
        return stringLoaded;
    }

    public boolean isSomethingLoad(){
        if(whatIsLoaded.size()>0){
            return true;
        }else {
            return false;
        }
    }


}
*/
