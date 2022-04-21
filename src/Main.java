import business.UserManager;
import persistence.postgresql.UserSQL;
import presentation.controllers.MainController;
import presentation.views.MainView;

public class Main {
    public static void main(String[] args) {
        // We use MVC and invokeLater to ensure the view is created in the context of the EDT
        javax.swing.SwingUtilities.invokeLater(() -> {
            UserManager userManager = new UserManager(new UserSQL());
            MainView mainView = new MainView();
            MainController mainController = new MainController(mainView, userManager);

            mainView.start();
            mainController.run();
        });
    }
}