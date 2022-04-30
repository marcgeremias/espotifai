package presentation.views;

import javax.swing.*;
import java.awt.*;

public interface MainViewFeatures {
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
