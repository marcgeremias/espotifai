import business.UserManager;
import business.entities.Playlist;
import persistence.SongDAO;
import persistence.UserDAO;
import persistence.config.DBConfig;
import persistence.postgresql.PlaylistSQL;
import persistence.postgresql.SongSQL;
import persistence.postgresql.UserSQL;
import presentation.controllers.LoginController;
import presentation.controllers.MainController;
import presentation.views.LoginView;
import presentation.views.MainView;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        //MVC
        //Model model = new Model();
        UserManager userManager = new UserManager(new UserSQL());
        MainView mainView = new MainView();
        MainController mainController = new MainController(mainView, userManager);

        mainView.start();
        //...Controller.run();
    }
}