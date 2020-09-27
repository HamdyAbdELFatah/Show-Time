package com.hamdy.showtime.ui.ui.home.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.hamdy.showtime.R;
import com.hamdy.showtime.ui.model.Teacher;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.Holder> {
    public TeacherAdapter(Context context, List<Teacher> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    Context context;
    List<Teacher> dataList;
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int pxWidth = displayMetrics.widthPixels;
        float width = pxWidth / displayMetrics.density;
        ViewGroup.LayoutParams layoutParams = holder.trendContainer.getLayoutParams();
        layoutParams.width = (int)( width/0.9);
        layoutParams.height = (int)( width/0.9);
        holder.trendContainer.setLayoutParams(layoutParams);
        holder.imageMovieTrend.setImageResource(R.drawable.photo);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    static class Holder extends RecyclerView.ViewHolder{
        ImageView imageMovieTrend;
        ConstraintLayout trendContainer;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageMovieTrend=itemView.findViewById(R.id.imageMovieTrend);
            trendContainer=itemView.findViewById(R.id.trendContainer);
        }
    }

}
