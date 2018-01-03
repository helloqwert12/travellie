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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dataobject.INTERACTION_TYPE;
import dataobject.Like;
import dataobject.Notification;
import dataobject.Post;
import dataobject.UserInfo;

/**
 * Created by tranminhquan on 11/15/2017.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.RecyclerViewHolder>{

    private List<Post> listPost = new ArrayList<>();
    private Context context;
    private DatabaseReference mDatabase;
    private UserInfo userInfo;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;


    public PostRecyclerAdapter(Context context, List<Post> lstPost){
        this.context = context;
        this.listPost = lstPost;

        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
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
//        userinfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                userInfo = dataSnapshot.getValue(UserInfo.class);
//                Picasso.with(context).load(userInfo.getAvatarLink()).into(viewHolder.roundedImageAvatar);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        Uri link = Uri.parse(listPost.get(position).getAvatarLink());
        Picasso.with(context).load(link).into(viewHolder.roundedImageAvatar);
        viewHolder.tvUseranme.setText(listPost.get(position).getUsername());
        viewHolder.tvContent.setText(listPost.get(position).getContent());
        viewHolder.tvContent.setText(listPost.get(position).getContent());

        //Chuyển đổi timstamp sang format của date
        Date date = new Date(listPost.get(position).getTimestamp());
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateFormatted = formatter.format(date);
        viewHolder.tvTimestamp.setText(dateFormatted);

        viewHolder.imgbtnLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(context, "You like post " + listPost.get(position).getContent(), Toast.LENGTH_SHORT).show();
                String likeid = mDatabase.child("interactions/likes").child(listPost.get(position).getPostid()).push().getKey();
                Like newLike = new Like();
                newLike.setLikeid(likeid);
                newLike.setPostid(listPost.get(position).getPostid());
                newLike.setTimestamp(Calendar.getInstance().getTimeInMillis());
                newLike.setUserid(currentUser.getUid());
                newLike.setUsername(currentUser.getDisplayName());

                //tăng like
//                DatabaseReference postRef = mDatabase.child("posts").child(listPost.get(position).getUserid())
//                        .child(listPost.get(position).getPostid()).runTransaction(new Transaction.Handler() {
//                            @Override
//                            public Transaction.Result doTransaction(MutableData mutableData) {
//
//                                if (mutableData.getValue() != null){
//                                    Post p = mutableData.getValue(Post.class);
//                                }
//
//                                //return Transaction.success(mutableData);
//                            }
//
//                            @Override
//                            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
//
//                            }
//                        });

                //Push to database
                mDatabase.child("interactions/likes").child(listPost.get(position).getPostid()).child(likeid).setValue(newLike);
                // Đồng thời đầy sang notifycations nếu là người khác like

                // ++Kiểm tra xem có tự sướng không?
                //if (currentUser.getUid().equals(listPost.get(position).getUserid()))
                //    return;

                // ++Nếu ko thì thêm vào notification của người tạo post
                Notification newNoti = new Notification();
                newNoti.setSenderName(currentUser.getDisplayName());
                newNoti.setType(INTERACTION_TYPE.LIKE);
                newNoti.setPostid(listPost.get(position).getPostid());
                String nid = mDatabase.child("notifications").child(listPost.get(position).getUserid()).push().getKey();
                newNoti.setId(nid);

                // Push to notification
                mDatabase.child("notifications").child(listPost.get(position).getUserid()).child(nid).setValue(newNoti);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(context, "You unliked post " + listPost.get(position).getContent(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.imgbtnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--TO DO: Popup comment

            }
        });
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
        ImageButton imgbtnCmt;
        ImageButton imgbtnShare;
        LikeButton imgbtnLike;


        public RecyclerViewHolder(final View itemView) {
            super(itemView);

            //match components
            roundedImageAvatar = itemView.findViewById(R.id.roundImageAvatar);
            tvUseranme = itemView.findViewById(R.id.tvUsername);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvContent = itemView.findViewById(R.id.tvContent);
            imgbtnCmt = itemView.findViewById(R.id.imgbtnCmt);
            imgbtnShare = itemView.findViewById(R.id.imgbtnShare);
            imgbtnLike = itemView.findViewById(R.id.imgbtnLike);


//            tvLink = itemView.findViewById(R.id.tvLink);
//            fimgvwPhoto = itemView.findViewById(R.id.fimgvwPhoto);
        }

    }
}

