package com.pb.elite.feedback;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.OrderDetailEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.response.OrderDetailResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.non_rto_fragments.ProvideVehicleDamageFragment;
import com.pb.elite.non_rto_fragments.adapter.InsurerMainAdapter;
import com.pb.elite.orderDetail.OrderDetailAdapter;
import com.pb.elite.orderDetail.OrderDetailFragment;
import com.pb.elite.utility.Constants;

import java.util.List;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener ,IResponseSubcriber {

    // etBody   btnSubmit
    EditText etBody,etReqName;
    TextView txtReqID;
    Button btnSubmit;
    RatingBar ratingBar;
    OrderDetailEntity orderDetailEntity;
    FeedbackAdapter feedbackAdapter;
    List<OrderDetailEntity> lstOrderDetail;
    UserEntity loginEntity;
    DataBaseController dataBaseController;
     AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dataBaseController = new DataBaseController(this);
        loginEntity = dataBaseController.getUserData();

        initialize();
        setOnClickListener();


        showDialog();
        new ProductController(this).getOrderData(loginEntity.getUser_id(), this);
    }

    private void initialize() {
        btnSubmit = findViewById(R.id.btnSubmit);
        txtReqID = findViewById(R.id.txtReqID);
        etBody = findViewById(R.id.etBody);
        etReqName = findViewById(R.id.etReqName);
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmit.setOnClickListener(this);
    }

    private void setOnClickListener()
    {
        etReqName.setFocusable(false);
        etReqName.setClickable(true);

        etReqName.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    public void requestPopUp(String Title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);

        TextView txtHdr;
        ImageView ivCross;

        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.feedback_dialog, null);

        builder.setView(dialogView);
        alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        txtHdr =  dialogView.findViewById(R.id.txtHdr);
        RecyclerView rvRTO = dialogView.findViewById(R.id.rvRTO);
        ivCross  = (ImageView) dialogView.findViewById(R.id.ivCross);

        rvRTO.setLayoutManager(new LinearLayoutManager(this));
        rvRTO.setHasFixedSize(true);
        rvRTO.setNestedScrollingEnabled(false);
        feedbackAdapter = new FeedbackAdapter(this, lstOrderDetail);
        rvRTO.setAdapter(feedbackAdapter);
        rvRTO.setVisibility(View.VISIBLE);
        txtHdr.setText(Title);


        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();

    }
    public void getOrderData(OrderDetailEntity temporderDetailEntity)
    {
        if(alertDialog != null)
            if(alertDialog.isShowing()) {
                alertDialog.dismiss();
                orderDetailEntity = temporderDetailEntity;
                txtReqID.setText(orderDetailEntity.getOrder_id());
                etReqName.setText(orderDetailEntity.getProduct_name());
            }
    }

    @Override
    public void onClick(View view) {
        Constants.hideKeyBoard(view, FeedbackActivity.this);
        switch (view.getId()) {
            case R.id.btnSubmit:

                if (!isEmpty(etBody)) {
                    getCustomToast("Please enter your feedback");
                    return;
                }

                getCustomToast("FeedBack Save Successfully");
                etBody.setText("");
                break;

            case R.id.etReqName:
                requestPopUp("Select Request");

                break;

        }


    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
        if (response instanceof OrderDetailResponse) {
            if (response.getStatus_code() == 0) {

                //Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                lstOrderDetail = ((OrderDetailResponse) response).getData();


            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
