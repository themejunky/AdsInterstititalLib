package utill;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    Runnable runnable ;
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

    public void startThrad(){
        mImageDrawable = (ClipDrawable) img.getDrawable();
        thread = new Thread(runnable =new Runnable() {
            @Override
            public void run() {
                Log.d("adasdfasd", String.valueOf("progressStatus: "+progressStatus));
                while (progressStatus < 100) {
                    progressStatus += 1;
                    Log.d("adasdfasd", String.valueOf("progressStatus: "+progressStatus));
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgressDrawable(draw);
                            progressBar.setProgress(progressStatus);

                            mImageDrawable.setLevel(progressStatus*100);
                            //   onClickOk(progressStatus);
                            if( progressBar.getProgress()==100){
                                thread.interrupt();
                                Log.d("adasdfasd", "setLevel"+(progressStatus*100));
                                isStoped=true;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressStatus=0;
                                        toLevel = 0;
                                    }
                                },1000);

                            }
                        }
                    });
                    if(!isStoped){
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


    private static void onClickOk(int progressStatus) {
        int temp_level = (progressStatus * MAX_LEVEL) / 100;

        if (toLevel == temp_level || temp_level > MAX_LEVEL) {
            return;
        }
        toLevel = (temp_level <= MAX_LEVEL) ? temp_level : toLevel;
        Log.d("adasdfasd","toLevel: " +toLevel);
        Log.d("adasdfasd","fromLevel: " +fromLevel);
        if (toLevel > fromLevel) {
            // cancel previous process first
            mUpHandler.post(animateUpImage);
        }
    }

    public void startProgressBar(ImageView img, final ProgressBar progressBar){
        this.img = img;
        this.progressBar = progressBar;
        startThrad();
        thread.start();

    }










    /*




     private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    Drawable draw;
    private EditText etPercent;
    private ClipDrawable mImageDrawable;

    // a field in your class
    private int mLevel = 0;
    private int fromLevel = 0;
    private int toLevel = 0;

    public static final int MAX_LEVEL = 10000;
    public static final int LEVEL_DIFF = 100;
    public static final int DELAY = 30;

    private Handler mUpHandler = new Handler();
    private Runnable animateUpImage = new Runnable() {

        @Override
        public void run() {
            doTheUpAnimation(fromLevel, toLevel);
        }
    };

    private Handler mDownHandler = new Handler();
    private Runnable animateDownImage = new Runnable() {

        @Override
        public void run() {
            doTheDownAnimation(fromLevel, toLevel);
        }
    };
    private long milisec =0;
    private int nr=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startProgressBar();
      *//*  final Handler handler = new Handler();
        final int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                startProgressBar();
                handler.postDelayed(this, delay);
            }
        }, delay);*//*

}


    private void doTheUpAnimation(int fromLevel, int toLevel) {
        mLevel += LEVEL_DIFF;
        mImageDrawable.setLevel(mLevel);
        if (mLevel <= toLevel) {
            mUpHandler.postDelayed(animateUpImage, DELAY);
        } else {
            mUpHandler.removeCallbacks(animateUpImage);
            MainActivity.this.fromLevel = toLevel;
        }
    }

    private void doTheDownAnimation(int fromLevel, int toLevel) {
        mLevel -= LEVEL_DIFF;
        mImageDrawable.setLevel(mLevel);
        if (mLevel >= toLevel) {
            mDownHandler.postDelayed(animateDownImage, DELAY);
        } else {
            mDownHandler.removeCallbacks(animateDownImage);
            MainActivity.this.fromLevel = toLevel;
        }
    }

    public void onClickOk(int progressStatus) {
        int temp_level = (progressStatus * MAX_LEVEL) / 100;

        if (toLevel == temp_level || temp_level > MAX_LEVEL) {
            return;
        }
        toLevel = (temp_level <= MAX_LEVEL) ? temp_level : toLevel;
        if (toLevel > fromLevel) {
            // cancel previous process first
            mDownHandler.removeCallbacks(animateDownImage);
            MainActivity.this.fromLevel = toLevel;

            mUpHandler.post(animateUpImage);
        } else {
            // cancel previous process first
            mUpHandler.removeCallbacks(animateUpImage);
            MainActivity.this.fromLevel = toLevel;

            mDownHandler.post(animateDownImage);
        }
    }

    public void startProgressBar(){
       *//* nr++;
        if(nr==1){
            onClickOk(50);
            milisec=10;
        }else {
            onClickOk(0);
            milisec=100;
        }*//*

        Log.d("adasdfasd", String.valueOf("intra"));
        ImageView img = (ImageView) findViewById(R.id.imageView1);
        mImageDrawable = (ClipDrawable) img.getDrawable();
        mImageDrawable.setLevel(0);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);
        // draw =getResources().getDrawable(R.mipmap.bara1);
        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                Log.d("adasdfasd", String.valueOf("progressStatus: "+progressStatus));
                while (progressStatus < 100) {
                    Log.d("adasdfasd", String.valueOf(progressBar.getProgress()));
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgressDrawable(draw);
                            progressBar.setProgress(progressStatus);
                            //  onClickOk(progressStatus);
                            // textView.setText(progressStatus+"/"+progressBar.getMax());
                            textView.setText("Loading ( " +progressStatus + " % complete)");
                            if( progressBar.getProgress()==100){
                               *//* progressBar.setProgress(0);
                                progressStatus = 0;*//*
                                // startProgressBar();
                                Toast.makeText(MainActivity.this, "Finish", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }*/




}
