package com.mobile.absoluke.travelie;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.haresh.multipleimagepickerlibrary.MultiImageSelector;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dataobject.UserInfo;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/*
* TO-DO:
* Them chuc nang Get Location       -----   Kiet
* Them chuc nang chon anh tu may    -----   Kiet
* Tham chuc nang Post               -----   Quan
* Xu ly rating                      -----   Quan
* */

public class AddPostActivity extends AppCompatActivity {
    static final String TAG = "AddPostActivity";
    private static final int REQUEST_CODE_FILE_PICTURE = 1;
    private static final int REQUEST_LOCATION = 2;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 401;
    private final int MAX_IMAGE_SELECTION_LIMIT = 10;
    private final int REQUEST_IMAGE = 301;
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
    //Location GPS
    LocationManager locationManager;
    Geocoder geocoder;
    List<Address> addresses;
    String[] infoLocation = new String[3];
    String info = "";
    private RecyclerView recyclerViewImages;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<String> mSelectedImagesList = new ArrayList<>();
    private MultiImageSelector mMultiImageSelector;
    private ImagesAdapter mImagesAdapter;

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
        //btnGetLocation = findViewById(R.id.btnGetLocation);
        btnPost = findViewById(R.id.btnPost);

        recyclerViewImages = findViewById(R.id.recycler_view_images);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewImages.setHasFixedSize(true);
        recyclerViewImages.setLayoutManager(gridLayoutManager);

        mMultiImageSelector = MultiImageSelector.create();

        //Set event
        btnChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkAndRequestPermissions()) {
                    mMultiImageSelector.showCamera(true);
                    mMultiImageSelector.count(MAX_IMAGE_SELECTION_LIMIT);
                    mMultiImageSelector.multi();
                    mMultiImageSelector.origin(mSelectedImagesList);
                    mMultiImageSelector.start(AddPostActivity.this, REQUEST_IMAGE);
                }
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        btnGetLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                    buildAlertMessageNoGps();
//
//                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                    getLocation();
//                }
//            }
//        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            try {
                mSelectedImagesList = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                mImagesAdapter = new ImagesAdapter(this, mSelectedImagesList);
                recyclerViewImages.setAdapter(mImagesAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkAndRequestPermissions() {
        int externalStoragePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (externalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            btnChoosePic.performClick();
        }
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

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(AddPostActivity.this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (AddPostActivity.this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(AddPostActivity.this, new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                getAddress(latti, longi);

            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                getAddress(latti, longi);

            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                getAddress(latti, longi);

            } else {

                Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    protected void getAddress(double latti, double longi) {

        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latti, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            //address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            infoLocation[0] = addresses.get(0).getAdminArea(); //State
            infoLocation[1] = addresses.get(0).getCountryName(); //Country
            infoLocation[2] = addresses.get(0).getFeatureName(); // Only if available else return NULL

            for (int i = 0; i < infoLocation.length; i++) {
                if (infoLocation[i] != null) {
                    info += infoLocation[i] + "\n";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Your current location is:" + "\n" + info, Toast.LENGTH_SHORT).show();
        //textView.setText("Your current location is:" + "\n" + temp);
    }
}
