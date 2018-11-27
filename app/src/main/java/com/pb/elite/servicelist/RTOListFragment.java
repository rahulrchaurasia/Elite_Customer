package com.pb.elite.servicelist;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.subcategoryEntity;
import com.pb.elite.product.ProductActivity;
import com.pb.elite.product.ProductMainActivity;
import com.pb.elite.servicelist.adapter.RTOServiceAdapter;
import com.pb.elite.servicelist.adapter.ServicePagerAdapter;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RTOListFragment extends BaseFragment {


    List<RTOServiceEntity> mRTOList;
    RecyclerView rvProduct;
    PrefManager prefManager;
    RTOServiceAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_rtolist, container, false);

        View view = inflater.inflate(R.layout.fragment_rtolist, container, false);


        initialize(view);
        mRTOList = new ArrayList<>();

        if (getArguments().getParcelableArrayList(ServicePagerAdapter.RTO_LIST) != null) {
            mRTOList =  getArguments().getParcelableArrayList(ServicePagerAdapter.RTO_LIST);
            bindData();
        }
        return view;
    }

    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());
        rvProduct = (RecyclerView) view.findViewById(R.id.rvProduct);
        rvProduct.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvProduct.setLayoutManager(layoutManager);

    }

    private void bindData() {
        mAdapter = new RTOServiceAdapter(RTOListFragment.this, mRTOList);
        rvProduct.setAdapter(mAdapter);

    }

    public void getProduct(RTOServiceEntity rtoServiceEntity) {

        if(rtoServiceEntity.getSubcategory().size() >0) {
            Intent intent = new Intent(getActivity(), SubProductActivity.class);
            intent.putParcelableArrayListExtra("SUB_PRODUCT_LIST", (ArrayList<subcategoryEntity>) rtoServiceEntity.getSubcategory());
            intent.putExtra("TYPE","RTO");
            startActivity(intent);
        }else{
            Intent intent = new Intent(getActivity(), ProductMainActivity.class);
            intent.putExtra(Constants.RTO_PRODUCT_DATA, rtoServiceEntity);
            intent.putExtra(Constants.SERVICE_TYPE, "RTO");
            startActivity(intent);
        }
    }
}
