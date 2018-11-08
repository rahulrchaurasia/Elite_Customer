package com.pb.elite.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pb.elite.BaseActivity;
import com.pb.elite.HomeActivity;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.register.RegisterController;
import com.pb.elite.core.response.LoginResponse;
import com.pb.elite.register.ClientDeclareActivity;
import com.pb.elite.register.SignUpActivity;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;

public class loginActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber {


    TextView tvRegistration, tvForgotPassword;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 1111;
    EditText etPassword, etMobile;

    PrefManager prefManager;
    Button btnSignIn;
//    String[] perms = {
//            "android.permission.CAMERA",
//            "android.permission.ACCESS_FINE_LOCATION",
//            "android.permission.SEND_SMS",
//            "android.permission.READ_SMS",
//            "android.permission.RECEIVE_SMS",
//            "android.permission.WRITE_EXTERNAL_STORAGE",
//            "android.permission.READ_EXTERNAL_STORAGE",
//            "android.permission.CALL_PHONE",
//            "android.permission.RECORD_AUDIO"
//    };


    String[] perms = {
            "android.permission.CAMERA",
            "android.permission.SEND_SMS",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",

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
        Constants.hideKeyBoard(view,loginActivity.this);
        switch (view.getId()) {
            case R.id.tvRegistration:
                startActivity(new Intent(loginActivity.this, ClientDeclareActivity.class));

                break;

            case R.id.tvForgotPassword:
                startActivity(new Intent(loginActivity.this, ForgotPasswordActivity.class));
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

                showDialog("Please Wait...");
                new RegisterController(loginActivity.this).getLogin(etMobile.getText().toString(), etPassword.getText().toString(), this);


                break;
        }
    }

    //region permission

    private boolean checkPermission() {

        int camera = ContextCompat.checkSelfPermission(getApplicationContext(), perms[0]);
        int sendSms = ContextCompat.checkSelfPermission(getApplicationContext(), perms[1]);
        int readSms = ContextCompat.checkSelfPermission(getApplicationContext(), perms[2]);
        int receiveSms = ContextCompat.checkSelfPermission(getApplicationContext(), perms[3]);
        int WRITE_EXTERNAL = ContextCompat.checkSelfPermission(getApplicationContext(), perms[4]);
        int READ_EXTERNAL = ContextCompat.checkSelfPermission(getApplicationContext(), perms[5]);

//        int callPhone = ContextCompat.checkSelfPermission(getApplicationContext(), perms[6]);
//        int recordAudio = ContextCompat.checkSelfPermission(getApplicationContext(), perms[7]);
//        int fineLocation = ContextCompat.checkSelfPermission(getApplicationContext(), perms[8]);

        return camera == PackageManager.PERMISSION_GRANTED

                && sendSms == PackageManager.PERMISSION_GRANTED
                && readSms == PackageManager.PERMISSION_GRANTED
                && receiveSms == PackageManager.PERMISSION_GRANTED
                && WRITE_EXTERNAL == PackageManager.PERMISSION_GRANTED
                && READ_EXTERNAL == PackageManager.PERMISSION_GRANTED;

    }


    @Override
    public void OnSuccess(APIResponse response, String message) {


        if (response instanceof LoginResponse) {
            cancelDialog();
            if (response.getStatus_code() == 0) {

                prefManager.setMobile(etMobile.getText().toString());
                prefManager.setPassword(etPassword.getText().toString());
              //  Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(loginActivity.this, HomeActivity.class));
                finish();
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

                    boolean sendSms = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readSms = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean receiveSms = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternal = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternal = grantResults[5] == PackageManager.PERMISSION_GRANTED;

//                    boolean fineLocation = grantResults[6] == PackageManager.PERMISSION_GRANTED;
//                    boolean callPhone = grantResults[7] == PackageManager.PERMISSION_GRANTED;
//                    boolean recordAudio = grantResults[8] == PackageManager.PERMISSION_GRANTED;



                    if (camera &&  sendSms && readSms && receiveSms && writeExternal && readExternal ) {
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
        new AlertDialog.Builder(loginActivity.this)
                .setTitle("Retry")
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                //.setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    //endregion


}
