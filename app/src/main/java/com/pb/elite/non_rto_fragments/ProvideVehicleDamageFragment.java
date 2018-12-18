package com.pb.elite.non_rto_fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.misc_non_rto.MiscNonRTOController;
import com.pb.elite.core.model.CityMainEntity;

import com.pb.elite.core.model.InsuranceCompanyEntity;
import com.pb.elite.core.model.ProductPriceEntity;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.UserConstatntEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.requestmodel.ProductPriceRequestEntity;
import com.pb.elite.core.requestmodel.ProvideClaimAssRequestEntity;
import com.pb.elite.core.response.MotorInsuranceListResponse;
import com.pb.elite.core.response.ProductPriceResponse;
import com.pb.elite.core.response.ProvideClaimAssResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.non_rto_fragments.adapter.IInsurer;
import com.pb.elite.non_rto_fragments.adapter.InsurerMainAdapter;
import com.pb.elite.product.ProductMainActivity;
import com.pb.elite.search.SearchCityActivity;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;
import com.pb.elite.utility.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProvideVehicleDamageFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber, IInsurer {

    // Service : 9


    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Context mContext;
    EditText etDate, etTime, etPlaceOfAccident, etInsCompanyName;


    // region Common Declaration
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText etCity, etVehicle, etPincode;
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

    int OrderID = 0;

    String CITY_ID = "";
    ProductPriceEntity productPriceEntity;

    // region Botom sheetDeclaration
    BottomSheetDialog mBottomSheetDialog;
    List<InsuranceCompanyEntity> insuranceCompanyEntityList;

    InsuranceCompanyEntity insuranceCompanyEntity;
    InsurerMainAdapter insurerMainAdapter;

    //endregion

    //endregion


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_provide_vehicle_damage, container, false);


        return view;
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


        showDialog();

        if (PRODUCT_CODE.equalsIgnoreCase("09")) {
            new MiscNonRTOController(mContext).getMotorInsuranceList(this);
        } else {
            new MiscNonRTOController(mContext).getHealthInsuranceList(this);
        }

        super.onViewCreated(view, savedInstanceState);

    }


    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());

        scrollView =  view.findViewById(R.id.scrollView);
        btnBooked =  view.findViewById(R.id.btnBooked);
        etCity =  view.findViewById(R.id.etCity);
        etPincode =  view.findViewById(R.id.etPincode);
        etVehicle =  view.findViewById(R.id.etVehicle);


        txtCharges =  view.findViewById(R.id.txtCharges);
        txtPrdName =  view.findViewById(R.id.txtPrdName);
        txtDoc =  view.findViewById(R.id.txtDoc);
        txtClientName =  view.findViewById(R.id.txtClientName);
        txtTAT =  view.findViewById(R.id.txtTAT);

        rlDoc =  view.findViewById(R.id.rlDoc);
        rlEditVehicle =  view.findViewById(R.id.rlEditVehicle);

        lyVehicle =  view.findViewById(R.id.lyVehicle);
        lvLogo =  view.findViewById(R.id.lvLogo);


        lyTAT =  view.findViewById(R.id.lyTAT);


        ivLogo =  view.findViewById(R.id.ivLogo);
        ivClientLogo =  view.findViewById(R.id.ivClientLogo);

        etDate = view.findViewById(R.id.etDate);
        etTime = view.findViewById(R.id.etTime);
        etPlaceOfAccident = view.findViewById(R.id.etPlaceOfAccident);
        etInsCompanyName = view.findViewById(R.id.etInsCompanyName);


    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);

        etInsCompanyName.setFocusable(false);
        etInsCompanyName.setClickable(true);

        rlDoc.setOnClickListener(this);
        rlEditVehicle.setOnClickListener(this);
        btnBooked.setOnClickListener(this);

        etCity.setOnClickListener(this);

        etInsCompanyName.setOnClickListener(this);
        etDate.setOnClickListener(this);
        etTime.setOnClickListener(this);


    }

    private void bindData() {
        Glide.with(getActivity())
                .load(userConstatntEntity.getCompanylogo())
                .into(ivClientLogo);

        txtClientName.setText(userConstatntEntity.getCompanyname());
        etVehicle.setText(userConstatntEntity.getVehicleno());

    }

    private void setVehicleEdiatable() {
        etVehicle.setEnabled(true);

        etVehicle.setText("");
        lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_content));

    }

    //region bottomSheetDialog
    public void getBottomSheetDialog() {

        mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.bottomSheetDialog);

        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);

        mBottomSheetDialog.setContentView(sheetView);
        TextView txtHdr = mBottomSheetDialog.findViewById(R.id.txtHdr);
        RecyclerView rvRTO = (RecyclerView) mBottomSheetDialog.findViewById(R.id.rvRTO);
        ImageView ivCross = (ImageView) mBottomSheetDialog.findViewById(R.id.ivCross);

        rvRTO.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRTO.setHasFixedSize(true);
        rvRTO.setNestedScrollingEnabled(false);
        insurerMainAdapter = new InsurerMainAdapter(ProvideVehicleDamageFragment.this, insuranceCompanyEntityList, this);
        rvRTO.setAdapter(insurerMainAdapter);
        rvRTO.setVisibility(View.VISIBLE);


        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBottomSheetDialog.isShowing()) {

                    mBottomSheetDialog.dismiss();
                }
            }
        });


        txtHdr.setText("Select Insurer Company");

        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.setCanceledOnTouchOutside(true);
        mBottomSheetDialog.show();


    }


    //endregion


    private boolean validate() {
        if (!isEmpty(etVehicle)) {
            etVehicle.requestFocus();
            etVehicle.setError("Enter Vehicle Number");
            return false;
        } else if (!isEmpty(etDate)) {
            etDate.requestFocus();
            etDate.setError("Enter Accident Date");
            return false;
        } else if (!isEmpty(etTime)) {
            etTime.requestFocus();
            etTime.setError("Enter Accident Time");
            return false;
        } else if (!isEmpty(etPlaceOfAccident)) {
            etPlaceOfAccident.requestFocus();
            etPlaceOfAccident.setError("Enter Place Of Accident");
            return false;
        } else if (!isEmpty(etInsCompanyName)) {
            etInsCompanyName.requestFocus();
            etInsCompanyName.setError("Enter Insurer Company Name");
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
        ProvideClaimAssRequestEntity requestEntity = new ProvideClaimAssRequestEntity();
        requestEntity.setAmount(txtCharges.getText().toString());
        requestEntity.setCityid(String.valueOf(CITY_ID));
        requestEntity.setPayment_status("0");
        requestEntity.setProdid(String.valueOf(PRODUCT_ID));
        requestEntity.setRto_id(productPriceEntity.getRto_id());
        requestEntity.setTransaction_id("");
        requestEntity.setUserid(String.valueOf(loginEntity.getUser_id()));
        requestEntity.setVehicleno(etVehicle.getText().toString());
        requestEntity.setPincode(etPincode.getText().toString());
        requestEntity.setAssistant_date(etDate.getText().toString());
        requestEntity.setAssistant_time(etTime.getText().toString());
        requestEntity.setAssistant_place(etPlaceOfAccident.getText().toString());
        requestEntity.setInsure_company_name("" + insuranceCompanyEntity.getInsurance_Id());

        new MiscNonRTOController(mContext).saveProvideClaimAssistance(requestEntity, this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.etDate:

                DateTimePicker.showPrevPickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                        if (view1.isShown()) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            String currentDay = simpleDateFormat.format(calendar.getTime());
                            etDate.setText(currentDay);
                            etDate.setError(null);
                        }
                    }
                });
                break;

            case R.id.etTime:

                DateTimePicker.showTimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (view.isShown()) {
                            String item = "";
                            if (hourOfDay >= 12 && minute > 0)
                                item = " PM";
                            else
                                item = " AM";


                            etTime.setText("" + hourOfDay + " : " + minute + item);
                            etTime.setError(null);

                        }
                    }
                });


                break;



            case R.id.rlEditVehicle:

                setVehicleEdiatable();
                break;
            case R.id.btnBooked:

                if (validate() == false) {
                    return;
                } else {

                    saveData();
                }

                break;

            case R.id.etInsCompanyName:
                if (insuranceCompanyEntityList != null && insuranceCompanyEntityList.size() > 0) {
                        getBottomSheetDialog();
                    }


                break;

            case R.id.etCity:

                if (!isEmpty(etVehicle)) {
                    etVehicle.requestFocus();
                    etVehicle.setError("Enter Vehicle Number");
                    return;
                }
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
                CITY_ID = String.valueOf(cityMainEntity.getCity_id());
                etCity.setText(cityMainEntity.getCityname());
                etCity.setError(null);

                showDialog();

                //region call Price Controller
                ProductPriceRequestEntity entity = new ProductPriceRequestEntity();
                entity.setVehicleno(etVehicle.getText().toString());
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
        } else if (response instanceof ProvideClaimAssResponse) {
            cancelDialog();
            if (response.getStatus_code() == 0) {

                OrderID = (((ProvideClaimAssResponse) response).getData().get(0).getId());
                String DisplayMessage = (((ProvideClaimAssResponse) response).getData().get(0).getDisplaymessage());
                showMiscPaymentAlert(btnBooked, response.getMessage().toString(), DisplayMessage, OrderID);

            }
        } else if (response instanceof MotorInsuranceListResponse) {
            cancelDialog();
            if (response.getStatus_code() == 0) {

                insuranceCompanyEntityList = ((MotorInsuranceListResponse) response).getData();

                //
            }
        }
        //
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getInsurer(InsuranceCompanyEntity entity) {

        if (mBottomSheetDialog != null) {

            if (mBottomSheetDialog.isShowing()) {
                if (entity != null) {

                    insuranceCompanyEntity = entity;
                    etInsCompanyName.setText("" + insuranceCompanyEntity.getInsurance_Name());
                    mBottomSheetDialog.dismiss();
                }

            }

        }
    }
}
