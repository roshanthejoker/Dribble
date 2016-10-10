package io.thejoker.dribble.injection.component;

import dagger.Component;
import io.thejoker.dribble.data.ForecastJobService;
import io.thejoker.dribble.injection.JobScope;

/**
 * Created by thejoker on 6/10/16.
 */

@JobScope @Component(dependencies = { ApplicationComponent.class }) public interface JobComponent {
  void inject(ForecastJobService forecastJobService);
}
