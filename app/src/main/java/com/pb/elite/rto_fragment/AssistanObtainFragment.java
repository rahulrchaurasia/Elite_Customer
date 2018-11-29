package com.pb.elite.rto_fragment;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.CorrectiontEnity;
import com.pb.elite.core.model.DocProductEnity;
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
import com.pb.elite.rto_fragment.adapter.CityMainAdapter;
import com.pb.elite.rto_fragment.adapter.IRTOCity;
import com.pb.elite.rto_fragment.adapter.RtoMainAdapter;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssistanObtainFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber, IRTOCity {


    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText etRTO, etRTO_OTH, etCity, etLic;
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    Button btnBooked;

    RTOServiceEntity serviceEntity;

    LinearLayout lvLogo, llDocumentUpload, lyRTO, lyTAT;
    RelativeLayout rlDoc, rlCorrect;
    LinearLayout lyLic;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;

    TextView textLicMandatory;


    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;
    int PARENT_PRODUCT_ID = 0;

    String AMOUNT = "0";
    int OrderID = 0;

    List<CorrectiontEnity> correctiontEnityList;

    // region Declaration

    BottomSheetDialog mBottomSheetDialog;
    List<RtoProductDisplayMainEntity> listCityMain;
    List<RtoProductEntity> rtoProductDisplayList;


    RtoProductDisplayMainEntity rtoProductDisplayMainEntity;
    RtoProductEntity rtoMainEntity;


    CityMainAdapter cityMainAdapter;
    RtoMainAdapter rtoMainAdapter;

    //endregion


    public AssistanObtainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_assistan_obtain, container, false);
        initialize(view);


        correctiontEnityList = new ArrayList<CorrectiontEnity>();
        getCorrectionField();
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
                    rlCorrect.setVisibility(View.GONE);

                } else if (PRODUCT_CODE.equalsIgnoreCase("2.2") || PRODUCT_CODE.equalsIgnoreCase("2.3")) {
                    lyLic.setVisibility(View.VISIBLE);
                    rlCorrect.setVisibility(View.GONE);

                } else if (PRODUCT_CODE.equalsIgnoreCase("2.4")) {
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
    public void getBottomSheetDialog(String type) {

        mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.bottomSheetDialog);

        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);

        mBottomSheetDialog.setContentView(sheetView);
        TextView txtHdr = mBottomSheetDialog.findViewById(R.id.txtHdr);
        RecyclerView rvCity = (RecyclerView) mBottomSheetDialog.findViewById(R.id.rvCity);
        RecyclerView rvRTO = (RecyclerView) mBottomSheetDialog.findViewById(R.id.rvRTO);
        ImageView ivCross = (ImageView) mBottomSheetDialog.findViewById(R.id.ivCross);


        rvCity.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCity.setHasFixedSize(true);

        rvRTO.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRTO.setHasFixedSize(true);

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBottomSheetDialog.isShowing()) {

                    mBottomSheetDialog.dismiss();
                }
            }
        });

        if (type.equalsIgnoreCase("CITY")) {
            txtHdr.setText("Select City");

            cityMainAdapter = new CityMainAdapter(AssistanObtainFragment.this, listCityMain, this);
            rvCity.setAdapter(cityMainAdapter);


            rvCity.setVisibility(View.VISIBLE);
            rvRTO.setVisibility(View.GONE);

        } else {

            txtHdr.setText("Select RTO");

            rtoProductDisplayList = rtoProductDisplayMainEntity.getRtolist();
            rtoMainAdapter = new RtoMainAdapter(AssistanObtainFragment.this, rtoProductDisplayList,this);
            rvRTO.setAdapter(rtoMainAdapter);
            rvCity.setVisibility(View.GONE);
            rvRTO.setVisibility(View.VISIBLE);

        }


        mBottomSheetDialog.show();


    }

/*    public void getCityBottomSheet(RtoProductDisplayMainEntity cityEntity) {

        if (mBottomSheetDialog != null) {

            if (mBottomSheetDialog.isShowing()) {

                rtoProductDisplayMainEntity = cityEntity;

                setCityData(rtoProductDisplayMainEntity.getCityname(), rtoProductDisplayMainEntity);

                mBottomSheetDialog.dismiss();

            }
        }
    }*/

   /* public void getRTOBottomSheet(RtoProductEntity rtoEntity) {

        if (mBottomSheetDialog != null) {

            if (mBottomSheetDialog.isShowing()) {

                rtoMainEntity = rtoEntity;

                setRTOData("" + rtoMainEntity.getSeries_no() + "-" + rtoMainEntity.getRto_location(), rtoMainEntity);
                mBottomSheetDialog.dismiss();

            }
        }


    }*/

    //endregion


    public void setCityData(String strCityName, RtoProductDisplayMainEntity rtoPrdEntity) {
        etCity.setText("" + strCityName);
        etRTO.setText("");
        rtoProductDisplayMainEntity = rtoPrdEntity;

        if (rtoProductDisplayMainEntity != null) {
            if (rtoProductDisplayMainEntity.getCity_id() == 2653) {

                etRTO_OTH.setVisibility(View.VISIBLE);
                etRTO.setVisibility(View.GONE);
                lvLogo.setVisibility(View.VISIBLE);
                setRtoTAT(rtoProductDisplayMainEntity);


            } else {
                etRTO.setVisibility(View.VISIBLE);
                etRTO_OTH.setVisibility(View.GONE);
                lvLogo.setVisibility(View.VISIBLE);
                setRtoTAT(rtoProductDisplayMainEntity);


            }
        }
    }

    public void setRTOData(String strRTOName, RtoProductEntity rtoEntity) {
        etRTO.setText("" + strRTOName);

        rtoMainEntity = rtoEntity;

    }


    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());


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

        textLicMandatory = (TextView) view.findViewById(R.id.textLicMandatory);


        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        rlCorrect = (RelativeLayout) view.findViewById(R.id.rlCorrect);

        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);
        llDocumentUpload = (LinearLayout) view.findViewById(R.id.llDocumentUpload);
        lyRTO = (LinearLayout) view.findViewById(R.id.lyRTO);
        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);

        lyLic = (LinearLayout) view.findViewById(R.id.lyLic);

        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);


    }


    private void getCorrectionField() {
        correctiontEnityList.clear();
        correctiontEnityList.add(new CorrectiontEnity("0", "Name", false));
        correctiontEnityList.add(new CorrectiontEnity("1", "DOB", false));
        correctiontEnityList.add(new CorrectiontEnity("2", "Driving License", false));
        correctiontEnityList.add(new CorrectiontEnity("2", "Address", false));

    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);

        etRTO.setFocusable(false);
        etRTO.setClickable(true);

        rlDoc.setOnClickListener(this);
        rlCorrect.setOnClickListener(this);
        btnBooked.setOnClickListener(this);


        etCity.setOnClickListener(this);
        etRTO.setOnClickListener(this);


    }

    private void bindData() {
        Glide.with(getActivity())
                .load(userConstatntEntity.getCompanylogo())
                .into(ivClientLogo);

        txtClientName.setText(userConstatntEntity.getCompanyname());

    }

    public List<RtoProductDisplayMainEntity> removeDuplicateCity(List<RtoProductDisplayMainEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {

                if ((list.get(i).getCity_id() == (list.get(j).getCity_id()))) {
                    list.remove(j);
                    j--;
                }
            }
        }
        return list;
    }


    private void setRtoTAT(RtoProductDisplayMainEntity rtoProd) {
        if (rtoProd.getPrice() != null) {
            txtCharges.setText("" + "\u20B9" + " " + rtoProd.getPrice());
            AMOUNT = rtoProd.getPrice().trim();
        }

        if (rtoProd.getTAT() != null) {
            lyTAT.setVisibility(View.VISIBLE);
            txtTAT.setText("" + rtoProd.getTAT());
        } else {
            lyTAT.setVisibility(View.GONE);
        }

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
            getCustomToast("Please Enter D");
            return false;
        }

        return true;
    }


    private void reqDocPopUp(List<DocProductEnity> lstDoc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);

        RecyclerView rvProductDoc;
        ProductDocAdapter mAdapter = new ProductDocAdapter(getActivity(), lstDoc);
        Button btnClose;
        ImageView ivClose;

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_doc_prod, null);


        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        btnClose = (Button) dialogView.findViewById(R.id.btnClose);
        ivClose = (ImageView) dialogView.findViewById(R.id.ivClose);
        rvProductDoc = (RecyclerView) dialogView.findViewById(R.id.rvProductDoc);
        rvProductDoc.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvProductDoc.setLayoutManager(layoutManager);
        rvProductDoc.setAdapter(mAdapter);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.setCancelable(false);

        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);


    }


    private void reqCorrectPopUp(List<CorrectiontEnity> correctiontEnityList) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);

        RecyclerView rvProductDoc;
        CorrectionAdapter mAdapter = new CorrectionAdapter(getActivity(), correctiontEnityList);
        Button btnSubmit;
        ImageView ivClose;

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_correct_doc, null);


        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        // set the custom dialog components - text, image and button

        TextView txtHdr = (TextView) dialogView.findViewById(R.id.txtHdr);

        btnSubmit = (Button) dialogView.findViewById(R.id.btnSubmit);
        ivClose = (ImageView) dialogView.findViewById(R.id.ivClose);

        txtHdr.setText("Correction List");
        btnSubmit.setVisibility(View.VISIBLE);
        rvProductDoc = (RecyclerView) dialogView.findViewById(R.id.rvProductDoc);
        rvProductDoc.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvProductDoc.setLayoutManager(layoutManager);
        rvProductDoc.setAdapter(mAdapter);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.setCancelable(false);

        alertDialog.show();


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
                    listCityMain = removeDuplicateCity(((RtoProductDisplayResponse) response).getData());


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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.rlDoc:
                showDialog();
                new ProductController(getActivity()).getProducDoc(PRODUCT_ID, AssistanObtainFragment.this);
                break;


            case R.id.rlCorrect:
                reqCorrectPopUp(correctiontEnityList);
                break;

            case R.id.btnBooked:

                if (validate() == false) {
                    return;
                }

                //region RTO Payment
                int cityID = rtoProductDisplayMainEntity.getCity_id();
                int rtoID = rtoMainEntity.getRto_id();
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
                requestEntity.setCityid("" + cityID);
                requestEntity.setAmount("" + AMOUNT);
                requestEntity.setPayment_status("0");
                requestEntity.setExtrarequest(extrarequest);


                //endregion

                ((ProductMainActivity) getActivity()).sendPaymentRequest(requestEntity);

                break;
            case R.id.etCity:

                if (listCityMain != null) {

                    getBottomSheetDialog("CITY");
                }

                break;

            case R.id.etRTO:

                if (!etCity.getText().toString().equalsIgnoreCase("")) {

                    getBottomSheetDialog("RTO");
                } else {
                    getCustomToast("Select City");
                }
                break;
        }


    }

    @Override
    public void getRTOCity(RtoProductDisplayMainEntity e, RtoProductEntity entity) {

        if (mBottomSheetDialog != null) {

            if (mBottomSheetDialog.isShowing()) {

                if (e != null) {
                    rtoProductDisplayMainEntity = e;

                    setCityData(rtoProductDisplayMainEntity.getCityname(), rtoProductDisplayMainEntity);

                    mBottomSheetDialog.dismiss();

                } else if (entity != null) {

                    rtoMainEntity = entity;

                    setRTOData("" + rtoMainEntity.getSeries_no() + "-" + rtoMainEntity.getRto_location(), rtoMainEntity);
                    mBottomSheetDialog.dismiss();
                }

            }

        }
    }
}
