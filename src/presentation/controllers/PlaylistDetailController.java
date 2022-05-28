package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Playlist;
import business.entities.Song;
import persistence.PlaylistDAOException;
import presentation.views.MainView;
import presentation.views.PlaylistDetailView;
import presentation.views.SignUpView;
import presentation.views.SongDetailView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Public controller for the playlist details view
 */
public class PlaylistDetailController implements ActionListener, MouseListener {
    private final PlayerViewListener listener;
    private final PlaylistDetailView playlistDetailView;
    private final UserManager userManager;
    private final SongManager songManager;
    private final PlaylistManager playlistManager;
    private ArrayList<ArrayList<String>> mySongs;
    private ArrayList<ArrayList<String>> allSongs;
    private ArrayList<String> actualPlaylist;
    private int actualPlaylistID;
    private ArrayList<Integer> songsOrder;

    /**
     * Constructor method for the PlaylistDetailController
     */
    public PlaylistDetailController(PlayerViewListener listener, PlaylistDetailView playlistDetailView,
                                    UserManager userManager,SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.playlistDetailView = playlistDetailView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
        this.actualPlaylistID = -1;
    }

    /**
     * Method that initializes the playlistView by getting all current songs of the system
     * and passing them to the JTable of all songs in the system
     * @param actualPlaylist
     */
    public void initView(ArrayList<String> actualPlaylist) {
        this.actualPlaylist = actualPlaylist;
        actualPlaylistID = Integer.parseInt(actualPlaylist.get(PlaylistManager.PLAYLIST_ID_ATTRIBUTE_INDEX));

        try {
            updateActualPlaylist();
        } catch (PlaylistDAOException e) {
        }

        // Check if the users have permissions
        playlistDetailView.setModify(userManager.getCurrentUser().equals(actualPlaylist.get(PlaylistManager.PLAYLIST_OWNER_ATTRIBUTE_INDEX)));

        allSongs = songManager.getAllSongs();
        playlistDetailView.fillSongsToAdd(allSongs);
    }

    /*
     * Method that updates the songs from the actual playlist
     */
    private void updateActualPlaylist() throws PlaylistDAOException {
        //mySongs = songManager.getAllPlaylistSongs(Integer.parseInt(actualPlaylist.get(PlaylistManager.PLAYLIST_ID_ATTRIBUTE_INDEX)));
        mySongs = songManager.getAllPlaylistSongs(actualPlaylistID);
        //songsOrder = playlistManager.getPlaylistSongsOrder(Integer.parseInt(actualPlaylist.get(PlaylistManager.PLAYLIST_ID_ATTRIBUTE_INDEX)));
        songsOrder = playlistManager.getPlaylistSongsOrder(actualPlaylistID);
        playlistDetailView.fillTable(mySongs, actualPlaylist);
    }

    /**
     * Method of the interface ActionListener that does all the appropriate actions when a button is pressed
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case PlaylistDetailView.BTN_ADD_SONG:
                try {
                    if(!playlistManager.isSongInsidePlaylist(
                            Integer.parseInt(allSongs.get(playlistDetailView.getSelectSong()).get(SongManager.SONG_ID_ATTRIBUTE_INDEX)),
                            songManager.getAllPlaylistSongs(actualPlaylistID))) {
                        int maxOrder=0;
                        if(songsOrder.size() > 0){
                            maxOrder = songsOrder.get(songsOrder.size()-1);
                        }

                        playlistManager.addSongToPlaylist(
                                actualPlaylistID,
                                Integer.parseInt(allSongs.get(playlistDetailView.getSelectSong()).get(SongManager.SONG_ID_ATTRIBUTE_INDEX)),
                                maxOrder+1
                        );
                        updateActualPlaylist();
                    }else{
                        playlistDetailView.notifyError("Song already added!");
                    }
                } catch (PlaylistDAOException ex) {
                }
                break;

            case PlaylistDetailView.BTN_DELATE_SONG:
                try {
                    if(playlistDetailView.getSelectedRow() >= 0){
                        if(playlistManager.isSongInsidePlaylist(Integer.parseInt(
                                mySongs.get(playlistDetailView.getSelectedRow()).get(SongManager.SONG_ID_ATTRIBUTE_INDEX))
                                ,songManager.getAllPlaylistSongs(actualPlaylistID))) {

                            playlistManager.deleteSongFromPlaylist(actualPlaylistID,
                                    Integer.parseInt(mySongs.get(playlistDetailView.getSelectedRow()).get(SongManager.SONG_ID_ATTRIBUTE_INDEX)));
                            updateActualPlaylist();
                        }
                    }
                } catch (PlaylistDAOException ex) {
                }
                break;

            case PlaylistDetailView.BTN_MOVE_UP:
                try {
                    if(playlistDetailView.getSelectedRow() > 0){

                        playlistManager.swapSongsOrder(actualPlaylistID,
                                Integer.parseInt(mySongs.get(playlistDetailView.getSelectedRow()).get(SongManager.SONG_ID_ATTRIBUTE_INDEX)),
                                Integer.parseInt(mySongs.get(playlistDetailView.getSelectedRow()-1).get(SongManager.SONG_ID_ATTRIBUTE_INDEX))
                        );
                    }
                    updateActualPlaylist();
                } catch (PlaylistDAOException ex) {
                }

                break;

            case PlaylistDetailView.BTN_MOVE_DOWN:
                try {
                    if(mySongs == null)break;

                    if(playlistDetailView.getSelectedRow() < mySongs.size()-1
                            && playlistDetailView.getSelectedRow() >= 0){
                        playlistManager.swapSongsOrder(actualPlaylistID,
                                Integer.parseInt(mySongs.get(playlistDetailView.getSelectedRow()).get(SongManager.SONG_ID_ATTRIBUTE_INDEX)),
                                Integer.parseInt(mySongs.get(playlistDetailView.getSelectedRow()+1).get(SongManager.SONG_ID_ATTRIBUTE_INDEX))
                        );
                    }
                    updateActualPlaylist();
                } catch (PlaylistDAOException ex) {
                }
                break;
        }
    }

    /**
     * Override method for the mouse clicked event
     * @param e event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int tableCol = playlistDetailView.getSelectedCol();
        if (tableCol == 0){
            listener.playSong(mySongs, playlistDetailView.getSelectedRow());
        } else if (tableCol == 1){
            listener.showSongDetails(mySongs.get(playlistDetailView.getSelectedRow()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Not used
    }
}
