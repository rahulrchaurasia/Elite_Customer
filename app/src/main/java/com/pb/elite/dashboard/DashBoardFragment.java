package com.pb.elite.dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pb.elite.BaseFragment;
import com.pb.elite.R;
import com.pb.elite.feedback.FeedbackActivity;
import com.pb.elite.orderDetail.OrderActivity;
import com.pb.elite.servicelist.Activity.ServiceActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends BaseFragment implements View.OnClickListener {


    ViewPager viewPager;
    List<Integer> banners;
    CardView cvService, cvRequest, cvfeedback;
    CustomPagerAdapter mBannerAdapter;
    CirclePageIndicator circlePageIndicator;

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


        viewPager = (ViewPager) view.findViewById(R.id.pager);
        circlePageIndicator = (CirclePageIndicator) view.findViewById(R.id.titles);

        cvService = (CardView) view.findViewById(R.id.cvService);
        cvRequest = (CardView) view.findViewById(R.id.cvRequest);
        cvfeedback = (CardView) view.findViewById(R.id.cvfeedback);

    }

    private void setBanner() {
        banners = new ArrayList<>();

        banners.add(R.drawable.banner1);
        banners.add(R.drawable.banner2);
        banners.add(R.drawable.banner3);
        mBannerAdapter = new CustomPagerAdapter(getContext(), banners);


        if (viewPager != null && circlePageIndicator != null) {
            viewPager.setAdapter(mBannerAdapter);
            circlePageIndicator.setViewPager(viewPager);

            Timer timer = new Timer();
            timer.schedule(new RemindTask(banners.size(), viewPager), 0, 1500);

        }
    }

    class RemindTask extends TimerTask {
        private int numberOfPages;
        private ViewPager mViewPager;
        private int page = 0;

        public RemindTask(int numberOfPages, ViewPager mViewPager) {
            this.numberOfPages = numberOfPages;
            this.mViewPager = mViewPager;
        }

        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (page > numberOfPages) { // In my case the number of pages are 5
                            mViewPager.setCurrentItem(0);
                            page = 0;
                        } else {
                            mViewPager.setCurrentItem(page++);
                        }
                    }
                });
            }

        }
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
