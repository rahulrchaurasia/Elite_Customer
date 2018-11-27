package com.pb.elite.productServiceRtoFragment;


import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssistanObtainFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {


    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    // RecyclerView rvCity , rvRTO;


    EditText etRTO, etRTO_OTH, etVehicle, etCity;
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    String CITY_ID_NON_RTO = "";
    Button btnBooked;

    RTOServiceEntity serviceEntity;
    subcategoryEntity subRTOEntity;

    LinearLayout lvLogo, llDocumentUpload, lyRTO, lyTAT;
    RelativeLayout rlDoc;
    LinearLayout lyMakeModel;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;



    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;
    int PARENT_PRODUCT_ID = 0;

    String AMOUNT = "0";
    int OrderID = 0;




    List<RtoProductDisplayMainEntity> listCityMain;

    RtoProductDisplayMainEntity rtoProductDisplayMainEntity;
    RtoProductEntity rtoMainEntity;

    public AssistanObtainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_assistan_obtain, container, false);
        initialize(view);

        setOnClickListener();

        dataBaseController = new DataBaseController(getActivity());
        loginEntity = dataBaseController.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();
        OrderID = 0;
        bindData();

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



        showDialog();
        new ProductController(getActivity()).getRTOProductList(PARENT_PRODUCT_ID, PRODUCT_CODE, loginEntity.getUser_id(), AssistanObtainFragment.this);


        return view;
    }

    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());


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

        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);
        llDocumentUpload = (LinearLayout) view.findViewById(R.id.llDocumentUpload);
        lyRTO = (LinearLayout) view.findViewById(R.id.lyRTO);
        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);

        lyMakeModel = (LinearLayout) view.findViewById(R.id.lyMakeModel);

        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);




    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);

        etRTO.setFocusable(false);
        etRTO.setClickable(true);

        rlDoc.setOnClickListener(this);
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


       return false;
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
        ProductDocAdapter mAdapter = new ProductDocAdapter(AssistanObtainFragment.this, lstDoc);
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


            case R.id.btnBooked:



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
