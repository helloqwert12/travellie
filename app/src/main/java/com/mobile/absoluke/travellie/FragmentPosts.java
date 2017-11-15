package com.mobile.absoluke.travellie;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dataobject.POST_TYPE;
import dataobject.Post;

/**
 * Created by Yul Lucia on 11/07/2017.
 * Modified by Quan Tran Minh on 15/11/2017
 */

public class FragmentPosts extends Fragment {

//    RecyclerView recyvwPosts;
//    PostRecyclerAdapter adapter;
//    RecyclerView.LayoutManager layoutManager;
//    List<Post> listPost;    //test

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
//        recyvwPosts = rootView.findViewById(R.id.recyvwPosts);
//
//        initRecyclerView();
        return rootView;
    }


//    public void initRecyclerView(){
//        listPost = new ArrayList<>();
//
//        //[Test] set up list post
//        Post post1 = new Post("Demo content 1", null, "userid1", "123", null, null, null, null, null, POST_TYPE.NEWFEEDS);
//        Post post2 = new Post("Demo content 2", null, "userid2", "456", null, null, null, null, null, POST_TYPE.NEWFEEDS);
//        Post post3 = new Post("Demo content 3", null, "userid3", "789", null, null, null, null, null, POST_TYPE.NEWFEEDS);
//        Post post4 = new Post("Demo content 4", null, "userid4", "112", null, null, null, null, null, POST_TYPE.NEWFEEDS);
//        Post post5 = new Post("Demo content 5", null, "userid5", "223", null, null, null, null, null, POST_TYPE.NEWFEEDS);
//
//        listPost.add(post1);
//        listPost.add(post2);
//        listPost.add(post3);
//        listPost.add(post4);
//
//        recyvwPosts.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(getContext());
//        recyvwPosts.setLayoutManager(layoutManager);
//
//        //setting the adapter
//        adapter = new PostRecyclerAdapter(listPost);
//        recyvwPosts.setAdapter(adapter);
//    }
}
