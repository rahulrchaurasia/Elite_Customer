package com.pb.elite.non_rto_fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.utility.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnalysisHealthPlanFragment extends BaseFragment implements View.OnClickListener {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Context mContext;
    EditText etDOB;

    public AnalysisHealthPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis_health_plan, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();

        init(view);

        setListener();

        super.onViewCreated(view, savedInstanceState);

    }

    private void setListener() {
        etDOB.setOnClickListener(this);

    }

    private void init(View view) {
        etDOB = view.findViewById(R.id.etDOB);
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
        }

    }
}