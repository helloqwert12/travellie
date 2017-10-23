package com.mobile.absoluke.travellie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import dataobject.UserInfo;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText etFirstName;
    TextInputEditText etLastName;
    EditText etDayOfBirth;
    Spinner spnGender;
    Button btnUpdate;
    ImageButton imgbtnTakePicture;
    CircleImageView cimgvwChangeAvatar;

    //DataObject
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        matchComponents();

//        Blurry.with(RegisterActivity.this)
//                .radius(10)
//                .sampling(8)
//                .async()
//                .animate(500)
//                .onto(rootView);


        //Get data from previous intent
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");

        //get data
        String uid = bundle.getString("ID");

        //Split name to firstname and lastname
        String[] splits = bundle.getString("NAME").split(" ");
        String firstName = splits[splits.length - 1];
        String lastName="";
        for(int i=0; i<splits.length-1;i++){
            lastName += splits[i] + " ";
        }
        String phone = bundle.getString("PHONE");
        String email = bundle.getString("EMAIL");
        String imageLink = bundle.getString("IMAGE");

        //Init and set data for UserInfo instance
        userInfo = new UserInfo();
        userInfo.setUserid(uid);
        userInfo.setFirstname(firstName);
        userInfo.setLastname(lastName);
        userInfo.setEmail(email);

        //[!!!!!!!] Con thieu setImage va setPhone

        //Display on activity
        etFirstName.setText(firstName);
        etLastName.setText(lastName);

    }

    protected void matchComponents(){
        //Match components
        etFirstName = (TextInputEditText) findViewById(R.id.etFirstName);
        etLastName = (TextInputEditText) findViewById(R.id.etLastName);
        etDayOfBirth = (EditText) findViewById(R.id.etDayOfBirth);

        spnGender = (Spinner) findViewById(R.id.spnGender);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        imgbtnTakePicture = (ImageButton) findViewById(R.id.imgbtnTakePicture);

        cimgvwChangeAvatar = (CircleImageView) findViewById(R.id.cimgvwChangeAvatar);

        //Set event
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgbtnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cimgvwChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
