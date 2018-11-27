package com.pb.elite.splash;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pb.elite.core.model.MakeEntity;
import com.pb.elite.core.model.ModelEntity;
import com.pb.elite.core.model.UserConstatntEntity;
import com.pb.elite.core.model.VariantEntity;
import com.pb.elite.core.model.VehicleMasterEntity;

import java.util.List;

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

    private static final String VEHICLE_MASTER = "vehicle_data";
    private static final String USER_CONSTATNT = "user_constatnt";

    private static final String IS_DEVICE_TOKEN = "devicetoken";
    public static String DEVICE_ID = "deviceID";
    public static String NOTIFICATION_COUNTER = "Notification_Counter";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //region master vehicle

    public boolean storeVehicle(VehicleMasterEntity entity) {
        editor.putString(VEHICLE_MASTER, new Gson().toJson(entity));
        return editor.commit();
    }

    public List<MakeEntity> getMake() {
        String fourWheeler = pref.getString(VEHICLE_MASTER, "");

        if (fourWheeler.length() > 0) {
            VehicleMasterEntity vehicleMaster = new Gson().fromJson(fourWheeler, VehicleMasterEntity.class);
            return vehicleMaster.getMake();
        } else {
            return null;
        }
    }

    public List<ModelEntity> getModel(MakeEntity entity) {
        return entity.getModel();
    }

    public List<VariantEntity> getVariant(ModelEntity entity) {
        if (entity.getVariant() != null) return entity.getVariant();
        else return null;
    }


    //endregion

    //region master vehicle

    public boolean storeUserConstatnt(UserConstatntEntity entity) {
        editor.putString(USER_CONSTATNT, new Gson().toJson(entity));
        return editor.commit();
    }

    public UserConstatntEntity getUserConstatnt() {
        String userComtatnt = pref.getString(USER_CONSTATNT, "");

        if (userComtatnt.length() > 0) {
            UserConstatntEntity userMaster = new Gson().fromJson(userComtatnt, UserConstatntEntity.class);
            return userMaster;
        } else {
            return null;
        }
    }




    //endregion

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

    public void setMobile(String mob) {
        editor.putString(MOBILE, mob);

        editor.commit();
    }

    public String getMobile() {
        return pref.getString(MOBILE, "");
    }

    public void setPassword(String pwd) {

        editor.putString(PASSWORD, pwd);
        editor.commit();
    }

    public String getPassword() {
        return pref.getString(PASSWORD, "");
    }


    public  void setDeviceID(String deviceID)
    {
        editor.putString(DEVICE_ID, deviceID);

        editor.commit();
    }

    public String getDeviceID() {
        return pref.getString(DEVICE_ID, "");
    }

    public int getNotificationCounter() {
        return pref.getInt(NOTIFICATION_COUNTER, 0);
    }

    public void setNotificationCounter(int counter) {
        editor.putInt(NOTIFICATION_COUNTER, counter);
        editor.commit();
    }


    public void setToken(String token) {

        editor.putString(IS_DEVICE_TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        return pref.getString(IS_DEVICE_TOKEN, "");
    }

    public void clearUserCache() {

        editor.remove(USER_CONSTATNT);

        editor.commit();
    }


}