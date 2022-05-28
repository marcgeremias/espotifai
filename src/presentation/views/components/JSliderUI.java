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

    /**
     * Basic constructor for the Slider UI
     * @param slider
     */
    public JSliderUI(JSlider slider){
        super(slider);
    }

    /**
     * This method calculates the track height and sets its shape
     */
    @Override
    protected void calculateTrackRect() {
        super.calculateTrackRect();
        trackRect.y = trackRect.y + (trackRect.height - 2) / 2;
        trackRect.height = 4;
        trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, 1, 5);
    }

    /**
     * This method calculates the current thumb location
     */
    @Override
    protected void calculateThumbLocation() {
        super.calculateThumbLocation();
        thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
    }

    /**
     * Method that sets the size for the thumb
     * @return dimension of the thumb
     */
    @Override
    protected Dimension getThumbSize() {
        return new Dimension(14, 14);
    }

    /**
     * This method paints the thumb with the desired graphics and colors
     * @param g
     */
    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(THUMB_BG_COLOR);
        if (focused) g2.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
    }

    /**
     * Override method to paint custom graphics
     * @param g
     * @param c
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g2, c);
    }

    /**
     * Override method to paint custom track graphics
     * @param g
     */
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

    /**
     * This method is overridden to do nothing so that focus action doesn't change the aspect of the component
     * @param g
     */
    @Override
    public void paintFocus(Graphics g) {
    }

    /**
     * This function determines whether a slider is being dragged or not
     * @return true if slider is being dragged, false otherwise
     */
    @Override
    protected boolean isDragging() {
        return super.isDragging();
    }

    /**
     * This method is to change the thumb position if the slider is clicked in a certain location
     * @param dir
     */
    @Override
    protected void scrollDueToClickInTrack(int dir) {
        int value = this.valueForXPosition(slider.getMousePosition().x);
        slider.setValue(value);
    }

    /**
     * This method is used to tell the slider if it is being focused by the cursor or not
     * @param focused a boolean indicating whether there is focus
     */
    public void setFocused(boolean focused) {
        this.focused = focused;
    }
}