package com.pb.elite.dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.feedback.FeedbackActivity;
import com.pb.elite.orderDetail.OrderActivity;
import com.pb.elite.servicelist.Activity.ServiceActivity;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends BaseFragment implements View.OnClickListener {


    BannerSlider bannerSlider;
    List<Banner> banners;
    CardView cvService, cvRequest,cvfeedback;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);

        initialize(view);
        setBanner();
        setListnner();
        return view;
    }

    private void initialize(View view) {


        bannerSlider = (BannerSlider) view.findViewById(R.id.banner_slider1);
        cvService = (CardView) view.findViewById(R.id.cvService);
        cvRequest = (CardView) view.findViewById(R.id.cvRequest);
        cvfeedback = (CardView) view.findViewById(R.id.cvfeedback);

    }

    private void setBanner() {
        banners = new ArrayList<>();

        banners.add(new DrawableBanner(R.drawable.banner1));
        banners.add(new DrawableBanner(R.drawable.banner2));
        banners.add(new DrawableBanner(R.drawable.banner3));

        bannerSlider.setBanners(banners);
    }

    private void setListnner() {

        cvService.setOnClickListener(this);
        cvRequest.setOnClickListener(this);
        cvfeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {


            case R.id.cvService:

                startActivity(new Intent(getActivity(), ServiceActivity.class));
                break;

            case R.id.cvRequest:

                startActivity(new Intent(getActivity(), OrderActivity.class));
                break;

            case R.id.cvfeedback:

                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;

        }

    }
}
