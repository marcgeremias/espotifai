package presentation.controllers;

import business.PlayerManager;
import business.SongManager;
import persistence.SongDAOException;
import presentation.views.MusicPlaybackView;
import presentation.views.components.SliderListener;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Public controller that manages the music playback sequence
 */
public class MusicPlaybackController implements ActionListener, SliderListener {

    public static final String SLDR_SONG = "sldr_song";
    public static final String SLDR_SOUND = "sldr_sound";
    public static final String BTN_PLAY = "btn_play";
    public static final String BTN_SKIP_NEXT = "btn_skip_next";
    public static final String BTN_SKIP_BACK = "btn_skip_back";
    public static final String BTN_RANDOM = "btn_random";
    public static final String BTN_LOOP = "btn_loop";
    public static final String BTN_LOOP_SONG = "btn_loop_song";
    public static final String BTN_SOUND = "btn_sound";
    public static final String TMR_INTERRUPT = "timer_int";

    private final MusicPlaybackView musicPlaybackView;
    private final SongManager songManager;
    private final PlayerManager playerManager;

    /**
     * Public constructor for the music playback controller class
     * @param musicPlaybackView instance of the View to manage
     * @param songManager instance of {@link SongManager} to be used by the controller
     * @param playerManager istance of {@link PlayerManager} to be used by the controller
     */
    public MusicPlaybackController(MusicPlaybackView musicPlaybackView, SongManager songManager, PlayerManager playerManager){
        this.musicPlaybackView = musicPlaybackView;
        this.songManager = songManager;
        this.playerManager = playerManager;
    }

    /**
     * This method will respond to the instance of {@link MusicPlaybackView} actions generated by buttons
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case BTN_PLAY -> {
                if (!musicPlaybackView.isBtnPlay()){
                    playerManager.resumeSong();
                } else {
                    playerManager.pauseSong();
                }
            }
            case BTN_LOOP -> {
                playerManager.toggleLoopPlaylist();
            }
            case BTN_RANDOM -> {
                playerManager.toggleRandom();
                // Toggle random next song
            }
            case BTN_SKIP_BACK -> {
                // Play song from beginning or previous song depending on current execution second
                try {
                    // Minimum seconds that need to pass to go back to previous song, else it will restart the song
                    if (playerManager.getCurrentSongSecond() > 3) {
                        musicPlaybackView.setSliderPos(0);
                        playerManager.setPlaybackFrame(0);
                    } else {
                        if (playerManager.setPreviousSongIndex()) {
                            playerManager.killSong();
                            prepareNewSong();
                        }
                        // We decided to not notify anything if there are no songs available going back,
                        // just let the user see that clicking backwards will do nothing

                    }
                } catch (SongDAOException | LineUnavailableException | IOException | UnsupportedAudioFileException ignored){
                }
            }
            case BTN_SKIP_NEXT -> {
                if (playerManager.generateNextIndex()) {
                    playerManager.killSong();
                    try {
                        prepareNewSong();
                    } catch (SongDAOException | LineUnavailableException | IOException | UnsupportedAudioFileException ignored) {
                    }
                }
                // We decided that if there are no more songs in the playlist we don't say anything
            }
            case BTN_LOOP_SONG -> {
                // No action required
            }
            case BTN_SOUND -> {
                if (musicPlaybackView.isBtnMute()){
                    playerManager.setAudioControlLevel(-80);
                } else {
                    playerManager.setAudioControlLevel(musicPlaybackView.getSoundSliderValue());
                }
            }
            case TMR_INTERRUPT -> {
                if (!playerManager.songEnded()) {
                    musicPlaybackView.setSliderPos(playerManager.getCurrentSongSecond());
                } else {
                    if (musicPlaybackView.isBtnSongLoop()){
                        playerManager.killSong();
                        try {
                            prepareNewSong();
                        } catch (SongDAOException | LineUnavailableException | IOException | UnsupportedAudioFileException ignored) {
                        }
                    } else if (playerManager.generateNextIndex()){
                        playerManager.killSong();
                        try {
                            prepareNewSong();
                        } catch (SongDAOException | IOException | LineUnavailableException | UnsupportedAudioFileException ignored) {
                        }
                    } else {
                        playerManager.killSong();
                        // We don't notify anything if no more songs in the playlist
                    }
                }
            }
        }
    }

    /**
     * This method manages action performed by sliders in the view, whether its the volume slider or the music progress slider
     * @param sliderPos integer containing the current slider position
     * @param source String containing the key of the source of the change
     */
    @Override
    public void sliderPositionChanged(int sliderPos, String source) {
        switch (source){
            case SLDR_SONG -> {
                // Update song position with sliderPos
                playerManager.setPlaybackFrame(sliderPos);
            }
            case SLDR_SOUND -> {
                playerManager.setAudioControlLevel(sliderPos == -30 ? -80 : (float) sliderPos);
                musicPlaybackView.setBtnMute(sliderPos < -30);
            }
        }
    }


    /**
     * This method is called to init a song playlist in the music playback controller to start a song reproduction
     * @param songs list of songs in the playlist to be loaded
     * @param index index corresponding with the desired starting song
     */
    public void initSongPlaylist(ArrayList<ArrayList<String>> songs, int index) {
        try {
            // If it's not same playlist, load new playlist
            if (!playerManager.isSamePlaylist(songs)) {
                playerManager.initSongPlaylist(songs, index);
            } else {
                playerManager.setCurrentSongIndex(index);
                playerManager.killSong();
            }
            prepareNewSong();
        } catch (SongDAOException | IOException | LineUnavailableException | UnsupportedAudioFileException ignored) {
            //We ignore whatever exception
        }
    }

    /*
    This method is called to load and prepare Manager and View for the next song to be played
     */
    private void prepareNewSong() throws SongDAOException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        playerManager.loadNextSong(this);
        playerManager.resumeSong();
        musicPlaybackView.setBtnPause(true);
        musicPlaybackView.setSliderValues(0, playerManager.getCurrentSongLength());
        ArrayList<String> currentSong = playerManager.getCurrentSongAttributes();
        musicPlaybackView.setSongDetails(
                currentSong.get(SongManager.SONG_TITLE_ATTRIBUTE_INDEX),
                currentSong.get(SongManager.SONG_AUTHOR_ATTRIBUTE_INDEX),
                songManager.getCoverImage(Integer.parseInt(currentSong.get(SongManager.SONG_ID_ATTRIBUTE_INDEX)))
        );
    }

    /**
     * This method clears all the information in the music playback container
     */
    public void clearData() {
        musicPlaybackView.setSliderPos(0);
        musicPlaybackView.setSongDetails(null, null, null);
    }
}
