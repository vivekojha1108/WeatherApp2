package com.vivekojha.weatherapp.nav;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.vivekojha.weatherapp.R;
import com.vivekojha.weatherapp.weatherData;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CurrentTemperatureFragment extends Fragment {


    public CurrentTemperatureFragment() {
        // Required empty public constructor
    }

    final String APP_ID = "f370bf459bd243a5db470613f7ee64f7";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;
    // final AsyncHttpClient client = new AsyncHttpClient();


    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView NameOfCity, WeatherState, Temperature;
    ImageView mWeatherIcon;

    LocationManager mLocationManager;
    LocationListener mLocationListener;
    ProgressBar progressBar;

    TextView Wind, FeelsLike, Rain, Min_Temp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_current_temperature, container, false);
        Temperature = v.findViewById(R.id.temperature);
        WeatherState = v.findViewById(R.id.weatherCondition);
        NameOfCity = v.findViewById(R.id.cityName);
        mWeatherIcon = v.findViewById(R.id.weatherIcon);
        progressBar = v.findViewById(R.id.progressBar);

        FeelsLike = v.findViewById(R.id.textView18);
        Rain = v.findViewById(R.id.textView19);
        Wind = v.findViewById(R.id.textView21);
        Min_Temp = v.findViewById(R.id.textView22);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getWeatherForCurrentLocation();

    }

    private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params = new RequestParams();
                params.put("lat", Latitude);
                params.put("lon", Longitude);
                params.put("appid", APP_ID);

                progressBar.setVisibility(View.VISIBLE);
                fetchWeatherData(params);

            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //not able to get location: if User already Allowed the location permission
            }
        };
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Location Get Successfully", Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            } else {
                Toast.makeText(getActivity(), "Please Allow Location Permission", Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void fetchWeatherData(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
               /* Toast.makeText(getActivity(), "Data get success", Toast.LENGTH_SHORT).show();*/

                weatherData weatherData = com.vivekojha.weatherapp.weatherData.fromJson(response);
                UpdateUI(weatherData);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    private void UpdateUI(weatherData weatherData) {

        Temperature.setText(weatherData.getmTemperature());
        NameOfCity.setText(weatherData.getmCity());
        WeatherState.setText(weatherData.getmWeatherType());
        int resourceID = getResources().getIdentifier(weatherData.getmIcon(), "drawable", getActivity().getPackageName());
        mWeatherIcon.setImageResource(resourceID);

        Wind.setText(weatherData.getWind());
        FeelsLike.setText(weatherData.getFeelsLike());
        Rain.setText(weatherData.getRain());
        Min_Temp.setText(weatherData.getMin_temp());



    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }
}

