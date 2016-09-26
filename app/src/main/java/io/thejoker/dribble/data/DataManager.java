package io.thejoker.dribble.data;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.thejoker.dribble.data.local.DatabaseHelper;
import io.thejoker.dribble.data.local.PreferenceHelper;
import io.thejoker.dribble.data.model.openweather.ForecastMain;
import io.thejoker.dribble.data.model.database.Location;
import io.thejoker.dribble.data.model.database.Weather;
import io.thejoker.dribble.data.model.database.WeatherWithLocation;
import io.thejoker.dribble.data.remote.WeatherService;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by thejoker on 10/9/16.
 */

@Singleton
public class DataManager {

    private final WeatherService mWeatherService;
    private final PreferenceHelper mPrefHelper;
    private final DatabaseHelper mDbHelper;

    @Inject
    DataManager(WeatherService mWeatherService, PreferenceHelper mPrefHelper, DatabaseHelper databaseHelper) {
        this.mWeatherService = mWeatherService;
        this.mPrefHelper = mPrefHelper;
        this.mDbHelper = databaseHelper;
    }


}
