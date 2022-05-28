package presentation.views.components;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Custom implementation of the JSlider component with MouseListener added to perform basic UI interactions
 */
public class JSliderCustom extends JSlider implements MouseListener {

    private JSliderUI sliderUI;
    private SliderListener sliderListener;
    private String source;

    /**
     * Public constructor that asks for the same parameters as the JSlider original component with an added
     * String that will contain the 'name' of the slider, so it can be handled appropriately later on.
     * @param minValue minimum value should always be zero
     * @param maxValue max value set by the song length
     * @param initialValue initial value for music slider should be zero, else as user desire
     * @param source unique String containing the type of slider
     */
    public JSliderCustom(int minValue, int maxValue, int initialValue, String source){
        super(minValue, maxValue, initialValue);
        this.source = source;
        addMouseListener(this);
        setBorder(null);
        setOpaque(false);
        sliderUI = new JSliderUI(this);
        setUI(sliderUI);
    }

    /**
     * This method attaches an action listener to the slider
     * @param sliderListener instance of the listener
     */
    public void addSliderListener(SliderListener sliderListener){
        this.sliderListener = sliderListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Do nothing here
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Do nothing here
    }

    /**
     * Override method that is called when the mouse click was released
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        sliderListener.sliderPositionChanged(this.getValue(), source);
        repaint();
    }

    /**
     * Override method that is called everytime the mouse enters the slider component
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        sliderUI.setFocused(true);
        repaint();
    }

    /**
     * Override method that is called when the mouse exits the slider component
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (!sliderUI.isDragging()) {
            sliderUI.setFocused(false);
            repaint();
        }
    }
}
