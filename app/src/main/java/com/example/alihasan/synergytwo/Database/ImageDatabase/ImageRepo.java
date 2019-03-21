package com.example.alihasan.synergytwo.Database.ImageDatabase;

import android.app.Application;
import android.os.AsyncTask;

import com.example.alihasan.synergytwo.Database.AssignmentDatabase;
import com.example.alihasan.synergytwo.Database.Business;
import com.example.alihasan.synergytwo.Database.BusinessDao;
import com.example.alihasan.synergytwo.Database.BusinessRepo;

import java.util.List;

public class ImageRepo {

    ImageDao mImageDao;

    ImageRepo(Application application) {
        AssignmentDatabase db = AssignmentDatabase.getDataBase(application);
        mImageDao = db.imageDao();
    }

    int getCount(){
        return mImageDao.getCount();
    }

    public void insert (ImageParam imageParam) {
        new insertAsyncTask(mImageDao).execute(imageParam);
    }

    public void delete(String globalImageFileName){
        new deleteAsyncTask(mImageDao).execute(globalImageFileName);
    }


    List<ImageParam> getAllData(){
        return new allDataAsyncTask(mImageDao).doInBackground();
    }




    private static class insertAsyncTask extends AsyncTask<ImageParam, Void, Void> {

        private ImageDao mAsyncTaskDao;

        insertAsyncTask(ImageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ImageParam... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends android.os.AsyncTask<String, Void, Void> {

        private ImageDao mAsyncTaskDao;

        deleteAsyncTask(ImageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class allDataAsyncTask extends AsyncTask<Void, Void, List<ImageParam>> {

        private ImageDao mAsyncTaskDao;

        allDataAsyncTask(ImageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<ImageParam> doInBackground(final Void... params) {

            return mAsyncTaskDao.getAllData();
        }
    }

}
