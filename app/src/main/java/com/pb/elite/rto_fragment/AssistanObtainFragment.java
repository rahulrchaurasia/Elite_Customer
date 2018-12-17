package com.pb.elite.rto_fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.CityMainEntity;
import com.pb.elite.core.model.CorrectiontEnity;
import com.pb.elite.core.model.DocProductEnity;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.RtoCityMain;
import com.pb.elite.core.model.RtoProductDisplayMainEntity;
import com.pb.elite.core.model.RtoProductEntity;
import com.pb.elite.core.model.UserConstatntEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.requestmodel.ExtrarequestEntity;
import com.pb.elite.core.requestmodel.InsertOrderRequestEntity;
import com.pb.elite.core.response.CityResponse;
import com.pb.elite.core.response.ProductDocumentResponse;
import com.pb.elite.core.response.RtoProductDisplayResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.product.ProductActivity;
import com.pb.elite.product.ProductDocAdapter;
import com.pb.elite.product.ProductMainActivity;
import com.pb.elite.rto_fragment.adapter.CityMainAdapter;
import com.pb.elite.rto_fragment.adapter.IRTOCity;
import com.pb.elite.rto_fragment.adapter.RtoMainAdapter;
import com.pb.elite.search.SearchCityActivity;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;
import com.pb.elite.utility.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssistanObtainFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, IResponseSubcriber, IRTOCity {


    // region Declaration
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText etRTO, etRTO_OTH, etCity, etLic;
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    Button btnBooked;

    RTOServiceEntity serviceEntity;

    ScrollView scrollView;
    LinearLayout lvLogo, llDocumentUpload, lyRTO, lyTAT;
    RelativeLayout rlDoc, rlCorrect;
    LinearLayout lyLic, llCorrection, lyName, lyDOB, lyAddress, lyVehicleType;
    ImageView ivLogo, ivClientLogo, ivArrow, ivLic;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;
    EditText etName, etDOB, etAddress;
    CheckBox chkName, chkDOB, chkAddress;

    TextView ivTick;


    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;
    int PARENT_PRODUCT_ID = 0;

    String AMOUNT = "0";
    int OrderID = 0;

    int CITY_ID;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    // region Botom sheetDeclaration

    BottomSheetDialog mBottomSheetDialog;


    CityMainEntity cityMainEntity;
    RtoCityMain rtoMainEntity;

    RtoMainAdapter rtoMainAdapter;

    //endregion

    //endregion


    //region datepicker

    protected View.OnClickListener datePickerDialog = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.etDOB) {
                DateTimePicker.showAgePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
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

            }

        }
    };

    //endregion
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_assistan_obtain, container, false);
        initialize(view);

        rlCorrect.setVisibility(View.GONE);
        llCorrection.setVisibility(View.GONE);
        lyVehicleType.setVisibility(View.GONE);

        setOnClickListener();

        dataBaseController = new DataBaseController(getActivity());
        loginEntity = dataBaseController.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();
        OrderID = 0;
        bindData();

        // region Filter Type

        if (getArguments() != null) {


            if (getArguments().getParcelable(Constants.SUB_PRODUCT_DATA) != null) {

                serviceEntity = getArguments().getParcelable(Constants.SUB_PRODUCT_DATA);
                PRODUCT_NAME = serviceEntity.getName();
                PARENT_PRODUCT_ID = serviceEntity.getId();
                PRODUCT_CODE = serviceEntity.getProductcode();

                if (PRODUCT_CODE.equalsIgnoreCase("2.1")) {
                    lyLic.setVisibility(View.GONE);
                    lyVehicleType.setVisibility(View.VISIBLE);


                } else if (PRODUCT_CODE.equalsIgnoreCase("2.2") || PRODUCT_CODE.equalsIgnoreCase("2.3")) {
                    lyLic.setVisibility(View.VISIBLE);
                    lyVehicleType.setVisibility(View.VISIBLE);

                }else if (PRODUCT_CODE.equalsIgnoreCase("2.4")) {
                    lyLic.setVisibility(View.VISIBLE);
                    rlCorrect.setVisibility(View.VISIBLE);

                }

            }

            //endregion

            txtPrdName.setText("" + PRODUCT_NAME);
            Toast.makeText(getActivity(), "" + PRODUCT_ID + "/" + PRODUCT_CODE, Toast.LENGTH_SHORT).show();
        }


        // endregion


        showDialog();
        new ProductController(getActivity()).getRTOProductList(PARENT_PRODUCT_ID, PRODUCT_CODE, loginEntity.getUser_id(), AssistanObtainFragment.this);


        return view;
    }


    //region bottomSheetDialog
    public void getBottomSheetDialog() {

        if(cityMainEntity.getRTOList().size() == 0)
        {
            getCustomToast("No RTO Available");
        }

        mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.bottomSheetDialog);

        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);

        mBottomSheetDialog.setContentView(sheetView);
        TextView txtHdr = mBottomSheetDialog.findViewById(R.id.txtHdr);
        RecyclerView rvRTO = (RecyclerView) mBottomSheetDialog.findViewById(R.id.rvRTO);
        ImageView ivCross = (ImageView) mBottomSheetDialog.findViewById(R.id.ivCross);

        rvRTO.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRTO.setHasFixedSize(true);
        rvRTO.setNestedScrollingEnabled(false);
        rtoMainAdapter = new RtoMainAdapter(AssistanObtainFragment.this, cityMainEntity.getRTOList(), this);
        rvRTO.setAdapter(rtoMainAdapter);
        rvRTO.setVisibility(View.VISIBLE);


        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBottomSheetDialog.isShowing()) {

                    mBottomSheetDialog.dismiss();
                }
            }
        });


        txtHdr.setText("Select RTO");

        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.setCanceledOnTouchOutside(true);
        mBottomSheetDialog.show();



    }

    public void setRTOData(String strRTOName, RtoCityMain rtoEntity) {
        etRTO.setText("" + strRTOName);

        rtoMainEntity = rtoEntity;

    }

    //endregion


    public void setCityData(String strCityName, CityMainEntity rtoPrdEntity) {
        etCity.setText("" + strCityName);
        etRTO.setText("");
        cityMainEntity = rtoPrdEntity;

//        if (cityMainEntity != null) {
//            if (cityMainEntity.getCity_id() == 2653) {
//
//                etRTO_OTH.setVisibility(View.VISIBLE);
//                etRTO.setVisibility(View.GONE);
//                lvLogo.setVisibility(View.VISIBLE);
//                setRtoTAT(cityMainEntity);
//
//
//            } else {
//                etRTO.setVisibility(View.VISIBLE);
//                etRTO_OTH.setVisibility(View.GONE);
//                lvLogo.setVisibility(View.VISIBLE);
//                setRtoTAT(cityMainEntity);
//
//
//            }
//        }
    }




    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());

         scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        btnBooked = (Button) view.findViewById(R.id.btnBooked);

        etRTO = (EditText) view.findViewById(R.id.etRTO);
        etRTO_OTH = (EditText) view.findViewById(R.id.etRTO_OTH);
        etCity = (EditText) view.findViewById(R.id.etCity);


        etLic = (EditText) view.findViewById(R.id.etLic);

        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        etName = (EditText) view.findViewById(R.id.etName);
        etDOB = (EditText) view.findViewById(R.id.etDOB);
        etAddress = (EditText) view.findViewById(R.id.etAddress);

        chkName = (CheckBox) view.findViewById(R.id.chkName);
        chkDOB = (CheckBox) view.findViewById(R.id.chkDOB);
        chkAddress = (CheckBox) view.findViewById(R.id.chkAddress);


        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        rlCorrect = (RelativeLayout) view.findViewById(R.id.rlCorrect);

        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);
        llDocumentUpload = (LinearLayout) view.findViewById(R.id.llDocumentUpload);
        lyRTO = (LinearLayout) view.findViewById(R.id.lyRTO);
        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);

        lyLic = (LinearLayout) view.findViewById(R.id.lyLic);

        llCorrection = (LinearLayout) view.findViewById(R.id.llCorrection);
        lyName = (LinearLayout) view.findViewById(R.id.lyName);
        lyDOB = (LinearLayout) view.findViewById(R.id.lyDOB);
        lyAddress = (LinearLayout) view.findViewById(R.id.lyAddress);
        lyVehicleType = (LinearLayout) view.findViewById(R.id.lyVehicleType);


        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);
        ivArrow = (ImageView) view.findViewById(R.id.ivArrow);
        ivLic = (ImageView) view.findViewById(R.id.ivLic);
        ivTick = (TextView) view.findViewById(R.id.ivTick);


    }


    private void setOnClickListener() {

        etDOB.setOnClickListener(datePickerDialog);
        etCity.setFocusable(false);
        etCity.setClickable(true);

        etRTO.setFocusable(false);
        etRTO.setClickable(true);

        rlDoc.setOnClickListener(this);
        rlCorrect.setOnClickListener(this);
        btnBooked.setOnClickListener(this);


        etCity.setOnClickListener(this);
        etRTO.setOnClickListener(this);

        chkName.setOnCheckedChangeListener(this);
        chkDOB.setOnCheckedChangeListener(this);
        chkAddress.setOnCheckedChangeListener(this);


    }

    private void bindData() {
        Glide.with(getActivity())
                .load(userConstatntEntity.getCompanylogo())
                .into(ivClientLogo);

        txtClientName.setText(userConstatntEntity.getCompanyname());

    }

//    public List<RtoProductDisplayMainEntity> removeDuplicateCity(List<RtoProductDisplayMainEntity> list) {
//        for (int i = 0; i < list.size(); i++) {
//            for (int j = i + 1; j < list.size(); j++) {
//
//                if ((list.get(i).getCity_id() == (list.get(j).getCity_id()))) {
//                    list.remove(j);
//                    j--;
//                }
//            }
//        }
//        return list;
//    }


    private void setRtoTAT(RtoProductDisplayMainEntity rtoProd) {
//        if (rtoProd.getPrice() != null) {
//            txtCharges.setText("" + "\u20B9" + " " + rtoProd.getPrice());
//            AMOUNT = rtoProd.getPrice().trim();
//        }
//
//        if (rtoProd.getTAT() != null) {
//            lyTAT.setVisibility(View.VISIBLE);
//            txtTAT.setText("" + rtoProd.getTAT());
//        } else {
//            lyTAT.setVisibility(View.GONE);
//        }

        Glide.with(getActivity())
                .load(rtoProd.getProduct_logo())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(ivLogo);


    }

    private boolean validate() {

        if ((etCity.getText().toString().trim().length() == 0)) {
            getCustomToast("Please Selct City");
            return false;
        }

        if ((etRTO.getText().toString().trim().length() == 0)) {
            getCustomToast("Please Selct RTO");
            return false;
        }

        if ((etLic.getText().toString().trim().length() == 0) && (lyLic.getVisibility() == View.VISIBLE)) {
            getCustomToast("Please Enter Driving License");
            return false;
        }

        if (PRODUCT_CODE.equalsIgnoreCase("2.4")) {
            if ((chkAddress.isChecked() == false) && (chkDOB.isChecked() == false) && (chkName.isChecked() == false)) {
                getCustomToast("Please Select Atlease One Field");
                ivTick.setVisibility(View.VISIBLE);

                return false;
            }

            if (chkName.isChecked() && (etName.getText().toString().trim().length() == 0)) {
                getCustomToast("Please Enter Correction In Name");
                ivTick.setVisibility(View.VISIBLE);
                return false;
            }
            if (chkDOB.isChecked() && (etDOB.getText().toString().trim().length() == 0)) {
                getCustomToast("Please Enter Correction In DOB");
                ivTick.setVisibility(View.VISIBLE);
                return false;
            }
            if (chkAddress.isChecked() && (etAddress.getText().toString().trim().length() == 0)) {
                getCustomToast("Please Enter Correction In Address");
                ivTick.setVisibility(View.VISIBLE);
                return false;
            }

            ivTick.setVisibility(View.GONE);

        }

        return true;
    }





    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
//        if (response instanceof OrderResponse) {
//            if (response.getStatus_code() == 0) {
//
//                OrderID = (((OrderResponse) response).getData().get(0).getId());
//
//                showPaymentAlert(btnBooked, response.getMessage().toString(), OrderID);
//
//            }
//
//        } else

        if (response instanceof RtoProductDisplayResponse) {
            if (response.getStatus_code() == 0) {

                if (((RtoProductDisplayResponse) response).getData().size() > 0) {


                    PRODUCT_ID = ((RtoProductDisplayResponse) response).getData().get(0).getProd_id();
                    //     listCityMain = removeDuplicateCity(((RtoProductDisplayResponse) response).getData());


                }
            }
        } else if (response instanceof ProductDocumentResponse) {
            if (response.getStatus_code() == 0) {

                if (((ProductDocumentResponse) response).getData() != null) {

                    reqDocPopUp(((ProductDocumentResponse) response).getData());
                } else {

                    Toast.makeText(getActivity(), "No Data Available", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (response instanceof CityResponse) {


            if (response.getStatus_code() == 0) {

                //   bindAutoCity();
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void setScrollatBottom() {
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 1000);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {


            case R.id.rlDoc:
                showDialog();
                new ProductController(getActivity()).getProducDoc(PRODUCT_ID, AssistanObtainFragment.this);
                break;


            case R.id.rlCorrect:

                if (llCorrection.getVisibility() == View.GONE) {
                    llCorrection.setVisibility(View.VISIBLE);
                    ivArrow.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow));

                } else {
                    llCorrection.setVisibility(View.GONE);
                    ivArrow.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow));
                }
                //  setScrollatBottom();
                break;

            case R.id.btnBooked:

                if (validate() == false) {
                    return;
                }

                //region RTO Payment

                int rtoID = rtoMainEntity.getId();
                InsertOrderRequestEntity requestEntity = new InsertOrderRequestEntity();

                ExtrarequestEntity extrarequest = new ExtrarequestEntity();
                extrarequest.setMakeNo("NULL");
                extrarequest.setModelNo("NULL");
                extrarequest.setVehicleNo("NULL");
                extrarequest.setDrivingLic(etLic.getText().toString());

                requestEntity.setProdid("" + PRODUCT_ID);
                requestEntity.setProdName("" + PRODUCT_NAME);
                requestEntity.setUserid("" + loginEntity.getUser_id());
                requestEntity.setTransaction_id("");
                requestEntity.setSubscription("");
                requestEntity.setVehicleno("");
                requestEntity.setPucexpirydate("");
                requestEntity.setRto_id("" + rtoID);
                requestEntity.setCityid("" + CITY_ID);
                requestEntity.setAmount("" + AMOUNT);
                requestEntity.setPayment_status("0");
                requestEntity.setExtrarequest(new Gson().toJson(extrarequest));


                //endregion

                ((ProductMainActivity) getActivity()).sendPaymentRequest(requestEntity);

                break;
            case R.id.etCity:

                startActivityForResult(new Intent(getActivity(), SearchCityActivity.class), Constants.SEARCH_CITY_CODE);


                break;

            case R.id.etRTO:
                // setScrollatBottom();
                if (!etCity.getText().toString().equalsIgnoreCase("")) {

                    getBottomSheetDialog();
                } else {
                    getCustomToast("Select City");
                }
                break;
        }


    }

    @Override
    public void getRTOCity(RtoCityMain entity) {

        if (mBottomSheetDialog != null) {

            if (mBottomSheetDialog.isShowing()) {
                if (entity != null) {

                    rtoMainEntity = entity;

                    setRTOData("" + rtoMainEntity.getSeries_no() + "-" + rtoMainEntity.getRto_location(), rtoMainEntity);
                    mBottomSheetDialog.dismiss();
                }

            }

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.chkName) {

            if (isChecked) {

                lyName.setBackgroundColor(getResources().getColor(R.color.seperator));

            } else {
                lyName.setBackgroundColor(getResources().getColor(R.color.white));
            }

        } else if (buttonView.getId() == R.id.chkDOB) {

            if (isChecked) {

                lyDOB.setBackgroundColor(getResources().getColor(R.color.seperator));

            } else {
                lyDOB.setBackgroundColor(getResources().getColor(R.color.white));
            }

        } else if (buttonView.getId() == R.id.chkAddress) {

            if (isChecked) {

                lyAddress.setBackgroundColor(getResources().getColor(R.color.seperator));

            } else {
                lyAddress.setBackgroundColor(getResources().getColor(R.color.white));
            }

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SEARCH_CITY_CODE) {
            if (data != null) {

                cityMainEntity = data.getParcelableExtra(Constants.SEARCH_CITY_DATA);
                CITY_ID = cityMainEntity.getCity_id();
                etCity.setText(cityMainEntity.getCityname());

            }
        }

    }
}
