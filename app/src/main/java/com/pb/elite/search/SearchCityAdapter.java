package com.pb.elite.search;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pb.elite.R;
import com.pb.elite.core.model.AllCityEntity;
import com.pb.elite.core.model.DocProductEnity;
import com.pb.elite.database.DataBaseController;

import java.util.List;

import io.realm.Case;
import io.realm.Sort;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class SearchCityAdapter extends RecyclerView.Adapter<SearchCityAdapter.ProductItem> {

    Activity mContext;
    List<AllCityEntity> CityList;
    DataBaseController dataBaseController;
    public SearchCityAdapter(Activity mContext, List<AllCityEntity> CityList) {
        this.mContext = mContext;
        this.CityList = CityList;
        dataBaseController = new DataBaseController(this.mContext);
    }


    public class ProductItem extends RecyclerView.ViewHolder
    {
        public TextView txtTitle;
        LinearLayout lyParent;


        public ProductItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            lyParent = (LinearLayout)itemView.findViewById(R.id.lyParent);

        }
    }

    @Override
    public SearchCityAdapter.ProductItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_city_search_item, parent, false);

        return new SearchCityAdapter.ProductItem (itemView);
    }

    @Override
    public void onBindViewHolder(ProductItem holder, int position) {

        final AllCityEntity entity = CityList.get(position);

        holder.txtTitle.setText( "" +entity.getCityname().toUpperCase());
        holder.lyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((SearchCityActivity)mContext).getNonRtoCity(entity);
            }
        });

    }

    public void filer(String strSearch) {
        List<AllCityEntity> lstCityfilter;


        lstCityfilter =  dataBaseController.getCityQueryList(strSearch);

        CityList = lstCityfilter;
        notifyDataSetChanged();
    }

    public void findAll(List<AllCityEntity> lstList) {
        CityList = lstList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return CityList.size();
    }
}
