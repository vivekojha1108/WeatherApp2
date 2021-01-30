package com.vivekojha.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    FragmentActivity mContext;
    LayoutInflater inflater;
    private List<CitiesName> citiesNamesList = null;
    private ArrayList<CitiesName> arrayList;

    public ListViewAdapter(FragmentActivity activity, List<CitiesName> citiesNamesList) {

        mContext = activity;
        inflater = LayoutInflater.from(mContext);
        this.citiesNamesList = citiesNamesList;
        this.arrayList = new ArrayList<CitiesName>();
        this.arrayList.addAll(citiesNamesList);

    }

    public static class ViewHolder {
        TextView cityName;
    }


    @Override
    public int getCount() {
        return citiesNamesList.size();
    }

    @Override
    public CitiesName getItem(int position) {
        return citiesNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.cityName = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // Set the results into TextViews
        holder.cityName.setText(citiesNamesList.get(position).getCitiesName());
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        citiesNamesList.clear();
        if (charText.length() == 0) {
            citiesNamesList.addAll(arrayList);
        } else {
            for (CitiesName wp : arrayList) {
                if (wp.getCitiesName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    citiesNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
