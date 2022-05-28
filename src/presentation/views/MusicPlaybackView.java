package presentation.views;

import business.entities.Song;
import presentation.controllers.MusicPlaybackController;
import presentation.views.components.JImagePanel;
import presentation.views.components.JSliderCustom;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static business.PlayerManager.MAX_VOL;
import static business.PlayerManager.MIN_VOL;

/**
 * Public class for the MusicPlaybackView that extends JPanel
 */
public class MusicPlaybackView extends JPanel {

    //Icons path
    private static final String ICONS_BASE_PATH = "./res/icons/";
    private static final String DEFAULT_EXTENSION = ".png";
    // P = primary, S = secondary, H = hover
    private static final String LOOP_P = ICONS_BASE_PATH + "loop-button_grey" + DEFAULT_EXTENSION;
    private static final String LOOP_S = ICONS_BASE_PATH + "loop-button_green" + DEFAULT_EXTENSION;
    private static final String LOOP_H = ICONS_BASE_PATH + "loop-button" + DEFAULT_EXTENSION;
    private static final String PLAY_P = ICONS_BASE_PATH + "play-button" + DEFAULT_EXTENSION;
    private static final String PAUSE_P = ICONS_BASE_PATH + "pause-button" + DEFAULT_EXTENSION;
    private static final String RANDOM_P = ICONS_BASE_PATH + "random-button_grey" + DEFAULT_EXTENSION;
    private static final String RANDOM_S = ICONS_BASE_PATH + "random-button_green"+ DEFAULT_EXTENSION;
    private static final String RANDOM_H = ICONS_BASE_PATH + "random-button" + DEFAULT_EXTENSION;
    private static final String SKIP_NEXT_P = ICONS_BASE_PATH + "skip-next-button_grey" + DEFAULT_EXTENSION;
    private static final String SKIP_NEXT_H = ICONS_BASE_PATH + "skip-next-button" + DEFAULT_EXTENSION;
    private static final String SKIP_BACK_P = ICONS_BASE_PATH + "skip-back-button_grey" + DEFAULT_EXTENSION;
    private static final String SKIP_BACK_H = ICONS_BASE_PATH + "skip-back-button" + DEFAULT_EXTENSION;
    private static final String SONG_LOOP_P = ICONS_BASE_PATH + "infinite_grey" + DEFAULT_EXTENSION;
    private static final String SONG_LOOP_S = ICONS_BASE_PATH + "infinite_green" + DEFAULT_EXTENSION;
    private static final String SONG_LOOP_H = ICONS_BASE_PATH + "infinite" + DEFAULT_EXTENSION;
    private static final String SOUND_P = ICONS_BASE_PATH + "audio-on_grey" + DEFAULT_EXTENSION;
    private static final String SOUND_S = ICONS_BASE_PATH + "audio-off" + DEFAULT_EXTENSION;
    private static final String SOUND_H = ICONS_BASE_PATH + "audio-on" + DEFAULT_EXTENSION;


    private static final int PANE_HEIGHT = 90;
    private static final Color BG_MUSIC_PLAYBACK_PANE = new Color(0x171616);
    private static final Color TIME_PROGRESS_FONT_COLOR = Color.WHITE;
    private static final String INITIAL_SLIDER_VALUE = "0:00";
    private static final Font SONG_TITLE_STYLE = new Font("Roboto", Font.BOLD, 14);
    private static final Font AUTHOR_NAME_STYLE = new Font("Roboto", Font.PLAIN, 10);
    private static final Color PRIMARY_TEXT_COLOR = Color.WHITE;
    private static final Color SECONDARY_TEXT_COLOR = Color.WHITE;
    private static final Dimension SLDR_MUSIC_DIM = new Dimension(400, 15);
    private static final Dimension SONG_COVER_SIZE = new Dimension(60, 60);
    private static final String SONG_NAME_PLACEHOLDER = "-";
    private static final String SONG_AUTHOR_PLACEHOLDER = "-";

    private JImagePanel btn_play;
    private JImagePanel btn_random;
    private JImagePanel btn_loop_song;
    private JImagePanel btn_loop_playlist;
    private JImagePanel btn_skip_next;
    private JImagePanel btn_skip_back;
    private JImagePanel btn_sound;
    private JSliderCustom sldr_music;
    private JSliderCustom sldr_volume;

    private JLabel currentTime;
    private JLabel totalTime;
    private JImagePanel songCoverImage;
    private JTextArea songTitle;
    private JLabel authorName;

    /**
     * Public constructor for the music playback panel
     */
    public MusicPlaybackView(){
        super();

        setPreferredSize(new Dimension(this.getWidth(), PANE_HEIGHT));
        setBackground(BG_MUSIC_PLAYBACK_PANE);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(controlsPane());
        centerPanel.add(musicSliderPane());

        add(coverImagePane(), BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightSideControls(), BorderLayout.EAST);
    }

    private Component controlsPane(){
        JPanel controls = new JPanel();
        controls.setOpaque(false);
        controls.setBorder(BorderFactory.createEmptyBorder(10, 0, 2, 0));
        ((FlowLayout)controls.getLayout()).setHgap(20);

        btn_random = new JImagePanel(RANDOM_P, RANDOM_H, RANDOM_S);
        btn_random.setPreferredSize(new Dimension(15, 15));
        btn_random.setOpaque(false);

        btn_skip_back = new JImagePanel(SKIP_BACK_P, SKIP_BACK_H, null);
        btn_skip_back.setPreferredSize(new Dimension(35, 25));
        btn_skip_back.setOpaque(false);

        btn_play = new JImagePanel(PLAY_P, null, PAUSE_P);
        btn_play.setPreferredSize(new Dimension(35, 35));
        btn_play.setOpaque(false);

        btn_skip_next = new JImagePanel(SKIP_NEXT_P, SKIP_NEXT_H, null);
        btn_skip_next.setPreferredSize(new Dimension(35, 25));
        btn_skip_next.setOpaque(false);

        btn_loop_playlist = new JImagePanel(LOOP_P, LOOP_H, LOOP_S);
        btn_loop_playlist.setPreferredSize(new Dimension(15, 15));
        btn_loop_playlist.setOpaque(false);

        controls.add(btn_random);
        controls.add(btn_skip_back);
        controls.add(btn_play);
        controls.add(btn_skip_next);
        controls.add(btn_loop_playlist);

        return controls;
    }

    private Component musicSliderPane(){
        JPanel musicProgress = new JPanel();
        musicProgress.setLayout(new FlowLayout());
        musicProgress.setSize(500, musicProgress.getHeight());
        musicProgress.setOpaque(false);
        musicProgress.setBorder(BorderFactory.createEmptyBorder(2, 0, 10, 0));

        // Init value will always be zero
        sldr_music = new JSliderCustom(0, 0, 0, MusicPlaybackController.SLDR_SONG);
        sldr_music.setPreferredSize(SLDR_MUSIC_DIM);

        currentTime = new JLabel();
        currentTime.setText(INITIAL_SLIDER_VALUE);
        currentTime.setForeground(TIME_PROGRESS_FONT_COLOR);
        totalTime = new JLabel();
        totalTime.setText(INITIAL_SLIDER_VALUE);
        totalTime.setForeground(TIME_PROGRESS_FONT_COLOR);

        musicProgress.add(currentTime);
        musicProgress.add(sldr_music);
        musicProgress.add(totalTime);

        return musicProgress;
    }

    private Component coverImagePane(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(300, panel.getHeight()));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        ((FlowLayout)panel.getLayout()).setHgap(16);

        songCoverImage = new JImagePanel(null);
        songCoverImage.setPreferredSize(SONG_COVER_SIZE);
        songCoverImage.setOpaque(false);

        JPanel rightSide = new JPanel();
        rightSide.setOpaque(false);
        rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
        songTitle = new JTextArea(1, 15);
        songTitle.setText(SONG_NAME_PLACEHOLDER);
        songTitle.setWrapStyleWord(true);
        songTitle.setLineWrap(true);
        songTitle.setOpaque(false);
        songTitle.setEditable(false);
        songTitle.setFocusable(false);
        songTitle.setFont(UIManager.getFont("Label.font"));
        songTitle.setBorder(UIManager.getBorder("Label.border"));
        /*songTitle = new JLabel(SONG_NAME_PLACEHOLDER);*/
        songTitle.setFont(SONG_TITLE_STYLE);
        songTitle.setForeground(PRIMARY_TEXT_COLOR);
        songTitle.setAlignmentX(LEFT_ALIGNMENT);
        authorName = new JLabel(SONG_AUTHOR_PLACEHOLDER);
        authorName.setFont(AUTHOR_NAME_STYLE);
        authorName.setForeground(SECONDARY_TEXT_COLOR);
        authorName.setAlignmentX(LEFT_ALIGNMENT);

        rightSide.add(songTitle);
        rightSide.add(authorName);

        panel.add(songCoverImage);
        panel.add(rightSide);

        return panel;
    }

    private Component rightSideControls(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(250, panel.getHeight()));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 20));
        ((FlowLayout)panel.getLayout()).setHgap(14);

        JPanel bufferPane = new JPanel();
        bufferPane.setOpaque(false);

        btn_loop_song = new JImagePanel(SONG_LOOP_P, SONG_LOOP_H, SONG_LOOP_S);
        btn_loop_song.setPreferredSize(new Dimension(15, 15));
        btn_loop_song.setOpaque(false);

        btn_sound = new JImagePanel(SOUND_P, SOUND_H, SOUND_S);
        btn_sound.setPreferredSize(new Dimension(15, 15));
        btn_sound.setOpaque(false);

        sldr_volume = new JSliderCustom(MIN_VOL, MAX_VOL, 0, MusicPlaybackController.SLDR_SOUND);
        sldr_volume.setPreferredSize(new Dimension(70, 15));
        bufferPane.add(btn_sound);
        bufferPane.add(sldr_volume);

        panel.add(btn_loop_song);
        panel.add(bufferPane);

        return panel;
    }

    /**
     * Public method that associates all the dynamic components of this view with its controller.
     * @param controller instance of {@link MusicPlaybackController}
     */
    public void registerController(MusicPlaybackController controller){
        btn_random.addActionListener(controller);
        btn_random.setActionCommand(MusicPlaybackController.BTN_RANDOM);
        btn_skip_back.addActionListener(controller);
        btn_skip_back.setActionCommand(MusicPlaybackController.BTN_SKIP_BACK);
        btn_play.addActionListener(controller);
        btn_play.setActionCommand(MusicPlaybackController.BTN_PLAY);
        btn_skip_next.addActionListener(controller);
        btn_skip_next.setActionCommand(MusicPlaybackController.BTN_SKIP_NEXT);
        btn_loop_playlist.addActionListener(controller);
        btn_loop_playlist.setActionCommand(MusicPlaybackController.BTN_LOOP);
        btn_sound.addActionListener(controller);
        btn_sound.setActionCommand(MusicPlaybackController.BTN_SOUND);
        btn_loop_song.addActionListener(controller);
        btn_loop_song.setActionCommand(MusicPlaybackController.BTN_LOOP_SONG);
        sldr_music.addSliderListener(controller);
        sldr_volume.addSliderListener(controller);
    }

    /**
     * This method will update the slider position given a value
     * @param frame integer containing the value of the desired position of the slider
     */
    public void setSliderPos(int frame) {
        sldr_music.setValue(frame);
        currentTime.setText((frame/60) % 60 + ":" + String.format("%02d",frame % 60));
        repaint();
    }

    /**
     * This method will set the song progress slider to the desired range of values
     * @param currentPos position where the slider will rest
     * @param currentSongLength maximum value for the slider
     */
    public void setSliderValues(int currentPos, int currentSongLength) {
        sldr_music.setMaximum(currentSongLength);
        sldr_music.setMinimum(0);
        sldr_music.setValue(currentPos);
        totalTime.setText((currentSongLength/60) % 60 + ":" + String.format("%02d",currentSongLength % 60));
        currentTime.setText((currentPos/60) % 60 + ":" + String.format("%02d",currentPos % 60));
        repaint();
    }

    /**
     * This method will set the song details and updated the view
     * @param songName String containing song name
     * @param author String containing author name
     * @param cover image containing the cover of the song
     */
    public void setSongDetails(String songName, String author, BufferedImage cover) {
        if (songName != null && author != null) {
            songTitle.setText(songName);
            authorName.setText(author);
            songCoverImage.attachImage(cover);
            revalidate();
            repaint();
        } else {
            songTitle.setText(SONG_NAME_PLACEHOLDER);
            authorName.setText(SONG_AUTHOR_PLACEHOLDER);
            currentTime.setText(INITIAL_SLIDER_VALUE);
            totalTime.setText(INITIAL_SLIDER_VALUE);
            songCoverImage.attachImage(null);
        }
    }

    /**
     * This method checks whether the play button is shown
     * @return true if the button shows the play button, false if the button shows the pause button
     */
    public boolean isBtnPlay(){
        return btn_play.isPrimarySelected();
    }

    /**
     * Sets the icon of the play button to Pause if true Play if false
     * @param pause if true sets the icon to Pause icon
     */
    public void setBtnPause(boolean pause){
        btn_play.setShowSecondary(pause);
    }


    /**
     * Method to check button mute state
     * @return true if mute icon is shown, false otherwise
     */
    public boolean isBtnMute() {
        return !btn_sound.isPrimarySelected();
    }

    /**
     * Sets the button mute to desired state
     * @param mute true or false
     */
    public void setBtnMute(boolean mute){
        btn_sound.setShowSecondary(mute);
    }

    /**
     * Getter for sound slider
     * @return current value of the sound slider
     */
    public float getSoundSliderValue() {
        return (float) sldr_volume.getValue();
    }

    /**
     * Method to check if the button loop is selected or not
     * @return true if the secondary icon for the loop song icon is shown, false otherwise
     */
    public boolean isBtnSongLoop(){
        return !btn_loop_song.isPrimarySelected();
    }
}
