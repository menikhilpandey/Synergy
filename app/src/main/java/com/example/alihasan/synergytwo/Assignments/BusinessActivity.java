package com.example.alihasan.synergytwo.Assignments;

import com.example.alihasan.synergytwo.PhotoActivity;
import com.example.alihasan.synergytwo.R;
import com.example.alihasan.synergytwo.api.model.BusinessModel;
import com.example.alihasan.synergytwo.api.service.Client;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BusinessActivity extends AppCompatActivity {

    static String SERVER_URL = "http://142.93.217.100/repignite/android/";

    /**
     * 21 fields
     * 13 EditText
     * 8 Spinners
     */
    EditText desigAppl,perCont,desig,contact,offTele,namebusiness,bussNature,years,
            noEmployee,noBranches,appnamever,empsighted,remarks;

    String sdesigAppl,sperCont,sdesig,scontact,soffTele,snamebusiness,sbussNature,
            syears,snoEmployee,snoBranches,sappnamever,sempsighted,sremarks;

    Spinner typeCompany,vcard,nameboard,ambience,
            exterior,easeToLoc,bact,polaffl,recomm;

    String stypeCompany,svcard,snameboard,sambience,
            sexterior,seaseToLoc,sbact,spolaffl,srecomm;

    String slongi,slati;

    ArrayAdapter<CharSequence> typecompadapter,vcardadapter, nameBoardadapter,ambienceadapter,
            exterioradapter,locateadapter,bactadapter,polaffladapter,recommadapter;

    Button nextButton, locationButton;

    ProgressDialog dialog;

    TextView lat,lng;
    public static final int LOCATION_REQ_CODE = 100;
    LocationManager locationManager;
    private double latitude = 0;
    private double longitude = 0;

    Intent i = getIntent();
    String caseNo = i.getStringExtra("CASENO");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        //EditText desigAppl,perCont,desig,contact,offTele,namebusiness,bussNature,years,
        //            noEmployee,noBranches,appnamever,empsighted,remarks;

        desigAppl = (EditText) findViewById(R.id.persondesignation);
        perCont = (EditText) findViewById(R.id.perContacted);
        desig = (EditText) findViewById(R.id.designationeditText);
        contact = (EditText)findViewById(R.id.phoneeditText);
        offTele = (EditText)findViewById(R.id.offteleditText);
        namebusiness = (EditText)findViewById(R.id.namebusiness);
        bussNature = (EditText)findViewById(R.id.busnatureeditText);
        years = (EditText)findViewById(R.id.yearseditText);
        noEmployee = (EditText)findViewById(R.id.EmpeditText);
        noBranches = (EditText) findViewById(R.id.noOfbranches);
        appnamever = (EditText) findViewById(R.id.app_verifired_whom);
        empsighted = (EditText) findViewById(R.id.no_emp_in_prem);
        remarks = (EditText) findViewById(R.id.OtherRemarkseditText);


        nameboard = (Spinner)findViewById(R.id.nameboard);
        ambience = (Spinner) findViewById(R.id.amb_office);
        exterior = (Spinner) findViewById(R.id.exterior);
        easeToLoc = (Spinner) findViewById(R.id.easy_locate);
        bact = (Spinner) findViewById(R.id.business_activity);
        typeCompany= (Spinner) findViewById(R.id.spinnecompanytyper);
        vcard = (Spinner) findViewById(R.id.vcardspinner);
        recomm = (Spinner)findViewById(R.id.recomm);

        nextButton = (Button) findViewById(R.id.nextButton);
        locationButton = (Button) findViewById(R.id.locationButton);

        lat = (TextView) findViewById(R.id.lat);
        lng = (TextView) findViewById(R.id.lng);

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

//                String sname,sdesig,scontact,soffTele,sbussNature,sYearCompany,snoEmployee,
//                        sappnamever,snamebusiness,spdesig,sremarks,snoBranches,sempsighted;

                /**
                 * SPINNER ADAPTERS
                 */

//                Spinner typeCompany,vcard,nameboard,ambience,
//                        exterior,easeToLoc,bact,recomm;
//
//                String stypeCompany,svcard,snameboard,sambience,
//                        sexterior,seaseToLoc,sbact,srecomm;

                typecompadapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.company, R.layout.support_simple_spinner_dropdown_item);
                typecompadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                typeCompany.setAdapter(typecompadapter);
                stypeCompany = typeCompany.getSelectedItem().toString();

                vcardadapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.name_board, R.layout.support_simple_spinner_dropdown_item);
                vcardadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                vcard.setAdapter(nameBoardadapter);

                vcard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch (i) {
                            case 0:
                                svcard = "YES";
                                break;
                            case 1:
                                svcard = "NO";
                                break;
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                nameBoardadapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.name_board, R.layout.support_simple_spinner_dropdown_item);
                nameBoardadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                nameboard.setAdapter(nameBoardadapter);

                nameboard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch (i) {
                            case 0:
                                snameboard = "YES";
                                break;
                            case 1:
                                snameboard = "NO";
                                break;
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                ambienceadapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.ambience, R.layout.support_simple_spinner_dropdown_item);
                ambienceadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                ambience.setAdapter(ambienceadapter);

                ambience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch (i) {
                            case 0:
                                sambience = "AVERAGE";
                                break;
                            case 1:
                                sambience = "GOOD";
                                break;
                            case 2:
                                sambience = "POOR";
                                break;
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                exterioradapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.exteriors, R.layout.support_simple_spinner_dropdown_item);
                exterioradapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                exterior.setAdapter(exterioradapter);

                exterior.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch (i) {
                            case 0:
                                sexterior = "C1";
                                break;
                            case 1:
                                sexterior = "C2";
                                break;
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                locateadapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.easy_locate, R.layout.support_simple_spinner_dropdown_item);
                locateadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                easeToLoc.setAdapter(locateadapter);

                easeToLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch (i) {
                            case 0:
                                seaseToLoc = "YES";
                                break;
                            case 1:
                                seaseToLoc = "NO";
                                break;
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                bactadapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.business_Act, R.layout.support_simple_spinner_dropdown_item);
                bactadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                bact.setAdapter(bactadapter);

                bact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch (i) {
                            case 0:
                                sbact = "HIGH";
                                break;
                            case 1:
                                sbact = "MEDIUM";
                                break;
                            case 2:
                                sbact = "LOW";
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                polaffladapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.aff_pol_party, R.layout.support_simple_spinner_dropdown_item);
                polaffladapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                polaffl.setAdapter(polaffladapter);

                polaffl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch (i) {
                            case 0:
                                spolaffl = "YES";
                                break;
                            case 1:
                                spolaffl = "NO";
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                recommadapter = ArrayAdapter.createFromResource(BusinessActivity.this, R.array.recom_or_not, R.layout.support_simple_spinner_dropdown_item);
                recommadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                recomm.setAdapter(recommadapter);
                srecomm = recomm.getSelectedItem().toString();

                /**
                 * Spinner Adapter END
                 */

                /**
                 * EDIT TEXTS
                 */

                sdesigAppl = desigAppl.getText().toString().trim();
                sperCont = perCont.getText().toString().trim();
                sdesig = desig.getText().toString().trim();
                scontact = contact.getText().toString().trim();
                soffTele = offTele.getText().toString().trim();
                snamebusiness = namebusiness.getText().toString().trim();
                sbussNature = bussNature.getText().toString().trim();
                syears = years.getText().toString().trim();
                snoEmployee = noEmployee.getText().toString().trim();
                snoBranches = noBranches.getText().toString().trim();
                sappnamever = appnamever.getText().toString().trim();
                sempsighted = empsighted.getText().toString().trim();
                sremarks = remarks.getText().toString().trim();

                slati = lat.getText().toString().trim();
                slongi = lng.getText().toString().trim();


                /**
                 * EDIT TEXTS END
                 */

                //String stypeCompany,svcard,snameboard,sambience,
                //            sexterior,seaseToLoc,sbact,srecomm;

                BusinessModel data = new BusinessModel(sdesigAppl,sperCont,
                        sdesig,scontact,soffTele,snamebusiness,sbussNature,syears,
                        stypeCompany,svcard,snoEmployee,snoBranches,snameboard,
                        sambience,sexterior,sappnamever,sbact,sempsighted,
                        spolaffl,srecomm,srecomm,slati,slongi);

                /**
                 * RETROFIT MAGIC
                 */
                retroFitHelper(data);

            }
        });


    }

    public void retroFitHelper(BusinessModel data)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Client client = retrofit.create(Client.class);

        Call<ArrayList<BusinessModel>> call = client.sendBusinessData(data);

        call.enqueue(new Callback<ArrayList<BusinessModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BusinessModel>> call, Response<ArrayList<BusinessModel>> response) {
                Toast.makeText(getApplicationContext(), "SUCCESSFULLY UPLOADED  ", Toast.LENGTH_SHORT).show();
                /**
                 * Will receive something to verify
                 * successful upload to table
                 */

                Intent intent = new Intent(BusinessActivity.this,PhotoActivity.class);
                intent.putExtra("CASENO",caseNo);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ArrayList<BusinessModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "FAILURE SENDING DATA", Toast.LENGTH_SHORT).show();
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
