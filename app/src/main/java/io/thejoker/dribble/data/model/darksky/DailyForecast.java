package io.thejoker.dribble.data.model.darksky;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thejoker on 26/9/16.
 */

public class DailyForecast {

    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("data")
    @Expose
    private List<MainData> data = new ArrayList<MainData>();

    /**
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return The data
     */
    public List<MainData> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<MainData> data) {
        this.data = data;
    }

}
