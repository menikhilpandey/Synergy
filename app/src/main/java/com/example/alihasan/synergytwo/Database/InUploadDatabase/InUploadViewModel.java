package com.example.alihasan.synergytwo.Database.InUploadDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.alihasan.synergytwo.Database.PropertyDatabase.Property;
import com.example.alihasan.synergytwo.Database.ResidenceDatabase.ResidenceRepo;

import java.util.List;

public class InUploadViewModel extends AndroidViewModel {

    InUploadRepo inUploadRepo;


    public InUploadViewModel(Application application) {
        super(application);

        inUploadRepo = new InUploadRepo(application);

    }

    public void insert(InUplaod inUplaod) {
        inUploadRepo.insert(inUplaod);
    }

    public void delete(InUplaod inUplaod) {
        inUploadRepo.delete(inUplaod);
    }

    public List<InUplaod> getAllData() {
        return inUploadRepo.getAllData();
    }

}
