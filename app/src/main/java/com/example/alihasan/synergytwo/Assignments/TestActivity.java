package com.example.alihasan.synergytwo.Assignments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alihasan.synergytwo.Adapters.DebtorAdapter;
import com.example.alihasan.synergytwo.Database.Business;
import com.example.alihasan.synergytwo.Database.BusinessViewModel;
import com.example.alihasan.synergytwo.Database.DebtorDatabase.DebtorViewModel;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageParam;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageViewModel;
import com.example.alihasan.synergytwo.R;
import com.example.alihasan.synergytwo.api.model.Debtor;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private DebtorViewModel imageViewModel;
    private RecyclerView recyclerView;
    private TextView textText;
    private DebtorAdapter mAdapter;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recyclerView = findViewById(R.id.testRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(TestActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.invalidate();
//
        imageViewModel = ViewModelProviders.of(this).get(DebtorViewModel.class);
//
        imageViewModel.insert(new Debtor("plm","rg","qwer","asdf", "pqa", "afsd"));

        imageViewModel.updateInUploads("eyt", "ryte");

        textText = findViewById(R.id.textTester);

        imageViewModel.getAllDebtor().observe(this, new Observer<List<Debtor>>() {
            @Override
            public void onChanged(@Nullable List<Debtor> debtors) {
                mAdapter = new DebtorAdapter(TestActivity.this, debtors, "afa",((MyApplication)getApplicationContext()).myGlobalArray);
                recyclerView.setAdapter(mAdapter);
            }
        });
//
        textText.setText(imageViewModel.fetchInUploads("eyt", "ryte") + "Hi");




    }
}
