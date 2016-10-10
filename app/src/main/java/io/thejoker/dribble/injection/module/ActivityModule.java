package io.thejoker.dribble.injection.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.thejoker.dribble.injection.ActivityScope;

/**
 * Created by thejoker on 10/9/16.
 */

@ActivityScope
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    Activity provideActivity(){
        return mActivity;
    }

    @Provides
    @ActivityScope
    Context provideContext(){
        return mActivity;
    }


}
