package com.pb.elite.orderDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.LoginEntity;
import com.pb.elite.core.model.OrderDetailEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.response.OrderDetailResponse;
import com.pb.elite.core.response.OrderResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.document.DocUploadActivity;
import com.pb.elite.product.ProductActivity;
import com.pb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends BaseFragment implements IResponseSubcriber {

    RecyclerView rvOrderDtl;
    List<OrderDetailEntity> lstOrderDetail;
    OrderDetailAdapter mAdapter;

    DataBaseController dataBaseController;
    UserEntity loginEntity;


    public OrderDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        initilize(view);
        dataBaseController = new DataBaseController(getActivity());
        loginEntity = dataBaseController.getUserData();

        showDialog();
        new ProductController(getContext()).getOrderData(loginEntity.getUser_id(), this);
        return view;
    }

    private void initilize(View view) {
        rvOrderDtl = (RecyclerView) view.findViewById(R.id.rvOrderDtl);
        rvOrderDtl.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvOrderDtl.setLayoutManager(mLayoutManager);

        lstOrderDetail = new ArrayList<OrderDetailEntity>();
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof OrderDetailResponse) {
            if (response.getStatus_code() == 0) {

                //Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                lstOrderDetail = ((OrderDetailResponse) response).getData();
                mAdapter = new OrderDetailAdapter(OrderDetailFragment.this, lstOrderDetail);
                rvOrderDtl.setAdapter(mAdapter);

            } else {
                rvOrderDtl.setAdapter(null);
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }


    public void getOrderId(int orderId) {


        startActivityForResult(new Intent(getActivity(), DocUploadActivity.class)
                .putExtra("ORDER_ID",orderId), Constants.ORDER_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ORDER_CODE) {
            if (data != null) {

//                String CITY_NAME = data.getStringExtra(Constants.SEARCH_CITY_NAME);
//                CITY_ID_NON_RTO = data.getStringExtra(Constants.SEARCH_CITY_ID);
//                acCity.setText(CITY_NAME);

                Toast.makeText(getActivity(),"Dafwf",Toast.LENGTH_LONG).show();
            }


        }
    }
}