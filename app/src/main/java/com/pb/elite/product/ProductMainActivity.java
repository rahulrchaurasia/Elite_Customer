package com.pb.elite.product;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.core.model.NONRTOServiceEntity;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.RtoProductDisplayMainEntity;
import com.pb.elite.core.model.RtoProductEntity;
import com.pb.elite.core.model.UserConstatntEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.model.subcategoryEntity;
import com.pb.elite.core.requestmodel.InsertOrderRequestEntity;
import com.pb.elite.payment.PaymentRazorActivity;
import com.pb.elite.productServiceRtoFragment.AssistanObtainFragment;
import com.pb.elite.productServiceRtoFragment.RenewRcFragment;
import com.pb.elite.rtoAdapter.CityMainAdapter;
import com.pb.elite.rtoAdapter.RtoMainAdapter;
import com.pb.elite.utility.Constants;

import java.util.List;

public class ProductMainActivity extends BaseActivity implements View.OnClickListener {

    UserConstatntEntity userConstatntEntity;

    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;

    String SERVICE_TYPE;
    String AMOUNT = "0";

    RTOServiceEntity serviceEntity;
    NONRTOServiceEntity nonrtoServiceEntity;
    subcategoryEntity subRTOEntity;
    //bottom_sheet_dialog
  //  BottomSheetBehavior sheetBehavior;
 //   LinearLayout layoutBottomSheet;

//    RecyclerView rvCity, rvRTO;
//    CityMainAdapter cityMainAdapter;
//    RtoMainAdapter rtoMainAdapter;
//
//    List<RtoProductDisplayMainEntity> listCityMain;
//    List<RtoProductEntity> rtoProductDisplayList;
//
//    RtoProductDisplayMainEntity cityMainEntity;

    RenewRcFragment rcFragment;
    AssistanObtainFragment assistanObtainFragment;

 //   ImageView ivCross;

    Bundle bundle;
    Fragment mainfragment = null;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // region Filter Type
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (getIntent().hasExtra(Constants.SERVICE_TYPE)) {

                SERVICE_TYPE = extras.getString(Constants.SERVICE_TYPE, "");
            }
            if (getIntent().hasExtra(Constants.RTO_PRODUCT_DATA)) {

                serviceEntity = extras.getParcelable(Constants.RTO_PRODUCT_DATA);
                PRODUCT_NAME = serviceEntity.getName();
                PRODUCT_CODE = serviceEntity.getProductcode();
                PRODUCT_ID = serviceEntity.getId();

            } else if (getIntent().hasExtra(Constants.NON_RTO_PRODUCT_DATA)) {

                nonrtoServiceEntity = extras.getParcelable(Constants.NON_RTO_PRODUCT_DATA);
                PRODUCT_NAME = nonrtoServiceEntity.getName();
                PRODUCT_CODE = nonrtoServiceEntity.getProductcode();
                PRODUCT_ID = nonrtoServiceEntity.getId();

            }else if (getIntent().hasExtra(Constants.SUB_PRODUCT_DATA)) {

                subRTOEntity = extras.getParcelable(Constants.SUB_PRODUCT_DATA);
                PRODUCT_NAME = subRTOEntity.getName();
                PRODUCT_CODE = subRTOEntity.getProductcode();
                PRODUCT_ID = subRTOEntity.getId();


                setListFragmentSubProdct();
            }

            //endregion


        }

        // endregion

    }


    private void setListFragmentProdct() {

        if ((PRODUCT_CODE.equalsIgnoreCase("1.1")) || (PRODUCT_CODE.equalsIgnoreCase("1.1"))
                || (PRODUCT_CODE.equalsIgnoreCase("1.1"))) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout, new RenewRcFragment());
            ft.commitAllowingStateLoss();
        }

    }


    private Fragment getProductFragment() {
        if ((PRODUCT_CODE.equalsIgnoreCase("1.1")) || (PRODUCT_CODE.equalsIgnoreCase("1.2"))
                || (PRODUCT_CODE.equalsIgnoreCase("1.3"))) {

            rcFragment = new RenewRcFragment();
            return rcFragment;


        } else if ((PRODUCT_CODE.equalsIgnoreCase("2.1")) || (PRODUCT_CODE.equalsIgnoreCase("2.2"))
                || (PRODUCT_CODE.equalsIgnoreCase("2.3")) || (PRODUCT_CODE.equalsIgnoreCase("2.4"))) {

            assistanObtainFragment = new AssistanObtainFragment();
            return assistanObtainFragment;


        }

        return null;
    }

    private void setListFragmentSubProdct() {


        if (getProductFragment() != null) {
            bundle = new Bundle();
            bundle.putParcelable(Constants.SUB_PRODUCT_DATA, subRTOEntity);

            mainfragment = getProductFragment();
            mainfragment.setArguments(bundle);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, mainfragment);
            transaction.commitAllowingStateLoss();
        }


    }


    public void sendPaymentRequest(InsertOrderRequestEntity requestEntity) {

        startActivity(new Intent(ProductMainActivity.this, PaymentRazorActivity.class)
                .putExtra("PRODUCT_NAME_PAYMENT", requestEntity));
        this.finish();
    }





    public void downloadPdf(String url, String name) {
        Toast.makeText(this, "Download started..", Toast.LENGTH_LONG).show();
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name + ".pdf");
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        r.setMimeType(MimeTypeMap.getFileExtensionFromUrl(url));
        DownloadManager dm = (DownloadManager) this.getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }


    @Override
    public void onClick(View v) {

    }
}
