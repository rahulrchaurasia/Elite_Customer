package com.pb.elite.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.pb.elite.productServiceRtoFragment.RenewRcFragment;
import com.pb.elite.rtoAdapter.CityMainAdapter;
import com.pb.elite.rtoAdapter.RtoMainAdapter;
import com.pb.elite.utility.Constants;

import java.util.List;

public class ProductMainActivity extends BaseActivity {

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
    BottomSheetBehavior sheetBehavior;
    LinearLayout layoutBottomSheet;

    RecyclerView rvCity,rvRTO;
    CityMainAdapter cityMainAdapter;
    RtoMainAdapter rtoMainAdapter;

    List<RtoProductDisplayMainEntity> listCityMain;
    List<RtoProductEntity> rtoProductDisplayList;

    RtoProductDisplayMainEntity cityMainEntity;

    RenewRcFragment rcFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvCity = (RecyclerView) findViewById(R.id.rvCity);
        rvRTO = (RecyclerView) findViewById(R.id.rvRTO);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        rvCity.setLayoutManager(layoutManager);
        rvCity.setHasFixedSize(true);


        rvRTO.setLayoutManager(layoutManager1);
        rvRTO.setHasFixedSize(true);

        layoutBottomSheet = findViewById(R.id.bottom_sheet_dialog);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);


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

            } else if (getIntent().hasExtra(Constants.SUB_PRODUCT_DATA)) {

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


    private void setListFragmentSubProdct() {

        if ((PRODUCT_CODE.equalsIgnoreCase("1.1")) || (PRODUCT_CODE.equalsIgnoreCase("1.2"))
                || (PRODUCT_CODE.equalsIgnoreCase("1.3"))) {

            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.SUB_PRODUCT_DATA, subRTOEntity);
            rcFragment = new RenewRcFragment();
            rcFragment.setArguments(bundle);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout, rcFragment);
            ft.commitAllowingStateLoss();
        }

    }


    public void sendPaymentRequest(InsertOrderRequestEntity requestEntity) {

        startActivity(new Intent(ProductMainActivity.this, PaymentRazorActivity.class)
                .putExtra("PRODUCT_NAME_PAYMENT", requestEntity));
        this.finish();
    }

    public void showCityBottomSheetDialog(List<RtoProductDisplayMainEntity> templistCityMain) {
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        listCityMain = templistCityMain;

      cityMainAdapter =  new CityMainAdapter(ProductMainActivity.this,listCityMain);
      rvCity.setAdapter(cityMainAdapter);
      rvCity.setVisibility(View.VISIBLE);
      rvRTO.setVisibility(View.GONE);

    }

    public void showRTOBottomSheetDialog() {
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        rtoProductDisplayList = cityMainEntity.getRtolist();
        rtoMainAdapter =  new RtoMainAdapter(ProductMainActivity.this,rtoProductDisplayList);
        rvRTO.setAdapter(rtoMainAdapter);
        rvCity.setVisibility(View.GONE);
        rvRTO.setVisibility(View.VISIBLE);

    }



    public void getCityBottomSheet(RtoProductDisplayMainEntity cityEntity)
    {
        cityMainEntity = cityEntity;
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        if(rcFragment == null) {
            rcFragment = (RenewRcFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        }

        rcFragment.setCityData(cityMainEntity.getCityname(),cityMainEntity);

    }

    public void getRTOBottomSheet(RtoProductEntity rtoEntity )
    {
        Toast.makeText(this,"RTO ",Toast.LENGTH_SHORT).show();
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        if(rcFragment == null) {
            rcFragment = (RenewRcFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        }

        rcFragment.setRTOData(rtoEntity.getSeries_no() +"-" + rtoEntity.getRto_location(),  rtoEntity);


    }
}
