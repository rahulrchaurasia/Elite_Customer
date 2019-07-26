package com.rb.elite.non_rto_fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rb.elite.BaseFragment;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.misc_non_rto.MiscNonRTOController;
import com.rb.elite.core.model.CityMainEntity;
import com.rb.elite.core.model.ProductPriceEntity;
import com.rb.elite.core.model.RTOServiceEntity;
import com.rb.elite.core.model.UserConstatntEntity;
import com.rb.elite.core.model.UserEntity;
import com.rb.elite.core.requestmodel.AnalysisCurrentHealthRequestEntity;
import com.rb.elite.core.requestmodel.ProductPriceRequestEntity;
import com.rb.elite.core.response.ProductPriceResponse;
import com.rb.elite.core.response.ProvideClaimAssResponse;
import com.rb.elite.database.DataBaseController;
import com.rb.elite.product.ProductMainActivity;
import com.rb.elite.search.SearchCityActivity;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.utility.Constants;
import com.rb.elite.utility.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnalysisHealthPlanFragment extends BaseFragment implements View.OnClickListener,CompoundButton.OnCheckedChangeListener, IResponseSubcriber {

    // Service : 14

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Context mContext;
    EditText etNameOfProposer, etSumAssured, etDOB;

    Spinner spNoOfFamily;
    RadioGroup radioGroup;
    RadioButton rbIndividual, rbFloater;

    // region Common Declaration
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText etPincode, etCity;
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    Button btnBooked;


    RTOServiceEntity serviceEntity;

    ScrollView scrollView;
    LinearLayout lyVehicle, lvLogo, lyTAT ,lyFamily;
    RelativeLayout rlDoc;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;

    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;

    int OrderID = 0;

    String CITY_ID;
    ProductPriceEntity productPriceEntity;
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis_health_plan, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();

        mContext = view.getContext();
        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();

        initialize(view);

        setOnClickListener();


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


    private void initialize(View view) {

        etDOB = view.findViewById(R.id.etDOB);

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        btnBooked = (Button) view.findViewById(R.id.btnBooked);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etPincode = (EditText) view.findViewById(R.id.etPincode);


        etNameOfProposer = (EditText) view.findViewById(R.id.etNameOfProposer);
        etSumAssured = (EditText) view.findViewById(R.id.etSumAssured);



        radioGroup = view.findViewById(R.id.radioGroup);
        rbFloater = view.findViewById(R.id.rbFloater);


        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);

        lyVehicle = (LinearLayout) view.findViewById(R.id.lyVehicle);
        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);


        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);


        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);

        etDOB = view.findViewById(R.id.etDOB);

        // region Floater
        radioGroup   = view.findViewById(R.id.radioGroup);
        rbIndividual = view.findViewById(R.id.rbIndividual);
        rbFloater = view.findViewById(R.id.rbFloater);

        lyFamily = view.findViewById(R.id.lyFamily);
        spNoOfFamily = view.findViewById(R.id.spNoOfFamily);

        //endregion


    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);
        etCity.setOnClickListener(this);

        rlDoc.setOnClickListener(this);
        btnBooked.setOnClickListener(this);

        etDOB.setOnClickListener(this);

        rbFloater.setOnCheckedChangeListener(this);
        rbIndividual.setOnCheckedChangeListener(this);



    }

    private void bindData() {
        Glide.with(getActivity())
                .load(userConstatntEntity.getCompanylogo())
                .into(ivClientLogo);

        txtClientName.setText(userConstatntEntity.getCompanyname());


    }



    private boolean validate() {
        if (!validateProposer(etNameOfProposer)) {
            return false;

        } else if (!validateProposer(etSumAssured)) {
            return false;
        } else if (!isEmpty(etDOB)) {
            etDOB.setError("Enter  DOB");
            return false;
        } else if (spNoOfFamily.getSelectedItemPosition() == 0) {
            getCustomToast("Please Select No. Of Family Member");
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

    private void getTatData() {
        if (productPriceEntity != null) {
            lvLogo.setVisibility(View.VISIBLE);
            txtCharges.setText(productPriceEntity.getPrice());
            txtTAT.setText(productPriceEntity.getTAT());

        } else {
            lvLogo.setVisibility(View.GONE);
        }
    }
    private void setScrollatBottom() {
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 1000);
    }


    private void saveData() {

        showDialog();
        AnalysisCurrentHealthRequestEntity requestEntity = new AnalysisCurrentHealthRequestEntity();
        requestEntity.setAmount(txtCharges.getText().toString());
        requestEntity.setCityid(String.valueOf(CITY_ID));
        requestEntity.setPayment_status("1");
        requestEntity.setProdid(String.valueOf(PRODUCT_ID));
        requestEntity.setRto_id(productPriceEntity.getRto_id());
        requestEntity.setTransaction_id("");
        requestEntity.setUserid(String.valueOf(loginEntity.getUser_id()));

        requestEntity.setPincode(etPincode.getText().toString());
        requestEntity.setDOB(etDOB.getText().toString());
        requestEntity.setSume_assured(etSumAssured.getText().toString());
        requestEntity.setName_of_praposal(etNameOfProposer.getText().toString());

        if (rbIndividual.isChecked()) {
            requestEntity.setCovered_for(rbIndividual.getText().toString());//hl

        } else if (rbFloater.isChecked()) {
            requestEntity.setCovered_for(rbFloater.getText().toString());
        }

        requestEntity.setNo_family_member(spNoOfFamily.getSelectedItem().toString());

        new MiscNonRTOController(mContext).saveAnalysisCurrentHealth(requestEntity, this);
    }

    private void init(View view) {
        etDOB = view.findViewById(R.id.etDOB);
    }

    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view,mContext);
        switch (view.getId()) {

            case R.id.etDOB:

                DateTimePicker.showPrevPickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
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
                setScrollatBottom();
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

                showMiscPaymentAlert(btnBooked, response.getMessage().toString(), ((ProvideClaimAssResponse) response).getData().get(0));
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.rbFloater) {

            if (isChecked) {
                lyFamily.setVisibility(View.VISIBLE);

            }
        }else  if (buttonView.getId() == R.id.rbIndividual) {

            if (isChecked) {
                lyFamily.setVisibility(View.GONE);

            }
        }
    }
}
