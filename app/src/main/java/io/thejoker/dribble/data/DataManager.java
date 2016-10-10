package io.thejoker.dribble.data;

import io.thejoker.dribble.data.local.DatabaseHelper;
import io.thejoker.dribble.data.local.PreferenceHelper;
import io.thejoker.dribble.data.model.darksky.Forecast;
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
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by thejoker on 10/9/16.
 */

@Singleton public class DataManager {

  private final WeatherService mWeatherService;
  private final PreferenceHelper mPrefHelper;
  private final DatabaseHelper mDbHelper;

  @Inject DataManager(WeatherService mWeatherService, PreferenceHelper mPrefHelper,
      DatabaseHelper databaseHelper) {
    this.mWeatherService = mWeatherService;
    this.mPrefHelper = mPrefHelper;
    this.mDbHelper = databaseHelper;
  }

  public Observable<Forecast> getData() {
    return mWeatherService.getForecast(19.0760, 72.8777, "si")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<CurrentWeather> fetchCurrentWeather() {
    return mPrefHelper.getCurrentWeatherAsObservable()
        .concatMap(new Func1<CurrentWeather, Observable<? extends CurrentWeather>>() {
          @Override
          public Observable<? extends CurrentWeather> call(CurrentWeather currentWeather) {
            return Observable.just(currentWeather);
          }
        });
  }

  public Observable<List<Weather>> fetchWeatherForcast() {
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

  public Observable<Location> fetchLocation() {
    return mDbHelper.fetchWeatherWithLocation()
        .flatMap(new Func1<List<WeatherWithLocation>, Observable<Location>>() {
          @Override
          public Observable<Location> call(List<WeatherWithLocation> weatherWithLocations) {

            return Observable.just(weatherWithLocations.get(0).location());
          }
        });
  }

  //public Observable<CurrentWeather> getCurrentWeather() {
  //    return Observable.concat(fetchCurrentWeatherFromNetwork()
  //            .first(new Func1<CurrentWeather, Boolean>() {
  //                @Override
  //                public Boolean call(CurrentWeather currentWeather) {
  //                    return currentWeather != null;
  //                }
  //            }).onErrorResumeNext(new Func1<Throwable, Observable<? extends CurrentWeather>>() {
  //                @Override
  //                public Observable<? extends CurrentWeather> call(Throwable throwable) {
  //                    return fetchCurrentWeatherFromDb();
  //                }
  //            }), fetchCurrentWeatherFromDb());
  //}

  //public Observable<List<Weather>> getForecast() {
  //    return Observable.concat(fetchWeatherFromNetwork()
  //            .first(new Func1<List<Weather>, Boolean>() {
  //                @Override
  //                public Boolean call(List<Weather> weathers) {
  //                    return weathers != null;
  //                }
  //            }).onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Weather>>>() {
  //                @Override
  //                public Observable<? extends List<Weather>> call(Throwable throwable) {
  //                    return fetchWeatherFromDb();
  //                }
  //            }), fetchWeatherFromDb());
  //}

  //public Observable<Location> getLocation() {
  //    return Observable.concat(fetchLocationFromNetwork()
  //            .first(new Func1<Location, Boolean>() {
  //                @Override
  //                public Boolean call(Location location) {
  //                    return location != null;
  //                }
  //            }), fetchLocationFromDb());
  //}
}


