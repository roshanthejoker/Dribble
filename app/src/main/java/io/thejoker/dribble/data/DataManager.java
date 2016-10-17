package io.thejoker.dribble.data;

import io.thejoker.dribble.data.local.DatabaseHelper;
import io.thejoker.dribble.data.local.PreferenceHelper;
import io.thejoker.dribble.data.model.darksky.DailyForecast;
import io.thejoker.dribble.data.model.darksky.Forecast;
import io.thejoker.dribble.data.model.darksky.MainData;
import io.thejoker.dribble.data.model.database.CurrentWeather;
import io.thejoker.dribble.data.model.database.Location;
import io.thejoker.dribble.data.model.database.Weather;
import io.thejoker.dribble.data.model.database.WeatherWithLocation;
import io.thejoker.dribble.data.remote.WeatherService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;
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
    DataManager(WeatherService mWeatherService, PreferenceHelper mPrefHelper,
                DatabaseHelper databaseHelper) {
        this.mWeatherService = mWeatherService;
        this.mPrefHelper = mPrefHelper;
        this.mDbHelper = databaseHelper;
    }

    private Observable<Forecast> getData() {
        return mWeatherService.getForecast(19.0760, 72.8777, "si")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Forecast>() {
                    @Override
                    public void call(Forecast forecast) {
                        mDbHelper.saveForecastToDb(forecast);
                        mPrefHelper.clearPreferences();
                        mPrefHelper.putCurrentWeather(forecast.getCurrentWeather());
                    }
                });

    }

    private Observable<CurrentWeather> fetchCurrentWeatherFromDb() {
        return mPrefHelper.getCurrentWeatherAsObservable()
                .subscribeOn(Schedulers.io());
    }

    private Observable<List<Weather>> fetchWeatherForecastFromDb() {
        return mDbHelper.fetchWeatherWithLocation()
                .concatMap(new Func1<List<WeatherWithLocation>, Observable<List<Weather>>>() {
                    @Override
                    public Observable<List<Weather>> call(List<WeatherWithLocation> weatherWithLocations) {
                        List<Weather> weatherList = new ArrayList<>(weatherWithLocations.size());

                        for (WeatherWithLocation item : weatherWithLocations) {
                            weatherList.add(item.weather());
                        }

                        return Observable.just(weatherList);
                    }
                }).subscribeOn(Schedulers.io());
    }

    private Observable<Location> fetchLocationDb() {
        return mDbHelper.fetchWeatherWithLocation()
                .flatMap(new Func1<List<WeatherWithLocation>, Observable<Location>>() {
                    @Override
                    public Observable<Location> call(List<WeatherWithLocation> weatherWithLocations) {

                        return Observable.just(weatherWithLocations.get(0).location());
                    }
                })
                .subscribeOn(Schedulers.io());
    }


    private Observable<CurrentWeather> getCurrentWeatherFromNetwork() {
        return getData().flatMap(new Func1<Forecast, Observable<CurrentWeather>>() {
            @Override
            public Observable<CurrentWeather> call(Forecast forecast) {
                CurrentWeather currentWeather = CurrentWeather.builder()
                        .setDate(forecast.getCurrentWeather().getTime())
                        .setIcon(forecast.getCurrentWeather().getIcon())
                        .setTemperature(forecast.getCurrentWeather().getTemperature())
                        .setSummary(forecast.getCurrentWeather().getSummary())
                        .setHumidity(forecast.getCurrentWeather().getHumidity())
                        .setPressure(forecast.getCurrentWeather().getPressure())
                        .build();

                return Observable.just(currentWeather);
            }
        });
    }

    private Observable<List<Weather>> getForecastFromNetwork() {
        return getData().flatMap(new Func1<Forecast, Observable<List<Weather>>>() {
            @Override
            public Observable<List<Weather>> call(Forecast forecast) {
                List<Weather> weatherList = new ArrayList<Weather>(forecast.getDaily().getData().size());

                for (MainData item : forecast.getDaily().getData()) {
                    Weather weather = Weather.builder()
                            .setWeather_id(null)
                            .setDate(item.getTime())
                            .setIcon(item.getIcon())
                            .setSummary(item.getSummary())
                            .setLocation_id(1L)
                            .setMinTemperature(item.getTemperatureMin())
                            .setMaxTemperature(item.getTemperatureMax())
                            .setHumidity(item.getHumidity())
                            .setPressure(item.getPressure())
                            .build();
                    weatherList.add(weather);
                }
                return Observable.just(weatherList);
            }
        });
    }

    private Observable<Location> getLocationFromNetwork() {
        return getData().flatMap(new Func1<Forecast, Observable<Location>>() {
            @Override
            public Observable<Location> call(Forecast forecast) {
                Location location = Location.builder()
                        .setLocation_id(null)
                        .setCoordLat(forecast.getLatitude())
                        .setCoordLon(forecast.getLongitude())
                        .build();
                return Observable.just(location);
            }
        });


    }

    public Observable<CurrentWeather> getCurrentWeather() {
       return Observable.merge(getCurrentWeatherFromNetwork(), fetchCurrentWeatherFromDb())
                .onErrorReturn(new Func1<Throwable, CurrentWeather>() {
                    @Override
                    public CurrentWeather call(Throwable throwable) {
                        return null;
                    }
                })
                .filter(new Func1<CurrentWeather, Boolean>() {
                    @Override
                    public Boolean call(CurrentWeather currentWeather) {
                        return currentWeather != null;
                    }
                });
    }
}


