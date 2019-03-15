package com.example.alihasan.synergytwo.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class BusinessViewModel extends AndroidViewModel {

    private BusinessRepo mRepository;

    private LiveData<List<Business>> mAllWords;

    public BusinessViewModel(Application application) {
        super(application);
        mRepository = new BusinessRepo(application);
//        mAllWords = mRepository.getAllWords();
    }

    public int getCount(){
        return mRepository.getCount();
    }

    public void insert(Business business) {
        mRepository.insert(business);
    }

    public void delete(){
        mRepository.delete();
    }

    public void deleteTest(Business business) {
        mRepository.deleteTest(business);
    }

    public List<Business> testGetAllData() {
        return mRepository.testGetAllData();
    }

}