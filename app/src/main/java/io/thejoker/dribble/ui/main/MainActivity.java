package io.thejoker.dribble.ui.main;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import butterknife.ButterKnife;
import io.thejoker.dribble.R;
import io.thejoker.dribble.data.ForecastJobService;
import io.thejoker.dribble.data.model.database.CurrentWeather;
import io.thejoker.dribble.data.model.database.Weather;
import io.thejoker.dribble.ui.base.BaseActivity;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMvpView {

  @Inject MainPresenter mPresenter;
  private final int JOB_ID = 100;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    mActivityComponent.inject(this);
    setContentView(R.layout.activity_main);

    JobInfo jobInfo =
        new JobInfo.Builder(JOB_ID, new ComponentName(this, ForecastJobService.class)).setPersisted(
            true)
            .setPeriodic(3600000)
            .setRequiresCharging(false)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .build();


    JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
    jobScheduler.schedule(jobInfo);
    mPresenter.attachView(this);
  }

  @Override protected void onStart() {
    super.onStart();
    mPresenter.getForecastWeather();
    mPresenter.getCurrentWeather();
  }

  @Override protected void onPause() {
    super.onPause();
    mPresenter.dettachView();
  }

  @Override public void showCurrentWeather(CurrentWeather currentWeather) {
    Log.d("showCurrentWeather", currentWeather.toString());
  }

  @Override public void showWeather(List<Weather> weathers) {
            Log.d("getForecastWeather",weathers.toString());
  }

  @Override public void showError() {
    Toast.makeText(this, "Oops! Something went wrong", Toast.LENGTH_LONG).show();
  }

  @Override public void showLoading() {

  }

  @Override public void showEmpty() {
    Toast.makeText(this,"Current Weather is Empty",Toast.LENGTH_LONG).show();
  }
}
