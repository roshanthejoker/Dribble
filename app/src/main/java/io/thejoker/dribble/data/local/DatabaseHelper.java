package io.thejoker.dribble.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.thejoker.dribble.data.model.darksky.Forecast;
import io.thejoker.dribble.data.model.darksky.MainData;
import io.thejoker.dribble.data.model.database.WeatherWithLocation;
import io.thejoker.dribble.data.model.database.Location;
import io.thejoker.dribble.data.model.database.Weather;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by thejoker on 23/9/16.
 */

@Singleton
public class DatabaseHelper {
    private final BriteDatabase mDb;

    @Inject
    DatabaseHelper(DbOpenHelper openHelper) {
        this.mDb = SqlBrite.create().wrapDatabaseHelper(openHelper, Schedulers.io());
    }

    public void saveForecastToDb(Forecast forecast) {
        final List<MainData> mainDataList = forecast.getDaily().getData();
        System.out.println(mainDataList.size());
        BriteDatabase.Transaction transaction = mDb.newTransaction();
        try {
            mDb.delete(Weather.TABLE_NAME,null);
            for (MainData item : mainDataList) {
                long result = mDb.insert(Weather.TABLE_NAME, Weather.WEATHER_FACTORY.marshal()
                        .location_id(1)
                        .date(item.getTime())
                        .minTemperature(item.getTemperatureMin())
                        .maxTemperature(item.getTemperatureMax())
                        .icon(item.getIcon())
                        .summary(item.getSummary())
                        .humidity(item.getHumidity())
                        .pressure(item.getPressure())
                        .asContentValues(), SQLiteDatabase.CONFLICT_REPLACE);

                System.out.println("Result: " + result);
                if (result >= 0) {
                    System.out.println("Data inserted Successfully");

                } else {
                    System.out.println("Data not inserted into Database");
                }
            }

            transaction.markSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            transaction.end();
        }
        BriteDatabase.Transaction transaction1 = mDb.newTransaction();
        try {
            mDb.delete(Location.TABLE_NAME,null);
            long locationResult = mDb.insert(Location.TABLE_NAME, Location.LOCATION_FACTORY.marshal()
                    .coordLon(forecast.getLongitude())
                    .coordLat(forecast.getLatitude())
                    .location_id(1L)
                    .asContentValues(), SQLiteDatabase.CONFLICT_REPLACE);
            if (locationResult >= 0) {
                System.out.println("Location Data inserted Successfully");

            } else {
                System.out.println("Data not inserted into Database");
            }
            transaction1.markSuccessful();
        } catch (Exception e)

        {
            e.printStackTrace();
        } finally {
            transaction1.end();
        }
    }


        public Observable<List<WeatherWithLocation>> fetchWeatherWithLocation() {
            return mDb.createQuery(Arrays.asList(Location.TABLE_NAME,Weather.TABLE_NAME),
                    WeatherWithLocation.SELECT_ALL)
                    .mapToList(new Func1<Cursor, WeatherWithLocation>() {
                        @Override
                        public WeatherWithLocation call(Cursor cursor) {
                            return WeatherWithLocation.map(cursor);
                        }
                    });
        }

    }

