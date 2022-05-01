package presentation.views;

import presentation.views.components.JImagePanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SideMenuView extends JPanel {
    private static final Color THEME = new Color(0x171616);

    private String pathHomeNormal = "res/icons/home.png";
    private String pathHomeHover = "res/icons/home_hover.png";
    private String pathSearchNormal = "res/icons/search.png";
    private String pathSearchHover = "res/icons/search_hover.png";
    private String pathLibraryNormal = "res/icons/library.png";
    private String pathLibraryHover = "res/icons/library_hover.png";

    public static final String HOME_BUTTON = "home_button_presses";
    public static final String SEARCH_BUTTON = "search_button_presses";
    public static final String LIBRARY_BUTTON = "library_button_presses";

    //Main section
    private JImagePanel homeJbutton;
    private JImagePanel searchJbutton;
    private JImagePanel libraryJbutton;

    //Secondary section
    private JImagePanel addMusicJbutton;
    private JImagePanel deleteMusicJbutton;

    //Last section
    private Button logOutJbutton; //HoverButton

    public SideMenuView() {
        configurePanel();
    }

    private void configurePanel() {
        this.setLayout(new BorderLayout()); // Setting of a Border Layout which structures the whole menu
        this.add(lateralMenu(), BorderLayout.WEST);
    }

    private Component lateralMenu() {
        JPanel bufferJPanel = new JPanel();
        bufferJPanel.setBackground(Color.BLACK);
        bufferJPanel.add(mainSection());
        bufferJPanel.add(playlistSection());
        bufferJPanel.add(logOutSection());

        return bufferJPanel;
    }

    private Component mainSection() {
        JPanel menu = new JPanel();

        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(homeOption());
        menu.add(searchOption());
        menu.add(libraryOption());

        menu.setOpaque(false);
        return menu;
    }

    /**
     * Metode que s'encarrega dels marges entre les opcions de la Main Section
     * @return el contenidor amb el panell del marge (sense opacitat)
     */
    public static Container margin() {
        JPanel upMargin = new JPanel();
        upMargin.setOpaque(false);
        upMargin.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        return upMargin;
    }

    private Component homeOption() {
        homeJbutton = new JImagePanel(pathHomeNormal, pathHomeHover, null);
        homeJbutton.setPreferredSize(new Dimension(84,20));
        homeJbutton.setOpaque(false);

        JPanel home = new JPanel();
        home.setBackground(Color.BLACK);
        home.setOpaque(true);
        home.add(margin());
        home.add(homeJbutton);

        return home;
    }

    private Component searchOption(){
        searchJbutton = new JImagePanel(pathSearchNormal, pathSearchHover, null);
        searchJbutton.setPreferredSize(new Dimension(70, 20));
        searchJbutton.setOpaque(false);

        JPanel search = new JPanel();
        search.setBackground(Color.BLACK);
        search.setOpaque(true);
        search.add(margin());
        search.add(searchJbutton);

        return search;
    }

    private Component libraryOption(){
        libraryJbutton = new JImagePanel(pathLibraryNormal, pathLibraryHover, null);
        libraryJbutton.setPreferredSize(new Dimension(74,20));
        libraryJbutton.setOpaque(false);

        JPanel library = new JPanel();
        library.setBackground(Color.BLACK);
        library.setOpaque(true);
        library.add(margin());
        library.add(libraryJbutton);

        return library;
    }

    private Component playlistSection() {
        JPanel playlists = new JPanel();

        return playlists;
    }

    private Component logOutSection() {

        JPanel logOutPanel = new JPanel();

        return logOutPanel;
    }

    public void registerController (ActionListener listener){
        homeJbutton.setActionCommand(HOME_BUTTON);
        homeJbutton.addActionListener(listener);

        searchJbutton.setActionCommand(SEARCH_BUTTON);
        searchJbutton.addActionListener(listener);

        libraryJbutton.setActionCommand(LIBRARY_BUTTON);
        libraryJbutton.addActionListener(listener);
    }

}
