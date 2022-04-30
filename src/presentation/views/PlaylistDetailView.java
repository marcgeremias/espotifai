package presentation.views;

import presentation.controllers.PlaylistDetailController;
import presentation.views.components.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class that shows the PLaylistDetail view and extend from JFrame
 */
public class PlaylistDetailView extends JPanel {

    // Image from the list of songs
    private JImagePanel listImage;

    public static final String LOGO_IMAGE_PATH = "res/images/nyan_cat.png";
    public static final String LOGO_PLAY_PATH = "res/icons/play-button.png";

    public static final String PLAYLIST_TYPE = "PLAYLIST";
    public static final String ALBUM_TYPE = "ALBUM";


    /**
     * Constructor method to set up the view
     */
    public void registerController(ActionListener controller) {

        Container c = new Container();
        this.add(c);
        c.setLayout(new BoxLayout(c, BoxLayout.X_AXIS));

        c.add(jPanelA());
        c.add(jPanelB());
    }



    private Component jPanelA(){
        JPanel jPanel1 = new JPanel();

        //jPanel1.add(imagePlaylist());
        jPanel1.add(imageHover());


        jPanel1.setBackground(Color.red);
        return jPanel1;
    }

    private Component jPanelB(){

        JPanel jPanelB = new JPanel();
        BoxLayout layout = new BoxLayout(jPanelB, BoxLayout.Y_AXIS);
        jPanelB.setLayout(layout);

        jPanelB.add(playlistText());
        jPanelB.add(playlistName());

        jPanelB.setBackground(Color.blue);
        return jPanelB;
    }

    private Component playlistText() {
        JPanel jPanel = new JPanel();

        JLabel jLabel = new JLabel();
        jLabel.setFont(new Font("Arial", Font.BOLD, 15));
        jLabel.setText(PLAYLIST_TYPE);
        jLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 800));
        jPanel.add(jLabel);

        jPanel.setBackground(Color.blue);
        return jPanel;
    }


    private Component playlistName(){
        JPanel jPanel = new JPanel();

        JLabel jLabel = new JLabel();
        jLabel.setFont(new Font("Arial", Font.BOLD, 40));
        jLabel.setText("MyPlaylist#1");
        jLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 200, 650));
        jPanel.add(jLabel);

        jPanel.setBackground(Color.green);
        return jPanel;
    }



    private Component imageHover(){
        // Definim l'imatge
        JPanel controls = new JPanel();
        JImagePanel btn_random;
        controls.setSize(250, 250);

        btn_random = new JImagePanel(LOGO_IMAGE_PATH, LOGO_PLAY_PATH, null);
        btn_random.setPreferredSize(new Dimension(250, 250));
        btn_random.setOpaque(false);

        controls.setBorder(BorderFactory.createEmptyBorder(40, 300, 6, 10));

        controls.add(btn_random);

        return controls;
    }


}
