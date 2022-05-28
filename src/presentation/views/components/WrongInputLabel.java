package presentation.views.components;

import javax.swing.*;
import java.awt.*;

/**
 * Public class that contains a method that creates a generalized JLabel for error messages
 */
public class WrongInputLabel {
    /**
     * This method sets the default styles for a JLabel error message
     * @param inputMessage
     * @return
     */
    public static JLabel wrongInputLabel(JLabel inputMessage) {
        inputMessage.setFont(new Font("Cascadia Code ExtraLight", Font.PLAIN, 13));
        inputMessage.setForeground(Color.RED);
        inputMessage.setVisible(false);
        return inputMessage;
    }
}
