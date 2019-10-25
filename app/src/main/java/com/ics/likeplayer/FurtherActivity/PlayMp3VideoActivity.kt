package com.ics.likeplayer.FurtherActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ics.likeplayer.R
import android.media.MediaPlayer
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.core.net.toUri
import java.util.concurrent.TimeUnit


class PlayMp3VideoActivity : AppCompatActivity() {
    private lateinit var b1: Button
    lateinit var b2: Button
    lateinit var b3: Button
    lateinit var b4: Button
    private var iv: ImageView? = null
    private lateinit var mediaPlayer: MediaPlayer

    private var startTime = 0.0
    private var finalTime = 0.0

    private var myHandler = Handler();
    private var forwardTime = 5000
    private var backwardTime = 5000
    private lateinit var seekbar: SeekBar
    private lateinit var tx1: TextView
    lateinit var tx2: TextView
    lateinit var tx3: TextView

    var oneTimeOnly = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ics.likeplayer.R.layout.activity_play_mp3_video)

        b1 =  findViewById(com.ics.likeplayer.R.id.button);
        b2 =  findViewById(com.ics.likeplayer.R.id.button2);
        b3 = findViewById(com.ics.likeplayer.R.id.button3);
        b4 = findViewById(com.ics.likeplayer.R.id.button4);
        iv = findViewById(com.ics.likeplayer.R.id.imageView);

        tx1 = findViewById(com.ics.likeplayer.R.id.textView2);
        tx2 = findViewById(com.ics.likeplayer.R.id.textView3);
        tx3 = findViewById(com.ics.likeplayer.R.id.textView4);
        tx3.setText("Song.mp3");

        mediaPlayer = MediaPlayer.create(this, intent.getStringExtra("mp3url").toUri());
        seekbar = findViewById(com.ics.likeplayer.R.id.seekBar);
        seekbar.setClickable(false);
        b2.setEnabled(false);
        mediaPlayer.start();
        b3.setOnClickListener(View.OnClickListener {

            Toast.makeText(getApplicationContext(), "Playing sound",Toast.LENGTH_SHORT).show();
            mediaPlayer.start()
            finalTime = mediaPlayer.duration.toDouble()
            startTime = mediaPlayer.currentPosition.toDouble()

            if (oneTimeOnly == 0) {
                seekbar.setMax(finalTime.toInt());
                oneTimeOnly = 1;
            }

            tx2.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(finalTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(
                            finalTime.toLong()
                        )))
            );

            tx1.setText(String.run {
                format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                        TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(
                                    startTime.toLong()
                                )))
            }
            );

            seekbar.setProgress(startTime.toInt());
         //   myHandler.postDelayed(UpdateSongTime,100);
            b2.setEnabled(true);
            b3.setEnabled(false);
        })

//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Pausing 
//                        sound",Toast.LENGTH_SHORT).show();
//                        mediaPlayer.pause();
//                b2.setEnabled(false);
//                b3.setEnabled(true);
//            }
//        });
        
    }
}
