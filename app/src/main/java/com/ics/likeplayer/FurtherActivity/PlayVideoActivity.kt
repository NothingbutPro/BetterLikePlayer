package com.ics.likeplayer.FurtherActivity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import android.util.DisplayMetrics




class PlayVideoActivity : AppCompatActivity() {
    lateinit var vidview :VideoView
    lateinit var myvideo :String
    lateinit var  mediacontroller : MediaController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ics.likeplayer.R.layout.activity_play_video)
        vidview = findViewById(com.ics.likeplayer.R.id.vidview)
        supportActionBar?.hide()
         mediacontroller =  MediaController(this);
        mediacontroller.setAnchorView(vidview);
        myvideo = intent.getStringExtra("vidurl")
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val params = vidview.getLayoutParams() as android.widget.LinearLayout.LayoutParams
        params.width = metrics.widthPixels
        params.height = metrics.heightPixels
        params.leftMargin = 0
        vidview.setLayoutParams(params)
        vidview.setMediaController(mediacontroller);
        vidview.setVideoURI(Uri.parse(myvideo));
        vidview.requestFocus();
        vidview.start()
    }
}
