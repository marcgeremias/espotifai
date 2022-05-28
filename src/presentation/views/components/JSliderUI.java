package presentation.views.components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom implementation of the JSlider UI interface
 */
public class JSliderUI extends BasicSliderUI {

    private static final Color TRACK_BG_COLOR_PRIMARY = new Color(0x535353);
    private static final Color TRACK_BG_COLOR_SECONDARY = new Color(0xC4C4C4);
    private static final Color TRACK_BG_COLOR_SECONDARY_HOVER = new Color(0x1DB954);
    private static final Color THUMB_BG_COLOR = new Color(0xFFFFFF);


    private Boolean focused = false;

    private final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();

    public JSliderUI(JSlider slider){
        super(slider);
    }

    @Override
    protected void calculateTrackRect() {
        super.calculateTrackRect();
        trackRect.y = trackRect.y + (trackRect.height - 2) / 2;
        trackRect.height = 4;
        trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, 1, 5);
    }

    @Override
    protected void calculateThumbLocation() {
        super.calculateThumbLocation();
        thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(14, 14);
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(THUMB_BG_COLOR);
        if (focused) g2.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g2, c);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Shape clip = g2.getClip();

        if (focused) {
            g2.setColor(TRACK_BG_COLOR_SECONDARY_HOVER);
        } else {
            g2.setColor(TRACK_BG_COLOR_SECONDARY);
        }

        g2.setClip(trackShape);
        g2.fill(trackShape);
        trackShape.y = trackRect.y;
        g2.setClip(clip);
        int thumbPos = thumbRect.x + thumbRect.width / 2;
        g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());

        g2.setColor(TRACK_BG_COLOR_PRIMARY);
        g2.fill(trackShape);
        g2.setClip(clip);

    }

    @Override
    public void paintFocus(Graphics g) {
    }

    @Override
    protected boolean isDragging() {
        return super.isDragging();
    }

    @Override
    protected void scrollDueToClickInTrack(int dir) {
        int value = this.valueForXPosition(slider.getMousePosition().x);
        slider.setValue(value);
    }

    /**
     * Sets the value of focus
     * @param focused a boolean indicating whether it is the focused component
     */
    public void setFocused(boolean focused) {
        this.focused = focused;
    }
}

