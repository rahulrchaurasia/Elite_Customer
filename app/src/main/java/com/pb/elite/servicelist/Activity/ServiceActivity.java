package com.pb.elite.servicelist.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.ServiceMainEntity;
import com.pb.elite.core.model.UserConstatntEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.response.ServiceListResponse;
import com.pb.elite.servicelist.NonRTOListFragment;
import com.pb.elite.servicelist.RTOListFragment;
import com.pb.elite.servicelist.adapter.ServicePagerAdapter;
import com.pb.elite.splash.PrefManager;

public class ServiceActivity extends BaseActivity  implements IResponseSubcriber {

    private String RTO = "RTO SERVICES";
    private String NonRTO = "MISCELLANEOUS SERVICES";

    private TabLayout tabLayout;
    TextView txtName, txtVehicle;
    private ViewPager viewPager;
    ServiceMainEntity mMasterData;

    UserEntity loginEntity;
    UserConstatntEntity userConstatntEntity;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();
        initialize();
        setUserInfo();
        showDialog();
        new ProductController(ServiceActivity.this).getRtoAndNonRtoList(this);


    }

    private void initialize() {
        mMasterData = new ServiceMainEntity();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        txtName = findViewById(R.id.txtName);
        txtVehicle = findViewById(R.id.txtVehicle);


        // setupTabIcons();
    }

    private void setUserInfo() {

        if (loginEntity != null) {
            txtName.setText("" + loginEntity.getName());

        } else {
            txtName.setText("");

        }

        if(userConstatntEntity!=null)
        {
            txtVehicle.setText("" +userConstatntEntity.getVehicleno() );
        }else{
            txtVehicle.setText("");
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ServicePagerAdapter adapter = new ServicePagerAdapter(ServiceActivity.this.getSupportFragmentManager(), mMasterData);


        adapter.addFrag(new RTOListFragment(), RTO);
        adapter.addFrag(new NonRTOListFragment(), NonRTO);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof ServiceListResponse) {
            if (response.getStatus_code() == 0) {

                if  (((ServiceListResponse) response).getData() != null)    {

                    if ((((ServiceListResponse) response).getData().getRTO().size() != 0) && (((ServiceListResponse) response).getData().getNONRTO().size() != 0)) {

//                        rtoServiceEntityList = ((ServiceListResponse) response).getData().getRTO();
//
//                        nonRtoServiceEntityList = ((ServiceListResponse) response).getData().getNONRTO();
                        mMasterData = ((ServiceListResponse) response).getData();

                        setupViewPager(viewPager);

                    }
                }
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // finish();
        supportFinishAfterTransition();
        super.onBackPressed();
    }
}
