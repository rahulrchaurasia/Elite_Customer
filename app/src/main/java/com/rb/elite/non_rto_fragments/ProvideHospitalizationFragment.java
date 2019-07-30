package com.rb.elite.non_rto_fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.rb.elite.BaseFragment;
import com.rb.elite.R;
import com.rb.elite.utility.Constants;
import com.rb.elite.utility.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProvideHospitalizationFragment extends BaseFragment implements View.OnClickListener {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Context mContext;
    EditText etHospitalizationDate;

    public ProvideHospitalizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_provide_hospitalization, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();

//        init(view);
//
//        setListener();

        super.onViewCreated(view, savedInstanceState);

    }

    private void setListener() {
        etHospitalizationDate.setOnClickListener(this);

    }

    private void init(View view) {
        etHospitalizationDate = view.findViewById(R.id.etHospitalizationDate);
    }

    @Override
    public void onClick(View v) {

        Constants.hideKeyBoard(v,mContext);
        if (v.getId() == R.id.etHospitalizationDate) {

            DateTimePicker.showOpenDatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                    if (view1.isShown()) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        String currentDay = simpleDateFormat.format(calendar.getTime());
                        etHospitalizationDate.setText(currentDay);
                    }
                }
            });
        }

    }
}
