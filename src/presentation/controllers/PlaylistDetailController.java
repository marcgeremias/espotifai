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

public class PlaylistDetailController implements ActionListener, MouseListener {
    private PlayerViewListener listener;
    private PlaylistDetailView playlistDetailView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;
    private ArrayList<Song> mySongs;
    private ArrayList<Song> allSongs;
    private Playlist actualPlaylist;
    private ArrayList<Integer> songsOrder;


    public PlaylistDetailController(PlayerViewListener listener, PlaylistDetailView playlistDetailView,
                                    UserManager userManager,SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.playlistDetailView = playlistDetailView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    /**
     * Method that initializes the playlistView by getting all current songs of the system
     * and passing them to the JTable of all songs in the system
     * @param actualPlaylist
     */
    public void initView(Playlist actualPlaylist) {
        this.actualPlaylist = actualPlaylist;

        try {
            updateActualPlaylist();
        } catch (PlaylistDAOException e) {
            e.printStackTrace();
        }


        // Check if the users have permissions
        playlistDetailView.setModify(userManager.getCurrentUser().equals(actualPlaylist.getOwner()));

        allSongs = songManager.getAllSongs();
        playlistDetailView.fillSongsToAdd(allSongs);
    }


    /**
     * Method that updates the songs from the actual playlist
     */
    private void updateActualPlaylist() throws PlaylistDAOException {
        mySongs = songManager.getAllPlaylistSongs(actualPlaylist.getId());
        songsOrder = playlistManager.getPlaylistSongsOrder(actualPlaylist.getId());
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
                    if(!playlistManager.isSongInsidePlaylist(allSongs.get(playlistDetailView.getSelectSong()).getId()
                    ,songManager.getAllPlaylistSongs(actualPlaylist.getId()))) {
                        int maxOrder=0;
                        if(songsOrder.size() > 0){
                            maxOrder = songsOrder.get(songsOrder.size()-1);
                        }

                        playlistManager.addSongToPlaylist(actualPlaylist.getId(),
                        allSongs.get(playlistDetailView.getSelectSong()).getId(), maxOrder+1);
                        updateActualPlaylist();
                    }else{
                        playlistDetailView.notifyError("Song already added!");
                    }
                } catch (PlaylistDAOException ex) {
                    ex.printStackTrace();
                }
                break;

            case PlaylistDetailView.BTN_DELATE_SONG:
                try {
                    if(playlistDetailView.getSelectedRow() >= 0){
                        if(playlistManager.isSongInsidePlaylist(mySongs.get(playlistDetailView.getSelectedRow()).getId()
                                ,songManager.getAllPlaylistSongs(actualPlaylist.getId()))){

                            playlistManager.deleteSongFromPlaylist(actualPlaylist.getId(),
                                    mySongs.get(playlistDetailView.getSelectedRow()).getId());
                            updateActualPlaylist();
                        }
                    }
                } catch (PlaylistDAOException ex) {
                    ex.printStackTrace();
                }
                break;

            case PlaylistDetailView.BTN_MOVE_UP:
                try {
                    if(playlistDetailView.getSelectedRow() > 0){

                        playlistManager.swapSongsOrder(actualPlaylist.getId(),
                                mySongs.get(playlistDetailView.getSelectedRow()).getId(),
                                mySongs.get(playlistDetailView.getSelectedRow()-1).getId());
                    }
                    updateActualPlaylist();
                } catch (PlaylistDAOException ex) {
                    ex.printStackTrace();
                }

                break;

            case PlaylistDetailView.BTN_MOVE_DOWN:
                try {
                    if(playlistDetailView.getSelectedRow() < mySongs.size()-1){
                        playlistManager.swapSongsOrder(actualPlaylist.getId(),
                                mySongs.get(playlistDetailView.getSelectedRow()).getId(),
                                mySongs.get(playlistDetailView.getSelectedRow()+1).getId());
                    }
                    updateActualPlaylist();
                } catch (PlaylistDAOException ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }

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

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
