package com.pb.elite.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by IN-RB on 02-02-2018.
 */

public class Constants {

    public static int SPLASH_DISPLAY_LENGTH = 2000;
    public static int PAYMENT_SUCCESS = 1;
    public static int PAYMENT_FAILURE = 0;
    public static String PAYMENT_AMOUNT = "10.05";

    public static  String SERVICE_TYPE = "ELITE_SERVICE_TYPE";

    public static String RTO_PRODUCT_DATA = "ELITE_RTO_PRODUCT_DATA";
    public static String NON_RTO_PRODUCT_DATA = "ELITE_NON_RTO_PRODUCT_DATA";

    public static String SUB_PRODUCT_DATA = "ELITE_SUB_RTO_PRODUCT_DATA";

    public static  int SEARCH_CITY_CODE = 5;
    public static  int ORDER_CODE = 2;

    public static  String SEARCH_CITY_NAME  = "ELITE_SEARCH_CITY_NAME";
    public static  String SEARCH_CITY_ID  = "ELITE_SEARCH_CITY_ID";







    public static void hideKeyBoard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean checkInternetStatus(Context context) {
      /*  ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                conMgr.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                conMgr.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                conMgr.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        }*/
        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // notify user you are online
            return true;
        }
        return false;
    }
}
