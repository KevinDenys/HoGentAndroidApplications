package be.hogent.jensbuysse.metartaff.models;

import java.util.List;

import be.hogent.jensbuysse.metartaff.MetarApplication;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.relation.ToOne;

/**
 * Created by eothein on 07.11.17.
 */


@Entity
public class Metar {

    @Id
    private long id;

    /**
     * raw MetarCode
     */
    private String rawMetar;

    /**
     * Airport related tot the Metar
     */
    @Index
    public ToOne<Airport> airport;

    /**
     * Day of the month
     */
    private int dayOfMonth;

    /**
     * Time
     */
    private String time;

    /**
     * Direction of the wind
     */
    private int windDirection;

    /**
     * The speed of the wind
     */
    private int windSpeed;


    /**
     * Possible gusts
     */
    private int uitSchieters;

    /**
     * Line of sight
     */
    private int sight;

    private String rowName;

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRawMetar() {
        return rawMetar;
    }

    public void setRawMetar(String rawMetar) {
        this.rawMetar = rawMetar;
    }


    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getUitSchieters() {
        return uitSchieters;
    }

    public void setUitSchieters(int uitSchieters) {
        this.uitSchieters = uitSchieters;
    }

    public int getSight() {
        return sight;
    }

    public void setSight(int sight) {
        this.sight = sight;
    }

    public List<Metar> getHistory(MetarApplication app){
        return airport.getTarget().getHistory(app);
    }
}
