package utill;

import android.app.Activity;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import themejunky.com.interstitial.R;

import static ads.interstitial.SmartAdInterstitial.mFacebookAd;


public class LoadingProgressBarFacebook extends AppCompatActivity {
    static Drawable draw;
    private static int mLevel = 0;
    public static final int LEVEL_DIFF = 100;
    public static final int DELAY = 30;
    private static ClipDrawable mImageDrawable;
    private static int toLevel = 0;
    private static int progressStatus = 0;
    private static Handler mUpHandler = new Handler();
    private static Handler handler = new Handler();
    private ImageView imageView;
    private ProgressBar progressBar;
    private long sleepMillisec = 50L;
    private boolean progressTrue = true;
    private static Runnable animateUpImage;
    private Thread thread = new Thread();
    private String stringLoading;
    private String stringAction;
    private TextView textLoading;
    private static boolean isBackPressed;
    public static Activity go;

    public LoadingProgressBarFacebook() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_progress_bar);
        this.stringLoading = this.getIntent().getStringExtra("textLoading");
        this.stringAction = this.getIntent().getStringExtra("textAction");
        this.init();
        this.ruunable();
        this.startThrad();
        this.thread.start();
        isBackPressed = false;
        go = this;
        Log.d("testache","LoadingProgressBarFacebook created");
    }

    private static void doTheUpAnimation(int toLevel) {
        mLevel += 100;
        mImageDrawable.setLevel(mLevel);
        if(mLevel <= toLevel) {
            mUpHandler.postDelayed(animateUpImage, 30L);
        } else {
            mUpHandler.removeCallbacks(animateUpImage);
        }

    }

    public void ruunable() {
        animateUpImage = new Runnable() {
            public void run() {
                LoadingProgressBarFacebook.doTheUpAnimation(LoadingProgressBarFacebook.toLevel);
            }
        };
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)   {
            Log.d("testache","back pressed1");
            isBackPressed = true;
            progressStatus = 0;
            toLevel = 0;
            this.progressTrue = false;
            this.finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("testacz","back pressed2");
    }

    public void startThrad() {
        mImageDrawable = (ClipDrawable)this.imageView.getDrawable();
        this.thread = new Thread(new Runnable() {
            public void run() {
                while(LoadingProgressBarFacebook.this.progressTrue) {
                    LoadingProgressBarFacebook.progressStatus = LoadingProgressBarFacebook.progressStatus + 1;
                    LoadingProgressBarFacebook.handler.post(new Runnable() {
                        public void run() {
                            LoadingProgressBarFacebook.this.progressBar.setProgressDrawable(LoadingProgressBarFacebook.draw);
                            LoadingProgressBarFacebook.this.progressBar.setProgress(LoadingProgressBarFacebook.progressStatus);
                            LoadingProgressBarFacebook.mImageDrawable.setLevel(LoadingProgressBarFacebook.progressStatus * 100);
                            LoadingProgressBarFacebook.this.setTextLoading();
                        }
                    });

                    try {
                        Thread.sleep(LoadingProgressBarFacebook.this.sleepMillisec);
                    } catch (InterruptedException var2) {
                        var2.printStackTrace();
                    }
                }

            }
        });
        this.thread.interrupt();
    }

    public static boolean userCanceledTheLoadingScreen(){
        return isBackPressed;
    }

    public void setTextLoading() {
        if(this.progressBar.getProgress() == 95) {
            Log.d("testache","setTextLoading 1");
            if ((mFacebookAd != null) && (mFacebookAd.isAdLoaded())) {
                if (!isBackPressed) {  //if user pressed back during loading screen, the ad is not shown
                    Log.d("testache", "setTextLoading 2");
                    Log.d("testache", "show fb!!!");
                    mFacebookAd.show();
                }
            }
            Log.d("testache","setTextLoading 3");
            progressStatus = 0;
            toLevel = 0;
            this.progressTrue = false;
            this.finish();
            Log.d("testache","setTextLoading 4");
/*        } else {
            if (Stuff.getAdFailedToLoad()){
                Stuff.setAdFailedToLoad(false);
                progressStatus = 0;
                toLevel = 0;
                this.progressTrue = false;
                this.finish();
            }*/
        }

    }

    public void init() {
        this.textLoading = (TextView)this.findViewById(R.id.loadingText);
        this.progressBar = (ProgressBar)this.findViewById(R.id.progressBar);
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        this.textLoading.setText(this.stringLoading);
    }

    protected void onDestroy() {
        super.onDestroy();
        progressStatus = 0;
    }

}
