package com.example.alihasan.synergytwo.Database.ImageDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

public class ImageViewModel extends AndroidViewModel {

    private ImageRepo mImageRepo;


    public ImageViewModel(Application application) {
        super(application);

        mImageRepo = new ImageRepo(application);
    }

    public int getCount() {
        return mImageRepo.getCount();
    }

    public void insert(ImageParam imageParam) {
        mImageRepo.insert(imageParam);
    }

    public void delete(ImageParam imageParam) {
        mImageRepo.delete(imageParam);
    }

    public List<ImageParam> getAllData() {
        return mImageRepo.getAllData();
    }
}
