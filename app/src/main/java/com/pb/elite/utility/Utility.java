package com.pb.elite.utility;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by IN-RB on 04-06-2018.
 */

public class Utility {


    public static String PUSH_BROADCAST_ACTION = "Finmart_Push_BroadCast_Action";
    public static String PUSH_NOTIFY = "notifyFlag";
    public static String PUSH_LOGIN_PAGE = "pushloginPage";


    public static MultipartBody.Part getMultipartImage(File file) {
        RequestBody imgBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imgFile = MultipartBody.Part.createFormData("docfile", file.getName(), imgBody);
        return imgFile;
    }


    public static HashMap<String, Integer> getBody(Context context, int userid, int doctype ,int orderID) {
        HashMap<String, Integer> body = new HashMap<String, Integer>();


        body.put("userid", (userid));
        body.put("doctype", (doctype));
        body.put("order_id", (orderID));

        return body;
    }

    public static File createDirIfNotExists() {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), "/Elite-Cust");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");
                ret = false;
            }
        }
        return file;
    }

}
