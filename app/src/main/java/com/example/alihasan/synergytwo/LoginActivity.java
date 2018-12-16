package com.example.alihasan.synergytwo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alihasan.synergytwo.Assignments.AssignmentChoose;
import com.example.alihasan.synergytwo.api.service.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText userEditText;
    EditText passEditText;
    static String SERVER_URL = "http://142.93.217.100/repignite/android/";

    String strID, strPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        userEditText = findViewById(R.id.userNameEditText);
        passEditText = findViewById(R.id.passEditText);

        /**
         * HAVE TO WRITE CHECK FOR NETWORK
         */
        //Assuming network is available

        /**
         * START OF RETROFIT
         */

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.getAuth(strID,strPass);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(Integer.parseInt(response.body().toString()) == 1)
                {
                    //ASSIGNMENT CHOOSE
                    Intent i = new Intent(LoginActivity.this,AssignmentChoose.class);
                    i.putExtra("USERNAME",strID);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "INVALID USER OR PASS", Toast.LENGTH_SHORT).show();


            }
        });

        /**
         * END OF RETROFIT
         */



    }
}
