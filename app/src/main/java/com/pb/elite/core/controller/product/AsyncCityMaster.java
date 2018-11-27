package com.pb.elite.core.controller.product;

import android.content.Context;
import android.os.AsyncTask;

import com.pb.elite.core.model.AllCityEntity;
import com.pb.elite.splash.PrefManager;

import java.util.List;

import io.realm.Realm;

/**
 * Created by IN-RB on 05-07-2018.
 */

public class AsyncCityMaster  extends AsyncTask< Void, Void ,Void>{

    Context mContext;
    List<AllCityEntity> lstAllCity;
    PrefManager prefManager;

    public AsyncCityMaster(Context mContext, List<AllCityEntity> lstAllCity) {
        this.mContext = mContext;
        this.lstAllCity = lstAllCity;
        prefManager = new PrefManager(mContext);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                   // realm.delete(AllCityEntity.class);
                    realm.copyToRealmOrUpdate(lstAllCity);
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(realm != null)
            {
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
