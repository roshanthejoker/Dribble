package io.thejoker.dribble.data.model.darksky;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by thejoker on 26/9/16.
 */

public class Forecast {
    @Generated("org.jsonschema2pojo")
    public class Example {

        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;
        @SerializedName("timezone")
        @Expose
        private String timezone;
        @SerializedName("offset")
        @Expose
        private Double offset;
        @SerializedName("daily")
        @Expose
        private DailyForecast daily;

        /**
         * @return The latitude
         */
        public Double getLatitude() {
            return latitude;
        }

        /**
         * @param latitude The latitude
         */
        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        /**
         * @return The longitude
         */
        public Double getLongitude() {
            return longitude;
        }

        /**
         * @param longitude The longitude
         */
        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        /**
         * @return The timezone
         */
        public String getTimezone() {
            return timezone;
        }

        /**
         * @param timezone The timezone
         */
        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        /**
         * @return The offset
         */
        public Double getOffset() {
            return offset;
        }

        /**
         * @param offset The offset
         */
        public void setOffset(Double offset) {
            this.offset = offset;
        }

        /**
         * @return The daily
         */
        public DailyForecast getDaily() {
            return daily;
        }

        /**
         * @param daily The daily
         */
        public void setDaily(DailyForecast daily) {
            this.daily = daily;
        }
    }
}
