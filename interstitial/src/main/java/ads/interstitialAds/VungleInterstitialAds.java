/*
package ads.interstitialAds;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vungle.publisher.VungleAdEventListener;
import com.vungle.publisher.VungleInitListener;
import com.vungle.publisher.VunglePub;

import java.util.Arrays;
import java.util.List;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.managers.ManagerBase;

*/
/**
 * Created by Junky2 on 5/24/2018.
 *//*


public class VungleInterstitialAds extends ManagerBase {
    private final String app_id;
    private final String[] placement_collection;
    private AdsListenerManager.ListenerLogs listenerLogs;
    private static VungleInterstitialAds mInstance = null;
    private VunglePub vunglePub = VunglePub.getInstance();
    private VungleAdEventListener vungleDefaultListener;

    public VungleInterstitialAds(Context context, String app_id, String[] placement_collection, AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
        this.app_id = app_id;
        this.placement_collection = placement_collection;
        initVungleInterstitial(context, app_id, placement_collection);



    }


    public void initVungleInterstitial(Context context, String app_id, final String[] placement_collection) {
        if (!vunglePub.isInitialized()) {
            vunglePub.init(context, app_id, placement_collection, new VungleInitListener() {
                @Override
                public void onSuccess() {
                    listenerLogs.logs("Vungle Inter: onSuccess");
                    vunglePub.clearAndSetEventListeners(vungleDefaultListener);
                    listenerLogs.logs("Vungle Inter: isAdPlayable: "+vunglePub.isAdPlayable(placement_collection[0]));

                }

                @Override
                public void onFailure(Throwable throwable) {
                    listenerLogs.logs("Vungle Inter: onFailure: " + throwable.getMessage());
                }
            });


        }

        vungleDefaultListener = new VungleAdEventListener() {
            @Override
            public void onAdStart(String s) {
                listenerLogs.logs("Vungle Inter: is onAdStart");
            }

            @Override
            public void onUnableToPlayAd(@NonNull String s, String s1) {
                listenerLogs.logs("Vungle Inter: onUnableToPlayAd: " + s1);
            }

            @Override
            public void onAdAvailabilityUpdate(@NonNull String s, boolean b) {
                listenerLogs.logs("Vungle Inter: onAdAvailabilityUpdate: " + b);

            }

            @Override
            public void onAdEnd(String s, boolean b, boolean b1) {
                listenerLogs.logs("Vungle Inter: is onAdEnd");
                listenerLogs.isClosedInterAds();
            }

        };

    }
*/
/*
    public void loadVungle() {
        if (placementId != null) {
            vunglePub.loadAd(placementId, new () {
                @Override
                public void onAdLoad(String s) {
                    listenerLogs.logs("Vungle Inter: is Loaded");
                    listenerAds.loadedInterstitialAds();
                    isVunleLoaded = true;
                }

                @Override
                public void onError(String s, Throwable throwable) {
                    listenerLogs.logs("Vungle Inter: onError: " + throwable.getMessage());
                }
            });
        }else {
            listenerLogs.logs("Vungle Inter: is app_id null");
        }
    *//*



    public void showVungleAds() {

        if (vunglePub.isAdPlayable(placement_collection[0])) {
            // Play a Placement ad with Placement ID, you can pass AdConfig to customize your ad
            vunglePub.playAd(placement_collection[0], null);
        }
    }

    public boolean isVungleLoaded() {
        if (vunglePub.isAdPlayable(placement_collection[0])) {
            listenerLogs.logs("Vungle Inter: isAdPlayable true");
            return true;
        } else {
            listenerLogs.logs("Vungle Inter: isAdPlayable false");
            return false;
        }
    }

    public synchronized static VungleInterstitialAds getmInstance(Context context, String app_id, String[] placement_collection, AdsListenerManager.ListenerLogs listenerLogs) {
        if (mInstance == null)
            mInstance = new VungleInterstitialAds(context, app_id, placement_collection, listenerLogs);
        return mInstance;

    }
}
*/
