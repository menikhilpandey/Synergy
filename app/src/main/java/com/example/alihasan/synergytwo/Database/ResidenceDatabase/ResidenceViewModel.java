package com.example.alihasan.synergytwo.Database.ResidenceDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.alihasan.synergytwo.Database.EmploymentDatabase.Employment;
import com.example.alihasan.synergytwo.Database.EmploymentDatabase.EmploymentRepo;

import java.util.List;

public class ResidenceViewModel extends AndroidViewModel {

    ResidenceRepo residenceRepo;

    public ResidenceViewModel(@NonNull Application application) {
        super(application);

        residenceRepo = new ResidenceRepo(application);

    }

    public int getCount() {
        return residenceRepo.getCount();
    }

    public void insert(Residence residence) {
        residenceRepo.insert(residence);
    }

    public void delete(Residence residence) {
        residenceRepo.delete(residence);
    }

    public List<Residence> getAllData() {
        return residenceRepo.getAllData();
    }
}
