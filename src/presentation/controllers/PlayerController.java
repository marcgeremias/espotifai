package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.User;
import presentation.views.*;

public class PlayerController implements PlayerViewListener {

    private PlayerView playerView;
    // If a logout is performed we want to change the view to the login one
    private MainViewListener listener;

    // Is this legal?
    User userLoggedIn;

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
    private UserManager userManager;
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
                            SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.playerView = playerView;
        this.userManager = userManager;
        DefaultView defaultView = new DefaultView();

        defaultController = new DefaultController(this, defaultView, userManager, songManager, playlistManager);
        defaultView.registerController(defaultController);

        songListView =  new SongListView();
        songListController = new SongListController(this, songListView, userManager, songManager, playlistManager);
        songListView.registerKeyController(songListController);

        libraryView = new LibraryView();
        libraryController = new LibraryController(this, libraryView, userManager, songManager, playlistManager);

        AddSongView addSongView = new AddSongView(songManager.getAuthors());
        addSongController = new AddSongController(this, addSongView, userManager, songManager);
        addSongView.registerController(addSongController);

        SongDetailView songDetailView = new SongDetailView();
        songDetailController = new SongDetailController(this, songDetailView, userManager, songManager, playlistManager);
        songDetailView.registerController(songDetailController);

        PlaylistDetailView playlistDetailView = new PlaylistDetailView(this);
        playlistDetailController = new PlaylistDetailController(this, playlistDetailView, userManager, songManager, playlistManager);
        playlistDetailView.registerController(playlistDetailController);

        UserProfileView userProfileView = new UserProfileView();
        userProfileController = new UserProfileController(this, userProfileView, userManager, songManager, playlistManager);
        userProfileView.registerController(userProfileController);

        MusicPlaybackView musicPlaybackView = new MusicPlaybackView();
        musicPlaybackController = new MusicPlaybackController(musicPlaybackView, songManager);
        musicPlaybackView.registerController(musicPlaybackController);

        SideMenuView sideMenuView = new SideMenuView();
        sideMenuController = new SideMenuController(this, sideMenuView, userManager, playlistManager, songManager);
        sideMenuView.registerController(sideMenuController);

        this.playerView.setContents(musicPlaybackView, sideMenuView);
        this.playerView.initCardLayout(defaultView, songListView, libraryView, addSongView,
                                        songDetailView, playlistDetailView, userProfileView);
        this.playerView.changeView(PlayerView.DEFAULT_VIEW);
    }

    @Override
    public void changeView(String card) {
        initCard(card);
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
                // We need to register the controller every time due to the dynamic JTable
                songListView.registerMouseController(songListController);
                break;
            case PlayerView.LIBRARY_VIEW:
                libraryController.initView();
                // We need to register the controller every time due to the dynamic JTable
                libraryView.registerMouseController(libraryController);
                break;
            case PlayerView.ADD_SONG_VIEW:
                break;
            case PlayerView.SONG_DETAIL_VIEW:
                break;
            case PlayerView.PLAYLIST_DETAIL_VIEW:
                break;
            case PlayerView.USER_PROFILE_VIEW:
                break;
        }
    }

    @Override
    public void logout() {
        userManager.logOutUser();
        listener.changeView(MainView.CARD_LOG_IN);
    }
}