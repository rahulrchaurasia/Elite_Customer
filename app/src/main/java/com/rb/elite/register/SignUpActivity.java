package com.rb.elite.register;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rb.elite.BaseActivity;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.register.RegisterController;
import com.rb.elite.core.model.MakeEntity;
import com.rb.elite.core.model.ModelEntity;
import com.rb.elite.core.model.PincodeEntity;
import com.rb.elite.core.model.PolicyEntity;
import com.rb.elite.core.model.VerifyOTPEntity;
import com.rb.elite.core.requestmodel.AddUserRequestEntity;
import com.rb.elite.core.requestmodel.RegisterRequest;
import com.rb.elite.core.requestmodel.UpdateUserRequestEntity;
import com.rb.elite.core.response.PincodeResponse;
import com.rb.elite.core.response.UserRegistrationResponse;
import com.rb.elite.core.response.VerifyUserRegisterResponse;
import com.rb.elite.database.DataBaseController;
import com.rb.elite.location.ILocationStateListener;
import com.rb.elite.location.LocationTracker;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.utility.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends BaseActivity implements IResponseSubcriber, View.OnClickListener, ILocationStateListener {

    EditText etPolicyNo, etEmail, etPassword, etconfirmPassword;
    Button btnVerify, btnSubmit;
    // TextView txtModel;
    EditText etFullName, etVehicle, etMobile, etPincode, etArea, etCity, etState;
    EditText etOtp;
    AutoCompleteTextView acMake, acModel;
    Dialog dialog;
    AddUserRequestEntity addUserRequestEntity;
    PincodeEntity pincodeEntity;
    UpdateUserRequestEntity updateUserRequestEntity;
    String OTP = "0000";
    LinearLayout llOtherInfo, llCityInfo;
    PolicyEntity policyEntity;
    DataBaseController dataBaseController;
    PrefManager prefManager;




    //region Location

    LocationTracker mLocationTracker;
    Location mLocation;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefManager = new PrefManager(this);
        dataBaseController = new DataBaseController(SignUpActivity.this);

        init_widets();
        setListener();

        //initialise location
        mLocationTracker = new LocationTracker(this);
        mLocationTracker.setLocationStateListener(this);

        mLocationTracker.init();



        if (getIntent().hasExtra("POLICY_DATA")) {

            policyEntity = getIntent().getExtras().getParcelable("POLICY_DATA");
            bindDetails();
        }





    }


    private void bindDetails() {
        etFullName.setText(policyEntity.getInsuredName());


    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        mLocationTracker.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    //region Broadcast Receiver
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                String message = intent.getStringExtra("message");
                String strmessage = extractDigitFromMessage(message);
                if (!strmessage.equals("")) {
                    etOtp.setText(strmessage);

                }
            }
        }
    };

    //endregion

    private String extractDigitFromMessage(String message) {
        //---This will match any 6 digit number in the message, can use "|" to lookup more possible combinations
        Pattern p = Pattern.compile("(|^)\\d{6}");
        try {
            if (message != null) {
                Matcher m = p.matcher(message);
                if (m.find()) {
                    return m.group(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void setListener() {
        btnSubmit.setOnClickListener(this);

       //  etPincode.addTextChangedListener(pincodeTextWatcher);
    }

    private void init_widets() {
        llOtherInfo = (LinearLayout) findViewById(R.id.llOtherInfo);
        llCityInfo = (LinearLayout) findViewById(R.id.llCityInfo);


        etFullName = (EditText) findViewById(R.id.etFullName);
        etVehicle = (EditText) findViewById(R.id.etVehicle);
        etPolicyNo = (EditText) findViewById(R.id.etPolicyNo);

        etMobile = (EditText) findViewById(R.id.etMobile);
        etPincode = (EditText) findViewById(R.id.etPincode);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etconfirmPassword = (EditText) findViewById(R.id.etconfirmPassword);

        //  btnVerify = (Button) findViewById(R.id.btnVerify);

        etArea = (EditText) findViewById(R.id.etArea);
        etCity = (EditText) findViewById(R.id.etCity);
        etState = (EditText) findViewById(R.id.etState);
        acMake = (AutoCompleteTextView) findViewById(R.id.acMake);
        acModel = (AutoCompleteTextView) findViewById(R.id.acModel);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        etVehicle.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(20)});


    }

    private boolean validateRegistration() {
        if (!isEmpty(etFullName)) {
            etFullName.requestFocus();
            etFullName.setError("Enter Name");
            return false;
        }

//        if (!isEmpty(etVehicle)) {
//            etFullName.requestFocus();
//            etFullName.setError("Enter Vehicle Number");
//            return false;
//        }
//        if (!isEmpty(etPolicyNo)) {
//            etPolicyNo.requestFocus();
//            etMobile.setError("Enter Reliance Policy Number");
//            return false;
//        }
//
//        if (!isEmpty(acMake)) {
//            acMake.requestFocus();
//            acMake.setError("Enter Make");
//            return false;
//        }
//        if (IsMakeValid == false) {
//            acMake.requestFocus();
//            acMake.setError("Invalid Make");
//            return false;
//
//        }

//        if (!isEmpty(acModel)) {
//            acModel.requestFocus();
//            acModel.setError("Enter Model");
//
//            return false;
//        }
//
//        if (IsModelValid == false) {
//            acModel.requestFocus();
//            acModel.setError("Invalid Model");
//
//            return false;
//        }

        if (!isEmpty(etMobile)) {
            etMobile.requestFocus();
            etMobile.setError("Enter Mobile");
            return false;
        }
        if (!isValidePhoneNumber(etMobile)) {
            etMobile.requestFocus();
            etMobile.setError("Enter Valid Mobile");
            return false;
        }
        if (!isEmpty(etEmail)) {
            etEmail.requestFocus();
            etEmail.setError("Enter Email");
            return false;
        }
        if (!isValideEmailID(etEmail)) {
            etEmail.requestFocus();
            etEmail.setError("Enter Valid Email");
            return false;
        }
//        if (!isEmpty(etPincode) && etPincode.getText().toString().length() != 6) {
//            etPincode.requestFocus();
//            etPincode.setError("Enter Pincode");
//            return false;
//        }
        if (!isEmpty(etPassword)) {
            etPassword.requestFocus();
            etPassword.setError("Enter Password");
            return false;
        }
        if (etPassword.getText().toString().trim().length() < 3) {
            etPassword.requestFocus();
            etPassword.setError("Minimum length should be 3");
            return false;
        }
        if (!isEmpty(etconfirmPassword)) {
            etconfirmPassword.requestFocus();
            etconfirmPassword.setError("Confirm Password");
            return false;
        }
        if (!etPassword.getText().toString().equals(etconfirmPassword.getText().toString())) {
            etconfirmPassword.requestFocus();
            etconfirmPassword.setError("Password Mismatch");
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view, this);
        switch (view.getId()) {

            case R.id.btnSubmit:

                if (validateRegistration() == true) {
                    showDialog();
                    new RegisterController(SignUpActivity.this).verifyOTPTegistration(etEmail.getText().toString(), etMobile.getText().toString(), "", SignUpActivity.this);

                }

                break;
        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
        if (response instanceof PincodeResponse) {
            if (response.getStatus_code() == 0) {

                pincodeEntity = ((PincodeResponse) response).getData().get(0);
                if (pincodeEntity != null) {
                    etArea.setText("" + pincodeEntity.getPostname());
                    etCity.setText("" + pincodeEntity.getCityname());
                    etState.setText("" + pincodeEntity.getState_name());
                }
            }
        } else if (response instanceof VerifyUserRegisterResponse) {

            if (response.getStatus_code() == 0) {
                VerifyOTPEntity verifyOTPEntity = ((VerifyUserRegisterResponse) response).getData();
                if (verifyOTPEntity.getSavedStatus() == 1) {
                    OTP = verifyOTPEntity.getOTP();
                    showOtpAlert();
                } else if (verifyOTPEntity.getSavedStatus() == 2) {
                    getCustomToast(response.getMessage());
                }
            }
        } else if (response instanceof UserRegistrationResponse) {

            if (response.getStatus_code() == 0) {


                getCustomToast(response.getMessage());
                this.finish();
            }
        }

        //
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        // Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
        getCustomToast(t.getMessage());
    }

    private void setRegisterRequest(String strOTP) {
        RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.setOtp("" + strOTP);
        registerRequest.setName("" + etFullName.getText());
        registerRequest.setEmailid("" + etEmail.getText());
        registerRequest.setMobile("" + etMobile.getText());
        registerRequest.setPassword("" + etPassword.getText());

        if(mLocation != null){
            registerRequest.setLat("" +  mLocation.getLatitude());
            registerRequest.setLon("" +  mLocation.getLongitude());
        }else{
            registerRequest.setLat("0" );
            registerRequest.setLon("0");
        }


        registerRequest.setPincode("" );
        registerRequest.setState("" );
        registerRequest.setArea("" );
        registerRequest.setCity("");

        registerRequest.setVehicle_no("" );
        registerRequest.setPolicy_no("" );

        registerRequest.setMake("" );
        registerRequest.setModel("");

        if (policyEntity != null) {
            registerRequest.setProductCode("" + policyEntity.getProductCode());
            registerRequest.setRiskEndDate("" + policyEntity.getRiskEndDate());
            registerRequest.setRiskStartDate("" + policyEntity.getRiskStartDate());
            registerRequest.setInsuredName("" + policyEntity.getInsuredName());

            registerRequest.setPolicyStatus("" + policyEntity.getPolicyStatus());
            registerRequest.setResponseStatus("" + policyEntity.getResponseStatus());
        } else {
            registerRequest.setProductCode("");
            registerRequest.setRiskEndDate("");
            registerRequest.setRiskStartDate("");
            registerRequest.setInsuredName("");

            registerRequest.setPolicyStatus("");
            registerRequest.setResponseStatus("");
        }


        showDialog();
        new RegisterController(this).saveUserRegistration(registerRequest, SignUpActivity.this);
    }

    private void showOtpAlert() {

        try {

            dialog = new Dialog(SignUpActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.otp_dialog);
            TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
            final TextView txtOTPMessage = (TextView) dialog.findViewById(R.id.txtOTPMessage);
            final TextView tvTime = (TextView) dialog.findViewById(R.id.tvTime);
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
            tvTitle.setText("Enter OTP sent on : " + etMobile.getText().toString());
            TextView resend = (TextView) dialog.findViewById(R.id.tvResend);
            etOtp = (EditText) dialog.findViewById(R.id.etOtp);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = lp.MATCH_PARENT;

            lp.height = lp.WRAP_CONTENT; // Height
            dialogWindow.setAttributes(lp);

            txtOTPMessage.setText("");
            txtOTPMessage.setVisibility(View.GONE);

            dialog.show();
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Close dialog
                    if (etOtp.getText().toString().equals(OTP)) {

                        etMobile.setText(etMobile.getText().toString());
                        dialog.dismiss();
                        setRegisterRequest(OTP);

                    } else {
                        Toast.makeText(SignUpActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        txtOTPMessage.setText("Invalid OTP");
                        txtOTPMessage.setVisibility(View.VISIBLE);
                    }


                }
            });

            resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    etOtp.setText("");
                    OTP = "";
                    showDialog("Re-sending otp...");
                    new RegisterController(SignUpActivity.this).verifyOTPTegistration(etEmail.getText().toString(), etMobile.getText().toString(), "", SignUpActivity.this);
                }
            });

            new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvTime.setText((millisUntilFinished / 1000) + " seconds remaining");
                }

                @Override
                public void onFinish() {
                    tvTime.setText("");
                    // dialog.dismiss();
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //region text watcher
    TextWatcher pincodeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (start < 6) {
                etCity.setText("");
                etState.setText("");
                etArea.setText("");
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 6) {
                showDialog("Fetching City...");
                new RegisterController(SignUpActivity.this).getCityState(etPincode.getText().toString(), SignUpActivity.this);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    //endregion

    //region Location

    @Override
    public void onLocationChanged(Location location) {

        mLocation = mLocationTracker.mLocation;
    }

    @Override
    public void onConnected() {
        mLocation = mLocationTracker.mLocation;

      //  Log.d("--location--", "onConnected: " + mLocation.getLatitude() +" " + mLocation.getLongitude());
    }

    @Override
    public void onConnectionFailed() {
        mLocation =null;
    }
    //endregion




}
