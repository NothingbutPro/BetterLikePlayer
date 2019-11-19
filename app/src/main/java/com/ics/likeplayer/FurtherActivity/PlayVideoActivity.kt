package com.ics.likeplayer.FurtherActivity

import android.annotation.SuppressLint
import android.app.PictureInPictureParams
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector

import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import android.os.Build

import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import android.util.Log
import android.widget.*
import com.google.android.exoplayer2.ui.PlayerView
import com.ics.likeplayer.ScreenshotManager
import com.google.android.exoplayer2.ui.PlayerControlView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.view.MotionEvent
import android.view.View
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlaybackControlView
import kotlinx.android.synthetic.main.activity_play_video.*


class PlayVideoActivity : AppCompatActivity(),Player.EventListener {


    companion object {
        @JvmStatic
        lateinit var screenshotPermission: Intent
    }
    private lateinit var thread: Thread
    //    @JvmStatic lateinit  val screenshotPermission :Intent ;
    //+++++++++++++++++++++++++++++++++For Variables++++++++++++++++++++++++
    private val REQUEST_ID: Int = 1
    private var videoPosition: Long = 0
    private var closepos: Int = 1
    private var StoporNot: Boolean = false
    private var LockORNot: Boolean = false
    private var  ScreenLockORNot: Boolean = false
    //+++++++++++++++++++++++++++++++++++++++++++++++
    private lateinit var simpleExoplayer: SimpleExoPlayer
    private lateinit var controls: PlaybackControlView
    private lateinit var hideli: LinearLayout
    private lateinit var pipmode: LinearLayout
    private lateinit var mainli: LinearLayout
    private lateinit var controlli: LinearLayout
    private lateinit var simplelin: LinearLayout
    private lateinit var imghideshow: ImageView
    private lateinit var screenshot: ImageView
    private lateinit var slevidname: TextView
    private lateinit var mPictureInPictureParamsBuilder: PictureInPictureParams.Builder
    private var playbackPosition = 0L
    lateinit var vidview: PlayerView
    //+++++++++++++++++++++++++++++++++++++++All Controls+++++++++++++++++++++++++++++++++++++++++++++++++
    lateinit var PlaynPauseBTn : ImageView
    lateinit var ReverseBtn : ImageView
    lateinit var NextBtn : ImageView
    lateinit var FastForwardBtn : ImageView
    lateinit var BackFastForwardBtn : ImageView
    lateinit var RepeatBtn : ImageView
    lateinit var VolumeBtn : ImageView
    lateinit var MuteBtn : ImageView
    lateinit var Img_lockscreen : ImageView
    lateinit var Img_rotate : ImageView
    lateinit var Img_lockscreen_hide : ImageView
    //+++++++++++++++++++++++++++++++++++++++End+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //    lateinit var controls: PlaybackControlView
    lateinit var myvideo: String
    lateinit var mediacontroller: MediaController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ics.likeplayer.R.layout.activity_play_video)
        vidview = findViewById(com.ics.likeplayer.R.id.simpleExoPlayerView)
        screenshot = findViewById(com.ics.likeplayer.R.id.screenshot)
        controls = findViewById(com.ics.likeplayer.R.id.controls)
        slevidname = findViewById(com.ics.likeplayer.R.id.slevidname)
        hideli = findViewById(com.ics.likeplayer.R.id.hideli)
//         controls = findViewById(R.id.controls)
//        controls.player = this.vidview
        pipmode = findViewById(com.ics.likeplayer.R.id.pipmode)
        imghideshow = findViewById(com.ics.likeplayer.R.id.imghideshow)
        mainli = findViewById(com.ics.likeplayer.R.id.mainli)

//        PauseBTn = findViewById(R.id.exo_play);
        InitializePlayer()
        InitializePlayerCOntrols()
        screenshot.setOnClickListener {
            takeScreenshot(vidview);
        }

        Img_rotate.setOnClickListener {
            Toast.makeText(this ,"hey",Toast.LENGTH_LONG).show()
            if( ScreenLockORNot)
            {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                ScreenLockORNot =false
            }else{
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                ScreenLockORNot =true
            }
//            if(ActivityInfo.CONFIG_ORIENTATION == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//            }else if(ActivityInfo.CONFIG_ORIENTATION == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//            {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            }
//            ScreenLockORNot =true
        }

        slevidname.setText(intent.getStringExtra("slevidname").toString())
        PlaynPauseBTn.setOnClickListener {
            if(simpleExoplayer.isPlaying)
            {
//                simpleExoplayer.pa
                simpleExoplayer.setPlayWhenReady(false);
                simpleExoplayer.getPlaybackState();
            }else{
                simpleExoplayer.setPlayWhenReady(true);
                simpleExoplayer.getPlaybackState();
            }
        }
        //for Hide and show
            vidview.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Toast.makeText(this@PlayVideoActivity , "Hey touch",Toast.LENGTH_LONG).show()
                if(mainli.visibility ==  View.VISIBLE && controls.visibility ==  View.VISIBLE && tootwa.visibility ==View.VISIBLE){
                    mainli.visibility = View.GONE
                    controls.visibility = View.GONE
                    Img_lockscreen_hide.visibility =  View.GONE
                    tootwa.visibility =View.GONE
                }
                else
                {
                    mainli.visibility = View.VISIBLE
                    controls.visibility = View.VISIBLE
                    Img_lockscreen_hide.visibility =  View.GONE
                    tootwa.visibility =View.VISIBLE
                }

                return v?.onTouchEvent(event) ?: true
            }
        })

        //
        pipmode.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
            ) {
//                mainli.visibility =View.GONE
                enterPIPMode()
            } else {
                Toast.makeText(this, "Sorry not Allowed", Toast.LENGTH_LONG).show()
                enterPIPMode()
//                if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        !Settings.canDrawOverlays(this)
//
//                    } else {
//                        TODO("VERSION.SDK_INT < M")
//
//                    }) {
//                    intent  = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
//                    startActivityForResult(intent, 0);
//                }
            }
        }
        imghideshow.setOnClickListener {
            if (hideli.visibility == View.VISIBLE) {
                imghideshow.rotation = (-90).toFloat();
                hideli.visibility = View.GONE

            } else {
                imghideshow.rotation = (90).toFloat();
                hideli.visibility = View.VISIBLE
            }
        }
//        supportActionBar?.hide()
//         mediacontroller =  MediaController(this);
//        mediacontroller.setAnchorView(vidview);
//        myvideo = intent.getStringExtra("vidurl")
//        val metrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(metrics)
//        val params = vidview.getLayoutParams() as android.widget.LinearLayout.LayoutParams
//        params.width = metrics.widthPixels
//        params.height = metrics.heightPixels
//        params.leftMargin = 0
//        vidview.setLayoutParams(params)
//        vidview.setMediaController(mediacontroller);
//        vidview.setVideoURI(Uri.parse(myvideo));
//        vidview.requestFocus();
//        vidview.start()

    }

    private fun InitializePlayerCOntrols() {
//        PlaynPauseBTn = findViewById(R.id.);
        PlaynPauseBTn = findViewById(com.ics.likeplayer.R.id.playnpause)
        ReverseBtn = findViewById(R.id.exo_prev);
        NextBtn = findViewById(R.id.exo_next);
        FastForwardBtn = findViewById(R.id.exo_ffwd);
        RepeatBtn = findViewById(R.id.exo_repeat_toggle);
        RepeatBtn = findViewById(R.id.exo_repeat_toggle);
//        VolumeBtn = findViewById(R.id.exo_);
        BackFastForwardBtn = findViewById(R.id.exo_rew);
        Img_lockscreen = findViewById(com.ics.likeplayer.R.id.img_lockscreen);
        Img_rotate = findViewById(com.ics.likeplayer.R.id.img_rotate);
        Img_lockscreen_hide = findViewById(com.ics.likeplayer.R.id.img_lockscreen_hide);
   //++++++++++++++++++++++++++++++++++++++++++++++++MAin Functions+++++++++++++++++++++++++++++++++++++++++++++++++++++
        Img_lockscreen_hide.setOnClickListener {
            if(mainli.visibility ==  View.VISIBLE && controls.visibility ==  View.VISIBLE && Img_lockscreen_hide.visibility ==  View.GONE &&
                tootwa.visibility ==  View.GONE)
            {
                mainli.visibility = View.GONE
                controls.visibility = View.GONE
                Img_lockscreen_hide.visibility = View.VISIBLE
                tootwa.visibility = View.GONE
            }else{
                mainli.visibility = View.VISIBLE
                controls.visibility = View.VISIBLE
                tootwa.visibility = View.VISIBLE
                Img_lockscreen_hide.visibility = View.GONE
            }
            LockORNot = false
        }

        Img_lockscreen.setOnClickListener {
            if(mainli.visibility ==  View.VISIBLE && controls.visibility ==  View.VISIBLE && Img_lockscreen_hide.visibility ==  View.GONE)
            {
                mainli.visibility = View.GONE
                controls.visibility = View.GONE
                tootwa.visibility = View.GONE
                Img_lockscreen_hide.visibility = View.VISIBLE
            }else{
                mainli.visibility = View.VISIBLE
                controls.visibility = View.VISIBLE
                tootwa.visibility = View.VISIBLE
                Img_lockscreen_hide.visibility = View.GONE
            }
            LockORNot = true
        }
        //++++++++++++++++++++++++++++++++++++fdjg+++++++++++++++++++++++++++++++++

//        MuteBtn = findViewById(R.id.exo_);
    }

    @SuppressLint("NewApi")
    private fun takeScreenshot(vidview: PlayerView) {
        ScreenshotManager.INSTANCE.requestScreenshotPermission(this, REQUEST_ID);
    }

    private fun InitializePlayer() {
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(
            this,
            DefaultRenderersFactory(this),
            DefaultTrackSelector(), DefaultLoadControl()
        );
        prepareExoplayer()

//        simpleExoPlayerView.player = simpleExoplayer
//        simpleExoplayer.seekTo(playbackPosition)
//        simpleExoplayer.playWhenReady = true
//        simpleExoplayer.addListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ID) {
//            ScreenshotManager.INSTANCE.onActivityResult(resultCode, data);
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
            ScreenshotManager.INSTANCE.takeScreenshot(this, data)
//            mainli.visibility =View.VISIBLE
//            controlli.visibility =View.VISIBLE
//
//            vidview.player = simpleExoplayer
//
//            vidview.showController()
//            vidview.showContextMenu()
            // prepareExoplayer()


//            if(!vidview.isControllerVisible)
//            {
//                vidview.controllerAutoShow =true
//            }
//            initializePlayer()

        }

    }

    private fun buildMediaSource(uri: Uri): MediaSource {
//        val mediaSource = buildMediaSource(uri)
//        simpleExoplayer.prepare(mediaSource, true, false)
//        mediacontroller =  MediaController(this);
//                vidview.setLayoutParams(params)
//        vidview.set(mediacontroller);
//        vidview.setVideoURI(Uri.parse(myvideo));
//        vidview.requestFocus();
//        val dataSourceFactory = DefaultHttpDataSourceFactory("ua", bandwidthMeter)
//        val dashChunkSourceFactory = DefaultDashChunkSource.Factory(dataSourceFactory)
//        return DashMediaSource(uri, dataSourceFactory, dashChunkSourceFactory, null, null)
        return ExtractorMediaSource.Factory(
            DefaultDataSourceFactory(this, "Exoplayer-local")
        ).createMediaSource(uri)
    }

    private fun prepareExoplayer() {
        vidview.setPlayer(simpleExoplayer);
        myvideo = intent.getStringExtra("vidurl")
        val uri = Uri.parse(myvideo)
        val mediaSource = buildMediaSource(uri)
        simpleExoplayer.prepare(mediaSource, true, false)
//        simpleExoplayer.setV
        simpleExoplayer.setPlayWhenReady(true);
        controls.player = simpleExoplayer

//        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        simpleExoplayer.seekTo(currentWindow, playbackPosition);

//        myvideo = Uri.parse("https://music.youtube.com/watch?v=BQ0mxQXmLsk&list=RDAMVMVbfpW0pbvaU").toString()
//        val uri = Uri.parse(myvideo)
//        val mediaSource = buildMediaSource(uri)
        simpleExoplayer.prepare(mediaSource)
        vidview.controllerShowTimeoutMs = 0
        simpleExoplayer.addListener(object: Player.EventListener{
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
            }

            override fun onPlayerError(error: ExoPlaybackException?) {
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.visibility = View.VISIBLE
                }
                else if (playbackState == Player.STATE_READY) {
                    progressBar.visibility = View.INVISIBLE
                } else if(playbackState == Player.STATE_ENDED)
                {
                    Toast.makeText(this@PlayVideoActivity ,"Your Life has been ended",Toast.LENGTH_SHORT ).show()
                }
            }

            override fun onLoadingChanged(isLoading: Boolean) {
            }

            override fun onPositionDiscontinuity(reason: Int) {
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
            }

            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
            }
        })
    }

    override fun onBackPressed() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
//            && packageManager
//                .hasSystemFeature(
//                    PackageManager.FEATURE_PICTURE_IN_PICTURE)){
//
//            enterPIPMode()
//        } else {
        simpleExoplayer.stop()
        super.onBackPressed()
//        }
    }

    //FOr PIPs
    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        if (isInPictureInPictureMode) {
            Toast.makeText(this, "You done it well", Toast.LENGTH_LONG).show()

            // Hide the full-screen UI (controls, etc.) while in picture-in-picture mode.
        } else {
            if (mainli.visibility == View.VISIBLE) {
                // imghideshow.rotation = (-90).toFloat();
                mainli.visibility = View.GONE

            } else {
                //    imghideshow.rotation = (90).toFloat();
                mainli.visibility = View.VISIBLE
            }
            if (StoporNot) {
                simpleExoplayer.stop()
//                    finish()
            }
//                simpleExoplayer.stop()
//                finish()
            // Restore the full-screen UI.
        }
    }

    override fun onStop() {
        Log.e("onStop", "called")
        StoporNot = true;

        super.onStop()
    }

//    fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
//        Log.e("Resume", "called")
//
//        super.onTopResumedActivityChanged(isTopResumedActivity)
//    }

    override fun onPostResume() {
        Log.e("Post Resume", "called")

        super.onPostResume()
    }

    //
    //Called when the user touches the Home or Recents button to leave the app.
    override fun onUserLeaveHint() {
        Toast.makeText(this, "you are in onUserLeaveHint ", Toast.LENGTH_LONG).show()
        super.onUserLeaveHint()
        enterPIPMode()
    }

    //For N devices that support it, not "officially"
    @Suppress("DEPRECATION")
    fun enterPIPMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
            && packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        ) {
            videoPosition = simpleExoplayer.currentPosition
            vidview.useController = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mainli.visibility = View.GONE
                val params = PictureInPictureParams.Builder()
                this.enterPictureInPictureMode(params.build())
                Toast.makeText(this, "you are if ", Toast.LENGTH_LONG).show()
//                if(mainli.visibility == View.GONE)
//                {
//                    mainli.visibility = View.VISIBLE
//                }else if(mainli.visibility == View.VISIBLE){
//                    mainli.visibility = View.GONE
//                }
            } else {
                Toast.makeText(this, "you are in else ", Toast.LENGTH_LONG).show()
                this.enterPictureInPictureMode()
            }
        }


    }

}



