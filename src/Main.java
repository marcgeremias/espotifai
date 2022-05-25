import business.PlayerManager;
import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import persistence.config.APILyrics;
import persistence.postgresql.PlaylistSQL;
import persistence.postgresql.SongSQL;
import persistence.postgresql.UserSQL;
import presentation.controllers.MainController;
import presentation.views.MainView;

import javax.sound.sampled.LineUnavailableException;

public class Main {
    public static void main(String[] args) {
        // We use MVC and invokeLater to ensure the view is created in the context of the EDT
        javax.swing.SwingUtilities.invokeLater(() -> {
            UserSQL userSQL = new UserSQL();
            UserManager userManager = new UserManager(userSQL);
            SongManager songManager = new SongManager(new SongSQL(), userSQL);
            PlaylistManager playlistManager = new PlaylistManager(new PlaylistSQL());
            PlayerManager playerManager = new PlayerManager(songManager);
            MainView mainView = new MainView();
            MainController mainController = new MainController(mainView, userManager, songManager, playlistManager, playerManager);

            mainView.start();
            mainController.run();
        });
    }
}