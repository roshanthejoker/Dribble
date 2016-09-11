package io.thejoker.dribble.injection.component;

import dagger.Component;
import dagger.Subcomponent;
import io.thejoker.dribble.injection.ActivityScope;
import io.thejoker.dribble.injection.module.ActivityModule;
import io.thejoker.dribble.ui.main.MainActivity;

/**
 * Created by thejoker on 11/9/16.
 */
@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
