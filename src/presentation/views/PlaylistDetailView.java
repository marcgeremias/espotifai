package presentation.views;

import presentation.controllers.MainViewListener;
import presentation.controllers.PlayerViewListener;
import presentation.controllers.PlaylistDetailController;
import presentation.views.components.JImagePanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;


/**
 * Class that shows the PLaylistDetail view and extend from JFrame
 */
public class PlaylistDetailView extends JPanel {

    // Image from the list of songs

    private JImagePanel listImage;
    private JImagePanel playButton;
    public static final String BTN_PLAY_IMAGE = "BTN PLAY IMAGE";
    public static final String BTN_LIST_IMAGE = "BTN LIST IMAGE";

    public static final String LOGO_IMAGE_PATH = "res/images/nyan_cat.png";
    public static final String LOGO_PLAY_PATH = "res/icons/play-button-green.png";

    public static final String PLAYLIST_TYPE = "PLAYLIST";
    public static final String ALBUM_TYPE = "ALBUM";
    private static final String LIST_NAME = "MyPlaylist#1";

    // Interface of the PlayerViewListener
    private PlayerViewListener listener;


    /**
     * Constructor method to set up the view
     */
    public PlaylistDetailView(PlayerViewListener listener) {
        this.listener = listener;
        this.setLayout(new BorderLayout());

        this.add(northMargin(), BorderLayout.NORTH);
        this.add(westMargin(), BorderLayout.WEST);
        this.add(eastMargin(), BorderLayout.EAST);
        this.add(center(), BorderLayout.CENTER);

    }

    /**
     * Method to add the listener to the playlist buttons
     */
    public void registerController(ActionListener controller) {
        playButton.setActionCommand(BTN_PLAY_IMAGE);
        playButton.addActionListener(controller);

        listImage.setActionCommand(BTN_LIST_IMAGE);
        listImage.addActionListener(controller);

    }


    /**
     * Method to configure all the center components and containers of the PlaylistDetail view
     * @return the JPanel with all the center of the PlaylistDetail view
     */
    private Component center() {
        //JPanel sencer
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        //Part superior
        center.add(upUI());
        //Part inferior
        center.add(downUI());

        return center;
    }


    /**
     * Method to set the top JPanel
     * @return the JPanel on the top
     */
    private Component upUI() {
        JPanel upUI = new JPanel();
        // Margin with song list
        upUI.setPreferredSize(new Dimension(160, 160));
        //upUI.setPreferredSize(new Dimension(320, 320));
        //Part esquerre
        upUI.add(setImageList());
        //Part dreta
        upUI.add(setTexts());
        // Left alignment
        upUI.setLayout(new FlowLayout(FlowLayout.LEFT));

        upUI.setBackground(Color.MAGENTA);
        return upUI;
    }

    /**
     * Method that creates a BoxLayout with som texts
     * @return the BoxLayout created inside a JPanel
     */
    private Component setTexts() {
        JPanel textsFrame = new JPanel();
        // Alignment on Y_AXIS
        textsFrame.setLayout(new BoxLayout(textsFrame, BoxLayout.Y_AXIS));

        textsFrame.add(typeText());
        textsFrame.add(listName());

        return textsFrame;
    }

    /**
     * Method that creates the text of the type List
     * @return the text created inside a JPanel
     */
    private Component typeText() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel jLabel = new JLabel();
        jLabel.setFont(new Font("Arial", Font.BOLD, 15));
        jLabel.setText(PLAYLIST_TYPE);
        jPanel.add(jLabel);

        return jPanel;
    }

    /**
     * Method that creates the text of the name List
     * @return the text created inside a JPanel
     */
    private Component listName() {
        JPanel jPanel = new JPanel();

        JLabel jLabel = new JLabel();
        jLabel.setFont(new Font("Arial", Font.BOLD, 40));
        jLabel.setText(LIST_NAME);
        jPanel.add(jLabel);

        return jPanel;
    }

    /**
     * Method that sets the images of the top JPanel
     * @return JPanel with the cover image and button image
     */
    private Component setImageList(){
        JPanel general = new JPanel();

        JPanel frameImage = new JPanel();
        JPanel frameButton = new JPanel();
        frameButton.setLayout(new FlowLayout(FlowLayout.LEFT));

        general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));

        // Defining the image
        // EN UN FUTUR S'HAURA DE TRANSFORMAR A BufferedImage PQ VINDRA DE LA DDBB
        listImage = new JImagePanel(LOGO_IMAGE_PATH, null, null);
        listImage.setPreferredSize(new Dimension(250, 250));
        frameImage.add(listImage);

        playButton = new JImagePanel(LOGO_PLAY_PATH, null, null);
        playButton.setPreferredSize(new Dimension(40, 40));
        frameButton.add(playButton);

        playButton.setBackground(Color.MAGENTA);
        // Add image to frame
        general.add(frameImage);
        general.add(frameButton);

        frameButton.setBackground(Color.MAGENTA);

        return general;
    }


    /**
     * Method that makes the bottom view
     * @return JPanel with the list of songs
     */
    private Component downUI() {
        JPanel downUI = new JPanel(new GridLayout());
        downUI.setPreferredSize(new Dimension(60,60));

        downUI.setBackground(Color.GREEN);

        downUI.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        String data[][]={
                {"101","Sachin","700000","asdf", "onsdf"},
                {"102","Sachin","700000","asdf", "onsdf"},
                {"103","Sachin","700000","asdf", "onsdf"},
                {"104","Sachin","700000","asdf", "onsdf"},
                {"105","Sachin","700000","asdf", "onsdf"},
                {"106","Sachin","700000","asdf", "onsdf"},
                {"107","Sachin","700000","asdf", "onsdf"}};
        String column[]={"Title","Genre","Album","Author", "User_ID"};
        JTable jt=new JTable(data,column);

        //jt.setEnabled(false);
        //jt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jt.setDefaultEditor(Object.class, null);
        jt.getTableHeader().setReorderingAllowed(false);


        ListSelectionModel model=jt.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String[] lsm = e.toString().split(" ");
                String aux = lsm[3];
                aux = aux.replace("{", "");
                aux =aux.replace("}", "");
                aux =aux.replace("=", "");
                aux =aux.replace("~", "");

                System.out.println(Integer.parseInt(aux));
                // S'ha de passar al controler de musica
                //listener.playSong(Integer.parseInt(aux));

            }
        });



        JScrollPane pane = new JScrollPane(jt);
        downUI.add(pane);

        return downUI;
    }


    /**
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    public Container northMargin() {
        JPanel northMargin = new JPanel();
        northMargin.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        northMargin.setBackground(Color.blue);
        return northMargin;
    }

    /**
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    public Container westMargin() {
        JPanel westMargin = new JPanel();
        westMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));

        westMargin.setBackground(Color.RED);
        return westMargin;
    }

    /**
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    public Container eastMargin() {
        JPanel eastMargin = new JPanel();
        eastMargin.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

        eastMargin.setBackground(Color.CYAN);
        return eastMargin;
    }
}
