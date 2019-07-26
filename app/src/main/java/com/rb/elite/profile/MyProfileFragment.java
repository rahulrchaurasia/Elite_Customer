package com.rb.elite.profile;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.rb.elite.BaseFragment;
import com.rb.elite.DemActivity;
import com.rb.elite.HomeActivity;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.register.RegisterController;
import com.rb.elite.core.model.FastLaneDataEntity;
import com.rb.elite.core.model.MakeEntity;
import com.rb.elite.core.model.ModelEntity;
import com.rb.elite.core.model.PincodeEntity;
import com.rb.elite.core.model.PolicyEntity;
import com.rb.elite.core.model.ProfileEntity;
import com.rb.elite.core.model.UserEntity;
import com.rb.elite.core.requestmodel.AddUserRequestEntity;
import com.rb.elite.core.requestmodel.RegisterRequest;
import com.rb.elite.core.requestmodel.UpdateUserRequestEntity;
import com.rb.elite.core.response.FastLaneDataResponse;
import com.rb.elite.core.response.PincodeResponse;
import com.rb.elite.core.response.ProfileResponse;
import com.rb.elite.core.response.UserRegistrationResponse;
import com.rb.elite.database.DataBaseController;
import com.rb.elite.register.MakeAdapter;
import com.rb.elite.register.ModelAdapter;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.splash.SplashScreenActivity;
import com.rb.elite.utility.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends BaseFragment implements IResponseSubcriber, View.OnClickListener {


    private Context mContext;
    EditText etPolicyNo, etEmail, etPassword, etconfirmPassword;
    Button btnVerify, btnSubmit, btnGo;
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
    UserEntity loginEntity;

    MakeAdapter makeAdapter;
    ModelAdapter modelAdapter;
    MakeEntity makeEntity;
    ModelEntity modelEntity;

    boolean IsMakeValid = false;
    boolean IsModelValid = false;
    Boolean isDataUploaded = true;


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
            if ((s.length() == 6) && (isDataUploaded)) {
                showDialog("Fetching City...");
                new RegisterController(getActivity()).getCityState(etPincode.getText().toString(), MyProfileFragment.this);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        return view;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser){
//
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();
        initialize_Widgets(view);

        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();

        setListener();
        acMake.setThreshold(2);
        acMake.setSelection(0);

        acModel.setThreshold(1);
        acModel.setSelection(0);

        addUserRequestEntity = new AddUserRequestEntity();
        updateUserRequestEntity = new UpdateUserRequestEntity();

        if(prefManager.getMake() != null) {
            makeAdapter = new MakeAdapter(getActivity(), R.layout.activity_sign_up, R.id.lbl_name, prefManager.getMake());
            acMake.setAdapter(makeAdapter);
        }else {
            acMake.setAdapter(null);
            startActivity(new Intent(getActivity(), SplashScreenActivity.class));

            getCustomToast("Your session has expired");
            getActivity().finish();

        }


        // region Make  Listener
        acMake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                acMake.setError(null);
                acMake.setSelection(0);
                IsMakeValid = true;
                makeEntity = makeAdapter.getItem(position);
                if (makeEntity.getModel() != null) {
                    modelAdapter = new ModelAdapter(getActivity(),
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


       /* acMake.addTextChangedListener(new TextWatcher() {
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
*/
        //endregion

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

        acMake.addTextChangedListener(textWatcherMake);
        acModel.addTextChangedListener(textWatcherModel);

        //endregion

        showDialog();
        new RegisterController(getActivity()).getUserProfile(this);


    }


    TextWatcher textWatcherMake = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (isDataUploaded) {

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
        }
    };

    TextWatcher textWatcherModel = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (isDataUploaded) {

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
        }
    };

    private void initialize_Widgets(View view) {


        llOtherInfo = (LinearLayout) view.findViewById(R.id.llOtherInfo);
        llCityInfo = (LinearLayout) view.findViewById(R.id.llCityInfo);


        etFullName = (EditText) view.findViewById(R.id.etFullName);
        etVehicle = (EditText) view.findViewById(R.id.etVehicle);
        etPolicyNo = (EditText) view.findViewById(R.id.etPolicyNo);

        etMobile = (EditText) view.findViewById(R.id.etMobile);
        etPincode = (EditText) view.findViewById(R.id.etPincode);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etconfirmPassword = (EditText) view.findViewById(R.id.etconfirmPassword);

        //  btnVerify = (Button) view.findViewById(R.id.btnVerify);

        etArea = (EditText) view.findViewById(R.id.etArea);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etState = (EditText) view.findViewById(R.id.etState);
        acMake = (AutoCompleteTextView) view.findViewById(R.id.acMake);
        acModel = (AutoCompleteTextView) view.findViewById(R.id.acModel);

        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnGo = (Button) view.findViewById(R.id.btnGo);

        etVehicle.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(20)});


    }

    private void setListener() {
        btnSubmit.setOnClickListener(this);
        btnGo.setOnClickListener(this);
        etPincode.addTextChangedListener(pincodeTextWatcher);
    }

    private boolean validateRegistration() {
        if (!isEmpty(etFullName)) {
            etFullName.requestFocus();
            etFullName.setError("Enter Name");
            return false;
        }

//        if (!isEmpty(etMobile)) {
//            etMobile.requestFocus();
//            etMobile.setError("Enter Mobile");
//            return false;
//        }
//        if (!isValidePhoneNumber(etMobile)) {
//            etMobile.requestFocus();
//            etMobile.setError("Enter Valid Mobile");
//            return false;
//        }
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

        if (!isEmpty(etVehicle)) {
            etVehicle.requestFocus();
            etVehicle.setError("Enter Vehicle Number");
            return false;
        }
//        if (!isEmpty(etPolicyNo)) {
//            etPolicyNo.requestFocus();
//            etMobile.setError("Enter Reliance Policy Number");
//            return false;
//        }

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

//        if (!isEmpty(etPincode) && etPincode.getText().toString().length() != 6) {
//            etPincode.requestFocus();
//            etPincode.setError("Enter Pincode");
//            return false;
//        }
//        if (!isEmpty(etPassword)) {
//            etPassword.requestFocus();
//            etPassword.setError("Enter Password");
//            return false;
//        }
//        if (etPassword.getText().toString().trim().length() < 3) {
//            etPassword.requestFocus();
//            etPassword.setError("Minimum length should be 3");
//            return false;
//        }
//        if (!isEmpty(etconfirmPassword)) {
//            etconfirmPassword.requestFocus();
//            etconfirmPassword.setError("Confirm Password");
//            return false;
//        }
//        if (!etPassword.getText().toString().equals(etconfirmPassword.getText().toString())) {
//            etconfirmPassword.requestFocus();
//            etconfirmPassword.setError("Password Mismatch");
//            return false;
//        }
        return true;
    }

    private void setRegisterRequest() {
        RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.setOtp("");
        registerRequest.setName("" + etFullName.getText());
        registerRequest.setEmailid("" + etEmail.getText());
        registerRequest.setMobile("" + etMobile.getText());
        registerRequest.setPassword("" + etPassword.getText());
        registerRequest.setLat("0");
        registerRequest.setLon("0");
        registerRequest.setUser_id(String.valueOf(loginEntity.getUser_id()));

//        if(mLocation != null){
//            registerRequest.setLat("" +  mLocation.getLatitude());
//            registerRequest.setLon("" +  mLocation.getLongitude());
//        }else{
//            registerRequest.setLat("0" );
//            registerRequest.setLon("0");
//        }


        registerRequest.setPincode("" + etPincode.getText().toString().trim());
        registerRequest.setState("" + etState.getText().toString().trim());
        registerRequest.setArea("");
        registerRequest.setCity("" + etCity.getText().toString().trim());

        registerRequest.setVehicle_no("" + etVehicle.getText().toString().trim());
        registerRequest.setPolicy_no("");

        registerRequest.setMake(""+acMake.getText().toString());
        registerRequest.setModel(""+acModel.getText().toString());


        showDialog();
        new RegisterController(getActivity()).saveUserProfile(registerRequest, this);
    }

    private void setProfile(ProfileEntity profile) {
        etFullName.setText("" + profile.getName());
        etVehicle.setText("" + profile.getVehicle_no());
        acMake.setText("" + profile.getMake());
        acModel.setText("" + profile.getModel());

        etEmail.setText("" + profile.getEmailid());
        etMobile.setText("" + profile.getMobile());

        etPincode.setText("" + profile.getPincode());
        etCity.setText("" + profile.getCity_id());
        etState.setText("" + profile.getState_id());

        etMobile.setEnabled(false);

        if(profile.getVehicle_no().trim().length()>0)
        {
            etVehicle.setEnabled(false);
            btnGo.setVisibility(View.GONE);
        }
    }

    private void resetMakeModel()
    {
        acMake.addTextChangedListener(textWatcherMake);
        acModel.addTextChangedListener(textWatcherModel);



        acModel.setError(null);
        acMake.setError(null);

        acMake.setText("");
        acModel.setText("");
        IsMakeValid = false;
        IsModelValid = false;
    }

    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view, getActivity());
        switch (view.getId()) {

            case R.id.btnSubmit:

                if (validateRegistration() == true) {

                    setRegisterRequest();

                }

                break;

            case R.id.btnGo:

                if (!isEmpty(etVehicle)) {
                    etVehicle.requestFocus();
                    etVehicle.setError("Enter Vehicle Number");
                    return;
                } else {

                    showDialog();
                    new RegisterController(getActivity()).getVechileDetails(etVehicle.getText().toString().trim(), this);

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

//                getCustomToast(response.getMessage());
            }
        } else if (response instanceof UserRegistrationResponse) {
            if (response.getStatus_code() == 0) {

                getCustomToast(response.getMessage());
            }
        } else if (response instanceof ProfileResponse) {
            if (response.getStatus_code() == 0) {
                isDataUploaded = false;
                IsMakeValid = true;
                IsModelValid = true;

                acModel.setError(null);
                acMake.setError(null);

                ProfileEntity profileEntity = ((ProfileResponse) response).getData().get(0);
                setProfile(profileEntity);
                isDataUploaded = true;

            }
        } else if (response instanceof FastLaneDataResponse) {

            // Toast.makeText(getActivity(),"Done",Toast.LENGTH_SHORT).show();
            FastLaneDataEntity fastLaneDataEntity = ((FastLaneDataResponse) response).getMasterData();

            try {
                if (fastLaneDataEntity != null && fastLaneDataEntity.getMake_Name() != "" && fastLaneDataEntity.getModel_Name() != "") {

                    acMake.setText("" + fastLaneDataEntity.getMake_Name());
                    acModel.setText("" + fastLaneDataEntity.getModel_Name());



                    acModel.setError(null);
                    acMake.setError(null);

                    IsMakeValid = true;
                    IsModelValid = true;

                } else {
                    resetMakeModel();

                }
            }catch (Exception ex)
            {
                resetMakeModel();

            }

        }

    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        // Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
        getCustomToast(t.getMessage());
    }



}
