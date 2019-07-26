package com.rb.elite.servicelist;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rb.elite.BaseFragment;
import com.rb.elite.R;
import com.rb.elite.core.model.RTOServiceEntity;
import com.rb.elite.core.model.UserConstatntEntity;
import com.rb.elite.core.model.UserEntity;
import com.rb.elite.database.DataBaseController;
import com.rb.elite.product.ProductMainActivity;
import com.rb.elite.servicelist.adapter.NonRTOServiceAdapter;
import com.rb.elite.servicelist.adapter.ServicePagerAdapter;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NonRTOListFragment extends BaseFragment {

    List<RTOServiceEntity> mNonRTOList;
    RecyclerView rvProduct;
    PrefManager prefManager;
    NonRTOServiceAdapter mAdapter;

    DataBaseController dataBaseController;
    UserEntity loginEntity;
    UserConstatntEntity userConstatntEntity;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_non_rtolist, container, false);

        dataBaseController = new DataBaseController(getActivity());
        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();

        initialize(view);
       //  setUserInfo();
        if (getArguments().getParcelableArrayList(ServicePagerAdapter.NONRTO_LIST) != null) {
            mNonRTOList = getArguments().getParcelableArrayList(ServicePagerAdapter.NONRTO_LIST);
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

    public void getProduct(RTOServiceEntity nonrtoServiceEntity) {

        if (nonrtoServiceEntity.getSubcategory().size() > 0) {
            Intent intent = new Intent(getActivity(), SubProductActivity.class);
            intent.putParcelableArrayListExtra("SUB_PRODUCT_LIST", (ArrayList<RTOServiceEntity>) nonrtoServiceEntity.getSubcategory());
            intent.putExtra(Constants.SERVICE_TYPE, Constants.SERVICE_NONRTO);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), ProductMainActivity.class);
            intent.putExtra(Constants.NON_RTO_PRODUCT_DATA, nonrtoServiceEntity);
            intent.putExtra(Constants.SERVICE_TYPE, Constants.SERVICE_NONRTO);
            startActivity(intent);
        }
    }

}
