package presentation.controllers;

import presentation.views.PlaceholderPasswordField;
import presentation.views.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;

public interface MainViewListener {
    /**
     * Method to change the view with the one chosen in the card
     * @param card the name put on the card layout that referes to a specific view
     */
    void changeView(String card);
}
