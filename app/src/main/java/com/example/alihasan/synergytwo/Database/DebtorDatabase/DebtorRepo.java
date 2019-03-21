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

    public void updateInUpload(String typeCase, String caseNo) {
        new updateInUploadsAsyncTask(mDebtorDao, caseNo).execute(typeCase);
    }

    public String fetchInUpload(String typeCase, String caseNo) {
        return new fetchInUploadsAsyncTask(mDebtorDao, caseNo, typeCase).doInBackground();
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

    private static class updateInUploadsAsyncTask extends AsyncTask<String, Void, Void> {

        private DebtorDao mAsyncTaskDao;
        private String caseNo;

        updateInUploadsAsyncTask(DebtorDao dao, String caseNo) {
            mAsyncTaskDao = dao;
            this.caseNo = caseNo;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.updateInUpload(params[0], caseNo);
            return null;
        }
    }

    private static class fetchInUploadsAsyncTask extends AsyncTask<Void, Void, String> {

        private DebtorDao mAsyncTaskDao;
        private String caseNo;
        private String typeCase;

        fetchInUploadsAsyncTask(DebtorDao dao, String caseNo, String typeCase) {
            mAsyncTaskDao = dao;
            this.caseNo = caseNo;
            this.typeCase = typeCase;
        }

        @Override
        protected String doInBackground(final Void... params) {

            return mAsyncTaskDao.fetchInUpload(typeCase ,caseNo);
        }
    }
}
