package com.rb.elite.profile;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rb.elite.BaseFragment;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.register.RegisterController;
import com.rb.elite.core.model.PincodeEntity;
import com.rb.elite.core.model.UserEntity;
import com.rb.elite.core.requestmodel.UpdateUserRequestEntity;
import com.rb.elite.core.response.PincodeResponse;
import com.rb.elite.core.response.UpdateUserResponse;
import com.rb.elite.database.DataBaseController;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.utility.Constants;

/**
 * Created by Rajeev Ranjan on 05/02/2018.
 */

public class ProfileFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {
    EditText etelite_card_no, etpolicy_no, etEmail, etPassword, etconfirmPassword;
    Button btnUpdate, btnSubmit;
    EditText etFullName, etMobile, etPincode, etArea, etCity, etState;
    FloatingActionButton fab;
    PincodeEntity pincodeEntity;
    UserEntity loginEntity;
    DataBaseController dataBaseController;
    UpdateUserRequestEntity updateUserRequestEntity;
    PrefManager prefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product, container, false);

        View view = inflater.inflate(R.layout.content_profile, container, false);
        dataBaseController = new DataBaseController(getActivity());
        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();
        updateUserRequestEntity = new UpdateUserRequestEntity();

        init_widets(view);
        setListener();
        if (loginEntity != null) {
            Constants.hideKeyBoard(etEmail, getActivity());
            bindUserDatas();
        } else {
            setAllEditable();
        }
        return view;
    }

    private void bindUserDatas() {
        btnUpdate.setVisibility(View.GONE);
        etFullName.setText("" + loginEntity.getName());
        etEmail.setText("" + loginEntity.getEmail());
        etMobile.setText("" + loginEntity.getMobile());
        etpolicy_no.setText("" + loginEntity.getPolicy_no());
        etelite_card_no.setText("" + loginEntity.getCard_no());
        etPincode.setText("" + loginEntity.getPincode());
        etState.setText("" + loginEntity.getState_name());
        etCity.setText("" + loginEntity.getCityname());
        etArea.setText("" + loginEntity.getArea());
    }

    private void setListener() {
        fab.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

    }

    private void init_widets(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        etelite_card_no = (EditText) view.findViewById(R.id.etelite_card_no);
        etpolicy_no = (EditText) view.findViewById(R.id.etpolicy_no);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etconfirmPassword = (EditText) view.findViewById(R.id.etconfirmPassword);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        etFullName = (EditText) view.findViewById(R.id.etFullName);
        etMobile = (EditText) view.findViewById(R.id.etMobile);
        etPincode = (EditText) view.findViewById(R.id.etPincode);
        etArea = (EditText) view.findViewById(R.id.etArea);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etState = (EditText) view.findViewById(R.id.etState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                setAllEditable();
                break;
            case R.id.btnUpdate:
                if (!isEmpty(etelite_card_no)) {
                    etelite_card_no.requestFocus();
                    etelite_card_no.setError("Enter Card Number");
                    return;
                }
                if (!isEmpty(etpolicy_no)) {
                    etpolicy_no.requestFocus();
                    etpolicy_no.setError("Enter Policy number");
                    return;
                }
                if (!isValidePhoneNumber(etMobile)) {
                    etMobile.requestFocus();
                    etMobile.setError("Enter Mobile");
                    return;
                }
                if (!isValideEmailID(etEmail)) {
                    etEmail.requestFocus();
                    etEmail.setError("Enter Email");
                    return;
                }
                if (!isEmpty(etFullName)) {
                    etFullName.requestFocus();
                    etFullName.setError("Enter Name");
                    return;
                }
                if (!isEmpty(etPincode) && etPincode.getText().toString().length() != 6) {
                    etPincode.requestFocus();
                    etPincode.setError("Enter Pincode");
                    return;
                }

                updateUserRequestEntity.setOtp(0);
                updateUserRequestEntity.setName(etFullName.getText().toString());
                updateUserRequestEntity.setEmail(etEmail.getText().toString());
                updateUserRequestEntity.setMobile(etMobile.getText().toString());
                updateUserRequestEntity.setPincode(etPincode.getText().toString());
                if (pincodeEntity != null) {
                    updateUserRequestEntity.setState("" + pincodeEntity.getState_id());
                    updateUserRequestEntity.setCity("" + pincodeEntity.getCity_id());
                    updateUserRequestEntity.setArea("" + pincodeEntity.getPostname());
                    updateUserRequestEntity.setAddress("" + pincodeEntity.getState_name());
                } else {
                    updateUserRequestEntity.setState("" + loginEntity.getState_id());
                    updateUserRequestEntity.setCity("" + loginEntity.getCity_id());
                    updateUserRequestEntity.setArea("" + loginEntity.getArea());
                    updateUserRequestEntity.setAddress("" + loginEntity.getState_name());
                }
                updateUserRequestEntity.setRto("1");
                showDialog();
                new RegisterController(getActivity()).updateUser(updateUserRequestEntity, this);
                break;
        }
    }

    private void setAllEditable() {
        etPincode.addTextChangedListener(pincodeTextWatcher);
        btnUpdate.setVisibility(View.VISIBLE);
        etelite_card_no.setEnabled(true);
        etpolicy_no.setEnabled(true);
        etMobile.setEnabled(true);
        etEmail.setEnabled(true);
        etPincode.setEnabled(true);
        etFullName.setEnabled(true);
        etPincode.setEnabled(true);
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
        if (response instanceof UpdateUserResponse) {
            cancelDialog();
            if (response.getStatus_code() == 0) {
                Toast.makeText(getActivity(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (response instanceof PincodeResponse) {
            cancelDialog();
            if (response.getStatus_code() == 0) {
                pincodeEntity = ((PincodeResponse) response).getData().get(0);
                if (pincodeEntity != null) {
                    etArea.setText("" + pincodeEntity.getPostname());
                    etCity.setText("" + pincodeEntity.getCityname());
                    etState.setText("" + pincodeEntity.getState_name());
                }
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                new RegisterController(getActivity()).getCityState(etPincode.getText().toString(), ProfileFragment.this);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    //endregion
}
