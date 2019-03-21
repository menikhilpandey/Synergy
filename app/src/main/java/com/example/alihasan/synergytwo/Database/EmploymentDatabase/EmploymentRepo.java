package com.example.alihasan.synergytwo.Database.EmploymentDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.alihasan.synergytwo.Database.AssignmentDatabase;
import com.example.alihasan.synergytwo.Database.Business;
import com.example.alihasan.synergytwo.Database.BusinessDao;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageDao;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageParam;

import java.util.List;

public class EmploymentRepo {

    private EmploymentDao mEmploymentDao;

    EmploymentRepo(Application application) {
        AssignmentDatabase db = AssignmentDatabase.getDataBase(application);
        mEmploymentDao = db.employmentDao();
    }

    int getCount(){
        return mEmploymentDao.getCount();
    }

    public void insert(Employment employment) {
        new insertAsyncTask(mEmploymentDao).execute(employment);
    }

    public void delete(Employment employment) {
        new deleteAsyncTask(mEmploymentDao).execute(employment);
    }

    public List<Employment> getAllData() {
        return new allDataAsyncTask(mEmploymentDao).doInBackground();
    }



    private static class allDataAsyncTask extends AsyncTask<Void, Void, List<Employment>> {

        private EmploymentDao mAsyncTaskDao;

        allDataAsyncTask(EmploymentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Employment> doInBackground(final Void... params) {

            return mAsyncTaskDao.getAllData();
        }
    }

    private static class insertAsyncTask extends AsyncTask<Employment, Void, Void> {

        private EmploymentDao mAsyncTaskDao;

        insertAsyncTask(EmploymentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Employment... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Employment, Void, Void> {

        private EmploymentDao mAsyncTaskDao;

        deleteAsyncTask(EmploymentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Employment... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


}
