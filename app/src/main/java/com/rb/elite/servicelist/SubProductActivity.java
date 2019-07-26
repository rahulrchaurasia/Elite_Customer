package com.rb.elite.servicelist;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.rb.elite.R;
import com.rb.elite.core.model.RTOServiceEntity;
import com.rb.elite.product.ProductMainActivity;
import com.rb.elite.servicelist.adapter.SubProductServiceAdapter;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.utility.Constants;

import java.util.List;

public class SubProductActivity extends AppCompatActivity {

    SubProductServiceAdapter subAdapter;
    RecyclerView rvSubProduct;
    PrefManager prefManager;
    List<RTOServiceEntity> msubList;
    String TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialize();

        if (getIntent().getStringExtra(Constants.SERVICE_TYPE) != null) {
            TYPE = getIntent().getStringExtra(Constants.SERVICE_TYPE);
        }

        if (getIntent().getParcelableArrayListExtra(Constants.SUB_PRODUCT_LIST) != null) {

            msubList = getIntent().getParcelableArrayListExtra(Constants.SUB_PRODUCT_LIST);

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


    public void getProduct(RTOServiceEntity subcategoryEntity) {

        Intent intent = new Intent(SubProductActivity.this, ProductMainActivity.class);
        intent.putExtra(Constants.SUB_PRODUCT_DATA, subcategoryEntity);
        intent.putExtra(Constants.SERVICE_TYPE, TYPE);
        startActivity(intent);
        //this.finish();

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
