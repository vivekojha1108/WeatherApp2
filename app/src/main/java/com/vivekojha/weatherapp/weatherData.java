package com.vivekojha.weatherapp;

import org.json.JSONException;
import org.json.JSONObject;

public class weatherData {

    public weatherData() {
        //empty constructor
    }

    private String mTemperature, mIcon, mCity, mWeatherType;
    private int mCondition;

    //
    private String wind, FeelsLike, rain, min_temp;


    public static weatherData fromJson(JSONObject jsonObject) {
        try {
            weatherData weatherD = new weatherData();
            weatherD.mCity = jsonObject.getString("name");
            weatherD.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0)
                    .getInt("id");
            weatherD.mWeatherType = jsonObject.getJSONArray("weather")
                    .getJSONObject(0).getString("main");
            weatherD.mIcon = updateWeatherIcon(weatherD.mCondition);

            double tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
            int roundOftempResult = (int) Math.rint(tempResult);
            weatherD.mTemperature = Integer.toString(roundOftempResult);

            //MoreData of Current
            double feelTempResult = jsonObject.getJSONObject("main").getDouble("feels_like") - 273.15;
            double minTempResult = jsonObject.getJSONObject("main").getDouble("temp_min") - 273.15;

            int roundOfFeelsTemp = (int) Math.rint(feelTempResult);
            int roundOfminTempResult = (int) Math.rint(minTempResult);

            weatherD.FeelsLike = Integer.toString(roundOfFeelsTemp);
            weatherD.min_temp = Integer.toString(roundOfminTempResult);

            weatherD.wind = jsonObject.getJSONObject("wind").getString("speed");
            weatherD.rain = jsonObject.getJSONObject("clouds").getString("all");

            return weatherD;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String updateWeatherIcon(int mCondition) {

        if (mCondition >= 0 && mCondition <= 300) {
            return "thunderstrom1";
        } else if (mCondition >= 300 && mCondition <= 500) {
            return "lightrain";
        } else if (mCondition >= 500 && mCondition <= 600) {
            return "shower";
        } else if (mCondition >= 600 && mCondition <= 700) {
            return "snow2";
        } else if (mCondition >= 701 && mCondition <= 771) {
            return "fog";
        } else if (mCondition >= 772 && mCondition <= 799) {
            return "overcast";
        } else if (mCondition >= 801 && mCondition <= 804) {
            return "cloudy";
        } else if (mCondition >= 900 && mCondition <= 902) {
            return "thunderstrom1";
        }
        if (mCondition == 800) {
            return "sunny";
        }
        if (mCondition == 903) {
            return "snow1";
        }
        if (mCondition == 904) {
            return "sunny";
        }
        if (mCondition >= 905 && mCondition <= 1000) {
            return "thunderstrom2";
        }

        return "dunno";


    }

    //getters

    public String getmTemperature() {
        return mTemperature + "°C";
    }

    public String getmIcon() {
        return mIcon;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmWeatherType() {
        return mWeatherType;
    }

    public int getmCondition() {
        return mCondition;
    }

    public String getWind() {
        return wind + "mph";
    }

    public String getFeelsLike() {
        return FeelsLike + "°C";
    }

    public String getRain() {
        return rain + "%";
    }

    public String getMin_temp() {
        return min_temp + "°C";
    }
    //setters


    public void setmTemperature(String mTemperature) {
        this.mTemperature = mTemperature;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public void setmWeatherType(String mWeatherType) {
        this.mWeatherType = mWeatherType;
    }

    public void setmCondition(int mCondition) {
        this.mCondition = mCondition;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public void setFeelsLike(String feelsLike) {
        FeelsLike = feelsLike;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }
}
