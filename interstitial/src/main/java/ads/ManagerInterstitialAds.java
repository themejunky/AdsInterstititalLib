package ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.ArrayList;
import java.util.List;


import themejunky.com.interstitial.R;

public class ManagerInterstitialAds  implements ListenerContract.ListenerIntern {
    private static ManagerInterstitialAds instance;
    private FacebookInterstitialAds facebookInterstitialAdsInterstitial;
    private AdmobInterstitialAds admobInterstitialAds;
    private final String tagName;
    private final Context context;
    private ListenerContract.AdsInterstitialListener listener;
    private int next;
    private String action = "testAction";
    private AppnextAdsInterstitial appnextInterstitialAds;
    private List<String> whatIsLoaded = new ArrayList<>();
    private ListenerContract.NoAdsLoaded noAdsLoadedListener;
    private int nrAdsManagers;
    private List<String> flow = new ArrayList<>();
    private android.app.AlertDialog mDialog;
    private boolean addShowed;
    private int relaoded;

    public static synchronized ManagerInterstitialAds getInstance(Context context, String tagName) {
        if (instance == null) {
            return new ManagerInterstitialAds(context, tagName);
        } else {
            return instance;
        }
    }

    public ManagerInterstitialAds(Context context, String tagName) {
        this.context= context;
        this.tagName = tagName;
    }


    public void showInterstitialLoading(Context context,boolean isShowLoading ,int timeLoadinMillisec, final String action,String textLoading,List<String> flow){
        this.action = action;
        this.flow = flow;
        Log.d(tagName,"showInterstitialLoading");
        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder( context );
        LayoutInflater factory = LayoutInflater.from(context);
        View dialog = factory.inflate(R.layout.activity_loading_screen, null);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.imageTest);
        TextView textView =  dialog.findViewById(R.id.loadingText);
        textView.setText(textLoading);
        alertDialog.setView(dialog);
        mDialog  = alertDialog.create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ThreeBounce threeBounce = new ThreeBounce();
        threeBounce.setBounds(0, 0, 100, 100);
        threeBounce.setColor(Color.CYAN);
        imageView.setImageDrawable(threeBounce);
        threeBounce.start();
        if(isShowLoading){
            mDialog.show();
        }

        requestNewInterstitial();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                Log.d(tagName,"showInterstitialLoading: " + whatIsLoaded.size());
                    if(noAdsLoadedListener!=null && whatIsLoaded.size()<1){
                        noAdsLoadedListener.noAdsLoaded(action);
                    }

            }
        },timeLoadinMillisec);
    }

    public void initFacebook(String key){
        if(key!=null){
            Log.d(tagName,"initFacebook");
            facebookInterstitialAdsInterstitial = new FacebookInterstitialAds(context, tagName, key,this);
        }

    }

    public void initAdmob(String key){
        if(key!=null){
            Log.d(tagName,"initAdmob");
            admobInterstitialAds = new AdmobInterstitialAds(context, tagName, key,this);
        }
    }
    public void initAppnext(String key){
        if(key!=null){
            Log.d(tagName,"initAppnext");
            appnextInterstitialAds = new AppnextAdsInterstitial(context, tagName, key,this);
        }
    }



    public void setInterstitialAdsListener(ListenerContract.AdsInterstitialListener adsListener) {
        this.listener = adsListener;
    }

    public void setNoAdsLoadedListener(ListenerContract.NoAdsLoaded noAdsLoadedListener){
        this.noAdsLoadedListener = noAdsLoadedListener;
    }

    @Override
    public void isInterstitialClosed() {
        if (listener != null){
            listener.afterInterstitialIsClosed(action);
        }else {
            Log.d(tagName,"listener null");
        }
    }

    @Override
    public void somethingReloaded(final String whatIsLoaded) {
        isSomeAdLoaded(whatIsLoaded);
        Log.d(tagName,"somethingReloaded: " + whatIsLoaded);
        nrAdsManagers++;
        if(flow!=null){
            try {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(whatIsLoaded.equals(flow.get(0))){
                            Log.d(tagName,"somethingReloaded: 1");
                            showInterstitial();
                        }else {
                            Log.d(tagName,"somethingReloaded: 1.1");
                            relaoded++;
                            somethingReloaded(whatIsLoaded);
                        }
                    }
                },1500);
                if(relaoded==1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(whatIsLoaded.equals(flow.get(0))){
                                Log.d(tagName,"somethingReloaded: 2");
                                showInterstitial();
                            }else {
                                Log.d(tagName,"somethingReloaded: 2.1");
                                showInterstitial();
                            }
                        }
                    },1500);
                }

            }catch (Exception e){
                Log.d(tagName,"Exception: " + e.getMessage());
            }

        }else {
            Log.d(tagName,"Flow is NULL");
        }

    }

    /*@Override
    public void somethingReloaded(String whatIsLoaded) {
        isSomeAdLoaded(whatIsLoaded);

        Log.d(tagName,"somethingReloaded: " + whatIsLoaded);
        nrAdsManagers++;
        if(flow!=null){
            try {
                if(whatIsLoaded.equals(flow.get(0))){
                    Log.d(tagName,"somethingReloaded: if");
                    showInterstitial();
                }else {
                    Log.d(tagName,"somethingReloaded: else");
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(tagName,"somethingReloaded: else 2");
                            // showInterstitial();
                        }
                    },2000);

                }

            }catch (Exception e){
                Log.d(tagName,"Exception: " + e.getMessage());
            }

        }else {
            Log.d(tagName,"Flow is NULL");
        }

    }*/


    public void showInterstitial() {
        mDialog.dismiss();
        if (flow != null && action != null) {
            part1Interstitial();
        }

    }

    private void part1Interstitial() {
        next = -1;
        runAdds_Part2Interstitial();
    }

    public void requestNewInterstitial(){
        if(admobInterstitialAds!=null){
            admobInterstitialAds.requestNewInterstitialAdmob();
        }
        if( facebookInterstitialAdsInterstitial!=null){
            facebookInterstitialAdsInterstitial.requestNewInterstitialFacebook();
        }
        if( appnextInterstitialAds!=null){
            appnextInterstitialAds.requestNewInterstitialAppnext();
        }

    }

    public void runAdds_Part2Interstitial() {
        next++;
        if (next < flow.size()) {
            switch (flow.get(next)) {
                case "admob":
                    Log.d(tagName, "Flow Interstitial: ---Admob 1 ---");
                    if (admobInterstitialAds!=null && admobInterstitialAds.isLoadedAdmob()) {
                        Log.d(tagName, "Flow Interstitial: ---Admob 2 ---");
                        admobInterstitialAds.showInterstitialAdmob();
                        Log.d(tagName, "Flow Interstitial: ---Admob 3 ---");
                    } else {
                        runAdds_Part2Interstitial();
                    }
                    break;
                case "facebook":
                    Log.d(tagName, "Flow Interstitial: ---Facebook 1 ---");
                    if (facebookInterstitialAdsInterstitial!=null &&facebookInterstitialAdsInterstitial.isFacebookLoaded()) {
                        Log.d(tagName, "Flow Interstitial: ---Facebook 2 ---");
                        facebookInterstitialAdsInterstitial.showInterstitialFacebook();
                        Log.d(tagName, "Flow Interstitial: ---Facebook 3 ---");
                    } else {
                        runAdds_Part2Interstitial();
                    }
                    break;
                case "appnext":
                    Log.d(tagName, "Flow Interstitial: ---Appnext 1 ---");
                    if (appnextInterstitialAds!=null &&appnextInterstitialAds.isLoadedAppNext()) {
                        Log.d(tagName, "Flow Interstitial: ---Appnext 2 ---");
                        appnextInterstitialAds.showAppNext();
                        Log.d(tagName, "Flow Interstitial: ---Appnext 3 ---");
                    } else {
                        runAdds_Part2Interstitial();
                    }
                    break;
                default:
                    Log.d(tagName, "Flow Interstitial: ---Default---");
                    break;
            }
        }
    }



    public List<String> isSomeAdLoaded(String reloadedList) {
       whatIsLoaded = new ArrayList<>();
        whatIsLoaded.add(reloadedList);
        Log.d(tagName, "isSomeAdLoaded: " + reloadedList);
        return whatIsLoaded;
    }

}
