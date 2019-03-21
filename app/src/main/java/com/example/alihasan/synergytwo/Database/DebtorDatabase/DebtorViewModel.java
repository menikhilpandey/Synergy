package com.example.alihasan.synergytwo.Database.DebtorDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.alihasan.synergytwo.api.model.Debtor;

import java.util.List;

public class DebtorViewModel extends AndroidViewModel {

    private DebtorRepo mDebtorRepo;
    private LiveData<List<Debtor>> mAllDebtor;

    public DebtorViewModel(Application application) {
        super(application);

        mDebtorRepo = new DebtorRepo(application);
        mAllDebtor = mDebtorRepo.getAllDebtor();
    }

    public LiveData<List<Debtor>> getAllDebtor(){
        return mAllDebtor;
    }

    public void insert(Debtor debtor){
        mDebtorRepo.insert(debtor);
    }

    public void delete(Debtor debtor){
        mDebtorRepo.delete(debtor);
    }

    public void deleteAll(){
        mDebtorRepo.deleteAll();
    }
}
