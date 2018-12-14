package com.pb.elite.rto_fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.CityMainEntity;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.UserConstatntEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.response.ProductDocumentResponse;
import com.pb.elite.core.response.RtoProductDisplayResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.search.SearchCityActivity;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;
import com.pb.elite.utility.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmartCardLicFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {

    // Service : 7

    // region Common Declaration

    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText etCity  ;
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    Button btnBooked;

    RTOServiceEntity serviceEntity;

    ScrollView scrollView;
    LinearLayout lvLogo, lyTAT;
    RelativeLayout rlDoc ;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;

    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;
    int PARENT_PRODUCT_ID = 0;

    String AMOUNT = "0";
    int OrderID = 0;

    String CITY_ID;

    //endregion

    EditText etRTO ,etPincode ,etLicNo ,etLicOwnerName ,etLicOwnerDob;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");


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
                            etLicOwnerDob.setText(currentDay);
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

        View view = inflater.inflate(R.layout.fragment_smart_card_lic, container, false);
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
                PARENT_PRODUCT_ID = serviceEntity.getId();
                PRODUCT_CODE = serviceEntity.getProductcode();


            }

            //endregion

            txtPrdName.setText("" + PRODUCT_NAME);
            Toast.makeText(getActivity(), "" + PRODUCT_ID + "/" + PRODUCT_CODE, Toast.LENGTH_SHORT).show();
        }


        // endregion


        showDialog();
        new ProductController(getActivity()).getRTOProductList(PARENT_PRODUCT_ID, PRODUCT_CODE, loginEntity.getUser_id(), SmartCardLicFragment.this);


        return view;
    }

    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        btnBooked = (Button) view.findViewById(R.id.btnBooked);
        etCity = (EditText) view.findViewById(R.id.etCity);


        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);

        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);


        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);


        etRTO = (EditText) view.findViewById(R.id.etRTO);
        etPincode = (EditText) view.findViewById(R.id.etPincode);
        etLicNo = (EditText) view.findViewById(R.id.etLicNo);
        etLicOwnerName = (EditText) view.findViewById(R.id.etLicOwnerName);
        etLicOwnerDob = (EditText) view.findViewById(R.id.etLicOwnerDob);


    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);

        rlDoc.setOnClickListener(this);
        btnBooked.setOnClickListener(this);

        etCity.setOnClickListener(this);



    }

    private void bindData() {
        Glide.with(getActivity())
                .load(userConstatntEntity.getCompanylogo())
                .into(ivClientLogo);

        txtClientName.setText(userConstatntEntity.getCompanyname());


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.rlDoc:
                showDialog();
                new ProductController(getActivity()).getProducDoc(PRODUCT_ID, this);
                break;

            case R.id.btnBooked:


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


    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
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
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
    }
}
