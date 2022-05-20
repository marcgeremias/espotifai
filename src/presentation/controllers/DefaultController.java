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

    private DefaultView defaultView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;

    private HomeController homeController;
    private StatsController statsController;

    public DefaultController(DefaultView defaultView, UserManager userManager,
                             SongManager songManager, PlaylistManager playlistManager) {
        this.defaultView = defaultView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;

        HomeView homeView = new HomeView();
        homeController = new HomeController(homeView, songManager, playlistManager);
        homeView.registerController(homeController);

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
            case DefaultView.SETTINGS:
                defaultView.changeView(DefaultView.SETTINGS);
                break;
            default:
                break;
        }
    }

}
