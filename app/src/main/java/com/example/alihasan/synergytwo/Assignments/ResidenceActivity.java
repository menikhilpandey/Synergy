package com.example.alihasan.synergytwo.Assignments;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

import com.example.alihasan.synergytwo.LoginActivity;
import com.example.alihasan.synergytwo.PhotoActivity;
import com.example.alihasan.synergytwo.R;
import com.example.alihasan.synergytwo.api.service.Client;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResidenceActivity extends AppCompatActivity {

    static String SERVER_URL = "http://be15ec7b.ngrok.io/project/aztekgo/android/";

    /**
     * Total 36
     * EditText  18
     * Spinner 18
     */

    EditText caseNo;

    EditText age, landmark, stayingSince, personContacted, noFamilyMem, working, dependentAmem,
            dependentCmem,retiredMember, spouseWorkDetail, neighbourName1, address1, neighbourName2, address2, addProof;

    Spinner easeToLocSpinner, locTypeSpinner, houseTypeSpinner, houseCondSpinner, owenershipSpinner, livingStandSpinner, appStaySpinner,
            relationshipSpinner, accoTypeSpinner, exteriorSpinner, spouseEarnSpinner, maritalStatSpinner, educatQualSpinner,
            neighbourFeedSpinner, vehicalSeenSpinner , politiclLinkSpinner, overallStatusSpinner, reasonNegativeSpinner;


    /**
     * EDITTEXT AND SPINNER
     * STRING VALUES
     */

    String sapplicantName,
            saddress,
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
            saddProof;


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


    String BUSINESS_ACTIVITY = "RESIDENCE";

    String TABLENAME = "cases-residence";

    ProgressBar progressBar;

    private int REQUEST_STRING_CODE=1234;

    boolean GOT_LOCATION = false;

    TextView fetchingLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residence);

        /**
         * PERMISSION CHECKS
         */

        String []permissionsList={Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this,
                permissionsList,
                REQUEST_STRING_CODE);

        progressBar=findViewById(R.id.progressBar);
        fetchingLocation = findViewById(R.id.fetchingLocation);


        SharedPreferences loginData = getSharedPreferences("PDANO", Context.MODE_PRIVATE);
        userName = loginData.getString("PDANO", "");

        SharedPreferences caseData = getSharedPreferences("CASEDATA", Context.MODE_PRIVATE);
        StringCaseNo = caseData.getString("CASENO", "");

        //EditText
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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(ContextCompat.checkSelfPermission(ResidenceActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {
            progressBar.setVisibility(View.VISIBLE);
            fetchingLocation.setVisibility(View.VISIBLE);
            GOT_LOCATION = true;

            if (isLocationServicesAvailable(ResidenceActivity.this)) {

                Log.d("THIS","HERE");
                dialog = new ProgressDialog(ResidenceActivity.this);
                dialog.setMessage("Getting Your location....");
                dialog.show();
                if (ActivityCompat.checkSelfPermission(ResidenceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ResidenceActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, locationListener);
                dialog.dismiss();
            }
            else {
                Log.d("THIS","HERE 2");
                if (ActivityCompat.checkSelfPermission(ResidenceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ResidenceActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(ResidenceActivity.this);
                final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
                final String message = "Enable either GPS or any other location"
                        + " service to find current location.  Click OK to go to"
                        + " location services settings to let you do so.";
                builder.setTitle("Enable Location");

                builder.setMessage(message)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface d, int id) {
                                        startActivity(new Intent(action));
                                        d.dismiss();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface d, int id) {
                                        d.cancel();
                                    }
                                }).show();
            }
        }

        else
        {
            Toast.makeText(getApplicationContext(), "GRANT LOCATION PERMISSION", Toast.LENGTH_SHORT).show();

            String []permissionsList2={Manifest.permission.ACCESS_COARSE_LOCATION};

            ActivityCompat.requestPermissions(ResidenceActivity.this,
                    permissionsList2,
                    REQUEST_STRING_CODE);
        }

        /**
         * LOCATION END
         */

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(GOT_LOCATION)
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

                    slati = lat.getText().toString().trim();
                    slongi = lng.getText().toString().trim();

                    /**
                     * RETROFIT MAGIC
                     */
                    retroFitHelper(TABLENAME,
                            StringCaseNo,
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
                            slongi);
                }

                else {
                    Toast.makeText(getApplicationContext(), "FETCHING LOCATION...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public  void retroFitHelper(String TABLENAME,
                                String CASENO,
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
                                String LONGITUDE)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.sendResidenceData(TABLENAME, CASENO,
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
                LONGITUDE);

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

                    Intent intent = new Intent(ResidenceActivity.this, PhotoActivity.class);
                    intent.putExtra("CASENO", StringCaseNo);
                    intent.putExtra("USERNAME", userName);
                    intent.putExtra("TYPEOFCASE", BUSINESS_ACTIVITY);
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

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());

            try {
                List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                Address address=addresses.get(0);
                String useradd="";
                for(int i=0;i<address.getMaxAddressLineIndex();i++)
                    useradd=useradd+address.getAddressLine(i).toString()+"\n";
                useradd=useradd+(address.getCountryName().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            dialog.dismiss();
            latitude = location.getLatitude();
            longitude =location.getLongitude();
            if (latitude != 0 && longitude != 0){

                progressBar.setVisibility(View.GONE);
                fetchingLocation.setVisibility(View.GONE);

                lat.setText(""+location.getLatitude());
                lng.setText(""+location.getLongitude());

                dialog.dismiss();
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            dialog.dismiss();
        }

        @Override
        public void onProviderDisabled(String provider) {
            dialog.dismiss();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_REQ_CODE){
            if(permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION)  {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)  {
                    dialog.setMessage("Getting Coordinates");
                    dialog.show();
                    //noinspection MissingPermission
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
                    dialog = new ProgressDialog(ResidenceActivity.this);
                }
            }
        }
    }

    public static boolean isLocationServicesAvailable(Context context) {
        int locationMode = 0;
        String locationProviders;
        boolean isAvailable = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            isAvailable = (locationMode != Settings.Secure.LOCATION_MODE_OFF);
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            isAvailable = !TextUtils.isEmpty(locationProviders);
        }

        boolean coarsePermissionCheck = (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        boolean finePermissionCheck = (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

        return isAvailable && (coarsePermissionCheck || finePermissionCheck);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.logout:

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

                Intent i = new Intent(ResidenceActivity.this,LoginActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
