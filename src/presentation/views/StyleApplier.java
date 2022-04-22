package presentation.views;

import javax.swing.*;
import java.awt.*;

public class StyleApplier {
    /**
     * Given a component, it defines a basic style
     * @param component: an instance of {@link Component}
     * @return the same component with the new appearance set
     */
    public static Component applyStyle(Component component) {
        if (component instanceof JComboBox) {
            component.setBackground(new Color(76, 76, 76));
            component.setForeground(Color.GRAY);
        }

        return component;
    }
}