package presentation.views;

import presentation.views.components.DataChart;

import javax.swing.*;
import java.awt.*;

/**
 * Public class for the StatsView graphic interface implementation
 */
public class StatsView extends PlayerView {
    private DataChart dataChart;

    /**
     * Public method that initiates a new view for the statistics section
     */
    public StatsView(){
        this.setLayout(new BorderLayout());
        this.add(dataChartPanel(), BorderLayout.CENTER);
        this.setVisible(true);
        this.setBackground(Color.DARK_GRAY);
    }

    /**
     * Method that generates a panel which contains the graphic of the system statistics
     * @return dataPanel: A panel which contains the graphic of the program statistics
     */
    private Component dataChartPanel() {
        dataChart = new DataChart();
        JPanel dataPanel = new JPanel();

        dataPanel.setLayout(new BorderLayout());
        dataPanel.setSize(1000,1000);
        dataPanel.setPreferredSize(new Dimension(1000,1000));
        dataPanel.setBackground(Color.DARK_GRAY);
        dataPanel.add(dataChart);
        dataPanel.setVisible(true);

        return dataPanel;
    }

    /**
     * Method that loads all the data from the persistence to the statistics data chart
     * @param data: Array of Integers containing the number of songs for each genre.
     */
    public void loadData(int[] data) {
        dataChart.loadData(data);
        revalidate();
        repaint();
    }
}