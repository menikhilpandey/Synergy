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

public class BusinessActivity extends AppCompatActivity {

    static String SERVER_URL = "http://87408ed5.ngrok.io/project/aztekgo/android/";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLocationServicesAvailable(BusinessActivity.this)) {

                    Log.d("THIS","HERE");
                    dialog = new ProgressDialog(BusinessActivity.this);
                    dialog.setMessage("Getting Your location....");
                    dialog.show();
                    if (ActivityCompat.checkSelfPermission(BusinessActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BusinessActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    if (ActivityCompat.checkSelfPermission(BusinessActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BusinessActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                            new AlertDialog.Builder(BusinessActivity.this);
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

                if(response.body().equals("Success")) {

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
                    dialog = new ProgressDialog(BusinessActivity.this);
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
