package com.example.alihasan.synergytwo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alihasan.synergytwo.Assignments.AssignmentChoose;
import com.example.alihasan.synergytwo.api.service.ServerURL;
import com.example.alihasan.synergytwo.Encoder.md5;
import com.example.alihasan.synergytwo.api.service.Client;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText userEditText;
    EditText passEditText;

    private int REQUEST_STRING_CODE = 1234;
    private int IN_APP_UPDATE = 1111;

    AppUpdateManager appUpdateManager;

    /**
     * TV
     */

    static String SERVER_URL = new ServerURL().getSERVER_URL();

    String strID, strPass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**
         * Support in-app Update
         */
        inAppUpdate();

        /**
         * Disable Screenshot
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        /**
         * PERMISSION CHECK
         */

        String []permissionsList={Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this,
                permissionsList,
                REQUEST_STRING_CODE);

        permissionCheckeToast();

        loginButton = findViewById(R.id.loginButton);
        userEditText = findViewById(R.id.userNameEditText);
        passEditText = findViewById(R.id.passEditText);

        /**
         * LOGINBUTTON hide for some time while auto login get executed
         */
//        userEditText.setVisibility(View.INVISIBLE);
//        passEditText.setVisibility(View.INVISIBLE);
//        loginButton.setVisibility(View.INVISIBLE);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                loginButton.setVisibility(View.VISIBLE);
//            }
//        }, 2000);

        /*
         * Auto LogIn feature
         */

        if(!getSharedPreferences("PDANOSHARED", Context.MODE_PRIVATE).getString("PDANO", "").equals("")){

            Intent i = new Intent(LoginActivity.this,AssignmentChoose.class);
            startActivity(i);

        }
//        else{
//            userEditText.setVisibility(View.VISIBLE);
//            passEditText.setVisibility(View.VISIBLE);
//            loginButton.setVisibility(View.VISIBLE);
//
//        }


        /**
         * feature end
         */



        /**
         * HAVE TO WRITE CHECK FOR NETWORK
         */
        //Assuming network is available

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * START OF RETROFIT
                 */

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Client client = retrofit.create(Client.class);

                strID = userEditText.getText().toString();
                strPass = passEditText.getText().toString();

                final String enUser = md5encoder(strID);
                String enPass = md5encoder(strPass);

                Call<String> call = client.getAuth(enUser,enPass);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if(response.body()==null)
                        {
                            Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                        }

                        else if(response.body().equals("Success"))
                        {
                            //ASSIGNMENT CHOOSE

                            SharedPreferences loginData = getSharedPreferences("PDANOSHARED", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = loginData.edit();
                            editor.putString("PDANO", strID);
                            editor.putString("ENPDANO",enUser);
                            editor.apply();

                            Intent i = new Intent(LoginActivity.this,AssignmentChoose.class);
//                            i.putExtra("USERNAME",strID);
                            startActivity(i);
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(), "INV U/P" + response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "No Internet/FAILURE", Toast.LENGTH_SHORT).show();
                    }
                });

                /**
                 * END OF RETROFIT
                 */
            }
        });

    }

    void inAppUpdate()
    {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                // Request the update.

                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            IN_APP_UPDATE);
                }

                catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                popupSnackbarForCompleteUpdate();
            } else {
                Log.e("IN_APP", "checkForAppUpdateAvailability: something else");
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IN_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Update flow failed! Result code: " + resultCode, Toast.LENGTH_SHORT).show();
                // If the update is cancelled or fails,
                // you can request to start the update again.
                popupSnackbarForCompleteUpdate();
            }
        }
    }

    public String md5encoder(String str)
    {
        byte[] md5Input = str.getBytes();

        BigInteger md5data = null;

        try {
            md5data = new BigInteger(1,md5.encryptMD5(md5Input));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String md5Str =  md5data.toString(16);
        if(md5Str.length() < 32){
            md5Str = 0 + md5Str;
        }

        return md5Str;
    }

    public void permissionCheckeToast(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getApplicationContext(), "GRANT PERMISSIONS", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.

                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            IMMEDIATE,
                                            this,
                                            IN_APP_UPDATE);
                                }

                                catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }

                        } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                                popupSnackbarForCompleteUpdate();
                            } else {
                                Log.e("IN_APP", "checkForAppUpdateAvailability: something else");
                            }
                        });
    }

    private void popupSnackbarForCompleteUpdate() {

        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.coordinatorLayout),
                        "Please update to continue...",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (appUpdateManager != null){
                inAppUpdate();
            }
        });

        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    private void upToDate() {

        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.coordinatorLayout),
                        "App up to date.",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Ok", view -> {
            snackbar.dismiss();
        });

        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }
}