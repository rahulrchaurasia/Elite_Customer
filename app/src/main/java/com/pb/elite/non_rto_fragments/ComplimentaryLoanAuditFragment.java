package com.pb.elite.non_rto_fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.misc_non_rto.MiscNonRTOController;
import com.pb.elite.core.model.CityMainEntity;
import com.pb.elite.core.model.ProductPriceEntity;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.UserConstatntEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.requestmodel.ComplimentaryLoanAuditRequestEntity;
import com.pb.elite.core.requestmodel.ProductPriceRequestEntity;
import com.pb.elite.core.requestmodel.ProvideClaimAssRequestEntity;
import com.pb.elite.core.response.ProductPriceResponse;
import com.pb.elite.core.response.ProvideClaimAssResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.product.ProductMainActivity;
import com.pb.elite.search.SearchCityActivity;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;
import com.pb.elite.utility.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ComplimentaryLoanAuditFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Context mContext;
    EditText etDOB,etNameOfApplicant,etloaneligibility,etExistingEMI,etRequiredLoanAmount;
    RadioGroup rgloantype;
    RadioButton rbimgsl, rbimgse, rbimgpro;

    // region Common Declaration
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText etPincode,  etCity  ;
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    Button btnBooked;

    RTOServiceEntity serviceEntity;

    ScrollView scrollView;
    LinearLayout lyVehicle, lvLogo, lyTAT;
    RelativeLayout rlDoc, rlEditVehicle;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;


    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;

    String AMOUNT = "0";
    int OrderID = 0;

    String CITY_ID;
    ProductPriceEntity productPriceEntity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complimentary_loan_audit, container, false);
        return view;
    }

    private void initialize(View view) {
        prefManager = new PrefManager(getActivity());

        scrollView =  view.findViewById(R.id.scrollView);
        btnBooked =  view.findViewById(R.id.btnBooked);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etPincode =  (EditText) view.findViewById(R.id.etPincode);

        etNameOfApplicant =  view.findViewById(R.id.etNameOfApplicant);
        etloaneligibility =  view.findViewById(R.id.etloaneligibility);
        etExistingEMI =  view.findViewById(R.id.etExistingEMI);
        etRequiredLoanAmount =  view.findViewById(R.id.etRequiredLoanAmount);
        etDOB = view.findViewById(R.id.etDOB);

        txtCharges =  view.findViewById(R.id.txtCharges);
        txtPrdName =  view.findViewById(R.id.txtPrdName);
        txtDoc =  view.findViewById(R.id.txtDoc);
        txtClientName =  view.findViewById(R.id.txtClientName);
        txtTAT =  view.findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        rlEditVehicle = (RelativeLayout) view.findViewById(R.id.rlEditVehicle);

        lvLogo =  view.findViewById(R.id.lvLogo);
        lyTAT =  view.findViewById(R.id.lyTAT);

        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);
    }

    private void setOnClickListener() {
        etCity.setFocusable(false);
        etCity.setClickable(true);

        rlDoc.setOnClickListener(this);
        rlEditVehicle.setOnClickListener(this);
        btnBooked.setOnClickListener(this);

        etCity.setOnClickListener(this);
        etDOB.setOnClickListener(this);

    }

    private void bindData() {
        Glide.with(getActivity())
                .load(userConstatntEntity.getCompanylogo())
                .into(ivClientLogo);

        txtClientName.setText(userConstatntEntity.getCompanyname());
      //  etVehicle.setText(userConstatntEntity.getVehicleno());

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();
        initialize(view);
        setOnClickListener();

        dataBaseController = new DataBaseController(getActivity());
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();

        OrderID = 0;
        bindData();

        // region Filter Type

        if (getArguments() != null) {


            if (getArguments().getParcelable(Constants.SUB_PRODUCT_DATA) != null) {

                serviceEntity = getArguments().getParcelable(Constants.SUB_PRODUCT_DATA);
                PRODUCT_NAME = serviceEntity.getName();
                PRODUCT_ID = serviceEntity.getId();
                PRODUCT_CODE = serviceEntity.getProductcode();

            }

            //endregion

            txtPrdName.setText("" + PRODUCT_NAME);

        }

        // endregion

        super.onViewCreated(view, savedInstanceState);

    }


    private boolean validate() {
        if (!isEmpty(etNameOfApplicant)) {
            etNameOfApplicant.requestFocus();
            etNameOfApplicant.setError("Enter Name Of Applicant");
            return false;
        } else if (!isEmpty(etDOB)) {
            etDOB.requestFocus();
            etDOB.setError("Enter Date Of Birth");
            return false;
        } else if (!isEmpty(etloaneligibility)) {
            etloaneligibility.requestFocus();
            etloaneligibility.setError("Enter Annual Income According To Loan Eligibility");
            return false;
        } else if (!isEmpty(etRequiredLoanAmount)) {
            etRequiredLoanAmount.requestFocus();
            etRequiredLoanAmount.setError("Enter Required Loan Amount");
            return false;

        } else if (!isEmpty(etCity)) {
            etCity.requestFocus();
            etCity.setError("Enter City");
            return false;
        }
        if (!isEmpty(etPincode) && etPincode.getText().toString().length() != 6) {
            etPincode.requestFocus();
            etPincode.setError("Enter Pincode");
            return false;
        }
        return true;
    }


    private void getTatData() {
        if (productPriceEntity != null) {
            lvLogo.setVisibility(View.VISIBLE);
            txtCharges.setText(productPriceEntity.getPrice());
            txtTAT.setText(productPriceEntity.getTAT());

        } else {
            lvLogo.setVisibility(View.GONE);
        }
    }

    private void saveData() {

        showDialog();
        ComplimentaryLoanAuditRequestEntity requestEntity = new ComplimentaryLoanAuditRequestEntity();
        requestEntity.setAmount(txtCharges.getText().toString());
        requestEntity.setCityid(String.valueOf(CITY_ID));
        requestEntity.setPayment_status("0");
        requestEntity.setProdid(String.valueOf(PRODUCT_ID));
        requestEntity.setRto_id(productPriceEntity.getRto_id());
        requestEntity.setTransaction_id("");
        requestEntity.setUserid(String.valueOf(loginEntity.getUser_id()));

        requestEntity.setPincode(etPincode.getText().toString());
        requestEntity.setCom_name("");
        requestEntity.setDOB(etDOB.getText().toString());
        requestEntity.setAnnual_income(etloaneligibility.getText().toString());

        if (rbimgsl.isChecked()) {
            requestEntity.setSalaried("");
        } else if (rbimgse.isChecked()) {
            requestEntity.setSalaried("");
        } else if (rbimgpro.isChecked()) {
            requestEntity.setSalaried("");
        }

        requestEntity.setAny_EMI("N");
        requestEntity.setAmount("" + etRequiredLoanAmount.getText().toString());

        new MiscNonRTOController(mContext).saveComplimentaryLoanAudit(requestEntity, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etDOB:
                DateTimePicker.showOpenDatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                        if (view1.isShown()) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            String currentDay = simpleDateFormat.format(calendar.getTime());
                            etDOB.setText(currentDay);
                        }
                    }
                });
                break;
            case R.id.rlDoc:

                ((ProductMainActivity) getActivity()).getProducDoc(PRODUCT_ID);
                break;
            case R.id.btnBooked:

                if (validate() == false) {
                    return;
                } else {

                    saveData();
                }

                break;
            case R.id.etCity:

                startActivityForResult(new Intent(getActivity(), SearchCityActivity.class), Constants.SEARCH_CITY_CODE);

                break;

        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SEARCH_CITY_CODE) {
            if (data != null) {

                CityMainEntity cityMainEntity = data.getParcelableExtra(Constants.SEARCH_CITY_DATA);
                CITY_ID =  String.valueOf(cityMainEntity.getCity_id());
                etCity.setText(cityMainEntity.getCityname());
                etCity.setError(null);

                showDialog();

                //region call Price Controller
                ProductPriceRequestEntity entity = new ProductPriceRequestEntity();
              //  entity.setVehicleno(etVehicle.getText().toString());
                entity.setCityid(CITY_ID);
                entity.setProduct_id(String.valueOf(PRODUCT_ID));
                entity.setProductcode(PRODUCT_CODE);
                entity.setUserid(String.valueOf(loginEntity.getUser_id()));
                entity.setMake("");
                entity.setModel("");

                new MiscNonRTOController(mContext).getProductTAT(entity, this);

                //endregion

            }
        }

    }


    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
        if (response instanceof ProductPriceResponse) {
            if (response.getStatus_code() == 0) {

                productPriceEntity = ((ProductPriceResponse) response).getData().get(0);
                getTatData();

            }
        }
        else if (response instanceof ProvideClaimAssResponse) {
            if (response.getStatus_code() == 0) {

                OrderID = (((ProvideClaimAssResponse) response).getData().get(0).getId());
                String DisplayMessage = (((ProvideClaimAssResponse) response).getData().get(0).getDisplaymessage());
                showMiscPaymentAlert(btnBooked, response.getMessage().toString(),DisplayMessage, OrderID);

            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
    }
}