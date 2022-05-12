package presentation.views.components;

import javax.swing.*;
import java.awt.*;

public class WrongInputLabel {
    public static JLabel wrongInputLabel(JLabel inputMessage) {
        inputMessage.setFont(new Font("Cascadia Code ExtraLight", Font.PLAIN, 13));
        inputMessage.setForeground(Color.RED);
        inputMessage.setVisible(false);
        return inputMessage;
    }
}
