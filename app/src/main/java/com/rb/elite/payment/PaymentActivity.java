package com.rb.elite.payment;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rb.elite.BaseActivity;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.product.ProductController;
import com.rb.elite.core.model.OrderEntity;
import com.rb.elite.core.requestmodel.UpdateOrderRequestEntity;
import com.rb.elite.core.response.ClientCommonResponse;
import com.rb.elite.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;

public class PaymentActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber {

    ImageView ivTick;
    Button btnHome;
    TextView tvPaymentStatus, tvPaymentDetails;
    UpdateOrderRequestEntity updateOrderRequestEntity;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    LinearLayout llOrderId;
    TextView tvOrderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        // Call the function callInstamojo to start payment here
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initWidgets();
        setListeners();
        orderEntity = getIntent().getParcelableExtra("ORDER");


        updateOrderRequestEntity = new UpdateOrderRequestEntity();
        if (orderEntity != null) {
            callInsFtamojoPay(orderEntity.getEmail(), orderEntity.getMobile(), orderEntity.getAmount(),
                    "" + orderEntity.getId(), orderEntity.getName());
        }
        // extractResponse("status=success:orderId=33a61edd737647b5a2529c942d335183:txnId=None:paymentId=MOJO8205005W35633327:token=pHsTKpFCePgTjGrzZV3aa9RXJSu5de");

    }

    private void extractResponse(String response) {
        String[] responseKeys = response.split(":");

        String status[] = responseKeys[0].split("=");
        String statusValue = status[1];

        String orderId[] = responseKeys[1].split("=");
        String orderIdValue = orderId[1];

        String txnId[] = responseKeys[2].split("=");
        String txnIdValue = txnId[1];

        String token[] = responseKeys[3].split("=");
        String tokenValue = token[1];

    }

    private void setListeners() {
        btnHome.setOnClickListener(this);
    }

    private void initWidgets() {
        ivTick = (ImageView) findViewById(R.id.ivTick);
        btnHome = (Button) findViewById(R.id.btnHome);
        tvPaymentStatus = (TextView) findViewById(R.id.tvPaymentStatus);
        tvPaymentDetails = (TextView) findViewById(R.id.tvPaymentDetails);
        llOrderId = (LinearLayout) findViewById(R.id.llOrderId);
        tvOrderId = (TextView) findViewById(R.id.tvOrderId);
    }

    private void callInsFtamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            // pay.put("amount", amount);
            pay.put("amount", Constants.PAYMENT_AMOUNT);
            pay.put("name", buyername);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;
    OrderEntity orderEntity;

    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                try {

                    //response   ---
                    //status=success:orderId=33a61edd737647b5a2529c942d335183:txnId=None:paymentId=MOJO8205005W35633327:token=pHsTKpFCePgTjGrzZV3aa9RXJSu5de

                    String[] responseKeys = response.split(":");

                    String status[] = responseKeys[0].split("=");
                    String statusValue = status[1];

                    String orderId[] = responseKeys[1].split("=");
                    String orderIdValue = orderId[1];

                    String txnId[] = responseKeys[2].split("=");
                    String txnIdValue = txnId[1];

                    String payment[] = responseKeys[3].split("=");
                    String paymentValue = payment[1];

                    String token[] = responseKeys[4].split("=");
                    String tokenValue = token[1];

                    ivTick.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    tvPaymentStatus.setText("Payment Success");
                    tvPaymentDetails.setText("Your Payment of " + "\u20B9" + orderEntity.getAmount() + "\n" + "was successfully completed.");
                    tvOrderId.setText(orderIdValue);
                    llOrderId.setVisibility(View.VISIBLE);

                    updateOrderRequestEntity.setOrder_id(orderEntity.getId());
                    updateOrderRequestEntity.setCode("");
                    updateOrderRequestEntity.setReason("");
                    updateOrderRequestEntity.setTransaction_id(paymentValue);//transaction id Payment Id same in database
                    updateOrderRequestEntity.setStatus(Constants.PAYMENT_SUCCESS);

                    updateOrderRequestEntity.setPg_order_id(orderIdValue);
                    updateOrderRequestEntity.setPg_status(statusValue);
                    updateOrderRequestEntity.setPg_paymentId(paymentValue);
                    updateOrderRequestEntity.setPg_token_value(tokenValue);
                    updateOrderRequestEntity.setPg_transaction_id(txnIdValue);

                    updateOrderRequestEntity.setRemark(response);
                    //Calendar calendar = Calendar.getInstance().getTime();
                    String currentDayTime = simpleDateFormat.format(Calendar.getInstance().getTime());
                    updateOrderRequestEntity.setPayment_datetime(currentDayTime);
                    showDialog();
                    new ProductController(PaymentActivity.this).updateOrderId(updateOrderRequestEntity, PaymentActivity.this);
                } catch (Exception e) {
                    updateOrderRequestEntity.setOrder_id(orderEntity.getId());
                    updateOrderRequestEntity.setCode("");
                    updateOrderRequestEntity.setReason("");
                    updateOrderRequestEntity.setStatus(Constants.PAYMENT_SUCCESS); // payment success
                    String currentDayTime = simpleDateFormat.format(Calendar.getInstance().getTime());
                    updateOrderRequestEntity.setPayment_datetime(currentDayTime);
                    updateOrderRequestEntity.setRemark(response);
                    showDialog();
                    new ProductController(PaymentActivity.this).updateOrderId(updateOrderRequestEntity, PaymentActivity.this);
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onFailure(int code, String reason) {

                updateOrderRequestEntity.setOrder_id(orderEntity.getId());
                updateOrderRequestEntity.setCode("" + code);
                updateOrderRequestEntity.setReason(reason);
                updateOrderRequestEntity.setTransaction_id("");
                updateOrderRequestEntity.setStatus(Constants.PAYMENT_FAILURE);
                updateOrderRequestEntity.setRemark("Payment Failed");
                //Calendar calendar = Calendar.getInstance().getTime();
                String currentDayTime = simpleDateFormat.format(Calendar.getInstance().getTime());
                updateOrderRequestEntity.setPayment_datetime(currentDayTime);
                showDialog();
                new ProductController(PaymentActivity.this).updateOrderId(updateOrderRequestEntity, PaymentActivity.this);

                ivTick.setImageDrawable(getResources().getDrawable(R.drawable.cross_with_circle));
                tvPaymentStatus.setText("Payment Failed");
                tvPaymentDetails.setText(reason);
                llOrderId.setVisibility(View.GONE);
                //Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG).show();

            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHome:
                this.finish();
                break;
        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
        if (response instanceof ClientCommonResponse) {
            cancelDialog();
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
