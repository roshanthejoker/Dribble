package io.thejoker.dribble.ui.main;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.thejoker.dribble.R;
import io.thejoker.dribble.data.model.database.Weather;
import io.thejoker.dribble.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainMvpView
{

@BindView(R.id.text)
    TextView mTextView;
@Inject
    MainPresenter mPresenter;

    List<Weather> mWeather;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mActivityComponent.inject(this);
        setContentView(R.layout.activity_main);
        mPresenter.attachView(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
//        mPresenter.dettachView();
    }

    @Override
    public void showWeather(List<Weather> weather) {
        mWeather = new ArrayList<>(weather.size());
        Toast.makeText(this,weather.get(0).description(),Toast.LENGTH_LONG).show();
        mTextView.setText(mWeather.get(0).description());
    }

    @Override
    public void showError() {
        Toast.makeText(this,"Oops! Something went wrong",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmpty() {

    }
}
