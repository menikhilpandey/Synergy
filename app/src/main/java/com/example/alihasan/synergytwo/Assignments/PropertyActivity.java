package com.example.alihasan.synergytwo.Assignments;

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

import com.example.alihasan.synergytwo.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class PropertyActivity extends AppCompatActivity {

    static String SERVER_URL = "http://87408ed5.ngrok.io/project/aztekgo/android/";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLocationServicesAvailable(PropertyActivity.this)) {

                    Log.d("THIS","HERE");
                    dialog = new ProgressDialog(PropertyActivity.this);
                    dialog.setMessage("Getting Your location....");
                    dialog.show();
                    if (ActivityCompat.checkSelfPermission(PropertyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PropertyActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    if (ActivityCompat.checkSelfPermission(PropertyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PropertyActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                            new AlertDialog.Builder(PropertyActivity.this);
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
                retroFitHelper();

            }
        });
    }

    public  void retroFitHelper()
    {

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
                    dialog = new ProgressDialog(PropertyActivity.this);
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
