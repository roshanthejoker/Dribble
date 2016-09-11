package io.thejoker.dribble.data.model.forecast;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thejoker on 10/9/16.
 */




@Generated("org.jsonschema2pojo")
public class ForecastMain {

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<ForecastList> list = new ArrayList<ForecastList>();

    /**
     *
     * @return
     * The city
     */
    public City getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The cod
     */
    public String getCod() {
        return cod;
    }

    /**
     *
     * @param cod
     * The cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     *
     * @return
     * The message
     */
    public Double getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(Double message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The cnt
     */
    public Integer getCnt() {
        return cnt;
    }

    /**
     *
     * @param cnt
     * The cnt
     */
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    /**
     *
     * @return
     * The list
     */
    public List<ForecastList> getList() {
        return list;
    }

    /**
     *
     * @param list
     * The list
     */
    public void setList(List<ForecastList> list) {
        this.list = list;
    }

}