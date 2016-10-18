package io.thejoker.dribble.data.model.database;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;

import io.thejoker.dribble.LocationModel;

/**
 * Created by thejoker on 26/9/16.
 */

@AutoValue
public abstract class Location implements LocationModel {

    public static final Factory<Location> LOCATION_FACTORY = new Factory<>(
            (location_id, coordLat, coordLon) -> new AutoValue_Location.Builder()
                    .setLocation_id(location_id)
                    .setCoordLat(coordLat)
                    .setCoordLon(coordLon)
                    .build());

    static final RowMapper<Location> LOCATION_ROW_MAPPER = LOCATION_FACTORY.select_locationMapper();


    public static Builder builder() {
        return new AutoValue_Location.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setLocation_id(Long value);

        public abstract Builder setCoordLat(double value);

        public abstract Builder setCoordLon(double value);

        public abstract Location build();
    }
}
