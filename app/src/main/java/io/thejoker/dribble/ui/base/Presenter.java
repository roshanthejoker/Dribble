package io.thejoker.dribble.ui.base;

/**
 * Created by thejoker on 23/9/16.
 */

public interface Presenter<V extends MvpView> {
    void attachView(V mvpView);
    void detachView();
}
