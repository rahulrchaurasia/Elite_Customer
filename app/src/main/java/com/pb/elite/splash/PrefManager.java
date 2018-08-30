package com.pb.elite.splash;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "ELITE_CUSTOMER";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_PRODUCT_MASTER_UPDATE = "isProductMasterUpdate";
    private static final String IS_RTO_MASTER_UPDATE = "isProductMasterUpdate";
    private static final String IS_DB_VERSION_UPDATED = "isDBVersiomrUpdate";
    private static final String IS_CITY_VERSION_UPDATED = "isCityBVersiomrUpdate";

    private static final String MOBILE = "ELITE_CUSTOMER_MOBILE";
    private static final String PASSWORD = "ELITE_CUSTOMER_PASSWORD";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public void setIsProductMasterUpdate(boolean isFirstTime) {
        editor.putBoolean(IS_PRODUCT_MASTER_UPDATE, isFirstTime);
        editor.commit();
    }

    public boolean IsProductMasterUpdate() {
        return pref.getBoolean(IS_PRODUCT_MASTER_UPDATE, true);
    }

    public void setIsRtoMasterUpdate(boolean isFirstTime) {
        editor.putBoolean(IS_RTO_MASTER_UPDATE, isFirstTime);
        editor.commit();
    }

    public boolean IsRtoMasterUpdate() {
        return pref.getBoolean(IS_RTO_MASTER_UPDATE, true);
    }

    ///


    public void setIsDBVersionUpdate(int dbVersion) {
        editor.putInt(IS_DB_VERSION_UPDATED, dbVersion);
        editor.commit();
    }

    public Integer getDBVersion() {
        return pref.getInt(IS_DB_VERSION_UPDATED, 1);
    }
    ///

    public void setCityVersionUpdate(int cityVersion) {
        editor.putInt(IS_CITY_VERSION_UPDATED, cityVersion);
        editor.commit();
    }

    public Integer getCityVersion() {
        return pref.getInt(IS_CITY_VERSION_UPDATED, 1);
    }

    public  void setMobile(String mob)
    {
        editor.putString(MOBILE, mob);

        editor.commit();
    }

    public String getMobile() {
        return pref.getString(MOBILE, "");
    }

    public  void setPassword(String pwd)
    {

        editor.putString(PASSWORD, pwd);
        editor.commit();
    }

    public String getPassword() {
        return pref.getString(PASSWORD, "");
    }





}