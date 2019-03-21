package com.example.alihasan.synergytwo.Database.PropertyDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.alihasan.synergytwo.Database.AssignmentDatabase;
import com.example.alihasan.synergytwo.Database.ResidenceDatabase.Residence;
import com.example.alihasan.synergytwo.Database.ResidenceDatabase.ResidenceDao;
import com.example.alihasan.synergytwo.Database.ResidenceDatabase.ResidenceRepo;

import java.util.List;

public class PropertyRepo {

    private PropertyDao mPropertyDao;

    public PropertyRepo(Application application) {
        AssignmentDatabase db = AssignmentDatabase.getDataBase(application);
        mPropertyDao = db.propertyDao();
    }

    int getCount(){
        return mPropertyDao.getCount();
    }

    public void insert(Property property) {
        new insertAsyncTask(mPropertyDao).execute(property);
    }

    public void delete(Property property) {
        new deleteAsyncTask(mPropertyDao).execute(property);
    }

    public List<Property> getAllData() {
        return new allDataAsyncTask(mPropertyDao).doInBackground();
    }



    private static class allDataAsyncTask extends AsyncTask<Void, Void, List<Property>> {

        private PropertyDao mAsyncTaskDao;

        allDataAsyncTask(PropertyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Property> doInBackground(final Void... params) {

            return mAsyncTaskDao.getAllData();
        }
    }

    private static class insertAsyncTask extends AsyncTask<Property, Void, Void> {

        private PropertyDao mAsyncTaskDao;

        insertAsyncTask(PropertyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Property... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Property, Void, Void> {

        private PropertyDao mAsyncTaskDao;

        deleteAsyncTask(PropertyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Property... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }



}
