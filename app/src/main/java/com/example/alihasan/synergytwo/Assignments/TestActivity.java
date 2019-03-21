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
import android.widget.Toast;

import com.example.alihasan.synergytwo.Adapters.DebtorAdapter;
import com.example.alihasan.synergytwo.Database.Business;
import com.example.alihasan.synergytwo.Database.BusinessViewModel;
import com.example.alihasan.synergytwo.Database.DebtorDatabase.DebtorViewModel;
import com.example.alihasan.synergytwo.R;
import com.example.alihasan.synergytwo.api.model.Debtor;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private DebtorViewModel businessViewModel;
    private RecyclerView recyclerView;
    private DebtorAdapter mAdapter;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recyclerView = findViewById(R.id.recyclerViewTest);
        LinearLayoutManager manager = new LinearLayoutManager(TestActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.invalidate();

        businessViewModel = ViewModelProviders.of(this).get(DebtorViewModel.class);
//
        businessViewModel.insert(new Debtor("afadf","asfdafd","afdf","afds","afaf","afadf"));
        businessViewModel.insert(new Debtor("khlkjh","asfdafd","afdf","afds","afaf","afadf"));

//


        businessViewModel.getAllDebtor().observe(this, new Observer<List<Debtor>>() {
            @Override
            public void onChanged(@Nullable List<Debtor> debtors) {
                mAdapter = new DebtorAdapter(TestActivity.this, debtors, "someNO",((MyApplication)getApplicationContext()).myGlobalArray);
                recyclerView.setAdapter(mAdapter);
            }
        });



    }
}
