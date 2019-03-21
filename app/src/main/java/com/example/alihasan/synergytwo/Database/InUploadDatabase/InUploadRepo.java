package com.example.alihasan.synergytwo.Database.InUploadDatabase;

import android.app.Application;
import android.os.AsyncTask;

import com.example.alihasan.synergytwo.Database.AssignmentDatabase;
import com.example.alihasan.synergytwo.Database.PropertyDatabase.PropertyDao;
import com.example.alihasan.synergytwo.Database.ResidenceDatabase.Residence;
import com.example.alihasan.synergytwo.Database.ResidenceDatabase.ResidenceDao;
import com.example.alihasan.synergytwo.Database.ResidenceDatabase.ResidenceRepo;

import java.util.List;

public class InUploadRepo {

    private InUploadDao mInUploadDao;

    public InUploadRepo(Application application) {
        AssignmentDatabase db = AssignmentDatabase.getDataBase(application);
        mInUploadDao = db.inUploadDao();
    }

    public void insert(InUplaod inUplaod) {
        new insertAsyncTask(mInUploadDao).execute(inUplaod);
    }

    public void delete(InUplaod inUplaod) {
        new delteAsyncTask(mInUploadDao).execute(inUplaod);
    }

    public List<InUplaod> getAllData() {
        return new allDataAsyncTask(mInUploadDao).doInBackground();
    }


    private static class insertAsyncTask extends AsyncTask<InUplaod, Void, Void> {

        private InUploadDao mAsyncTaskDao;

        insertAsyncTask(InUploadDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final InUplaod... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class delteAsyncTask extends AsyncTask<InUplaod, Void, Void> {

        private InUploadDao mAsyncTaskDao;

        delteAsyncTask(InUploadDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final InUplaod... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class allDataAsyncTask extends AsyncTask<Void, Void, List<InUplaod>> {

        private InUploadDao mAsyncTaskDao;

        allDataAsyncTask(InUploadDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<InUplaod> doInBackground(final Void... params) {

            return mAsyncTaskDao.getAllData();
        }
    }


}
