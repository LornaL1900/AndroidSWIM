package com.example.androidswim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<String> wavestrings;
    Context context;

//    public MyAdapter(Context ct, ArrayList<Integer> ws, ArrayList<Integer> freqs, ArrayList<Double> phases,  ArrayList<Double> amps){
    public MyAdapter(Context ct, ArrayList<String> wss){
        context = ct;
        wavestrings = wss;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_wavestring.setText(wavestrings.get(position));
//        holder.mainLayout.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, AddHarmonicActivity.class);
//                intent.putExtra("wt", wts.get(position));
////                intent.putExtra("description", descriptions.get(position));
////                intent.putExtra("pos", position);
//                context.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return wavestrings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_wavestring;
        ConstraintLayout mainLayout;


        public MyViewHolder(View itemView){
            super(itemView);
            tv_wavestring = itemView.findViewById(R.id.tv_wavestring);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }


}


