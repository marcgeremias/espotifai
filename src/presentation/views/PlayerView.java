package presentation.views;

import presentation.controllers.MusicPlaybackController;
import presentation.controllers.PlayerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * PlayerView class that acts as 'frame' containing the card layout and the side and playback views
 */
public class PlayerView extends JPanel {

    public static final String HOME_VIEW = "home_view";
    public static final String SEARCH_VIEW = "search_view";
    public static final String LIBRARY_VIEW = "library_view";
    public static final String ADD_SONG_VIEW = "add_song_view";
    public static final String STATS_VIEW = "stats_view";
    public static final String SONG_DETAIL_VIEW = "song_detail_view";
    public static final String PLAYLIST_DETAIL_VIEW = "playlist_detail_view";
    public static final String USER_PROFILE_VIEW = "user_profile_view";

    public static final Color CENTER_BACKGROUND_COLOR = new Color(35, 35, 35);

    private CardLayout cardManager;

    private JPanel centerPane;

    /**
     * Public constructor that prepares the view
     */
    public PlayerView(){
        super();
        cardManager = new CardLayout();
        centerPane = new JPanel(cardManager);
        setLayout(new BorderLayout());
    }

    /**
     * Method that is called when all the sub-views are already loaded and can be added to the 'frame' panel
     * @param musicPlaybackPane music playback panel
     * @param sideMenuPane side menu panel
     */
    public void setContents(MusicPlaybackView musicPlaybackPane, SideMenuView sideMenuPane){
        add(centerPane, BorderLayout.CENTER);
        add(sideMenuPane, BorderLayout.WEST);
        add(musicPlaybackPane, BorderLayout.SOUTH);
    }

    /**
     * Method called to associate all the views that are included in the card layout
     * @param homeView
     * @param searchView
     * @param libraryView
     * @param addSongView
     * @param statsView
     * @param songDetailView
     * @param playlistDetailView
     */
    public void initCardLayout(HomeView homeView, SearchView searchView, LibraryView libraryView,
                               AddSongView addSongView, StatsView statsView, SongDetailView songDetailView,
                               PlaylistDetailView playlistDetailView, UserProfileView userProfileView){
        centerPane.add(homeView, HOME_VIEW);
        centerPane.add(searchView, SEARCH_VIEW);
        centerPane.add(libraryView, LIBRARY_VIEW);
        centerPane.add(addSongView, ADD_SONG_VIEW);
        centerPane.add(statsView, STATS_VIEW);
        centerPane.add(songDetailView, SONG_DETAIL_VIEW);
        centerPane.add(playlistDetailView, PLAYLIST_DETAIL_VIEW);
        centerPane.add(userProfileView, USER_PROFILE_VIEW);
    }

    /**
     * Method that will change the view from the card manager to the on matching the key string given.
     * @param card key string containing the view to render.
     */
    public void changeView(String card) {
        cardManager.show(centerPane, card);
    }

}