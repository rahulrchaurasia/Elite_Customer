package com.rb.elite.product;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.rb.elite.BaseActivity;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.product.ProductController;
import com.rb.elite.core.model.RTOServiceEntity;
import com.rb.elite.core.requestmodel.InsertOrderRequestEntity;
import com.rb.elite.core.response.ProductDocumentResponse;
import com.rb.elite.non_rto_fragments.AnalysisHealthPlanFragment;
import com.rb.elite.non_rto_fragments.BeyondLifeFinancialFragment;
import com.rb.elite.non_rto_fragments.ComplimentaryCreditReportFragment;

import com.rb.elite.non_rto_fragments.ComplimentaryLoanAuditFragment;
import com.rb.elite.non_rto_fragments.LICChangeAssistanceFragment;
import com.rb.elite.non_rto_fragments.PUCFragment;
import com.rb.elite.non_rto_fragments.ProvideVehicleDamageFragment;
import com.rb.elite.non_rto_fragments.SpecialHealthTopUpFragment;
import com.rb.elite.non_rto_fragments.TransferNCBFragment;
import com.rb.elite.payment.PaymentRazorActivity;
import com.rb.elite.rto_fragment.AddressEndorsementFragment;
import com.rb.elite.rto_fragment.AssistanObtainFragment;
import com.rb.elite.rto_fragment.DrivingLicVerifyFragment;
import com.rb.elite.rto_fragment.HypotheticalFragment;
import com.rb.elite.rto_fragment.RenewRcFragment;
import com.rb.elite.rto_fragment.SmartCardLicFragment;
import com.rb.elite.rto_fragment.TransferOwnershipFragment;
import com.rb.elite.rto_fragment.VehicleRegistCertificateFragment;
import com.rb.elite.utility.Constants;

public class ProductMainActivity extends BaseActivity implements IResponseSubcriber {

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


        else if (productEntity.getProductcode().equalsIgnoreCase("09")) {
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
            ComplimentaryLoanAuditFragment complimentaryLoanauditFragment = new ComplimentaryLoanAuditFragment();
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


    public void getProducDoc(int PRODUCT_ID) {

        showDialog();
        new ProductController(this).getProducDoc(PRODUCT_ID, ProductMainActivity.this);
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

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();

        if (response instanceof ProductDocumentResponse) {

            if (response.getStatus_code() == 0) {

                if (((ProductDocumentResponse) response).getData() != null) {
                    reqDocPopUp(((ProductDocumentResponse) response).getData());
                } else {

                    getCustomToast("No Data Available");
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

    //endregion
}
