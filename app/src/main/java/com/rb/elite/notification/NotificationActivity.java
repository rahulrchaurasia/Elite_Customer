package com.rb.elite.notification;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


import com.rb.elite.BaseActivity;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.product.ProductController;
import com.rb.elite.core.model.NotificationEntity;
import com.rb.elite.core.model.UserEntity;
import com.rb.elite.core.response.NotificationResponse;
import com.rb.elite.document.DocUploadActivity;
import com.rb.elite.orderDetail.OrderActivity;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends BaseActivity implements IResponseSubcriber {


    UserEntity loginEntity;
    PrefManager prefManager;

    RecyclerView rvNotify;
    List<NotificationEntity> NotificationLst;
    NotificationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        prefManager.setNotificationCounter(0);
        initialize();

        showDialog();
        new ProductController(NotificationActivity.this).getNotifcation(loginEntity.getUser_id(),"1", this);
    }


    private void initialize() {


        NotificationLst = new ArrayList<NotificationEntity>();

        prefManager.setNotificationCounter(0);

        rvNotify = (RecyclerView) findViewById(R.id.rvNotify);
        rvNotify.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this);
        rvNotify.setLayoutManager(layoutManager);



    }
    public void redirectToMain(NotificationEntity notifyEntity) {

        if(notifyEntity.getNotifyflag().toUpperCase().equals("DR")) {

            startActivity(new Intent(this, DocUploadActivity.class));
            finish();
        }
        else if(notifyEntity.getNotifyflag().toUpperCase().equals("NA")) {

            startActivity(new Intent(this, OrderActivity.class));
            finish();
        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof NotificationResponse) {
            if (response.getStatus_code() == 0) {

                //Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                NotificationLst = ((NotificationResponse) response).getData();
                mAdapter = new NotificationAdapter(NotificationActivity.this, NotificationLst);
                rvNotify.setAdapter(mAdapter);

            } else {
                rvNotify.setAdapter(null);
                Snackbar.make(rvNotify, "No Notification  Data Available", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();

        // intent.putExtra("COUNTER", "0");
        setResult(Constants.REQUEST_CODE, intent);
        finish();
        super.onBackPressed();
    }

}
