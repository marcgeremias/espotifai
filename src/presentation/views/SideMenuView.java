package presentation.views;

import presentation.controllers.SideMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SideMenuView extends JPanel {
    public SideMenuView() {
        this.setLayout(new BorderLayout());
        JLabel test = new JLabel("SIDE PANEL HERE");
        test.setForeground(Color.WHITE);
        this.setBackground(Color.BLACK);
        this.add(test, BorderLayout.CENTER);
    }

    public void registerController(ActionListener controller) {
    }
}
