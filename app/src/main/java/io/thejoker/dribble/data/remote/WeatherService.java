package io.thejoker.dribble.data.remote;

import java.util.List;
import java.util.Map;

import io.thejoker.dribble.data.model.darksky.Forecast;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by thejoker on 10/9/16.
 */

public interface WeatherService {

    @GET("{Latitude},{Longitude}")
    Observable<Forecast>
    getForecast(@Path("Latitude")Double latitude,@Path("Longitude")Double longitude,
                @Query("units")String unit);
}






