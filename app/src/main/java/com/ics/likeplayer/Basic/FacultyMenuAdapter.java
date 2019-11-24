package com.ics.likeplayer.Basic;

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

public class FacultyMenuAdapter extends RecyclerView.Adapter<FacultyMenuAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DIrectories_Folders> pojoClassArrayList;

    public FacultyMenuAdapter(Context context, ArrayList<DIrectories_Folders> pojoClassArrayList) {
        this.context = context;
        this.pojoClassArrayList = pojoClassArrayList;
    }

    @Override
    public FacultyMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.directories, parent, false);

        return new FacultyMenuAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FacultyMenuAdapter.ViewHolder holder, int position) {
        Log.e("Dire namne is", "" + pojoClassArrayList.get(position).getName());
        holder.Dir_Name.setText(pojoClassArrayList.get(position).getName());
        holder.Dir_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AllVideoActivity.class);
                intent.putExtra("dirpath", "" + pojoClassArrayList.get(position).getBaseAddress());
                intent.putExtra("nofsongs", "" + pojoClassArrayList.get(position).getNo_of_SOngs());
                v.getContext().startActivity(intent);
            }
        });
//        holder.singer_name.setText(pojoClassArrayList.get(position).getSinger_name());
//        holder.time.setText(pojoClassArrayList.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return pojoClassArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Dir_Name, singer_name, time;

        public ViewHolder(View view) {
            super(view);

            Dir_Name = view.findViewById(R.id.dir_name);
//            singer_name =  view.findViewById(R.id.singer_name);
//            time =  view.findViewById(R.id.time);
        }
    }
}