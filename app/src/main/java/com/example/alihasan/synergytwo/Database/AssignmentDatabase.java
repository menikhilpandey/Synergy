package com.example.alihasan.synergytwo.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Business.class}, version = 1)
public abstract class AssignmentDatabase extends RoomDatabase {

    public abstract BusinessDao businessdao();

    private static volatile AssignmentDatabase INSTANCE;

    static AssignmentDatabase getDataBase(final Context context){

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
