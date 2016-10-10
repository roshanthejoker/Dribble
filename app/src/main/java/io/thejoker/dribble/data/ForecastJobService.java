package io.thejoker.dribble.data;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import io.thejoker.dribble.DribbleApplication;
import io.thejoker.dribble.data.local.DatabaseHelper;
import io.thejoker.dribble.data.local.PreferenceHelper;
import io.thejoker.dribble.data.model.darksky.Forecast;
import io.thejoker.dribble.injection.component.DaggerJobComponent;
import io.thejoker.dribble.injection.component.JobComponent;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by thejoker on 5/10/16.
 */

public class ForecastJobService extends JobService {

  @Inject DataManager mDataManager;
  @Inject DatabaseHelper mDbHelper;
  @Inject PreferenceHelper mPrefHelper;
  private Subscription mSubscription;



  @Override public boolean onStartJob(final JobParameters params) {
    Log.d("ForecastJobService", "onStartJob Started");
   mSubscription =  mDataManager.getData().subscribe(new Subscriber<Forecast>() {
      @Override public void onCompleted() {
        jobFinished(params, false);
      }

      @Override public void onError(Throwable e) {
        jobFinished(params, true);
      }

      @Override public void onNext(Forecast forecast) {
        mDbHelper.saveForecastToDb(forecast);
        mPrefHelper.clearPreferences();
        mPrefHelper.putCurrentWeather(forecast.getCurrentWeather());
      }
    });
    return true;
  }

  @Override public boolean onStopJob(JobParameters params) {
    return true;
  }

  @Override public void onCreate() {
    super.onCreate();
    JobComponent mComponent = DaggerJobComponent.builder()
        .applicationComponent(DribbleApplication.getApplication(this).getComponent())
        .build();

    mComponent.inject(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mSubscription.unsubscribe();
  }
}
