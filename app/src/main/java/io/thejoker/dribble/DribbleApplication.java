package io.thejoker.dribble;

import android.app.Application;
import android.content.Context;

import io.thejoker.dribble.injection.component.ApplicationComponent;
import io.thejoker.dribble.injection.component.DaggerApplicationComponent;
import io.thejoker.dribble.injection.module.ApplicationModule;

/**
 * Created by thejoker on 10/9/16.
 */

public class DribbleApplication extends Application {

    public ApplicationComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();



    }
    public ApplicationComponent getComponent(){
        if(mAppComponent == null) {
            mAppComponent =  DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mAppComponent;
    }

    public static DribbleApplication getApplication(Context context){
        return (DribbleApplication) context.getApplicationContext();
    }
}
