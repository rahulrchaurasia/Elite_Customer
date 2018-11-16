package com.pb.elite.product;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.AllCityEntity;
import com.pb.elite.core.model.DocProductEnity;
import com.pb.elite.core.model.NONRTOServiceEntity;


import com.pb.elite.core.model.NonRtoProductDisplayMainEntity;
import com.pb.elite.core.model.NonRtoSpeciallistEntity;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.RtoProductDisplayMainEntity;
import com.pb.elite.core.model.RtoProductEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.model.subcategoryEntity;
import com.pb.elite.core.requestmodel.InsertOrderRequestEntity;
import com.pb.elite.core.response.CityResponse;
import com.pb.elite.core.response.NonRtoProductDisplayResponse;
import com.pb.elite.core.response.OrderResponse;
import com.pb.elite.core.response.ProductDocumentResponse;
import com.pb.elite.core.response.RtoProductDisplayResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.document.DocUploadActivity;
import com.pb.elite.payment.PaymentRazorActivity;
import com.pb.elite.search.SearchCityActivity;
import com.pb.elite.utility.Constants;
import com.pb.elite.utility.DateTimePicker;
import com.pb.elite.utility.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

public class ProductActivity extends BaseActivity implements View.OnClickListener, BaseActivity.PopUpListener, IResponseSubcriber {


    EditText acCity;
    TextView textCity;
    Spinner spRTO, spCity;
    EditText etRTO, etVehicleNo, etVehicleExp;
    List<String> RtoList, CityList;  //ProductList,
    ArrayAdapter<String> RtoAdapter, CityAdapter;
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    String CITY_ID_NON_RTO = "";
    Button btnBooked;

    // ProductEntity productEntity;

    RTOServiceEntity serviceEntity;
    NONRTOServiceEntity nonrtoServiceEntity;
    subcategoryEntity subRTOEntity;

    List<NonRtoSpeciallistEntity> nonRtoSpeciList;


    int CatType = 1;
    int RTO_ID, CityID = 0;


    LinearLayout lvLogo, llDocumentUpload, lyRTO, lyTAT, lySpecial, lyPuc;
    RelativeLayout rlDoc, rlfileUpload;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;

    LinkedHashMap<String, Integer> mapCity;

    LinkedHashMap<String, Integer> mapLoc;


    String PRODUCT_NAME = "";
    int PRODUCT_ID = 0;

    String SERVICE_TYPE;
    String AMOUNT = "0";

    RecyclerView rvSpecial;
    NonRtoProductDisplayMainEntity nonRTOProd;

    SpecialProductAdapter mSpecialAdapter;
    int OrderID = 0;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerPopUp(this);
        initialize();


        // region Filter Type

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (getIntent().hasExtra(Constants.SERVICE_TYPE)) {

                SERVICE_TYPE = extras.getString(Constants.SERVICE_TYPE, "");
            }
            if (getIntent().hasExtra(Constants.RTO_PRODUCT_DATA)) {

                serviceEntity = extras.getParcelable(Constants.RTO_PRODUCT_DATA);
                PRODUCT_NAME = serviceEntity.getName();
                PRODUCT_ID = serviceEntity.getId();

            } else if (getIntent().hasExtra(Constants.NON_RTO_PRODUCT_DATA)) {

                nonrtoServiceEntity = extras.getParcelable(Constants.NON_RTO_PRODUCT_DATA);
                PRODUCT_NAME = nonrtoServiceEntity.getName();
                PRODUCT_ID = nonrtoServiceEntity.getId();

            } else if (getIntent().hasExtra(Constants.SUB_PRODUCT_DATA)) {

                subRTOEntity = extras.getParcelable(Constants.SUB_PRODUCT_DATA);
                PRODUCT_NAME = subRTOEntity.getName();
                PRODUCT_ID = subRTOEntity.getId();

            }

            //endregion

            if (PRODUCT_ID == 131) {
                lyPuc.setVisibility(View.VISIBLE);
                etVehicleExp.setOnClickListener(this);
            } else {
                lyPuc.setVisibility(View.GONE);
            }

            txtPrdName.setText("" + PRODUCT_NAME);
            Toast.makeText(this, "" + PRODUCT_ID, Toast.LENGTH_SHORT).show();


        }

        // endregion

        dataBaseController = new DataBaseController(ProductActivity.this);
        loginEntity = dataBaseController.getUserData();
        CityList = new ArrayList<String>();
        RtoList = new ArrayList<String>();
        OrderID = 0;
        bindClient();

        if (SERVICE_TYPE.equals("RTO")) {
            textCity.setText("Select City");
            showDialog();
            new ProductController(this).getRTOProductList(PRODUCT_ID, ProductActivity.this);
        } else {

            acCity.setVisibility(View.VISIBLE);

            spCity.setVisibility(View.GONE);
            spRTO.setVisibility(View.GONE);
            lyRTO.setVisibility(View.GONE);
            textCity.setText("Enter City");


            showDialog();
            new ProductController(this).getNonRTOProductList(PRODUCT_ID, ProductActivity.this);
        }
    }


//    View.OnFocusChangeListener acCityFocusChange = new View.OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View view, boolean b) {
//            if (!b) {
//
//                String str = acCity.getText().toString();
//
//                ListAdapter listAdapter = acCity.getAdapter();
//                for (int i = 0; i < listAdapter.getCount(); i++) {
//                    String temp = listAdapter.getItem(i).toString();
//                    if (str.compareTo(temp) == 0) {
//                        return;
//                    }
//                }
//
//                acCity.setText("");
//                acCity.setError("Invalid city");
//                acCity.setFocusable(true);
//            }
//        }
//    };

    // region Method
    private void initialize() {


        textCity = (TextView) findViewById(R.id.textCity);
        acCity = (EditText) findViewById(R.id.acCity);
        spRTO = (Spinner) findViewById(R.id.spRTO);
        spCity = (Spinner) findViewById(R.id.spCity);

        btnBooked = (Button) findViewById(R.id.btnBooked);

        etRTO = (EditText) findViewById(R.id.etRTO);

        etVehicleNo = (EditText) findViewById(R.id.etVehicleNo);
        etVehicleExp = (EditText) findViewById(R.id.etVehicleExp);
        txtCharges = (TextView) findViewById(R.id.txtCharges);
        txtPrdName = (TextView) findViewById(R.id.txtPrdName);
        txtDoc = (TextView) findViewById(R.id.txtDoc);
        txtClientName = (TextView) findViewById(R.id.txtClientName);
        txtTAT = (TextView) findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) findViewById(R.id.rlDoc);
        rlfileUpload = (RelativeLayout) findViewById(R.id.rlfileUpload);
        lvLogo = (LinearLayout) findViewById(R.id.lvLogo);
        llDocumentUpload = (LinearLayout) findViewById(R.id.llDocumentUpload);
        lyRTO = (LinearLayout) findViewById(R.id.lyRTO);
        lyTAT = (LinearLayout) findViewById(R.id.lyTAT);
        lySpecial = (LinearLayout) findViewById(R.id.lySpecial);
        lyPuc = (LinearLayout) findViewById(R.id.lyPuc);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) findViewById(R.id.ivClientLogo);


        btnBooked.setOnClickListener(this);
        acCity.setOnClickListener(this);
        rlDoc.setOnClickListener(this);
        rlfileUpload.setOnClickListener(this);


        initNonRTOSpecial();

    }

    private void initNonRTOSpecial() {
        rvSpecial = (RecyclerView) findViewById(R.id.rvSpecial);
        rvSpecial.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductActivity.this);
        rvSpecial.setLayoutManager(layoutManager);

    }

//    private boolean valNonRTOCity() {
//        String str = acCity.getText().toString();
//
//        ListAdapter listAdapter = acCity.getAdapter();
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            String temp = listAdapter.getItem(i).toString();
//            if (str.compareTo(temp) == 0) {
//                return true;
//            }
//        }
//
//        return false;
//    }


//    private void bindAutoCity() {
//        //region make AutoComplete City
//        CityList = dataBaseController.getAllCityList();
//        CityAdapter = new ArrayAdapter(ProductActivity.this, R.layout.spinner_item, CityList);
//        acCity.setAdapter(CityAdapter);
//        acCity.setThreshold(2);
//
//        acCity.setOnFocusChangeListener(acCityFocusChange);
//
//        acCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//            }
//        });
//
//    }


    private void bindClient() {
        Glide.with(ProductActivity.this)
                .load("http://elite.rupeeboss.com/images/upload/168reli.png")
                .into(ivClientLogo);

        txtClientName.setText("Reliance General Insurance");
    }

    private void reqDocPopUp(List<DocProductEnity> lstDoc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this, R.style.CustomDialog);

        RecyclerView rvProductDoc;
        ProductDocAdapter mAdapter = new ProductDocAdapter(this, lstDoc);
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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductActivity.this);
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


    private boolean validate() {
        if (SERVICE_TYPE.equals("RTO")) {


            CityID = mapCity.get(spCity.getSelectedItem().toString());
            if (CityID == 0) {
                Snackbar.make(btnBooked, "Please Select City", Snackbar.LENGTH_SHORT).show();
                return false;
            }

            if (etRTO.getVisibility() == View.VISIBLE) {
                Snackbar.make(btnBooked, "Please Enter RTO ", Snackbar.LENGTH_SHORT).show();
                return false;
            } else {
                RTO_ID = mapLoc.get(spRTO.getSelectedItem().toString());

                if (RTO_ID == 0) {
                    Snackbar.make(btnBooked, "Please Select RTO Location", Snackbar.LENGTH_SHORT).show();
                    return false;
                }

            }


        } else {

//            boolean blnchk = valNonRTOCity();
//            if (blnchk == false) {
//                Snackbar.make(btnBooked, "Please Enter City", Snackbar.LENGTH_SHORT).show();
//                return false;
//            }

            if (lyPuc.getVisibility() == View.VISIBLE) {

                if (!isEmpty(etVehicleExp)) {
                    Snackbar.make(btnBooked, "Please Enter Expiry Date", Snackbar.LENGTH_SHORT).show();
                    return false;
                }


                if (!isEmpty(etVehicleNo)) {
                    Snackbar.make(btnBooked, "Please Enter Vehicle Number", Snackbar.LENGTH_SHORT).show();
                    return false;
                }

            }

        }


        return true;
    }


    //endregion

    // region Event
    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.acCity:
                if (SERVICE_TYPE.equals("NONRTO")) {
                    startActivityForResult(new Intent(ProductActivity.this, SearchCityActivity.class), Constants.SEARCH_CITY_CODE);
                }
                break;

            case R.id.rlfileUpload:
                startActivityForResult(new Intent(ProductActivity.this, DocUploadActivity.class), Constants.UPLOAD_FILE);
                break;

            case R.id.rlDoc:
                showDialog();
                new ProductController(this).getProducDoc(PRODUCT_ID, ProductActivity.this);
                break;


            case R.id.etVehicleExp:

                DateTimePicker.showDatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                        if (view1.isShown()) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            String currentDay = simpleDateFormat.format(calendar.getTime());
                            etVehicleExp.setText(currentDay);
                        }
                    }
                });
                break;
            case R.id.btnBooked:

                if (validate() == false) {
                    return;
                }

                if (SERVICE_TYPE.equals("RTO")) {

                    //region RTO Payment
                    int cityID = mapCity.get(spCity.getSelectedItem().toString());
                    InsertOrderRequestEntity requestEntity = new InsertOrderRequestEntity();

                    requestEntity.setProdid("" + PRODUCT_ID);
                    requestEntity.setProdName("" + PRODUCT_NAME);
                    requestEntity.setUserid("" + loginEntity.getUser_id());
                    requestEntity.setTransaction_id("");
                    requestEntity.setSubscription("");
                    requestEntity.setVehicleno("");
                    requestEntity.setPucexpirydate("");
                    requestEntity.setRto_id(spRTO.getSelectedItem().toString());
                    requestEntity.setCityid("" + cityID);
                    requestEntity.setAmount("" + AMOUNT);
                    requestEntity.setPayment_status("0");


                    startActivity(new Intent(ProductActivity.this, PaymentRazorActivity.class)
                            .putExtra("PRODUCT_NAME_PAYMENT", requestEntity));

                    //endregion


                } else {

                    //region Non RTO Payment
                    String VEHICLEE_NO = "";
                    String PUC_EXP_DATE = "";
                    String SUBSCRIPTION = "";

                    // region PUC Data
                    if (PRODUCT_ID == 131) {
                        VEHICLEE_NO = etVehicleNo.getText().toString();
                        PUC_EXP_DATE = etVehicleExp.getText().toString();
                    }
                    //endregion

                    // region Special Offer
                    if (nonRTOProd.getIsSpecial().equals("1")) {
                        NonRtoSpeciallistEntity nonRtoSpEntity = getSpecialSelectedItem();
                        if (nonRtoSpEntity != null) {
                            SUBSCRIPTION = nonRtoSpEntity.getSpName();
                            AMOUNT = nonRtoSpEntity.getSpPrice();
                        }
                    }
                    //endregion


//                    int cityID = dataBaseController.getMainCity_ID(acCity.getText().toString());


                    InsertOrderRequestEntity requestEntity = new InsertOrderRequestEntity();

                    requestEntity.setProdid("" + PRODUCT_ID);
                    requestEntity.setUserid("" + loginEntity.getUser_id());
                    requestEntity.setTransaction_id("");
                    requestEntity.setSubscription(SUBSCRIPTION);
                    requestEntity.setVehicleno(VEHICLEE_NO);
                    requestEntity.setPucexpirydate(PUC_EXP_DATE);
                    requestEntity.setRto_id("");
                    requestEntity.setCityid("" + CITY_ID_NON_RTO);
                    requestEntity.setAmount(AMOUNT);
                    requestEntity.setPayment_status("1");


                    showDialog();
                    new ProductController(ProductActivity.this).inserOrderData(requestEntity, this);

                    //endregion
                }

                break;


        }

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

            mapLoc.put(rtoObject.getSeries_no(), rtoObject.getRto_id());    // adding in Map

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

        RtoAdapter = new ArrayAdapter(ProductActivity.this, R.layout.spinner_item, RtoList);
        spRTO.setAdapter(RtoAdapter);
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

        Glide.with(ProductActivity.this)
                .load(rtoProd.getProduct_logo())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(ivLogo);


    }

    private void setNonRtoData(NonRtoProductDisplayMainEntity nonRTOProd) {

        lvLogo.setVisibility(View.VISIBLE);
        if (nonRTOProd.getPrice() != null) {

            if (nonRTOProd.getPrice().trim().equals("0")) {
                txtCharges.setText("Discounted Product");
                AMOUNT = "0";
            } else {
                txtCharges.setText("" + "\u20B9" + " " + nonRTOProd.getPrice());
                AMOUNT = nonRTOProd.getPrice().trim();
            }


        }

        if (nonRTOProd.getTAT() != null && (!nonRTOProd.getTAT().equals(""))) {
            lyTAT.setVisibility(View.VISIBLE);
            txtTAT.setText("" + nonRTOProd.getTAT());
        } else {
            lyTAT.setVisibility(View.GONE);
        }

        Glide.with(ProductActivity.this)
                .load(nonRTOProd.getProduct_logo())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(ivLogo);

        if (nonRTOProd.getIsSpecial().equals("1")) {

            lySpecial.setVisibility(View.VISIBLE);
            nonRtoSpeciList = nonRTOProd.getSpeciallist();
            mSpecialAdapter = new SpecialProductAdapter(ProductActivity.this, nonRtoSpeciList);
            rvSpecial.setAdapter(mSpecialAdapter);
        } else {
            lySpecial.setVisibility(View.GONE);
        }

    }

    public NonRtoSpeciallistEntity getSpecialSelectedItem() {
        NonRtoSpeciallistEntity nonRtoSpeciallistEntity = mSpecialAdapter.getUserSelectedItem();


        return nonRtoSpeciallistEntity;
    }

    public void setSpecialAmount(NonRtoSpeciallistEntity curEntity, Boolean isCheck) {
        if (curEntity.getSpPrice() != null) {
            if (isCheck) {
                txtCharges.setText("" + "\u20B9" + " " + curEntity.getSpPrice());
                AMOUNT = curEntity.getSpPrice();
            } else {

                if (nonRTOProd.getPrice() != null) {

                    if (nonRTOProd.getPrice().trim().equals("0")) {
                        txtCharges.setText("Discounted Product");
                        AMOUNT = "0";
                    } else {
                        txtCharges.setText("" + "\u20B9" + " " + nonRTOProd.getPrice());
                        AMOUNT = nonRTOProd.getPrice().trim();
                    }


                }
            }

        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof OrderResponse) {
            if (response.getStatus_code() == 0) {

                OrderID = (((OrderResponse) response).getData().get(0).getId());

                showPaymentAlert(btnBooked, response.getMessage().toString(), OrderID);

            }

        } else if (response instanceof RtoProductDisplayResponse) {
            if (response.getStatus_code() == 0) {

                if (((RtoProductDisplayResponse) response).getData().size() > 0) {

                    spCity.setVisibility(View.VISIBLE);
                    acCity.setVisibility(View.GONE);

                    List<RtoProductDisplayMainEntity> listCity = removeDuplicateCity(((RtoProductDisplayResponse) response).getData());

                    bindRTOSpinner(listCity);


                }
            }
        } else if (response instanceof NonRtoProductDisplayResponse) {
            if (response.getStatus_code() == 0) {

                if (((NonRtoProductDisplayResponse) response).getData() != null) {

                    nonRTOProd = ((NonRtoProductDisplayResponse) response).getData().get(0);
                    setNonRtoData(nonRTOProd);
                }
                // Todo : Verify whether city List Have data or not
                CityList = dataBaseController.getAllCityList();
                if (CityList.size() > 0) {
                    // bindAutoCity();
                } else {
                    showDialog();
                    new ProductController(this).getCityMaster(ProductActivity.this);
                }
            }
        } else if (response instanceof ProductDocumentResponse) {
            if (response.getStatus_code() == 0) {

                if (((ProductDocumentResponse) response).getData() != null) {

                    reqDocPopUp(((ProductDocumentResponse) response).getData());
                } else {

                    Toast.makeText(this, "No Data Available", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (response instanceof CityResponse) {


            if (response.getStatus_code() == 0) {

                //   bindAutoCity();
            }
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


    private void bindRTOSpinner(final List<RtoProductDisplayMainEntity> rtoProductDisplayList) {


        RtoProductDisplayMainEntity rtoEntity = new RtoProductDisplayMainEntity();

        rtoEntity.setCity_id(0);
        rtoEntity.setCityname("Select");
        rtoEntity.setProd_id(0);
        rtoEntity.setPrice("");
        rtoEntity.setTAT("");
        rtoEntity.setProduct_logo("");

        rtoProductDisplayList.add(0, rtoEntity);

        CityAdapter = new ArrayAdapter(ProductActivity.this, R.layout.spinner_item, getRTOCityList(rtoProductDisplayList));
        spCity.setAdapter(CityAdapter);
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {

                    clearCity();


                } else if (rtoProductDisplayList.get(position).getCity_id() == 2653) {

                    etRTO.setVisibility(View.VISIBLE);
                    spRTO.setVisibility(View.GONE);
                    lvLogo.setVisibility(View.VISIBLE);
                    setRtoTAT(rtoProductDisplayList.get(position));

                } else if (rtoProductDisplayList.get(position).getRtolist() != null) {

                    if (rtoProductDisplayList.get(position).getRtolist().size() > 0) {

                        spRTO.setVisibility(View.VISIBLE);
                        etRTO.setVisibility(View.GONE);
                        RtoList = getRTOLocList(rtoProductDisplayList.get(position).getRtolist());
                        RtoAdapter = new ArrayAdapter(ProductActivity.this, R.layout.spinner_item, RtoList);
                        spRTO.setAdapter(RtoAdapter);

                        lvLogo.setVisibility(View.VISIBLE);
                        setRtoTAT(rtoProductDisplayList.get(position));


                    } else {
                        clearCity();
                    }
                } else {
                    clearCity();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    public void OnFailure(Throwable t) {
        //  btnBooked.setEnabled(true);
        cancelDialog();
        Toast.makeText(ProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    //endregion


    @Override
    public void onPositiveButtonClick(View view) {

        if (view.getId() == R.id.btnBooked) {
            if (OrderID > 0) {
                startActivity(new Intent(ProductActivity.this, DocUploadActivity.class)
                        .putExtra("ORDER_ID", OrderID));
                this.finish();
            }
        }

    }

    public void getSpecialProduct(NonRtoSpeciallistEntity nonRtoSpeciallistEntity, int position) {


        for (int i = 0; i < nonRtoSpeciList.size(); i++) {
            if (nonRtoSpeciList.get(i).getProductid() == nonRtoSpeciallistEntity.getProductid()) {
                nonRtoSpeciList.get(i).setCheck(true);
            } else {
                nonRtoSpeciList.get(i).setCheck(false);
            }
        }

        mSpecialAdapter.refreshAdapter(nonRtoSpeciList);

    }

    public void showPaymentAlert(final View view, String strBody, final int OrderID) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this, R.style.CustomDialog);


        Button btnClose;
        TextView txtMessage;


        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_success_message, null);


        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        btnClose = (Button) dialogView.findViewById(R.id.btnClose);
        txtMessage = (TextView) dialogView.findViewById(R.id.txtMessage);

        txtMessage.setText("" + strBody);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startActivity(new Intent(ProductActivity.this, DocUploadActivity.class)
                        .putExtra("ORDER_ID", OrderID));

                ProductActivity.this.finish();
            }
        });


        alertDialog.setCancelable(false);

        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.SEARCH_CITY_CODE) {
            if (data != null) {

                String CITY_NAME = data.getStringExtra(Constants.SEARCH_CITY_NAME);
                CITY_ID_NON_RTO = data.getStringExtra(Constants.SEARCH_CITY_ID);
                acCity.setText(CITY_NAME);

            }
        }else if  (requestCode == Constants.UPLOAD_FILE) {
            if (data != null) {

                Toast.makeText(this,"data",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void downloadPdf(String url, String name) {
        Toast.makeText(this, "Download started..", Toast.LENGTH_LONG).show();
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name + ".pdf");
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        r.setMimeType(MimeTypeMap.getFileExtensionFromUrl(url));
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }

}
