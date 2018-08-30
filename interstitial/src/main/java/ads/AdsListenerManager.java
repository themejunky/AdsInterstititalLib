package ads;

/**
 * Created by Alin on 11/16/2017.
 */

public interface AdsListenerManager {


    interface ListenerLogs {
        void logs(String logs);
        void isClosedInterAds();
    }

    interface ListenerAds {
        void loadedNativeAds(String type);
        void loadedInterstitialAds();
        void afterInterstitialIsClosed(String action);
    }

    interface NativeListener {
        void nativeLoaded();

    }


}
