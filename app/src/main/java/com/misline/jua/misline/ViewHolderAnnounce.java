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

public class ViewHolderAnnounce extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolderAnnounce(@NonNull View itemView) {
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


    public void setDetails (Context ctx, String name, String title, String userimage, String announcephoto, String description, String date){
        TextView mAnnounceNameView = mView.findViewById(R.id.announceTitle);
        TextView mNoterNameView = mView.findViewById(R.id.notername);
        CircleImageView mUserImage = mView.findViewById(R.id.userimage);
        ImageView mAnnounceImage = mView.findViewById(R.id.announceImage);
        TextView mDate = mView.findViewById(R.id.date);
        Glide.with(ctx.getApplicationContext()).load(userimage)
                .apply(new RequestOptions())
                .into(mUserImage);
        mAnnounceNameView.setText(title);
        mNoterNameView.setText(name);
        Glide.with(ctx.getApplicationContext()).load(announcephoto)
                .apply(new RequestOptions())
                .into(mAnnounceImage);
        mDate.setText(date);



    }

    private ViewHolderAnnounce.ClickListener mClickListener;


    //interface to send callbacks

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolderAnnounce.ClickListener clickListener){

        mClickListener = clickListener;

    }
}
