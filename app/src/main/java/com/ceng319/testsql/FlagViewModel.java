package com.ceng319.testsql;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Collections;
import java.util.List;

public class FlagViewModel extends AndroidViewModel{
    private FlagRepository mRepository;
    private LiveData<List<FlagEntity>> mAllSelectedFlags;
   // private LiveData<List<FlagEntity>> mAllFlags;
    private MutableLiveData<String> flagType = new MutableLiveData<>("Africa"); // default to Africa


    public FlagViewModel(@NonNull Application application) {
        super(application);
        mRepository = new FlagRepository(application);
        // mAllFlags = mRepository.getmAllFlags();
        // mAllSelectedFlags = mRepository.getmAllsearchedFlags("Africa%");
        mAllSelectedFlags = (LiveData<List<FlagEntity>>) Transformations.switchMap(flagType, filter -> {
            mAllSelectedFlags = mRepository.getmAllsearchedFlags(filter);
              return mAllSelectedFlags;

        });
    }

    // This method is called from the Activity.
    public void changeFlagType(String str) {
        flagType.setValue(str);
    }

    public LiveData<List<FlagEntity>> getmAllSelectedFlags(String selection) {
        return mAllSelectedFlags;
    }

}
