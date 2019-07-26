package com.rb.elite.dashboard;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rb.elite.R;

import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<Integer> mResources;
    Fragment mfragment;

    public CustomPagerAdapter(Context context,Fragment fragment, List<Integer> mResources) {
        mContext = context;
        mfragment = fragment;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mResources = mResources;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ((ImageView) itemView.findViewById(R.id.img)).setImageResource(mResources.get(position));
        container.addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((DashBoardFragment)mfragment).redirectToDashBoard(position);
            }
        });

        return itemView;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}