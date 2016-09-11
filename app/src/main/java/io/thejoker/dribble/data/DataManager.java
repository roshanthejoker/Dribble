package io.thejoker.dribble.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.thejoker.dribble.data.local.PreferenceHelper;
import io.thejoker.dribble.data.model.forecast.ForecastList;
import io.thejoker.dribble.data.remote.WeatherService;
import rx.Observable;

/**
 * Created by thejoker on 10/9/16.
 */

@Singleton
public class DataManager {

    private final WeatherService mWeatherService;
    private final PreferenceHelper mPrefHelper;

    @Inject
    public DataManager(WeatherService mWeatherService, PreferenceHelper mPrefHelper) {
        this.mWeatherService = mWeatherService;
        this.mPrefHelper = mPrefHelper;
    }

public Observable<List<ForecastList>> getForecast(){
    return mWeatherService.getForecast("Mumbai","metric");
}
}
