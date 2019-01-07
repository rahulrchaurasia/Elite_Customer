package com.rb.elite.product;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rb.elite.BaseFragment;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.product.ProductController;
import com.rb.elite.core.model.ProductEntity;
import com.rb.elite.core.response.ProductResponse;
import com.rb.elite.core.response.RtoLocationReponse;
import com.rb.elite.servicelist.adapter.RTOServiceAdapter;
import com.rb.elite.splash.PrefManager;

import java.util.List;


public class ProductDisplayFragment extends BaseFragment implements IResponseSubcriber {


    List<ProductEntity> productEntityList;
    RecyclerView rvProduct;

    RTOServiceAdapter mAdapter;
    PrefManager prefManager;
    ProductEntity productEntity;
    String Type = "";

    public ProductDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_product_display, container, false);

        View view = inflater.inflate(R.layout.content_product_detail, container, false);
        initialize(view);

        showDialog();
        new ProductController(getActivity()).getProductMaster(this);

        return view;
    }

    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());
        rvProduct = (RecyclerView) view.findViewById(R.id.rvProduct);
        rvProduct.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvProduct.setLayoutManager(layoutManager);


    }

    @Override
    public void OnSuccess(APIResponse response, String message) {


        cancelDialog();
        if (response instanceof ProductResponse) {
            if (response.getStatus_code() == 0) {

                if (((ProductResponse) response).getData() != null) {
                    productEntityList = ((ProductResponse) response).getData();

                   // mAdapter = new RTOServiceAdapter(this, productEntityList);
                    rvProduct.setAdapter(mAdapter);
                } else {
                    rvProduct.setAdapter(null);
                    Snackbar.make(rvProduct, "No Data Available", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                rvProduct.setAdapter(null);
                Snackbar.make(rvProduct, "No Data Available", Snackbar.LENGTH_SHORT).show();
            }
        } else if (response instanceof RtoLocationReponse) {
            if (response.getStatus_code() == 0) {

                //  response.body().getData().getRtolist()

                if (((RtoLocationReponse) response).getData().getRtolist() != null) {

                    //
                    if (((RtoLocationReponse) response).getData().getRtolist().size() == 0) {
                        Type = "NONRTO";
                    } else {
                        Type = "RTO";
                    }

                    Intent intent = new Intent(getActivity(), ProductMainActivity.class);
                    intent.putExtra("PRODUCT_DATA", productEntity);
                    intent.putExtra("SERVICE_TYPE", Type);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
    }

    public void getProduct(ProductEntity prdEntity) {

        this.productEntity = prdEntity;
        showDialog();

        new ProductController(getActivity()).getRTOLocationMaster(productEntity.getId(), this);


    }
}
