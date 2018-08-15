package ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import themejunky.com.interstitial.R;

public class ManagerInterstitialAds  implements ListenerContract.ListenerIntern {
    private static ManagerInterstitialAds instance;
    private FacebookInterstitialAds facebookInterstitialAdsInterstitial;
    private AdmobInterstitialAds admobInterstitialAds;
    private final String tagName;
    private final Context context;
    private ListenerContract.AdsInterstitialListener listener;
    private int next;
    private static List<String> addsFlowInterstitial = new ArrayList<>();
    private ListenerContract.ReloadInterstitial reloadedListener;
    private boolean somethingIsLoaded;
    private boolean isReloaded;
    private List<String> flowAds;
    private String action = "testAction";
    private AppnextAdsInterstitial appnextInterstitialAds;
    private int nrAdsManagers;
    private List<String> whatIsLoaded = new ArrayList<>();
    private ListenerContract.NoAdsLoaded noAdsLoadedListener;

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


    public void showInterstitialLoading(Context context, int timeLoadinMillisec, final String action){
        this.action = action;
        Log.d(tagName,"showInterstitialLoading");
        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder( context );
        LayoutInflater factory = LayoutInflater.from(context);
        View dialog = factory.inflate(R.layout.activity_loading_screen, null);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.imageTest);
        alertDialog.setView(dialog);
        final android.app.AlertDialog mDialog = alertDialog.create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ThreeBounce threeBounce = new ThreeBounce();
        threeBounce.setBounds(0, 0, 100, 100);
        threeBounce.setColor(Color.CYAN);
        imageView.setImageDrawable(threeBounce);
        threeBounce.start();
        mDialog.show();

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
    public void somethingReloaded(String whatIsLoaded) {
        isSomeAdLoaded(whatIsLoaded);
        Log.d(tagName,"somethingReloaded: " + whatIsLoaded);
        if(reloadedListener!=null){
            Log.d(tagName,"reloadedListener: nu este null");
            reloadedListener.reloadedInterstitial(whatIsLoaded);
        }else {
            Log.d(tagName,"reloadedListener: Este null");
        }
    }

    public void setReloadedListener(ListenerContract.ReloadInterstitial reloadedListener){
        this.reloadedListener = reloadedListener;
    }

    public void showInterstitial(List<String> flow) {
        if (flow != null && action != null) {
            addsFlowInterstitial = flow;
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
        if (next < addsFlowInterstitial.size()) {
            switch (addsFlowInterstitial.get(next)) {
                case "admob":
                    if (admobInterstitialAds!=null && admobInterstitialAds.isLoadedAdmob()) {
                        admobInterstitialAds.showInterstitialAdmob();
                    } else {
                        runAdds_Part2Interstitial();
                    }
                    break;
                case "facebook":
                    if (facebookInterstitialAdsInterstitial!=null &&facebookInterstitialAdsInterstitial.isFacebookLoaded()) {
                        facebookInterstitialAdsInterstitial.showInterstitialFacebook();
                    } else {
                        runAdds_Part2Interstitial();
                    }
                    break;
                case "appnext":
                    if (appnextInterstitialAds!=null &&appnextInterstitialAds.isLoadedAppNext()) {
                        appnextInterstitialAds.showAppNext();
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

   /* @Override
    public void reloadedInterstitial(String whatIsLoaded) {
        isSomeAdLoaded(whatIsLoaded);
        Log.d(tagName, "reloadedInterstitial - showInterstitial");
        nrAdsManagers++;
        if(whatIsLoaded.equals(flowAds.get(0))){
            showInterstitial(flowAds);
        }else if(nrAdsManagers==2) {
            showInterstitial(flowAds);
        }
    }*/
}
