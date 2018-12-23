package com.example.alihasan.synergytwo.Assignments;

import com.example.alihasan.synergytwo.LoginActivity;
import com.example.alihasan.synergytwo.PhotoActivity;
import com.example.alihasan.synergytwo.R;
import com.example.alihasan.synergytwo.api.service.AppLocationService;
import com.example.alihasan.synergytwo.api.service.Client;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
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

public class BusinessActivity extends AppCompatActivity {

    static String SERVER_URL = new ServerURL().getSERVER_URL();

    /**
     * 29 ELEMENTS
     * 18 EDITTEXTS
     * 11 SPINNER
     */

    EditText caseNo;

    EditText applName,address, contactNo, compName,
            businessNature, designation, workingSince,
            personContacted, desigPersonMet, empNo,
            landmark, branchNo, yearsPresentAdd,
            conVer1, conVer2, addProofDetail;

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
            sconVer1, sconVer2, saddProofDetail;

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

    TextView lat,lng;
    public static final int LOCATION_REQ_CODE = 100;
    LocationManager locationManager;
    private double latitude = 0;
    private double longitude = 0;

    Intent i = getIntent();
//        String StringCaseNo = i.getStringExtra("CASENO");
//    String userName = i.getStringExtra("USERNAME");

    String userName;
    String StringCaseNo;



    String BUSINESS_ACTIVITY = "BUSINESS";

    String TABLENAME = "cases-business";

    ProgressBar progressBar;

    private int REQUEST_STRING_CODE=1234;

    boolean GOT_LOCATION = false;

    TextView fetchingLocation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        /**
         * PERMISSION CHECKS
         */

        String []permissionsList={Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this,
                permissionsList,
                REQUEST_STRING_CODE);

        displayLocationSettingsRequest(BusinessActivity.this);


        progressBar = findViewById(R.id.progressBar);
        fetchingLocation = findViewById(R.id.fetchingLocation);

        SharedPreferences loginData = getSharedPreferences("PDANO", Context.MODE_PRIVATE);
        userName = loginData.getString("PDANO", "");

        SharedPreferences caseData = getSharedPreferences("CASEDATA", Context.MODE_PRIVATE);
        StringCaseNo = caseData.getString("CASENO","");

        //        EDITTEXTS

        applName = findViewById(R.id.applName);
        address = findViewById(R.id.address) ;
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

                    slati = lat.getText().toString().trim();
                    slongi = lng.getText().toString().trim();

                    /**
                     * EDIT TEXTS END
                     */

//                    Toast.makeText(BusinessActivity.this, slati + " " + slongi , Toast.LENGTH_SHORT).show();
                    //String stypeCompany,svcard,snameboard,sambience,
                    //            sexterior,seaseToLoc,sbact,srecomm;
                    /**
                     * RETROFIT MAGIC
                     */
                    retroFitHelper(TABLENAME,StringCaseNo,seaseLocSpinner,soffOwnershipSpinner,
                            scompName,slocalityTypeSpinner, sbusinessNature, sdesignation, sworkingSince,
                            spersonContacted, sdesigContacted, sempNo,
                            slandmark, sbranchNo,sbusinessSetupSpinner, sbusinessBoardSpinner, syearsPresentAdd,
                            svisCardSpinner,sapplVeriFrom,sconVer1, sconVer2,sconVeriFeed, saddProofDetail,
                            spolLinkSpinner, soverallStatusSpinner,sreasonNegativeSpinner,slati,slongi);
                }

                else {
//                    Toast.makeText(getApplicationContext(), "FETCHING LOCATION...", Toast.LENGTH_SHORT).show();

                    Toast.makeText(BusinessActivity.this, "Click again after a moment", Toast.LENGTH_LONG).show();

                    String []permissionsList={Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(BusinessActivity.this,
                            permissionsList,
                            REQUEST_STRING_CODE);

                    displayLocationSettingsRequest(BusinessActivity.this);


                }

            }
        });

    }


    public void retroFitHelper(final String TABLENAME,
                               final String CASENO,
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
                               final String LONGITUDE)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.sendBusinessData(TABLENAME, CASENO, EASELOCATE, OFFICEOWNERSHIP,
                APPLCOMPANYNAME, LOCALITYTYPE, NATUREBUSNIESS,
                APPLDESIGNATION, WORKINGSINCE, PERSONCONTACTED,
                PERSONDESIGNATION, NOSEMP, LANDMARK, NOSBRANCHES,
                BUSINESSSETUP, BUSINESSBOARD, NOSYEARSATADDRESS,
                VISITINGCARD, APPLNAMEVERIFFROM, CONTACTVERIF1,
                CONTACTVERIF2, CONTACTFEEDBACK, PROOFDETAILS,
                POLITICALLINK, OVERALLSTATUS, REASONNEGATIVEFI,
                LATITUDE, LONGITUDE);

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

                    Intent intent = new Intent(BusinessActivity.this, PhotoActivity.class);
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

//    LocationListener locationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//            Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
//
//            try {
//                List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                Address address=addresses.get(0);
//                String useradd="";
//                for(int i=0;i<address.getMaxAddressLineIndex();i++)
//                    useradd=useradd+address.getAddressLine(i).toString()+"\n";
//                useradd=useradd+(address.getCountryName().toString());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            dialog.dismiss();
//            latitude = location.getLatitude();
//            longitude =location.getLongitude();
//            if (latitude != 0 && longitude != 0){
//
//                    progressBar.setVisibility(View.GONE);
//                    fetchingLocation.setVisibility(View.GONE);
//
//
//                    lat.setText(""+location.getLatitude());
//                    lng.setText(""+location.getLongitude());
//
//                    dialog.dismiss();
//
//
//
//
//
//            }
//
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//            dialog.dismiss();
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//            dialog.dismiss();
//        }
//    };

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == LOCATION_REQ_CODE){
//            if(permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION)  {
//                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)  {
//                    dialog.setMessage("Getting Coordinates");
//                    dialog.show();
//                    //noinspection MissingPermission
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
//                    dialog = new ProgressDialog(BusinessActivity.this);
//                }
//            }
//        }
//    }

//    public static boolean isLocationServicesAvailable(Context context) {
//        int locationMode = 0;
//        String locationProviders;
//        boolean isAvailable = false;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            try {
//                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
//            } catch (Settings.SettingNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            isAvailable = (locationMode != Settings.Secure.LOCATION_MODE_OFF);
//        } else {
//            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//            isAvailable = !TextUtils.isEmpty(locationProviders);
//        }
//
//        boolean coarsePermissionCheck = (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
//        boolean finePermissionCheck = (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
//
//        return isAvailable && (coarsePermissionCheck || finePermissionCheck);
//    }

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

                Toast.makeText(BusinessActivity.this, "LOGGED OUT SUCCESSFULLY", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(BusinessActivity.this,LoginActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                            status.startResolutionForResult(BusinessActivity.this, 1);
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
}
