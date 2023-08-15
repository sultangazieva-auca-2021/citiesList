package com.example.worldcitieslist;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

public class FillTheCityInfoRow extends Application {
    private static ArrayList<CityInfoModel> cityInfoModels = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        setCityInfoModels(this);
    }

    private void setCityInfoModels(Context context) {
        String[] cityNames = context.getResources().getStringArray(R.array.cities_names);
        String[] temperature = context.getResources().getStringArray(R.array.curr_weather);
        String[] time = context.getResources().getStringArray(R.array.curr_time);
        String[] desc = context.getResources().getStringArray(R.array.description);

        for (int i = 0; i < cityNames.length; i++) {
            cityInfoModels.add(new CityInfoModel(cityNames[i], temperature[i], time[i], desc[i]));
        }
    }

    public static ArrayList<CityInfoModel> getCityInfoModels() {
        return cityInfoModels;
    }

    public static void setCityInfoModels(ArrayList<CityInfoModel> cityInfoModels) {
        FillTheCityInfoRow.cityInfoModels = cityInfoModels;
    }
}
