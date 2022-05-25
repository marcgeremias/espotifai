package business;

import business.entities.Genre;
import business.entities.Song;
import persistence.SongDAOException;
import presentation.controllers.MusicPlaybackController;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

import static presentation.controllers.MusicPlaybackController.TMR_INTERRUPT;

public class PlayerManager {

    public static final int MIN_VOL = -30;
    public static final int MAX_VOL = 6;

    private SongManager songManager;

    private ArrayList<Song> songs;
    private int currentSongIndex;
    private Timer songTimer;
    private Clip player;
    private int playerHandle;
    private SongLoader songLoader;
    private AudioInputStream[] songsBuffer;
    private FloatControl audioControl;

    private boolean repeatSong;
    private boolean repeatPlaylist;
    private boolean randomSong;

    private int currentSongLength;
    private Stack<Integer> trail;

    public PlayerManager(SongManager songManager) throws LineUnavailableException {
        this.songManager = songManager;
        this.songs = new ArrayList<>();
        this.currentSongIndex = 0;
        this.repeatSong = false;
        this.repeatPlaylist = false;
        this.randomSong = false;
        songLoader = new SongLoader(songManager);
    }

    /**
     * This method will recieve a list of songs and an index and set the values accordingly
     * @param songs list of songs that will be played in order
     * @param index index start of the song we want to reproduce first
     */
    public void initSongPlaylist(ArrayList<Song> songs, int index) {
        this.songs = songs;
        this.currentSongIndex = index;
        this.trail = new Stack<>();
        songsBuffer = new AudioInputStream[songs.size()];
        songLoader.load(songs, songsBuffer);
        songLoader.start();
    }

    public void loadNextSong(MusicPlaybackController listener) throws SongDAOException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream ais;
        ArrayList<Integer> tmp = songLoader.getSongsSaved();
        if (tmp.contains(songs.get(currentSongIndex))){
            int indexWhere = tmp.indexOf(songs.get(currentSongIndex));
            ais = AudioSystem.getAudioInputStream(new File("./res/songs/" + songs + ".wav"));
        } else {
            ais = songManager.getSongStream(songs.get(currentSongIndex));
        }
        //AudioCue.makeStereoCue(ais);
        player = AudioSystem.getClip();
        player.open(ais);
        //playerHandle = player.play();
        // Value is in microseconds and it needs conversion to seconds
        currentSongLength = (int) (player.getMicrosecondLength() / 1000000);
        // delay is represented in ms so 1000 is 1 second
        songTimer = new Timer(1000, listener);
        songTimer.setActionCommand(TMR_INTERRUPT);
        // Set audio control for player
        audioControl = (FloatControl) player.getControl(FloatControl.Type.MASTER_GAIN);
    }

    public int getCurrentSongLength() {
        return currentSongLength;
    }

    public void setPlaybackFrame(int sliderPos) {
        //player.setFramePosition(playerHandle, sliderPos * 1000000L);
        player.setMicrosecondPosition(sliderPos * 1000000L);
    }

    public Song getCurrentSongAttributes() {
        return songs.get(currentSongIndex);
    }

    public boolean generateNextIndex(){
        trail.push(currentSongIndex);
        System.out.println("Pushed "+currentSongIndex);
        if(randomSong){
            Random rand = new Random();
            int r;
            do {
                r = rand.nextInt(songs.size());
            } while (currentSongIndex == r);
            currentSongIndex = r;
        } else {
            currentSongIndex++;
            if (repeatPlaylist && currentSongIndex == songs.size()){
                currentSongIndex = 0;
            } else if (currentSongIndex >= songs.size()){
                return false;
            }
        }
        System.out.println("New song "+currentSongIndex);
        return true;
    }

    public void toggleRandom(){
        randomSong = !randomSong;
    }

    public void toggleLoopSong(){
        repeatSong = !repeatSong;
    }

    public void toggleLoopPlaylist(){
        repeatPlaylist = !repeatPlaylist;
    }

    public void resumeSong(){
        if (player != null) {
            player.start();
            songTimer.start();
        }
    }

    public void pauseSong() {
        if (player != null) {
            player.stop();
            songTimer.stop();
        }
    }

    public void killSong() {
        if (player != null) {
            if (player.isRunning()) {
                player.stop();
                player.close();
                player.flush();
                player.drain();
                player = null;
                songTimer.stop();
                songTimer = null;
            }
        }
    }

    public void setAudioControlLevel(float decibelsReduce) {
        //player.(playerHandle, decibelsReduce/100);
        audioControl.setValue(decibelsReduce);
    }

    public boolean songEnded() {
        return currentSongLength <= player.getMicrosecondPosition()/1000000;
    }

    public boolean setPreviousSongIndex() {
        try {
            if (trail.size() >= 1) {
                //trail.pop();
                currentSongIndex = trail.pop();
                return true;
            } else {
                return false;
            }
        } catch (IndexOutOfBoundsException | EmptyStackException e){
            return false;
        }
    }
}
