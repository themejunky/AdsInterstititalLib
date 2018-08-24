package ads.nativeAdmob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;

import java.util.List;

import ads.ListenerContract;
import themejunky.com.interstitial.R;


/**
 * Created by Junky2 on 9/26/2017.
 */

public class AdmobNativeAds {

    private static AdmobNativeAds instance = null;
    private final Context nContext;
    private View mAdView;
    private ListenerContract.ListenerLogs listenerLogs;
    private AdLoader adLoader;

    public AdmobNativeAds(Context context, String key, ListenerContract.ListenerLogs listenerLogs,View mAdView) {
        nContext = context;
        this.listenerLogs = listenerLogs;
        this.mAdView=mAdView;
        init(key);
    }

    public void init(String idUnitAdmob) {

        adLoader = new AdLoader.Builder(nContext, idUnitAdmob)
                .forContentAd(new NativeContentAd.OnContentAdLoadedListener() {

                    @Override
                    public void onContentAdLoaded(NativeContentAd contentAd) {
                        mAdView = mInflateLayout(R.layout.ad_content);
                        if(mAdView!=null){
                            populateContentAdView(contentAd, (NativeContentAdView) mAdView);
                        }
                        listenerLogs.logs("Admob Native: ad_content Loaded");
                    }
                })
                .forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
                    @Override
                    public void onAppInstallAdLoaded(NativeAppInstallAd appInstallAd) {
                        mAdView = mInflateLayout(R.layout.ad_app_install);
                        if(mAdView!=null){
                            populateAppInstallAdView(appInstallAd, (NativeAppInstallAdView) mAdView);
                        }
                        listenerLogs.logs("Admob Native: ad_app_install Loaded");
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        switch (errorCode) {
                            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                                listenerLogs.logs("Admob Native: Failed to received ad! Internal error code: '%s'." + errorCode);
                                break;
                            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                                listenerLogs.logs("Admob Native: Failed to received ad! Invalid request error code: '%s'." + errorCode);
                                break;
                            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                                listenerLogs.logs("Admob Native: Failed to received ad! Network error code: '%s'." + errorCode);
                                break;
                            case AdRequest.ERROR_CODE_NO_FILL:
                                listenerLogs.logs("Admob Native: Failed to received ad! No fill error code: '%s'." + errorCode);
                                break;
                            default:
                                listenerLogs.logs("Admob Native: Failed to received ad! Error code: '%s'." + errorCode);
                        }
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .build())
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    /**
     * Populates a {@link NativeAppInstallAdView} object with data from a given
     * {@link NativeAppInstallAd}.
     *
     * @param nativeAppInstallAd the object containing the ad's assets
     * @param adView             the view to be populated
     */
    private void populateAppInstallAdView(NativeAppInstallAd nativeAppInstallAd,
                                          NativeAppInstallAdView adView) {

        adView.setHeadlineView(adView.findViewById(R.id.appinstall_headline));
        // adView.setBodyView(adView.findViewById(R.id.appinstall_body));
        adView.setCallToActionView(adView.findViewById(R.id.appinstall_call_to_action));
        adView.setIconView(adView.findViewById(R.id.appinstall_app_icon));
        // adView.setPriceView(adView.findViewById(R.id.appinstall_price));
        adView.setStarRatingView(adView.findViewById(R.id.appinstall_stars));
        adView.setStoreView(adView.findViewById(R.id.appinstall_store));
        adView.setMediaView((MediaView) adView.findViewById(R.id.appinstall_media));
        //adView.setIconView(adView.findViewById(R.id.appinstall_image));


        // Some assets are guaranteed to be in every NativeAppInstallAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAppInstallAd.getHeadline());
        //  ((TextView) adView.getBodyView()).setText(nativeAppInstallAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAppInstallAd.getCallToAction());
        ((ImageView) adView.getIconView()).setImageDrawable(
                nativeAppInstallAd.getIcon().getDrawable());

        MediaView mediaView = adView.findViewById(R.id.appinstall_media);
        //ImageView mainImageView = adView.findViewById(R.id.appinstall_image);

       /* if (nativeAppInstallAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAppInstallAd.getPrice());
        }*/

        if (nativeAppInstallAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAppInstallAd.getStore());
        }

        if (nativeAppInstallAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAppInstallAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAppInstallAd);
    }

    /**
     * Populates a {@link NativeContentAdView} object with data from a given
     * {@link NativeContentAd}.
     *
     * @param nativeContentAd the object containing the ad's assets
     * @param adView          the view to be populated
     */
    private void populateContentAdView(NativeContentAd nativeContentAd, NativeContentAdView adView) {

        adView.setHeadlineView(adView.findViewById(R.id.contentad_headline));
        adView.setImageView(adView.findViewById(R.id.contentad_image));
        adView.setBodyView(adView.findViewById(R.id.contentad_body));
        adView.setCallToActionView(adView.findViewById(R.id.contentad_call_to_action));
        adView.setLogoView(adView.findViewById(R.id.contentad_logo));
        // adView.setAdvertiserView(adView.findViewById(R.id.contentad_advertiser));

        // Some assets are guaranteed to be in every NativeContentAd.
        ((TextView) adView.getHeadlineView()).setText(nativeContentAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeContentAd.getBody());
        ((TextView) adView.getCallToActionView()).setText(nativeContentAd.getCallToAction());
        //((TextView) adView.getAdvertiserView()).setText(nativeContentAd.getAdvertiser());

        List<NativeAd.Image> images = nativeContentAd.getImages();

        if (images.size() > 0) {
            ((ImageView) adView.getImageView()).setImageDrawable(images.get(0).getDrawable());
        }

        // Some aren't guaranteed, however, and should be checked.
        NativeAd.Image logoImage = nativeContentAd.getLogo();

        if (logoImage == null) {
            adView.getLogoView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getLogoView()).setImageDrawable(logoImage.getDrawable());
            adView.getLogoView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeContentAd);
    }


    View mInflateLayout(int nLayout) {
        LayoutInflater factory = LayoutInflater.from(nContext);
        View DialogInflateView = factory.inflate(nLayout, null);
        return DialogInflateView.findViewById(R.id.nContainer);
    }
}
