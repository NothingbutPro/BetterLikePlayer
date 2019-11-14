package com.ics.likeplayer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ics.likeplayer.FurtherActivity.AllVideoActivity;
import com.ics.likeplayer.Model.DIrectories_Folders;
import com.ics.likeplayer.R;

import java.util.ArrayList;

public class AllDirectoriesAdapter extends RecyclerView.Adapter<AllDirectoriesAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<DIrectories_Folders> pojoClassArrayList;

    public AllDirectoriesAdapter(Context context, ArrayList<DIrectories_Folders> pojoClassArrayList) {
        this.context = context;
        this.pojoClassArrayList = pojoClassArrayList;
    }
    @Override
    public AllDirectoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.directories, parent, false);

        return new AllDirectoriesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllDirectoriesAdapter.ViewHolder holder, int position) {
        Log.e("Dire namne is",""+pojoClassArrayList.get(position).getName());
//        pojoClassArrayList.set(position , )
//        DirectoriesList.set
        holder.Dir_Name.setText(pojoClassArrayList.get(position).getName() +" "+ pojoClassArrayList.get(position).getNo_of_SOngs());
        holder.Dir_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , AllVideoActivity.class);
                intent.putExtra("dirpath" , ""+pojoClassArrayList.get(position).getBaseAddress());
                intent.putExtra("sectionvid" , ""+pojoClassArrayList.get(position).getName());
                intent.putExtra("nofsongs" , ""+pojoClassArrayList.get(position).getNo_of_SOngs());
                v.getContext().startActivity(intent);
                ((Activity)context).finish();
                Log.e("aleardy " , "called");
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
