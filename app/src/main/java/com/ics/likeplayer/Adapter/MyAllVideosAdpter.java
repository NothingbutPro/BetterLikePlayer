package com.ics.likeplayer.Adapter;

import android.content.ContentResolver;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ics.likeplayer.FurtherActivity.PlayVideoActivity;
import com.ics.likeplayer.Model.AllVideos;
import com.ics.likeplayer.Model.PojoClass;
import com.ics.likeplayer.R;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * Created by kuldeep on 13/02/18.
 */

public class MyAllVideosAdpter extends RecyclerView.Adapter<MyAllVideosAdpter.ViewHolder>  {
    File file;
    private Context context;
    private ArrayList<AllVideos> pojoClassArrayList;

    public MyAllVideosAdpter(Context context, ArrayList<AllVideos> pojoClassArrayList) {
        this.context = context;
        this.pojoClassArrayList = pojoClassArrayList;
    }
    @Override
    public MyAllVideosAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allvideos, parent, false);
//        file = new File("xyz");
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyAllVideosAdpter.ViewHolder holder, int position) {
        file = new File(pojoClassArrayList.get(position).getPath());
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//use one of overloaded setDataSource() functions to set your data source
        retriever.setDataSource(context, Uri.fromFile(file));
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInMillisec = Long.parseLong(time );

        retriever.release();
        Bitmap bMap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
        holder.song_name.setText(pojoClassArrayList.get(position).getName());
        holder.vidthm.setImageBitmap(bMap);
        holder.time.setText( String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(timeInMillisec),
                TimeUnit.MILLISECONDS.toSeconds(timeInMillisec) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMillisec))
        ));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , PlayVideoActivity.class);
                intent.putExtra("vidurl" , ""+pojoClassArrayList.get(position).getPath());
                v.getContext().startActivity(intent);
            }
        });
//        holder.singer_name.setText(pojoClassArrayList.get(position).getSinger_name());
//        holder.time.setText(pojoClassArrayList.get(position).getTime());

    }

    @Override
    public int getItemCount()   {
        return pojoClassArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView song_name,singer_name,time;
        ImageView vidthm;

        public ViewHolder(View view) {
            super(view);

            song_name =  view.findViewById(R.id.song_name);
            vidthm =  view.findViewById(R.id.vidthm);
            time =  view.findViewById(R.id.time);

        }
    }
}
