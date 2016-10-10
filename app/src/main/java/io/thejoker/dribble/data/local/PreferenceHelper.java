package io.thejoker.dribble.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.thejoker.dribble.data.model.darksky.CurrentForecast;
import io.thejoker.dribble.data.model.database.CurrentWeather;
import io.thejoker.dribble.injection.AppContext;
import rx.Observable;
import rx.functions.Func0;

/**
 * Created by thejoker on 11/9/16.
 */

@Singleton
public class PreferenceHelper {

    public static final String PREF_FILE_NAME = "dribble_pref_file";
    private final SharedPreferences mPreferences;
    private static final String PREF_KEY_CURRENT_WEATHER = "PREF_KEY_CURRENT_WEATHER";
    private Gson mGson;

    @Inject
    public PreferenceHelper(@AppContext Context context) {
        this.mPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz")
                .create();
    }

    public void clearPreferences() {
        mPreferences.edit().clear().apply();
    }

    public void putCurrentWeather(CurrentForecast currentForecast) {
        mPreferences.edit().putString(PREF_KEY_CURRENT_WEATHER, mGson.toJson(currentForecast)).apply();
    }

    private CurrentForecast getCurrentWeather() {
        String currentWeatherJson = mPreferences.getString(PREF_KEY_CURRENT_WEATHER, null);
        if (currentWeatherJson != null) {
            return mGson.fromJson(currentWeatherJson, new TypeToken<CurrentForecast>() {
            }.getType());
        } else {
            Log.e("PrefHelper", "Oops there's nothing in the preferences");
            return null;
        }
    }

    public Observable<CurrentWeather> getCurrentWeatherAsObservable() {
        return Observable.defer(new Func0<Observable<CurrentWeather>>() {
            @Override
            public Observable<CurrentWeather> call() {
                CurrentForecast currentForecast = getCurrentWeather();
                CurrentWeather currentWeather = CurrentWeather.builder()
                        .setDate(currentForecast.getTime())
                        .setIcon(currentForecast.getIcon())
                        .setSummary(currentForecast.getSummary())
                        .setTemperature(currentForecast.getTemperature())
                        .setHumidity(currentForecast.getHumidity())
                        .setPressure(currentForecast.getPressure())
                        .build();

                return Observable.just(currentWeather);
            }
        });
    }

}


