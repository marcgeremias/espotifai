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

    /**
     * Checks whether a song belongs to the playlist
     * @param song an integer representing the song
     * @return a boolean indicating whether the song is in the playlist
     */
    public boolean containsSong(int song) {
        for (int s : songs) {
            if (s == song) {
                return true;
            }
        }

        return false;
    }

    /**
     * Removes a song from the playlist
     * @param song an integer representing the song to remove
     */
    public void removeSong(int song) {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i) == song) {
                songs.remove(i);
                break;
            }
        }
    }
}