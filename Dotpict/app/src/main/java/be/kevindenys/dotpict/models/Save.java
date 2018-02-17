package be.kevindenys.dotpict.models;

import com.google.gson.Gson;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Kevin on 3/12/2017.
 */

//Objectbox kan geen Arrays opslaan en al zeker geen double arrays dus daarom de grid omzetten naar een JSON

@Entity
public class Save {
    @Id
    private long id;
    private String name;
    private String gridJSON;

    public Save(){}
    public Save(String name, Grid grid) {
        this.name = name;
        Gson gson = new Gson();
        //Grid converten naar JSON
        this.gridJSON = gson.toJson(grid);
    }
    public Save(long id, String name, String gridJSON) {
        this.name = name;
        this.id = id;
        this.gridJSON = gridJSON;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //JSON terug naar GRID veranderen
    public Grid getGrid() {
        Gson gson = new Gson();
        return gson.fromJson(gridJSON,Grid.class);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGridJSON() {
        return gridJSON;
    }

}
