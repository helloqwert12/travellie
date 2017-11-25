package com.mobile.absoluke.travellie;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import dataobject.UserInfo;

/*
* TO-DO:
* Them chuc nang Get Location       -----   Kiet
* Them chuc nang chon anh tu may    -----   Kiet
* Tham chuc nang Post               -----   Quan
* Xu ly rating                      -----   Quan
* */

public class AddPostActivity extends AppCompatActivity {
    static final String TAG = "AddPostActivity";
    //Components
    RoundedImage roundedImageAvatar;
    TextView tvUsername;
    EditText editText;
    ImageButton btnChoosePic;
    ImageButton btnGetLocation;
    ImageButton btnPost;

    //Firebase
    FirebaseUser currentUser;
    DatabaseReference mDatabase, curUserRef;
    FirebaseStorage storage;
    StorageReference storageRef;

    //Dataobject
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        matchComponents();
        initFirebase();
        updateUI();
    }

    void initFirebase(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        //Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    void matchComponents(){
        roundedImageAvatar = findViewById(R.id.roundImageAvatar);
        tvUsername = findViewById(R.id.tvUsername);
        editText = findViewById(R.id.editText);
        btnChoosePic = findViewById(R.id.btnChoosePic);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        btnPost = findViewById(R.id.btnPost);

        //Set event
        btnChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void updateUI(){
        curUserRef = mDatabase.child("users_info").child(currentUser.getUid());
        curUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo = dataSnapshot.getValue(UserInfo.class);

                //load user info from database to UI
                Picasso.with(AddPostActivity.this).load(Uri.parse(userInfo.getAvatarLink())).into(roundedImageAvatar);
                tvUsername.setText(userInfo.getFirstname() + " " + userInfo.getLastname());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
