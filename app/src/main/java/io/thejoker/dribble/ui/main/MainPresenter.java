package io.thejoker.dribble.ui.main;

import io.thejoker.dribble.data.DataManager;
import io.thejoker.dribble.data.model.database.CurrentWeather;
import io.thejoker.dribble.data.model.database.Location;
import io.thejoker.dribble.data.model.database.Weather;
import io.thejoker.dribble.ui.base.BasePresenter;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by thejoker on 23/9/16.
 */

public class MainPresenter extends BasePresenter<MainMvpView> {
  private final DataManager mDataManager;
  private CompositeSubscription mSubscription;

  @Inject MainPresenter(DataManager mDataManager) {
    this.mDataManager = mDataManager;
  }

  @Override public void attachView(MainMvpView mvpView) {
    mSubscription = new CompositeSubscription();
    super.attachView(mvpView);
  }

  @Override public void dettachView() {
    super.dettachView();
    if (mSubscription != null) mSubscription.unsubscribe();
  }

  public void getForecastWeather() {
    Subscription forecastSubscription =
        mDataManager.fetchWeatherForcast().subscribe(new Subscriber<List<Weather>>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            e.printStackTrace();
            getmMvpView().showError();
          }

          @Override public void onNext(List<Weather> weathers) {
            getmMvpView().showWeather(weathers);
          }
        });
    mSubscription.add(forecastSubscription);
  }

  public void getLocation() {
    Subscription locationSubscription =
        mDataManager.fetchLocation().subscribe(new Subscriber<Location>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {

          }

          @Override public void onNext(Location location) {

          }
        });
    mSubscription.add(locationSubscription);
  }

  public void getCurrentWeather() {
    Subscription currentWeatherSubscription =
        mDataManager.fetchCurrentWeather().subscribe(new Subscriber<CurrentWeather>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            e.printStackTrace();
          }

          @Override public void onNext(CurrentWeather currentWeather) {
            if (currentWeather != null) {
              getmMvpView().showCurrentWeather(currentWeather);
            } else {
              getmMvpView().showEmpty();
            }
          }
        });
    mSubscription.add(currentWeatherSubscription);
  }
}
