package com.ics.likeplayer.FurtherActivity

import android.annotation.SuppressLint
import android.app.PictureInPictureParams
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import com.google.android.exoplayer2.ui.PlaybackControlView
import kotlinx.android.synthetic.main.activity_play_video.view.*


class PlayVideoActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        lateinit var screenshotPermission: Intent
    }

    //    @JvmStatic lateinit  val screenshotPermission :Intent ;
    private val REQUEST_ID: Int = 1
    private var videoPosition: Long = 0
    private var closepos: Int = 1
    private var StoporNot: Boolean = false
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
    lateinit var PlaynPauseBTn : ImageButton
    lateinit var ReverseBtn : ImageButton
    lateinit var NextBtn : ImageButton
    lateinit var FastForwardBtn : ImageButton
    lateinit var BackFastForwardBtn : ImageButton
    lateinit var RepeatBtn : ImageButton
    lateinit var VolumeBtn : ImageButton
    lateinit var MuteBtn : ImageButton
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
        slevidname.setText(intent.getStringExtra("slevidname").toString())

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
        PlaynPauseBTn = findViewById(R.id.exo_play);
        ReverseBtn = findViewById(R.id.exo_prev);
        NextBtn = findViewById(R.id.exo_next);
        FastForwardBtn = findViewById(R.id.exo_ffwd);
        RepeatBtn = findViewById(R.id.exo_repeat_toggle);
//        VolumeBtn = findViewById(R.id.exo_);
        BackFastForwardBtn = findViewById(R.id.exo_rew);
//        MuteBtn = findViewById(R.id.exo_);
    }

    @SuppressLint("NewApi")
    private fun takeScreenshot(vidview: PlayerView) {
        ScreenshotManager.INSTANCE.requestScreenshotPermission(this, REQUEST_ID);

//    try {
//        croppedBitmap = Bitmap.createBitmap(bitmap, 0, 0, windowSize.x, windowSize.y);
//    } catch (OutOfMemoryError e) {
//        Log.d("hey", "Out of memory when cropping bitmap of screen size");
//        croppedBitmap = bitmap;
//    }
//    if (croppedBitmap != bitmap) {
//        bitmap.recycle();
//    }

//        var view = this.getWindow().getDecorView();
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//        val b1 = view.getDrawingCache();
//        val frame =  Rect();
//        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        val statusBarHeight = frame.top;
//
//        //Find the screen dimensions to create bitmap in the same size.
//        val width = getWindow().getWindowManager().getDefaultDisplay().width
//        val height = getWindow().getWindowManager().getDefaultDisplay().height
//
//        val b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
//        view.destroyDrawingCache();
//         val dir = Environment.getExternalStorageDirectory().absolutePath+File.separator+"myDirectory";
//     val view =   window.decorView
//    //create folder
//    val folder =  File(dir); //folder name
//    folder.mkdirs();
//        val window =  Timeline.Window();
//        MediaStore.Images.Media.insertImage(contentResolver, b, "Screen", "screen");

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

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        Log.e("Resume", "called")

        super.onTopResumedActivityChanged(isTopResumedActivity)
    }

    override fun onPostResume() {
        Log.e("Post Resume", "called")

//        if(mainli.visibility == View.GONE)
//        {
//            mainli.visibility = View.VISIBLE
//        }else if(mainli.visibility == View.VISIBLE){
//            mainli.visibility = View.GONE
//        }
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



