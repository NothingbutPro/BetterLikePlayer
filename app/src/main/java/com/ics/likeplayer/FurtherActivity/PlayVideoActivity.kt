package com.ics.likeplayer.FurtherActivity

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray

import com.google.android.exoplayer2.ui.SimpleExoPlayerView

import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.util.Rational

import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.opengl.Visibility
import android.util.Log
import android.view.ActionMode
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast


class PlayVideoActivity : AppCompatActivity(){

    private var videoPosition: Long =0
    private var closepos: Int =1
    private var StoporNot: Boolean =false
    private lateinit var simpleExoplayer: SimpleExoPlayer
    private   lateinit  var   hideli: LinearLayout
    private   lateinit  var   pipmode: LinearLayout
    private   lateinit  var   mainli: LinearLayout
    private   lateinit  var   imghideshow: ImageView
    private lateinit var mPictureInPictureParamsBuilder: PictureInPictureParams.Builder
    private var playbackPosition = 0L
    lateinit var vidview: SimpleExoPlayerView
    lateinit var myvideo: String
    lateinit var mediacontroller: MediaController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ics.likeplayer.R.layout.activity_play_video)
        vidview = findViewById(com.ics.likeplayer.R.id.simpleExoPlayerView)
        pipmode = findViewById(com.ics.likeplayer.R.id.pipmode)
        hideli = findViewById(com.ics.likeplayer.R.id.hideli)
        imghideshow = findViewById(com.ics.likeplayer.R.id.imghideshow)
        mainli = findViewById(com.ics.likeplayer.R.id.mainli)
//        mainli.visibility =View.GONE
        initializePlayer()
        pipmode.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)){
//                mainli.visibility =View.GONE
                enterPIPMode()
            } else {
            Toast.makeText(this ,"Sorry not Allowed" , Toast.LENGTH_LONG).show()
            }
        }
        imghideshow.setOnClickListener {
            if(hideli.visibility == View.VISIBLE)
            {
                imghideshow.rotation = (-90).toFloat();
                hideli.visibility =View.GONE

            }else{
                imghideshow.rotation = (90).toFloat();
                hideli.visibility =View.VISIBLE
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

    private fun initializePlayer() {
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(this),
            DefaultTrackSelector(), DefaultLoadControl()
        );
        prepareExoplayer()

//        simpleExoPlayerView.player = simpleExoplayer
//        simpleExoplayer.seekTo(playbackPosition)
//        simpleExoplayer.playWhenReady = true
//        simpleExoplayer.addListener(this)
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
        simpleExoplayer.setPlayWhenReady(true);
//        simpleExoplayer.seekTo(currentWindow, playbackPosition);

//        myvideo = Uri.parse("https://music.youtube.com/watch?v=BQ0mxQXmLsk&list=RDAMVMVbfpW0pbvaU").toString()
//        val uri = Uri.parse(myvideo)
//        val mediaSource = buildMediaSource(uri)
        simpleExoplayer.prepare(mediaSource)
    }

    override fun onBackPressed(){
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
        override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean,
                                                   newConfig: Configuration) {
            if (isInPictureInPictureMode) {
                Toast.makeText(this , "You done it well",Toast.LENGTH_LONG).show()

                // Hide the full-screen UI (controls, etc.) while in picture-in-picture mode.
            } else {
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
        Log.e("onStop" , "called")
        StoporNot =true;
        super.onStop()
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        Log.e("Resume" , "called")

        super.onTopResumedActivityChanged(isTopResumedActivity)
    }

    override fun onPostResume() {
        Log.e("Post Resume" , "called")
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
        Toast.makeText(this , "you are in onUserLeaveHint ",Toast.LENGTH_LONG).show()
        super.onUserLeaveHint()
        enterPIPMode()
    }

    //For N devices that support it, not "officially"
    @Suppress("DEPRECATION")
    fun enterPIPMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
            && packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
            videoPosition = simpleExoplayer.currentPosition
            vidview.useController = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val params = PictureInPictureParams.Builder()
                this.enterPictureInPictureMode(params.build())
                Toast.makeText(this , "you are if ",Toast.LENGTH_LONG).show()
//                if(mainli.visibility == View.GONE)
//                {
//                    mainli.visibility = View.VISIBLE
//                }else if(mainli.visibility == View.VISIBLE){
//                    mainli.visibility = View.GONE
//                }
            } else {
                Toast.makeText(this , "you are in else ",Toast.LENGTH_LONG).show()
                this.enterPictureInPictureMode()
            }
        }


    }


}



