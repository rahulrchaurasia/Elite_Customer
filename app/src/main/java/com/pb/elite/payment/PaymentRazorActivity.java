package com.pb.elite.payment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.pb.elite.core.controller.rto_service.RTOController;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.requestmodel.AdditionHypothecationRequestEntity;
import com.pb.elite.core.requestmodel.AddressEndorsementRCRequestEntity;
import com.pb.elite.core.requestmodel.AssistanceObtainingRequestEntity;
import com.pb.elite.core.requestmodel.DriverDLVerificationRequestEntity;
import com.pb.elite.core.requestmodel.InsertOrderRequestEntity;
import com.pb.elite.core.requestmodel.PaperToSmartCardRequestEntity;
import com.pb.elite.core.requestmodel.RCRequestEntity;
import com.pb.elite.core.requestmodel.TransferOwnershipRequestEntity;
import com.pb.elite.core.requestmodel.VehicleRegCertificateRequestEntity;
import com.pb.elite.core.response.OrderResponse;
import com.pb.elite.core.response.ProvideClaimAssResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.document.DocUploadActivity;
import com.pb.elite.product.ProductActivity;
import com.pb.elite.utility.Constants;
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
    TextView txtprdName, txtAmount,txtName;
    Button btnSubmit;
    InsertOrderRequestEntity orderRequestEntity;

    String REQUEST_TYPE = "";
    // Service 1
    RCRequestEntity rcRequestEntity;
    AssistanceObtainingRequestEntity assistanceRequestEntity;
    AdditionHypothecationRequestEntity additionHypothecationRequestEntity;
    TransferOwnershipRequestEntity transferOwnershipRequestEntity;

    DriverDLVerificationRequestEntity driverDLRequestEntity;
    AddressEndorsementRCRequestEntity addressEndRequestEntity;
    PaperToSmartCardRequestEntity paperRequestEntity;
    VehicleRegCertificateRequestEntity vehicleRegRequestEntity;

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

       txtName.setText(""+loginEntity.getName());

        Bundle extras = getIntent().getBundleExtra(Constants.PAYMENT_REQUEST_BUNDLE);
        if (extras != null) {
             REQUEST_TYPE = extras.getString(Constants.REQUEST_TYPE, "");

            if(REQUEST_TYPE.equals("1"))
            {
                // Service 1
                rcRequestEntity  =  extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = rcRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(rcRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }else if(REQUEST_TYPE.equals("2"))
            {
                // Service 2
                assistanceRequestEntity  =  extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = assistanceRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(assistanceRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);
            }else if(REQUEST_TYPE.equals("3"))
            {
                // Service 3
                additionHypothecationRequestEntity  = extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = additionHypothecationRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(additionHypothecationRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }else if(REQUEST_TYPE.equals("4"))
            {
                // Service 4
                transferOwnershipRequestEntity  = extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = transferOwnershipRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(transferOwnershipRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }else if(REQUEST_TYPE.equals("5"))
            {
                // Service 5
                driverDLRequestEntity   = extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = driverDLRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(driverDLRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }else if(REQUEST_TYPE.equals("6"))
            {
                // Service 6
                addressEndRequestEntity  = extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = addressEndRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(addressEndRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }else if(REQUEST_TYPE.equals("7"))
            {
                // Service 7
                paperRequestEntity  = extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = paperRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(paperRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }else if(REQUEST_TYPE.equals("8"))
            {
                // Service 8
                vehicleRegRequestEntity  = extras.getParcelable(Constants.PRODUCT_PAYMENT_REQUEST);
                PRODUCT_NAME = vehicleRegRequestEntity.getProdName();
                AMOUNT_PAYMENT = (Long.valueOf(vehicleRegRequestEntity.getAmount()));
                txtAmount.setText("Charges - " + "\u20B9" + " " + AMOUNT_PAYMENT);
                txtprdName.setText(PRODUCT_NAME);

            }

        }


        Checkout.preload(getApplicationContext());


        btnSubmit.setOnClickListener(this);
    }

    private void initialize() {
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        txtprdName = (TextView) findViewById(R.id.txtprdName);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        txtName  = (TextView) findViewById(R.id.txtName);

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

            if(REQUEST_TYPE.equals("1"))
            {
                // Service 1
                rcRequestEntity.setPayment_status("1");
                rcRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveRCService1(rcRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("2"))
            {
                // Service 2
                assistanceRequestEntity.setPayment_status("1");
                assistanceRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveAssistanceObtaining(assistanceRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("3"))
            {
                // Service 3
                additionHypothecationRequestEntity.setPayment_status("1");
                additionHypothecationRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveAdditionHypothecation(additionHypothecationRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("4"))
            {
                // Service 4
                transferOwnershipRequestEntity.setPayment_status("1");
                transferOwnershipRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveTransferOwnership(transferOwnershipRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("5"))
            {
                // Service 5
                driverDLRequestEntity.setPayment_status("1");
                driverDLRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveDriverDLVerification(driverDLRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("6"))
            {
                // Service 6
                addressEndRequestEntity.setPayment_status("1");
                addressEndRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveAddressEndorsementRC(addressEndRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("7"))
            {
                // Service 7
                paperRequestEntity.setPayment_status("1");
                paperRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).savePaperSmartCard(paperRequestEntity,this);
            }else  if(REQUEST_TYPE.equals("8"))
            {
                // Service 7
                vehicleRegRequestEntity.setPayment_status("1");
                vehicleRegRequestEntity.setTransaction_id(razorpayPaymentID);
                showDialog();
                new RTOController(this).saveVehicleRegCertificate(vehicleRegRequestEntity,this);
            }



            //vehicleRegRequestEntity



        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }


    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();

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
        if (response instanceof ProvideClaimAssResponse) {
            if (response.getStatus_code() == 0) {

                OrderID = (((ProvideClaimAssResponse) response).getData().get(0).getId());
                String DisplayMessage = (((ProvideClaimAssResponse) response).getData().get(0).getDisplaymessage());
                showPaymentAlert(btnSubmit, response.getMessage().toString(),DisplayMessage, OrderID);

            }

        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
    }




    public void showPaymentAlert(final View view, String strhdr, String DisplayMessage, final int OrderID) {

        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentRazorActivity.this, R.style.CustomDialog);


        Button btnClose;
        TextView txtHdr ,txtMessage;


        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_success_message, null);


        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        btnClose = (Button) dialogView.findViewById(R.id.btnClose);
        txtMessage = (TextView) dialogView.findViewById(R.id.txtMessage);
        txtHdr = (TextView) dialogView.findViewById(R.id.txtHdr);

        txtHdr.setText(""+ strhdr);
        txtMessage.setText("" + DisplayMessage);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startActivity(new Intent(PaymentRazorActivity.this, DocUploadActivity.class)
                        .putExtra("ORDER_ID", OrderID));

                PaymentRazorActivity.this.finish();


            }
        });


        alertDialog.setCancelable(false);

        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);


    }
}
