package com.example.alihasan.synergytwo.Database.EmploymentDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.alihasan.synergytwo.Database.BusinessRepo;

import java.util.List;

public class EmploymentViewModel extends AndroidViewModel {

    private EmploymentRepo employmentRepo;

    public EmploymentViewModel(@NonNull Application application) {
        super(application);

        employmentRepo = new EmploymentRepo(application);

    }

    public int getCount() {
        return employmentRepo.getCount();
    }

    public void insert(Employment employment) {
        employmentRepo.insert(employment);
    }

    public void delete(Employment employment) {
        employmentRepo.delete(employment);
    }

    public List<Employment> getAllData() {
        return employmentRepo.getAllData();
    }
}
