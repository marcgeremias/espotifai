package presentation.controllers;

import business.PlayerManager;
import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.*;

import static presentation.views.MainView.CARD_PLAYER;

/**
 * Public controller that switches between the principal views
 */
public class MainController implements MainViewListener {

    private MainView mainView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;
    private PlayerManager playerManager;
    private LoginController loginController;
    private SignUpController signUpController;
    private PlayerController playerController;

    /**
     * Constructor initializing the MainController necessari attributes
     * @param mainView the MainView class
     * @param userManager the UserManager class
     */
    public MainController(MainView mainView, UserManager userManager, SongManager songManager,
                          PlaylistManager playlistManager, PlayerManager playerManager) {
        this.mainView = mainView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
        this.playerManager = playerManager;
    }

    /**
     * Method that starts executing the program
     */
    public void run() {

        // Defining login view and controller
        LoginView loginView = new LoginView();
        loginController = new LoginController(this, loginView, userManager);
        loginView.registerController(loginController, loginController);

        // Defining signup view and controller
        SignUpView signUpView = new SignUpView();
        signUpController = new SignUpController(this, signUpView, userManager);
        signUpView.registerController(signUpController);

        // Defining player view
        // PlayerView acts as a 'frame' and there are no buttons or actions associated with this view because it
        // is formed by other views with their respective controllers therefore it doesn't need to register a controller.
        PlayerView playerView = new PlayerView();
        playerController = new PlayerController(this, playerView, userManager, songManager, playlistManager, playerManager);

        // Defining views in the card layout of the JFrame MainView
        mainView.initCardLayout(loginView, signUpView, playerView);

        mainView.changeView(MainView.CARD_LOG_IN);
    }

    /**
     * Changes the current view
     * @param card the name put on the card layout that referes to a specific view
     */
    @Override
    public void changeView(String card) {
        initViews(card);
        mainView.changeView(card);
    }

    /**
     * Method that inits all the views needed of the program
     * @param card the identifier of a view inside a cardLayout
     */
    public void initViews(String card) {
        if (CARD_PLAYER.equals(card)) {
            playerController.initHomeView();
        }
    }
}
