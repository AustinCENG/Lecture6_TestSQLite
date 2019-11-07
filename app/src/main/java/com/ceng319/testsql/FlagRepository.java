package com.ceng319.testsql;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class FlagRepository {

    private FlagDAO mFlagDao;
   //  private LiveData<List<FlagEntity>> mAllFlags;
    private LiveData<List<FlagEntity>> mAllSearchedFlags;


    FlagRepository(Application application) {
        FlagRoomDatabase db = FlagRoomDatabase.getDatabase(application);
        mFlagDao = db.flagDao();
        // mAllFlags = mFlagDao.getAlphabetizedFlags();
        mAllSearchedFlags = mFlagDao.getAlphabetizedFlags();

    }

  /*  LiveData<List<FlagEntity>> getmAllFlags() {
        return mAllFlags;
    }*/
    LiveData<List<FlagEntity>> getmAllsearchedFlags(String selected) {
        mAllSearchedFlags = mFlagDao.searchFlags(selected);
        return mAllSearchedFlags;
    }

    public void insert (FlagEntity flag) {
        new insertAsyncTask(mFlagDao).execute(flag);
    }

    private static class insertAsyncTask extends AsyncTask<FlagEntity, Void, Void> {

        private FlagDAO mAsyncTaskDao;

        insertAsyncTask(FlagDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FlagEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
