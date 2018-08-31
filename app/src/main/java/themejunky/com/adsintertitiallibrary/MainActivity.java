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

public class MainActivity extends AppCompatActivity implements ListenerContract.AdsInterstitialListener, ListenerContract.NoAdsLoaded {

    public ManagerInterstitialAds managerInterstitialAds;
    private FacebookInterstitialAds facebookInterstitialAds1;
    private FacebookInterstitialAds facebookInterstitialAds2;

    private List<String> flow = Arrays.asList("facebook","admob","appnext");
    private boolean somethingIsLoaded;
    private boolean isReloaded;
    private int nrAdsManagers=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        managerInterstitialAds =new ManagerInterstitialAds();
        managerInterstitialAds.setTagName("infoTagName");
        //managerInterstitialAds.initAdmob("ca-app-pub-5322508131338449/2877444211",false,this);
        managerInterstitialAds.initAdmob("ca-app-pub-3940256099942544/8691691433",false,this);
        facebookInterstitialAds1 = managerInterstitialAds.initFacebook("223324501480181_4783806226412333",false,this);
        facebookInterstitialAds2 = managerInterstitialAds.initFacebook("223324501480181_478379819307980",false,this);
      //  managerInterstitialAds.initAppnext("aacbb73a-09b8-455d-b9d8-1d246d5a2cb44");
       // facebookInterstitialAds1.requestNewInterstitialFacebook();
       // facebookInterstitialAds2.requestNewInterstitialFacebook();
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
        managerInterstitialAds.showInterstitial(this,facebookInterstitialAds1,true,"intro","Loading Wallpaper...",flow);

    }


    @Override
    public void afterInterstitialIsClosed(String action) {
        switch (action){
            case "intro":
                somethingIsLoaded=false;
                startActivity(new Intent(this, Main2Activity.class));
                break;
            case "intro2":
                somethingIsLoaded=false;
                startActivity(new Intent(this, Main3Activity.class));
                break;

        }
    }


    @Override
    public void noAdsLoaded(String action) {
        Log.d("qwqwq","noAdsLoaded: "+ action);
        switch (action){
            case "intro":
                startActivity(new Intent(this, Main2Activity.class));
                break;

        }
    }

    public void onClick2(View view) {
       // facebookInterstitialAds2.showInterstitialFacebook();
        managerInterstitialAds.showInterstitial(this,facebookInterstitialAds2,true,"intro2","Applying...",flow);
    }
}
