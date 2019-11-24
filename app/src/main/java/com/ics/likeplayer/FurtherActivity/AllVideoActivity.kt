package com.ics.likeplayer.FurtherActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ics.likeplayer.Adapter.AllDirectoriesAdapter
import com.ics.likeplayer.Adapter.MyAllVideosAdpter
import com.ics.likeplayer.MainActivity
import com.ics.likeplayer.Model.AllVideos
import com.ics.likeplayer.Model.DIrectories_Folders
import com.ics.likeplayer.R
import java.io.File
import java.util.ArrayList


class AllVideoActivity : AppCompatActivity() {
    private var myAllVideosAdpter: MyAllVideosAdpter? =null
    lateinit var RootDirname :String
     lateinit var allvideorec : RecyclerView
     lateinit var sectionvid : TextView
     lateinit var backbtns : ImageView
//     lateinit var File : File
    var AllVideosList : ArrayList<AllVideos> = ArrayList()
//    private var MyAllVideosAdpter: myAllVideosAdpter = null
//     lateinit var File : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("Created" , "AllVideoActivity.")
        setContentView(R.layout.activity_all_video)
        allvideorec = findViewById(R.id.allvideorec);
        sectionvid = findViewById(R.id.sectionvid);
        backbtns = findViewById(R.id.backbtns);
        RootDirname = intent.getStringExtra("dirpath")
        sectionvid.setText(intent.getStringExtra("sectionvid"))
        backbtns.setOnClickListener {
            onBackPressed()
        }
        if(!RootDirname.isBlank() || !RootDirname.isEmpty())
        {
//            Toast.makeText(this , "Directory Name"+RootDirname,Toast.LENGTH_LONG).show()
//            File = Uri.fromFile(File(RootDirname.toUri())
            val File = File(RootDirname);
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

                    if(namepath !=null && (namepath.endsWith(".mp4") ||namepath.endsWith(".mkv")
                                ||namepath.endsWith(".m4v") ||namepath.endsWith(".avi") ||namepath.endsWith(".mov") ||
                                namepath.endsWith(".3gp") ||namepath.endsWith(".flv") ||namepath.endsWith(".wmv") ||
                                namepath.endsWith(".rmvb") ||namepath.endsWith(".ts")||namepath.endsWith(".webm")))
                    {
                        AllVideosList.add(AllVideos(it.name,"","" ,it.absolutePath))
                    }
                }
                myAllVideosAdpter = MyAllVideosAdpter(this, AllVideosList)
                //  mLayoutManager = LinearLayoutManager(context)
                allvideorec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                //    songrec.setLayoutManager(mLayoutManager)
                allvideorec.setItemAnimator(DefaultItemAnimator())
                allvideorec.setAdapter(myAllVideosAdpter)
            }else{
                Toast.makeText(this , "No it's not", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this , "Invalid Directory",Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
         intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
        this.finish()

        super.onBackPressed()
    }

    override fun onStart() {
        Log.e("Started" , "AllVideoActivity.")


        super.onStart()
    }
}
