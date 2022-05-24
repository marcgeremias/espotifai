package presentation.controllers;

import business.PlayerManager;
import business.SongManager;
import business.entities.Genre;
import business.entities.Song;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import persistence.SongDAOException;
import presentation.views.MusicPlaybackView;
import presentation.views.components.SliderListener;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class MusicPlaybackController implements ActionListener, SliderListener {

    public static final String SLDR_SONG = "sldr_song";
    public static final String SLDR_SOUND = "sldr_sound";
    public static final String BTN_PLAY = "btn_play";
    public static final String BTN_SKIP_NEXT = "btn_skip_next";
    public static final String BTN_SKIP_BACK = "btn_skip_back";
    public static final String BTN_RANDOM = "btn_random";
    public static final String BTN_LOOP = "btn_loop";
    public static final String BTN_LYRICS = "btn_lyrics";
    public static final String BTN_SOUND = "btn_sound";
    public static final String TMR_INTERRUPT = "timer_int";

    private MusicPlaybackView musicPlaybackView;
    private SongManager songManager;
    private PlayerManager playerManager;

    private int songSecondPos;

    public MusicPlaybackController(MusicPlaybackView musicPlaybackView, SongManager songManager, PlayerManager playerManager){
        this.musicPlaybackView = musicPlaybackView;
        this.songManager = songManager;
        this.playerManager = playerManager;
    }

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
                playerManager.toggleLoopSong();
            }
            case BTN_RANDOM -> {
                playerManager.toggleRandom();
                // Toggle random next song
            }
            case BTN_SKIP_BACK -> {
                // Play song from beginning or previous song depending on current execution second
                try {
                    // Minimum seconds that need to pass to go back to previous song, else it will restart the song
                    if (songSecondPos > 3) {
                        playerManager.killSong();
                        playerManager.loadNextSong(this);
                    } else {
                        playerManager.killSong();
                        if (playerManager.setPreviousSongIndex()) {
                            playerManager.loadNextSong(this);
                        } else {
                            musicPlaybackView.notifySongError("No previous songs found");
                        }
                    }
                } catch (SongDAOException | LineUnavailableException | IOException ex){
                    musicPlaybackView.notifySongError("Couldn't skip back");
                }
            }
            case BTN_SKIP_NEXT -> {
                playerManager.killSong();
                playerManager.generateNextIndex();
                try {
                    playerManager.loadNextSong(this);
                } catch (SongDAOException | LineUnavailableException | IOException ex) {
                    musicPlaybackView.notifySongError("Couldn't load song");
                }
            }
            case BTN_LYRICS -> {
                // Toggle lyrics show/not show
                ArrayList<Song> songs = new ArrayList<>();
                songs.add(new Song(1, "Esperare", "NUSE", Genre.ROCK, "Nena Daconte", "Test", 160, null));
                initSongPlaylist(songs, 0);
            }
            case BTN_SOUND -> {
                if (musicPlaybackView.isBtnMute()){
                    playerManager.setAudioControlLevel(0);
                } else {
                    playerManager.setAudioControlLevel(musicPlaybackView.getSoundSliderValue());
                }
            }
            case TMR_INTERRUPT -> {
                songSecondPos++;
                if (!playerManager.songEnded()) {
                    musicPlaybackView.setSliderPos(songSecondPos);
                }
            }
        }
    }

    @Override
    public void sliderPositionChanged(int sliderPos, String source) {
        switch (source){
            case SLDR_SONG -> {
                // Update song position with sliderPos
                playerManager.setPlaybackFrame(sliderPos);
                songSecondPos = sliderPos;
            }
            case SLDR_SOUND -> {
                playerManager.setAudioControlLevel(sliderPos == 30 ? 0 : (float) sliderPos);
                musicPlaybackView.setBtnMute(sliderPos < 5);
            }
        }
    }


    public void initSongPlaylist(ArrayList<Song> songs, int index) {
        playerManager.initSongPlaylist(songs, index);
        try {
            prepareNewSong();
        } catch (SongDAOException | IOException | LineUnavailableException e) {
            musicPlaybackView.notifySongError(e.getMessage());
        }
    }

    private void prepareNewSong() throws SongDAOException, LineUnavailableException, IOException {
        playerManager.loadNextSong(this);
        playerManager.resumeSong();
        musicPlaybackView.setBtnPause(true);
        musicPlaybackView.setSliderValues(0, playerManager.getCurrentSongLength());
        musicPlaybackView.setSoundSliderPos(70); // initial pos
        playerManager.setAudioControlLevel(70);
        Song currentSong = playerManager.getCurrentSongAttributes();
        musicPlaybackView.setSongDetails(currentSong, songManager.getCoverImage(currentSong.getId()));
        songSecondPos = 0;
    }
}
