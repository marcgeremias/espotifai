package presentation.views.components;

import javax.swing.*;
import java.awt.*;

/**
 * Public class that extends JPanel to create custom Password field component
 */
public class PasswordField extends JPanel {
    /**
     * Public constructor for the class that sets all the custom attributes by default
     * @param placeHolder
     * @param passField
     */
    public PasswordField(String placeHolder, PlaceholderPasswordField passField) {
        //Using own Field classes
        passField.setBorder(BorderFactory.createCompoundBorder(
                passField.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 5)));
        passField.setColumns(28);
        passField.setBackground(new Color(76, 76, 76));
        passField.setForeground(Color.WHITE);
        passField.setPlaceholder(placeHolder);
        passField.setOpaque(true);
        passField.setEchoChar('*');
        Font f = passField.getFont();
        passField.setFont(new Font(f.getName(), f.getStyle(), 12));
        this.setOpaque(false);
        this.add(passField);
    }
}