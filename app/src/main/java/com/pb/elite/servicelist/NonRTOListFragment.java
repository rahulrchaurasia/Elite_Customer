package com.pb.elite.servicelist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.core.model.NONRTOServiceEntity;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.subcategoryEntity;
import com.pb.elite.product.ProductActivity;
import com.pb.elite.servicelist.adapter.NonRTOServiceAdapter;
import com.pb.elite.servicelist.adapter.RTOServiceAdapter;
import com.pb.elite.servicelist.adapter.ServicePagerAdapter;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NonRTOListFragment extends BaseFragment {

    List<NONRTOServiceEntity> mNonRTOList;
    RecyclerView rvProduct;
    PrefManager prefManager;
    NonRTOServiceAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_non_rtolist, container, false);

        initialize(view);
        if (getArguments().getParcelableArrayList(ServicePagerAdapter.NONRTO_LIST) != null) {
            mNonRTOList =  getArguments().getParcelableArrayList(ServicePagerAdapter.NONRTO_LIST);
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
        mAdapter = new NonRTOServiceAdapter(NonRTOListFragment.this, mNonRTOList);
        rvProduct.setAdapter(mAdapter);

    }

    public void getProduct(NONRTOServiceEntity nonrtoServiceEntity) {

        if(nonrtoServiceEntity.getSubcategory().size() >0) {
            Intent intent = new Intent(getActivity(), SubProductActivity.class);
            intent.putParcelableArrayListExtra("SUB_PRODUCT_LIST", (ArrayList<subcategoryEntity>) nonrtoServiceEntity.getSubcategory());
            intent.putExtra("TYPE","NONRTO");
            startActivity(intent);
        }else{
            Intent intent = new Intent(getActivity(), ProductActivity.class);
            intent.putExtra(Constants.NON_RTO_PRODUCT_DATA, nonrtoServiceEntity);
            intent.putExtra(Constants.SERVICE_TYPE, "NONRTO");
            startActivity(intent);
        }
    }

}
