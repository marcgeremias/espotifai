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
import java.util.ArrayList;

public class PlaylistDetailController implements ActionListener {
    private PlayerViewListener listener;
    private PlaylistDetailView playlistDetailView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;
    private ArrayList<Song> mySongs;
    private ArrayList<Song> allSongs;
    private Playlist actualPlaylist;


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

        updateActualPlaylist();

        // Check if the users have permissions
        playlistDetailView.setModify(userManager.getCurrentUser().equals(actualPlaylist.getOwner()));

        allSongs = songManager.getAllSongs();
        playlistDetailView.fillSongsToAdd(allSongs);
    }


    private void updateActualPlaylist(){
        mySongs = songManager.getAllPlaylistSongs(actualPlaylist.getId());
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
                    if(!playlistManager.isSongInsidePlaylist(allSongs.get(playlistDetailView.getjSelectSong()).getId()
                    ,songManager.getAllPlaylistSongs(actualPlaylist.getId()))) {
                        playlistManager.addSongToPlaylist(actualPlaylist.getId(),
                        allSongs.get(playlistDetailView.getjSelectSong()).getId());
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
                    if(playlistDetailView.getSelectedRow() > 0){
                        if(playlistManager.isSongInsidePlaylist(allSongs.get(playlistDetailView.getSelectedRow()).getId()
                                ,songManager.getAllPlaylistSongs(actualPlaylist.getId()))){
                            playlistManager.deleteSongFromPlaylist(actualPlaylist.getId(),
                                    mySongs.get(playlistDetailView.getjSelectSong()).getId());
                            updateActualPlaylist();
                        }
                    }
                } catch (PlaylistDAOException ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }
}
