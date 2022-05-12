package presentation.controllers;

import presentation.views.components.PlaceholderPasswordField;
import presentation.views.components.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;

public interface MainViewListener {
    /**
     * Method to change the view with the one chosen in the card
     * @param card the name put on the card layout that referes to a specific view
     */
    void changeView(String card);
}
