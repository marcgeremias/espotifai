package presentation.controllers;

import business.SongManager;
import presentation.views.MusicPlaybackView;
import presentation.views.components.SliderListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private MusicPlaybackView musicPlaybackView;
    private SongManager songManager;

    public MusicPlaybackController(MusicPlaybackView musicPlaybackView, SongManager songManager){
        this.musicPlaybackView = musicPlaybackView;
        this.songManager = songManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case BTN_PLAY -> {
                // Stop or Play song
            }
            case BTN_LOOP -> {
                // Toggle loop control of the song
            }
            case BTN_RANDOM -> {
                // Toggle random next song
            }
            case BTN_SKIP_BACK -> {
                // Play song from beginning or previous song depending on current execution second
            }
            case BTN_SKIP_NEXT -> {
                // Skip to next song
            }
            case BTN_LYRICS -> {
                // Toggle lyrics show/not show
            }
            case BTN_SOUND -> {
                //Toggle mute/unmute
            }
        }
    }

    @Override
    public void sliderPositionChanged(int sliderPos, String source) {
        switch (source){
            case SLDR_SONG -> {
                // Update song position with sliderPos
            }
            case SLDR_SOUND -> {
                // Update Volume mixer with slider position
            }
        }
    }

}
