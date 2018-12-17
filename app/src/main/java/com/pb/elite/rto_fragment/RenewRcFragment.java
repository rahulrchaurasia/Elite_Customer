package com.pb.elite.rto_fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.pb.elite.core.model.DocProductEnity;
import com.pb.elite.core.model.MakeEntity;
import com.pb.elite.core.model.ModelEntity;
import com.pb.elite.core.model.RTOServiceEntity;

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
import com.pb.elite.product.ProductDocAdapter;
import com.pb.elite.product.ProductMainActivity;
import com.pb.elite.register.MakeAdapter;
import com.pb.elite.register.ModelAdapter;
import com.pb.elite.rto_fragment.adapter.CityMainAdapter;
import com.pb.elite.rto_fragment.adapter.IRTOCity;
import com.pb.elite.rto_fragment.adapter.RtoMainAdapter;
import com.pb.elite.search.SearchCityActivity;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RenewRcFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {


    // Code : 1.0
    // Validation Pending

    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    // RecyclerView rvCity , rvRTO;

    TextView textCity, txtModel;
    Spinner spRTO, spCity;
    EditText etPincode,etVehicle, etCity;
    List<String> RtoList, CityList;  //ProductList,
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    String CITY_ID_NON_RTO = "";
    Button btnBooked;

    RTOServiceEntity serviceEntity;


    LinearLayout lyVehicle ,lvLogo, llDocumentUpload, lyRTO, lyTAT;
    RelativeLayout rlDoc, rlEditMakeModel;
    LinearLayout lyMakeModel;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;

    AutoCompleteTextView acMake, acModel;

    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;
    int PARENT_PRODUCT_ID = 0;

    String AMOUNT = "0";
    int OrderID = 0;


    MakeAdapter makeAdapter;
    ModelAdapter modelAdapter;
    MakeEntity makeEntity;
    ModelEntity modelEntity;

    boolean IsMakeValid = false;
    boolean IsModelValid = false;

    String CITY_ID;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_renew_rc, container, false);
        initialize(view);

        setOnClickListener();


        dataBaseController = new DataBaseController(getActivity());
        loginEntity = dataBaseController.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();
        CityList = new ArrayList<String>();
        RtoList = new ArrayList<String>();
        OrderID = 0;
        bindData();
        setAutoComplete();


        // region Filter Type

        if (getArguments() != null) {


            if (getArguments().getParcelable(Constants.SUB_PRODUCT_DATA) != null) {

                serviceEntity = getArguments().getParcelable(Constants.SUB_PRODUCT_DATA);
                PRODUCT_NAME = serviceEntity.getName();
                PARENT_PRODUCT_ID = serviceEntity.getId();
                PRODUCT_CODE = serviceEntity.getProductcode();

            }

            //endregion

            txtPrdName.setText("" + PRODUCT_NAME);
            Toast.makeText(getActivity(), "" + PRODUCT_ID + "/" + PRODUCT_CODE, Toast.LENGTH_SHORT).show();
        }


        // endregion


        textCity.setText("Select City");
        showDialog();
        new ProductController(getActivity()).getRTOProductList(PARENT_PRODUCT_ID, PRODUCT_CODE, loginEntity.getUser_id(), RenewRcFragment.this);

        return view;

    }

    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());


        textCity = (TextView) view.findViewById(R.id.textCity);
        txtModel = (TextView) view.findViewById(R.id.txtModel);
        spRTO = (Spinner) view.findViewById(R.id.spRTO);
        spCity = (Spinner) view.findViewById(R.id.spCity);

        btnBooked = (Button) view.findViewById(R.id.btnBooked);

        etPincode = (EditText) view.findViewById(R.id.etPincode);

        etCity = (EditText) view.findViewById(R.id.etCity);
        etVehicle = (EditText) view.findViewById(R.id.etVehicle);

        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        rlEditMakeModel = (RelativeLayout) view.findViewById(R.id.rlEditMakeModel);
        lyVehicle =  (LinearLayout) view.findViewById(R.id.lyVehicle);
        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);
        llDocumentUpload = (LinearLayout) view.findViewById(R.id.llDocumentUpload);
        lyRTO = (LinearLayout) view.findViewById(R.id.lyRTO);
        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);

        lyMakeModel = (LinearLayout) view.findViewById(R.id.lyMakeModel);

        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);

        acMake = (AutoCompleteTextView) view.findViewById(R.id.acMake);
        acModel = (AutoCompleteTextView) view.findViewById(R.id.acModel);

        acModel.setEnabled(false);
        acModel.setEnabled(false);

        acMake.setThreshold(2);
        acMake.setSelection(0);

        acModel.setThreshold(1);
        acModel.setSelection(0);


    }


    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);



        rlDoc.setOnClickListener(this);
        btnBooked.setOnClickListener(this);
        rlEditMakeModel.setOnClickListener(this);

        etCity.setOnClickListener(this);



    }

    private void setAutoComplete() {
        makeAdapter = new MakeAdapter(getActivity(), R.layout.activity_sign_up, R.id.lbl_name, prefManager.getMake());
        acMake.setAdapter(makeAdapter);


        // region Make Listener
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
                    txtModel.setVisibility(View.VISIBLE);

                } else {
                    acModel.setText("");
                    acModel.setEnabled(false);
                    acModel.setVisibility(View.INVISIBLE);
                    txtModel.setVisibility(View.INVISIBLE);
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

                if (s.length() > 0) {
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
        });


        //endregion

        //region  Model Listener

        acModel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                acModel.setError(null);
                IsModelValid = true;
                modelEntity = modelAdapter.getItem(position);
                acMake.setSelection(0);
                showDialog();
                new ProductController(getActivity()).RTOProductListOnChangeVehicle(PARENT_PRODUCT_ID, PRODUCT_CODE, loginEntity.getUser_id(), acMake.getText().toString(), acModel.getText().toString(), RenewRcFragment.this);


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

    private void bindData() {
        Glide.with(getActivity())
                .load(userConstatntEntity.getCompanylogo())
                .into(ivClientLogo);

        txtClientName.setText(userConstatntEntity.getCompanyname());
        etVehicle.setText(userConstatntEntity.getVehicleno());
        acMake.setText(userConstatntEntity.getMake());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                acMake.dismissDropDown();
            }
        });
        acModel.setText(userConstatntEntity.getModel());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                acModel.dismissDropDown();
            }
        });

        if (userConstatntEntity.getMake() != "") {
            IsMakeValid = true;
        }
        if (userConstatntEntity.getModel() != "") {
            IsModelValid = true;
        }
    }



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


        if ((acMake.getText().toString().trim().length() == 0)) {
            acMake.requestFocus();
            acMake.setError("Enter Make");
            return false;
        }
        if (IsMakeValid == false) {
            //getCustomToast("Please Enter Make");
            acMake.requestFocus();
            acMake.setError("Enter Make");
            return false;
        }

        if ((acModel.getText().toString().trim().length() == 0)) {
           // getCustomToast("Please Enter Model");
            acModel.requestFocus();
            acModel.setError("Enter Model");
            return false;
        }

        if (IsModelValid == false) {
//            getCustomToast("Please Enter Model");
            acModel.requestFocus();
            acModel.setError("Enter Model");
            return false;
        }
        if ((etCity.getText().toString().trim().length() == 0)) {
            //getCustomToast("Please Selct City");
            etCity.requestFocus();
            etCity.setError("Selct City");
            return false;
        }

        if (!isEmpty(etPincode) && etPincode.getText().toString().length() != 6) {
            etPincode.requestFocus();
            etPincode.setError("Enter Pincode");
            return false;
        }

        return true;
    }

    private void setMakeModelEdiatable() {
        etVehicle.setEnabled(true);
        acModel.setEnabled(true);
        acMake.setEnabled(true);
        acMake.setText("");
        acModel.setText("");
        etVehicle.setText("");
        lyMakeModel.setBackgroundColor(getResources().getColor(R.color.bg_dashboard));
        lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_dashboard));

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
        }
//        else if (response instanceof CityResponse) {
//
//
//            if (response.getStatus_code() == 0) {
//
//                //   bindAutoCity();
//            }
//        }

    }


    @Override
    public void OnFailure(Throwable t) {
        //  btnBooked.setEnabled(true);
        cancelDialog();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.rlDoc:
                showDialog();
                new ProductController(getActivity()).getProducDoc(PRODUCT_ID, RenewRcFragment.this);
                break;

            case R.id.rlEditMakeModel:

                setMakeModelEdiatable();
                break;
            case R.id.btnBooked:

                if (validate() == false) {
                    return;
                }

                //region RTO Payment

               // int rtoID = rtoMainEntity.getRto_id();
                int rtoID = 0;
                InsertOrderRequestEntity requestEntity = new InsertOrderRequestEntity();

                ExtrarequestEntity extrarequest = new ExtrarequestEntity();
                extrarequest.setMakeNo(acMake.getText().toString());
                extrarequest.setModelNo(acModel.getText().toString());
                extrarequest.setVehicleNo(etVehicle.getText().toString());

                requestEntity.setProdid("" + PRODUCT_ID);
                requestEntity.setProdName("" + PRODUCT_NAME);
                requestEntity.setUserid("" + loginEntity.getUser_id());
                requestEntity.setTransaction_id("");
                requestEntity.setSubscription("");
                requestEntity.setVehicleno("");
                requestEntity.setPucexpirydate("");
                requestEntity.setRto_id("" + rtoID);
                requestEntity.setCityid("" + Integer.valueOf(CITY_ID));
                requestEntity.setAmount("" + AMOUNT);
                requestEntity.setPayment_status("0");
                requestEntity.setExtrarequest(new Gson().toJson(extrarequest));


                //endregion

                ((ProductMainActivity) getActivity()).sendPaymentRequest(requestEntity);


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
                CITY_ID = data.getStringExtra(Constants.SEARCH_CITY_ID);
                etCity.setText(cityMainEntity.getCityname());

            }
        }

    }
}
