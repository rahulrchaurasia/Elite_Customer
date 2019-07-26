package com.rb.elite.register;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.rb.elite.BaseActivity;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.register.RegisterController;
import com.rb.elite.core.model.PolicyEntity;
import com.rb.elite.core.response.PolicyResponse;

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
        etpolicyVeh_no.setFilters( new InputFilter[]{
                new InputFilter.AllCaps(),
                new InputFilter.LengthFilter(30),
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if(source.equals(" ")){ // for backspace
                            return source;
                        }
                        if(source.toString().matches("^[a-zA-Z0-9]+$")){
                            return source;
                        }
                        return "";
                    }
                }
        });
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
                PolicyEntity policyEntity =  ((PolicyResponse) response).getData().get(0);

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
        getCustomToast(t.getMessage());
    }
}
