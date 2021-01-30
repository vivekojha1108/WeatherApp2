package com.vivekojha.weatherapp.nav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import com.vivekojha.weatherapp.CitiesName;
import com.vivekojha.weatherapp.ListViewAdapter;
import com.vivekojha.weatherapp.R;

import java.util.ArrayList;

public class SearchCityFragment extends Fragment implements SearchView.OnQueryTextListener {

    public SearchCityFragment() {
        // Required empty public constructor
    }


    ListView list;
    ListViewAdapter adapter;
    SearchView searchCity;
    String[] cityNameList;
    ArrayList<CitiesName> arrayList = new ArrayList<CitiesName>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_city, container, false);

        // Sample Cities Name
        cityNameList = new String[]{"Noida", "Delhi", "Mumbai", "Kolkata"};
        // Locate the ListView in listview_main.xml
        list = view.findViewById(R.id.listview);

        for (int i = 0; i < cityNameList.length; i++) {
            CitiesName citiesNames = new CitiesName(cityNameList[i]);
            // Binds all strings into an array
            arrayList.add(citiesNames);
            // Pass results to ListViewAdapter Class
            adapter = new ListViewAdapter(getActivity(), arrayList);

            // Binds the Adapter to the ListView
            list.setAdapter((ListAdapter) adapter);

            // Locate the EditText in listview_main.xml
            searchCity = (SearchView) view.findViewById(R.id.searchView);
            searchCity.setOnQueryTextListener(SearchCityFragment.this);

        }
        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}