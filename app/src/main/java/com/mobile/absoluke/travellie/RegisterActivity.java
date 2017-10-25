package com.mobile.absoluke.travellie;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

import dataobject.UserInfo;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_FILE = 2;
    //Debug
    String TAG = "RegisterActivity";

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
        //userInfo.setUserid(uid);
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
                if (hasNull()){
                    Log.e(TAG, "Component has null value");
                    Toast.makeText(RegisterActivity.this, R.string.has_null_value, Toast.LENGTH_SHORT).show();
                }
                else{
                    //Push data to database
                }
            }
        });

        imgbtnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[] {android.Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            }
        });

        cimgvwChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent()
                        .addCategory(Intent.CATEGORY_OPENABLE)
                        .setType("image/*")
                        .setAction(Intent.ACTION_OPEN_DOCUMENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), REQUEST_CODE_FILE);
            }
        });
    }

    // Kiểm tra việc người dùng có cho phép mở Camera trên Android 6.0 hay không
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        } else {
            Toast.makeText(this, "Bạn không cho phép mở Camera",Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // Lấy hình vừa chụp được gán vào
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            cimgvwChangeAvatar.setImageBitmap(bitmap);
        }
        else if(requestCode == REQUEST_CODE_FILE && resultCode == RESULT_OK && data != null) {
            Uri selectedfile = data.getData(); //The uri with the location of the file
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cimgvwChangeAvatar.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean hasNull(){
        if (etFirstName.getText() == null) return true;
        if (etLastName.getText() == null) return true;
        if (etDayOfBirth.getText() == null) return true;
        //Check gender spinner here!

        return false;
    }
}
