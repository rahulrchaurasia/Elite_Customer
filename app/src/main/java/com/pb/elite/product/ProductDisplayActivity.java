package com.pb.elite.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.ProductEntity;
import com.pb.elite.core.response.ProductResponse;
import com.pb.elite.core.response.RtoLocationReponse;
import com.pb.elite.servicelist.adapter.RTOServiceAdapter;
import com.pb.elite.splash.PrefManager;

import java.util.List;

public class ProductDisplayActivity extends BaseActivity implements IResponseSubcriber {

    List<ProductEntity> productEntityList;
    RecyclerView rvProduct;

    RTOServiceAdapter mAdapter;

    PrefManager prefManager;

    ProductEntity productEntity;
    String Type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();
        showDialog();
        new ProductController(ProductDisplayActivity.this).getProductMaster(ProductDisplayActivity.this);


    }

    private void initialize() {

        prefManager = new PrefManager(ProductDisplayActivity.this);
        rvProduct = (RecyclerView) findViewById(R.id.rvProduct);
        rvProduct.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvProduct.setLayoutManager(layoutManager);


    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof ProductResponse) {
            if (response.getStatus_code() == 0) {

                if (((ProductResponse) response).getData() != null) {
                    productEntityList = ((ProductResponse) response).getData();

                 //   mAdapter = new RTOServiceAdapter(ProductDisplayActivity.this, productEntityList);
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

                    Intent intent = new Intent(ProductDisplayActivity.this, ProductActivity.class);
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

        new ProductController(ProductDisplayActivity.this).getRTOLocationMaster(productEntity.getId(), this);


    }
}
