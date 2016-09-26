package io.thejoker.dribble.ui.main;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.thejoker.dribble.data.DataManager;
import io.thejoker.dribble.data.model.database.Location;
import io.thejoker.dribble.data.model.database.Weather;
import io.thejoker.dribble.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by thejoker on 23/9/16.
 */

public class MainPresenter extends BasePresenter<MainMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    MainPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void dettachView() {
        super.dettachView();
        if (mSubscription != null)
            mSubscription.unsubscribe();
    }
}
