package com.example.alihasan.synergytwo.Assignments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alihasan.synergytwo.Adapters.DebtorAdapter;
import com.example.alihasan.synergytwo.LoginActivity;
import com.example.alihasan.synergytwo.R;
import com.example.alihasan.synergytwo.api.model.Debtor;
import com.example.alihasan.synergytwo.api.service.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssignmentChoose extends AppCompatActivity {

//    ListView listview;


    private RecyclerView recyclerView;
    private DebtorAdapter mAdapter;

    static String SERVER_URL = "http://be15ec7b.ngrok.io/project/aztekgo/android/";

//    Intent i = getIntent();

//    String pdaNo = i.getStringExtra("USERNAME");
//    String pdaNo = "PDA123";
    String pdaNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_choose);

        SharedPreferences loginData = getSharedPreferences("PDANOSHARED", Context.MODE_PRIVATE);
        pdaNo = loginData.getString("PDANO", "");

//        listview = findViewById(R.id.listViewData);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(AssignmentChoose.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.invalidate();

        /**
         * START OF RETROFIT
         */

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Client client = retrofit.create(Client.class);

        Call<List<Debtor>> call = client.getDebtors(pdaNo);

        call.enqueue(new Callback<List<Debtor>>() {
            @Override
            public void onResponse(Call<List<Debtor>> call, Response<List<Debtor>> response) {

                Toast.makeText(getApplicationContext(), "GOT RESPONSE", Toast.LENGTH_SHORT).show();

                List<Debtor> dataList = response.body();

                mAdapter = new DebtorAdapter(AssignmentChoose.this,dataList,pdaNo);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<List<Debtor>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "IN FAILURE"+ pdaNo, Toast.LENGTH_SHORT).show();
            }
        });


    }

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

                Intent i = new Intent(AssignmentChoose.this,LoginActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
