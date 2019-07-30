package com.rb.elite.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rb.elite.BaseActivity;
import com.rb.elite.HomeActivity;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.register.RegisterController;
import com.rb.elite.core.response.LoginResponse;
import com.rb.elite.register.ClientDeclareActivity;
import com.rb.elite.register.SignUpActivity;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.utility.Constants;
import com.rb.elite.utility.ReadDeviceID;
import com.rb.elite.utility.Utility;

public class LoginActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber {


    TextView tvRegistration, tvForgotPassword;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 1111;
    EditText etPassword, etMobile;

    PrefManager prefManager;
    String strToken;
    Button btnSignIn;
    String deviceId = "";


    String[] perms = {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        initialize();
        prefManager = new PrefManager(this);

        String mob = prefManager.getMobile();
        String pwd = prefManager.getPassword();

        etMobile.setText(mob);
        etPassword.setText(pwd);

        try {
            deviceId = prefManager.getDeviceID();
            if (deviceId == null || deviceId.matches("")) {
                deviceId = new ReadDeviceID(this).getAndroidID();
                prefManager.setDeviceID(deviceId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!checkPermission()) {
            requestPermission();
        }


    }

    private void initialize() {

        etPassword = (EditText) findViewById(R.id.etPassword);
        etMobile = (EditText) findViewById(R.id.etMobile);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        tvRegistration = (TextView) findViewById(R.id.tvRegistration);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(this);
        tvRegistration.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Constants.hideKeyBoard(view, LoginActivity.this);
        switch (view.getId()) {
            case R.id.tvRegistration:
               // startActivity(new Intent(LoginActivity.this, ClientDeclareActivity.class));
                // Changes For common app 05
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

                break;

            case R.id.tvForgotPassword:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;
            case R.id.btnSignIn:
                if (!isEmpty(etMobile)) {
                    etMobile.requestFocus();
                    Snackbar.make(etMobile, "Enter Mobile", Snackbar.LENGTH_LONG).show();
                    // etMobile.setError("Enter Mobile");
                    return;
                }
                if (!isEmpty(etPassword)) {
                    etPassword.requestFocus();
                    Snackbar.make(etMobile, "Enter Password", Snackbar.LENGTH_LONG).show();
                    //  etPassword.setError("Enter Password");
                    return;
                }

                strToken = prefManager.getToken();
                if (prefManager.getToken() != null) {
                    strToken = prefManager.getToken();
                } else {
                    strToken = "";
                }

                showDialog("Please Wait...");
                new RegisterController(LoginActivity.this).getLogin(etMobile.getText().toString(), etPassword.getText().toString(), strToken, deviceId, this);


                break;
        }
    }

    //region permission

    private boolean checkPermission() {

        int camera = ContextCompat.checkSelfPermission(getApplicationContext(), perms[0]);

        int write_external = ContextCompat.checkSelfPermission(getApplicationContext(), perms[1]);
        int read_external = ContextCompat.checkSelfPermission(getApplicationContext(), perms[2]);

        int fineLocation = ContextCompat.checkSelfPermission(getApplicationContext(), perms[3]);
        int coarseLocation = ContextCompat.checkSelfPermission(getApplicationContext(), perms[4]);


        return camera == PackageManager.PERMISSION_GRANTED


                && write_external == PackageManager.PERMISSION_GRANTED
                && read_external == PackageManager.PERMISSION_GRANTED
                && fineLocation == PackageManager.PERMISSION_GRANTED
                && coarseLocation == PackageManager.PERMISSION_GRANTED;

    }


    @Override
    public void OnSuccess(APIResponse response, String message) {


        if (response instanceof LoginResponse) {
            cancelDialog();
            if (response.getStatus_code() == 0) {

                prefManager.setMobile(etMobile.getText().toString());
                prefManager.setPassword(etPassword.getText().toString());
                //  Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();

                if (prefManager.getPushNotifyData() != null) {

                    startActivity(new Intent(LoginActivity.this, HomeActivity.class).putExtra(Utility.PUSH_LOGIN_PAGE, "555"));
                    finish();
                } else {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }


            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        //Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        getCustomToast(t.getMessage());
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, perms, REQUEST_CODE_ASK_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0) {

                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                    boolean writeExternal = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternal = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean fineLocation = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean coarseLocation = grantResults[4] == PackageManager.PERMISSION_GRANTED;


                    if (camera && writeExternal && readExternal && fineLocation && coarseLocation) {
                        // you can do all necessary steps
                        // new Dialer().getObject().getLeadData(String.valueOf(Utility.EmpCode), this, this);
                        // Toast.makeText(this, "All permission granted", Toast.LENGTH_SHORT).show();
                    } else {

                        //Permission Denied, You cannot access location data and camera
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            showMessageOKCancel("Required permissions to proceed Elite Customer..!",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // finish();
                                            requestPermission();
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Retry")
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                //.setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    //endregion


}
