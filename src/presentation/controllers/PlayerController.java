package presentation.controllers;

import business.PlayerManager;
import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Song;
import business.entities.Playlist;
import business.entities.User;
import persistence.SongDAOException;
import persistence.UserDAOException;
import presentation.views.*;

import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Public controller that manages the different views
 */
public class PlayerController implements PlayerViewListener {

    private final PlayerView playerView;
    // If a logout is performed we want to change the view to the login one
    private final MainViewListener listener;

    // Pane controllers
    private final DefaultController defaultController;
    private final SongListController songListController;
    private final LibraryController libraryController;
    private final AddSongController addSongController;
    private final SongDetailController songDetailController;
    private final PlaylistDetailController playlistDetailController;
    private final UserProfileController userProfileController;
    private final MusicPlaybackController musicPlaybackController;
    private final SideMenuController sideMenuController;
    private final CreatePlaylistController createPlaylistController;

    private final UserManager userManager;
    private final PlayerManager playerManager;
    private final SongManager songManager;

    /**
     * This method initializes all the view necessary for the Main execution of the program
     * @param listener MainViewListener
     * @param playerView view associated with the controller in this case acting as a fake frame
     * @param userManager userManager for accessing user data
     * @param songManager songManager for accessing song data
     * @param playlistManager playlistManager for accessing playlist data
     */
    public PlayerController(MainViewListener listener, PlayerView playerView, UserManager userManager,
                            SongManager songManager, PlaylistManager playlistManager, PlayerManager playerManager) {
        this.listener = listener;
        this.playerView = playerView;
        this.userManager = userManager;
        this.playerManager = playerManager;
        this.songManager = songManager;
        DefaultView defaultView = new DefaultView();

        defaultController = new DefaultController(this, defaultView, userManager, songManager, playlistManager);
        defaultView.registerController(defaultController);

        // We need to make the view an attribute due to a dynamic JTable
        SongListView songListView = new SongListView();
        songListController = new SongListController(this, songListView, songManager, playlistManager);
        songListView.registerKeyController(songListController);
        songListView.registerMouseController(songListController);

        LibraryView libraryView = new LibraryView();
        libraryController = new LibraryController(this, libraryView, userManager, playlistManager);
        libraryView.registerMouseController(libraryController);

        SideMenuView sideMenuView = new SideMenuView();
        sideMenuController = new SideMenuController(this);
        sideMenuView.registerController(sideMenuController);

        AddSongView addSongView = new AddSongView();
        addSongController = new AddSongController(this, addSongView, userManager, songManager);
        addSongView.registerController(addSongController);

        SongDetailView songDetailView = new SongDetailView();
        songDetailController = new SongDetailController(this, songDetailView, userManager, songManager, playlistManager);
        songDetailView.registerController(songDetailController);

        PlaylistDetailView playlistDetailView = new PlaylistDetailView();
        playlistDetailController = new PlaylistDetailController(this, playlistDetailView, userManager, songManager, playlistManager);
        playlistDetailView.registerController(playlistDetailController);
        playlistDetailView.registerMouseController(playlistDetailController);

        UserProfileView userProfileView = new UserProfileView();
        userProfileController = new UserProfileController(this, userProfileView, songManager, playerManager);
        userProfileView.registerController(userProfileController);

        MusicPlaybackView musicPlaybackView = new MusicPlaybackView();
        musicPlaybackController = new MusicPlaybackController(musicPlaybackView, songManager, playerManager);
        musicPlaybackView.registerController(musicPlaybackController);

        CreatePlaylistView createPlaylistView = new CreatePlaylistView();
        createPlaylistController = new CreatePlaylistController(this, createPlaylistView, userManager, playlistManager);
        createPlaylistView.registerController(createPlaylistController);

        this.playerView.setContents(musicPlaybackView, sideMenuView);
        this.playerView.initCardLayout(defaultView, songListView, libraryView, addSongView,
                                        songDetailView, playlistDetailView, userProfileView, createPlaylistView);
        this.playerView.changeView(PlayerView.DEFAULT_VIEW);
    }

    /**
     * Displays the attributes of a song
     * @param song an ArrayList of String containing the attributes
     */
    @Override
    public void showSongDetails(ArrayList<String> song) {
        songDetailController.initView(song);
        playerView.revalidate();
        playerView.changeView(PlayerView.SONG_DETAIL_VIEW);
    }

    /**
     * Changes the current view
     * @param card a String containing the view to display
     */
    @Override
    public void changeView(String card) {
        initCard(card);
        playerView.revalidate();
        playerView.changeView(card);
    }

    /*
     * Method that initializes all the data needed when accessing into a view
     * @param card the ID of the card of the view we are initializing
     */
    private void initCard(String card) {
        switch (card){
            case PlayerView.DEFAULT_VIEW:
                defaultController.initCard();
                break;
            case PlayerView.SONG_LIST_VIEW:
                songListController.initView();
                break;
            case PlayerView.LIBRARY_VIEW:
                libraryController.initView();
                // We need to register the controller every time due to the dynamic JTable
                break;
            case PlayerView.ADD_SONG_VIEW:
                addSongController.initView();
                break;
            case PlayerView.SONG_DETAIL_VIEW:
                //System.out.println("SONG DETAIL VIEW");
                //songDetailController.initView(songListController.getSongNum());
                break;
            case PlayerView.PLAYLIST_DETAIL_VIEW:
                break;
            case PlayerView.USER_PROFILE_VIEW:
                userProfileController.setNickname(userManager.getCurrentUser());
                break;

            case PlayerView.CREATE_PLAYLIST:
                break;
        }
    }

    /**
     * Logs out the current user
     */
    @Override
    public void logout() {
        userManager.logOutUser();
        playerManager.clearData();
        musicPlaybackController.clearData();
        listener.changeView(MainView.CARD_LOG_IN);
        playerView.changeView(DefaultView.HOME_VIEW);
    }

    /**
     * Plays a song
     * @param songs list of {@link Song} with all the songs in the playlist
     * @param index index representing the position of the song to be played in the playback interface
     */
    @Override
    public void playSong(ArrayList<ArrayList<String>> songs, int index) {
        musicPlaybackController.initSongPlaylist(songs, index);
    }

    /**
     * Initializes the homeView
     */
    public void initHomeView() {
        defaultController.initCard();
        playerView.revalidate();
        playerView.changeView(PlayerView.DEFAULT_VIEW);
    }

    /**
     * Displays the attributes of a playlist
     * @param playlistId an ArrayList of String containing the attributes
     */
    @Override
    public void showPlaylistDetails(ArrayList<String> playlistId) {
        playerView.changeView(PlayerView.PLAYLIST_DETAIL_VIEW);
        playlistDetailController.initView(playlistId);
    }

    /**
     * Deletes all data from a user and the user itself
     */
    @Override
    public void delete() {
        try {
            songManager.deleteAllSongsFromUser(userManager.getCurrentUser());
            playerManager.clearData();
            musicPlaybackController.clearData();
            userManager.deleteUser();
            userManager.logOutUser();
            listener.changeView(MainView.CARD_LOG_IN);
            playerView.changeView(PlayerView.DEFAULT_VIEW);
        } catch (UserDAOException | SongDAOException ignored) {
        }
    }

    /**
     * Clears reproductor when song deleted
     */
    @Override
    public void songWasDeleted() {
        playerManager.clearData();
        musicPlaybackController.clearData();
    }
}