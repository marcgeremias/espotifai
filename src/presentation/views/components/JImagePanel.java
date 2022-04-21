package presentation.views.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Custom JImagePanel implementation for renderin images.
 */
public class JImagePanel extends JPanel implements MouseListener {

    private static final String IMAGE_NULL_PATH = "./res/icons/no-image-loaded.png";

    private ActionListener actionListener;
    private String actionCommand;

    private BufferedImage primaryIcon;
    private BufferedImage hoverIcon;
    private BufferedImage secondaryIcon;
    private BufferedImage noImage;
    private boolean showHover;
    private boolean showSecondary;


    /**
     * JImagePanel with primary image, hover image and secondary image
     * @param primaryIcon primary image
     * @param hoverIcon hover image
     * @param secondaryIcon secondary image
     */
    public JImagePanel(String primaryIcon, String hoverIcon, String secondaryIcon) {
        try {
            this.primaryIcon = primaryIcon != null ? ImageIO.read(new File(primaryIcon)) : null;
            this.hoverIcon = hoverIcon != null ? ImageIO.read(new File(hoverIcon)) : null;
            this.secondaryIcon = secondaryIcon != null ? ImageIO.read(new File(secondaryIcon)) : null;
            this.noImage = ImageIO.read(new File(IMAGE_NULL_PATH));
            showHover = false;
            showSecondary = false;
            addMouseListener(this);
        } catch (IOException e) {
            // Not properly managed, sorry!
            e.printStackTrace();
        }
    }

    /**
     * Constructor that only adds 1 image to the component as the primary and sets the others null.
     * @param image BufferedImage with the image to display
     */
    public JImagePanel(BufferedImage image){
        this.primaryIcon = image;
        this.secondaryIcon = null;
        this.hoverIcon = null;
        showHover = false;
        showSecondary = false;
        addMouseListener(this);
        try {
            this.noImage = ImageIO.read(new File(IMAGE_NULL_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method overrides the default paintComponent and paints the corresponding image
     * @param g graphics instance
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (showSecondary && secondaryIcon != null){
            g.drawImage(secondaryIcon, 0, 0, getWidth(), getHeight(), this);
        } else if (showHover && hoverIcon != null){
            g.drawImage(hoverIcon, 0, 0, getWidth(), getHeight(), this);
        } else if (primaryIcon != null) {
            g.drawImage(primaryIcon, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.drawImage(noImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * This method will swap the primary image for the secondary and viceversa.
     */
    public void swapSecondary(){
        showSecondary = !showSecondary;
    }

    /**
     * Method that will add action listener to the component
     * @param actionListener instance of {@link ActionListener}
     */
    public void addActionListener(ActionListener actionListener){
        this.actionListener = actionListener;
    }

    /**
     * Method that binds the action command given in the params with the listener added to the component.
     * @param command String containing the key word.
     */
    public void setActionCommand(String command){
        this.actionCommand = command;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        showSecondary = !showSecondary;
        actionListener.actionPerformed(new ActionEvent(e, ActionEvent.ACTION_PERFORMED, actionCommand));
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        showHover = true;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        showHover = false;
        repaint();
    }
}


