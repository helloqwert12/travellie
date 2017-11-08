package com.mobile.absoluke.travellie;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaCas;
import android.media.tv.TvInputService;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.*;
import com.facebook.HttpMethod;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dataobject.UserInfo;
import de.hdodenhof.circleimageview.CircleImageView;
import tool.FirebaseStorageTool;

public class RegisterActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_FILE_AVATAR = 2;
    private static final int REQUEST_CODE_FILE_COVER = 3;
    //Debug
    String TAG = "RegisterActivity";

    TextInputEditText etFirstName;
    TextInputEditText etLastName;
    EditText etDayOfBirth;
    Spinner spnGender;
    Button btnUpdate;
    ImageButton imgbtnTakePicture;
    CircleImageView cimgvwChangeAvatar;
    ImageView imageCover;

    //DataObject
    UserInfo userInfo;

    //Intent and bundle
    Intent intent;
    Bundle bundle;
    
    int choice; //choice of gender

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
        intent = getIntent();
        bundle = intent.getBundleExtra("BUNDLE");

        String firstName = Profile.getCurrentProfile().getFirstName();
        String lastName = Profile.getCurrentProfile().getLastName() + " " + Profile.getCurrentProfile().getMiddleName();

        Uri profileUri = Profile.getCurrentProfile().getProfilePictureUri(200,200);
        Log.i(TAG, "Profile uri: " + profileUri);

        etFirstName.setText(firstName);
        etLastName.setText(lastName);

        Picasso.with(RegisterActivity.this).load(profileUri).into(cimgvwChangeAvatar, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Toast.makeText(RegisterActivity.this, "Error when loading image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void matchComponents(){
        //Match components
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etDayOfBirth = findViewById(R.id.etDayOfBirth);

        spnGender = findViewById(R.id.spnGender);

        btnUpdate = findViewById(R.id.btnUpdate);

        imgbtnTakePicture = findViewById(R.id.imgbtnTakePicture);
        cimgvwChangeAvatar = findViewById(R.id.cimgvwChangeAvatar);
        imageCover = findViewById(R.id.imageCover);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spnGender.setAdapter(adapter);
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choice = spnGender.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Set event
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(RegisterActivity.this, "Debug: Button update clicked!", Toast.LENGTH_SHORT).show();
                if (hasNull()){
                    Log.e(TAG, "Component has null value");
                    Toast.makeText(RegisterActivity.this, R.string.has_null_value, Toast.LENGTH_SHORT).show();
                }
                else{
                    String uid = bundle.getString("ID");
                    String phone = bundle.getString("PHONE");
                    String email = bundle.getString("EMAIL");

                    //--TO DO:
                    //Push data to storage and get the link
                    //Assign the link to imageLink
                    //(include avatar and cover)
                    //-Push avatar to storage and get link
                    Uri avaLink = FirebaseStorageTool.uploadFromImageView(cimgvwChangeAvatar, "avatar", uid + "avatar");
                    Uri coverLink = FirebaseStorageTool.uploadFromImageView(imageCover, "cover", uid + "cover");

                    //Push data to database:
                    //Init and set data for UserInfo instance
                    userInfo = new UserInfo();
                    userInfo.setFirstname(etFirstName.getText().toString());
                    userInfo.setLastname(etLastName.getText().toString());
                    userInfo.setEmail(email);
                    userInfo.setAvatarLink(avaLink.toString());
                    userInfo.setPhone(phone);
                    userInfo.setDateofbirth(etDayOfBirth.getText().toString());
                    userInfo.setCoverLink(coverLink.toString());
                    userInfo.setGender(choice);
                    userInfo.setRank("Beginner");
                }
            }
        });

        imgbtnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[] {android.Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA);
            }
        });

        cimgvwChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent()
                        .addCategory(Intent.CATEGORY_OPENABLE)
                        .setType("image/*")
                        .setAction(Intent.ACTION_OPEN_DOCUMENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), REQUEST_CODE_FILE_AVATAR);
            }
        });

        imageCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent()
                        .addCategory(Intent.CATEGORY_OPENABLE)
                        .setType("image/*")
                        .setAction(Intent.ACTION_OPEN_DOCUMENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), REQUEST_CODE_FILE_COVER);
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
        else if(requestCode == REQUEST_CODE_FILE_AVATAR && resultCode == RESULT_OK && data != null) {
            Uri selectedfile = data.getData(); //The uri with the location of the file
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cimgvwChangeAvatar.setImageBitmap(bitmap);
        }
        else if (requestCode == REQUEST_CODE_FILE_COVER && resultCode == RESULT_OK && data != null){
            Uri selectedfile = data.getData(); //The uri with the location of the file
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageCover.setImageBitmap(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //Kiểm tra có components nào null hay không
    private boolean hasNull(){
        if (etFirstName.getText().length() == 0) return true;
        if (etLastName.getText().length() == 0) return true;
        if (etDayOfBirth.getText().length() == 0) return true;
        //Check gender spinner here!
        Log.i(TAG,"Has no null");
        return false;
    }

}
