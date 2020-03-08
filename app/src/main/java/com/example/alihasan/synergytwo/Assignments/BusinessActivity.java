package com.example.alihasan.synergytwo.Assignments;

import com.example.alihasan.synergytwo.Adapters.RecyclerViewAdapter;
import com.example.alihasan.synergytwo.ClassHelper.PhotoHelper;
import com.example.alihasan.synergytwo.CounterSingleton;
import com.example.alihasan.synergytwo.Database.AssignmentDatabase;
import com.example.alihasan.synergytwo.Database.Business;
import com.example.alihasan.synergytwo.Database.BusinessDao;
//import com.example.alihasan.synergytwo.Database.BusinessRepo;
//import com.example.alihasan.synergytwo.Database.BusinessViewModel;
import com.example.alihasan.synergytwo.Database.BusinessViewModel;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageParam;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageViewModel;
import com.example.alihasan.synergytwo.Database.InUploadDatabase.InUplaod;
import com.example.alihasan.synergytwo.Database.InUploadDatabase.InUploadViewModel;
import com.example.alihasan.synergytwo.R;
import com.example.alihasan.synergytwo.api.service.AppLocationService;
import com.example.alihasan.synergytwo.api.service.Client;
import com.example.alihasan.synergytwo.api.service.ServerURL;
import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BusinessActivity extends AppCompatActivity implements Listener {

    /**
     * DD test
     */
    private BusinessViewModel businessViewModel;
    private ImageViewModel    imageViewModel   ;
    private InUploadViewModel inUploadViewModel;
    /**
     *
     */

//    static String SERVER_URL = new ServerURL().getSERVER_URL();

    EditText address;

    /**
     * 29 ELEMENTS
     * 18 EDITTEXTS
     * 11 SPINNER
     */

    EditText caseNo;

    TextView personName,caseType,bankType;

    EditText applName, contactNo, compName,
            businessNature, designation, workingSince,
            personContacted, desigPersonMet, empNo,
            landmark, branchNo, yearsPresentAdd,
            conVer1, conVer2, addProofDetail,remark;


    Spinner pdaNoSpinner, easeLocSpinner, offOwnershipSpinner,
            localityTypeSpinner, businessSetupSpinner, businessBoardSpinner,
            visCardSpinner,applVeriFrom, conVeriFeed,
            polLinkSpinner, overallStatusSpinner,reasonNegativeSpinner;

    /**
     * EDITTEXT AND SPINNER
     * STRING VALUES
     */

    String sapplName,saddress, scontactNo, scompName,
            sbusinessNature, sdesignation, sworkingSince,
            spersonContacted, sdesigContacted, sempNo,
            slandmark, sbranchNo, syearsPresentAdd,
            sconVer1, sconVer2, saddProofDetail,sremark;

    String spdaNoSpinner, seaseLocSpinner, soffOwnershipSpinner,
            slocalityTypeSpinner, sbusinessSetupSpinner, sbusinessBoardSpinner,
            svisCardSpinner,sapplVeriFrom, sconVeriFeed,
            spolLinkSpinner, soverallStatusSpinner,sreasonNegativeSpinner;
    /**
     * SPINNER ADAPTER
     */
    ArrayAdapter<CharSequence> spdaNoSpinnerAdapter, seaseLocSpinnerAdapter,
            soffOwnershipSpinnerAdapter, slocalityTypeSpinnerAdapter,
            sbusinessSetupSpinnerAdapter, sbusinessBoardSpinnerAdapter,
            svisCardSpinnerAdapter,sapplVeriFromAdapter, sconVeriFeedAdapter,
            spolLinkSpinnerAdapter, soverallStatusSpinnerAdapter,sreasonNegativeSpinnerAdapter;

    /**
     * LOCATION VARIABLES
     */
    String slongi,slati;

    ProgressDialog dialog;

    Button nextButton, locationButton;




    Intent i = getIntent();
//        String StringCaseNo = i.getStringExtra("CASENO");
//    String userName = i.getStringExtra("USERNAME");

    String userName;
    String StringCaseNo;



    String ACTIVITY = "BUSINESS";

    String TABLENAME = "cases-business";

    String PERSONNAME,CLIENTCODE;

    String ADDRESS;

    ProgressBar progressBar;

    private static final int REQUEST_STRING_CODE=1234;

    boolean GOT_LOCATION = false;

    TextView fetchingLocation;

    private RecyclerView recyclerView;

    /**
     * LOCATION
     */

    EasyWayLocation easyWayLocation;

    TextView lat,lng;
    private Double lati, longi;

    final String []locationPermissionList={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,};

    private static final int LOCATION_STRING_CODE = 925;


    /**
     * PHOTO ACTIVITY
    */
    final String []permissionsList={Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final String FILE_PROVIDER_AUTHORITY = "com.example.alihasan.synergytwo.fileprovider";

    private String mTempPhotoPath;

    private Bitmap mResultsBitmap;

    File photoFile = null;

    static String globalImageFileName;
//    int counter = 0;

    private CounterSingleton counter;

    PhotoHelper photoHelper;



    boolean EXIT_CODE = false;

    private ArrayList<Bitmap> mImageUrls = new ArrayList<>();
    private ArrayList<String> mImageNames = new ArrayList<>();

    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        /**
         * Disable Screenshot
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        /**
         * DD test
         */
        businessViewModel = ViewModelProviders.of(this).get(BusinessViewModel.class);
        imageViewModel = ViewModelProviders.of(this).get(ImageViewModel.class);
        inUploadViewModel = ViewModelProviders.of(this).get(InUploadViewModel.class);


        /**
         * PERMISSION CHECKS
         */

        /*
        LOCATION STUFF
         */

        easyWayLocation = new EasyWayLocation(this);
        easyWayLocation.setListener(this);

        ActivityCompat.requestPermissions(this,
                locationPermissionList,
                LOCATION_STRING_CODE);

        counter = CounterSingleton.getInstance();
        counter.setCounter(0);

        photoHelper = new PhotoHelper(BusinessActivity.this);

        progressBar = findViewById(R.id.progressBar);
        fetchingLocation = findViewById(R.id.fetchingLocation);
        recyclerView = findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.empty_view);
        EXIT_CODE = false;

        personName = findViewById(R.id.PersonNameMAIN);
        caseType = findViewById(R.id.CaseTypeMAIN);
        bankType = findViewById(R.id.bankTypeMAIN);

        SharedPreferences loginData = getSharedPreferences("PDANOSHARED", Context.MODE_PRIVATE);
        userName = loginData.getString("PDANO", "");

        SharedPreferences caseData = getSharedPreferences("CASEDATA", Context.MODE_PRIVATE);
        StringCaseNo = caseData.getString("CASENO","");
        PERSONNAME = caseData.getString("PERSONNAME","");
        ADDRESS = caseData.getString("ADDRESS","");
        CLIENTCODE = caseData.getString("CLIENTCODE","");

        if(PERSONNAME!=null) {
            personName.setText(PERSONNAME);
            caseType.setText(ACTIVITY);
            bankType.setText(CLIENTCODE);
        }

        //        EDITTEXTS

        address = findViewById(R.id.address);
        address.setText(ADDRESS);

        applName = findViewById(R.id.applName);
        contactNo = findViewById(R.id.contactNo);
        compName = findViewById(R.id.compName);
        businessNature = findViewById(R.id.businessNature);
        designation = findViewById(R.id.designation);
        workingSince = findViewById(R.id.workingSince);
        personContacted = findViewById(R.id.personContacted);
        desigPersonMet = findViewById(R.id.desigPersonMet);
        empNo = findViewById(R.id.empNo);
        landmark = findViewById(R.id.landmark);branchNo = findViewById(R.id.branchNo);
        yearsPresentAdd = findViewById(R.id.yearsPresentAdd);
        conVer1 = findViewById(R.id.conVer1);
        conVer2 = findViewById(R.id.conVer2);
        addProofDetail = findViewById(R.id.addProofDetail);
        remark = findViewById(R.id.remarks);

//        SPINNERS
        pdaNoSpinner = findViewById(R.id.pdaNoSpinner);
        easeLocSpinner = findViewById(R.id.easeLocSpinner);
        offOwnershipSpinner = findViewById(R.id.offOwnershipSpinner);
        localityTypeSpinner = findViewById(R.id.localityTypeSpinner);
        businessSetupSpinner = findViewById(R.id.businessSetupSpinner);
        businessBoardSpinner = findViewById(R.id.businessBoardSpinner);
        visCardSpinner = findViewById(R.id.visCardSpinner);
        applVeriFrom = findViewById(R.id.applVeriFrom);
        conVeriFeed = findViewById(R.id.conVeriFeedSpinner);
        polLinkSpinner = findViewById(R.id.polLinkSpinner);
        overallStatusSpinner = findViewById(R.id.overallStatusSpinner);
        reasonNegativeSpinner = findViewById(R.id.reasonNegativeSpinner);

        nextButton = findViewById(R.id.nextButton);
        locationButton = findViewById(R.id.locationButton);

        lat = findViewById(R.id.lat);
        lng = findViewById(R.id.lng);

        /**
         * SETTING SPINNER ADAPTERS
         */

        spdaNoSpinnerAdapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.YESNO, R.layout.support_simple_spinner_dropdown_item);
        spdaNoSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        pdaNoSpinner.setAdapter(spdaNoSpinnerAdapter);

        seaseLocSpinnerAdapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.EASELOCATE2, R.layout.support_simple_spinner_dropdown_item);
        seaseLocSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        easeLocSpinner.setAdapter(seaseLocSpinnerAdapter);

        soffOwnershipSpinnerAdapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.OFFICEOWNERSHIP, R.layout.support_simple_spinner_dropdown_item);
        soffOwnershipSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        offOwnershipSpinner.setAdapter(soffOwnershipSpinnerAdapter);

        slocalityTypeSpinnerAdapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.LOCALITYTYPE2, R.layout.support_simple_spinner_dropdown_item);
        slocalityTypeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        localityTypeSpinner.setAdapter(slocalityTypeSpinnerAdapter);

        sbusinessSetupSpinnerAdapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.BUSINESSSETUP, R.layout.support_simple_spinner_dropdown_item);
        sbusinessSetupSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        businessSetupSpinner.setAdapter(sbusinessSetupSpinnerAdapter);

        sbusinessBoardSpinnerAdapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.BUSINESSBOARD, R.layout.support_simple_spinner_dropdown_item);
        sbusinessBoardSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        businessBoardSpinner.setAdapter(sbusinessBoardSpinnerAdapter);

        svisCardSpinnerAdapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.VISITINGCARD2, R.layout.support_simple_spinner_dropdown_item);
        svisCardSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        visCardSpinner.setAdapter(svisCardSpinnerAdapter);

        sapplVeriFromAdapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.APPLNAMEVERIFFROM, R.layout.support_simple_spinner_dropdown_item);
        sapplVeriFromAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        applVeriFrom.setAdapter(sapplVeriFromAdapter);

        sconVeriFeedAdapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.CONTACTFEEDBACK, R.layout.support_simple_spinner_dropdown_item);
        sconVeriFeedAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        conVeriFeed.setAdapter(sconVeriFeedAdapter);

        spolLinkSpinnerAdapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.POLITICALLINK2, R.layout.support_simple_spinner_dropdown_item);
        spolLinkSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        polLinkSpinner.setAdapter(spolLinkSpinnerAdapter);

        soverallStatusSpinnerAdapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.OVERALLSTATUS2, R.layout.support_simple_spinner_dropdown_item);
        soverallStatusSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        overallStatusSpinner.setAdapter(soverallStatusSpinnerAdapter);

        sreasonNegativeSpinnerAdapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.REASONNEGATIVEFI2, R.layout.support_simple_spinner_dropdown_item);
        sreasonNegativeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        reasonNegativeSpinner.setAdapter(sreasonNegativeSpinnerAdapter);

        /**
         * END SETTING ADAPTERS
         */
        /**
         * LOCATION BUTTON
         */


        /**
         * LOCATION END
         */

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermission();

                //as counter starts from 0
                if(counter.getCounter() >  3)
                    EXIT_CODE = true;

                if(!EXIT_CODE)
                {
                        if(counter.getCounter() < 3)
                        {
                            Toast.makeText(getApplicationContext(), "UPLOAD AT LEAST 3 IMAGES", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "JUST A MINUTE. UPLOADING IMAGE...", Toast.LENGTH_SHORT).show();
                        }

                        return;
                }


                /**
                 * Fetched
                 */

                if(lat.getText().toString().length()>0 && lng.getText().toString().length()>0)
                {

                    spdaNoSpinner = pdaNoSpinner.getSelectedItem().toString();
                    seaseLocSpinner = easeLocSpinner.getSelectedItem().toString();
                    soffOwnershipSpinner = offOwnershipSpinner.getSelectedItem().toString();
                    slocalityTypeSpinner = localityTypeSpinner.getSelectedItem().toString();
                    sbusinessSetupSpinner = businessSetupSpinner.getSelectedItem().toString();
                    sbusinessBoardSpinner = businessBoardSpinner.getSelectedItem().toString();
                    svisCardSpinner = visCardSpinner.getSelectedItem().toString();
                    sapplVeriFrom = applVeriFrom.getSelectedItem().toString();
                    sconVeriFeed = conVeriFeed.getSelectedItem().toString();
                    spolLinkSpinner = polLinkSpinner.getSelectedItem().toString();
                    soverallStatusSpinner = overallStatusSpinner.getSelectedItem().toString();
                    sreasonNegativeSpinner = reasonNegativeSpinner.getSelectedItem().toString();

                    /**
                     * END OF ADAPTERS
                     */

                    saddress = address.getText().toString().trim();
                    sapplName = applName.getText().toString().trim();
                    saddress = address.getText().toString().trim();
                    scontactNo = contactNo.getText().toString().trim();
                    scompName = compName.getText().toString().trim();
                    sbusinessNature = businessNature.getText().toString().trim();
                    sdesignation = designation.getText().toString().trim();
                    sworkingSince = workingSince.getText().toString().trim();
                    spersonContacted = personContacted.getText().toString().trim();
                    sdesigContacted = designation.getText().toString().trim();
                    sempNo = empNo.getText().toString().trim();
                    slandmark = landmark.getText().toString().trim();
                    sbranchNo = branchNo.getText().toString().trim();
                    syearsPresentAdd = yearsPresentAdd.getText().toString().trim();
                    sconVer1 = conVer1.getText().toString().trim();
                    sconVer2 = conVer2.getText().toString().trim();
                    saddProofDetail = addProofDetail.getText().toString().trim();
                    sremark = remark.getText().toString().trim();

                    slati = lat.getText().toString().trim();
                    slongi = lng.getText().toString().trim();

                    /**
                     * EDIT TEXTS END
                     */

                    /**
                     * RETROFIT MAGIC
                     */
                    storeData(TABLENAME,StringCaseNo,saddress,seaseLocSpinner,soffOwnershipSpinner,
                            scompName,slocalityTypeSpinner, sbusinessNature, sdesignation, sworkingSince,
                            spersonContacted, sdesigContacted, sempNo,
                            slandmark, sbranchNo,sbusinessSetupSpinner, sbusinessBoardSpinner, syearsPresentAdd,
                            svisCardSpinner,sapplVeriFrom,sconVer1, sconVer2,sconVeriFeed, saddProofDetail,
                            spolLinkSpinner, soverallStatusSpinner,sreasonNegativeSpinner,slati,slongi,sremark);
                }

                else {

                    ActivityCompat.requestPermissions(BusinessActivity.this,
                            locationPermissionList,
                            LOCATION_STRING_CODE);
                }

                onSubmit(StringCaseNo);
            }

        });

    }

    /**
     *LOCATION STUFF
     */
    @Override
    public void locationOn() {
//        Toast.makeText(this, "Location ON", Toast.LENGTH_SHORT).show();
//        easyWayLocation.beginUpdates();
    }

    @Override
    public void onPositionChanged() {
//        lati = easyWayLocation.getLatitude();
//        longi = easyWayLocation.getLongitude();
//        tvLatitude.setText(String.valueOf(lati));
//        tvLongitude.setText(String.valueOf(longi));
    }

    @Override
    public void locationCancelled() {
//        easyWayLocation.showAlertDialog(getString(R.string.loc_title), getString(R.string.loc_mess), null);
    }

    private void showExplanation(String title,
                                 String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission();
                    }
                });
        builder.create().show();
    }

    private void showCamExplanation(String title,
                                 String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestCamPermission();
                    }
                });
        builder.create().show();
    }

    private void requestCamPermission() {
        ActivityCompat.requestPermissions(BusinessActivity.this,
                permissionsList,
                REQUEST_STRING_CODE);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(BusinessActivity.this,
                locationPermissionList,
                LOCATION_STRING_CODE);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case LOCATION_STRING_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(BusinessActivity.this, "Location Permission Granted!", Toast.LENGTH_SHORT).show();
                    easyWayLocation.beginUpdates();
                    lati = easyWayLocation.getLatitude();
                    longi = easyWayLocation.getLongitude();
                    lat.setText(String.valueOf(lati));
                    lng.setText(String.valueOf(longi));
                }
                else {
                    Toast.makeText(BusinessActivity.this, "Location Permission Denied!", Toast.LENGTH_SHORT).show();
                    showExplanation(getString(R.string.loc_title),getString(R.string.loc_mess));
                }
                break;

            case REQUEST_STRING_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    launchCamera();
                    Toast.makeText(BusinessActivity.this, "Camera Permission Granted!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(BusinessActivity.this, "Camera Permission Denied!", Toast.LENGTH_SHORT).show();
                    showCamExplanation(getString(R.string.cam_title),getString(R.string.cam_mess));
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage();
        } else {
            // Otherwise, delete the temporary image file
            photoHelper.deleteImageFile(this, mTempPhotoPath);
        }
    }

    /**
     *
     * LOCATION STUFF ENDS
     */

    public void onSubmit(String stringCaseNo)
    {

        inUploadViewModel.insert(new InUplaod(stringCaseNo,"BUSINESS"));

        SharedPreferences preferences2 =getSharedPreferences("CASEDATA",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.clear();
        editor2.apply();
        finish();

        Intent backToAssignmentChoose = new Intent(BusinessActivity.this,AssignmentChoose.class);
        startActivity(backToAssignmentChoose);
    }

    public void storeData(final String TABLENAME,
                          final String CASENO,
                          final String ADDRESS,
                          final String EASELOCATE,
                          final String OFFICEOWNERSHIP,
                          final String APPLCOMPANYNAME,
                          final String LOCALITYTYPE,
                          final String NATUREBUSNIESS,
                          final String APPLDESIGNATION,
                          final String WORKINGSINCE,
                          final String PERSONCONTACTED,
                          final String PERSONDESIGNATION,
                          final String NOSEMP,
                          final String LANDMARK,
                          final String NOSBRANCHES,
                          final String BUSINESSSETUP,
                          final String BUSINESSBOARD,
                          final String NOSYEARSATADDRESS,
                          final String VISITINGCARD,
                          final String APPLNAMEVERIFFROM,
                          final String CONTACTVERIF1,
                          final String CONTACTVERIF2,
                          final String CONTACTFEEDBACK,
                          final String PROOFDETAILS,
                          final String POLITICALLINK,
                          final String OVERALLSTATUS,
                          final String REASONNEGATIVEFI,
                          final String LATITUDE,
                          final String LONGITUDE,
                          final String REMARKS){

        businessViewModel.insert(new Business(TABLENAME,CASENO,ADDRESS,EASELOCATE,
                OFFICEOWNERSHIP,APPLCOMPANYNAME,LOCALITYTYPE,
                NATUREBUSNIESS,APPLDESIGNATION,WORKINGSINCE,
                PERSONCONTACTED,PERSONDESIGNATION,NOSEMP,
                LANDMARK,NOSBRANCHES,BUSINESSSETUP,
                BUSINESSBOARD,NOSYEARSATADDRESS,
                VISITINGCARD,APPLNAMEVERIFFROM,CONTACTVERIF1,
                CONTACTVERIF2,CONTACTFEEDBACK,PROOFDETAILS,
                POLITICALLINK,OVERALLSTATUS,REASONNEGATIVEFI,
                LATITUDE,LONGITUDE,REMARKS));

        Toast.makeText(getApplicationContext(), "SUCCESSFULLY UPDATED IN DB  ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.camerabutton, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.camera:
                ActivityCompat.requestPermissions(this,
                        permissionsList,
                        REQUEST_STRING_CODE);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void launchCamera() {

        // Create the capture image intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the temporary File where the photo should go
            try {
                photoFile = photoHelper.createTempImageFile(BusinessActivity.this);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the path of the temporary file
                mTempPhotoPath = photoFile.getAbsolutePath();

                // Get the content URI for the image file
                Uri photoURI = FileProvider.getUriForFile(this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera activity
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    /**
     * Method for processing the captured image and setting it to the TextView.
     */
    private void processAndSetImage() {

        mResultsBitmap = photoHelper.getBitmap(mTempPhotoPath,BusinessActivity.this);
        /**
         * UPLOAD IMAGE USING RETROFIT
         */

        retroFitHelper(photoHelper.encodeImage(mResultsBitmap));

        // Set the new bitmap to the ImageView of RecyclerView
        mImageUrls.add(mResultsBitmap);
        mImageNames.add(globalImageFileName);
        emptyView.setVisibility(View.GONE);
        initRecyclerView();

//        imageView.setImageBitmap(mResultsBitmap);
    }

    private void initRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mImageUrls,mImageNames,emptyView, imageViewModel);
        recyclerView.setAdapter(adapter);
    }

    public void retroFitHelper(String encodedImage)
    {

        /**
         * DD change
         */
        globalImageFileName = photoHelper.getGlobalImageFileName();
        imageViewModel.insert(new ImageParam(encodedImage,globalImageFileName,StringCaseNo,ACTIVITY,userName));

                    Toast.makeText(getApplicationContext(), "IMAGE UPLOADED DATABASE", Toast.LENGTH_SHORT).show();
                    counter.addCounter();

                    if(counter.getCounter() >= 3)
                        EXIT_CODE = true;
    }

}
