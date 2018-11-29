package com.pb.elite.product;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.core.requestmodel.InsertOrderRequestEntity;
import com.pb.elite.non_rto_fragments.ProvideVehicleDamageFragment;
import com.pb.elite.payment.PaymentRazorActivity;
import com.pb.elite.rto_fragment.AssistanObtainFragment;
import com.pb.elite.rto_fragment.RenewRcFragment;
import com.pb.elite.utility.Constants;

public class ProductMainActivity extends BaseActivity {

    String SERVICE_TYPE;
    RTOServiceEntity productEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (getIntent().hasExtra(Constants.SERVICE_TYPE)) {
                SERVICE_TYPE = extras.getString(Constants.SERVICE_TYPE, "");
            }
            if (getIntent().hasExtra(Constants.RTO_PRODUCT_DATA)) {
                productEntity = extras.getParcelable(Constants.RTO_PRODUCT_DATA);
            } else if (getIntent().hasExtra(Constants.NON_RTO_PRODUCT_DATA)) {
                productEntity = extras.getParcelable(Constants.NON_RTO_PRODUCT_DATA);
            } else if (getIntent().hasExtra(Constants.SUB_PRODUCT_DATA)) {
                productEntity = extras.getParcelable(Constants.SUB_PRODUCT_DATA);
            }

            loadFragments(getFragmentFromProduct(productEntity));


        }

    }


    private Fragment getFragmentFromProduct(RTOServiceEntity productEntity) {

        if ((productEntity.getProductcode().equalsIgnoreCase("1.1"))
                || (productEntity.getProductcode().equalsIgnoreCase("1.2"))
                || (productEntity.getProductcode().equalsIgnoreCase("1.3"))) {
            RenewRcFragment rcFragment = new RenewRcFragment();
            rcFragment.setArguments(getBundleRTO());
            return rcFragment;

        } else if ((productEntity.getProductcode().equalsIgnoreCase("2.1"))
                || (productEntity.getProductcode().equalsIgnoreCase("2.2"))
                || (productEntity.getProductcode().equalsIgnoreCase("2.3"))
                || (productEntity.getProductcode().equalsIgnoreCase("2.4"))) {
            AssistanObtainFragment obtainFragment = new AssistanObtainFragment();
            obtainFragment.setArguments(getBundleRTO());
            return obtainFragment;
        } else if (productEntity.getProductcode().equalsIgnoreCase("09")  //Miscellaneous
                || productEntity.getProductcode().equalsIgnoreCase("09")) {
            return new ProvideVehicleDamageFragment();
        } else if (productEntity.getProductcode().equalsIgnoreCase("10")) {
            return new ProvideVehicleDamageFragment();
        } else if (productEntity.getProductcode().equalsIgnoreCase("11")) {
            return new ProvideVehicleDamageFragment();
        } else if (productEntity.getProductcode().equalsIgnoreCase("12")) {
            return new ProvideVehicleDamageFragment();
        } else if (productEntity.getProductcode().equalsIgnoreCase("13")) {
            return new ProvideVehicleDamageFragment();
        } else if (productEntity.getProductcode().equalsIgnoreCase("14")) {
            return new ProvideVehicleDamageFragment();
        } else if (productEntity.getProductcode().equalsIgnoreCase("15")) {
            return new ProvideVehicleDamageFragment();
        } else if (productEntity.getProductcode().equalsIgnoreCase("16")) {
            return new ProvideVehicleDamageFragment();
        } else if (productEntity.getProductcode().equalsIgnoreCase("17")) {
            return new ProvideVehicleDamageFragment();
        } else if (productEntity.getProductcode().equalsIgnoreCase("18")) {
            return new ProvideVehicleDamageFragment();
        }

        return null;
    }


    private Bundle getBundleRTO() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.SUB_PRODUCT_DATA, productEntity);
        return bundle;
    }


    public void sendPaymentRequest(InsertOrderRequestEntity requestEntity) {

        startActivity(new Intent(ProductMainActivity.this, PaymentRazorActivity.class)
                .putExtra("PRODUCT_NAME_PAYMENT", requestEntity));
        this.finish();
    }


    public void downloadPdf(String url, String name) {
        Toast.makeText(this, "Download started..", Toast.LENGTH_LONG).show();
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name + ".pdf");
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        r.setMimeType(MimeTypeMap.getFileExtensionFromUrl(url));
        DownloadManager dm = (DownloadManager) this.getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }


    //region load fragment

    private void loadFragments(Fragment fragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.commitAllowingStateLoss();
    }

    //endregion
}
