package com.example.alihasan.synergytwo.Database.DebtorDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.alihasan.synergytwo.Database.AssignmentDatabase;
import com.example.alihasan.synergytwo.api.model.Debtor;

import java.util.List;

public class DebtorRepo {

    private DebtorDao mDebtorDao;
    private LiveData<List<Debtor>> mAllDebtor;

    DebtorRepo(Application application){
        AssignmentDatabase db = AssignmentDatabase.getDataBase(application);
        mDebtorDao = db.debtorDao();
        mAllDebtor = mDebtorDao.getAllDebtor();
    }

    LiveData<List<Debtor>> getAllDebtor(){
        return mAllDebtor;
    }

    public void insert(Debtor debtor){
        new insertAsyncTask(mDebtorDao).execute(debtor);
    }

    public void delete(Debtor debtor){
        new deleteAsyncTask(mDebtorDao).execute(debtor);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(mDebtorDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Debtor, Void, Void> {

        private DebtorDao mAsyncTaskDao;

        insertAsyncTask(DebtorDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Debtor... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Debtor, Void, Void> {

        private DebtorDao mAsyncTaskDao;

        deleteAsyncTask(DebtorDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Debtor... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private DebtorDao mAsyncTaskDao;

        deleteAllAsyncTask(DebtorDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
