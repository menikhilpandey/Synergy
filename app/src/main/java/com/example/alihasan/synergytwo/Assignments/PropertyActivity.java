package com.example.alihasan.synergytwo.Assignments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PropertyActivity extends AppCompatActivity {

    static String SERVER_URL = new ServerURL().getSERVER_URL();

    /**
     * Total 23
     * EditText  10
     * Spinner 13
     */

    EditText caseNo;

    EditText personContacted, area, documentVerify, neighbourName1, address1, neighbourName2, address2, propertySoldWhom, remarkPurchaser;

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
            sremarkPurchaser;

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


    String BUSINESS_ACTIVITY = "PROPERTY";

    String TABLENAME = "cases-property";

    ProgressBar progressBar;

    private int REQUEST_STRING_CODE=1234;

    boolean GOT_LOCATION = false;

    TextView fetchingLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

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

        displayLocationSettingsRequest(PropertyActivity.this);


        progressBar=findViewById(R.id.progressBar);
        fetchingLocation = findViewById(R.id.fetchingLocation);

        SharedPreferences loginData = getSharedPreferences("PDANO", Context.MODE_PRIVATE);
        userName = loginData.getString("PDANO", "");

        SharedPreferences caseData = getSharedPreferences("CASEDATA", Context.MODE_PRIVATE);
        StringCaseNo = caseData.getString("CASENO", "");

        //EditText
        personContacted = findViewById(R.id.personContacted);
        area = findViewById(R.id.area);
        documentVerify = findViewById(R.id.docVerify);
        neighbourName1 = findViewById(R.id.neighbourName1);
        address1 = findViewById(R.id.address1);
        neighbourName2 = findViewById(R.id.neighbourName2);
        address2 = findViewById(R.id.address2);
        propertySoldWhom = findViewById(R.id.propertySoldWhom);
        remarkPurchaser = findViewById(R.id.remarkPurchaser);

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

                    spersonContacted = personContacted.getText().toString().trim();
                    sarea = area.getText().toString().trim();
                    sdocumentVerify = documentVerify.getText().toString().trim();
                    sneighbourName1 = neighbourName1.getText().toString().trim();
                    saddress1 = address1.getText().toString().trim();
                    sneighbourName2 = neighbourName2.getText().toString().trim();
                    saddress2 = address2.getText().toString().trim();
                    spropertySoldWhom = propertySoldWhom.getText().toString().trim();
                    sremarkPurchaser = remarkPurchaser.getText().toString().trim();

                    slati = lat.getText().toString().trim();
                    slongi = lng.getText().toString().trim();

                    /**
                     * RETROFIT MAGIC
                     */
                    retroFitHelper(TABLENAME,
                            StringCaseNo,
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
                            slongi);
                }

                else {
//                    Toast.makeText(getApplicationContext(), "FETCHING LOCATION...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(PropertyActivity.this, "Click again after a moment", Toast.LENGTH_LONG).show();

                    String []permissionsList={Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(PropertyActivity.this,
                            permissionsList,
                            REQUEST_STRING_CODE);

                    displayLocationSettingsRequest(PropertyActivity.this);

                }

            }
        });
    }

    public  void retroFitHelper(String TABLENAME,
                                String CASENO,
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
                                String LONGITUDE)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.sendPropertyData(
                TABLENAME,
                CASENO,
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

                    Intent intent = new Intent(PropertyActivity.this, PhotoActivity.class);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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
                            status.startResolutionForResult(PropertyActivity.this, 1);
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
            case R.id.logout:

                String enUser;
                SharedPreferences preferences =getSharedPreferences("PDANOSHARED",Context.MODE_PRIVATE);
                enUser = preferences.getString("ENPDANO", "");

                logout(enUser);

                return true;
            default:
                return super.onOptionsItemSelected(item);
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
                    Intent i = new Intent(PropertyActivity.this,LoginActivity.class);
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


}

