package com.pb.elite.servicelist;

import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pb.elite.R;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.model.subcategoryEntity;
import com.pb.elite.product.ProductActivity;
import com.pb.elite.servicelist.adapter.SubProductServiceAdapter;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.List;

public class SubProductActivity extends AppCompatActivity {

    SubProductServiceAdapter subAdapter;
    RecyclerView rvSubProduct;
    PrefManager prefManager;
    List<subcategoryEntity> msubList;
    String TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialize();

        if(getIntent().getStringExtra("TYPE") != null)
        {
            TYPE = getIntent().getStringExtra("TYPE");
        }

        if(getIntent().getParcelableArrayListExtra("SUB_PRODUCT_LIST")  != null){

            msubList =  getIntent().getParcelableArrayListExtra("SUB_PRODUCT_LIST");

            bindData();
        }
    }

    private void initialize() {

        prefManager = new PrefManager(SubProductActivity.this);
        rvSubProduct = (RecyclerView) findViewById(R.id.rvSubProduct);
        rvSubProduct.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SubProductActivity.this);
        rvSubProduct.setLayoutManager(layoutManager);


    }

    private void bindData() {
        subAdapter = new SubProductServiceAdapter(this, msubList);
        rvSubProduct.setAdapter(subAdapter);

    }



    public void getProduct(subcategoryEntity subcategoryEntity) {

            Intent intent = new Intent(SubProductActivity.this, ProductActivity.class);
            intent.putExtra(Constants.SUB_PRODUCT_DATA, subcategoryEntity);
            intent.putExtra(Constants.SERVICE_TYPE, TYPE);
            startActivity(intent);
            this.finish();

    }

}
