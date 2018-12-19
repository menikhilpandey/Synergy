package com.example.alihasan.synergytwo.Assignments;

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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class EmploymentActivity extends AppCompatActivity {

    static String SERVER_URL = "http://87408ed5.ngrok.io/project/aztekgo/android/";

    /**
     * Total 30
     * EditText  16
     * Spinner 14
     */


    EditText caseNo;

    EditText companyName, landmark, designOfApp, personMet, designOfPersonMet, personContactNo, officeTele, dateOfJoin, noOfEmp,
            personContacted, desigPersonContacted, nameOfRepManager,desigRepoManager, contactNoOfRepoManager, salary, tpcPersonName;

    Spinner easeLocSpinner, locTypeSpinner, addConfirmSpinner, doesAppWorkSpinner, officeNameBoardSpinner, orgTypeSpinner,
            visitingCardObtSpinner, natureBusinessSpinner, jobTypeSpinner, workingAsSpinner, jobTransferSpinner, tpcConfirmSpinner,
            overallStatusSpinner, reasonNegativeSpinner;

    /**
     * EDITTEXT AND SPINNER
     * STRING VALUES
     */

    String scompanyName, slandmark, sdesignOfApp, spersonMet, sdesignOfPersonMet, spersonContactNo, sofficeTele, sdateOfJoin, snoOfEmp,
            spersonContacted, sdesigPersonContacted, snameOfRepManager,sdesigRepoManager, scontactNoOfRepoManager, ssalary, stpcPersonName;

    String seaseLocSpinner, slocTypeSpinner, saddConfirmSpinner, sdoesAppWorkSpinner, sofficeNameBoardSpinner, sorgTypeSpinner,
            svisitingCardObtSpinner, snatureBusinessSpinner, sjobTypeSpinner, sworkingAsSpinner, sjobTransferSpinner, stpcConfirmSpinner,
            soverallStatusSpinner, sreasonNegativeSpinner;

    /**
     * SPINNER ADAPTER
     */

    ArrayAdapter<CharSequence> seaseLocSpinnerAdapter, slocTypeSpinnerAdapter, saddConfirmSpinnerAdapter, sdoesAppWorkSpinnerAdapter,
            sofficeNameBoardSpinnerAdapter, sorgTypeSpinnerAdapter,
            svisitingCardObtSpinnerAdapter, sNatureBusinessSpinnerAdapter, sjobTypeSpinnerAdapter, sworkingAsSpinnerAdapter,
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment);

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
        jobTypeSpinner = findViewById(R.id.jobTypeSpinner);
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
        //            svisitingCardObtSpinnerAdapter, sNatureBusinessSpinnerAdapter, sjobTypeSpinnerAdapter, sworkingAsSpinnerAdapter,
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

        sjobTypeSpinnerAdapter = ArrayAdapter.createFromResource(EmploymentActivity.this, R.array.JOBTYPE, R.layout.support_simple_spinner_dropdown_item);
        sjobTypeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        jobTransferSpinner.setAdapter(sjobTypeSpinnerAdapter);

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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLocationServicesAvailable(EmploymentActivity.this)) {

                    Log.d("THIS","HERE");
                    dialog = new ProgressDialog(EmploymentActivity.this);
                    dialog.setMessage("Getting Your location....");
                    dialog.show();
                    if (ActivityCompat.checkSelfPermission(EmploymentActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EmploymentActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    if (ActivityCompat.checkSelfPermission(EmploymentActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EmploymentActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                            new AlertDialog.Builder(EmploymentActivity.this);
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
        });

        /**
         * LOCATION END
         */

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seaseLocSpinner = easeLocSpinner.getSelectedItem().toString();
                slocTypeSpinner = locTypeSpinner.getSelectedItem().toString();
                saddConfirmSpinner = addConfirmSpinner.getSelectedItem().toString();
                sdoesAppWorkSpinner = doesAppWorkSpinner.getSelectedItem().toString();
                sofficeNameBoardSpinner = officeNameBoardSpinner.getSelectedItem().toString();
                sorgTypeSpinner = orgTypeSpinner.getSelectedItem().toString();
                svisitingCardObtSpinner = visitingCardObtSpinner.getSelectedItem().toString();
                snatureBusinessSpinner = natureBusinessSpinner.getSelectedItem().toString();
                sjobTypeSpinner = jobTypeSpinner.getSelectedItem().toString();
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
                        sjobTypeSpinner,
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

                if(response.body().equals("Success")) {

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
                    dialog = new ProgressDialog(EmploymentActivity.this);
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
}
