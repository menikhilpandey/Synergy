package com.example.alihasan.synergytwo.Assignments;

import com.example.alihasan.synergytwo.Adapters.RecyclerViewAdapter;
import com.example.alihasan.synergytwo.CounterSingleton;
import com.example.alihasan.synergytwo.LoginActivity;
import com.example.alihasan.synergytwo.PhotoActivity;
import com.example.alihasan.synergytwo.R;
import com.example.alihasan.synergytwo.api.service.AppLocationService;
import com.example.alihasan.synergytwo.api.service.Client;
import com.example.alihasan.synergytwo.api.service.ServerURL;
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
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
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

public class EmploymentActivity extends AppCompatActivity {

    static String SERVER_URL = new ServerURL().getSERVER_URL();

    /**
     * Total 30
     * EditText  16
     * Spinner 14
     */


    EditText address;
    String saddress;

    EditText companyName, landmark, designOfApp, personMet, designOfPersonMet, personContactNo, officeTele, dateOfJoin, noOfEmp,
            personContacted, desigPersonContacted, nameOfRepManager,desigRepoManager, contactNoOfRepoManager, salary, tpcPersonName,remark;

    Spinner easeLocSpinner, locTypeSpinner, addConfirmSpinner, doesAppWorkSpinner, officeNameBoardSpinner, orgTypeSpinner,
            visitingCardObtSpinner, natureBusinessSpinner, typeOfJobSpinner, workingAsSpinner, jobTransferSpinner, tpcConfirmSpinner,
            overallStatusSpinner, reasonNegativeSpinner;

    /**
     * EDITTEXT AND SPINNER
     * STRING VALUES
     */

    String scompanyName, slandmark, sdesignOfApp, spersonMet, sdesignOfPersonMet, spersonContactNo, sofficeTele, sdateOfJoin, snoOfEmp,
            spersonContacted, sdesigPersonContacted, snameOfRepManager,sdesigRepoManager, scontactNoOfRepoManager, ssalary, stpcPersonName,sremark;

    String seaseLocSpinner, slocTypeSpinner, saddConfirmSpinner, sdoesAppWorkSpinner, sofficeNameBoardSpinner, sorgTypeSpinner,
            svisitingCardObtSpinner, snatureBusinessSpinner, stypeOfJobSpinner, sworkingAsSpinner, sjobTransferSpinner, stpcConfirmSpinner,
            soverallStatusSpinner, sreasonNegativeSpinner;

    /**
     * SPINNER ADAPTER
     */

    ArrayAdapter<CharSequence> seaseLocSpinnerAdapter, slocTypeSpinnerAdapter, saddConfirmSpinnerAdapter, sdoesAppWorkSpinnerAdapter,
            sofficeNameBoardSpinnerAdapter, sorgTypeSpinnerAdapter,
            svisitingCardObtSpinnerAdapter, sNatureBusinessSpinnerAdapter, stypeOfJobSpinnerAdapter, sworkingAsSpinnerAdapter,
            sjobTransferSpinnerAdapter, stpcConfirmSpinnerAdapter,
            soverallStatusSpinnerAdapter, sreasonNegativeSpinnerAdapter;

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


    String ACTIVITY = "EMPLOYMENT";

    String TABLENAME = "cases-employment";

    String ADDRESS,CLIENTCODE;

    ProgressBar progressBar;

    private int REQUEST_STRING_CODE=1234;

    boolean GOT_LOCATION = false;

    TextView fetchingLocation;

    /**
     * PHOTO ACTIVITY
     */

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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment);

        /**
         * PERMISSION CHECKS
         */

        counter = CounterSingleton.getInstance();
        counter.setCounter(0);

        String []permissionsList={Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this,
                permissionsList,
                REQUEST_STRING_CODE);

        displayLocationSettingsRequest(EmploymentActivity.this);


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

        companyName = findViewById(R.id.compName);
        landmark = findViewById(R.id.landmark);
        designOfApp = findViewById(R.id.designation);
        personMet = findViewById(R.id.personMet);
        designOfPersonMet = findViewById(R.id.desigPersonMet);
        personContactNo = findViewById(R.id.personContactNo);
        officeTele = findViewById(R.id.officeTele);
        dateOfJoin = findViewById(R.id.dateOfJoin);
        noOfEmp = findViewById(R.id.empNo);
        personContacted = findViewById(R.id.personContacted);
        desigPersonContacted = findViewById(R.id.desigPersonContacted);
        nameOfRepManager = findViewById(R.id.nameReportManager);
        desigRepoManager = findViewById(R.id.desigRepoManager);
        contactNoOfRepoManager = findViewById(R.id.contactNoRepoManager);
        salary = findViewById(R.id.salary);
        tpcPersonName = findViewById(R.id.tpcPersonName);
        remark = findViewById(R.id.remarks);

        //Spinner
        easeLocSpinner = findViewById(R.id.easeLocSpinner);
        locTypeSpinner = findViewById(R.id.localityTypeSpinner);
        addConfirmSpinner = findViewById(R.id.addressConfirmSpinner);
        doesAppWorkSpinner = findViewById(R.id.doesApplicantWorkSpinner);
        officeNameBoardSpinner = findViewById(R.id.officeNameBoardSpinner);
        orgTypeSpinner = findViewById(R.id.orgTypeSpinner);
        visitingCardObtSpinner = findViewById(R.id.visCardSpinner);
        natureBusinessSpinner = findViewById(R.id.businessNatureSpinner);
        typeOfJobSpinner = findViewById(R.id.typeOfJobSpinner);
        workingAsSpinner = findViewById(R.id.workingAsSpinner);
        jobTransferSpinner = findViewById(R.id.jobTransferSpinner);
        tpcConfirmSpinner = findViewById(R.id.tpcConfirmSpinner);
        overallStatusSpinner = findViewById(R.id.overallStatusSpinner);
        reasonNegativeSpinner = findViewById(R.id.reasonNegativeSpinner);

        nextButton = findViewById(R.id.nextButton);
        locationButton = findViewById(R.id.locationButton);

        lat = findViewById(R.id.lat);
        lng = findViewById(R.id.lng);


        /**
         * SETTING SPINNER ADAPTERS
         */

        //seaseLocSpinnerAdapter, slocTypeSpinnerAdapter, saddConfirmSpinnerAdapter, sdoesAppWorkSpinnerAdapter,
        //            sofficeNameBoardSpinnerAdapter, sorgTypeSpinnerAdapter,
        //            svisitingCardObtSpinnerAdapter, sNatureBusinessSpinnerAdapter, stypeOfJobSpinnerAdapter, sworkingAsSpinnerAdapter,
        //            sjobTransferSpinnerAdapter, stpcConfirmSpinnerAdapter,
        //            soverallStatusSpinnerAdapter, sreasonNegativeSpinnerAdapter;

        seaseLocSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.EASELOCATE3, R.layout.support_simple_spinner_dropdown_item);
        seaseLocSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        easeLocSpinner.setAdapter(seaseLocSpinnerAdapter);

        slocTypeSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.LOCALITYTYPE3, R.layout.support_simple_spinner_dropdown_item);
        slocTypeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        locTypeSpinner.setAdapter(slocTypeSpinnerAdapter);

        saddConfirmSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.ADDRESSCONF, R.layout.support_simple_spinner_dropdown_item);
        saddConfirmSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        addConfirmSpinner.setAdapter(saddConfirmSpinnerAdapter);

        sdoesAppWorkSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.DOESAPPLWORK, R.layout.support_simple_spinner_dropdown_item);
        sdoesAppWorkSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        doesAppWorkSpinner.setAdapter(sdoesAppWorkSpinnerAdapter);

        sofficeNameBoardSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.OFFICEBOARD, R.layout.support_simple_spinner_dropdown_item);
        sofficeNameBoardSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        officeNameBoardSpinner.setAdapter(sofficeNameBoardSpinnerAdapter);

        sorgTypeSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.ORGANISATIONTYPE, R.layout.support_simple_spinner_dropdown_item);
        sorgTypeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        orgTypeSpinner.setAdapter(sorgTypeSpinnerAdapter);

        svisitingCardObtSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.VISITINGCARD3, R.layout.support_simple_spinner_dropdown_item);
        svisitingCardObtSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        visitingCardObtSpinner.setAdapter(svisitingCardObtSpinnerAdapter);

        sNatureBusinessSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.NATUREBUSNIESS, R.layout.support_simple_spinner_dropdown_item);
        sNatureBusinessSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        natureBusinessSpinner.setAdapter(sNatureBusinessSpinnerAdapter);

        stypeOfJobSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.JOBTYPE, R.layout.support_simple_spinner_dropdown_item);
        stypeOfJobSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typeOfJobSpinner.setAdapter(stypeOfJobSpinnerAdapter);

        sworkingAsSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.WORKINGAS, R.layout.support_simple_spinner_dropdown_item);
        sworkingAsSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        workingAsSpinner.setAdapter(sworkingAsSpinnerAdapter);

        sjobTransferSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.JOBTRANSFER, R.layout.support_simple_spinner_dropdown_item);
        sjobTransferSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        jobTransferSpinner.setAdapter(sjobTransferSpinnerAdapter);

        stpcConfirmSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.TPCCONFIRM, R.layout.support_simple_spinner_dropdown_item);
        stpcConfirmSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        tpcConfirmSpinner.setAdapter(stpcConfirmSpinnerAdapter);

        soverallStatusSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.OVERALLSTATUS3, R.layout.support_simple_spinner_dropdown_item);
        soverallStatusSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        overallStatusSpinner.setAdapter(soverallStatusSpinnerAdapter);

        sreasonNegativeSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.REASONNEGATIVEFI3, R.layout.support_simple_spinner_dropdown_item);
        sreasonNegativeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        reasonNegativeSpinner.setAdapter(sreasonNegativeSpinnerAdapter);

        /**
         * END SETTING ADAPTERS
         */
        /**
         * LOCATION BUTTON
         */

        final AppLocationService appLocationService = new AppLocationService(
                this);
        /**
         * LOCATION END
         */

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(counter.getCounter() <  3)
                    EXIT_CODE = false;

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
                 *LocationFetching
                 */
//                final AppLocationService appLocationService = new AppLocationService(
//                        BusinessActivity.this);


                Location nwLocation = appLocationService
                        .getLocation();

                if (nwLocation != null) {


                    double latitude = nwLocation.getLatitude();
                    double longitude = nwLocation.getLongitude();

//                    locText.setText(latitude + " " + longitude);

                    lat.setText(String.valueOf(latitude));
                    lng.setText(String.valueOf(longitude));

//                    GOT_LOCATION = true;
                }


                /**
                 * Fetched
                 */

                if(nwLocation != null)
                {
                    seaseLocSpinner = easeLocSpinner.getSelectedItem().toString();
                    slocTypeSpinner = locTypeSpinner.getSelectedItem().toString();
                    saddConfirmSpinner = addConfirmSpinner.getSelectedItem().toString();
                    sdoesAppWorkSpinner = doesAppWorkSpinner.getSelectedItem().toString();
                    sofficeNameBoardSpinner = officeNameBoardSpinner.getSelectedItem().toString();
                    sorgTypeSpinner = orgTypeSpinner.getSelectedItem().toString();
                    svisitingCardObtSpinner = visitingCardObtSpinner.getSelectedItem().toString();
                    snatureBusinessSpinner = natureBusinessSpinner.getSelectedItem().toString();
                    stypeOfJobSpinner = (String) typeOfJobSpinner.getSelectedItem().toString();
                    sworkingAsSpinner = workingAsSpinner.getSelectedItem().toString();
                    sjobTransferSpinner = jobTransferSpinner.getSelectedItem().toString();
                    stpcConfirmSpinner = tpcConfirmSpinner.getSelectedItem().toString();
                    soverallStatusSpinner = overallStatusSpinner.getSelectedItem().toString();
                    sreasonNegativeSpinner = reasonNegativeSpinner.getSelectedItem().toString();
                    sremark = remark.getText().toString().trim();

                    saddress = address.getText().toString().trim();
                    scompanyName = companyName.getText().toString().trim();
                    slandmark = landmark.getText().toString().trim();
                    sdesignOfApp = designOfApp.getText().toString().trim();
                    spersonMet = personMet.getText().toString().trim();
                    sdesignOfPersonMet = designOfPersonMet.getText().toString().trim();
                    spersonContactNo = personContactNo.getText().toString().trim();
                    sofficeTele = officeTele.getText().toString().trim();
                    sdateOfJoin = dateOfJoin.getText().toString().trim();
                    snoOfEmp = noOfEmp.getText().toString().trim();
                    spersonContacted = personContacted.getText().toString().trim();
                    sdesigPersonContacted = desigPersonContacted.getText().toString().trim();
                    snameOfRepManager = nameOfRepManager.getText().toString().trim();
                    sdesigRepoManager = desigRepoManager.getText().toString().trim();
                    scontactNoOfRepoManager = contactNoOfRepoManager.getText().toString().trim();
                    ssalary = salary.getText().toString().trim();
                    stpcPersonName = tpcPersonName.getText().toString().trim();

                    slati = lat.getText().toString().trim();
                    slongi = lng.getText().toString().trim();

//                    Toast.makeText(EmploymentActivity.this, slati + " " + slongi , Toast.LENGTH_SHORT).show();


                    /**
                     * RETROFIT MAGIC
                     */
                    retroFitHelper(TABLENAME,
                            StringCaseNo,
                            saddress,
                            seaseLocSpinner,
                            scompanyName,
                            slocTypeSpinner,
                            saddConfirmSpinner,
                            slandmark,
                            sdesignOfApp,
                            spersonMet,
                            sdesignOfPersonMet,
                            spersonContactNo,
                            sdoesAppWorkSpinner,
                            sofficeTele,
                            sofficeNameBoardSpinner,
                            sorgTypeSpinner,
                            sdateOfJoin,
                            svisitingCardObtSpinner,
                            snatureBusinessSpinner,
                            stypeOfJobSpinner,
                            sworkingAsSpinner,
                            sjobTransferSpinner,
                            snoOfEmp,
                            spersonContacted,
                            sdesigPersonContacted,
                            snameOfRepManager,
                            sdesigRepoManager,
                            scontactNoOfRepoManager,
                            ssalary,
                            stpcConfirmSpinner,
                            stpcPersonName,
                            soverallStatusSpinner,
                            sreasonNegativeSpinner,
                            slati, slongi,sremark);
                }

                else {
//                    Toast.makeText(getApplicationContext(), "FETCHING LOCATION...", Toast.LENGTH_SHORT).show();

                    Toast.makeText(EmploymentActivity.this, "Click again after a moment", Toast.LENGTH_LONG).show();

                    String []permissionsList={Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(EmploymentActivity.this,
                            permissionsList,
                            REQUEST_STRING_CODE);

                    displayLocationSettingsRequest(EmploymentActivity.this);

                }


                onSubmit();
            }

        });
    }

    public  void retroFitHelper(String TABLENAME,
                                String CASENO,
                                String ADDRESS,
                                String EASELOCATE,
                                String APPLCOMPANYNAME,
                                String LOCALITYTYPE,
                                String ADDRESSCONF,
                                String LANDMARK,
                                String APPLDESIGNATION,
                                String PERSONMET,
                                String PERSONDESIGNATION,
                                String PERSONCONTACT,
                                String DOESAPPLWORK,
                                String OFFICETEL,
                                String OFFICEBOARD,
                                String ORGANISATIONTYPE,
                                String DOJ,
                                String VISITINGCARD,
                                String NATUREBUSNIESS,
                                String JOBTYPE,
                                String WORKINGAS,
                                String JOBTRANSFER,
                                String NOSEMP,
                                String PERSONCONTACTED,
                                String PERSONCONDESIGNATION,
                                String MANAGER,
                                String MANAGERDESIGNATION,
                                String MANAGERCONTACT,
                                String SALARY,
                                String TPCCONFIRM,
                                String TPCNAME,
                                String OVERALLSTATUS,
                                String REASONNEGATIVEFI,
                                String LATITUDE,
                                String LONGITUDE,
                                String REMARKS)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.sendEmploymentData(TABLENAME,
                CASENO,
                ADDRESS,
                EASELOCATE,
                APPLCOMPANYNAME,
                LOCALITYTYPE,
                ADDRESSCONF,
                LANDMARK,
                APPLDESIGNATION,
                PERSONMET,
                PERSONDESIGNATION,
                PERSONCONTACT,
                DOESAPPLWORK,
                OFFICETEL,
                OFFICEBOARD,
                ORGANISATIONTYPE,
                DOJ,
                VISITINGCARD,
                NATUREBUSNIESS,
                JOBTYPE,
                WORKINGAS,
                JOBTRANSFER,
                NOSEMP,
                PERSONCONTACTED,
                PERSONCONDESIGNATION,
                MANAGER,
                MANAGERDESIGNATION,
                MANAGERCONTACT,
                SALARY,
                TPCCONFIRM,
                TPCNAME,
                OVERALLSTATUS,
                REASONNEGATIVEFI,
                LATITUDE,
                LONGITUDE,
                REMARKS);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body()==null)
                {
                    Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                }

                else if(response.body().equals("Success")) {

                    Toast.makeText(getApplicationContext(), "SUCCESSFULLY UPLOADED  ", Toast.LENGTH_SHORT).show();
                    /**
                     * Will receive something to verify
                     * successful upload to table
                     */

                    Intent intent = new Intent(EmploymentActivity.this, AssignmentChoose.class);
                    intent.putExtra("CASENO", StringCaseNo);
                    intent.putExtra("USERNAME", userName);
                    intent.putExtra("TYPEOFCASE", ACTIVITY);
                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "SOMETHING WENT WRONG"+response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "IN FAILURE", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.camerabutton, menu);
        return true;
    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("TAG", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("TAG", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(EmploymentActivity.this, 1);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("TAG", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("TAG", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }


        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.camera:

//                String enUser;
//                SharedPreferences preferences =getSharedPreferences("PDANOSHARED",Context.MODE_PRIVATE);
//                enUser = preferences.getString("ENPDANO", "");
//
//                logout(enUser);
                launchCamera();


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
                photoFile = createTempImageFile(EmploymentActivity.this);
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

    public void logout(String enUser)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.logout(enUser);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body()==null)
                {
                    Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                }

                else if(response.body().equals("Success"))
                {
                    SharedPreferences preferences =getSharedPreferences("PDANOSHARED",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    finish();

                    SharedPreferences preferences2 = getSharedPreferences("CASEDATA",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = preferences2.edit();
                    editor2.clear();
                    editor2.apply();
                    finish();
                    Intent i = new Intent(EmploymentActivity.this,LoginActivity.class);
                    startActivity(i);

                    Toast.makeText(getApplicationContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong :(" + response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No Internet/FAILURE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage();
        } else {

            // Otherwise, delete the temporary image file
            deleteImageFile(this, mTempPhotoPath);
        }
    }

    /**
     * Method for processing the captured image and setting it to the TextView.
     */
    private void processAndSetImage() {

        mResultsBitmap = getBitmap(mTempPhotoPath);
        /**
         * UPLOAD IMAGE USING RETROFIT
         */

        retroFitHelper(encodeImage(mResultsBitmap));

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
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mImageUrls,mImageNames,emptyView);
        recyclerView.setAdapter(adapter);
    }

    public void retroFitHelper(String encodedImage)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.imageUpload(encodedImage,globalImageFileName,StringCaseNo,ACTIVITY,userName);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body()==null)
                {
                    Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                }

                else if(response.body().equals("Success"))
                {
                    Toast.makeText(getApplicationContext(), "IMAGE UPLOAD SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    counter.addCounter();

                    if(counter.getCounter() >= 3)
                        EXIT_CODE = true;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "IMAGE UPLOAD UNSUCCESSFUL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "IN FAILURE", Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void onSubmit()
    {
        retrofitExit(StringCaseNo,ACTIVITY);

//                SharedPreferences preferences =getSharedPreferences("PDANOSHARED",Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.clear();
//                editor.apply();
//                finish();

        SharedPreferences preferences2 =getSharedPreferences("CASEDATA",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.clear();
        editor2.apply();
        finish();

        Intent backToAssignmentChoose = new Intent(EmploymentActivity.this,AssignmentChoose.class);
        startActivity(backToAssignmentChoose);
    }

    public  void retrofitExit(String caseNo, String caseType){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.exit(caseNo,caseType);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body().equals("Success"))
                {
                    Toast.makeText(getApplicationContext(), "COMPLETED", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "IN FAILURE", Toast.LENGTH_SHORT).show();


            }
        });

    }

    /**
     * Resamples the captured photo to fit the screen for better memory usage.
     *
     * @param context   The application context.
     * @param imagePath The path of the photo to be resampled.
     * @return The resampled bitmap
     */
    static Bitmap resamplePic(Context context, String imagePath) {

        // Get device screen size information
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        int targetH = metrics.heightPixels;
        int targetW = metrics.widthPixels;

        // Get the dimensions of the original bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(imagePath);
    }

    /**
     * Creates the temporary image file in the cache directory.
     *
     * @return The temporary image file.
     * @throws IOException Thrown if there is an error creating the file
     */
    static File createTempImageFile(Context context)
            throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = "AZTEK" + timeStamp + "_";

        globalImageFileName = imageFileName;

        File storageDir = context.getExternalCacheDir();

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    /**
     * Deletes image file for a given path.
     *
     * @param context   The application context.
     * @param imagePath The path of the photo to be deleted.
     */
    static boolean deleteImageFile(Context context, String imagePath) {

        // Get the file
        File imageFile = new File(imagePath);

        // Delete the image
        boolean deleted = imageFile.delete();

        // If there is an error deleting the file, show a Toast
        if (!deleted) {
            String errorMessage = context.getString(R.string.error);

        }

        return deleted;
    }

    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = false;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }

            Bitmap b = null;
            in = getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();

//                mod_width = actual_width*(180/actual_height)

                double y = 300;
                double x = ((double) width)*(y/((double) height));

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }


    private String encodeImage(Bitmap bm)
    {

        Toast.makeText(getApplicationContext(), "In encodemessage", Toast.LENGTH_SHORT).show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        Toast.makeText(getApplicationContext(), "About TO return", Toast.LENGTH_SHORT).show();


        return encImage;
    }







}
