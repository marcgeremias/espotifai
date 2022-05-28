package presentation.views.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Public class that generates a custom hover button that extends from JButton
 */
public class HoverButton extends JButton implements MouseListener{
    private Color mouseOverColor;
    private Color defaultColor;

    /**
     * Public constructor for the custom JButton
     * @param mouseOverColor
     * @param defaultColor
     * @param text
     */
    public HoverButton(Color mouseOverColor, Color defaultColor, String text){
        super(text);
        this.mouseOverColor = mouseOverColor;
        this.defaultColor = defaultColor;

        this.setFocusable(false);
        this.setOpaque(true);
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Do nothing here
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Do nothing here
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Do nothing here
    }

    /**
     * Override method that is called whenever the mouse enteres the component
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBackground(this.mouseOverColor);
    }

    /**
     * Override method that is called whenever the mouse exists the component
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(this.defaultColor);
    }
}