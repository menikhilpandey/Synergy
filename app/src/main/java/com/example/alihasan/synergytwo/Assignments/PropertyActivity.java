package com.example.alihasan.synergytwo.Assignments;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import com.example.alihasan.synergytwo.Adapters.RecyclerViewAdapter;
import com.example.alihasan.synergytwo.ClassHelper.PhotoHelper;
import com.example.alihasan.synergytwo.CounterSingleton;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageParam;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageViewModel;
import com.example.alihasan.synergytwo.Database.InUploadDatabase.InUplaod;
import com.example.alihasan.synergytwo.Database.InUploadDatabase.InUploadViewModel;
import com.example.alihasan.synergytwo.Database.PropertyDatabase.Property;
import com.example.alihasan.synergytwo.Database.PropertyDatabase.PropertyViewModel;
import com.example.alihasan.synergytwo.R;
import com.example.alihasan.synergytwo.api.service.ServerURL;
import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class PropertyActivity extends AppCompatActivity implements Listener {

    /**
     * DD test
     */
    private PropertyViewModel propertyViewModel;
    private ImageViewModel imageViewModel   ;
    private InUploadViewModel inUploadViewModel;
    /**
     *
     */

    static String SERVER_URL = new ServerURL().getSERVER_URL();

    /**
     * Total 23
     * EditText  10
     * Spinner 13
     */

    EditText address;
    String saddress;

    EditText personContacted, area, documentVerify, neighbourName1, address1,
            neighbourName2, address2, propertySoldWhom, remarkPurchaser,remark;

    Spinner easeToLocSpinner, relationshipSpinner, propTypeSpinner, noYearsPresentOwnSpinner, cooperativeAppSpinner, neighbourFeedSpinner,
            locTypeSpinner, ambienceSpinner, appStaySpinner, vehicalSeenSpinner ,politicalLinkSpinner, OverallStatusSpinner,
            reasonNegativeSpinner;

    /**
     * EDITTEXT AND SPINNER
     * STRING VALUES
     */

    String spersonContacted,
            sarea,
            sdocumentVerify,
            sneighbourName1,
            saddress1,
            sneighbourName2,
            saddress2,
            spropertySoldWhom,
            sremarkPurchaser,
            sremark;

    String  seaseToLocSpinner,
            srelationshipSpinner,
            spropTypeSpinner,
            snoYearsPresentOwnSpinner,
            scooperativeAppSpinner,
            sneighbourFeedSpinner,
            slocTypeSpinner,
            sambienceSpinner,
            sappStaySpinner,
            svehicalSeenSpinner,
            spoliticalLinkSpinner,
            sOverallStatusSpinner,
            sreasonNegativeSpinner;



    /**
     * SPINNER ADAPTER
     */

    ArrayAdapter<CharSequence> easeToLocSpinnerAdapter,
            relationshipSpinnerAdapter,
            propTypeSpinnerAdapter,
            noYearsPresentOwnAdapter,
            cooperativeAppSpinnerAdapter,
            neighbourFeedSpinnerAdapter,
            locTypeSpinnerAdapter,
            ambienceSpinnerAdapter,
            appStaySpinnerAdapter,
            vehicalSeenSpinnerAdapter,
            politicalLinkSpinnerAdapter,
            OverallStatusSpinnerAdapter,
            reasonNegativeSpinnerAdapter;

    /**
     * LOCATION VARIABLES
     */
    String slongi, slati;

    ProgressDialog dialog;

    Button nextButton, locationButton;

    TextView lat, lng;
    public static final int LOCATION_REQ_CODE = 100;
    LocationManager locationManager;
    private double latitude = 0;
    private double longitude = 0;

    Intent i = getIntent();
//        String StringCaseNo = i.getStringExtra("CASENO");
//    String userName = i.getStringExtra("USERNAME");

    String userName;
    String StringCaseNo;


    String ACTIVITY = "PROPERTY";

    String TABLENAME = "cases-property";

    String ADDRESS,CLIENTCODE;

    ProgressBar progressBar;

    private static final int REQUEST_STRING_CODE=1234;

    boolean GOT_LOCATION = false;

    TextView fetchingLocation;

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
    private CounterSingleton counter;

    boolean EXIT_CODE = false;

    private ArrayList<Bitmap> mImageUrls = new ArrayList<>();
    private ArrayList<String> mImageNames = new ArrayList<>();

    TextView emptyView;
    private RecyclerView recyclerView;
    String PERSONNAME;

    TextView personName,caseType,bankType;

    PhotoHelper photoHelper;

    /**
     * LOCATION
     */

    EasyWayLocation easyWayLocation;

    private Double lati, longi;

    final String []locationPermissionList={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,};

    private static final int LOCATION_STRING_CODE = 925;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        /**
         * Disable Screenshot
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        /**
         * DD test
         */
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
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


        photoHelper = new PhotoHelper(PropertyActivity.this);


        progressBar=findViewById(R.id.progressBar);
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
        StringCaseNo = caseData.getString("CASENO", "");
        PERSONNAME = caseData.getString("PERSONNAME","");
        ADDRESS = caseData.getString("ADDRESS","");
        CLIENTCODE = caseData.getString("CLIENTCODE","");


        if(PERSONNAME!=null) {
            personName.setText(PERSONNAME);
            caseType.setText(ACTIVITY);
            bankType.setText(CLIENTCODE);
        }

        //EditText

        address = findViewById(R.id.address);
        address.setText(ADDRESS);

        personContacted = findViewById(R.id.personContacted);
        area = findViewById(R.id.area);
        documentVerify = findViewById(R.id.docVerify);
        neighbourName1 = findViewById(R.id.neighbourName1);
        address1 = findViewById(R.id.address1);
        neighbourName2 = findViewById(R.id.neighbourName2);
        address2 = findViewById(R.id.address2);
        propertySoldWhom = findViewById(R.id.propertySoldWhom);
        remarkPurchaser = findViewById(R.id.remarkPurchaser);
        remark = findViewById(R.id.remarks);

        //Spinner
        easeToLocSpinner = findViewById(R.id.easeLocSpinner);
        relationshipSpinner = findViewById(R.id.relationshipSpinner);
        propTypeSpinner = findViewById(R.id.propertyTypeSpinner);
        noYearsPresentOwnSpinner = findViewById(R.id.noYearPresentOwnSpinner);
        cooperativeAppSpinner = findViewById(R.id.cooperativeAppSpinner);
        neighbourFeedSpinner = findViewById(R.id.neighbourFeedbackSpinner);
        locTypeSpinner = findViewById(R.id.localityTypeSpinner);
        ambienceSpinner = findViewById(R.id.ambienceSpinner);
        appStaySpinner = findViewById(R.id.applicantStaySpinner);
        vehicalSeenSpinner = findViewById(R.id.vehicalSeenSpinner);
        politicalLinkSpinner = findViewById(R.id.polLinkSpinner);
        OverallStatusSpinner = findViewById(R.id.overallStatusSpinner);
        reasonNegativeSpinner = findViewById(R.id.reasonNegativeSpinner);

        nextButton = findViewById(R.id.nextButton);
        locationButton = findViewById(R.id.locationButton);

        lat = findViewById(R.id.lat);
        lng = findViewById(R.id.lng);

        easeToLocSpinnerAdapter = ArrayAdapter.createFromResource(PropertyActivity.this, R.array.EASELOCATE, R.layout.support_simple_spinner_dropdown_item);
        easeToLocSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        easeToLocSpinner.setAdapter(easeToLocSpinnerAdapter);

        relationshipSpinnerAdapter = ArrayAdapter.createFromResource(PropertyActivity.this, R.array.RELATIONSHIP, R.layout.support_simple_spinner_dropdown_item);
        relationshipSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        relationshipSpinner.setAdapter(relationshipSpinnerAdapter);

        propTypeSpinnerAdapter = ArrayAdapter.createFromResource(PropertyActivity.this, R.array.PROPERTYTYPE, R.layout.support_simple_spinner_dropdown_item);
        propTypeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        propTypeSpinner.setAdapter(propTypeSpinnerAdapter);

        noYearsPresentOwnAdapter = ArrayAdapter.createFromResource(PropertyActivity.this, R.array.NOSYEARSOWNER, R.layout.support_simple_spinner_dropdown_item);
        noYearsPresentOwnAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        noYearsPresentOwnSpinner.setAdapter(noYearsPresentOwnAdapter);

        cooperativeAppSpinnerAdapter = ArrayAdapter.createFromResource(PropertyActivity.this, R.array.APPLCOOPERATIVE, R.layout.support_simple_spinner_dropdown_item);
        cooperativeAppSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        cooperativeAppSpinner.setAdapter(cooperativeAppSpinnerAdapter);

        neighbourFeedSpinnerAdapter = ArrayAdapter.createFromResource(PropertyActivity.this, R.array.NEIGHBOURFEEDBACK, R.layout.support_simple_spinner_dropdown_item);
        neighbourFeedSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        neighbourFeedSpinner.setAdapter(neighbourFeedSpinnerAdapter);

        locTypeSpinnerAdapter = ArrayAdapter.createFromResource(PropertyActivity.this, R.array.LOCALITYTYPE, R.layout.support_simple_spinner_dropdown_item);
        locTypeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        locTypeSpinner.setAdapter(locTypeSpinnerAdapter);

        ambienceSpinnerAdapter = ArrayAdapter.createFromResource(PropertyActivity.this, R.array.AMBIENCE, R.layout.support_simple_spinner_dropdown_item);
        ambienceSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ambienceSpinner.setAdapter(ambienceSpinnerAdapter);

        appStaySpinnerAdapter = ArrayAdapter.createFromResource(PropertyActivity.this, R.array.APPLSTAYATADDRESS, R.layout.support_simple_spinner_dropdown_item);
        appStaySpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        appStaySpinner.setAdapter(appStaySpinnerAdapter);

        vehicalSeenSpinnerAdapter= ArrayAdapter.createFromResource(PropertyActivity.this, R.array.VEHICLESEEN, R.layout.support_simple_spinner_dropdown_item);
        vehicalSeenSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        vehicalSeenSpinner.setAdapter(vehicalSeenSpinnerAdapter);

        politicalLinkSpinnerAdapter = ArrayAdapter.createFromResource(PropertyActivity.this, R.array.POLITICALLINK, R.layout.support_simple_spinner_dropdown_item);
        politicalLinkSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        politicalLinkSpinner.setAdapter(politicalLinkSpinnerAdapter);

        OverallStatusSpinnerAdapter = ArrayAdapter.createFromResource(PropertyActivity.this, R.array.OVERALLSTATUS, R.layout.support_simple_spinner_dropdown_item);
        OverallStatusSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        OverallStatusSpinner.setAdapter(OverallStatusSpinnerAdapter);

        reasonNegativeSpinnerAdapter = ArrayAdapter.createFromResource(PropertyActivity.this, R.array.REASONNEGATIVEFI, R.layout.support_simple_spinner_dropdown_item);
        reasonNegativeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        reasonNegativeSpinner.setAdapter(reasonNegativeSpinnerAdapter);

        /**
         * END SETTING ADAPTERS
        **/


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    seaseToLocSpinner = easeToLocSpinner.getSelectedItem().toString();
                    srelationshipSpinner = relationshipSpinner.getSelectedItem().toString();
                    spropTypeSpinner = propTypeSpinner.getSelectedItem().toString();
                    snoYearsPresentOwnSpinner = noYearsPresentOwnSpinner.getSelectedItem().toString();
                    scooperativeAppSpinner = cooperativeAppSpinner.getSelectedItem().toString();
                    sneighbourFeedSpinner = neighbourFeedSpinner.getSelectedItem().toString();
                    slocTypeSpinner = locTypeSpinner.getSelectedItem().toString();
                    sambienceSpinner = ambienceSpinner.getSelectedItem().toString();
                    sappStaySpinner = appStaySpinner.getSelectedItem().toString();
                    svehicalSeenSpinner = vehicalSeenSpinner.getSelectedItem().toString();
                    spoliticalLinkSpinner = politicalLinkSpinner.getSelectedItem().toString();
                    sOverallStatusSpinner = OverallStatusSpinner.getSelectedItem().toString();
                    sreasonNegativeSpinner = reasonNegativeSpinner.getSelectedItem().toString();

                    saddress = address.getText().toString().trim();
                    spersonContacted = personContacted.getText().toString().trim();
                    sarea = area.getText().toString().trim();
                    sdocumentVerify = documentVerify.getText().toString().trim();
                    sneighbourName1 = neighbourName1.getText().toString().trim();
                    saddress1 = address1.getText().toString().trim();
                    sneighbourName2 = neighbourName2.getText().toString().trim();
                    saddress2 = address2.getText().toString().trim();
                    spropertySoldWhom = propertySoldWhom.getText().toString().trim();
                    sremarkPurchaser = remarkPurchaser.getText().toString().trim();
                    sremark = remark.getText().toString().trim();

                    slati = lat.getText().toString().trim();
                    slongi = lng.getText().toString().trim();

                    /**
                     * RETROFIT MAGIC
                     */
                    storeData(TABLENAME,
                            StringCaseNo,
                            saddress,
                            seaseToLocSpinner,
                            spersonContacted,
                            srelationshipSpinner,
                            spropTypeSpinner,
                            snoYearsPresentOwnSpinner,
                            sarea,
                            sdocumentVerify,
                            sneighbourName1,
                            saddress1,
                            sneighbourName2,
                            saddress2,
                            spropertySoldWhom,
                            sremarkPurchaser,
                            scooperativeAppSpinner,
                            sneighbourFeedSpinner,
                            slocTypeSpinner,
                            sambienceSpinner,
                            sappStaySpinner,
                            svehicalSeenSpinner,
                            spoliticalLinkSpinner,
                            sOverallStatusSpinner,
                            sreasonNegativeSpinner,
                            slati,
                            slongi,
                            sremark);
                }

                else {
                    ActivityCompat.requestPermissions(PropertyActivity.this,
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

    private void requestPermission() {
        ActivityCompat.requestPermissions(PropertyActivity.this,
                locationPermissionList,
                LOCATION_STRING_CODE);
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
        ActivityCompat.requestPermissions(PropertyActivity.this,
                permissionsList,
                REQUEST_STRING_CODE);
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
                    Toast.makeText(PropertyActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    easyWayLocation.beginUpdates();
                    lati = easyWayLocation.getLatitude();
                    longi = easyWayLocation.getLongitude();
                    lat.setText(String.valueOf(lati));
                    lng.setText(String.valueOf(longi));
                }
                else {
                    Toast.makeText(PropertyActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                    showExplanation(getString(R.string.loc_title),getString(R.string.loc_mess));
                }
                break;

            case REQUEST_STRING_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    launchCamera();
                    Toast.makeText(PropertyActivity.this, "Camera Permission Granted!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PropertyActivity.this, "Camera Permission Denied!", Toast.LENGTH_SHORT).show();
                    showCamExplanation(getString(R.string.cam_title),getString(R.string.cam_mess));
                }
        }
    }

    /**
     *
     * LOCATION STUFF ENDS
     */

    public void onSubmit(String stringCaseNo)
    {
        inUploadViewModel.insert(new InUplaod(stringCaseNo,"PROPERTY"));
        
        SharedPreferences preferences2 =getSharedPreferences("CASEDATA",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.clear();
        editor2.apply();
        finish();

        Intent backToAssignmentChoose = new Intent(PropertyActivity.this,AssignmentChoose.class);
        startActivity(backToAssignmentChoose);
    }

    public  void storeData(String TABLENAME,
                                String CASENO,
                                String ADDRESS,
                                String EASELOCATE,
                                String PERSONCONTACTED,
                                String RELATIONSHIP,
                                String PROPERTYTYPE,
                                String NOSYEARSOWNER,
                                String AREA,
                                String DOCSVERIF,
                                String NEIGHBOURNAME1,
                                String ADDRESS1,
                                String NEIGHBOURNAME2,
                                String ADDRESS2,
                                String PROPERTYSOLDTO,
                                String PURCHASERDETAILS,
                                String APPLCOOPERATIVE,
                                String NEIGHBOURFEEDBACK,
                                String LOCALITYTYPE,
                                String AMBIENCE,
                                String APPLSTAYATADDRESS,
                                String VEHICLESEEN,
                                String POLITICALLINK,
                                String OVERALLSTATUS,
                                String REASONNEGATIVEFI,
                                String LATITUDE,
                                String LONGITUDE,
                                String REMARKS)
    {

        propertyViewModel.insert(new Property(
                TABLENAME,
                CASENO,
                ADDRESS,
                EASELOCATE,
                PERSONCONTACTED,
                RELATIONSHIP,
                PROPERTYTYPE,
                NOSYEARSOWNER,
                AREA,
                DOCSVERIF,
                NEIGHBOURNAME1,
                ADDRESS1,
                NEIGHBOURNAME2,
                ADDRESS2,
                PROPERTYSOLDTO,
                PURCHASERDETAILS,
                APPLCOOPERATIVE,
                NEIGHBOURFEEDBACK,
                LOCALITYTYPE,
                AMBIENCE,
                APPLSTAYATADDRESS,
                VEHICLESEEN,
                POLITICALLINK,
                OVERALLSTATUS,
                REASONNEGATIVEFI,
                LATITUDE,
                LONGITUDE,
                REMARKS));
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
                photoFile = photoHelper.createTempImageFile(PropertyActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage();
        } else {
            Toast.makeText(getApplicationContext(),"not taken", Toast.LENGTH_LONG).show();

            // Otherwise, delete the temporary image file
            photoHelper.deleteImageFile(this, mTempPhotoPath);
        }
    }

    /**
     * Method for processing the captured image and setting it to the TextView.
     */
    private void processAndSetImage() {

        mResultsBitmap = photoHelper.getBitmap(mTempPhotoPath,PropertyActivity.this);
        /**
         * UPLOAD IMAGE USING RETROFIT
         */

        businessRetroFitUpload(photoHelper.encodeImage(mResultsBitmap));

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
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mImageUrls,mImageNames,emptyView,imageViewModel);
        recyclerView.setAdapter(adapter);
    }

    public void businessRetroFitUpload(String encodedImage)
    {

        /**
         * DD change
         */
        globalImageFileName = photoHelper.getGlobalImageFileName();
        imageViewModel.insert(new ImageParam(encodedImage,globalImageFileName,StringCaseNo,ACTIVITY,userName));

                    Toast.makeText(getApplicationContext(), "IMAGE UPLOADED DATABASE", Toast.LENGTH_SHORT).show();
                    counter.addCounter();

                    if(counter.addCounter() >= 3)
                        EXIT_CODE = true;
                    
    }

}

