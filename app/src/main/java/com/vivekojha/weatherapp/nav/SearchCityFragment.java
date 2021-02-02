package com.vivekojha.weatherapp.nav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.vivekojha.weatherapp.CitiesName;
import com.vivekojha.weatherapp.ListViewAdapter;
import com.vivekojha.weatherapp.R;
import com.vivekojha.weatherapp.weatherData;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchCityFragment extends Fragment implements SearchView.OnQueryTextListener {

    public SearchCityFragment() {
        // Required empty public constructor
    }

    final String APP_ID = "f370bf459bd243a5db470613f7ee64f7";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    ListView list;
    ListViewAdapter adapter;
    SearchView searchCity;
    String[] cityNameList;
    ArrayList<CitiesName> arrayList = new ArrayList<CitiesName>();

    ProgressBar progressBar;
    ImageView WeatheIcon;
    TextView Temp, wConditon, sCityName;

    TextView Wind, FeelsLike, Rain, Min_Temp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_city, container, false);

        progressBar = view.findViewById(R.id.progressBarCitySearch);
        WeatheIcon = view.findViewById(R.id.imageView3);
        Temp = view.findViewById(R.id.textView6);
        sCityName = view.findViewById(R.id.textView7);
        wConditon = view.findViewById(R.id.textView8);


        FeelsLike = view.findViewById(R.id.textView18);
        Rain = view.findViewById(R.id.textView19);
        Wind = view.findViewById(R.id.textView21);
        Min_Temp = view.findViewById(R.id.textView22);


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

        getWeatherForNewCity(query);
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    private void getWeatherForNewCity(String query) {

        RequestParams params = new RequestParams();
        params.put("q", query);
        params.put("appid", APP_ID);

        progressBar.setVisibility(View.VISIBLE);
        fetchWeatherCityData(params);
    }

    private void fetchWeatherCityData(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                /* Toast.makeText(getActivity(), "Data get success", Toast.LENGTH_SHORT).show();*/

                weatherData weatherData = com.vivekojha.weatherapp.weatherData.fromJson(response);
                UpdateSearchCityUI(weatherData);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void UpdateSearchCityUI(weatherData weatherData) {

        Temp.setText(weatherData.getmTemperature());
        sCityName.setText(weatherData.getmCity());
        wConditon.setText(weatherData.getmWeatherType());
      /*
        int resourceID = getResources().getIdentifier(weatherData.getmIcon(), "drawable", getActivity().getPackageName());
        wConditon.setImageResource(resourceID);*/

        Wind.setText(weatherData.getWind());
        FeelsLike.setText(weatherData.getFeelsLike());
        Rain.setText(weatherData.getRain());
        Min_Temp.setText(weatherData.getMin_temp());
    }

}