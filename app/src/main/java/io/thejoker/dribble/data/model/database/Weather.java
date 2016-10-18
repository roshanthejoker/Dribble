package io.thejoker.dribble.data.model.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;

import io.thejoker.dribble.WeatherModel;

/**
 * Created by thejoker on 26/9/16.
 */

@AutoValue
public abstract class Weather implements WeatherModel {
    public static final Factory<Weather> WEATHER_FACTORY = new Factory<>(
            (weather_id, location_id, date, summary, icon,
             minTemperature, maxTemperature, pressure, humidity) -> new AutoValue_Weather.Builder()
                    .setWeather_id(weather_id)
                    .setLocation_id(location_id)
                    .setDate(date)
                    .setIcon(icon)
                    .setSummary(summary)
                    .setMinTemperature(minTemperature)
                    .setMaxTemperature(maxTemperature)
                    .setPressure(pressure)
                    .setHumidity(humidity)
                    .build());

    public static Builder builder() {
        return new AutoValue_Weather.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setLocation_id(long value);

        public abstract Builder setWeather_id(Long value);

        public abstract Builder setDate(long value);

        public abstract Builder setSummary(String value);

        public abstract Builder setIcon(String value);

        public abstract Builder setMinTemperature(double value);

        public abstract Builder setMaxTemperature(double value);

        public abstract Builder setPressure(double value);

        public abstract Builder setHumidity(double value);

        public abstract Weather build();
    }

    static final RowMapper<Weather> WEATHER_ROW_MAPPER = WEATHER_FACTORY.select_weatherMapper();
}
