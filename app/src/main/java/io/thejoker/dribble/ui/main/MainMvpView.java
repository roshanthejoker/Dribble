package io.thejoker.dribble.ui.main;

import java.util.List;

import io.thejoker.dribble.data.model.database.Weather;
import io.thejoker.dribble.ui.base.MvpView;

/**
 * Created by thejoker on 23/9/16.
 */

interface MainMvpView extends MvpView {

    void showWeather(List<Weather> weather);
    void showError();
    void showEmpty();
}
