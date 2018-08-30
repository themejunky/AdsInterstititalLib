/*
package ads.interstitialAds;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyUserMetadata;
import com.adcolony.sdk.AdColonyZone;
import com.appnext.ads.interstitial.InterstitialActivity;

import themejunky.module_adsmanager.ads.AdsListenerManager;

*/
/**
 * Created by Junky2 on 6/27/2018.
 *//*


public class AdColonyInterstitialAds {
    private static AdColonyInterstitialAds instance = null;
    private final String colonyAppID;
    private final String zoneId;
    private final Activity context;
    private final AdsListenerManager.ListenerLogs listenerLogs;
    private AdColonyInterstitialListener listener;
    private AdColonyAdOptions adOptions;
    private AdColonyInterstitial ads;
    private boolean isAdColonyReady=false;

    public AdColonyInterstitialAds(Activity context,String colonyAppID, String zoneId,AdsListenerManager.ListenerLogs listenerLogs){
        this.context = context;
        this.listenerLogs = listenerLogs;
        this.colonyAppID=colonyAppID;
        this.zoneId = zoneId;
        init();

    }

    public void init(){
        isAdColonyReady=false;

        AdColonyAppOptions app_options = new AdColonyAppOptions().setUserID("unique_user_id");
        listenerLogs.logs("AdColony Intersitial: Orinentation 1 : " +  app_options.getAppOrientation());
        app_options.setAppOrientation(AdColonyAppOptions.PORTRAIT);

        AdColony.configure(context,app_options, colonyAppID, zoneId);

        AdColonyUserMetadata metadata = new AdColonyUserMetadata();

        adOptions = new AdColonyAdOptions().setUserMetadata(metadata);

        listenerLogs.logs("AdColony Intersitial: Orinentation 2 : " +  app_options.getAppOrientation());
        listener = new AdColonyInterstitialListener() {
            public void onRequestFilled(AdColonyInterstitial ad) {
                ads = ad;
                isAdColonyReady = true;
                // Ad passed back in request filled callback, ad can now be shown
                listenerLogs.logs("AdColony Intersitial: onRequestFilled " + ad.getZoneID() );
            }
            @Override
            public void onOpened(AdColonyInterstitial ad) {
                // Ad opened, reset UI to reflect state change
                listenerLogs.logs("AdColony Intersitial: onOpened");

            }

            @Override
            public void onClosed(AdColonyInterstitial ad) {
                super.onClosed(ad);
                listenerLogs.logs("AdColony Intersitial: onClosed");
                listenerLogs.isClosedInterAds();
                isAdColonyReady=false;
               // AdColony.requestInterstitial(zoneId, listener);
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                super.onRequestNotFilled(zone);
                listenerLogs.logs("AdColony Intersitial: onRequestNotFilled");
            }

            @Override
            public void onAudioStarted(AdColonyInterstitial ad) {
                super.onAudioStarted(ad);
                listenerLogs.logs("AdColony Intersitial: onAudioStarted");
            }

            @Override
            public void onLeftApplication(AdColonyInterstitial ad) {
                super.onLeftApplication(ad);
                listenerLogs.logs("AdColony Intersitial: onLeftApplication");
            }

            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                // Request a new ad if ad is expiring
                listenerLogs.logs("AdColony Intersitial: onExpiring");
               // AdColony.requestInterstitial(zoneId, this, adOptions);
            }

            @Override
            public void onAudioStopped(AdColonyInterstitial ad) {
                super.onAudioStopped(ad);
                listenerLogs.logs("AdColony Intersitial: onExpiring");
            }
        };

        AdColony.requestInterstitial(zoneId, listener);



    }



    public void showAds (){
        if(ads!=null){
            ads.show();
        }
    }
    public boolean isAdColonyLoaded(){
        if(isAdColonyReady){
            listenerLogs.logs("AdColony Intersitial: isAdColonyLoaded true");
            return true;
        }else {
            listenerLogs.logs("AdColony Intersitial: isAdColonyLoaded false");
            return false;}
    }

    public void adColonyOnResume(){
        if (ads == null || ads.isExpired()) {
            // Optionally update location info in the ad options for each request:
            // LocationManager locationManager =
            //     (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            // Location location =
            //     locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // adOptions.setUserMetadata(adOptions.getUserMetadata().setUserLocation(location));
            AdColony.requestInterstitial(zoneId, listener, adOptions);
        }
    }

    public static synchronized AdColonyInterstitialAds getInstance(Activity context,String colonyAppID, String zoneId,AdsListenerManager.ListenerLogs listenerLogs){
        if(instance==null){
            return new AdColonyInterstitialAds(context,colonyAppID,zoneId,listenerLogs);
        }else {
            return instance;
        }
    }
}
*/
