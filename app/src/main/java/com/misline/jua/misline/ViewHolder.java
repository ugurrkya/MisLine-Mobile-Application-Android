package com.misline.jua.misline;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;


        //item click

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });


        //item long click

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });

    }


    public void setDetails (Context ctx, String name, String title, String userimage){
        TextView mShareNameView = mView.findViewById(R.id.shareTitle);
        TextView mNoterNameView = mView.findViewById(R.id.notername);
        CircleImageView mUserImage = mView.findViewById(R.id.userimage);

        Glide.with(ctx.getApplicationContext()).load(userimage)
                .apply(new RequestOptions())
                .into(mUserImage);
        mShareNameView.setText(title);
        mNoterNameView.setText(name);




    }

    private ViewHolder.ClickListener mClickListener;


    //interface to send callbacks

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){

        mClickListener = clickListener;

    }
}
