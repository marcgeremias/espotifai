package presentation.controllers;

import business.entities.Song;

import java.util.ArrayList;

import business.entities.Playlist;

public interface PlayerViewListener {

    void showSongDetails(ArrayList<String> song);

    /**
     * This method will pass a String containing a key identifier for a card in order to switch the card layout.
     * @param card
     */
    void changeView(String card);

    /**
     * This method is called when user logs out of the account and the mainview needs to be refreshed to the login page
     */
    void logout();

    /**
     * This method is called when a song is to be played in the playback interface
     * @param songs list of {@link Song} with all the songs in the playlist
     * @param index index representing the position of the song to be played in the playback interface
     */
    void playSong(ArrayList<ArrayList<String>> songs, int index);

    void showPlaylistDetails(Playlist playlist);

    /**
     * This method is called when a user wants to delete its account
     */
    void delete();

    /**
     * Notifies that a song has been deleted
     */
    void songWasDeleted();
}
