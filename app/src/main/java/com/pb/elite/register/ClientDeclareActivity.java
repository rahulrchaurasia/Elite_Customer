package com.pb.elite.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.register.RegisterController;
import com.pb.elite.core.model.PolicyEntity;
import com.pb.elite.core.response.PolicyResponse;
import com.pb.elite.login.loginActivity;

import java.util.List;

public class ClientDeclareActivity extends BaseActivity implements IResponseSubcriber, View.OnClickListener {

    EditText  etpolicyVeh_no;
    Button btnSubmit;
    LinearLayout lyOther;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_declare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        setListener();

    }

    private void init()
    {
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        lyOther = (LinearLayout) findViewById(R.id.lyOther);
        etpolicyVeh_no = (EditText) findViewById(R.id.etpolicyVeh_no);
        etpolicyVeh_no.setFocusable(false);


    }

    private void setListener()
    {
        lyOther.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        etpolicyVeh_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                etpolicyVeh_no.setFocusableInTouchMode(true);

                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSubmit:
                if (!isEmpty(etpolicyVeh_no)) {
                    etpolicyVeh_no.requestFocus();
                   // etpolicyVeh_no.setError("Enter Policy Number");
                    getCustomToast("Enter Reliance Policy Number");
                    return;
                }

                showDialog();
                new RegisterController(this).getPolicyData(etpolicyVeh_no.getText().toString(), ClientDeclareActivity.this);
                break;

            case R.id.lyOther:
                startActivity(new Intent(ClientDeclareActivity.this, SignUpActivity.class));
                this.finish();
                break;
        }

    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof PolicyResponse) {

            if (response.getStatus_code() == 0) {
                PolicyEntity policyEntity =  ((PolicyResponse) response).getData();

                Intent intent = new Intent(ClientDeclareActivity.this, SignUpActivity.class);
                intent.putExtra("POLICY_DATA",policyEntity);
                startActivity(intent);
                this.finish();

            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
    }
}
