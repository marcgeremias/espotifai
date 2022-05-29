package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.DefaultView;
import presentation.views.HomeView;
import presentation.views.PlayerView;
import presentation.views.StatsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Public controller to switch between views
 */
public class DefaultController implements ActionListener {

    private DefaultView defaultView;
    private HomeController homeController;
    private StatsController statsController;

    // We need to make the view an attribute due to a dynamic JTable
    private HomeView homeView;

    /**
     * Creates an instance of DefaultController
     * @param listener an instance of PlayerViewListener
     * @param defaultView an instance of DefaultView
     * @param userManager an instance of UserManager
     * @param songManager an instance of SongManager
     * @param playlistManager an instance of PlaylistManager
     */
    public DefaultController(PlayerViewListener listener, DefaultView defaultView, UserManager userManager,
                             SongManager songManager, PlaylistManager playlistManager) {
        this.defaultView = defaultView;

        homeView = new HomeView();
        homeController = new HomeController(listener, homeView, userManager, playlistManager);

        StatsView statsView = new StatsView();
        statsController = new StatsController(statsView, songManager);
        //statsView.registerController(statsController);

        defaultView.initCardLayout(homeView, statsView);
        defaultView.changeView(DefaultView.HOME_VIEW);
    }

    /**
     * Decides which action to execute
     * @param e an instance of ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case DefaultView.MAIN_SECTION:
                defaultView.changeView(DefaultView.HOME_VIEW);
                break;
            case DefaultView.STATISTICS:
                statsController.refreshView();
                defaultView.changeView(DefaultView.STATS_VIEW);
                break;
            default:
                break;
        }
    }

    /**
     * Initializes homeView
     */
    public void initCard() {
        homeController.initView();
        homeView.registerMouseController(homeController);
    }
}