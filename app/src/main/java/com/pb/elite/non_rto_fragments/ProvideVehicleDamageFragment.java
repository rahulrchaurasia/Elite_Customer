package com.pb.elite.non_rto_fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.misc_non_rto.MiscNonRTOController;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.CityMainEntity;
import com.pb.elite.core.model.ProductPriceEntity;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.UserConstatntEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.requestmodel.ProductPriceRequestEntity;
import com.pb.elite.core.requestmodel.ProvideClaimAssRequestEntity;
import com.pb.elite.core.response.OrderResponse;
import com.pb.elite.core.response.ProductDocumentResponse;
import com.pb.elite.core.response.ProductPriceResponse;
import com.pb.elite.core.response.ProvideClaimAssResponse;
import com.pb.elite.core.response.RtoProductDisplayResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.rto_fragment.TransferOwnershipFragment;
import com.pb.elite.search.SearchCityActivity;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;
import com.pb.elite.utility.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProvideVehicleDamageFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {

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
    int PARENT_PRODUCT_ID = 0;

    int OrderID = 0;

    String CITY_ID = "";
    ProductPriceEntity productPriceEntity;

    //endregion


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_provide_vehicle_damage, container, false);


        return view;
    }

    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        btnBooked = (Button) view.findViewById(R.id.btnBooked);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etPincode = (EditText) view.findViewById(R.id.etPincode);
        etVehicle = (EditText) view.findViewById(R.id.etVehicle);


        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        rlEditVehicle = (RelativeLayout) view.findViewById(R.id.rlEditVehicle);

        lyVehicle = (LinearLayout) view.findViewById(R.id.lyVehicle);
        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);


        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);


        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);

        etDate = view.findViewById(R.id.etDate);
        etTime = view.findViewById(R.id.etTime);
        etPlaceOfAccident = view.findViewById(R.id.etPlaceOfAccident);
        etInsCompanyName = view.findViewById(R.id.etInsCompanyName);


    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);

        rlDoc.setOnClickListener(this);
        rlEditVehicle.setOnClickListener(this);
        btnBooked.setOnClickListener(this);

        etCity.setOnClickListener(this);

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
        lyVehicle.setBackgroundColor(getResources().getColor(R.color.white));

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
                PARENT_PRODUCT_ID = serviceEntity.getId();
                PRODUCT_CODE = serviceEntity.getProductcode();


            }

            //endregion

            txtPrdName.setText("" + PRODUCT_NAME);
            Toast.makeText(getActivity(), "" + PRODUCT_ID + "/" + PRODUCT_CODE, Toast.LENGTH_SHORT).show();


        }


        // endregion


        showDialog();
        new ProductController(getActivity()).getRTOProductList(PARENT_PRODUCT_ID, PRODUCT_CODE, loginEntity.getUser_id(), this);



        super.onViewCreated(view, savedInstanceState);

    }

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

    private void saveData()
    {

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
        requestEntity.setInsure_company_name(etInsCompanyName.getText().toString());

        new MiscNonRTOController(mContext).saveProvideClaimAssistance(requestEntity,this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.etDate:

                DateTimePicker.showDatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
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

            case R.id.rlDoc:
                showDialog();
                new ProductController(getActivity()).getProducDoc(PRODUCT_ID, this);
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
                CITY_ID =  String.valueOf(cityMainEntity.getCity_id());
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

                    getCustomToast("No Data Available");
                }
            }
        } else if (response instanceof ProductPriceResponse) {
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
        //
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
