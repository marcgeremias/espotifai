package business.entities;

import java.util.ArrayList;

public class Playlist {
    private String name;
    private User owner;
    private ArrayList<Song> songs;

    public Playlist(String name, User owner) {
        this.name = name;
        this.owner = owner;
        songs = new ArrayList<>();
    }
}