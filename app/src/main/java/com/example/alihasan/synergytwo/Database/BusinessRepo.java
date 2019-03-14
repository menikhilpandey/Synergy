package com.example.alihasan.synergytwo.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class BusinessRepo {

    private BusinessDao mBusinessDao;
    private LiveData<List<Business>> mAllData;

    BusinessRepo(Application application) {
        AssignmentDatabase db = AssignmentDatabase.getDataBase(application);
        mBusinessDao = db.businessdao();
//        mAllData = mBusinessDao.getAllWords();
    }

//    LiveData<List<Business>> getAllWords() {
//        return mAllData;
//    }

    int getCount(){
        return mBusinessDao.getCount();
    }

    void delete(){
        new deleteAsyncTask(mBusinessDao).execute();
    }

    List<Business> testGetAllData(){
        return new TestGetAsynkTask(mBusinessDao).doInBackground();
    }

    private class TestGetAsynkTask extends AsyncTask<Void, Void,List<Business>>
    {

        private BusinessDao mAsyncTaskDao;

        TestGetAsynkTask(BusinessDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Business> doInBackground(Void... voids) {
            return mAsyncTaskDao.testGetAllWords();
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private BusinessDao mAsyncTaskDao;

        deleteAsyncTask(BusinessDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.delete();
            return null;
        }
    }

    public void insert (Business business) {
        new insertAsyncTask(mBusinessDao).execute(business);
    }


    private static class insertAsyncTask extends AsyncTask<Business, Void, Void> {

        private BusinessDao mAsyncTaskDao;

        insertAsyncTask(BusinessDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Business... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


}
