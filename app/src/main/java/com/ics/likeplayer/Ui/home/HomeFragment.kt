package com.ics.likeplayer.Ui.home

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ics.likeplayer.Adapter.AllDirectoriesAdapter
import com.ics.likeplayer.Adapter.MyAllVideosAdpter
import com.ics.likeplayer.Model.PojoClass
import com.ics.likeplayer.Model.DIrectories_Folders
import com.ics.likeplayer.R
import java.io.File
import java.util.ArrayList


class HomeFragment : Fragment() {
    private  var paths: String = ""
     var p :Int = 0
    private lateinit var DIrSOngss: Array<File>
    var pojoClassArrayList : ArrayList<PojoClass>  = ArrayList()
    var DirectoriesList : ArrayList<DIrectories_Folders>  = ArrayList()
//    lateinit var DIrSOngss : ArrayList<Files>  = ArrayList()
    lateinit var fileuri : String
    lateinit var prevfileuri : String
    lateinit var filesuri:String
//      var paths:String
      var DIrnames:String =""
    lateinit  var DIrAddresss:String
//    lateinit  var DIrSOngss:String
//    private lateinit var pojoClassArrayList: ArrayList<PojoClass>
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var Songrec: RecyclerView
    private lateinit var Allsdcardrec: RecyclerView
    private lateinit var sname: TextView
    private lateinit var arttist: TextView
    private var myAllVideosAdpter: MyAllVideosAdpter? = null
    private var AllDirectoriesAdapter: AllDirectoriesAdapter? = null
//    public
    //      var mp3Files = null;
    private val song_name = arrayOf(
        "Treat You Better"

    )

    private val singer_name = arrayOf(

        "Shawn Mendis"
    )
    private val time = arrayOf("2:22", "4:21", "2:58", "2:28", "5:39", "3:12")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        Songrec = root.findViewById(R.id.songrec)
        sname = root.findViewById(R.id.sname)
        arttist = root.findViewById(R.id.arttist)
        Allsdcardrec = root.findViewById(R.id.allsdcardrec)
//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })

   //     readmp3mediafiles(activity);
        readmp4mediafiles(activity);
    //    scanDeviceForMp3Files(activity)

        return root
    }

    private fun readmp4mediafiles(activity: FragmentActivity?) {

        val contentResolver = context?.getContentResolver()
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//        val music = MediaStore.Video.Media.DISPLAY_NAME + " != 0"
        val sort = MediaStore.Video.Media.ALBUM + " ASC"
//        val projection = arrayOf(
//            MediaStore.Audio.Albums.ALBUM_ID,
//            MediaStore.Audio.Media.TITLE,
//            MediaStore.Audio.Media.DATA
//        )
        val cursor = contentResolver?.query(uri, null, null, null, sort)
        if (cursor != null) {
            //   Msg.log(String.valueOf(cursor.count))
            Log.e("Video cusror", "COunt is" + cursor.count)
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                    val DIrname = File(path).parentFile.name
                    val DIrAddress = File(path).parentFile.absolutePath
                    val DIrSOngs = File(path).parentFile.listFiles()
                    if(DIrnames.equals(DIrSOngs.get(0).parentFile.name))
                    {

                        p = cursor.position;
                    }
                    else
                    {
                        p=0;
                        DIrnames =DIrname

                    }

                    val title =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    Toast.makeText(activity,"Title is"+DIrname ,Toast.LENGTH_LONG).show()
                    //for getting directories

                    if (path != null && path!!.endsWith(".mp4")) {
                        if(p ==0)
                        {
                            val   dir = DIrectories_Folders(DIrname ,DIrAddress, DIrSOngs.size.toString());
                            DirectoriesList.add(p ,dir)
                       //     Log.e("homefrag" , ""+DIrSOngs.get(cursor.position))
                        }
                        else{
//                            val   dir = DIrectories_Folders(DIrname ,DIrAddress, DIrSOngs.size.toString());
//                            DirectoriesList.add(cursor.position ,dir)
//                            Log.e("homefrag else" , ""+DIrSOngs.get(cursor.position))
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
                        Log.e("homefrag else else wa" , ""+DIrSOngs.get(p))
                    }
                }
                AllDirectoriesAdapter = AllDirectoriesAdapter(activity, DirectoriesList)
                //  mLayoutManager = LinearLayoutManager(context)
                Songrec.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                //    songrec.setLayoutManager(mLayoutManager)
                Songrec.setItemAnimator(DefaultItemAnimator())
                Songrec.setAdapter(AllDirectoriesAdapter)
            }
        }

    }

//    private fun scanDeviceForMp3Files(activity: FragmentActivity?) {
//        filesuri = ".mp3"
//        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
////      +  MediaStore.Audio.Media.DATA + " LIKE '"+filesuri+"/%'"
//        val projection = arrayOf(
//            MediaStore.Audio.Media.TITLE,
//            MediaStore.Audio.Media.ARTIST,
//            MediaStore.Audio.Media.DATA,
//            MediaStore.Audio.Media.DISPLAY_NAME,
//            MediaStore.Audio.Media.DURATION
//        )
//        val sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC"
////         mp3Files = ArrayList()
//
//        var cursor: Cursor? = null
//        try {
//            val uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//            val folderuri = Environment.getExternalStorageDirectory().listFiles().asList()
//            // using extension function walk
////            FolderWithMusic(folderuri)
//            folderuri.forEach {
//                if(it.exists()) {
//                    fileuri = it.canonicalPath
//                    prevfileuri = fileuri;
//
//                    val filesuri = it.listFiles()
//                    try {
//                    if (!filesuri.isEmpty()) {
//                        filesuri.forEach {
//                            println(it.name)
//                            fileuri = prevfileuri + "/" + it.name
//                            File(filesuri.toString()).walk().forEach{
//                                println(it)
//                                if (fileuri != null && fileuri!!.endsWith(".mp3")) {
//                                    Toast.makeText(
//                                        activity,
//                                        "got a song" + fileuri,
//                                        Toast.LENGTH_LONG
//                                    ).show();
////                                    song_name.set(0, title)
////                                    singer_name.set(0, artist)
////                                    time.set(0, songDuration)
////                                    sname.setText(title)
////                                    arttist.setText(artist)
////                        mp3Files.add(path)
//                                }
////
//                            }
////                        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " +
////                                MediaStore.Audio.Media.DATA + " LIKE '"+DOWNLOAD_FILE_DIR+"/%'"
////                            cursor =
////                                activity?.getContentResolver()?.query(
////                                    Uri.parse("file:///storage/emulated/0/.thumbnails/1568102846140.jpg"),
////                                    projection,
////                                    null,
////                                    null,
////                                    sortOrder
////                                )
////                            Log.e("file in last uri", "" + Uri.fromFile(it) +"Cursor "+cursor)
////                            if (cursor != null) {
////                                cursor!!.moveToFirst()
////
////                                while (!cursor!!.isAfterLast()) {
////                                    val title = cursor!!.getString(0)
////                                    val artist = cursor!!.getString(1)
////                                    val path = cursor!!.getString(2)
////                                    val displayName = cursor!!.getString(3)
////                                    val songDuration = cursor!!.getString(4)
////                                    cursor!!.moveToNext()
////                                    if (path != null && path!!.endsWith(".mp3")) {
////                                        Toast.makeText(
////                                            activity,
////                                            "got a song" + title,
////                                            Toast.LENGTH_LONG
////                                        ).show();
////                                        song_name.set(0, title)
////                                        singer_name.set(0, artist)
////                                        time.set(0, songDuration)
////                                        sname.setText(title)
////                                        arttist.setText(artist)
//////                        mp3Files.add(path)
////                                    }
////                                }
////
////
////                                for (i in song_name.indices) {
////
////                                    val pojoClass = PojoClass()
////                                    pojoClass.song_name = song_name[i]
////                                    pojoClass.singer_name = singer_name[i]
////                                    pojoClass.time = time[i]
////                                    Toast.makeText(
////                                        activity,
////                                        "at pojo class a song" + song_name[i],
////                                        Toast.LENGTH_LONG
////                                    ).show();
////
////                                    pojoClassArrayList!!.add(pojoClass)
////                                }
////
////
////                                myAllVideosAdpter = MyAllVideosAdpter(activity, pojoClassArrayList)
////                                //  mLayoutManager = LinearLayoutManager(context)
////                                Songrec.layoutManager = LinearLayoutManager(
////                                    activity,
////                                    LinearLayoutManager.VERTICAL,
////                                    false
////                                )
////                                //    songrec.setLayoutManager(mLayoutManager)
////                                Songrec.setItemAnimator(DefaultItemAnimator())
////                                Songrec.setAdapter(myAllVideosAdpter)
////                                fileuri = ""
////                            }else{
////
////                                fileuri = ""
////                            }
//
//                        }
////                    filesuri.forEach {
////                        Log.e("files" , "are"+it)
////                        println(it)
////                    }
//                        //      Log.e("files" , "are"+filesuri)
//                        //      println(it)
//                        //  Log.e("exists" , "exist"+it.name)
//
////                    println(it)
//                    }
//                    }catch (e: Exception)
//                    {
//                        e.stackTrace
//                    }
//                }
//
//            }
////            File(folderuri.toString()).walk().forEach {
////                println(it)
////                Log.e("files are" , ""+it)
////            }
////            cursor =
////                activity?.getContentResolver()?.query(uri, projection, selection, null, sortOrder)
////            if (cursor != null) {
////                cursor!!.moveToFirst()
////
////                while (!cursor!!.isAfterLast()) {
////                    val title = cursor!!.getString(0)
////                    val artist = cursor!!.getString(1)
////                    val path = cursor!!.getString(2)
////                    val displayName = cursor!!.getString(3)
////                    val songDuration = cursor!!.getString(4)
////                    cursor!!.moveToNext()
////                    if (path != null && path!!.endsWith(".mp3")) {
////                        Toast.makeText(activity,"got a song"+title ,Toast.LENGTH_LONG).show();
////                        song_name.set(0,title)
////                        singer_name.set(0,artist)
////                        time.set(0,songDuration)
////                        sname.setText(title)
////                        arttist.setText(artist)
//////                        mp3Files.add(path)
////                    }
////                }
////
////
////                for (i in song_name.indices) {
////
////                    val pojoClass = PojoClass()
////                    pojoClass.song_name = song_name[i]
////                    pojoClass.singer_name = singer_name[i]
////                    pojoClass.time = time[i]
////                    Toast.makeText(activity,"at pojo class a song"+song_name[i] ,Toast.LENGTH_LONG).show();
////
////                    pojoClassArrayList!!.add(pojoClass)
////                }
////
////
////                myAllVideosAdpter = MyAllVideosAdpter(activity, pojoClassArrayList)
////               //  mLayoutManager = LinearLayoutManager(context)
////                Songrec.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)
////            //    songrec.setLayoutManager(mLayoutManager)
////                Songrec.setItemAnimator(DefaultItemAnimator())
////                Songrec.setAdapter(myAllVideosAdpter)
////
////            }
//
////            // print to see list of mp3 files
////            for (file in mp3Files) {
////                Log.i("TAG", file)
////            }
//
//        } catch (e: Exception) {
//            Log.e("TAG", e.toString())
//        } finally {
//            if (cursor != null) {
//                cursor!!.close()
//            }
//        }
//
//    }

//
//    private fun readmp3mediafiles(activity: FragmentActivity?) {
//        Toast.makeText(activity,"Called",Toast.LENGTH_LONG).show()
//
//        val contentResolver = context?.getContentResolver()
//        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//        val music = MediaStore.Audio.Media.IS_MUSIC + " != 0"
//        val sort = MediaStore.Audio.Media.ALBUM + " ASC"
//        val projection = arrayOf(
//            MediaStore.Audio.Albums.ALBUM_ID,
//            MediaStore.Audio.Media.TITLE,
//            MediaStore.Audio.Media.DATA
//        )
//        val cursor = contentResolver?.query(uri, projection, music, null, sort)
//
//        if (cursor != null) {
//            //   Msg.log(String.valueOf(cursor.count))
//            Log.e("cusror" , "COunt is"+cursor.count)
//            if (cursor.count > 0) {
//                while (cursor.moveToNext()) {
//                    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
//                    val DIrname = File(path).parentFile.name
//                    val DIrAddress = File(path).parentFile.absolutePath
//                    val DIrSOngs = File(path).parentFile.listFiles()
//                     p  = cursor.position;
////                    DIrnames =DIrname
////                    paths =path
////                    DIrAddresss =DIrAddress
////                    DIrSOngss =DIrSOngs
//
//                 //   val DIrname = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME))
//                    val title =
//                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
//                    Toast.makeText(activity,"Title is"+DIrname ,Toast.LENGTH_LONG).show()
//                    //for getting directories
//
//                    if (path != null && path!!.endsWith(".mp3")) {
//                        if(p ==0)
//                        {
//                            val   dir = DIrectories_Folders(DIrname ,DIrAddress, DIrSOngs.size.toString());
//                            DirectoriesList.add(cursor.position ,dir)
//                            Log.e("homefrag" , ""+DIrSOngs.get(cursor.position))
//                        }
//                        else{
//                            Log.e("homefrag else" , ""+DIrSOngs.get(cursor.position))
//                        }
////                        DIrSOngs.forEach {
////                            if(it.exists()) {
////                                if(p ==0 && DIrname.equals(it.name))
////                                Toast.makeText(activity, "got a song" +it.name, Toast.LENGTH_LONG).show();
////                                song_name.set(0, title)
////                                singer_name.set(0, DIrname)
////                                time.set(0, songDuration)
////                                sname.setText(title)
////                                arttist.setText(artist)
//
////                                p == 1;
////                            }
////                        }
//
//                    }else{
//                        Log.e("homefrag else else wa" , ""+DIrSOngs.get(cursor.position))
//                    }
//
//                }
//                AllDirectoriesAdapter = AllDirectoriesAdapter(activity, DirectoriesList)
//                //  mLayoutManager = LinearLayoutManager(context)
//                Songrec.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//                //    songrec.setLayoutManager(mLayoutManager)
//                Songrec.setItemAnimator(DefaultItemAnimator())
//                Songrec.setAdapter(AllDirectoriesAdapter)
//            }
//
//            else{
//
//                Toast.makeText(activity,"Title is NOne",Toast.LENGTH_LONG).show()
//
//            }
//        }else{
//            Toast.makeText(activity,"Cursor is  is NOne",Toast.LENGTH_LONG).show()
//        }
//    }


//    private fun readmediafiles() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//
//    }

}



//private operator fun Nothing?.iterator(): Iterator<String> {
//    return  iterator()
//
//}
