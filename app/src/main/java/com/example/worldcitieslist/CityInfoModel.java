package com.example.worldcitieslist;
public class CityInfoModel  {
    private String cityName;
    private String temperature;
    private String time;
    private String desc;

    public CityInfoModel(String cityName, String temperature, String time, String desc) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.time = time;
        this.desc = desc;
    }

    public String getCityName() {
        return cityName;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getTime() {
        return time;
    }

    public String getDesc() {
        return desc;
    }
}
