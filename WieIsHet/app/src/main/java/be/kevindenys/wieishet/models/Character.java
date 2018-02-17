package be.kevindenys.wieishet.models;

import java.io.Serializable;

/**
 * Created by Kevin on 30-10-2017.
 */

public class Character implements Serializable {
    private int databaseID;
    private int gameID;
    private int characterImage;
    private String name;
    private String description;

    public Character(int databaseID, int gameID, int characterImage, String name, String description) {
        this.databaseID = databaseID;
        this.gameID = gameID;
        this.characterImage = characterImage;
        this.name = name;
        this.description = description;
    }

    public int getCharacterImage() {
        return characterImage;
    }

    public int getDatabaseID() {
        return databaseID;
    }

    public void setDatabaseID(int databaseID) {
        this.databaseID = databaseID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setCharacterImage(int characterImage) {
        this.characterImage = characterImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

}
