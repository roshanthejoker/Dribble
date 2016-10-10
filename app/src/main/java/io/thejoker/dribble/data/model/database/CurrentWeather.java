package io.thejoker.dribble.data.model.database;

import com.google.auto.value.AutoValue;

/**
 * Created by thejoker on 30/9/16.
 */

@AutoValue
public abstract class CurrentWeather {

    abstract long date();
    abstract String summary();
    abstract String icon();
    abstract double temperature();
    abstract double pressure();
    abstract double humidity();

   public static Builder builder(){
        return new AutoValue_CurrentWeather.Builder();
    }

    @AutoValue.Builder
   public abstract static class Builder{
       public abstract Builder setDate(long value);
       public abstract Builder setSummary(String value);
       public abstract Builder setIcon(String value);
       public abstract Builder setTemperature(double value);
       public abstract Builder setPressure(double value);
       public abstract Builder setHumidity(double value);
       public abstract CurrentWeather build();
    }

}
