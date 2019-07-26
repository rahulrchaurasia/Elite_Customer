package com.rb.elite.rating;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rb.elite.BaseActivity;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.product.ProductController;
import com.rb.elite.core.controller.register.RegisterController;
import com.rb.elite.core.model.CompleteOrderEntity;
import com.rb.elite.core.model.RateDisplayEntity;
import com.rb.elite.core.model.UserEntity;
import com.rb.elite.core.requestmodel.RateRequestEntity;
import com.rb.elite.core.response.CompleteOrderResponse;
import com.rb.elite.core.response.DisplayRateResponse;
import com.rb.elite.core.response.RateResponse;
import com.rb.elite.feedback.ComplOrderAdapter;
import com.rb.elite.feedback.ICompOrderData;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.List;


public class RateActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber, ICompOrderData, BaseActivity.CustomPopUpListener {

    EditText etBody, etReqName;
    TextView txtReqestID, txtMessage;
    Button btnSubmit;
    LinearLayout lyComment;
    CardView cvRating;
    RatingBar ratingBar;
    float rating;
    UserEntity loginEntity;
    PrefManager prefManager;

    AlertDialog alertDialog;
    CompleteOrderEntity completeOrderEntity;
    ComplOrderAdapter feedbackAdapter;
    List<CompleteOrderEntity> lstCompOrderDetail;
    RateDisplayEntity rateDisplayEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        registerCustomPopUp(RateActivity.this);
        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        lstCompOrderDetail = new ArrayList<CompleteOrderEntity>();
        initialize();
        setOnClickListener();

        showDialog();
        new ProductController(this).getCompleteOrderData(loginEntity.getUser_id(), this);
    }

    private void initialize() {

        cvRating = findViewById(R.id.cvRating);
        ratingBar = findViewById(R.id.ratingBar);
        etBody = findViewById(R.id.etBody);
        etReqName = findViewById(R.id.etReqName);
        lyComment = findViewById(R.id.lyComment);
        ratingBar.setRating(5.0f);
        txtReqestID = findViewById(R.id.txtReqestID);
        txtMessage = findViewById(R.id.txtMessage);

        btnSubmit = findViewById(R.id.btnSubmit);

    }

    private void setOnClickListener() {
        etReqName.setFocusable(false);
        etReqName.setClickable(true);

        etReqName.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    public void requestPopUp(String Title) {

        if (alertDialog != null && alertDialog.isShowing()) {

            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);

        TextView txtHdr;
        ImageView ivCross;

        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.feedback_dialog, null);

        builder.setView(dialogView);
        alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        txtHdr = dialogView.findViewById(R.id.txtHdr);
        RecyclerView rvRTO = dialogView.findViewById(R.id.rvRTO);
        ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

        rvRTO.setLayoutManager(new LinearLayoutManager(this));
        rvRTO.setHasFixedSize(true);
        rvRTO.setNestedScrollingEnabled(false);
        feedbackAdapter = new ComplOrderAdapter(RateActivity.this, lstCompOrderDetail, this);
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


    private boolean validate() {

        if (!isEmpty(etReqName)) {
            etReqName.requestFocus();
            etReqName.setError("Select Request Id");
            return false;
        } else if (ratingBar.getRating() == 0) {
            getCustomToast("Please Rate");
            return false;
        } else if (ratingBar.getRating() < 5) {

            txtMessage.setTextColor(getResources().getColor(R.color.red_nav));
            txtMessage.setText(getResources().getString(R.string.rate_Negative));
            lyComment.setVisibility(View.VISIBLE);
            if (!isEmpty(etBody)) {
                etBody.requestFocus();
                etBody.setError("Enter Comment");
                return false;
            }

        } else if (!isEmpty(etBody) && ratingBar.getRating() < 5) {
            etBody.requestFocus();
            etBody.setError("Enter Comment");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view, RateActivity.this);
        switch (view.getId()) {
            case R.id.btnSubmit:

                if (rateDisplayEntity != null) {
                    return;
                }

                if (validate()) {
                    rating = ratingBar.getRating();
                    RateRequestEntity rateEntity = new RateRequestEntity();
                    rateEntity.setUserid("" + loginEntity.getUser_id());
                    rateEntity.setRequest_id("" + completeOrderEntity.getOrder_id());
                    rateEntity.setRating("" + rating);
                    rateEntity.setComment("" + etBody.getText().toString());

                    showDialog();
                    new RegisterController(this).saveRate(rateEntity, this);

                }

                break;

            case R.id.etReqName:
                if (lstCompOrderDetail.size() > 0) {
                    etReqName.setError(null);
                    lyComment.setVisibility(View.GONE);
                    txtMessage.setTextColor(getResources().getColor(R.color.blue_descent));
                    requestPopUp("Select Request");
                }

                break;

        }

    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof CompleteOrderResponse) {
            if (response.getStatus_code() == 0) {

                lstCompOrderDetail = ((CompleteOrderResponse) response).getData();


            }
        } else if (response instanceof DisplayRateResponse) {
            if (response.getStatus_code() == 0) {

                rateDisplayEntity = ((DisplayRateResponse) response).getData().get(0);
                ratingBar.setRating(rateDisplayEntity.getRating());
                ratingBar.setIsIndicator(true);
                cvRating.setEnabled(false);
                txtMessage.setText("You have already Rated us.");

            } else {
                rateDisplayEntity = null;
                ratingBar.setRating(5.0f);
                ratingBar.setIsIndicator(false);
                cvRating.setEnabled(true);
                txtMessage.setText("");
            }
        } else if (response instanceof RateResponse) {
            if (response.getStatus_code() == 0) {

                if (ratingBar.getRating() == 5.0) {

                    openPopUp(btnSubmit, "Thanks", getResources().getString(R.string.rate_Positive), "OK", "", false, false);
                } else {
                    getCustomToast(response.getMessage());
                    this.finish();
                }


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

    @Override
    public void getOrderData(CompleteOrderEntity tempcompleteOrderEntity) {

        if (alertDialog != null)
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
                completeOrderEntity = tempcompleteOrderEntity;
                txtReqestID.setText("" + completeOrderEntity.getDisplay_order_id());
                etReqName.setText(completeOrderEntity.getProduct_name());
                // showDialog();
                new RegisterController(this).displayRate(loginEntity.getUser_id(), String.valueOf(completeOrderEntity.getOrder_id()), RateActivity.this);
            }
    }

    @Override
    public void onPositiveButtonClick(Dialog dialog, View view) {

        dialog.dismiss();
        this.finish();
    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {
        dialog.dismiss();
        this.finish();
    }
}
