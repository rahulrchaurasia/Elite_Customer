package com.rb.elite.emailUs;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.rb.elite.core.controller.register.RegisterController;
import com.rb.elite.core.model.UserConstatntEntity;
import com.rb.elite.core.response.UserConsttantResponse;
import com.rb.elite.splash.PrefManager;

public class EmailUsActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber, BaseActivity.CustomPopUpListener {

    LinearLayout lyCalling, lyEmail;
    TextView txtCall ,txtEmail;
    UserConstatntEntity userConstatntEntity;
    PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager = new PrefManager(this);
        userConstatntEntity = prefManager.getUserConstatnt();
        initialize();
        setListner();
        setCallUI();
        if (userConstatntEntity == null) {

            if (prefManager.getUserConstatnt() == null) {
                new RegisterController(this).getUserConstatnt(this);
            }
        }
    }

    private void initialize() {
        lyCalling =  findViewById(R.id.lyCalling);
        lyEmail =  findViewById(R.id.lyEmail);
        txtCall =  findViewById(R.id.txtCall);
        txtEmail =  findViewById(R.id.txtEmail);


    }


    private void setListner() {
        lyCalling.setOnClickListener(this);
        lyEmail.setOnClickListener(this);
    }

    private void setCallUI()
    {
        if(userConstatntEntity != null) {
            txtCall.setText(userConstatntEntity.getContactno());
            txtEmail.setText(userConstatntEntity.getEmailid());
        }
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.lyCalling:

                if (userConstatntEntity != null) {
                    ConfirmAlert("Calling", getResources().getString(R.string.supp_Calling) + " ", userConstatntEntity.getContactno());
                }

                break;


            case R.id.lyEmail:

                if (userConstatntEntity != null) {
                    composeEmail(userConstatntEntity.getEmailid(), "Elite Support Team");
                }
                break;


        }


    }

    public void ConfirmAlert(String Title, String strBody, final String strMobile) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);


        Button btnSubmit;
        TextView txtTile, txtBody, txtMob;
        ImageView ivCross;

        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_calling_popup, null);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        txtTile = (TextView) dialogView.findViewById(R.id.txtTile);
        txtBody = (TextView) dialogView.findViewById(R.id.txtMessage);
        txtMob = (TextView) dialogView.findViewById(R.id.txtOther);
        ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

        btnSubmit = (Button) dialogView.findViewById(R.id.btnSubmit);

        txtTile.setText(Title);
        txtBody.setText(strBody);
        txtMob.setText(strMobile);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                Intent intentCalling = new Intent(Intent.ACTION_DIAL);
                intentCalling.setData(Uri.parse("tel:" + strMobile));
                if (ActivityCompat.checkSelfPermission(EmailUsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intentCalling);

            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();

    }


    @Override
    public void OnSuccess(APIResponse response, String message) {

        if (response instanceof UserConsttantResponse) {

            if (response.getStatus_code() == 0) {

                userConstatntEntity = ((UserConsttantResponse) response).getData().get(0);
                if (userConstatntEntity != null) {
                    setCallUI();
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
    public void onPositiveButtonClick(Dialog dialog, View view) {
        dialog.cancel();
        openAppSetting();
    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {
        dialog.cancel();
    }
}
