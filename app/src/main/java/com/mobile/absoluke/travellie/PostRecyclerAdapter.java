package com.mobile.absoluke.travellie;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import dataobject.Post;

/**
 * Created by tranminhquan on 11/15/2017.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.RecyclerViewHolder> {

    private List<Post> listPost = new ArrayList<>();

    public PostRecyclerAdapter(List<Post> lstPost){
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

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {

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
        ImageView nimgvwAvatar; //--TO DO: change to NetworkImageView
        TextView tvUseranme;
        TextView tvTimestamp;
        TextView tvContent;
        TextView tvLink;
        ImageView fimgvwPhoto; //--TO DO: change to FeedImageView


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            //match components
            nimgvwAvatar = itemView.findViewById(R.id.nimgvwAvatar);
            tvUseranme = itemView.findViewById(R.id.tvUsername);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvLink = itemView.findViewById(R.id.tvLink);
            fimgvwPhoto = itemView.findViewById(R.id.fimgvwPhoto);
        }

    }
}

