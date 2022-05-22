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

public class DefaultController implements ActionListener {

    private PlayerViewListener listener;
    private DefaultView defaultView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;

    private HomeController homeController;
    private StatsController statsController;

    // We need to make the view an attribute due to a dynamic JTable
    private HomeView homeView;

    public DefaultController(PlayerViewListener listener, DefaultView defaultView, UserManager userManager,
                             SongManager songManager, PlaylistManager playlistManager) {
        this.defaultView = defaultView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
        this.listener = listener;

        homeView = new HomeView();
        homeController = new HomeController(listener, homeView, userManager, songManager, playlistManager);

        StatsView statsView = new StatsView();
        statsController = new StatsController(statsView, songManager);
        //statsView.registerController(statsController);

        defaultView.initCardLayout(homeView, statsView);
        defaultView.changeView(DefaultView.HOME_VIEW);
    }

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

    /*
     * Method that initializes all the data needed when accessing into a view
     * @param card the ID of the card of the view we are initializing
     */
    public void initCard() {
           // case DefaultView.HOME_VIEW:
                homeController.initView();
                // We need to register the controller every time due to the dynamic JTable
                homeView.registerMouseController(homeController);
             //   break;
            //case DefaultView.STATS_VIEW:
              //  break;

    }

}
