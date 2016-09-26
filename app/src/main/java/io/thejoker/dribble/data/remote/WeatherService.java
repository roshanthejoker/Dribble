package io.thejoker.dribble.data.remote;

import io.thejoker.dribble.data.model.darksky.Forecast;
import io.thejoker.dribble.data.model.openweather.ForecastMain;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by thejoker on 10/9/16.
 */

public interface WeatherService {

    @GET("/{latitude},{longitude}")
    Observable<Forecast>
    getForecast(@Path("latitude") long latitude, @Path("longitude") long longitude);
}






