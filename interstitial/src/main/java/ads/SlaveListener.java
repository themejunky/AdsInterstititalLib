package ads;

import ads.AdsListenerManager;

/**
 * Created by Junky2 on 4/19/2018.
 */

public  class SlaveListener implements AdsListenerManager.NativeListener,AdsListenerManager.ListenerLogs {
    @Override
    public void logs(String logs) {}

    @Override
    public void isClosedInterAds() {}

    @Override
    public void nativeLoaded() {}
}
