package io.thejoker.dribble.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.thejoker.dribble.data.model.database.Location;
import io.thejoker.dribble.data.model.database.Weather;
import io.thejoker.dribble.injection.AppContext;

/**
 * Created by thejoker on 22/9/16.
 */

@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="forecast.db";
    private static final int DATABASE_VERSION =2;


    @Inject
    DbOpenHelper(@AppContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.beginTransaction();
            db.execSQL(Location.CREATE_TABLE);
            db.execSQL(Weather.CREATE_TABLE);
            db.setTransactionSuccessful();
            System.out.println("OnCreate Called");
        }

        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.delete(Weather.TABLE_NAME,null,null);
        db.delete(Location.TABLE_NAME,null,null);
        onCreate(db);
    }
}
