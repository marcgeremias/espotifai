package presentation.controllers;

import business.entities.Playlist;

public interface PlayerViewListener {

    /**
     * This method will pass a String containing a key identifier for a card in order to switch the card layout.
     * @param card
     */
    void changeView(String card);

    /**
     * This method is called when user logs out of the account and the mainview needs to be refreshed to the login page
     */
    void logout();

    void showPlaylistDetails(Playlist playlist);
}
