package com.shoaibnwar.iwsm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.shoaibnwar.iwsm.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gold on 6/26/2018.
 */

public class CustomeItem extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<HashMap<String, String>>  beanList;
    private LayoutInflater inflater;
    ValueFilter valueFilter;


    public CustomeItem(Context context, ArrayList<HashMap<String, String>> beanList) {
        this.context = context;
        this.beanList = beanList;

    }


    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int i) {
        return beanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.custom_list_item, null);
        }

        TextView txtName = (TextView) view.findViewById(R.id.tv_title);
        TextView txtId = (TextView) view.findViewById(R.id.tv_price);
        TextView txtItemCode = (TextView) view.findViewById(R.id.tv_item_code);

        txtName.setText(beanList.get(i).get("Name"));
        txtId.setText(beanList.get(i).get("Price"));
        txtItemCode.setText(beanList.get(i).get("Code"));
        return view;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<HashMap<String, String>> filterList = new ArrayList<>();
                for (int i = 0; i < beanList.size(); i++) {

                    if ((beanList.get(i).get("Name").toUpperCase()).contains(constraint.toString().toUpperCase()) ||
                            (beanList.get(i).get("Code").toUpperCase()).contains(constraint.toString().toUpperCase())) {


                        HashMap<String, String> list = new HashMap<>();
                        String name = beanList.get(i).get("Name");
                        String price = beanList.get(i).get("Price");
                        String itemCode = beanList.get(i).get("Code");
                        list.put("Name", name);
                        list.put("Price", price);
                        list.put("Code", itemCode);

                        filterList.add(list);

                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = beanList.size();
                results.values = beanList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            beanList = (ArrayList<HashMap<String, String>>) results.values;
            notifyDataSetChanged();
        }

    }
}