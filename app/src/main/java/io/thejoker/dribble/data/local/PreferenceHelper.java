package io.thejoker.dribble.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.thejoker.dribble.injection.AppContext;

/**
 * Created by thejoker on 11/9/16.
 */

@Singleton
public class PreferenceHelper {

    public static final String PREF_FILE_NAME = "dribble_pref_file";
    private final SharedPreferences mPreferences;

    @Inject
    public PreferenceHelper(@AppContext Context context) {
        this.mPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
    }

    public void clearPreferences(){
        mPreferences.edit().clear().apply();
    }


}
