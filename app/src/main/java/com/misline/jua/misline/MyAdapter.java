package com.misline.jua.misline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    RecyclerView  recyclerView;
    Context context;
    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> filelink = new ArrayList<>();
    public void update(String name, String url){
        items.add(name);
        filelink.add(url);
        notifyDataSetChanged();

    }

    public MyAdapter(RecyclerView recyclerView, Context context, ArrayList<String> items, ArrayList<String> filelink) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;
        this.filelink = filelink;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nameofFile.setText(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameofFile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameofFile = itemView.findViewById(R.id.nameofFile);
            itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("IntentReset")
                @Override
                public void onClick(View view) {
                    int position = recyclerView.getChildLayoutPosition(view);
                    Intent intent = new Intent();
                    intent.setType(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(filelink.get(position)));
                    context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });
        }
    }
}
