package com.pb.elite.servicelist;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.NONRTOServiceEntity;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.ServiceMainEntity;
import com.pb.elite.core.response.ServiceListResponse;
import com.pb.elite.servicelist.adapter.ServicePagerAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductHomeFragment extends BaseFragment implements IResponseSubcriber {

    private String RTO = "RTO SERVICES";
    private String NonRTO = "MISCELLANEOUS SERVICES";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ServiceMainEntity mMasterData;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_product_home, container, false);

        View view = inflater.inflate(R.layout.fragment_product_home, container, false);
        initialize(view);

        showDialog();
        new ProductController(getActivity()).getRtoAndNonRtoList(this);

        return view;
    }

    private void initialize(View view) {
        mMasterData = new ServiceMainEntity();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        //setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);


        // setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        ServicePagerAdapter adapter = new ServicePagerAdapter(getActivity().getSupportFragmentManager(), mMasterData);


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

//    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
//
//    }


}
