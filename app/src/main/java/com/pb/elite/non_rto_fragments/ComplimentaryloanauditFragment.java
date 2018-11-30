package com.pb.elite.non_rto_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pb.elite.BaseFragment;
import com.pb.elite.R;


public class ComplimentaryloanauditFragment extends BaseFragment implements View.OnClickListener {




    private Context mContext;
    EditText etpanNo, etName;
    Button submitbtn;

    public ComplimentaryloanauditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complimentary_loan_audit, container, false);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.etHospitalizationDate) {

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();

        init(view);

     //   setListener();

        super.onViewCreated(view, savedInstanceState);

    }
    private void init(View view) {
        etpanNo = view.findViewById(R.id.etpanNo);
        etName = view.findViewById(R.id.etName);
        submitbtn = view.findViewById(R.id.submitbtn);
    }

}
