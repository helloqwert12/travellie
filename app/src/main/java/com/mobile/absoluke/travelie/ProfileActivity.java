package com.mobile.absoluke.travelie;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
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

public class ProfileActivity extends AppCompatActivity {
    static final String TAG = "ProfileActivity";

    TabLayout tabLayout;

    //Components
    ImageView imageCover;
    TextView tvUsername;
    RoundedImage roundedImageChangeAvatar;

    //Firebase
    FirebaseAuth auth;
    FirebaseUser currentUser;
    DatabaseReference mDatabase, curUserRef;
    FirebaseStorage storage;
    StorageReference storageRef;

    public UserInfo userInfo;


    private int[] tabIcons = {
            R.drawable.location,
            R.drawable.posts,
            R.drawable.photos,
            R.drawable.about
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        createTabFragment();
        initFirabase();
        matchComponents();
        loadDataFromFirebase();
    }

    private void createTabFragment(){
        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
//        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
//        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default main_toolbar layout
        ab.setDisplayShowTitleEnabled(false); // disable the default title element here

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.containerProfile);
        viewPager.setAdapter(pagerAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        setupTabIcons();

        //Change TabItem icon color
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //for removing the color of first icon when switched to next tab
                tabLayout.getTabAt(0).getIcon().clearColorFilter();
                //for other tabs
                tab.getIcon().clearColorFilter();

            }

            //            Let it empty
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void matchComponents(){
        imageCover = findViewById(R.id.imageCover);
        tvUsername = findViewById(R.id.username);
        roundedImageChangeAvatar = findViewById(R.id.roundImageChangeAvatar);
    }

    private void initFirabase(){
        //Auth
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        //Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Storage
        storage = FirebaseStorage.getInstance("gs://travellie-5884f.appspot.com");
        storageRef = storage.getReference();
    }

    private  void loadDataFromFirebase(){
        curUserRef = mDatabase.child("users_info").child(currentUser.getUid());
        curUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               userInfo = dataSnapshot.getValue(UserInfo.class);
               Log.i(TAG, "Avatar link: " + userInfo.getAvatarLink());
                Log.i(TAG, "User id: " + userInfo.getUserid());

               //Load info to UI
                //-Load avatar and cover
                StorageReference avatarRef = storage.getReferenceFromUrl(userInfo.getAvatarLink());
                Log.i(TAG, "avatarRef: " + avatarRef);

//                Glide.with(ProfileActivity.this)
//                        .using(new FirebaseImageLoader())
//                        .load(avatarRef)
//                        .into(roundedImageChangeAvatar);
                //            .into(cimgvwChangeAvatar);
                Picasso.with(ProfileActivity.this).load(Uri.parse(userInfo.getAvatarLink())).into(roundedImageChangeAvatar);

                StorageReference coverRef = storage.getReferenceFromUrl(userInfo.getCoverLink());
                Log.i(TAG, "coverRef: " + coverRef);

//                Glide.with(ProfileActivity.this)
//                        .using(new FirebaseImageLoader())
//                        .load(coverRef)
//                        .into(imageCover);
                Picasso.with(ProfileActivity.this).load(Uri.parse(userInfo.getCoverLink())).into(imageCover);

                //-Load info
                String fullName = userInfo.getLastname() + " " + userInfo.getFirstname();
                tvUsername.setText(fullName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
