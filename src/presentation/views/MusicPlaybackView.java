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
    private static final String LYRICS_P = ICONS_BASE_PATH + "microphone-button_grey" + DEFAULT_EXTENSION;
    private static final String LYRICS_S = ICONS_BASE_PATH + "microphone-button_green" + DEFAULT_EXTENSION;
    private static final String LYRICS_H = ICONS_BASE_PATH + "microphone-button" + DEFAULT_EXTENSION;
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
    private static final int MAX_VOLUME_TICS = 100;

    private JImagePanel btn_play;
    private JImagePanel btn_random;
    private JImagePanel btn_loop;
    private JImagePanel btn_skip_next;
    private JImagePanel btn_skip_back;
    private JImagePanel btn_lyrics;
    private JImagePanel btn_sound;
    private JSliderCustom sldr_music;
    private JSliderCustom sldr_volume;

    private JLabel currentTime;
    private JLabel totalTime;
    private JImagePanel songCoverImage;
    private JLabel songTitle;
    private JLabel authorName;

    private int currentSongDuration;

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

        btn_loop = new JImagePanel(LOOP_P, LOOP_H, LOOP_S);
        btn_loop.setPreferredSize(new Dimension(15, 15));
        btn_loop.setOpaque(false);

        controls.add(btn_random);
        controls.add(btn_skip_back);
        controls.add(btn_play);
        controls.add(btn_skip_next);
        controls.add(btn_loop);

        return controls;
    }

    private Component musicSliderPane(){
        JPanel musicProgress = new JPanel();
        musicProgress.setLayout(new FlowLayout());
        musicProgress.setSize(500, musicProgress.getHeight());
        musicProgress.setOpaque(false);
        musicProgress.setBorder(BorderFactory.createEmptyBorder(2, 0, 10, 0));

        sldr_music = new JSliderCustom(0, currentSongDuration, 0, MusicPlaybackController.SLDR_SONG);
        sldr_music.setPreferredSize(new Dimension(400, 15));

        currentTime = new JLabel();
        currentTime.setText(INITIAL_SLIDER_VALUE);
        currentTime.setForeground(TIME_PROGRESS_FONT_COLOR);
        totalTime = new JLabel();
        totalTime.setText((currentSongDuration/60) % 60 + ":" + String.format("%02d",currentSongDuration % 60));
        totalTime.setForeground(TIME_PROGRESS_FONT_COLOR);

        musicProgress.add(currentTime);
        musicProgress.add(sldr_music);
        musicProgress.add(totalTime);

        return musicProgress;
    }

    private Component coverImagePane(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(250, panel.getHeight()));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        ((FlowLayout)panel.getLayout()).setHgap(16);

        songCoverImage = new JImagePanel(null);
        songCoverImage.setPreferredSize(new Dimension(60, 60));
        songCoverImage.setOpaque(false);

        JPanel rightSide = new JPanel();
        rightSide.setOpaque(false);
        rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
        songTitle = new JLabel("Song Title Here");
        songTitle.setFont(SONG_TITLE_STYLE);
        songTitle.setForeground(PRIMARY_TEXT_COLOR);
        authorName = new JLabel("Author Name Here");
        authorName.setFont(AUTHOR_NAME_STYLE);
        authorName.setForeground(SECONDARY_TEXT_COLOR);

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

        btn_lyrics = new JImagePanel(LYRICS_P, LYRICS_H, LYRICS_S);
        btn_lyrics.setPreferredSize(new Dimension(15, 15));
        btn_lyrics.setOpaque(false);

        btn_sound = new JImagePanel(SOUND_P, SOUND_H, SOUND_S);
        btn_sound.setPreferredSize(new Dimension(15, 15));
        btn_sound.setOpaque(false);

        sldr_volume = new JSliderCustom(MIN_VOL, MAX_VOL, 70, MusicPlaybackController.SLDR_SOUND);
        sldr_volume.setPreferredSize(new Dimension(70, 15));
        bufferPane.add(btn_sound);
        bufferPane.add(sldr_volume);

        panel.add(btn_lyrics);
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
        btn_loop.addActionListener(controller);
        btn_loop.setActionCommand(MusicPlaybackController.BTN_LOOP);
        btn_sound.addActionListener(controller);
        btn_sound.setActionCommand(MusicPlaybackController.BTN_SOUND);
        btn_lyrics.addActionListener(controller);
        btn_lyrics.setActionCommand(MusicPlaybackController.BTN_LYRICS);
        sldr_music.addSliderListener(controller);
        sldr_volume.addSliderListener(controller);
    }

    /**
     * This method will update all the values in the MusicPlayback panel to match the new parameters given.
     * @param songTitle String containing the new song title
     * @param authorName String containing the new author title
     * @param songDuration integer containing the song duration
     * @param cover BufferedImage with the cover image to display
     */
    public void setCurrentSongValues(String songTitle, String authorName, int songDuration, BufferedImage cover) {
        this.currentSongDuration = songDuration;
        this.songTitle.setText(songTitle);
        this.authorName.setText(authorName);
        this.songCoverImage = new JImagePanel(cover);
        revalidate();
        repaint();
    }

    /**
     * This method will swap the sound button on or off
     */
    public void toggleSoundButton(){
        btn_sound.swapSecondary();
        repaint();
    }

    public void notifySongError(String message) {
        JOptionPane.showMessageDialog(this, message, "Song Loading Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setSliderPos(int frame) {
        sldr_music.setValue(frame);
        currentTime.setText((frame/60) % 60 + ":" + String.format("%02d",frame % 60));
        repaint();
    }

    public void setSliderValues(int currentPos, int currentSongLength) {
        sldr_music.setMaximum(currentSongLength);
        sldr_music.setMinimum(0);
        sldr_music.setValue(currentPos);
        totalTime.setText((currentSongLength/60) % 60 + ":" + String.format("%02d",currentSongLength % 60));
        currentTime.setText((currentPos/60) % 60 + ":" + String.format("%02d",currentPos % 60));
        repaint();
    }

    public void setSongDetails(Song currentSong, BufferedImage cover) {
        songTitle.setText(currentSong.getTitle());
        authorName.setText(currentSong.getAuthor());
        songCoverImage = new JImagePanel(cover);
        revalidate();
        repaint();
    }

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

    public void setSoundSliderPos(int pos) {
        sldr_volume.setValue(pos);
        repaint();
    }

    public boolean isBtnMute() {
        return !btn_sound.isPrimarySelected();
    }

    public void setBtnMute(boolean mute){
        btn_sound.setShowSecondary(mute);
    }

    public float getSoundSliderValue() {
        return (float) sldr_volume.getValue();
    }
}
