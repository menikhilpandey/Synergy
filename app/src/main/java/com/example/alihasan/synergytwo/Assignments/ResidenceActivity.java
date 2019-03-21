package com.example.alihasan.synergytwo.Assignments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import com.example.alihasan.synergytwo.Adapters.RecyclerViewAdapter;
import com.example.alihasan.synergytwo.ClassHelper.PhotoHelper;
import com.example.alihasan.synergytwo.CounterSingleton;
import com.example.alihasan.synergytwo.Database.BusinessViewModel;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageParam;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageViewModel;
import com.example.alihasan.synergytwo.Database.ResidenceDatabase.Residence;
import com.example.alihasan.synergytwo.Database.ResidenceDatabase.ResidenceViewModel;
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
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
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
import android.widget.RelativeLayout;
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

public class ResidenceActivity extends AppCompatActivity {

    /**
     * DD test
     */
    private ResidenceViewModel residenceViewModel;
    private ImageViewModel imageViewModel   ;
    /**
     *
     */

    static String SERVER_URL = new ServerURL().getSERVER_URL();

    /**
     * Total 36
     * EditText  18
     * Spinner 18
     */

    EditText address;
    String saddress;

    EditText age, landmark, stayingSince, personContacted, noFamilyMem, working, dependentAmem,
            dependentCmem,retiredMember, spouseWorkDetail, neighbourName1, address1, neighbourName2, address2, addProof,remark;

    Spinner easeToLocSpinner, locTypeSpinner, houseTypeSpinner, houseCondSpinner, owenershipSpinner, livingStandSpinner, appStaySpinner,
            relationshipSpinner, accoTypeSpinner, exteriorSpinner, spouseEarnSpinner, maritalStatSpinner, educatQualSpinner,
            neighbourFeedSpinner, vehicalSeenSpinner , politiclLinkSpinner, overallStatusSpinner, reasonNegativeSpinner;


    /**
     * EDITTEXT AND SPINNER
     * STRING VALUES
     */

    String sapplicantName,
            salternateTele,
            sage,
            slandmark,
            sstayingSince,
            spersonContacted,
            snoFamilyMem,
            sworking,
            sdependentAmem,
            sdependentCmem,
            sretiredMember,
            sspouseWorkDetail,
            sneighbourName1,
            saddress1,
            sneighbourName2,
            saddress2,
            saddProof,
            sremark;


    String seaseToLocSpinner,
            slocTypeSpinner,
            shouseTypeSpinner,
            shouseCondSpinner,
            sowenershipSpinner,
            slivingStandSpinner,
            sappStaySpinner,
            srelationshipSpinner,
            saccoTypeSpinner,
            sexteriorSpinner,
            sspouseEarnSpinner,
            smaritalStatSpinner,
            seducatQualSpinner,
            sneighbourFeedSpinner,
            svehicalSeenSpinner ,
            spoliticlLinkSpinner,
            soverallStatusSpinner,
            sreasonNegativeSpinner;


    /**
     * SPINNER ADAPTER
     */

    ArrayAdapter<CharSequence> easeToLocSpinnerAdapter,
            locTypeSpinnerAdapter,
            houseTypeSpinnerAdapter,
            houseCondSpinnerAdapter,
            owenershipSpinnerAdapter,
            livingStandSpinnerAdapter,
            appStaySpinnerAdapter,
            relationshipSpinnerAdapter,
            accoTypeSpinnerAdapter,
            exteriorSpinnerAdapter,
            spouseEarnSpinnerAdapter,
            maritalStatSpinnerAdapter,
            educatQualSpinnerAdapter,
            neighbourFeedSpinnerAdapter,
            vehicalSeenSpinnerAdapter,
            politiclLinkSpinnerAdapter,
            overallStatusSpinnerAdapter,
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


    String ACTIVITY = "RESIDENCE";

    String TABLENAME = "cases-residence";

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

    PhotoHelper photoHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residence);

        /**
         * DD test
         */
        residenceViewModel = ViewModelProviders.of(this).get(ResidenceViewModel.class);
        imageViewModel = ViewModelProviders.of(this).get(ImageViewModel.class);

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

        displayLocationSettingsRequest(ResidenceActivity.this);

        photoHelper = new PhotoHelper(ResidenceActivity.this);


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

        age = findViewById(R.id.age);
        landmark = findViewById(R.id.landmark);
        stayingSince = findViewById(R.id.stayingSince);
        personContacted = findViewById(R.id.personContacted);
        noFamilyMem = findViewById(R.id.familyMembers);
        working = findViewById(R.id.working);
        dependentAmem = findViewById(R.id.depAdultMember);
        dependentCmem = findViewById(R.id.depChildrenMember);
        retiredMember = findViewById(R.id.retiredMember);
        spouseWorkDetail = findViewById(R.id.spouseWorkingDetail);
        neighbourName1 = findViewById(R.id.neighbourName1);
        address1 = findViewById(R.id.address1);
        neighbourName2 = findViewById(R.id.neighbourName2);
        address2 = findViewById(R.id.address2);
        addProof = findViewById(R.id.addProofDetail);
        remark = findViewById(R.id.remarks);

        //Spinner
        easeToLocSpinner = findViewById(R.id.easeLocSpinner);
        locTypeSpinner = findViewById(R.id.localityTypeSpinner);
        houseTypeSpinner = findViewById(R.id.houseTypeSpinner);
        houseCondSpinner = findViewById(R.id.houseConditionSpinner);
        owenershipSpinner = findViewById(R.id.ownershipSpinner);
        livingStandSpinner = findViewById(R.id.livingStandardSpinner);
        appStaySpinner = findViewById(R.id.applicantStaySpinner);
        relationshipSpinner = findViewById(R.id.relationshipSpinner);
        accoTypeSpinner = findViewById(R.id.accomodationTypeSPinner);
        exteriorSpinner = findViewById(R.id.exteriorSpinner);
        spouseEarnSpinner = findViewById(R.id.spouseEarningSpinner);
        maritalStatSpinner = findViewById(R.id.maritalStatusSpinner);
        educatQualSpinner = findViewById(R.id.educationalQualiSPinner);
        neighbourFeedSpinner = findViewById(R.id.neighbourFeedbackSpinner);
        vehicalSeenSpinner = findViewById(R.id.vehicalSeenSpinner);
        politiclLinkSpinner = findViewById(R.id.polLinkSpinner);
        overallStatusSpinner = findViewById(R.id.overallStatusSpinner);
        reasonNegativeSpinner = findViewById(R.id.reasonNegativeSpinner);

        nextButton = findViewById(R.id.nextButton);
        locationButton = findViewById(R.id.locationButton);

        lat = findViewById(R.id.lat);
        lng = findViewById(R.id.lng);


        /**
         * SETTING SPINNER ADAPTERS
         */

        //easeToLocSpinnerAdapter,
        //            locTypeSpinnerAdapter,
        //            houseTypeSpinnerAdapter,
        //            houseCondSpinnerAdapter,
        //            owenershipSpinnerAdapter,
        //            livingStandSpinnerAdapter,
        //            appStaySpinnerAdapter,
        //            relationshipSpinnerAdapter,
        //            accoTypeSpinnerAdapter,
        //            exteriorSpinnerAdapter,
        //            spouseEarnSpinnerAdapter,
        //            maritalStatSpinnerAdapter,
        //            educatQualSpinnerAdapter,
        //            neighbourFeedSpinnerAdapter,
        //             vehicalSeenSpinnerAdapter,
        //            politiclLinkSpinnerAdapter,
        //            overallStatusSpinnerAdapter,
        //            reasonNegativeSpinnerAdapter;

        easeToLocSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.EASELOCATE1, R.layout.support_simple_spinner_dropdown_item);
        easeToLocSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        easeToLocSpinner.setAdapter(easeToLocSpinnerAdapter);

        locTypeSpinnerAdapter= ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.LOCALITYTYPE1, R.layout.support_simple_spinner_dropdown_item);
        locTypeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        locTypeSpinner.setAdapter(locTypeSpinnerAdapter);

        houseTypeSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.HOUSETYPE, R.layout.support_simple_spinner_dropdown_item);
        houseTypeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        houseTypeSpinner.setAdapter(houseTypeSpinnerAdapter);

        houseCondSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.HOUSECONDITION, R.layout.support_simple_spinner_dropdown_item);
        houseCondSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        houseCondSpinner.setAdapter(houseCondSpinnerAdapter);

        owenershipSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.OWNERSHIP, R.layout.support_simple_spinner_dropdown_item);
        owenershipSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        owenershipSpinner.setAdapter(owenershipSpinnerAdapter);

        livingStandSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.LIVINGSTANDARD, R.layout.support_simple_spinner_dropdown_item);
        livingStandSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        livingStandSpinner.setAdapter(livingStandSpinnerAdapter);

        appStaySpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.APPLSTAYATADDRESS1, R.layout.support_simple_spinner_dropdown_item);
        appStaySpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        appStaySpinner.setAdapter(appStaySpinnerAdapter);

        relationshipSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.RELATIONSHIP1, R.layout.support_simple_spinner_dropdown_item);
        relationshipSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        relationshipSpinner.setAdapter(relationshipSpinnerAdapter);

        accoTypeSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.ACCOMODATIONTYPE, R.layout.support_simple_spinner_dropdown_item);
        accoTypeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        accoTypeSpinner.setAdapter(accoTypeSpinnerAdapter);

        exteriorSpinnerAdapter  = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.EXTERIOR, R.layout.support_simple_spinner_dropdown_item);
        exteriorSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        exteriorSpinner.setAdapter(exteriorSpinnerAdapter);

        spouseEarnSpinnerAdapter  = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.SPOUSEEARNING, R.layout.support_simple_spinner_dropdown_item);
        spouseEarnSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spouseEarnSpinner.setAdapter(spouseEarnSpinnerAdapter);

        maritalStatSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.MARITALSTATUS, R.layout.support_simple_spinner_dropdown_item);
        maritalStatSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        maritalStatSpinner.setAdapter(maritalStatSpinnerAdapter);

        educatQualSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.EDUQUAL, R.layout.support_simple_spinner_dropdown_item);
        educatQualSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        educatQualSpinner.setAdapter(educatQualSpinnerAdapter);

        neighbourFeedSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.NEIGHBOURFEEDBACK1, R.layout.support_simple_spinner_dropdown_item);
        neighbourFeedSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        neighbourFeedSpinner.setAdapter(neighbourFeedSpinnerAdapter);

        vehicalSeenSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.VEHICLESEEN1, R.layout.support_simple_spinner_dropdown_item);
        vehicalSeenSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        vehicalSeenSpinner.setAdapter(vehicalSeenSpinnerAdapter);

        politiclLinkSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.POLITICALLINK1, R.layout.support_simple_spinner_dropdown_item);
        politiclLinkSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        politiclLinkSpinner.setAdapter(politiclLinkSpinnerAdapter);

        overallStatusSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.OVERALLSTATUS1, R.layout.support_simple_spinner_dropdown_item);
        overallStatusSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        overallStatusSpinner.setAdapter(overallStatusSpinnerAdapter);

        reasonNegativeSpinnerAdapter = ArrayAdapter.createFromResource(ResidenceActivity.this, R.array.REASONNEGATIVEFI1, R.layout.support_simple_spinner_dropdown_item);
        reasonNegativeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        reasonNegativeSpinner.setAdapter(reasonNegativeSpinnerAdapter);

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

                if(counter.getCounter() >  3)
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
                    seaseToLocSpinner = easeToLocSpinner.getSelectedItem().toString();
                    slocTypeSpinner = locTypeSpinner.getSelectedItem().toString();
                    shouseTypeSpinner = houseTypeSpinner.getSelectedItem().toString();
                    shouseCondSpinner = houseCondSpinner.getSelectedItem().toString();
                    sowenershipSpinner = owenershipSpinner.getSelectedItem().toString();
                    slivingStandSpinner = livingStandSpinner.getSelectedItem().toString();
                    sappStaySpinner = appStaySpinner.getSelectedItem().toString();
                    srelationshipSpinner = relationshipSpinner.getSelectedItem().toString();
                    saccoTypeSpinner = accoTypeSpinner.getSelectedItem().toString();
                    sexteriorSpinner = exteriorSpinner.getSelectedItem().toString();
                    sspouseEarnSpinner = spouseEarnSpinner.getSelectedItem().toString();
                    smaritalStatSpinner = maritalStatSpinner.getSelectedItem().toString();
                    seducatQualSpinner = educatQualSpinner.getSelectedItem().toString();
                    sneighbourFeedSpinner = neighbourFeedSpinner.getSelectedItem().toString();
                    svehicalSeenSpinner  = vehicalSeenSpinner .getSelectedItem().toString();
                    spoliticlLinkSpinner = politiclLinkSpinner.getSelectedItem().toString();
                    soverallStatusSpinner = overallStatusSpinner.getSelectedItem().toString();
                    sreasonNegativeSpinner = reasonNegativeSpinner.getSelectedItem().toString();

                    saddress = address.getText().toString().trim();
                    sage = age.getText().toString().trim();
                    slandmark = landmark.getText().toString().trim();
                    sstayingSince = stayingSince.getText().toString().trim();
                    spersonContacted = personContacted.getText().toString().trim();
                    snoFamilyMem = noFamilyMem.getText().toString().trim();
                    sworking = working.getText().toString().trim();
                    sdependentAmem = dependentAmem.getText().toString().trim();
                    sdependentCmem = dependentCmem.getText().toString().trim();
                    sretiredMember = retiredMember.getText().toString().trim();
                    sspouseWorkDetail = spouseWorkDetail.getText().toString().trim();
                    sneighbourName1 = neighbourName1.getText().toString().trim();
                    saddress1 = address1.getText().toString().trim();
                    sneighbourName2 = neighbourName2.getText().toString().trim();
                    saddress2 = address2.getText().toString().trim();
                    saddProof = addProof.getText().toString().trim();
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
                            sage,
                            slocTypeSpinner,
                            shouseTypeSpinner,
                            shouseCondSpinner,
                            sowenershipSpinner,
                            slivingStandSpinner,
                            slandmark,
                            sstayingSince,
                            sappStaySpinner,
                            spersonContacted,
                            srelationshipSpinner,
                            saccoTypeSpinner,
                            sexteriorSpinner,
                            snoFamilyMem,
                            sworking,
                            sdependentAmem,
                            sdependentCmem,
                            sretiredMember,
                            sspouseEarnSpinner,
                            sspouseWorkDetail,
                            smaritalStatSpinner,
                            seducatQualSpinner,
                            sneighbourName1,
                            saddress1,
                            sneighbourName2,
                            saddress2,
                            sneighbourFeedSpinner,
                            saddProof,
                            svehicalSeenSpinner,
                            spoliticlLinkSpinner,
                            soverallStatusSpinner,
                            sreasonNegativeSpinner,
                            slati,
                            slongi,
                            sremark);
                }

                else {
//                    Toast.makeText(getApplicationContext(), "FETCHING LOCATION...", Toast.LENGTH_SHORT).show();

                    Toast.makeText(ResidenceActivity.this, "Click again after a moment", Toast.LENGTH_LONG).show();

                    String []permissionsList={Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(ResidenceActivity.this,
                            permissionsList,
                            REQUEST_STRING_CODE);

                    displayLocationSettingsRequest(ResidenceActivity.this);
                }

                onSubmit(StringCaseNo);

            }
        });
    }

    public void onSubmit(String stringCaseNo)
    {
        ((MyApplication)getApplicationContext()).myGlobalArray.add(stringCaseNo);

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

        Intent backToAssignmentChoose = new Intent(ResidenceActivity.this,AssignmentChoose.class);
        startActivity(backToAssignmentChoose);
    }

    public  void storeData(String TABLENAME,
                                String CASENO,
                                String ADDRESS,
                                String EASELOCATE,
                                String AGE,
                                String LOCALITYTYPE,
                                String HOUSETYPE,
                                String HOUSECONDITION,
                                String OWNERSHIP,
                                String LIVINGSTANDARD,
                                String LANDMARK,
                                String STAYINGSINCE,
                                String APPLSTAYATADDRESS,
                                String PERSONCONTACTED,
                                String RELATIONSHIP,
                                String ACCOMODATIONTYPE,
                                String EXTERIOR,
                                String NOOFFAMILY,
                                String WORKING,
                                String DEPENDENTADULTS,
                                String DEPENDENTCHILDREN,
                                String RETIREDMEMBER,
                                String SPOUSEEARNING,
                                String SPOUSEDETAILS,
                                String MARITALSTATUS,
                                String EDUQUAL,
                                String NEIGHBOURNAME1,
                                String ADDRESS1,
                                String NEIGHBOURNAME2,
                                String ADDRESS2,
                                String NEIGHBOURFEEDBACK,
                                String PROOFDETAILS,
                                String VEHICLESEEN,
                                String POLITICALLINK,
                                String OVERALLSTATUS,
                                String REASONNEGATIVEFI,
                                String LATITUDE,
                                String LONGITUDE,
                                String REMARKS)
    {


        residenceViewModel.insert(new Residence(TABLENAME, CASENO,
                ADDRESS,
                EASELOCATE,
                AGE,
                LOCALITYTYPE,
                HOUSETYPE,
                HOUSECONDITION,
                OWNERSHIP,
                LIVINGSTANDARD,
                LANDMARK,
                STAYINGSINCE,
                APPLSTAYATADDRESS,
                PERSONCONTACTED,
                RELATIONSHIP,
                ACCOMODATIONTYPE,
                EXTERIOR,
                NOOFFAMILY,
                WORKING,
                DEPENDENTADULTS,
                DEPENDENTCHILDREN,
                RETIREDMEMBER,
                SPOUSEEARNING,
                SPOUSEDETAILS,
                MARITALSTATUS,
                EDUQUAL,
                NEIGHBOURNAME1,
                ADDRESS1,
                NEIGHBOURNAME2,
                ADDRESS2,
                NEIGHBOURFEEDBACK,
                PROOFDETAILS,
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
                            status.startResolutionForResult(ResidenceActivity.this, 1);
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
                photoFile = photoHelper.createTempImageFile(ResidenceActivity.this);
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

            // Otherwise, delete the temporary image file
            photoHelper.deleteImageFile(this, mTempPhotoPath);
        }
    }

    /**
     * Method for processing the captured image and setting it to the TextView.
     */
    private void processAndSetImage() {

        mResultsBitmap = photoHelper.getBitmap(mTempPhotoPath,ResidenceActivity.this);
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

                    Toast.makeText(getApplicationContext(), "IMAGE UPLOAD SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    counter.addCounter();

                    if(counter.getCounter() >= 3)
                        EXIT_CODE = true;

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
}

