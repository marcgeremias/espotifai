package presentation.views;

import javax.swing.*;
import java.awt.*;

/**
 * PlayerView class that acts as 'frame' containing the card layout and the side and playback views
 */
public class PlayerView extends JPanel {

    public static final String DEFAULT_VIEW = "default_view";
    public static final String SONG_LIST_VIEW = "song_list_view";
    public static final String LIBRARY_VIEW = "library_view";
    public static final String ADD_SONG_VIEW = "add_song_view";
    public static final String SONG_DETAIL_VIEW = "song_detail_view";
    public static final String PLAYLIST_DETAIL_VIEW = "playlist_detail_view";
    public static final String USER_PROFILE_VIEW = "user_profile_view";
    public static final String CREATE_PLAYLIST = "create_playlist_view";

    public static final Color CENTER_BACKGROUND_COLOR = new Color(35, 35, 35);

    private final CardLayout cardManager;

    private final JPanel centerPane;

    /**
     * Public constructor that prepares the view
     */
    public PlayerView() {
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
     * @param defaultView
     * @param songListView
     * @param libraryView
     * @param addSongView
     * @param songDetailView
     * @param playlistDetailView
     * @param userProfileView
     * @param createPlaylistView
     */
    public void initCardLayout(DefaultView defaultView, SongListView songListView, LibraryView libraryView,
                               AddSongView addSongView, SongDetailView songDetailView,
                               PlaylistDetailView playlistDetailView, UserProfileView userProfileView,
                               CreatePlaylistView createPlaylistView){
        centerPane.add(defaultView, DEFAULT_VIEW);
        centerPane.add(songListView, SONG_LIST_VIEW);
        centerPane.add(libraryView, LIBRARY_VIEW);
        centerPane.add(addSongView, ADD_SONG_VIEW);
        centerPane.add(songDetailView, SONG_DETAIL_VIEW);
        centerPane.add(playlistDetailView, PLAYLIST_DETAIL_VIEW);
        centerPane.add(userProfileView, USER_PROFILE_VIEW);
        centerPane.add(createPlaylistView, CREATE_PLAYLIST);
    }

    /**
     * Method that will change the view from the card manager to the on matching the key string given.
     * @param card key string containing the view to render.
     */
    public void changeView(String card) {
        cardManager.show(centerPane, card);
    }
}