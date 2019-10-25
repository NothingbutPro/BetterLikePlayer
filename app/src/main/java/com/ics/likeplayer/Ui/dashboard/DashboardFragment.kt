package com.ics.likeplayer.Ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.provider.MediaStore
import android.content.ContentResolver
import android.R
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ics.likeplayer.Adapter.AllDirectoriesAdapter
import com.ics.likeplayer.Adapter.Mp3OnlyAdapter
import com.ics.likeplayer.Model.DIrectories_Folders
import com.ics.likeplayer.Model.DIrectories_Mp3_Folders
import java.io.File
import java.util.ArrayList


class DashboardFragment : Fragment() {

    var DirectoriesList : ArrayList<DIrectories_Mp3_Folders> = ArrayList()
    private var p: Int =0
    private lateinit var dashboardViewModel: DashboardViewModel
    private var Mp3OnlyAdapter: Mp3OnlyAdapter? = null
    private lateinit var mp3rect: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(com.ics.likeplayer.R.layout.fragment_dashboard, container, false)
        mp3rect = root.findViewById(com.ics.likeplayer.R.id.mprect)
//        dashboardViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        readmp3mediafiles(activity)


        return root
    }

    private fun readmp3mediafiles(activity: FragmentActivity?) {
        Toast.makeText(activity,"Called",Toast.LENGTH_LONG).show()

        val contentResolver = context?.getContentResolver()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val music = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val sort = MediaStore.Audio.Media.ALBUM + " ASC"
        val projection = arrayOf(
            MediaStore.Audio.Albums.ALBUM_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA
        )
        val cursor = contentResolver?.query(uri, projection, music, null, sort)

        if (cursor != null) {
            //   Msg.log(String.valueOf(cursor.count))
            Log.e("cusror" , "COunt is"+cursor.count)
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val DIrname = File(path).parentFile.name
                    val DIrAddress = File(path).parentFile.absolutePath
                    val DIrSOngs = File(path).parentFile.listFiles()
                    p  = cursor.position;
//                    DIrnames =DIrname
//                    paths =path
//                    DIrAddresss =DIrAddress
//                    DIrSOngss =DIrSOngs

                    //   val DIrname = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME))
                    val title =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    Toast.makeText(activity,"Title is"+DIrname ,Toast.LENGTH_LONG).show()
                    //for getting directories

                    if (path != null && path!!.endsWith(".mp3")) {
                        if(p ==0)
                        {
                            val   dir = DIrectories_Mp3_Folders(DIrname ,DIrAddress, DIrSOngs.size.toString());
                            DirectoriesList.add(cursor.position ,dir)
                            Log.e("homefrag" , ""+DIrSOngs.get(cursor.position))
                        }
                        else{
                            Log.e("homefrag else" , ""+DIrSOngs.get(cursor.position))
                        }
//                        DIrSOngs.forEach {
//                            if(it.exists()) {
//                                if(p ==0 && DIrname.equals(it.name))
//                                Toast.makeText(activity, "got a song" +it.name, Toast.LENGTH_LONG).show();
//                                song_name.set(0, title)
//                                singer_name.set(0, DIrname)
//                                time.set(0, songDuration)
//                                sname.setText(title)
//                                arttist.setText(artist)

//                                p == 1;
//                            }
//                        }

                    }else{
                        Log.e("homefrag else else wa" , ""+DIrSOngs.get(cursor.position))
                    }

                }
                Mp3OnlyAdapter = Mp3OnlyAdapter(activity, DirectoriesList)
                //  mLayoutManager = LinearLayoutManager(context)
                mp3rect.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                //    songrec.setLayoutManager(mLayoutManager)
                mp3rect.setItemAnimator(DefaultItemAnimator())
                mp3rect.setAdapter(Mp3OnlyAdapter)
            }

            else{

                Toast.makeText(activity,"Title is NOne",Toast.LENGTH_LONG).show()

            }
        }else{
            Toast.makeText(activity,"Cursor is  is NOne",Toast.LENGTH_LONG).show()
        }
    }
}