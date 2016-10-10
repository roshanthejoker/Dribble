package io.thejoker.dribble.ui.main;


import java.util.List;

import io.thejoker.dribble.data.model.darksky.Forecast;
import io.thejoker.dribble.data.model.database.CurrentWeather;
import io.thejoker.dribble.data.model.database.Weather;
import io.thejoker.dribble.ui.base.MvpView;

/**
 * Created by thejoker on 23/9/16.
 */

interface MainMvpView extends MvpView {

    void showCurrentWeather(CurrentWeather currentWeather);
    void showWeather(List<Weather> weatherList);
    void showError();
    void showLoading();
    void showEmpty();
}
