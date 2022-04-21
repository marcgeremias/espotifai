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

    /**
     * Method that configures our custom PasswordField with the class PlaceholderPasswordField
     * @param placeHolder the placeholder you want to put in the password field
     * @return the JPanel with the custom password field configured
     */
    Component passwordField(String placeHolder, PlaceholderPasswordField passField);

    /**
     * Method that configures our custom textField with the class PlaceholderTextField
     * @param placeHolder the placeholder you want to put in the text field
     * @return the JPanel with the custom text field configured
     */
    Component textField(String placeHolder, PlaceholderTextField textField);

    /**
     * Method that shows a message when a wrong input is set
     * @param inputMessage the attribute with the input message to put
     * @return the input label message hided in a JPanel
     */
    Component wrongInputLabel(JLabel inputMessage);

}
