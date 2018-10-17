package themejunky.com.adsintertitiallibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import ads.ListenerContract;
import ads.ManagerInterstitialAds;
import ads.interstitial.FacebookInterstitialAds;
import ads.interstitial.SmartAd;
import ads.interstitial.SmartAdInterstitial;
import utill.LoadingProgressBarFacebook;

public class MainActivity extends AppCompatActivity implements ListenerContract.AdsInterstitialListener, ListenerContract.NoAdsLoaded, SmartAdInterstitial.OnSmartAdInterstitialListener {

    private ManagerInterstitialAds managerInterstitialAds;
    private FacebookInterstitialAds facebookInterstitialAds1;
    private FacebookInterstitialAds facebookInterstitialAds2;

    private List<String> flow = Arrays.asList("admob", "appnext");
    private boolean somethingIsLoaded;
    private boolean isReloaded;
    private int nrAdsManagers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        managerInterstitialAds = new ManagerInterstitialAds();
        managerInterstitialAds.setTagName("InfoAds");

        //managerInterstitialAds.initAdmob("ca-app-pub-5322508131338449/2877444211",true,this);
        managerInterstitialAds.initAdmob("ca-app-pub-3940256099942544/8691691433",false,this);

        //facebookInterstitialAds1 = managerInterstitialAds.initFacebook("223324501480181_4783806226412333",false,this);
        //facebookInterstitialAds1 = managerInterstitialAds.initFacebook("384109022042526_523676831419077",false,this);
        //facebookInterstitialAds2 = managerInterstitialAds.initFacebook("223324501480181_478379819307980",true,this);
        //facebookInterstitialAds1.requestNewInterstitialFacebook();
        // facebookInterstitialAds2.requestNewInterstitialFacebook();

        managerInterstitialAds.initAppnext("aacbb73a-09b8-455d-b9d8-1d246d5a2cb4", false, this);
        //managerInterstitialAds.initAppnext("729ceb30-a78a-492f-950b-deec4d2fca0f", false, this);
        //managerInterstitialAds.initAppnext("8ce1a263-7a74-42b1-b209-80276c0fe971", false, this);

        managerInterstitialAds.setInterstitialAdsListener(this);
        managerInterstitialAds.setNoAdsLoadedListener(this);
        // managerInterstitialAds.setFlow(flow);


    }

    public void onClick(View view) {
        //  managerInterstitialAds.requestNewInterstitial(this,flow,"intro");
        //  managerInterstitialAds.showInterstitialLoading(this,false,5000,"intro","Loading Wallpaper...",flow);
        // managerInterstitialAds.runAdds_Part2Interstitial();

        //managerInterstitialAds.facebookInterstitialAdsInterstitial.showInterstitialFacebook();
        //managerInterstitialAds.showFacebook(facebookInterstitialAds1);
        //managerInterstitialAds.showInterstitial(this,facebookInterstitialAds2,false,"intro","Loading Wallpaper...",flow);


        SmartAd.addTestDevice(SmartAd.AD_TYPE_GOOGLE, "2184F858FFCDF534E26419F85B421D1F");
        SmartAd.addTestDevice(SmartAd.AD_TYPE_FACEBOOK, "77f147bc-a6b9-4d32-a464-3e77c4c902ef");

        SmartAdInterstitial.showAd(MainActivity.this, null, "947881942088350_947883058754905", true, SmartAd.PLACEMENT_INTRO, "TEXT");
    }

    public void onClick2(View view) {
        // facebookInterstitialAds2.showInterstitialFacebook();
        managerInterstitialAds.showInterstitial(this, facebookInterstitialAds2, true, "intro2", "Applying...", flow);
    }

    @Override
    public void afterInterstitialIsClosed(String action) {
        switch (action) {
            case "intro":
                somethingIsLoaded = false;
                startActivity(new Intent(this, Main2Activity.class));
                break;
            case "intro2":
                somethingIsLoaded = false;
                startActivity(new Intent(this, Main3Activity.class));
                break;

        }
    }


    @Override
    public void noAdsLoaded(String action) {
        Log.d("qwqwq", "noAdsLoaded: " + action+" ");
        switch (action) {
            case "intro":
                startActivity(new Intent(this, Main2Activity.class));
                break;

        }
    }


    public void onSmartAdInterstitialDone(int adType, int mPlacement) {
        Log.d("qwqwq", "onSmartAdInterstitialDone " + adType + " " + mPlacement);
        switch (mPlacement) {
            case SmartAd.PLACEMENT_INTRO:
                Log.d("InfoAds", "onSmartAdInterstitialDone PLACEMENT_INTRO");
                break;
        }
    }

    public void onSmartAdInterstitialFail(int adType, int mPlacement) {
        Log.d("qwqwq", "onSmartAdInterstitialFail " + adType + " " + mPlacement);
        switch (mPlacement) {
            case SmartAd.PLACEMENT_INTRO:
                Log.d("InfoAds", "onSmartAdInterstitialFail PLACEMENT_INTRO");
                break;
        }
    }


    public void onSmartAdInterstitialClose(int adType, int mPlacement) {
        //LoadingProgressBarFacebook.go.finish(); //to be used only if activity doesn't close
        Log.d("InfoAds", "onSmartAdInterstitialClose " + adType + " " + mPlacement);
    }
}
