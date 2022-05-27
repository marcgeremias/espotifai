package presentation.controllers;

import business.PlayerManager;
import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Song;
import business.entities.Playlist;
import business.entities.User;
import persistence.UserDAOException;
import presentation.views.*;

import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PlayerController implements PlayerViewListener {

    private PlayerView playerView;
    // If a logout is performed we want to change the view to the login one
    private MainViewListener listener;

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

    private UserManager userManager;
    private PlayerManager playerManager;
    // We need to make the view an attribute due to a dynamic JTable
    private SongListView songListView;
    private LibraryView libraryView;

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
        DefaultView defaultView = new DefaultView();

        defaultController = new DefaultController(this, defaultView, userManager, songManager, playlistManager);
        defaultView.registerController(defaultController);

        songListView =  new SongListView();
        songListController = new SongListController(this, songListView, userManager, songManager, playlistManager);
        songListView.registerKeyController(songListController);
        songListView.registerMouseController(songListController);

        libraryView = new LibraryView();
        libraryController = new LibraryController(this, libraryView, userManager, songManager, playlistManager);
        libraryView.registerMouseController(libraryController);

        SideMenuView sideMenuView = new SideMenuView();
        sideMenuController = new SideMenuController(this, sideMenuView, userManager, playlistManager, songManager);
        sideMenuView.registerController(sideMenuController);

        AddSongView addSongView = new AddSongView(songManager.getAuthors());
        addSongController = new AddSongController(this, addSongView, userManager, songManager);
        addSongView.registerController(addSongController);

        SongDetailView songDetailView = new SongDetailView();
        songDetailController = new SongDetailController(this, songDetailView, userManager, songManager, playlistManager);
        songDetailView.registerController(songDetailController);

        PlaylistDetailView playlistDetailView = new PlaylistDetailView();
        playlistDetailController = new PlaylistDetailController(this, playlistDetailView, userManager, songManager, playlistManager);
        playlistDetailView.registerController(playlistDetailController);

        UserProfileView userProfileView = new UserProfileView();
        userProfileController = new UserProfileController(this, userProfileView, userManager, songManager, playlistManager);
        userProfileView.registerController(userProfileController);

        MusicPlaybackView musicPlaybackView = new MusicPlaybackView();
        musicPlaybackController = new MusicPlaybackController(musicPlaybackView, songManager, playerManager);
        musicPlaybackView.registerController(musicPlaybackController);

        CreatePlaylistView createPlaylistView = new CreatePlaylistView();
        createPlaylistController = new CreatePlaylistController(this, createPlaylistView, userManager, songManager, playlistManager);
        createPlaylistView.registerController(createPlaylistController);

        this.playerView.setContents(musicPlaybackView, sideMenuView);
        this.playerView.initCardLayout(defaultView, songListView, libraryView, addSongView,
                                        songDetailView, playlistDetailView, userProfileView, createPlaylistView);
        this.playerView.changeView(PlayerView.DEFAULT_VIEW);
    }

    @Override
    public void showSongDetails(Song song) {
        songDetailController.initView(song);
        playerView.revalidate();
        playerView.changeView(PlayerView.SONG_DETAIL_VIEW);
    }

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

    @Override
    public void logout() {
        userManager.logOutUser();
        playerManager.clearData();
        musicPlaybackController.clearData();
        listener.changeView(MainView.CARD_LOG_IN);
        playerView.changeView(DefaultView.HOME_VIEW);
    }

    @Override
    public void playSong(ArrayList<Song> songs, int index) {
        musicPlaybackController.initSongPlaylist(songs, index);
    }

    public void initHomeView() {
        defaultController.initCard();
        playerView.revalidate();
        playerView.changeView(PlayerView.DEFAULT_VIEW);
    }

    @Override
    public void showPlaylistDetails(Playlist playlistId) {
        playerView.changeView(PlayerView.PLAYLIST_DETAIL_VIEW);
        playlistDetailController.initView(playlistId);
    }

    @Override
    public void delete() {
        try {
            userManager.deleteUser();
            userManager.logOutUser();
            listener.changeView(MainView.CARD_LOG_IN);
            playerView.changeView(PlayerView.DEFAULT_VIEW);
        } catch (UserDAOException e) {
            e.printStackTrace();
        }
    }


}