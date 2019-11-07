package com.ceng319.testsql;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "flag_table")
public class FlagEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int uid;
    @ColumnInfo(name = "countryname")
    private String country;
    @ColumnInfo(name = "flagimage")
    private String flag;

    public FlagEntity(int uid, String countryname, String flagimage) {
        this.uid = uid;
        this.country = countryname;
        this.flag = flagimage;
    }
    public FlagEntity() {

    }

    // Getters
    public int getUid() {
        return uid;
    }

    public String getCountry() {
        return country;
    }

    public String getFlag() {
        return flag;
    }

    // Setters
    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setCountry(String mCountryName) {
        this.country = mCountryName;
    }

    public void setFlag(String mFlagImage) {
        this.flag = mFlagImage;
    }



    // Constructor of the entity.


}
