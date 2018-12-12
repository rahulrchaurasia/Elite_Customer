package com.pb.elite.non_rto_fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.utility.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ComplimentaryloanauditFragment extends BaseFragment implements View.OnClickListener {



    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Context mContext;
    EditText etDOB,etemi, etName;
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
        if (v.getId() == R.id.etDOB) {

            DateTimePicker.showOpenDatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                    if (view1.isShown()) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        String currentDay = simpleDateFormat.format(calendar.getTime());
                        etDOB.setText(currentDay);
                    }
                }
            });
        } else if (v.getId() == R.id.etHospitalizationDate) {

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();

        init(view);

        setListener();

        super.onViewCreated(view, savedInstanceState);

    }

    private void init(View view) {
        etemi = view.findViewById(R.id.etemi);
        etDOB= view.findViewById(R.id.etDOB);
        etName = view.findViewById(R.id.etName);
        submitbtn = view.findViewById(R.id.submitbtn);
    }

    private void setListener() {
        etDOB.setOnClickListener(this);

    }

}
