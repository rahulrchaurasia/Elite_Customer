package com.pb.elite.rto_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pb.elite.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleRegistCertificateFragment extends Fragment {


    public VehicleRegistCertificateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_regist_certificate, container, false);
    }

}
