package com.pb.elite.rto_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.utility.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferVehicleFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer_vehicle, container, false);


        return view;
    }

}
