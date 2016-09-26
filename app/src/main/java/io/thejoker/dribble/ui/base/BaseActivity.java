package io.thejoker.dribble.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.thejoker.dribble.DribbleApplication;
import io.thejoker.dribble.injection.component.ActivityComponent;
import io.thejoker.dribble.injection.component.DaggerActivityComponent;
import io.thejoker.dribble.injection.module.ActivityModule;

/**
 * Created by thejoker on 11/9/16.
 */

public class BaseActivity extends AppCompatActivity implements MvpView{

    public ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(DribbleApplication.getApplication(this).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }


}
