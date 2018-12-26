package com.example.alihasan.synergytwo.Assignments;

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


    EditText caseNo;

    EditText companyName, landmark, designOfApp, personMet, designOfPersonMet, personContactNo, officeTele, dateOfJoin, noOfEmp,
            personContacted, desigPersonContacted, nameOfRepManager,desigRepoManager, contactNoOfRepoManager, salary, tpcPersonName;

    Spinner easeLocSpinner, locTypeSpinner, addConfirmSpinner, doesAppWorkSpinner, officeNameBoardSpinner, orgTypeSpinner,
            visitingCardObtSpinner, natureBusinessSpinner, typeOfJobSpinner, workingAsSpinner, jobTransferSpinner, tpcConfirmSpinner,
            overallStatusSpinner, reasonNegativeSpinner;

    /**
     * EDITTEXT AND SPINNER
     * STRING VALUES
     */

    String scompanyName, slandmark, sdesignOfApp, spersonMet, sdesignOfPersonMet, spersonContactNo, sofficeTele, sdateOfJoin, snoOfEmp,
            spersonContacted, sdesigPersonContacted, snameOfRepManager,sdesigRepoManager, scontactNoOfRepoManager, ssalary, stpcPersonName;

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


    String BUSINESS_ACTIVITY = "EMPLOYMENT";

    String TABLENAME = "cases-employment";

    ProgressBar progressBar;

    private int REQUEST_STRING_CODE=1234;

    boolean GOT_LOCATION = false;

    TextView fetchingLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment);

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

        displayLocationSettingsRequest(EmploymentActivity.this);


        progressBar=findViewById(R.id.progressBar);
        fetchingLocation = findViewById(R.id.fetchingLocation);


        SharedPreferences loginData = getSharedPreferences("PDANO", Context.MODE_PRIVATE);
        userName = loginData.getString("PDANO", "");

        SharedPreferences caseData = getSharedPreferences("CASEDATA", Context.MODE_PRIVATE);
        StringCaseNo = caseData.getString("CASENO", "");


        //EditText
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
                            slati, slongi);
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

            }


        });
    }

    public  void retroFitHelper(String TABLENAME,
                                String CASENO,
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
                                String LONGITUDE)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.sendEmploymentData(TABLENAME,
                CASENO,
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

                    Intent intent = new Intent(EmploymentActivity.this, PhotoActivity.class);
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


}
