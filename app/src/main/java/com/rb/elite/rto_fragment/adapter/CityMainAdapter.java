package com.rb.elite.rto_fragment.adapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rb.elite.R;
import com.rb.elite.core.model.RtoProductDisplayMainEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IN-RB on 26-11-2018.
 */

public class CityMainAdapter extends RecyclerView.Adapter<CityMainAdapter.CityItem> implements Filterable {


    Fragment mContext;

    List<RtoProductDisplayMainEntity> rtoCityList;
    IRTOCity iRTOCity;
    ValueFilter valueFilter;
    public CityMainAdapter(Fragment mContext, List<RtoProductDisplayMainEntity> rtoCityList, IRTOCity irtoCity) {
        this.mContext = mContext;
        this.rtoCityList = rtoCityList;
        iRTOCity = irtoCity;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public class CityItem extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public LinearLayout lyParent;


        public CityItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            lyParent = (LinearLayout) itemView.findViewById(R.id.lyParent);
        }
    }


    @Override
    public CityMainAdapter.CityItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_rto_list_item, parent, false);

        return new CityMainAdapter.CityItem(itemView);
    }

    @Override
    public void onBindViewHolder(CityMainAdapter.CityItem holder, int position) {
        final RtoProductDisplayMainEntity cityEntity = rtoCityList.get(position);

       // holder.txtTitle.setText("" + cityEntity.getCityname());

        holder.lyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // iRTOCity.getRTOCity(cityEntity, null);
            }
        });


    }

    public void updateList(List<RtoProductDisplayMainEntity> temprtoCityList) {
        rtoCityList = temprtoCityList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return rtoCityList.size();
    }

    public Fragment getmContext() {
        return mContext;
    }

    public class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<RtoProductDisplayMainEntity> filterList = new ArrayList<>();
                for (int i = 0; i < rtoCityList.size(); i++) {
//                    if ((rtoCityList.get(i).getCityname().toUpperCase()).contains(constraint.toString().toUpperCase())) {
//                        filterList.add(rtoCityList.get(i));
//                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = rtoCityList.size();
                results.values = rtoCityList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            rtoCityList = (List<RtoProductDisplayMainEntity>) results.values;
            notifyDataSetChanged();
        }


    }


}