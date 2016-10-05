package io.thejoker.dribble.data;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.thejoker.dribble.data.local.DatabaseHelper;
import io.thejoker.dribble.data.local.PreferenceHelper;
import io.thejoker.dribble.data.model.darksky.CurrentForecast;
import io.thejoker.dribble.data.model.darksky.Forecast;
import io.thejoker.dribble.data.model.darksky.MainData;
import io.thejoker.dribble.data.model.database.CurrentWeather;
import io.thejoker.dribble.data.model.database.Location;
import io.thejoker.dribble.data.model.database.Weather;
import io.thejoker.dribble.data.model.database.WeatherWithLocation;
import io.thejoker.dribble.data.remote.WeatherService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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

    public Observable<Forecast> getData() {
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
                })
                .flatMap(new Func1<Forecast, Observable<Forecast>>() {
                    @Override
                    public Observable<Forecast> call(Forecast forecast) {
                        return Observable.just(forecast);
                    }
                }).cache();
    }


    private Observable<List<Weather>> fetchWeatherFromNetwork() {
        return getData()
                .map(new Func1<Forecast, List<MainData>>() {
                    @Override
                    public List<MainData> call(Forecast forecast) {
                        return forecast.getDaily().getData();
                    }
                })
                .concatMap(new Func1<List<MainData>, Observable<? extends List<Weather>>>() {
                    @Override
                    public Observable<? extends List<Weather>> call(List<MainData> mainDatas) {
                        List<Weather> weatherList = new ArrayList<>(mainDatas.size());
                        for (MainData item : mainDatas) {
                            Weather weather = Weather.builder()
                                    .setWeather_id(null)
                                    .setLocation_id(1L)
                                    .setHumidity(item.getHumidity())
                                    .setDate(item.getTime())
                                    .setSummary(item.getSummary())
                                    .setIcon(item.getSummary())
                                    .setMinTemperature(item.getTemperatureMin())
                                    .setMaxTemperature(item.getTemperatureMax())
                                    .setPressure(item.getPressure())
                                    .build();
                            weatherList.add(weather);
                        }
                        return Observable.just(weatherList);
                    }
                });
    }

    private Observable<CurrentWeather> fetchCurrentWeatherFromNetwork() {
        return getData()
                .map(new Func1<Forecast, CurrentForecast>() {
                    @Override
                    public CurrentForecast call(Forecast forecast) {
                        return forecast.getCurrentWeather();
                    }
                })
                .concatMap(new Func1<CurrentForecast, Observable<? extends CurrentWeather>>() {
                    @Override
                    public Observable<? extends CurrentWeather> call(CurrentForecast currentForecast) {
                        CurrentWeather currentWeather = CurrentWeather.builder()
                                .setDate(currentForecast.getTime())
                                .setSummary(currentForecast.getSummary())
                                .setIcon(currentForecast.getIcon())
                                .setTemperature(currentForecast.getTemperature())
                                .setHumidity(currentForecast.getHumidity())
                                .setPressure(currentForecast.getPressure())
                                .build();

                        return Observable.just(currentWeather);
                    }
                });
    }

    private Observable<CurrentWeather> fetchCurrentWeatherFromDb() {
        return mPrefHelper.getCurrentWeatherAsObservable()
                .concatMap(new Func1<CurrentWeather, Observable<? extends CurrentWeather>>() {
                    @Override
                    public Observable<? extends CurrentWeather> call(CurrentWeather currentWeather) {
                        return Observable.just(currentWeather);
                    }
                });
    }

    private Observable<Location> fetchLocationFromNetwork() {
        return getData()
                .concatMap(new Func1<Forecast, Observable<? extends Location>>() {
                    @Override
                    public Observable<? extends Location> call(Forecast forecast) {
                        Location location = Location.builder()
                                .setCoordLat(forecast.getLatitude())
                                .setCoordLon(forecast.getLongitude())
                                .build();

                        return Observable.just(location);
                    }
                });
    }


    private Observable<List<Weather>> fetchWeatherFromDb() {
        return mDbHelper.fetchWeatherWithLocation()
                .concatMap(new Func1<List<WeatherWithLocation>, Observable<List<Weather>>>() {
                    @Override
                    public Observable<List<Weather>> call(List<WeatherWithLocation> weatherWithLocations) {
                        List<Weather> weatherList = new ArrayList<Weather>(weatherWithLocations.size());

                        for (WeatherWithLocation item : weatherWithLocations) {
                            weatherList.add(item.weather());
                        }

                        return Observable.just(weatherList);
                    }
                });
    }

    private Observable<Location> fetchLocationFromDb() {
        return mDbHelper.fetchWeatherWithLocation()
                .flatMap(new Func1<List<WeatherWithLocation>, Observable<Location>>() {
                    @Override
                    public Observable<Location> call(List<WeatherWithLocation> weatherWithLocations) {

                        return Observable.just(weatherWithLocations.get(0).location());
                    }
                });
    }

    public Observable<CurrentWeather> getCurrentWeather() {
        return Observable.concat(fetchCurrentWeatherFromNetwork()
                .first(new Func1<CurrentWeather, Boolean>() {
                    @Override
                    public Boolean call(CurrentWeather currentWeather) {
                        return currentWeather != null;
                    }
                }).onErrorResumeNext(new Func1<Throwable, Observable<? extends CurrentWeather>>() {
                    @Override
                    public Observable<? extends CurrentWeather> call(Throwable throwable) {
                        return fetchCurrentWeatherFromDb();
                    }
                }), fetchCurrentWeatherFromDb());
    }

    public Observable<List<Weather>> getForecast() {
        return Observable.concat(fetchWeatherFromNetwork()
                .first(new Func1<List<Weather>, Boolean>() {
                    @Override
                    public Boolean call(List<Weather> weathers) {
                        return weathers != null;
                    }
                }).onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Weather>>>() {
                    @Override
                    public Observable<? extends List<Weather>> call(Throwable throwable) {
                        return fetchWeatherFromDb();
                    }
                }), fetchWeatherFromDb());
    }

    public Observable<Location> getLocation() {
        return Observable.concat(fetchLocationFromNetwork()
                .first(new Func1<Location, Boolean>() {
                    @Override
                    public Boolean call(Location location) {
                        return location != null;
                    }
                }), fetchLocationFromDb());
    }
}


