package business.entities;

import persistence.postgresql.PlaylistSQL;

import java.util.ArrayList;

public class Playlist {

    private int id;
    private String name;
    private User owner;
    private ArrayList<Song> songs;

    public Playlist(String name, User owner){
        this.name = name;
        this.owner = owner;
    }

    public Playlist(int id, String name, User owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        songs = new ArrayList<>();
    }

    public Playlist(int id, String name, User owner, ArrayList<Song> songs) {
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

    public User getOwner() {
        return owner.clone();
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
}