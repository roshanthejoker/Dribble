package io.thejoker.dribble.ui.base;

/**
 * Created by thejoker on 23/9/16.
 */

public class BasePresenter<T extends MvpView> implements Presenter<T> {
    private T mMvpView;
    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }
    private boolean isViewAttached(){
        return mMvpView!=null;
    }
    public T getmMvpView(){
        return mMvpView;
    }

    public void checkViewAttached(){
        if(!isViewAttached()) throw new MvpViewNotAttachedException();

    }

    private static class MvpViewNotAttachedException extends RuntimeException{
        MvpViewNotAttachedException(){
            super("\"Please call Presenter.attachView(MvpView) before\" +\n" +
                    "                    \" requesting data to the Presenter\"");
        }
    }
}
