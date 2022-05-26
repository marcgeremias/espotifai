package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.StatsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatsController implements ActionListener {

    private StatsView statsView;
    private SongManager songManager;

    public StatsController(StatsView statsView, SongManager songManager) {
        this.statsView = statsView;
        this.songManager = songManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void refreshView() {
        //Actualitza la vista amb info nova pel grafic
    }
}