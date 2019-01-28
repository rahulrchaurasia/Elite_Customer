package com.rb.elite;

import android.app.Application;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name("elite.realm")                // user defined name
//                .schemaVersion(0)
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        Realm.setDefaultConfiguration(config);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/oxygenlight.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}