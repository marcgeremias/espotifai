package presentation.views;

import presentation.views.components.HoverButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Public class default view that is part of a card layout switching views inside the home view
 */
public class DefaultView extends JPanel {
    public static final String MAIN_SECTION = "main_button_pressed";
    public static final String STATISTICS = "statistics_button_pressed";

    public static final String HOME_VIEW = "home_view";
    public static final String STATS_VIEW = "stats_view";

    private HoverButton mainButton;
    private HoverButton statisticsButton;

    private CardLayout cardManager;
    private JPanel centerPane;

    /**
     * Public constructor for the DefaultView of the HomeView
     */
    public DefaultView() {
        cardManager = new CardLayout();
        centerPane = new JPanel(cardManager);

        centerPane.setOpaque(true);

        this.setLayout(new BorderLayout());
        this.add(centerPane, BorderLayout.CENTER);

        configureView();
    }

    /*
    This method is called to configure the view
     */
    private void configureView() {
        this.add(center(), BorderLayout.NORTH);
    }

    /*
    Method that configures all the components in the center of the view
     */
    private Component center() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.DARK_GRAY);
        center.add(optionBar());

        return center;
    }

    /*
    Method that initializes the components for the optionBar
     */
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

    /**
     * Public method to register controller for the view action buttons
     * @param controller instance
     */
    public void registerController(ActionListener controller) {
        mainButton.setActionCommand(MAIN_SECTION);
        mainButton.addActionListener(controller);

        statisticsButton.setActionCommand(STATISTICS);
        statisticsButton.addActionListener(controller);
    }

    /**
     * This method is called to initialize the card layout
     * @param homeView
     * @param statsView
     */
    public void initCardLayout (HomeView homeView, StatsView statsView){
        centerPane.add(homeView, HOME_VIEW);
        centerPane.add(statsView, STATS_VIEW);
    }

    /**
     * Public method to change the displayed view
     * @param card string containing the target view
     */
    public void changeView(String card) {
        CardLayout cl = (CardLayout) centerPane.getLayout();
        cl.show(centerPane ,card);
    }
}
