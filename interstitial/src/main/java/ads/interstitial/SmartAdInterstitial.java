package ads.interstitial;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import utill.LoadingProgressBarFacebook;

/**
 * Created by shock on 2017. 8. 30..
 */

public class SmartAdInterstitial implements com.facebook.ads.InterstitialAdListener {

    private OnSmartAdInterstitialListener             mListener;
    private Context                                   mContext;
    private boolean                                   mIsAutoStart;
    private boolean                                   mShowLoading;
    private String                                    mGoogleID;
    private String                                    mFacebookID;

    @SmartAd.SmartAdOrder
    private int                                       mAdOrder = SmartAd.AD_TYPE_RANDOM;
    private int                                       mPlacement;

    private com.google.android.gms.ads.InterstitialAd mGoogleAd;
    public static com.facebook.ads.InterstitialAd     mFacebookAd;
    private String                                    mTitle;

    public SmartAdInterstitial(Context context, @SmartAd.SmartAdOrder int adOrder,
                               String googleID, String facebookID, boolean isAutoStart, boolean showLoading, int placement, String title,
                               final OnSmartAdInterstitialListener callback)
    {
        if (callback != null) {
            mListener = callback;
        } else if (context instanceof OnSmartAdInterstitialListener) {
            mListener = (OnSmartAdInterstitialListener) context;
        }

        this.mContext = context;
        this.mAdOrder = (adOrder==SmartAd.AD_TYPE_RANDOM) ? SmartAd.randomAdOrder() : adOrder;
        this.mGoogleID = googleID;
        this.mFacebookID = facebookID;
        this.mIsAutoStart = isAutoStart;
        this.mShowLoading = showLoading;
        this.mPlacement = placement;
        this.mTitle = title;
        loadAd();
    }

    private void loadAd() {
        if (SmartAd.IsShowAd(this)) {
            switch (mAdOrder) {
                case SmartAd.AD_TYPE_GOOGLE  : loadGoogle();   break;
                case SmartAd.AD_TYPE_FACEBOOK: loadFacebook(); break;
            }
        } else {
            onDone(SmartAd.AD_TYPE_PASS);
            destroy();
        }
    }

    public void showLoadedAd(boolean bool) {
        //Log.d("testache","boolean " + bool);

        if (SmartAd.IsShowAd(this)) {
            //Log.d("testache","intra in SmartAd.IsShowAd");
            if ((mGoogleAd != null) && (mGoogleAd.isLoaded())) {
                if (!bool) {
                    mGoogleAd.show();
                }
            } else if ((mFacebookAd != null) && (mFacebookAd.isAdLoaded())) {
                //Log.d("testache","intra fb 1 " + bool+" "+mFacebookAd.isAdLoaded());
                if (!bool) {
                    //Log.d("testache","intra fb 2");
                    mFacebookAd.show();
                }
            }
        } else {
            destroy();
        }
    }

    public void destroy() {
        if (mGoogleAd!=null) {
            mGoogleAd.setAdListener(null);
            mGoogleAd = null;
        }
        if (mFacebookAd!=null) {
            mFacebookAd.setAdListener(null);
            mFacebookAd.destroy();
            mFacebookAd = null;
        }
        mListener = null;
    }

    static public SmartAdInterstitial showAdWidthCallback(Context context, @SmartAd.SmartAdOrder int adOrder,
                                                          String googleID, String facebookID, boolean isAutoStart, boolean showLoading, int placement, String title,
                                                          final OnSmartAdInterstitialListener callback)
    {
        SmartAdInterstitial ad = new SmartAdInterstitial(context, adOrder, googleID, facebookID, isAutoStart, showLoading, placement, title, callback);
        return ad;
    }

    static public SmartAdInterstitial showAdWidthCallback(Context context, String googleID, String facebookID, boolean showLoading, int placement, String title,
                                                          final OnSmartAdInterstitialListener callback)
    {
        return SmartAdInterstitial.showAdWidthCallback(context, SmartAd.AD_TYPE_RANDOM, googleID, facebookID, true, showLoading, placement, title, callback);
    }

    static public SmartAdInterstitial showAd(Context context, @SmartAd.SmartAdOrder int adOrder, String googleID,
                                             String facebookID, boolean isAutoStart, boolean showLoading, int placement, String title)
    {
        return SmartAdInterstitial.showAdWidthCallback(context, adOrder, googleID, facebookID, isAutoStart, showLoading, placement, title, null);
    }

    static public SmartAdInterstitial showAd(Context context, String googleID, String facebookID, boolean showLoading, int placement, String title) {
        return SmartAdInterstitial.showAd(context, SmartAd.AD_TYPE_RANDOM, googleID, facebookID, true, showLoading, placement, title);
    }

    private void onDone(@SmartAd.SmartAdResult int type) {
        if (mListener!=null) {
            mListener.onSmartAdInterstitialDone(type, mPlacement);
        }
    }

    private void onFail(@SmartAd.SmartAdResult int type) {
        if (mListener!=null) {
            mListener.onSmartAdInterstitialFail(type, mPlacement);
            destroy();
        }
    }

    // 구글 *****************************************************************************************

    private void loadGoogle() {
        if (mGoogleID != null) {
            //The ads from google are loaded using ManagerInsterstitialAds

            /*
            if (mShowLoading) {
                Log.d("testache","mShowLoading received "+mShowLoading);
                Intent intent = new Intent(mContext, LoadingProgressBarFacebook.class);
                intent.putExtra("textLoading", mTitle);
                mContext.startActivity(intent);
            }
            */
            mGoogleAd = new com.google.android.gms.ads.InterstitialAd(mContext);
            mGoogleAd.setAdUnitId(mGoogleID);
            mGoogleAd.setAdListener(mGoogleListener);
            mGoogleAd.loadAd(SmartAd.getGoogleAdRequest());
        } else {
            if ((mAdOrder == SmartAd.AD_TYPE_GOOGLE) && (mFacebookID != null)) loadFacebook();
            else onFail(SmartAd.AD_TYPE_GOOGLE);
        }
    }

    private com.google.android.gms.ads.AdListener mGoogleListener = new com.google.android.gms.ads.AdListener() {
        @Override
        public void onAdLoaded() {
            super.onAdLoaded();

            //if (mIsAutoStart) {
                //dismissProgressDialog
                Log.d("testache","google ad is loaded");
                showLoadedAd(mShowLoading);
            //}
        }

        @Override
        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
            Log.e("SmartAd", "SmartAdInterstitial : type = Google, error code = "+i);
            mGoogleAd = null;

            if ((mAdOrder == SmartAd.AD_TYPE_GOOGLE) && (mFacebookID != null)) loadFacebook();
            else onFail(SmartAd.AD_TYPE_GOOGLE);
        }

        @Override
        public void onAdClosed() {
            super.onAdClosed();
            if (mListener!=null) mListener.onSmartAdInterstitialClose(SmartAd.AD_TYPE_GOOGLE, mPlacement);
            destroy();
        }
    };

    // 페이스북 **************************************************************************************

    private void loadFacebook() {
        if (mFacebookID != null) {
            mFacebookAd = new com.facebook.ads.InterstitialAd(mContext, mFacebookID);
            mFacebookAd.setAdListener(this);
            mFacebookAd.loadAd();

            if (mShowLoading) {
                Log.d("testache","mShowLoading received "+mShowLoading);
                Intent intent = new Intent(mContext, LoadingProgressBarFacebook.class);
                intent.putExtra("textLoading", mTitle);
                mContext.startActivity(intent);
            }
        } else {
            if ((mAdOrder == SmartAd.AD_TYPE_FACEBOOK) && (mGoogleID != null)) loadGoogle();
            else onFail(SmartAd.AD_TYPE_FACEBOOK);
        }
    }

    @Override
    public void onAdLoaded(com.facebook.ads.Ad ad) {
        //if (mIsAutoStart) {
            //dismissProgressDialog();
            Log.d("testache","fb ad is loaded");
            showLoadedAd(mShowLoading);
        //}
    }

    @Override
    public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError adError) {
        Log.d("testache", "SmartAdInterstitial : type = Facebook, error code = "+adError.getErrorCode()+", error message = "+adError.getErrorMessage());

        ad.destroy();
        mFacebookAd.destroy();
        mFacebookAd = null;

        if ((mAdOrder == SmartAd.AD_TYPE_FACEBOOK) && (mGoogleID != null)) loadGoogle();
        else onFail(SmartAd.AD_TYPE_FACEBOOK);
    }

    @Override
    public void onInterstitialDismissed(com.facebook.ads.Ad ad) {
        ad.destroy();
        if (mListener!=null) mListener.onSmartAdInterstitialClose(SmartAd.AD_TYPE_FACEBOOK, mPlacement);
        destroy();
    }

    @Override public void onAdClicked(com.facebook.ads.Ad ad) {}
    @Override public void onLoggingImpression(com.facebook.ads.Ad ad) {}
    @Override public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {}

    // 반환 인터페이스 *********************************************************************************

    public interface OnSmartAdInterstitialListener {
        void onSmartAdInterstitialDone(int adType, int mPlacement);
        void onSmartAdInterstitialFail(int adType, int mPlacement);
        void onSmartAdInterstitialClose(int adType, int mPlacement);
    }


}
