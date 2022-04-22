package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.User;
import presentation.views.*;
import presentation.views.components.JSliderUI;
import presentation.views.components.SliderListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayerController implements PlayerViewListener {

    private PlayerView playerView;
    // If a logout is performed we want to change the view to the login one
    private MainViewListener listener;

    // Is this legal?
    User userLoggedIn;

    // Pane controllers
    private final HomeController homeController;
    private final SearchController searchController;
    private final LibraryController libraryController;
    private final AddSongController addSongController;
    private final StatsController statsController;
    private final SongDetailController songDetailController;
    private final PlaylistDetailController playlistDetailController;
    private final MusicPlaybackController musicPlaybackController;
    private final SideMenuController sideMenuController;

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

        HomeView homeView = new HomeView();
        homeController = new HomeController(this, homeView, userManager, songManager, playlistManager);
        homeView.registerController(homeController);

        SearchView searchView =  new SearchView();
        searchController = new SearchController(this, searchView, userManager, songManager, playlistManager);
        searchView.registerController(searchController);

        LibraryView libraryView = new LibraryView();
        libraryController = new LibraryController(this, libraryView, userManager, songManager, playlistManager);
        libraryView.registerController(libraryController);

        // TODO: Create method in Manager to get all the authors
        ArrayList<String> authors = new ArrayList<>();
        authors.add("EODF"); // TODO: Remove hardcoded data (TESTING PURPOSES)
        AddSongView addSongView = new AddSongView(authors);
        addSongController = new AddSongController(this, addSongView, userManager, songManager);
        addSongView.registerController(addSongController);

        StatsView statsView = new StatsView();
        statsController = new StatsController(this, statsView, userManager, songManager, playlistManager);
        statsView.registerController(statsController);

        SongDetailView songDetailView = new SongDetailView();
        songDetailController = new SongDetailController(this, songDetailView, userManager, songManager, playlistManager);
        songDetailView.registerController(songDetailController);

        PlaylistDetailView playlistDetailView = new PlaylistDetailView();
        playlistDetailController = new PlaylistDetailController(this, playlistDetailView, userManager, songManager, playlistManager);
        playlistDetailView.registerController(playlistDetailController);

        MusicPlaybackView musicPlaybackView = new MusicPlaybackView();
        musicPlaybackController = new MusicPlaybackController(musicPlaybackView, songManager);
        musicPlaybackView.registerController(musicPlaybackController);

        SideMenuView sideMenuView = new SideMenuView();
        sideMenuController = new SideMenuController(this, sideMenuView, userManager, playlistManager);
        sideMenuView.registerController(sideMenuController);

        this.playerView.setContents(musicPlaybackView, sideMenuView);
        this.playerView.initCardLayout(homeView, searchView, libraryView, addSongView, statsView, songDetailView, playlistDetailView);
        //this.playerView.changeView(PlayerView.HOME_VIEW);
        this.playerView.changeView(PlayerView.ADD_SONG_VIEW);   // TODO: remove line (TESTING PURPOSES)
    }

    @Override
    public void changeView(String card) {
        playerView.changeView(card);
    }

    @Override
    public void logout() {
        listener.changeView(MainView.CARD_LOG_IN);
    }
}