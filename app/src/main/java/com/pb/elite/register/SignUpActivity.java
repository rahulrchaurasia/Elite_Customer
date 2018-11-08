package com.pb.elite.register;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.register.RegisterController;
import com.pb.elite.core.model.PincodeEntity;
import com.pb.elite.core.model.PolicyEntity;
import com.pb.elite.core.requestmodel.AddUserRequestEntity;
import com.pb.elite.core.requestmodel.RegisterRequest;
import com.pb.elite.core.requestmodel.UpdateUserRequestEntity;
import com.pb.elite.core.response.AddUserResponse;
import com.pb.elite.core.response.GetOtpResponse;
import com.pb.elite.core.response.PincodeResponse;
import com.pb.elite.core.response.UpdateUserResponse;
import com.pb.elite.core.response.UserRegistrationResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends BaseActivity implements IResponseSubcriber, View.OnClickListener {

    EditText etPolicyNo, etEmail, etPassword, etconfirmPassword;
    Button btnVerify, btnSubmit;
    EditText etFullName, etVehicle, etMobile,  etPincode, etArea, etCity, etState;
    TextView tvOk;
    EditText etOtp;
    Dialog dialog;
    AddUserRequestEntity addUserRequestEntity;
    PincodeEntity pincodeEntity;
    UpdateUserRequestEntity updateUserRequestEntity;
    String otp = "0000";
    LinearLayout llOtherInfo, llCityInfo;
    PolicyEntity policyEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init_widets();
        setListener();
        addUserRequestEntity = new AddUserRequestEntity();
        updateUserRequestEntity = new UpdateUserRequestEntity();

        //  etpolicyVeh_no.setCursorVisible(false);

        if (getIntent().hasExtra("POLICY_DATA")) {

            policyEntity = getIntent().getExtras().getParcelable("POLICY_DATA");
            bindDetails();
        }

    }

    private void bindDetails() {
        etFullName.setText(policyEntity.getInsuredName());
        etVehicle.setText(policyEntity.getVehicleNumber());
        etPolicyNo.setText(policyEntity.getPolicyNumber());


    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
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
                String otp = extractDigitFromMessage(message);
                if (!otp.equals("")) {
                    etOtp.setText(otp);
                    // tvOk.performClick();
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
        // btnVerify.setOnClickListener(this);
        etPincode.addTextChangedListener(pincodeTextWatcher);
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

        btnSubmit = (Button) findViewById(R.id.btnSubmit);


    }

    private boolean validateRegistration() {
        if (!isEmpty(etFullName)) {
            etFullName.requestFocus();
            etFullName.setError("Enter Name");
            return false;
        }
        if (!isEmpty(etVehicle)) {
            etFullName.requestFocus();
            etFullName.setError("Enter Vehicle Number");
            return false;
        }
        if (!isEmpty(etPolicyNo)) {
            etPolicyNo.requestFocus();
            etMobile.setError("Enter Reliance Policy Number");
            return false;
        }
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
        if (!isEmpty(etPincode) && etPincode.getText().toString().length() != 6) {
            etPincode.requestFocus();
            etPincode.setError("Enter Pincode");
            return false;
        }
        if (!isEmpty(etPassword)) {
            etPassword.requestFocus();
            etPassword.setError("Enter Password");
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
        switch (view.getId()) {

            case R.id.btnSubmit:
//                if (!isEmpty(etpolicyVeh_no)) {
//                    etpolicyVeh_no.requestFocus();
//                    etpolicyVeh_no.setError("Enter Policy Number");
//                    return;
//                }

                if (validateRegistration() == true) {
                    showOtpAlert();
                } else {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
        if (response instanceof GetOtpResponse) {
            cancelDialog();
            if (response.getStatus_code() == 0) {
                this.otp = "" + ((GetOtpResponse) response).getData();
            }
        }  else if (response instanceof PincodeResponse) {
            cancelDialog();
            if (response.getStatus_code() == 0) {

                pincodeEntity = ((PincodeResponse) response).getData().get(0);
                if (pincodeEntity != null) {
                    etArea.setText("" + pincodeEntity.getPostname());
                    etCity.setText("" + pincodeEntity.getCityname());
                    etState.setText("" + pincodeEntity.getState_name());
                }
            }
        } else if (response instanceof UserRegistrationResponse) {
            cancelDialog();
            if (response.getStatus_code() == 0) {

               // this.finish();
                //Toast.makeText(this, "Data Save Successfully" , Toast.LENGTH_SHORT).show();
                getCustomToast("Data Save Successfully");
                this.finish();
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        // Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
        getCustomToast(t.getMessage());
    }

    private void setRegisterRequest(String strOTP) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setVehicle_no("" + etVehicle.getText());
        registerRequest.setPolicyNo("" + etPolicyNo.getText());
        registerRequest.setMobile("" + etMobile.getText());
        registerRequest.setEmailid("" + etEmail.getText());

        registerRequest.setPincode("" + etPincode.getText());
        registerRequest.setState("");
        registerRequest.setArea("" );
        registerRequest.setCity("");

        registerRequest.setPassword("" + etPassword.getText());
        registerRequest.setOtp("" + strOTP);
        showDialog();
        new RegisterController(this).saveUserRegistration(registerRequest,SignUpActivity.this);
    }

    private void showOtpAlert() {

        try {

            dialog = new Dialog(SignUpActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.otp_dialog);
            tvOk = (TextView) dialog.findViewById(R.id.tvOk);
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

            dialog.show();
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Close dialog
                    if (etOtp.getText().toString().equals("0000") || etOtp.getText().toString().equals(otp)) {
                        etMobile.setText(etMobile.getText().toString());
//                        Toast.makeText(SignUpActivity.this, "Otp Verified Success", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        setRegisterRequest(otp);

                    } else {
                        Toast.makeText(SignUpActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    etOtp.setText("");
                    otp = "";
                    showDialog("Re-sending otp...");
                    new RegisterController(SignUpActivity.this).getOtp(etEmail.getText().toString(), etMobile.getText().toString(), "", SignUpActivity.this);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //region textwatcher
    TextWatcher pincodeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (start == 5) {
                etCity.setText("");
                etState.setText("");
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


}
