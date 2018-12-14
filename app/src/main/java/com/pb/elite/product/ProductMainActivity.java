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
import com.pb.elite.non_rto_fragments.AnalysisHealthPlanFragment;
import com.pb.elite.non_rto_fragments.BeyondLifeFinancialFragment;
import com.pb.elite.non_rto_fragments.ComplimentaryCreditReportFragment;
import com.pb.elite.non_rto_fragments.ComplimentaryLoanauditFragment;
import com.pb.elite.non_rto_fragments.LICChangeAssistanceFragment;
import com.pb.elite.non_rto_fragments.PUCFragment;
import com.pb.elite.non_rto_fragments.ProvideVehicleDamageFragment;
import com.pb.elite.non_rto_fragments.SpecialHealthTopUpFragment;
import com.pb.elite.non_rto_fragments.TransferNCBFragment;
import com.pb.elite.payment.PaymentRazorActivity;
import com.pb.elite.rto_fragment.AddressEndorsementFragment;
import com.pb.elite.rto_fragment.AssistanObtainFragment;
import com.pb.elite.rto_fragment.DrivingLicVerifyFragment;
import com.pb.elite.rto_fragment.HypotheticalFragment;
import com.pb.elite.rto_fragment.RenewRcFragment;
import com.pb.elite.rto_fragment.SmartCardLicFragment;
import com.pb.elite.rto_fragment.TransferOwnershipFragment;
import com.pb.elite.rto_fragment.VehicleRegistCertificateFragment;
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

            if (productEntity != null)
                loadFragments(getFragmentFromProduct(productEntity));
            else
                getCustomToast("Under construction..");


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
        }else if ((productEntity.getProductcode().equalsIgnoreCase("3.1"))
                || (productEntity.getProductcode().equalsIgnoreCase("3.2"))) {
            HypotheticalFragment hypotheticalFragment = new HypotheticalFragment();
            hypotheticalFragment.setArguments(getBundleRTO());
            return hypotheticalFragment;
        }  else if ((productEntity.getProductcode().equalsIgnoreCase("4.1"))
                || (productEntity.getProductcode().equalsIgnoreCase("4.2"))) {
            TransferOwnershipFragment transferVehicleFragment = new TransferOwnershipFragment();
            transferVehicleFragment.setArguments(getBundleRTO());
            return transferVehicleFragment;
        } else if ((productEntity.getProductcode().equalsIgnoreCase("5"))) {
            DrivingLicVerifyFragment drivingLicVerifyFragment = new DrivingLicVerifyFragment();
            drivingLicVerifyFragment.setArguments(getBundleRTO());
            return drivingLicVerifyFragment;
        }else if ((productEntity.getProductcode().equalsIgnoreCase("6"))) {
            AddressEndorsementFragment addressEndorsementFragment = new AddressEndorsementFragment();
            addressEndorsementFragment.setArguments(getBundleRTO());
            return addressEndorsementFragment;
        } else if ((productEntity.getProductcode().equalsIgnoreCase("7"))) {
            SmartCardLicFragment smartCardLicFragment = new SmartCardLicFragment();
            smartCardLicFragment.setArguments(getBundleRTO());
            return smartCardLicFragment;
        }
        else if ((productEntity.getProductcode().equalsIgnoreCase("8"))) {
            VehicleRegistCertificateFragment certificateFragment = new VehicleRegistCertificateFragment();
            certificateFragment.setArguments(getBundleRTO());
            return certificateFragment;
        }
        /*////////////////////////////////////////////////////////////////////////////////
                                            Miscellaneous
        ///////////////////////////////////////////////////////////////////////////////// */


        else if (productEntity.getProductcode().equalsIgnoreCase("09")  //
                || productEntity.getProductcode().equalsIgnoreCase("09")) {
            ProvideVehicleDamageFragment provideVehicleDamageFragment = new ProvideVehicleDamageFragment();
            provideVehicleDamageFragment.setArguments(getBundleRTO());
            return provideVehicleDamageFragment;
        } else if (productEntity.getProductcode().equalsIgnoreCase("10")) {
            ProvideVehicleDamageFragment provideVehicleDamageFragment = new ProvideVehicleDamageFragment();
            provideVehicleDamageFragment.setArguments(getBundleRTO());
            return provideVehicleDamageFragment;
        } else if (productEntity.getProductcode().equalsIgnoreCase("11")) {
            PUCFragment pucFragment = new PUCFragment();
            pucFragment.setArguments(getBundleRTO());
            return pucFragment;
        } else if (productEntity.getProductcode().equalsIgnoreCase("12")) {
            SpecialHealthTopUpFragment specialHealthTopUpFragment = new SpecialHealthTopUpFragment();
            specialHealthTopUpFragment.setArguments(getBundleRTO());
            return specialHealthTopUpFragment;
        } else if (productEntity.getProductcode().equalsIgnoreCase("13")) {
            TransferNCBFragment transferNCBFragment = new TransferNCBFragment();
            transferNCBFragment.setArguments(getBundleRTO());
            return transferNCBFragment;

        } else if (productEntity.getProductcode().equalsIgnoreCase("14")) {
            AnalysisHealthPlanFragment analysisHealthPlanFragment = new AnalysisHealthPlanFragment();
            analysisHealthPlanFragment.setArguments(getBundleRTO());
            return analysisHealthPlanFragment;

        } else if (productEntity.getProductcode().equalsIgnoreCase("15")) {
            LICChangeAssistanceFragment licChangeAssistanceFragment = new LICChangeAssistanceFragment();
            licChangeAssistanceFragment.setArguments(getBundleRTO());
            return licChangeAssistanceFragment;
        } else if (productEntity.getProductcode().equalsIgnoreCase("16")) {
            BeyondLifeFinancialFragment beyondLifeFinancialFragment = new BeyondLifeFinancialFragment();
            beyondLifeFinancialFragment.setArguments(getBundleRTO());
            return beyondLifeFinancialFragment;

        } else if (productEntity.getProductcode().equalsIgnoreCase("17")) {
            ComplimentaryCreditReportFragment complimentaryCreditReportFragment = new ComplimentaryCreditReportFragment();
            complimentaryCreditReportFragment.setArguments(getBundleRTO());
            return  complimentaryCreditReportFragment;

        } else if (productEntity.getProductcode().equalsIgnoreCase("18")) {
            ComplimentaryLoanauditFragment complimentaryLoanauditFragment = new ComplimentaryLoanauditFragment();
            complimentaryLoanauditFragment.setArguments(getBundleRTO());
            return complimentaryLoanauditFragment;
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

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout, fragment);
            ft.commitAllowingStateLoss();
        } else {
            getCustomToast("Under Construction...");
        }
    }

    //endregion
}
