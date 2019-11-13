package com.ics.likeplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ics.likeplayer.FurtherActivity.AllMp3Activities;
import com.ics.likeplayer.FurtherActivity.AllVideoActivity;
import com.ics.likeplayer.Model.DIrectories_Folders;
import com.ics.likeplayer.Model.DIrectories_Mp3_Folders;
import com.ics.likeplayer.Model.PojoClass;
import com.ics.likeplayer.Model.PojoClass1;
import com.ics.likeplayer.R;


import java.util.ArrayList;


/**
 * Created by kuldeep on 13/02/18.
 */

public class Mp3OnlyAdapter extends RecyclerView.Adapter<Mp3OnlyAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<DIrectories_Mp3_Folders> pojoClassArrayList;

    public Mp3OnlyAdapter(Context context, ArrayList<DIrectories_Mp3_Folders> pojoClassArrayList) {
        this.context = context;
        this.pojoClassArrayList = pojoClassArrayList;
    }
    @Override
    public Mp3OnlyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.directories, parent, false);

        return new Mp3OnlyAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Mp3OnlyAdapter.ViewHolder holder, int position) {
        Log.e("Dire namne is",""+pojoClassArrayList.get(position).getName());
        holder.Dir_Name.setText(pojoClassArrayList.get(position).getName()+""+pojoClassArrayList.get(position).getNo_of_SOngs());
        holder.Dir_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , AllMp3Activities.class);
                intent.putExtra("dirpath" , ""+pojoClassArrayList.get(position).getBaseAddress());
                intent.putExtra("nofsongs" , ""+pojoClassArrayList.get(position).getNo_of_SOngs());
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

        TextView Dir_Name,singer_name,time;

        public ViewHolder(View view) {
            super(view);

            Dir_Name =  view.findViewById(R.id.dir_name);
//            singer_name =  view.findViewById(R.id.singer_name);
//            time =  view.findViewById(R.id.time);
        }
    }
}
