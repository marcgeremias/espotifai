package business.entities;

import persistence.postgresql.PlaylistSQL;

import java.util.ArrayList;

public class Playlist {

    private int id;
    private String name;
    private String owner;
    private ArrayList<Integer> songs;

    public Playlist(String name, String owner){
        this.name = name;
        this.owner = owner;
    }

    public Playlist(int id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        songs = new ArrayList<>();
    }

    public Playlist(int id, String name, String owner, ArrayList<Integer> songs) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.songs = songs;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<Integer> getSongs() {
        return songs;
    }
}