package presentation.views.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HoverButton extends JButton implements MouseListener{
    private Color mouseOverColor;
    private Color defaultColor;

    public HoverButton(Color mouseOverColor, Color defaultColor, String text){
        super(text);
        this.mouseOverColor = mouseOverColor;
        this.defaultColor = defaultColor;

        this.setOpaque(true);
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBackground(this.mouseOverColor);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(this.defaultColor);
    }

}
