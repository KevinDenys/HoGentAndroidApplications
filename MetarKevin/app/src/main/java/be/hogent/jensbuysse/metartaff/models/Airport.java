package be.hogent.jensbuysse.metartaff.models;

import java.util.List;

import be.hogent.jensbuysse.metartaff.MetarApplication;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Created by eothein on 07.11.17.
 */

@Entity
public class Airport {


    @Id
    private long id;

    /**
     * Vierletterige plaatsindicator volgens de standaarden van de ICAO en de WMO.
     */
    private String  plaatsindicator;

    /**
     * Korte beschrijving van de afkorting (naam van de luchthaven of stad)
     */
    private String beschrijving;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlaatsindicator() {
        return plaatsindicator;
    }

    public void setPlaatsindicator(String plaatsindicator) {
        this.plaatsindicator = plaatsindicator;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public List<Metar> getHistory(MetarApplication app){
        List<Metar> history = app.getBoxStore().boxFor(Metar.class).query().equal(Metar_.airportId,this.id).build().find();
        return history;
    }
}
