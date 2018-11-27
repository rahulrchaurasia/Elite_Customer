package com.pb.elite.feedback;

import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.utility.Constants;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

   // etBody   btnSubmit
    EditText etBody;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initialize();
    }

    private void initialize()
    {
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        etBody = (EditText) findViewById(R.id.etBody);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Constants.hideKeyBoard(view,FeedbackActivity.this);
        switch (view.getId()) {
            case R.id.btnSubmit:

                if(!isEmpty(etBody))
                {
                    getCustomToast("Please enter your feedback");
                    return;
                }

                getCustomToast("FeedBack Save Successfully");
                etBody.setText("");
                break;
        }


        }
}
