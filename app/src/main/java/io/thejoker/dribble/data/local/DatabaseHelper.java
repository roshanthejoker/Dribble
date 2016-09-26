package io.thejoker.dribble.data.local;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;


import javax.inject.Inject;
import javax.inject.Singleton;

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


}
