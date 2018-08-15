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
public class MainActivity extends AppCompatActivity implements ListenerContract.ReloadInterstitial,ListenerContract.AdsInterstitialListener, ListenerContract.NoAdsLoaded {

    public ManagerInterstitialAds managerInterstitialAds;

    private List<String> flow = Arrays.asList("facebook","facebook","appnext","admob");
    private boolean somethingIsLoaded;
    private boolean isReloaded;
    private int nrAdsManagers=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        managerInterstitialAds = ManagerInterstitialAds.getInstance(this,"aesfasdfasd");
        managerInterstitialAds.initAdmob("ca-app-pub-5322508131338449/28774442111");
        managerInterstitialAds.initAppnext("aacbb73a-09b8-455d-b9d8-1d246d5a2cb44");
        managerInterstitialAds.setInterstitialAdsListener(this);
        managerInterstitialAds.setReloadedListener(this);
        managerInterstitialAds.setNoAdsLoadedListener(this);

    }

    public void onClick(View view) {
     //  managerInterstitialAds.requestNewInterstitial(this,flow,"intro");
        managerInterstitialAds.showInterstitialLoading(this,5000,"intro");
    }

  /*  @Override
    public void reloadedInterstitial() {
        if (!somethingIsLoaded) {
            managerInterstitialAds.showInterstitial(flow,"intro");
            somethingIsLoaded = true;
            isReloaded=true;
        }
    }*/

    @Override
    public void afterInterstitialIsClosed(String action) {
        switch (action){
            case "intro":
                somethingIsLoaded=false;
                startActivity(new Intent(this, Main2Activity.class));

        }
    }


    @Override
    public void reloadedInterstitial(String whatIsLoaded) {
        nrAdsManagers++;
        if(whatIsLoaded.equals(flow.get(0))){
            managerInterstitialAds.showInterstitial(flow);
        }else if(nrAdsManagers==2) {
            managerInterstitialAds.showInterstitial(flow);
        }

    }

    @Override
    public void noAdsLoaded(String action) {
        Log.d("aesfasdfasd","noAdsLoaded: "+ action);
        switch (action){
            case "intro":
                startActivity(new Intent(this, Main2Activity.class));
                break;

        }
    }
}
