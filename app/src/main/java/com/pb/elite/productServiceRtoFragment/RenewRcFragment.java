package com.pb.elite.productServiceRtoFragment;


import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
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
import android.webkit.MimeTypeMap;
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
import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.DocProductEnity;
import com.pb.elite.core.model.MakeEntity;
import com.pb.elite.core.model.ModelEntity;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.RtoProductDisplayMainEntity;
import com.pb.elite.core.model.RtoProductEntity;
import com.pb.elite.core.model.UserConstatntEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.model.subcategoryEntity;
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
import com.pb.elite.rtoAdapter.CityMainAdapter;
import com.pb.elite.rtoAdapter.RtoMainAdapter;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

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
    EditText etRTO, etRTO_OTH, etVehicle, etCity;
    List<String> RtoList, CityList;  //ProductList,
    ArrayAdapter<String> RtoAdapter, CityAdapter;
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    String CITY_ID_NON_RTO = "";
    Button btnBooked;

    RTOServiceEntity serviceEntity;
    subcategoryEntity subRTOEntity;

    LinearLayout lvLogo, llDocumentUpload, lyRTO, lyTAT;
    RelativeLayout rlDoc, rlEditMakeModel;
    LinearLayout lyMakeModel;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;

    LinkedHashMap<String, Integer> mapCity;

    LinkedHashMap<String, Integer> mapLoc;
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


    List<RtoProductDisplayMainEntity> listCityMain;

    RtoProductDisplayMainEntity rtoProductDisplayMainEntity;
    RtoProductEntity rtoMainEntity;


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

                subRTOEntity = getArguments().getParcelable(Constants.SUB_PRODUCT_DATA);
                PRODUCT_NAME = subRTOEntity.getName();
                PARENT_PRODUCT_ID = subRTOEntity.getId();
                PRODUCT_CODE = subRTOEntity.getProductcode();

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

        etRTO = (EditText) view.findViewById(R.id.etRTO);
        etRTO_OTH = (EditText) view.findViewById(R.id.etRTO_OTH);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etVehicle = (EditText) view.findViewById(R.id.etVehicle);

        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        rlEditMakeModel = (RelativeLayout) view.findViewById(R.id.rlEditMakeModel);
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

        etRTO.setFocusable(false);
        etRTO.setClickable(true);

        rlDoc.setOnClickListener(this);
        btnBooked.setOnClickListener(this);
        rlEditMakeModel.setOnClickListener(this);

        etCity.setOnClickListener(this);
        etRTO.setOnClickListener(this);


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


        //region comment
//        acMake.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                String str = acMake.getText().toString();
//
//                ListAdapter listAdapter = acMake.getAdapter();
//                for (int i = 0; i < listAdapter.getCount(); i++) {
//                    String temp = listAdapter.getItem(i).toString();
//                    if (str.compareTo(temp) == 0) {
//                        acMake.setError(null);
//                        return;
//                    }
//                }
//
//                acMake.setError("Invalid Make");
//                acMake.setFocusable(true);
//            }
//        });
//        acModel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                String str = acModel.getText().toString();
//
//                ListAdapter listAdapter = acModel.getAdapter();
//                for (int i = 0; i < listAdapter.getCount(); i++) {
//                    String temp = listAdapter.getItem(i).toString();
//                    if (str.compareTo(temp) == 0) {
//                        acModel.setError(null);
//                        return;
//                    }
//                }
//
//                acModel.setError("Invalid Model");
//                acModel.setFocusable(true);
//            }
//        });

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


    //region not in used
    private void bindRTOSpinner(final List<RtoProductDisplayMainEntity> rtoProductDisplayList) {


        RtoProductDisplayMainEntity rtoEntity = new RtoProductDisplayMainEntity();

//        rtoEntity.setCity_id(0);
//        rtoEntity.setCityname("Select");
//        rtoEntity.setProd_id(0);
//        rtoEntity.setPrice("");
//        rtoEntity.setTAT("");
//        rtoEntity.setProduct_logo("");
//
//        rtoProductDisplayList.add(0, rtoEntity);

        CityAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, getRTOCityList(rtoProductDisplayList));
        spCity.setAdapter(CityAdapter);
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


//                if (position == 0) {
//
//                    clearCity();
//
//
//                } else if (rtoProductDisplayList.get(position).getCity_id() == 2653) {
//
//                    etRTO.setVisibility(View.VISIBLE);
//                    spRTO.setVisibility(View.GONE);
//                    lvLogo.setVisibility(View.VISIBLE);
//                    setRtoTAT(rtoProductDisplayList.get(position));
//
//
//                } else if (rtoProductDisplayList.get(position).getRtolist() != null) {
//
//                    if (rtoProductDisplayList.get(position).getRtolist().size() > 0) {
//
//                        spRTO.setVisibility(View.VISIBLE);
//                        RtoList = getRTOLocList(rtoProductDisplayList.get(position).getRtolist());
//                        RtoAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, RtoList);
//                        spRTO.setAdapter(RtoAdapter);
//
//                        lvLogo.setVisibility(View.VISIBLE);
//                        setRtoTAT(rtoProductDisplayList.get(position));
//
//
//                    } else {
//                        clearCity();
//                    }
//                } else {
//                    clearCity();
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private List<String> getRTOCityList(List<RtoProductDisplayMainEntity> rtoCityList) {


        mapCity = new LinkedHashMap<>();
        // mapCity.put("SELECT", 0);
        for (RtoProductDisplayMainEntity rtoObject : rtoCityList) {


            mapCity.put(rtoObject.getCityname().toUpperCase(), rtoObject.getCity_id());    // adding in Map
            //  mapCity.put("" + i + rtoObject.getCityname().toUpperCase(),  i + rtoObject.getCity_id());

            //  i= i+1;

        }
        CityList.clear();
        CityList = new ArrayList<String>(mapCity.keySet());
        return CityList;
    }

    private List<String> getRTOLocList(List<RtoProductEntity> rtoLoclist) {


        mapLoc = new LinkedHashMap<>();
        mapLoc.put("SELECT", 0);
        for (RtoProductEntity rtoObject : rtoLoclist) {

            mapLoc.put(rtoObject.getSeries_no() + "-" + rtoObject.getRto_location(), rtoObject.getRto_id());    // adding in Map

        }

        RtoList.clear();
        RtoList = new ArrayList<String>(mapLoc.keySet());

        return RtoList;
    }

    private void clearCity() {
        AMOUNT = "0";
        lvLogo.setVisibility(View.GONE);
        RtoList = new ArrayList<String>();
        RtoList.add(0, "Select");

        RtoAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, RtoList);
        spRTO.setAdapter(RtoAdapter);
    }

    //endregion

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


        if ((acMake.getText().toString().trim().length() == 0)) {
            Snackbar.make(btnBooked, "Please Enter Make", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (IsMakeValid == false) {
            Snackbar.make(btnBooked, "Please Enter Make", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if ((acModel.getText().toString().trim().length() == 0)) {
            Snackbar.make(btnBooked, "Please Enter Model", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (IsModelValid == false) {
            Snackbar.make(btnBooked, "Please Enter Model", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        int CityID = mapCity.get(spCity.getSelectedItem().toString());
        if (CityID == 0) {
            Snackbar.make(btnBooked, "Please Select City", Snackbar.LENGTH_SHORT).show();
            return false;
        }


        int RTO_ID = mapLoc.get(spRTO.getSelectedItem().toString());

        if (RTO_ID == 0) {
            Snackbar.make(btnBooked, "Please Select RTO Location", Snackbar.LENGTH_SHORT).show();
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
        lyMakeModel.setBackgroundColor(getResources().getColor(R.color.white));

    }

    public void downloadPdf(String url, String name) {
        Toast.makeText(getActivity(), "Download started..", Toast.LENGTH_LONG).show();
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name + ".pdf");
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        r.setMimeType(MimeTypeMap.getFileExtensionFromUrl(url));
        DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }

    private void reqDocPopUp(List<DocProductEnity> lstDoc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);

        RecyclerView rvProductDoc;
        ProductDocAdapter mAdapter = new ProductDocAdapter(RenewRcFragment.this, lstDoc);
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


    public void setCityData(String strCityName, RtoProductDisplayMainEntity rtoPrdEntity) {
        etCity.setText("" + strCityName);
       etRTO.setText("");
        rtoProductDisplayMainEntity = rtoPrdEntity;

        if(rtoProductDisplayMainEntity != null) {
            if (rtoProductDisplayMainEntity.getCity_id() == 2653) {

                etRTO_OTH.setVisibility(View.VISIBLE);
                etRTO.setVisibility(View.GONE);
                lvLogo.setVisibility(View.VISIBLE);
                setRtoTAT(rtoProductDisplayMainEntity);


            } else  {
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


                    bindRTOSpinner(listCityMain);


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
                int cityID = mapCity.get(spCity.getSelectedItem().toString());
                int rtoID = mapLoc.get(spRTO.getSelectedItem().toString());
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
                requestEntity.setCityid("" + cityID);
                requestEntity.setAmount("" + AMOUNT);
                requestEntity.setPayment_status("0");
                requestEntity.setExtrarequest(extrarequest);


                //endregion

                ((ProductMainActivity) getActivity()).sendPaymentRequest(requestEntity);


                break;

            case R.id.etCity:

                if (listCityMain != null) {

                    ((ProductMainActivity) getActivity()).showCityBottomSheetDialog(listCityMain);
                }

                break;

            case R.id.etRTO:

                if (!etCity.getText().toString().equalsIgnoreCase("")) {
                    ((ProductMainActivity) getActivity()).showRTOBottomSheetDialog();
                } else {
                    getCustomToast("Select City");
                }
                break;
        }


    }
}
