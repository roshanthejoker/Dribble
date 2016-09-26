package io.thejoker.dribble.injection.component;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import io.thejoker.dribble.data.DataManager;
import io.thejoker.dribble.data.local.DatabaseHelper;
import io.thejoker.dribble.data.local.PreferenceHelper;
import io.thejoker.dribble.data.remote.WeatherService;
import io.thejoker.dribble.injection.ActivityScope;
import io.thejoker.dribble.injection.AppContext;
import io.thejoker.dribble.injection.module.ApplicationModule;

/**
 * Created by thejoker on 10/9/16.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    @AppContext Context context();
    WeatherService weatherService();
    PreferenceHelper preferenceHelper();
    DataManager dataManager();
    DatabaseHelper databaseHelper();
}
