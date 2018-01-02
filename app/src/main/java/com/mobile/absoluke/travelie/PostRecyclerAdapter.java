package com.mobile.absoluke.travelie;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dataobject.Post;
import dataobject.UserInfo;

/**
 * Created by tranminhquan on 11/15/2017.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.RecyclerViewHolder>{

    private List<Post> listPost = new ArrayList<>();
    private Context context;
    private DatabaseReference userinfoRef;
    private UserInfo userInfo;


    public PostRecyclerAdapter(Context context ,DatabaseReference userinfoRef, List<Post> lstPost){
        this.userinfoRef = userinfoRef;
        this.context = context;
        this.listPost = lstPost;
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.feed_item, viewGroup, false);
        return new RecyclerViewHolder(itemView);
    }

    // Dùng để bind dữ liệu từ dataobject Post vào components feed_item
    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, final int position) {
        userinfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo = dataSnapshot.getValue(UserInfo.class);
                Picasso.with(context).load(userInfo.getAvatarLink()).into(viewHolder.roundedImageAvatar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        viewHolder.tvUseranme.setText(listPost.get(position).getUsername());
        viewHolder.tvContent.setText(listPost.get(position).getContent());
        viewHolder.tvContent.setText(listPost.get(position).getContent());

        //Chuyển đổi timstamp sang format của date
        Date date = new Date(listPost.get(position).getTimestamp());
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateFormatted = formatter.format(date);
        viewHolder.tvTimestamp.setText(dateFormatted);
    }


    public void addItem(int position, Post post){
        listPost.add(position, post);
        notifyItemInserted(position);
    }

    public void updateList(List<Post> lstPost){
        this.listPost = lstPost;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        //Declare components
        ImageView roundedImageAvatar; //--TODO: change to NetworkImageView
        TextView tvUseranme;
        TextView tvTimestamp;
        TextView tvContent;
        //TextView tvLink;
        //ImageView imgvwPhoto; //--TODO: change to FeedImageView
//        ImageButton imgbtnCmt;
//        ImageButton imgbtnShare;
//        LikeButton imgbtnLike;


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            //match components
            roundedImageAvatar = itemView.findViewById(R.id.roundImageAvatar);
            tvUseranme = itemView.findViewById(R.id.tvUsername);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvContent = itemView.findViewById(R.id.tvContent);
//            imgbtnCmt = itemView.findViewById(R.id.imgbtnCmt);
//            imgbtnShare = itemView.findViewById(R.id.imgbtnShare);
//            imgbtnLike = itemView.findViewById(R.id.imgbtnLike);
//            tvLink = itemView.findViewById(R.id.tvLink);
//            fimgvwPhoto = itemView.findViewById(R.id.fimgvwPhoto);
        }

    }
}

