package com.ceng319.testsql;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = {FlagEntity.class}, version = 1, exportSchema = false)
public abstract class FlagRoomDatabase extends RoomDatabase {
    public abstract FlagDAO flagDao();

    private static volatile FlagRoomDatabase INSTANCE;
    //  singleton  pattern is used here to make sure there is only one instance of the database.
    static FlagRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FlagRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FlagRoomDatabase.class, "flag_database")
                            .addCallback(sFlagRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static FlagRoomDatabase.Callback sFlagRoomDatabaseCallback =
            new FlagRoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     * If you want to start with more flags, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final FlagDAO mDao;

        PopulateDbAsync(FlagRoomDatabase db) {
            mDao = db.flagDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            List<FlagEntity> itemList = SampleData.flagEntityList;
            mDao.insertAll(itemList);
            return null;
        }
    }

}
