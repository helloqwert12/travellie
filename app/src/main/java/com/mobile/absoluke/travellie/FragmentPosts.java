package com.mobile.absoluke.travellie;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dataobject.POST_TYPE;
import dataobject.Post;
import tool.Tool;

/**
 * Created by Yul Lucia on 11/07/2017.
 * Modified by Quan Tran Minh on 15/11/2017
 */

public class FragmentPosts extends Fragment {

    //List Post
    RecyclerView recyvwPosts;
    PostRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Post> listPost;    //test

    //Component
    Button imgbtnPost;
    Button imgbtnChoosePic;
    EditText etEditPost;
    Spinner spnPostType;
    FloatingActionButton fbtnAddPost;

    //Firebase
    FirebaseAuth auth;
    FirebaseUser currentUser;
    DatabaseReference mDatabase, postUserRef;
    FirebaseStorage storage;
    StorageReference storageRef;

    POST_TYPE postTypeChoice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);

        //Match Components
        matchComponent(rootView);
        setEventComponents();
        initFirebase();
        initRecyclerView();
        return rootView;
    }

    public void initFirebase(){
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        postUserRef = mDatabase.child("interactions/posts").child(currentUser.getUid());
        storage = FirebaseStorage.getInstance("gs://travellie-5884f.appspot.com");
        storageRef = storage.getReference();

        //set listener for postUserRef
        postUserRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post post = dataSnapshot.getValue(Post.class);
                DatabaseReference postRef = mDatabase.child("newsfeed");
                //Check type of post
                switch (post.getType()){
                    case FOOD:
                        postRef = postRef.child("food");
                        break;
                    case HOTEL:
                        postRef = postRef.child("hotel");
                        break;
                    case ENTERTAINMENT:
                        postRef = postRef.child("entertainment");
                        break;
                }

                //push to type of post
                postRef.push().setValue(post);

                //push to general
                mDatabase.child("newsfeed/general").push().setValue(post);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void initRecyclerView(){
        listPost = new ArrayList<>();

        //[Test] set up list post
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
//        listPost.add(post5);

        recyvwPosts.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyvwPosts.setLayoutManager(layoutManager);
        recyvwPosts.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

            }
        });

        //setting the adapter
        adapter = new PostRecyclerAdapter(listPost);
        recyvwPosts.setAdapter(adapter);
    }

    public void matchComponent(View rootView){
        recyvwPosts = rootView.findViewById(R.id.recyvwPosts);
//        imgbtnPost = rootView.findViewById(R.id.imgbtnPost);
//        imgbtnChoosePic = rootView.findViewById(R.id.imgbtnChoosePic);
//        etEditPost = rootView.findViewById(R.id.etEditPost);
//        spnPostType = rootView.findViewById(R.id.spnPostType);
        fbtnAddPost = rootView.findViewById(R.id.fbtnAddPost);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.post_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
//        spnPostType.setAdapter(adapter);
//        spnPostType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                switch (i){
//                    case 0:
//                        postTypeChoice = POST_TYPE.ENTERTAINMENT;
//                        break;
//                    case 1:
//                        postTypeChoice = POST_TYPE.HOTEL;
//                        break;
//                    case 2:
//                        postTypeChoice = POST_TYPE.FOOD;
//                        break;
//                    case 3:
//                        postTypeChoice = POST_TYPE.GENERAL;
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    public void setEventComponents(){
//        imgbtnChoosePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

//        imgbtnPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (etEditPost.getText().toString().replace(" ","").length() == 0){
//                    Toast.makeText(view.getContext(), R.string.empty_content, Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    //Push to firebase database
//                    //-TO DO: handle images, links, tags
//
//                    Post newPost = new Post(etEditPost.getText().toString(), null,
//                            currentUser.getUid(), Calendar.getInstance().getTimeInMillis(),
//                            null, null, null, null, null, postTypeChoice);
//                    postUserRef.push().setValue(newPost);
//
//                    Toast.makeText(getContext(), R.string.update_post_success, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
        fbtnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tool.changeActivity(getContext(), AddPostActivity.class);
            }
        });
    }
}
