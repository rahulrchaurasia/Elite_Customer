package com.pb.elite.splash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.pb.elite.BaseActivity;
import com.pb.elite.HomeActivity;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.controller.register.RegisterController;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.response.CityResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.login.LoginActivity;
import com.pb.elite.utility.Constants;
import com.pb.elite.welcome.WelcomeActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends BaseActivity implements IResponseSubcriber {

    PrefManager prefManager;
    TextView txtGroup;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    DataBaseController dataBaseController;
    List<String> allCityEntityList;

    int CityVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        txtGroup = (TextView) findViewById(R.id.txtGroup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefManager = new PrefManager(this);
        dataBaseController = new DataBaseController(SplashScreenActivity.this);
        allCityEntityList = new ArrayList<String>();

        verify();
        fetchCar();

    }

    private void fetchCar() {
        new RegisterController(this).getCarVehicleMaster();
    }

    private void fetchCityMasters() {

        allCityEntityList = dataBaseController.getAllCityList();

        if (allCityEntityList.size() == 0) {     // step 4 (if db version is below than city list is already filled above)
            new ProductController(SplashScreenActivity.this).getCityMaster(SplashScreenActivity.this);
        }


        UserEntity loginEntity = dataBaseController.getUserData();


        if (loginEntity != null) {
            startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));

        } else {
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));

        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
//        if (response instanceof DBVersionRespone) {  //step2
//            if (response.getStatus_code() == 0) {
//
//                CityVersion = ((DBVersionRespone) response).getData().get(0).getCity_Version();
//
//                if (prefManager.getCityVersion() < CityVersion) {
//                    prefManager.setCityVersionUpdate(CityVersion);
//                    new ProductController(SplashScreenActivity.this).getCityMaster(SplashScreenActivity.this);
//                }
//                fetchCityMasters();
//
//            }
//        }

        if (response instanceof CityResponse) {  //step3
            if (response.getStatus_code() == 0) {


            }

        }
    }

    @Override
    public void OnFailure(Throwable t) {
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

    }

    public void verify() {
        if (!Constants.checkInternetStatus(SplashScreenActivity.this)) {

            Snackbar snackbar = Snackbar.make(txtGroup, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            verify();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.CYAN);

            snackbar.show();
        } else {
            if (prefManager.isFirstTimeLaunch()) {

                startActivity(new Intent(this, WelcomeActivity.class));
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //  new RegisterController(SplashScreenActivity.this).getDbVersion(SplashScreenActivity.this);  //step1

                        fetchCityMasters();


                    }
                }, SPLASH_DISPLAY_LENGTH);

            }
        }


    }
}
