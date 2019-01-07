package com.rb.elite.core.controller.product;

import android.content.Context;
import android.os.AsyncTask;

import com.rb.elite.core.model.CityEntity;
import com.rb.elite.core.model.RTOEntity;
import com.rb.elite.splash.PrefManager;

import java.util.List;

import io.realm.Realm;

/**
 * Created by IN-RB on 07-02-2018.
 */

public class AsyncRTOMaster  extends AsyncTask<Void, Void, Void> {

    PrefManager prefManager;
    Context mContext;
    List<RTOEntity> lstRTO;
    List<CityEntity> lstCity;

    public AsyncRTOMaster(Context mContext, List<RTOEntity> lstRTO, List<CityEntity> lstCity) {
        this.mContext = mContext;
        this.lstRTO = lstRTO;

        prefManager = new PrefManager(mContext);
        prefManager.setIsRtoMasterUpdate(false);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(CityEntity.class);
                    realm.delete(RTOEntity.class);
                    realm.copyToRealmOrUpdate(lstRTO);
                    realm.copyToRealmOrUpdate(lstCity);

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}
