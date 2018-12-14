package com.pb.elite.non_rto_fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.utility.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferNCBFragment extends BaseFragment implements View.OnClickListener {

    // Service 13
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Context mContext;
    EditText etDate, etTime;

    public TransferNCBFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer_ncb, container, false);
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
        etDate.setOnClickListener(this);
        etTime.setOnClickListener(this);
    }

    private void init(View view) {
        etDate = view.findViewById(R.id.etDate);
        etTime = view.findViewById(R.id.etTime);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.etDate) {

            DateTimePicker.showOpenDatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                    if (view1.isShown()) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        String currentDay = simpleDateFormat.format(calendar.getTime());
                        etDate.setText(currentDay);
                    }
                }
            });
        } else if (v.getId() == R.id.etTime) {

            DateTimePicker.showTimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (view.isShown()) {
                        String item = "";

                        if (hourOfDay >= 12 && minute > 0)
                            item = " PM";
                        else
                            item = " AM";


                        etTime.setText("" + hourOfDay + " : " + minute + item);

                    }
                }
            });
        }

    }
}
