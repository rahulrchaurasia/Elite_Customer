package com.pb.elite.register;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;


import com.pb.elite.R;
import com.pb.elite.core.model.CarMasterEntity;
import com.pb.elite.core.model.MakeX;

import java.util.ArrayList;
import java.util.List;

public class MakeAdapter extends ArrayAdapter<CarMasterEntity> {

    Context context;
    int resource,textViewResourceId;
    List<CarMasterEntity> items, tempItems, suggestions;

    public MakeAdapter(Context context, int resource, int textViewResourceId,  List<CarMasterEntity> items) {
        super(context, resource,  items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<CarMasterEntity>(items); // this makes the difference.
        suggestions = new ArrayList<CarMasterEntity>();
    }

    @Nullable
    @Override
    public CarMasterEntity getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_people, parent, false);
        }
        CarMasterEntity carMasterEntity = items.get(position);
        if (carMasterEntity != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(carMasterEntity.getMake_Name());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((CarMasterEntity) resultValue).getMake_Name();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (CarMasterEntity people : tempItems) {
                    if (people.getMake_Name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<CarMasterEntity> filterList = (ArrayList<CarMasterEntity>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (CarMasterEntity people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}