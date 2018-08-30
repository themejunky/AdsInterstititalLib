package themejunky.com.adsintertitiallibrary;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import ads.Action;
import ads.AdsListenerManager;
import ads.ModuleAdsManager;


public class MainActivity extends AppCompatActivity implements AdsListenerManager.ListenerAds, View.OnClickListener {

    private List<String> flowAds = Arrays.asList("admob","facebook","appnext");
    private ModuleAdsManager mModuleAdsManager;
    private Button apply, rate, getMore;
    private View viewButtons;
    private ImageView splash;
    private LinearLayout layoutButtons;
    private byte nrFailedLoad = 0;
    private RelativeLayout containerFacebook, containerAdmob, containerAppnext;
    private boolean isLoaded;
    final private String APP_ID = "app185a7e71e1714831a49ec7";
    final private String ZONE_ID = "vz06e8c32a037749699e7050";
    final String app_id = "5916309cb46f6b5a3e00009c";
    final String DEFAULT_PLACEMENT_ID = "DEFAULT32590";
    private final String[] placement_list = { DEFAULT_PLACEMENT_ID, "TESTREW28799", "TESTINT07107" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placement_list[0]="DEFAULT32590";

        initView();
        Log.d("TestButton","onCreate");

        containerAdmob = findViewById(R.id.containerAdmob);
        containerAppnext = findViewById(R.id.containerAppnext);
        containerFacebook = findViewById(R.id.containerFacebook);

        mModuleAdsManager = new ModuleAdsManager();
        mModuleAdsManager.setListenerAds(this);
        mModuleAdsManager.setLogName("InfoAds");
        mModuleAdsManager.initManagers(this, true);
        //mModuleAdsManager.getManagerInterstitial().initInterstitialChartboost(this,"5af56f18e113780b0e5a1360", "46cfc662d3d840bf07db9f500244dc7820453682"); //test

        //mModuleAdsManager.getManagerNative().initNativeAdmob("ca-app-pub-8562466601970101/9984599253",false);
        //mModuleAdsManager.getManagerNative().iniNativeAppnext("66f95906-de1e-4643-b953-b8bd30524882",true);
        //mModuleAdsManager.getManagerNative().iniNativeFacebook("",true);
        //mModuleAdsManager.getManagerNative().showAds(flowAds,containerAdmob);

        mModuleAdsManager.getManagerInterstitial().initInterstitialFacebook("2064441373794453_2064443300460927");
        mModuleAdsManager.getManagerInterstitial().initInterstitialAdmob("ca-app-pub-5322508131338449/6935033151");
        //mModuleAdsManager.getManagerInterstitial().initInterstitialAppnext("aacbb73a-09b8-455d-b9d8-1d246d5a2cb4");
        //mModuleAdsManager.getManagerInterstitial().initInterstitialAdColony(APP_ID,ZONE_ID);.
        //mModuleAdsManager.getManagerInterstitial().initInterstitialAdColony("app1f87c72549f94ad9bb","vzf857cf81285d4051bc");
        //mModuleAdsManager.getManagerInterstitial().initInterstitialVungle(app_id,placement_list);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("TestButton","---------start 1---------------");
                // mModuleAdsManager.getManagerNative().showAds(flowAds,((RelativeLayout) findViewById(R.id.containerAdmob)));
                findViewById(R.id.applyid).setEnabled(true);

            }
        },5000);



    }

    public void initView() {
        apply = (Button) findViewById(R.id.applyid);


        Log.d("TestLogs2", "onCreate");

        apply.setOnClickListener(this);

    }


    @Override
    public void afterInterstitialIsClosed(String action) {
        Log.d("TestLogs", "afterInterstitialIsClosed");
        switch (action) {
            case Action.APPLY:
                startActivity(new Intent(this, ApplyActivity.class));
                break;
            case "rate":
                Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show();
                break;
            case "more":
                Toast.makeText(this, "GetMore", Toast.LENGTH_SHORT).show();
                break;
            case "back":
                finish();
                break;

        }

    }

    @Override
    public void loadedInterstitialAds() {
        isLoaded = true;
    }


    @Override
    public void loadedNativeAds(String type) {
        Log.d("TestButton","loadedNativeAds");
    /*    if (type.equals("admob") && flowAds.size() > 0) {
            moduleAdsManager.setNativeFlowAndShowAds(flowAds, containerFacebook, containerAdmob,containerAppnext);

        }else if(type.equals("appnext") && flowAds.size() > 0){
            moduleAdsManager.setNativeFlowAndShowAds(flowAds, containerFacebook, containerAdmob,containerAppnext);
        }*/
    }


    @Override
    public void onClick(View v) {
        viewButtons = v;
        switch (viewButtons.getId()) {
            case R.id.applyid:
                if(mModuleAdsManager.getManagerInterstitial().isSomeAdLoaded()){
                    mModuleAdsManager.getManagerInterstitial().showInterstitial(flowAds, Action.APPLY);
                }else{
                    startActivity(new Intent(this, ApplyActivity.class));
                }

              /*  mModuleAdsManager.getManagerInterstitial().reLoadedInterstitial();

                if (mModuleAdsManager.getManagerInterstitial().isSomeAdLoaded()) {
                    Log.d("TestButton", "1");
                    mModuleAdsManager.getManagerInterstitial().showInterstitial(flowAds, Action.APPLY);
                    Log.d("TestButton", "2");
                } else {
                    Log.d("TestButton", "3");
                    Toast.makeText(this, "Apply", Toast.LENGTH_SHORT).show();
                }*/
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //mModuleAdsManager.getManagerInterstitial().showInterstitial(flowAds, Action.BACK);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mModuleAdsManager.getManagerNative().nContainer.removeAllViews();
        Log.d("TestButton","onDestroy");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TestButton","onPause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TestButton","onResume");
    }
}
