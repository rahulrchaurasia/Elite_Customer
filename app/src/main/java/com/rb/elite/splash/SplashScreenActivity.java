package com.rb.elite.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.snackbar.Snackbar;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.rb.elite.BaseActivity;
import com.rb.elite.HomeActivity;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.register.RegisterController;
import com.rb.elite.core.model.UserEntity;
import com.rb.elite.database.DataBaseController;
import com.rb.elite.login.LoginActivity;
import com.rb.elite.utility.Constants;
import com.rb.elite.welcome.WelcomeActivity;

public class SplashScreenActivity extends BaseActivity implements IResponseSubcriber {

    PrefManager prefManager;
    TextView txtGroup;
    private final int SPLASH_DISPLAY_LENGTH = 2500;
    DataBaseController dataBaseController;

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
        verify();
    }

    private void fetchCar() {
        new RegisterController(this).getCarVehicleMaster(null);
    }
    private void fetchCity() {
        new RegisterController(SplashScreenActivity.this).getCityMainMaster(null);
    }

//    private void fetchUserConstatnt() {
//        new RegisterController(this).getUserConstatnt(null);
//    }


    @Override
    protected void onResume() {
        super.onResume();
        fetchMasters();
    }

    private void fetchMasters() {

        if(prefManager.getCityData().size() == 0)
        {
            fetchCity();
        }

        if (prefManager.getMake() == null) {
            fetchCar();
        }




    }

    @Override
    public void OnSuccess(APIResponse response, String message) {


    }

    @Override
    public void OnFailure(Throwable t) {
        Toast.makeText(this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();

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
//            // Changing message text color
//            snackbar.setActionTextColor(Color.RED);
//
//            // Changing action button text color
//            View sbView = snackbar.getView();
//            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
//            textView.setTextColor(Color.CYAN);
            snackbar.show();
        } else {
            if (prefManager.isFirstTimeLaunch()) {
                startActivity(new Intent(this, WelcomeActivity.class));
            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UserEntity loginEntity = prefManager.getUserData();
                        if (loginEntity != null) {
                            startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));

                        } else {
                            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                        }

                    }
                }, SPLASH_DISPLAY_LENGTH);

            }
        }
    }
}
