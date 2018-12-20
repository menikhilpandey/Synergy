package com.example.alihasan.synergytwo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alihasan.synergytwo.Assignments.AssignmentChoose;
import com.example.alihasan.synergytwo.Encoder.md5;
import com.example.alihasan.synergytwo.api.service.Client;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText userEditText;
    EditText passEditText;

    private int REQUEST_STRING_CODE=1234;

    /**
     * TV
     */

    static String SERVER_URL = "http://be15ec7b.ngrok.io/project/aztekgo/android/";

    String strID, strPass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**
         * PERMISSION CHECK
         */

        String []permissionsList={Manifest.permission.CAMERA,
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

                SharedPreferences loginData = getSharedPreferences("PDANOSHARED", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginData.edit();
                editor.putString("PDANO", strID);
                editor.apply();

                String enUser = md5encoder(strID);
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
                        Toast.makeText(getApplicationContext(), "FAILURE", Toast.LENGTH_SHORT).show();
                    }
                });

                /**
                 * END OF RETROFIT
                 */
            }
        });

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
}
