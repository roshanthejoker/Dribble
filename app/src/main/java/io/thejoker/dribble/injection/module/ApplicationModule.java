package io.thejoker.dribble.injection.module;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.thejoker.dribble.BuildConfig;
import io.thejoker.dribble.data.remote.WeatherService;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thejoker on 10/9/16.
 */
@Singleton
@Module
public class ApplicationModule {

    private Application mApplication;
    public static final String BASE_URL =
            "https://api.darksky.net/forecast/"+BuildConfig.DARK_SKY_API_KEY+"/";

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Singleton
    @Provides
    Application provideApplication(){
        return mApplication;
    }

    @Singleton
    @Provides
     Context provideAppContext(){
        return mApplication.getApplicationContext();
    }


    @Singleton
    @Provides
    OkHttpClient provideHttpClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        OkHttpClient okHttpClient = httpClient.build();

        return okHttpClient;
    }

        @Singleton
        @Provides
        public WeatherService provideWeatherService(OkHttpClient okHttpClient) {

            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build();
            return retrofit.create(WeatherService.class);
        }
}