package utill;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Junky2 on 8/24/2018.
 */

public class StartProgressBar {

    private static int progressStatus = 0;
    private TextView textView;
    private static Handler handler = new Handler();
    static Drawable draw;
    private EditText etPercent;
    private static ClipDrawable mImageDrawable;

    // a field in your class
    private static int mLevel = 0;
    private static int fromLevel = 0;
    private static int toLevel = 0;

    public static final int MAX_LEVEL = 10000;
    public static final int LEVEL_DIFF = 100;
    public static final int DELAY = 30;
    Thread thread = new Thread();
    Runnable runnable;
    private static Handler mUpHandler = new Handler();
    private static Runnable animateUpImage = new Runnable() {

        @Override
        public void run() {
            doTheUpAnimation(fromLevel, toLevel);
        }
    };
    private ImageView img;
    private ProgressBar progressBar;
    private boolean isStoped;

    private static void doTheUpAnimation(int fromLevel, int toLevel) {
        mLevel += LEVEL_DIFF;
        mImageDrawable.setLevel(mLevel);
        if (mLevel <= toLevel) {
            mUpHandler.postDelayed(animateUpImage, DELAY);
        } else {
            mUpHandler.removeCallbacks(animateUpImage);

        }

    }

    public void startThrad() {
        mImageDrawable = (ClipDrawable) img.getDrawable();
        thread = new Thread(runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("adasdfasd", String.valueOf("progressStatus: " + progressStatus));
                while (progressStatus < 100) {
                    progressStatus += 1;
                    Log.d("adasdfasd", String.valueOf("progressStatus: " + progressStatus));
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgressDrawable(draw);
                            progressBar.setProgress(progressStatus);

                            mImageDrawable.setLevel(progressStatus * 100);
                            //   onClickOk(progressStatus);
                            if (progressBar.getProgress() == 100) {
                                thread.interrupt();
                                Log.d("adasdfasd", "setLevel" + (progressStatus * 100));
                                isStoped = true;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressStatus = 0;
                                        toLevel = 0;
                                    }
                                }, 1000);

                            }
                        }
                    });
                    if (!isStoped) {
                        try {
                            // Sleep for 200 milliseconds.
                            Thread.sleep(55);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    public void startProgressBar(ImageView img, final ProgressBar progressBar) {
        this.img = img;
        this.progressBar = progressBar;
        startThrad();
        thread.start();

    }


}
