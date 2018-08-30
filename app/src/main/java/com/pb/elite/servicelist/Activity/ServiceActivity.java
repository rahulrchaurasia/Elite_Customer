package com.pb.elite.servicelist.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.ServiceMainEntity;
import com.pb.elite.core.response.ServiceListResponse;
import com.pb.elite.servicelist.NonRTOListFragment;
import com.pb.elite.servicelist.RTOListFragment;
import com.pb.elite.servicelist.adapter.ServicePagerAdapter;

public class ServiceActivity extends BaseActivity  implements IResponseSubcriber {

    private String RTO = "RTO SERVICES";
    private String NonRTO = "MISCELLANEOUS SERVICES";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ServiceMainEntity mMasterData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initialize();
        showDialog();
        new ProductController(ServiceActivity.this).getRtoAndNonRtoList(this);


    }

    private void initialize() {
        mMasterData = new ServiceMainEntity();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);


        // setupTabIcons();
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
}
