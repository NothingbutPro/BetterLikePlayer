package com.ics.likeplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.ics.likeplayer.FurtherActivity.AllMp3Activities;
import com.ics.likeplayer.FurtherActivity.PlayMp3VideoActivity;
import com.ics.likeplayer.FurtherActivity.PlayVideoActivity;
import com.ics.likeplayer.Model.AllVideos;
import com.ics.likeplayer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AllMp3Adapters extends RecyclerView.Adapter<AllMp3Adapters.ViewHolder>  {
    File file;
    private Context context;
    private ArrayList<AllVideos> pojoClassArrayList;

    public AllMp3Adapters(Context context, ArrayList<AllVideos> pojoClassArrayList) {
        this.context = context;
        this.pojoClassArrayList = pojoClassArrayList;
    }
    @Override
    public AllMp3Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allvideos, parent, false);
//        file = new File("xyz");
        return new AllMp3Adapters.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllMp3Adapters.ViewHolder holder, int position) {
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
                Intent intent = new Intent(v.getContext() , PlayMp3VideoActivity.class);
                intent.putExtra("mp3url" , ""+pojoClassArrayList.get(position).getPath());
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
