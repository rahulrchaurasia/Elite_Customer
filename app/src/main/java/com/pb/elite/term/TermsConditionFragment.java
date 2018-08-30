package com.pb.elite.term;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pb.elite.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsConditionFragment extends Fragment {


    public TermsConditionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_terms_condition, container, false);

        View view = inflater.inflate(R.layout.fragment_terms_condition, container, false);
        initialize(view);


        return view;
    }

    private void initialize(View view) {


    }

}
