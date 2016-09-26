package io.thejoker.dribble.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;
import io.thejoker.dribble.injection.AppContext;

/**
 * Created by thejoker on 22/9/16.
 */

@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="forecast.db";
    private static final int DATABASE_VERSION =1;


    @Inject
    DbOpenHelper(@AppContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }
}
