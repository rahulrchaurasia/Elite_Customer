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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.pb.elite.core.model.ProductPriceEntity;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.RtoCityMain;
import com.pb.elite.core.model.UserConstatntEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.requestmodel.ProductPriceRequestEntity;
import com.pb.elite.core.requestmodel.SpecialBenefitsRequestEntity;
import com.pb.elite.core.requestmodel.TransferBenefitsNCBRequestEntity;
import com.pb.elite.core.response.ProductPriceResponse;
import com.pb.elite.core.response.ProvideClaimAssResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.product.ProductMainActivity;
import com.pb.elite.register.MakeAdapter;
import com.pb.elite.register.ModelAdapter;
import com.pb.elite.register.SignUpActivity;
import com.pb.elite.rto_fragment.AssistanObtainFragment;
import com.pb.elite.rto_fragment.adapter.IRTOCity;
import com.pb.elite.rto_fragment.adapter.RtoMainAdapter;
import com.pb.elite.search.SearchCityActivity;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;
import com.pb.elite.utility.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferNCBFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber, IRTOCity {

    // Service 13

    private Context mContext;
    AutoCompleteTextView acMake ;
    // region Common Declaration
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText etVehicle,  etMake, etInsCompanyName, etPincode, etCity,  etRTO;
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    Button btnBooked;


    RTOServiceEntity serviceEntity;

    ScrollView scrollView;
    LinearLayout lyVehicle, lvLogo, lyTAT ;
    RelativeLayout rlDoc ,rlEditVehicle;;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;

    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;


    String AMOUNT = "0";
    int OrderID = 0;

    String CITY_ID;
    ProductPriceEntity productPriceEntity;
    boolean IsMakeValid = false;
    MakeAdapter makeAdapter;

    // region Botom sheetDeclaration

    BottomSheetDialog mBottomSheetDialog;


    CityMainEntity cityMainEntity;
    RtoCityMain rtoMainEntity;

    RtoMainAdapter rtoMainAdapter;

    //endregion

    //endregion


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer_ncb, container, false);
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
        rtoMainAdapter = new RtoMainAdapter(TransferNCBFragment.this, cityMainEntity.getRTOList(), this);
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

    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());

        scrollView =  view.findViewById(R.id.scrollView);
        btnBooked =  view.findViewById(R.id.btnBooked);
        etCity =  view.findViewById(R.id.etCity);
        etPincode =  view.findViewById(R.id.etPincode);
        etVehicle =  view.findViewById(R.id.etVehicle);
        etRTO   =  view.findViewById(R.id.etRTO);
        etMake   =  view.findViewById(R.id.etMake);
        etInsCompanyName = view.findViewById(R.id.etInsCompanyName);

        txtCharges =  view.findViewById(R.id.txtCharges);
        txtPrdName =  view.findViewById(R.id.txtPrdName);
        txtDoc =  view.findViewById(R.id.txtDoc);
        txtClientName =  view.findViewById(R.id.txtClientName);
        txtTAT =  view.findViewById(R.id.txtTAT);

        rlDoc = view.findViewById(R.id.rlDoc);
        rlEditVehicle =  view.findViewById(R.id.rlEditVehicle);

        lyVehicle =  view.findViewById(R.id.lyVehicle);
        lvLogo =  view.findViewById(R.id.lvLogo);


        lyTAT = view.findViewById(R.id.lyTAT);


        ivLogo =  view.findViewById(R.id.ivLogo);
        ivClientLogo = view.findViewById(R.id.ivClientLogo);

        acMake = view.findViewById(R.id.acMake);



    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);
        etCity.setOnClickListener(this);

        etRTO.setOnClickListener(this);

        rlDoc.setOnClickListener(this);
        rlEditVehicle.setOnClickListener(this);
        btnBooked.setOnClickListener(this);




    }

    private void setMakeListner()
    {
        acMake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                acMake.setError(null);
                acMake.setSelection(0);
                IsMakeValid = true;

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
                if( acMake.getAdapter()!= null ) {
                    for (int i = 0; i < listAdapter.getCount(); i++) {
                        String temp = listAdapter.getItem(i).toString().toUpperCase();
                        if (str.compareTo(temp) == 0) {
                            acMake.setError(null);

                            IsMakeValid = true;


                            return;
                        }
                    }
                }

                acMake.setError("Invalid Make");
                acMake.setFocusable(true);
                IsMakeValid = false;


            }
        });


    }

    private void bindData() {
        Glide.with(getActivity())
                .load(userConstatntEntity.getCompanylogo())
                .into(ivClientLogo);

        txtClientName.setText(userConstatntEntity.getCompanyname());
        etVehicle.setText(userConstatntEntity.getVehicleno());
        acMake.setText(""+userConstatntEntity.getMake());
        IsMakeValid = true;
    }

    private boolean validate() {

        if (!validateVehicle(etVehicle)) {

            return false;
        }else if (!validateMake(acMake,IsMakeValid)) {

            return false;
        }
        else if (!validateInsCompName(etInsCompanyName)) {

            return false;
        }
        else if (!validateCity(etCity)) {

            return false;
        }
        else if (!validatePinCode(etPincode)) {

            return false;
        }
        return true;
    }

    private void setVehicleEdiatable() {
        etVehicle.setEnabled(true);

        etVehicle.setText("");
        lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_content));

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

        TransferBenefitsNCBRequestEntity requestEntity  = new TransferBenefitsNCBRequestEntity();

        requestEntity.setAmount(txtCharges.getText().toString());
        requestEntity.setCityid(String.valueOf(CITY_ID));
        requestEntity.setPayment_status("1");
        requestEntity.setProdid(String.valueOf(PRODUCT_ID));

        requestEntity.setRto_id(productPriceEntity.getRto_id());
        requestEntity.setTransaction_id("");
        requestEntity.setUserid(String.valueOf(loginEntity.getUser_id()));
        requestEntity.setVehicleno(String.valueOf(etVehicle.getText()));


        requestEntity.setPincode(etPincode.getText().toString());

        requestEntity.setMake(etMake.getText().toString());
        requestEntity.setInsure_company_name(etInsCompanyName.getText().toString());



        new MiscNonRTOController(mContext).saveTransferNCBBenefits(requestEntity, this);
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
        acMake.setThreshold(2);
        acMake.setSelection(0);

        bindData();

        makeAdapter = new MakeAdapter(getActivity(), R.layout.activity_sign_up, R.id.lbl_name, prefManager.getMake());
        acMake.setAdapter(makeAdapter);
        setMakeListner();

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


        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.rlEditVehicle:

                setVehicleEdiatable();
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

            case R.id.etRTO:
                // setScrollatBottom();
                if (!etCity.getText().toString().equalsIgnoreCase("")) {

                    getBottomSheetDialog();
                } else {

                    etCity.requestFocus();
                    etCity.setError("Enter City");
                }
                break;



        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SEARCH_CITY_CODE) {
            if (data != null) {

                 cityMainEntity = data.getParcelableExtra(Constants.SEARCH_CITY_DATA);
                CITY_ID = String.valueOf(cityMainEntity.getCity_id());
                etCity.setText(cityMainEntity.getCityname());
                etCity.setError(null);


                showDialog();

                //region call Price Controller
                ProductPriceRequestEntity entity = new ProductPriceRequestEntity();
                //   entity.setVehicleno(etVehicle.getText().toString());
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
            if (response.getStatus_code() == 0) {

                OrderID = (((ProvideClaimAssResponse) response).getData().get(0).getId());
                String DisplayMessage = (((ProvideClaimAssResponse) response).getData().get(0).getDisplaymessage());
                showMiscPaymentAlert(btnBooked, response.getMessage().toString(), DisplayMessage, OrderID);

            }
        }else if (response instanceof ProvideClaimAssResponse) {
            if (response.getStatus_code() == 0) {

                OrderID = (((ProvideClaimAssResponse) response).getData().get(0).getId());
                String DisplayMessage = (((ProvideClaimAssResponse) response).getData().get(0).getDisplaymessage());
                showMiscPaymentAlert(btnBooked, response.getMessage().toString(), DisplayMessage, OrderID);

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
}
