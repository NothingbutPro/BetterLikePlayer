package com.ics.likeplayer.FurtherActivity;

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ics.likeplayer.Adapter.AllMp3Adapters
import com.ics.likeplayer.Adapter.Mp3OnlyAdapter
import com.ics.likeplayer.Model.AllVideos
import com.ics.likeplayer.R
import java.io.File

public class AllMp3Activities : AppCompatActivity() {
private var AallMp3Adapters: AllMp3Adapters? =null
        lateinit var RootDirname :String
        lateinit var allvideorec : RecyclerView
        lateinit var File : File
        var AllVideosList : ArrayList<AllVideos> = ArrayList()
//    private var MyAllVideosAdpter: myAllVideosAdpter = null
//     lateinit var File : File

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_video)
        allvideorec = findViewById(R.id.allvideorec);
        RootDirname = intent.getStringExtra("dirpath")
        if(!RootDirname.isBlank() || !RootDirname.isEmpty())
        {
        Toast.makeText(this , "Directory Name"+RootDirname,Toast.LENGTH_LONG).show()
//            File = Uri.fromFile(File(RootDirname.toUri())
        File = File(RootDirname);
        if(File.exists())
        {
//               Toast.makeText(this ,"it exxisdt"+File.listFiles().get(0).name,Toast.LENGTH_LONG).show()
//                for (files in File.listFiles())
//                {
//                    Toast.makeText(this ,"it exxisdt"+File.listFiles().get(0).name,Toast.LENGTH_LONG).show()
//                }
        File.listFiles().forEach {
            val namepath = it.absolutePath
        Log.e("path is" , "file:"+File.absolutePath)

        if(namepath !=null && (namepath.endsWith(".mp3")))
        {
        AllVideosList.add(AllVideos(it.name,"","" ,it.absolutePath))
        }
        }
            AallMp3Adapters = AllMp3Adapters(this, AllVideosList)
        //  mLayoutManager = LinearLayoutManager(context)
        allvideorec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //    songrec.setLayoutManager(mLayoutManager)
        allvideorec.setItemAnimator(DefaultItemAnimator())
        allvideorec.setAdapter(AallMp3Adapters)
        }else{
        Toast.makeText(this , "No it's not", Toast.LENGTH_LONG).show()
        }
        }else{
        Toast.makeText(this , "Invalid Directory",Toast.LENGTH_LONG).show()
        }

        }
        }

