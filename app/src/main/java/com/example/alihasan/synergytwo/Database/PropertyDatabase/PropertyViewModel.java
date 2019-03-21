package com.example.alihasan.synergytwo.Database.PropertyDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.alihasan.synergytwo.Database.ResidenceDatabase.Residence;
import com.example.alihasan.synergytwo.Database.ResidenceDatabase.ResidenceRepo;

import java.util.List;

public class PropertyViewModel extends AndroidViewModel {

    PropertyRepo propertyRepo;

    public PropertyViewModel(@NonNull Application application) {
        super(application);

        propertyRepo = new PropertyRepo(application);

    }

    public int getCount() {
        return propertyRepo.getCount();
    }

    public void insert(Property property) {
        propertyRepo.insert(property);
    }

    public void delete(Property property) {
        propertyRepo.delete(property);
    }

    public List<Property> getAllData() {
        return propertyRepo.getAllData();
    }

}
