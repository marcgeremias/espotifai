package presentation.views.components;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

/**
 * Class extracted from https://stackoverflow.com/questions/16213836/java-swing-jtextfield-set-placeholder
 * that allows you to put a placeholder in a JPasswordField
 */
public class PlaceholderPasswordField extends JPasswordField {

    // The placeholder we are going to set
    private String placeholder;

    public PlaceholderPasswordField() {
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        // We override the paint component to se the placeholder inside
        super.paintComponent(pG);

        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        g.drawString(placeholder, getInsets().left, pG.getFontMetrics()
                .getMaxAscent() + getInsets().top);
    }

    /**
     * Setter of the placeholder
     * @param placeholder the placeholder
     */
    public void setPlaceholder(final String placeholder) {
        this.placeholder = placeholder;
    }
}