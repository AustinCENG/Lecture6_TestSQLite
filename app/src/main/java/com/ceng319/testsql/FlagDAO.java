package com.ceng319.testsql;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FlagDAO {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FlagEntity flag);

    @Query("DELETE FROM flag_table")
    void deleteAll();

    @Query("SELECT * from flag_table ORDER BY countryname ASC")
    LiveData<List<FlagEntity>> getAlphabetizedFlags();

    @Query("SELECT * from flag_table WHERE flagimage LIKE :continent ORDER BY countryname ASC")
    LiveData<List<FlagEntity>> searchFlags(String continent);


    @Query("SELECT * FROM flag_table")
    List<FlagEntity> getAll();

    @Query("SELECT * FROM flag_table where countryname LIKE  :countryname")
    FlagEntity findByName(String countryname);

    @Query("SELECT COUNT(*) from flag_table")
    int countFlags();

    @Insert
    void insertAll(FlagEntity... flags);

    @Insert
    void insertAll(List<FlagEntity> flags);

    @Delete
    void delete(FlagEntity flag);

}
