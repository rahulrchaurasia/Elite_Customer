package com.pb.elite.core.controller.product;

import android.content.Context;
import android.os.AsyncTask;

import com.pb.elite.core.model.CarMasterEntity;
import com.pb.elite.splash.PrefManager;

import java.util.List;

import io.realm.Realm;



/**
 * Created by Nilesh Birhade on 29-11-2017.
 */

public class AsyncCarMaster extends AsyncTask<Void, Void, Void> {
    PrefManager prefManager;
    Context mContext;
    List<CarMasterEntity> listCarMaster;

    public AsyncCarMaster(Context context, List<CarMasterEntity> list) {
        listCarMaster = list;
        mContext = context;
        prefManager = new PrefManager(mContext);
    }


    @Override
    protected Void doInBackground(Void... voids) {

        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(CarMasterEntity.class);
                    //realm.copyToRealm(listCarMaster);
                    realm.copyToRealmOrUpdate(listCarMaster);

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
