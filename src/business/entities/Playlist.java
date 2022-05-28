package business.entities;

import persistence.postgresql.PlaylistSQL;

import java.util.ArrayList;

/**
 * Public class that encapsulates information related to a playlist
 */
public class Playlist {

    private int id;
    private String name;
    private String owner;
    private ArrayList<Integer> songs;

    /**
     * Constructor for Playlist instance
     * @param name String
     * @param owner String
     */
    public Playlist(String name, String owner){
        this.name = name;
        this.owner = owner;
    }

    /**
     * Constructor for Playlist instance
     * @param id int
     * @param name String
     * @param owner String
     */
    public Playlist(int id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        songs = new ArrayList<>();
    }

    /**
     * Constructor for playlist
     * @param id int
     * @param name String
     * @param owner String
     * @param songs List of Songs
     */
    public Playlist(int id, String name, String owner, ArrayList<Integer> songs) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.songs = songs;
    }

    /**
     * Getter for name attribute
     * @return name attribute
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for id attribute
     * @return id attribute
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for owner attribute
     * @return owner attribute
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Getter for list of songs attribute
     * @return list of songs
     */
    public ArrayList<Integer> getSongs() {
        return songs;
    }

}