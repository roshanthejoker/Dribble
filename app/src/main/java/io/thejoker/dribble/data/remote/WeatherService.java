package io.thejoker.dribble.data.remote;

import java.io.IOException;
import java.net.Authenticator;
import java.util.List;

import javax.inject.Singleton;

import io.thejoker.dribble.BuildConfig;
import io.thejoker.dribble.data.model.forecast.ForecastList;
import io.thejoker.dribble.data.model.forecast.ForecastMain;
import io.thejoker.dribble.data.model.forecast.Weather;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by thejoker on 10/9/16.
 */

public interface WeatherService {

    @GET("/forecast")
    Observable<List<ForecastList>>
    getForecast(@Query("q")String location, @Query("unit") String unit);
}






