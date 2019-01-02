package com.pb.elite.register;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.register.RegisterController;
import com.pb.elite.core.model.MakeEntity;
import com.pb.elite.core.model.ModelEntity;
import com.pb.elite.core.model.PincodeEntity;
import com.pb.elite.core.model.PolicyEntity;
import com.pb.elite.core.model.VerifyOTPEntity;
import com.pb.elite.core.requestmodel.AddUserRequestEntity;
import com.pb.elite.core.requestmodel.RegisterRequest;
import com.pb.elite.core.requestmodel.UpdateUserRequestEntity;
import com.pb.elite.core.response.PincodeResponse;
import com.pb.elite.core.response.UserRegistrationResponse;
import com.pb.elite.core.response.VerifyUserRegisterResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends BaseActivity implements IResponseSubcriber, View.OnClickListener {

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


    MakeAdapter makeAdapter;
    ModelAdapter modelAdapter;
    MakeEntity makeEntity;
    ModelEntity modelEntity;

    boolean IsMakeValid = false;
    boolean IsModelValid = false;

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
        acMake.setThreshold(2);
        acMake.setSelection(0);

        acModel.setThreshold(1);
        acModel.setSelection(0);

        addUserRequestEntity = new AddUserRequestEntity();
        updateUserRequestEntity = new UpdateUserRequestEntity();

        makeAdapter = new MakeAdapter(this, R.layout.activity_sign_up, R.id.lbl_name, prefManager.getMake());
        acMake.setAdapter(makeAdapter);

        //  etpolicyVeh_no.setCursorVisible(false);

        if (getIntent().hasExtra("POLICY_DATA")) {

            policyEntity = getIntent().getExtras().getParcelable("POLICY_DATA");
            bindDetails();
        }

        acMake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                acMake.setError(null);
                acMake.setSelection(0);
                IsMakeValid = true;
                makeEntity = makeAdapter.getItem(position);
                if (makeEntity.getModel() != null) {
                    modelAdapter = new ModelAdapter(SignUpActivity.this,
                            R.layout.activity_sign_up, R.id.lbl_name, makeEntity.getModel());
                    acModel.setAdapter(modelAdapter);
                    acModel.setEnabled(true);
                    IsModelValid = true;
                    acModel.setVisibility(View.VISIBLE);


                } else {
                    acModel.setText("");
                    acModel.setEnabled(false);
                    acModel.setVisibility(View.INVISIBLE);
                    IsModelValid = false;


                }
            }
        });


        acMake.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String str = acMake.getText().toString();

                ListAdapter listAdapter = acMake.getAdapter();
                for (int i = 0; i < listAdapter.getCount(); i++) {
                    String temp = listAdapter.getItem(i).toString().toUpperCase();
                    if (str.compareTo(temp) == 0) {
                        acMake.setError(null);
                        acModel.setText("");
                        acModel.setEnabled(true);
                        IsMakeValid = true;


                        return;
                    }
                }

                acMake.setError("Invalid Make");
                acMake.setFocusable(true);

                acModel.setText("");
                acModel.setEnabled(false);
                IsMakeValid = false;


            }
        });

        //region  Model Listener

        acModel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IsModelValid = true;
                acModel.setError(null);
                modelEntity = modelAdapter.getItem(position);
                acMake.setSelection(0);

            }
        });
        acModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    String str = acModel.getText().toString();

                    ListAdapter listAdapter = acModel.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); i++) {
                        String temp = listAdapter.getItem(i).toString().toUpperCase();
                        if (str.compareTo(temp) == 0) {
                            acModel.setError(null);
                            IsModelValid = true;
                            return;
                        }
                    }

                    acModel.setError("Invalid Model");
                    acModel.setFocusable(true);
                    IsModelValid = false;


                }

            }
        });

        //endregion

    }


    private void bindDetails() {
        etFullName.setText(policyEntity.getInsuredName());
        etVehicle.setText(policyEntity.getVehicleNumber());
        etPolicyNo.setText(policyEntity.getPolicyNumber());
        acMake.setText(policyEntity.getMake());
        acModel.setText(policyEntity.getModel());
        IsMakeValid = true;
        IsModelValid = true;


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

        if (!isEmpty(acMake)) {
            acMake.requestFocus();
            acMake.setError("Enter Make");
            return false;
        }
        if (IsMakeValid == false) {
            acMake.requestFocus();
            acMake.setError("Invalid Make");
            return false;

        }

        if (!isEmpty(acModel)) {
            acModel.requestFocus();
            acModel.setError("Enter Model");

            return false;
        }

        if (IsModelValid == false) {
            acModel.requestFocus();
            acModel.setError("Invalid Model");

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

        registerRequest.setPincode("" + etPincode.getText());
        registerRequest.setState("" + etState.getText());
        registerRequest.setArea("" + etArea.getText());
        registerRequest.setCity("" + etCity.getText());

        registerRequest.setVehicle_no("" + etVehicle.getText());
        registerRequest.setPolicy_no("" + etPolicyNo.getText());
        registerRequest.setPassword("" + etPassword.getText());
        registerRequest.setMake("" + acMake.getText());
        registerRequest.setModel("" + acModel.getText());

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
                    if (etOtp.getText().toString().equals("0000") || etOtp.getText().toString().equals(OTP)) {

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


    //region textwatcher
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


}
