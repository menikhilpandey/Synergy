package com.example.alihasan.synergytwo.Database.ResidenceDatabase;

import android.app.Application;
import android.os.AsyncTask;

import com.example.alihasan.synergytwo.Database.AssignmentDatabase;
import com.example.alihasan.synergytwo.Database.EmploymentDatabase.Employment;
import com.example.alihasan.synergytwo.Database.EmploymentDatabase.EmploymentDao;
import com.example.alihasan.synergytwo.Database.EmploymentDatabase.EmploymentRepo;

import java.util.List;

public class ResidenceRepo {

    private ResidenceDao mResidenceDao;

    ResidenceRepo(Application application) {
        AssignmentDatabase db = AssignmentDatabase.getDataBase(application);
        mResidenceDao = db.residenceDao();
    }

    int getCount(){
        return mResidenceDao.getCount();
    }

    public void insert(Residence residence) {
        new insertAsyncTask(mResidenceDao).execute(residence);
    }

    public void delete(Residence residence) {
        new deleteAsyncTask(mResidenceDao).execute(residence);
    }

    public List<Residence> getAllData() {
        return new allDataAsyncTask(mResidenceDao).doInBackground();
    }



    private static class allDataAsyncTask extends AsyncTask<Void, Void, List<Residence>> {

        private ResidenceDao mAsyncTaskDao;

        allDataAsyncTask(ResidenceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Residence> doInBackground(final Void... params) {

            return mAsyncTaskDao.getAllData();
        }
    }

    private static class insertAsyncTask extends AsyncTask<Residence, Void, Void> {

        private ResidenceDao mAsyncTaskDao;

        insertAsyncTask(ResidenceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Residence... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Residence, Void, Void> {

        private ResidenceDao mAsyncTaskDao;

        deleteAsyncTask(ResidenceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Residence... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }



}
