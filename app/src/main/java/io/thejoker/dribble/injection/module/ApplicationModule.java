package io.thejoker.dribble.injection.module;

import android.app.Application;
import android.content.Context;

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
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5";

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
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalHttUrl = original.url();

                        HttpUrl url = originalHttUrl.newBuilder()
                                .addQueryParameter("APPID", BuildConfig.OPEN_WEATHER_API_KEY)
                                .addQueryParameter("cnt", "7")
                                .build();
                        Request.Builder requestBuilder = original.newBuilder()
                                .url(url);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                }).build();
        return okHttpClient;
    }

        @Singleton
        @Provides
        public WeatherService provideWeatherService(OkHttpClient httpClient) {

            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .baseUrl(BASE_URL)
                    .build();

            return retrofit.create(WeatherService.class);
        }
}