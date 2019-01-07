package com.rb.elite.core.controller.product;

import android.content.Context;

import com.rb.elite.core.model.CityEntity;
import com.rb.elite.core.model.RTOEntity;

import java.util.List;

import io.realm.Realm;

/**
 * Created by IN-RB on 25-06-2018.
 */

public class SyncRTOMaster {


    Context mContext;
    List<RTOEntity> lstRTO;
    List<CityEntity> lstCity;


    public SyncRTOMaster(Context mContext, List<RTOEntity> lstRTO, List<CityEntity> lstCity) {
        this.mContext = mContext;
        this.lstRTO = lstRTO;
        this.lstCity = lstCity;

    }




    public  void getRTOData()
    {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    realm.copyToRealmOrUpdate(lstCity);
                    realm.copyToRealmOrUpdate(lstRTO);



                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

    }



}
