package presentation.controllers;

import business.SongManager;
import persistence.SongDAOException;
import presentation.views.StatsView;
import presentation.views.components.DataChart;

/**
 * Public controller that controls the statistics
 */
public class StatsController {

    private StatsView statsView;
    private SongManager songManager;

    /**
     * Public method that creates a new constructor which links with the Statistics view
     * @param statsView an instance of StatsView
     * @param songManager an instance of SongManager
     */
    public StatsController(StatsView statsView, SongManager songManager) {
        this.statsView = statsView;
        this.songManager = songManager;
    }

    /**
     * Method that loads the data into the DataChart that prints the Statistics
     */
    public void refreshView() {
        try {
            //int[] data = songManager.getNumberOfSongsByGenre();
            int[] data = songManager.getNumberOfSongs();
            statsView.loadData(data);
        } catch (SongDAOException e) {
            e.printStackTrace();
        }
    }
}