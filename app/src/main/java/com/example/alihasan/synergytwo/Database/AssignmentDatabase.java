package com.example.alihasan.synergytwo.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.alihasan.synergytwo.Database.DebtorDatabase.DebtorDao;
import com.example.alihasan.synergytwo.Database.EmploymentDatabase.Employment;
import com.example.alihasan.synergytwo.Database.EmploymentDatabase.EmploymentDao;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageDao;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageParam;
import com.example.alihasan.synergytwo.api.model.Debtor;

@Database(entities = {Business.class, Employment.class, Debtor.class, ImageParam.class}, version = 1)
public abstract class AssignmentDatabase extends RoomDatabase {

    public abstract BusinessDao businessdao();
    public abstract DebtorDao debtorDao();
    public abstract ImageDao imageDao();
    public abstract EmploymentDao employmentDao();

    private static volatile AssignmentDatabase INSTANCE;

    public static AssignmentDatabase getDataBase(final Context context){

        if (INSTANCE == null) {
            synchronized (AssignmentDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AssignmentDatabase.class, "business_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
