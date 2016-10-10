package io.thejoker.dribble.data.model.database;

import android.database.Cursor;

import com.google.auto.value.AutoValue;

import io.thejoker.dribble.WeatherWithLocationModel;

/**
 * Created by thejoker on 30/9/16.
 */

@AutoValue
public abstract class WeatherWithLocation implements WeatherWithLocationModel {
    public abstract Weather weather();
    public abstract Location location();

    public static WeatherWithLocation map(Cursor cursor){
        return new AutoValue_WeatherWithLocation(Weather.WEATHER_ROW_MAPPER.map(cursor),
                Location.LOCATION_ROW_MAPPER.map(cursor));
    }
}
