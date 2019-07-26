package com.rb.elite.servicelist.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rb.elite.core.model.ServiceMainEntity;
import com.rb.elite.servicelist.NonRTOListFragment;
import com.rb.elite.servicelist.RTOListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IN-RB on 06-08-2018.
 */

public class ServicePagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public final static String RTO_LIST = "LIST_RTO_SERVICE";
    public final static String NONRTO_LIST = "LIST_NONRTO_SERVICE";
    ServiceMainEntity mMasterData;

    public ServicePagerAdapter(FragmentManager fm ,ServiceMainEntity MasterData) {
        super(fm);
        mMasterData = MasterData;
    }


    @Override
    public Fragment getItem(int position) {

     //   return mFragmentList.get(position);
        switch (position) {
            case 0:
                // RTO fragment
                RTOListFragment Qfragment = new RTOListFragment();
                Bundle bundle = new Bundle();
                if (mMasterData == null) {
                    bundle.putParcelableArrayList(RTO_LIST, null);
                } else {
                  //  bundle.putParcelableArrayList(RTO_LIST, (ArrayList<? extends Parcelable>) mMasterData.getRTO());
                    bundle.putParcelableArrayList(RTO_LIST, (ArrayList<? extends Parcelable>) mMasterData.getRTO());
                }
                Qfragment.setArguments(bundle);
                return Qfragment;
            case 1:
                // Non RTO fragment
                NonRTOListFragment Afragment = new NonRTOListFragment();
                Bundle Abundle = new Bundle();
                if (mMasterData == null) {
                    Abundle.putParcelableArrayList(NONRTO_LIST, null);
                } else {
                    Abundle.putParcelableArrayList(NONRTO_LIST, (ArrayList<? extends Parcelable>) mMasterData.getNONRTO());
                }
                Afragment.setArguments(Abundle);
                return Afragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
