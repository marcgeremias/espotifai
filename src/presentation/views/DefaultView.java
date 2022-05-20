package presentation.views;

import presentation.views.components.HoverButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultView extends JPanel {
    public static final String MAIN_SECTION = "main_button_pressed";
    public static final String STATISTICS = "statistics_button_pressed";
    public static final String SETTINGS = "settings_button_pressed";

    public static final String HOME_VIEW = "home_view";
    public static final String STATS_VIEW = "stats_view";

    private HoverButton settingsButton;
    private HoverButton mainButton;
    private HoverButton statisticsButton;

    private CardLayout cardManager;
    private JPanel centerPane;

    public DefaultView() {
        cardManager = new CardLayout();
        centerPane = new JPanel(cardManager);

        centerPane.setOpaque(true);

        this.setLayout(new BorderLayout());
        this.add(centerPane, BorderLayout.CENTER);

        configureView();
    }

    private void configureView() {
        this.add(center(), BorderLayout.NORTH);
        this.add(settingsButtonRegion(), BorderLayout.EAST);
    }

    private Component center() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.DARK_GRAY);
        center.add(optionBar());

        return center;
    }

    private Component settingsButtonRegion() {
        JPanel settings = new JPanel();
        Border emptyBorder = BorderFactory.createEmptyBorder();

        settingsButton = new HoverButton(Color.LIGHT_GRAY, Color.DARK_GRAY, "SETTINGS");
        settingsButton.setForeground(Color.WHITE);
        settingsButton.setBackground(Color.DARK_GRAY);
        settingsButton.setFont(new Font("Apple Casual", Font.BOLD, 15));
        settingsButton.setBorder(emptyBorder);

        settings.setBackground(Color.DARK_GRAY);
        settings.add(settingsButton);
        settings.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 60));
        return settings;
    }

    private Component optionBar() {
        JPanel container = new JPanel();
        Border emptyBorder = BorderFactory.createEmptyBorder();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        container.setBackground(Color.DARK_GRAY);

        mainButton = new HoverButton(Color.LIGHT_GRAY, Color.DARK_GRAY, "MAIN");
        mainButton.setOpaque(true);
        mainButton.setBackground(Color.DARK_GRAY);
        mainButton.setForeground(Color.WHITE);
        mainButton.setFont(new Font("Apple Casual", Font.BOLD, 15));
        mainButton.setBorder(emptyBorder);
        container.add(mainButton);

        container.add(Box.createRigidArea(new Dimension(150,0)));

        statisticsButton = new HoverButton(Color.LIGHT_GRAY, Color.DARK_GRAY, "STATISTICS");
        statisticsButton.setBackground(Color.DARK_GRAY);
        statisticsButton.setForeground(Color.WHITE);
        statisticsButton.setFont(new Font("Apple Casual", Font.BOLD, 15));
        statisticsButton.setBorder(emptyBorder);
        container.add(statisticsButton);

        container.add(Box.createRigidArea(new Dimension(700,0)));

        container.setBorder(BorderFactory.createEmptyBorder(50,200,20,10));

        return container;
    }

    public void registerController(ActionListener controller) {
        mainButton.setActionCommand(MAIN_SECTION);
        mainButton.addActionListener(controller);

        statisticsButton.setActionCommand(STATISTICS);
        statisticsButton.addActionListener(controller);

        settingsButton.setActionCommand(SETTINGS);
        settingsButton.addActionListener(controller);
    }

    public void initCardLayout (HomeView homeView, StatsView statsView){
        centerPane.add(homeView, HOME_VIEW);
        centerPane.add(statsView, STATS_VIEW);

        //cardManager.addLayoutComponent(homeView, HOME_VIEW);
        //cardManager.addLayoutComponent(statsView, STATS_VIEW);
        //cardManager.first(centerPane);
    }

    public void changeView(String card) {
        CardLayout cl = (CardLayout) centerPane.getLayout();
        cl.show(centerPane ,card);
    }
}
