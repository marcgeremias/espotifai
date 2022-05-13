package presentation.views.components;

import javax.swing.*;
import java.awt.*;

public class TextField extends JPanel {
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