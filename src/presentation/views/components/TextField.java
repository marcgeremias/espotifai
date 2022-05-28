package presentation.views.components;

import javax.swing.*;
import java.awt.*;

/**
 * This class creates a custom component for a TextField like implementation with palceholder
 */
public class TextField extends JPanel {
    /**
     * Public constructor for the custom TextField panel class
     * @param placeHolder
     * @param textField
     */
    public TextField(String placeHolder, PlaceholderTextField textField) {
        //Using own textField classes
        textField.setBorder(BorderFactory.createCompoundBorder(
                textField.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 5)));
        textField.setColumns(28);
        textField.setBackground(new Color(76, 76, 76));
        textField.setForeground(Color.WHITE);
        textField.setPlaceholder(placeHolder);
        textField.setOpaque(true);
        Font f = textField.getFont();
        textField.setFont(new Font(f.getName(), f.getStyle(), 12));
        this.setOpaque(false);
        this.add(textField);
    }
}