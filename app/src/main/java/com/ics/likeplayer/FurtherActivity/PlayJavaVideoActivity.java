package com.ics.likeplayer.FurtherActivity;

import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.github.nisrulz.sensey.MovementDetector;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.hmomeni.verticalslider.VerticalSlider;
import com.ics.likeplayer.R;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.ics.likeplayer.ScreenshotManager;

import java.util.Arrays;



public class PlayJavaVideoActivity extends AppCompatActivity   {
    public SimpleExoPlayer simpleExoplayer;
     public PlayerView vidview;
    public PlaybackControlView controls;
    public  LinearLayout hideli;
    public LinearLayout pipmode;
    public LinearLayout mainli;
    public  LinearLayout controlli;
    public  LinearLayout simplelin;
    public  ImageView imghideshow;
    public ImageView screenshot;
    public TextView slevidname;
//    private View progressBar;
//+++++++++++++++++++++++++++++++++For Variables++++++++++++++++++++++++
    public  int REQUEST_ID = 1;
    public long videoPosition = 0;
    public int closepos = 1;
    public Boolean StoporNot = false;
    public Boolean LockORNot =false;
    public Boolean ScreenLockORNot = false;
    public Context context;
    //+++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++All Controls+++++++++++++++++++++++++++++++++++++++++++++++++
    ImageView  ReverseBtn ;
    ImageView  NextBtn ;
    ImageView FastForwardBtn ;
    ImageView BackFastForwardBtn ;
    ImageView RepeatBtn ;
    ImageView VolumeBtn ;
    ImageView MuteBtn ;
    ImageView Img_lockscreen ;
    ImageView Img_rotate ;
    ImageView Img_lockscreen_hide ;
    private String myvideo;
    private ImageView PlaynPauseBTn;
    private ImageView img_rotate;
    private VerticalSlider verticalSlider;

    //+++++++++++++++++++++++++++++++++++++++End+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private PictureInPictureParams.Builder mPictureInPictureParamsBuilder;
    private com.google.android.material.appbar.AppBarLayout  tootwa;
//+++++++++++++++++++++++++++++++++++++++FOR GESTURES++++++++++++++++++
    private GestureDetector mDetector;
    private GestureDetector gestureDetector;
    private TouchTypeDetector.TouchTypListener touchTypListener;
//    private DiscreteSlider mSlider;
    //++++++++++++++++++++++++++++++++


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Sensey.getInstance().setupDispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_play_video);
        super.onCreate(savedInstanceState);
        context = PlayJavaVideoActivity.this;
        InitializeEverything();
        InitializePlayer();
        InitializePlayerControls();

        Sensey.getInstance().init(context);
        touchTypListener=new TouchTypeDetector.TouchTypListener() {
            @Override public void onTwoFingerSingleTap() {
                // Two finger single tap
            }

            @Override public void onThreeFingerSingleTap() {
                // Three finger single tap
            }

            @Override public void onDoubleTap() {
                // Double tap
            }

            @Override
            public void onScroll(int scrollDirection) {
                switch (scrollDirection) {
                    case TouchTypeDetector.SCROLL_DIR_UP:
                        Toast.makeText(PlayJavaVideoActivity.this, "Scrolling up", Toast.LENGTH_SHORT).show();
                        // Scrolling Up
                        if(verticalSlider.getVisibility() == View.GONE) {
                            verticalSlider.setVisibility(View.VISIBLE);
                            if (verticalSlider.getProgress() < verticalSlider.getMax()) {
                                verticalSlider.setProgress(verticalSlider.getProgress() + 5);

                            } else if (verticalSlider.getProgress() == 100) {
                                verticalSlider.setProgress(verticalSlider.getProgress());
                            }
                        }
                        break;
                    case TouchTypeDetector.SCROLL_DIR_DOWN:
                        Toast.makeText(PlayJavaVideoActivity.this, "Scrolling Down", Toast.LENGTH_SHORT).show();
                        if(verticalSlider.getVisibility() == View.GONE) {
                            if (verticalSlider.getProgress() != 0) {
                                verticalSlider.setProgress(verticalSlider.getProgress() - 5);
                                verticalSlider.setVisibility(View.GONE);
                            } else {
                                verticalSlider.setProgress(verticalSlider.getProgress());
                            }
                        }
                        // Scrolling Down
                        break;
                    case TouchTypeDetector.SCROLL_DIR_LEFT:
                        Toast.makeText(PlayJavaVideoActivity.this, "Scrolling SCROLL_DIR_LEFT", Toast.LENGTH_SHORT).show();
                        // Scrolling Left
                        break;
                    case TouchTypeDetector.SCROLL_DIR_RIGHT:
                        Toast.makeText(PlayJavaVideoActivity.this, "Scrolling SCROLL_DIR_RIGHT", Toast.LENGTH_SHORT).show();

                        // Scrolling Right
                        break;
                    default:
                        // Do nothing
                        break;
                }
            }

            @Override public void onSingleTap() {
                if(mainli.getVisibility() ==View.VISIBLE && controls.getVisibility() == View.VISIBLE && tootwa.getVisibility() == View.VISIBLE  )
                {
                    mainli.setVisibility(View.GONE);
                    controls.setVisibility(View.GONE);
                    tootwa.setVisibility(View.GONE);
                }else {
                    mainli.setVisibility(View.VISIBLE);
                    controls.setVisibility(View.VISIBLE);
                    tootwa.setVisibility(View.VISIBLE);
                }
                // Single tap
            }

            @Override public void onSwipe(int swipeDirection) {
                switch (swipeDirection) {
                    case TouchTypeDetector.SWIPE_DIR_UP:
                        Toast.makeText(PlayJavaVideoActivity.this, "Scrolling SWIPE_DIR_UP", Toast.LENGTH_SHORT).show();

//                        verticalSlider.getOnProgressChangeListener().onChanged(10,100);

                        // Swipe Up
                        break;
                    case TouchTypeDetector.SWIPE_DIR_DOWN:

                        // Swipe Down
                        break;
                    case TouchTypeDetector.SWIPE_DIR_LEFT:
                        Toast.makeText(PlayJavaVideoActivity.this, "Scrolling SWIPE_DIR_LEFT", Toast.LENGTH_SHORT).show();

                        // Swipe Left
                        break;
                    case TouchTypeDetector.SWIPE_DIR_RIGHT:
                        Toast.makeText(PlayJavaVideoActivity.this, "Scrolling SWIPE_DIR_RIGHT", Toast.LENGTH_SHORT).show();

                        // Swipe Right
                        break;
                    default:
                        //do nothing
                        break;
                }
            }

            @Override public void onLongPress() {
                // Long press
            }
        };
        Sensey.getInstance().startTouchTypeDetection(PlayJavaVideoActivity.this, touchTypListener);
    }

//    private void setUpView() {
//        mSlider.setTrackWidth(Utils.convertDpToPixel(4, this));
//        mSlider.setTrackColor(0xFFD81B60);
//        mSlider.setInactiveTrackColor(0x3DD81B60);
//
//        mSlider.setThumbRadius(Utils.convertDpToPixel(6, this));
//        mSlider.setThumbColor(0xFFD81B60);
//        mSlider.setThumbPressedColor(0x1FD81B60);
//        mSlider.setLeft(10);
//        mSlider.setTickMarkColor(0x3DFFFFFF);
//        mSlider.setTickMarkInactiveColor(0x1FD81B60);
//        mSlider.setTickMarkPatterns(
//                Arrays.asList(new Dot(), new Dash(Utils.convertDpToPixel(1, this))));
//
//        mSlider.setValueLabelTextColor(Color.WHITE);
//        mSlider.setValueLabelTextSize(Utils.convertSpToPixel(16, this));
//        mSlider.setValueLabelFormatter(new DiscreteSlider.ValueLabelFormatter() {
//
//            @Override
//            public String getLabel(int input) {
//                return Integer.toString(input);
//            }
//        });
//
//        mSlider.setCount(21);
//        mSlider.setMode(DiscreteSlider.MODE_NORMAL);
//
//        mSlider.setMinProgress(5);
//
//        mSlider.setOnValueChangedListener(new DiscreteSlider.OnValueChangedListener() {
//
//            @Override
//            public void onValueChanged(int progress, boolean fromUser) {
//                super.onValueChanged(progress, fromUser);
//                Log.i("DiscreteSlider", "Progress: " + progress + ", fromUser: " + fromUser);
//            }
//
//            @Override
//            public void onValueChanged(int minProgress, int maxProgress, boolean fromUser) {
//                super.onValueChanged(minProgress, maxProgress, fromUser);
//                Log.i("DiscreteSlider",
//                        "MinProgress: " + minProgress + ", MaxProgress: " + maxProgress +
//                                ", fromUser: " + fromUser);
//            }
//        });
//
//        mSlider.setClickable(true);
//    }

    private void InitializePlayerControls() {
        PlaynPauseBTn = findViewById(com.ics.likeplayer.R.id.playnpause);
        ReverseBtn = findViewById(R.id.exo_prev);
        NextBtn = findViewById(R.id.exo_next);
        FastForwardBtn = findViewById(R.id.exo_ffwd);
        RepeatBtn = findViewById(R.id.exo_repeat_toggle);
        RepeatBtn = findViewById(R.id.exo_repeat_toggle);
        verticalSlider = findViewById(R.id.verticalSlider);
//        VolumeBtn = findViewById(R.id.exo_);
        BackFastForwardBtn = findViewById(R.id.exo_rew);
        tootwa = findViewById(R.id.tootwa);
        Img_lockscreen = findViewById(com.ics.likeplayer.R.id.img_lockscreen);
        img_rotate = findViewById(com.ics.likeplayer.R.id.img_rotate);
        Img_lockscreen_hide = findViewById(com.ics.likeplayer.R.id.img_lockscreen_hide);

        // get the gesture detector

        //++++++++++++++++++++++++++++++++++++++++++++++++MAin Functions+++++++++++++++++++++++++++++++++++++++++++++++++++++

        screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot(vidview);
            }
        });
        img_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ScreenLockORNot)
                {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    ScreenLockORNot =false;
                }else{
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    ScreenLockORNot =true;
                }
            }
        });

        Img_lockscreen_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainli.getVisibility() ==  View.VISIBLE && controls.getVisibility()  ==  View.VISIBLE && Img_lockscreen_hide.getVisibility()  ==  View.GONE &&
                        tootwa.getVisibility() ==  View.GONE)
                {
                    mainli.setVisibility( View.GONE);
                    controls.setVisibility( View.GONE);
                    Img_lockscreen_hide.setVisibility(View.VISIBLE);
                    tootwa.setVisibility(View.GONE);
                }else{
                    mainli.setVisibility(View.VISIBLE);
                    controls.setVisibility(View.VISIBLE);
                    tootwa.setVisibility(View.VISIBLE);
                    Img_lockscreen_hide.setVisibility(View.GONE);
                }
                LockORNot = false;
            }
        });

        Img_lockscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainli.getVisibility() ==  View.VISIBLE && controls.getVisibility() ==  View.VISIBLE && Img_lockscreen_hide.getVisibility() ==  View.GONE)
                {
                    mainli.setVisibility(View.GONE);
                    controls.setVisibility(View.GONE);
                    tootwa.setVisibility(View.GONE);
                    Img_lockscreen_hide.setVisibility(View.VISIBLE);
                }else{
                    mainli.setVisibility(View.VISIBLE);
                    controls.setVisibility(View.VISIBLE);
                    tootwa.setVisibility(View.VISIBLE);
                    Img_lockscreen_hide.setVisibility(View.GONE);
                }
                LockORNot = true;
            }
        });
        //++++++++++++++++++++++++++++++++++++fdjg+++++++++++++++++++++++++++++++++
    }

    private void takeScreenshot(PlayerView vidview) {
        ScreenshotManager.INSTANCE.requestScreenshotPermission(this, REQUEST_ID);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            Toast.makeText(this, "You done it well", Toast.LENGTH_LONG).show();

            // Hide the full-screen UI (controls, etc.) while in picture-in-picture mode.
        } else {
            if (mainli.getVisibility() == View.VISIBLE) {
                 imghideshow.setRotation((-90));
                mainli.setVisibility(View.GONE);

            } else {
                    imghideshow.setRotation((90));
                mainli.setVisibility(View.VISIBLE) ;
            }
            if (StoporNot) {
                simpleExoplayer.stop();
//                    finish()
            }
//                simpleExoplayer.stop()
//                finish()
            // Restore the full-screen UI.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_ID) {
//            ScreenshotManager.INSTANCE.onActivityResult(resultCode, data);
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
            ScreenshotManager.INSTANCE.takeScreenshot(this, data);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void InitializeEverything() {
        vidview = findViewById(com.ics.likeplayer.R.id.simpleExoPlayerView);
        screenshot = findViewById(com.ics.likeplayer.R.id.screenshot);
        controls = findViewById(com.ics.likeplayer.R.id.controls);
        slevidname = findViewById(com.ics.likeplayer.R.id.slevidname);
        hideli = findViewById(com.ics.likeplayer.R.id.hideli);
//        mSlider = findViewById(R.id.volumeslider);
//        progressBar = findViewById(com.ics.likeplayer.R.id.progressBar);
//         controls = findViewById(R.id.controls)
//        controls.player = this.vidview
        pipmode = findViewById(com.ics.likeplayer.R.id.pipmode);
        imghideshow = findViewById(com.ics.likeplayer.R.id.imghideshow);
        mainli = findViewById(com.ics.likeplayer.R.id.mainli);
    }

    private void InitializePlayer() {

            simpleExoplayer = ExoPlayerFactory.newSimpleInstance(
                    this,
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(),new DefaultLoadControl()
            );
            prepareExoplayer();

//        simpleExoPlayerView.player = simpleExoplayer
//        simpleExoplayer.seekTo(playbackPosition)
//        simpleExoplayer.playWhenReady = true
//        simpleExoplayer.addListener(this)

    }

    private void prepareExoplayer() {
        vidview.setPlayer(simpleExoplayer);
        myvideo = getIntent().getStringExtra("vidurl");
        Uri uri = Uri.parse(myvideo);
//        MediaSource mediaSource = buildMediaSource(uri);
        MediaSource mediaSource = buildMediaSource(uri);
        simpleExoplayer.prepare(mediaSource, true, false);
//        simpleExoplayer.setV
        simpleExoplayer.setPlayWhenReady(true);
        controls.setPlayer(simpleExoplayer);
        pipmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    enterPIPMode();


            }
        });
        vidview.setControllerShowTimeoutMs(0);
        simpleExoplayer.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {
//                    progressBar.getVisibility() = View.VISIBLE;
                }
                else if (playbackState == Player.STATE_READY) {
//                    progressBar.getVisibility() = View.INVISIBLE;
                } else if(playbackState == Player.STATE_ENDED)
                {
                    Toast.makeText(getApplicationContext() ,"Your Life has been ended",Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }

    private MediaSource buildMediaSource( Uri uris) {
        return new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(PlayJavaVideoActivity.this,"Exoplayer-local")).
                createMediaSource(uris);
         
    }

    @Override
    protected void onUserLeaveHint() {
        Toast.makeText(this, "you are in onUserLeaveHint ", Toast.LENGTH_LONG).show();
        super.onUserLeaveHint();
        enterPIPMode();
    }

    private void enterPIPMode() {

            videoPosition = simpleExoplayer.getCurrentPosition();
            vidview.setUseController(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mainli.setVisibility(View.GONE);
                PictureInPictureParams.Builder params = new PictureInPictureParams.Builder();
                this.enterPictureInPictureMode(params.build());
                Toast.makeText(this, "you are if ", Toast.LENGTH_LONG).show();
//                if(mainli.visibility == View.GONE)
//                {
//                    mainli.visibility = View.VISIBLE
//                }else if(mainli.visibility == View.VISIBLE){
//                    mainli.visibility = View.GONE
//                }
            }

    }

    @Override
    protected void onPostResume() {
        Log.e("Post Resume", "called");
        super.onPostResume();
    }

    @Override
    protected void onStop() {
        Log.e("onStop", "called");
        StoporNot = true;

        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        simpleExoplayer.stop();
        
    }



}
