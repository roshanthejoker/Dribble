package io.thejoker.dribble.data.local;

import android.content.ContentValues;

/**
 * Created by thejoker on 22/9/16.
 */

class Db {
    Db(){}


    abstract static class CityTable{
        static final String TABLE_NAME = "location";
        static final String COLUMN_ID = "id";
        static final String COLUMN_LOCATION_NAME = "location_name";
        static final String COLUMN_COORD_LAT = "coord_lat";
        static final String COLUMN_COORD_LON = "coord_lon";
    }

    abstract static class WeatherTable{
        static final String TABLE_NAME = "weather";
        static final String COLUMN_ID = "id";
        static final String COLUMN_LOCATION_ID = "location_id";
        static final String COLUMN_DATE = "date";
        static final String COLUMN_TEMP = "temperature";
        static final String COLUMN_SHORT_DESC = "description";
        static final String COLUMN_TEMP_MIN = "min_temperature";
        static final String COLUMN_TEMP_MAX = "max_temperature";
        static final String COLUMN_PRESSURE = "pressure";
        static final String COLUMN_HUMIDITY = "humidity";
    }

    static final String CREATE_CITY_TABLE = "CREATE TABLE "+CityTable.TABLE_NAME
            +" ("+
            CityTable.COLUMN_ID + " REAL PRIMARY KEY," +
            CityTable.COLUMN_LOCATION_NAME + " TEXT NOT NULL," +
            CityTable.COLUMN_COORD_LAT + " REAL NOT NULL," +
            CityTable.COLUMN_COORD_LON + " REAL NOT NULL " + " );";

    static final String CREATE_WEATHER_TABLE = "CREATE TABLE "+WeatherTable.TABLE_NAME
            +" ("+
            WeatherTable.COLUMN_ID + " INTEGER PRIMARY KEY," +
            WeatherTable.COLUMN_DATE + " INTEGER NOT NULL," +
            WeatherTable.COLUMN_TEMP + " REAL NOT NULL," +
            WeatherTable.COLUMN_TEMP_MIN + " REAL NOT NULL," +
            WeatherTable.COLUMN_TEMP_MAX + " REAL NOT NULL," +
            WeatherTable.COLUMN_PRESSURE + " REAL NOT NULL," +
            WeatherTable.COLUMN_SHORT_DESC + " TEXT NOT NULL," +
            WeatherTable.COLUMN_HUMIDITY + " REAL NOT NULL," +
            "FOREIGN KEY (" + WeatherTable.COLUMN_LOCATION_ID + ")" +
            " REFERENCES " + CityTable.TABLE_NAME + "(" + CityTable.COLUMN_ID + "));";


//
//    static ContentValues LocationToContentValues(City city){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(CityTable.COLUMN_ID,city.getId());
//        contentValues.put(CityTable.COLUMN_LOCATION_NAME,city.getName());
//        contentValues.put(CityTable.COLUMN_COORD_LAT,city.getCoord().getLat());
//        contentValues.put(CityTable.COLUMN_COORD_LON,city.getCoord().getLon());
//        return contentValues;
//    }
//
//    static ContentValues WeatherToContentValues(ForecastList forecast,Weather weather){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(WeatherTable.COLUMN_ID,1);
//        contentValues.put(WeatherTable.COLUMN_DATE,forecast.getDt());
//        contentValues.put(WeatherTable.COLUMN_TEMP,forecast.getMain().getTemp());
//        contentValues.put(WeatherTable.COLUMN_TEMP_MIN,forecast.getMain().getTempMax());
//        contentValues.put(WeatherTable.COLUMN_TEMP_MAX,forecast.getMain().getTempMax());
//        contentValues.put(WeatherTable.COLUMN_PRESSURE,forecast.getMain().getPressure());
//        contentValues.put(WeatherTable.COLUMN_SHORT_DESC,weather.getDescription());
//        contentValues.put(WeatherTable.COLUMN_HUMIDITY,forecast.getMain().getHumidity());
//
//        return contentValues;
//    }
}


