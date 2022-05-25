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

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;

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
                        musicPlaybackView.setSliderPos(0);
                        playerManager.setPlaybackFrame(0);
                        songSecondPos = 0;
                    } else {
                        if (playerManager.setPreviousSongIndex()) {
                            playerManager.killSong();
                            prepareNewSong();
                            //playerManager.loadNextSong(this);
                            //playerManager.resumeSong();
                        } else {
                            musicPlaybackView.notifySongError("No previous songs found");
                        }
                    }
                } catch (SongDAOException | LineUnavailableException | IOException | UnsupportedAudioFileException ex){
                    musicPlaybackView.notifySongError("Couldn't skip back");
                }
            }
            case BTN_SKIP_NEXT -> {
                if (playerManager.generateNextIndex()) {
                    playerManager.killSong();
                    try {
                        prepareNewSong();
                    } catch (SongDAOException | LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                        musicPlaybackView.notifySongError("Couldn't load song");
                    }
                } else {
                    musicPlaybackView.notifySongError("No more songs in the playlist");
                }
            }
            case BTN_LYRICS -> {
                // Toggle lyrics show/not show
                ArrayList<Song> songs = songManager.getAllSongs();
                ArrayList<Song> songsFiltered = new ArrayList<>();
                for (Song song :songs){
                    if (song.getUser().equals("Armand")){
                        songsFiltered.add(song);
                    }
                }
                //songs.add(new Song(1, "Esperare", "NUSE", Genre.ROCK, "Nena Daconte", "Test", 160, null));
                initSongPlaylist(songsFiltered, 0);
            }
            case BTN_SOUND -> {
                if (musicPlaybackView.isBtnMute()){
                    playerManager.setAudioControlLevel(-80);
                } else {
                    playerManager.setAudioControlLevel(musicPlaybackView.getSoundSliderValue());
                }
            }
            case TMR_INTERRUPT -> {
                songSecondPos++;
                if (!playerManager.songEnded()) {
                    musicPlaybackView.setSliderPos(songSecondPos);
                } else {
                    /*if (playerManager.generateNextIndex()){
                        playerManager.killSong();
                        try {
                            playerManager.loadNextSong(this);
                        } catch (SongDAOException | IOException | LineUnavailableException ex) {
                            musicPlaybackView.notifySongError("Couldn't load song");
                        }
                    } else {
                        playerManager.killSong();
                        musicPlaybackView.notifySongError("No more songs in the playlist");
                    }*/
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
                playerManager.setAudioControlLevel(sliderPos == -30 ? -80 : (float) sliderPos);
                musicPlaybackView.setBtnMute(sliderPos < -30);
            }
        }
    }


    public void initSongPlaylist(ArrayList<Song> songs, int index) {
        try {
            playerManager.initSongPlaylist(songs, index);
            prepareNewSong();
        } catch (SongDAOException | IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            musicPlaybackView.notifySongError(e.getMessage());
        }
    }

    private void prepareNewSong() throws SongDAOException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        playerManager.loadNextSong(this);
        playerManager.resumeSong();
        musicPlaybackView.setBtnPause(true);
        musicPlaybackView.setSliderValues(0, playerManager.getCurrentSongLength());
        //musicPlaybackView.setSoundSliderPos(0); // initial pos
        //playerManager.setAudioControlLevel(0);
        Song currentSong = playerManager.getCurrentSongAttributes();
        musicPlaybackView.setSongDetails(currentSong, songManager.getCoverImage(currentSong.getId()));
        songSecondPos = 0;
    }

}
