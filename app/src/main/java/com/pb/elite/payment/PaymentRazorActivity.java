package com.pb.elite.payment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.requestmodel.InsertOrderRequestEntity;
import com.pb.elite.core.response.OrderResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.document.DocUploadActivity;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentRazorActivity extends BaseActivity implements PaymentResultListener, IResponseSubcriber, View.OnClickListener {

    private static final String TAG = "RAZOR_PAYMRNT";
    int OrderID = 0;
    long AMOUNT_PAYMENT = 0;
    String PRODUCT_NAME = "";
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    TextView txtprdName, txtAmount;
    Button btnSubmit;
    InsertOrderRequestEntity orderRequestEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_razor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dataBaseController = new DataBaseController(PaymentRazorActivity.this);
        loginEntity = dataBaseController.getUserData();

        OrderID = 0;

        initialize();

        if (getIntent().hasExtra("PRODUCT_NAME_PAYMENT")) {

            orderRequestEntity = getIntent().getExtras().getParcelable("PRODUCT_NAME_PAYMENT");

            PRODUCT_NAME = orderRequestEntity.getProdName();
            AMOUNT_PAYMENT = (Long.valueOf(orderRequestEntity.getAmount()));
            txtAmount.setText("" + "\u20B9" + " " + AMOUNT_PAYMENT);
            txtprdName.setText(PRODUCT_NAME);


        }
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//
//            PRODUCT_NAME = extras.getString("PRODUCT_NAME_PAYMENT");
//            PRODUCT_ID = extras.getInt("PRODUCT_NAME_PAYMENT",0);
//            RTO_ID = extras.getString("RTO_ID", "0");
//            AMOUNT_PAYMENT = (Long.valueOf(extras.getString("AMOUNT_PAYMENT", "0"))) ;
//            txtAmount.setText("" + "\u20B9" + " " + AMOUNT_PAYMENT);
//            txtprdName.setText(PRODUCT_NAME);
//        }


        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());


        btnSubmit.setOnClickListener(this);
    }

    private void initialize() {
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        txtprdName = (TextView) findViewById(R.id.txtprdName);
        txtAmount = (TextView) findViewById(R.id.txtAmount);


    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", PRODUCT_NAME);
            options.put("description", "");
            //You can omit the image option to fetch the image from dashboard
            //  options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "100");

            JSONObject preFill = new JSONObject();
            preFill.put("email", loginEntity.getEmail());
            preFill.put("contact", loginEntity.getMobile());


            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            //  Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();


            orderRequestEntity.setPayment_status("1");
            orderRequestEntity.setTransaction_id(razorpayPaymentID);
            showDialog();
            new ProductController(PaymentRazorActivity.this).inserOrderData(orderRequestEntity, this);


        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }

        //  showDialog();
        //  new ProductController(PaymentRazorActivity.this).inserOrderData(productEntity.getId(), loginEntity.getUser_id(),Integer.valueOf(RTO_ID), this);

    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
//            orderRequestEntity.setPayment_status("0");
//            orderRequestEntity.setTransaction_id("");
//            showDialog();
//            new ProductController(PaymentRazorActivity.this).inserOrderData(orderRequestEntity, this);

        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnSubmit) {
            startPayment();
        }
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
        finish();
        super.onBackPressed();
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof OrderResponse) {
            if (response.getStatus_code() == 0) {

                OrderID = (((OrderResponse) response).getData().get(0).getId());

                showPaymentAlert(btnSubmit, response.getMessage().toString(), OrderID);

            }

        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
    }

    //  @Override
//    public void onPositiveButtonClick(View view) {
//
//        if (view.getId() == R.id.btn_pay) {
//
//            if(OrderID > 0) {
//                startActivity(new Intent(PaymentRazorActivity.this, DocUploadActivity.class)
//                             .putExtra("ORDER_ID",OrderID));
//                this.finish();
//            }
//        }
//    }


    public void showPaymentAlert(final View view, String strBody, final int OrderID) {
        try {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle("Elite");

            builder.setMessage(strBody);
            String positiveText = "Ok";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            startActivity(new Intent(PaymentRazorActivity.this, DocUploadActivity.class)
                                    .putExtra("ORDER_ID", OrderID));

                            PaymentRazorActivity.this.finish();


                        }
                    });
            final android.support.v7.app.AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception ex) {
            Toast.makeText(this, "Please try again..", Toast.LENGTH_SHORT).show();
        }
    }


}
