package business;

import business.entities.Genre;
import business.entities.Song;
import persistence.SongDAO;
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

/**
 * The PlayerManager class is an instance used to execute all the logic behind a music playback execution
 */
public class PlayerManager {

    public static final int MIN_VOL = -30;
    public static final int MAX_VOL = 6;

    private SongDAO songDAO;

    private ArrayList<Song> songs;
    private int currentSongIndex;
    private Timer songTimer;
    private Clip player;
    private SongLoader songLoader;
    private FloatControl audioControl;

    private boolean repeatPlaylist;
    private boolean randomSong;

    private int currentSongLength;
    private Stack<Integer> trail;

    /**
     * Public constructor for the PlayerManager class
     * @param songDAO instance of {@link SongDAO}
     */
    public PlayerManager(SongDAO songDAO) {
        this.songDAO = songDAO;
        this.songs = new ArrayList<>();
        this.currentSongIndex = 0;
        this.repeatPlaylist = false;
        this.randomSong = false;
    }

    /**
     * This method will recieve a list of songs and an index and set the values accordingly
     * @param songsStr list of songs that will be played in order
     * @param index index start of the song we want to reproduce first
     */
    public void initSongPlaylist(ArrayList<ArrayList<String>> songsStr, int index) {
        if (player != null){
            if (player.isRunning()){
                player.stop();
            }
            player = null;
        }
        if (songTimer != null) {
            songTimer.stop();
            songTimer = null;
        }
        this.trail = new Stack<>();
        for (int i = 0; i < index; i++) {
            trail.push(i);
        }

        this.songs = new ArrayList<>();
        for (ArrayList<String> strings : songsStr) {
            this.songs.add(new Song(
                    Integer.parseInt(strings.get(SongManager.SONG_ID_ATTRIBUTE_INDEX)),
                    strings.get(SongManager.SONG_TITLE_ATTRIBUTE_INDEX),
                    strings.get(SongManager.SONG_ALBUM_ATTRIBUTE_INDEX),
                    Genre.valueOf(strings.get(SongManager.SONG_GENRE_ATTRIBUTE_INDEX)),
                    strings.get(SongManager.SONG_AUTHOR_ATTRIBUTE_INDEX),
                    strings.get(SongManager.SONG_IMAGE_ATTRIBUTE_INDEX),
                    Integer.parseInt(strings.get(SongManager.SONG_DURATION_ATTRIBUTE_INDEX)),
                    strings.get(SongManager.SONG_USER_ATTRIBUTE_INDEX)
            ));
        }
        
        this.currentSongIndex = index;
        // This method needs to be called everytime because a new thread needs to be initiated
        songLoader = new SongLoader(songDAO);
        songLoader.load(songs);
        songLoader.start();
    }

    /**
     * This method will start reproducing the song indicated by the currentSongIndex variable
     * @param listener instance of {@link MusicPlaybackController} that will be responsible for managing the timer
     * @throws SongDAOException if the song can't be downloaded
     * @throws LineUnavailableException if the system can't open a new Clip instance
     * @throws IOException if a file can't be read correctly
     * @throws UnsupportedAudioFileException if the format of the file read isn't supported by the {@link AudioSystem} instance
     * @see AudioSystem
     */
    public void loadNextSong(MusicPlaybackController listener) throws SongDAOException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream ais;
        ArrayList<Integer> tmp = songLoader.getSongsSaved();
        //If the song is already downloaded we can play it directly from local files
        if (tmp.contains(songs.get(currentSongIndex).getId())){
            int indexWhere = tmp.indexOf(songs.get(currentSongIndex).getId());
            ais = AudioSystem.getAudioInputStream(new File("./res/songs/" + tmp.get(indexWhere) + ".wav"));
        } else {
            ais = songDAO.downloadSong(songs.get(currentSongIndex).getId());
        }
        player = AudioSystem.getClip();
        player.open(ais);
        // Value is in microseconds and it needs conversion to seconds
        currentSongLength = (int) (player.getMicrosecondLength() / 1000000);
        // delay is represented in ms so 1000 is 1 second
        if (songTimer == null) songTimer = new Timer(1000, listener);
        else songTimer.restart();
        songTimer.setActionCommand(TMR_INTERRUPT);
        // Set audio control for player
        audioControl = (FloatControl) player.getControl(FloatControl.Type.MASTER_GAIN);
    }

    /**
     * This method will get the current song length
     * @return integer corresponding to the current song length
     */
    public int getCurrentSongLength() {
        return currentSongLength;
    }

    /**
     * This method will set the playback position in the song thread
     * @param sliderPos value corresponding to the position where playback frame is to be placed
     */
    public void setPlaybackFrame(int sliderPos) {
        //player.setFramePosition(playerHandle, sliderPos * 1000000L);
        if (player != null) {
            player.setMicrosecondPosition(sliderPos * 1000000L);
        }
    }

    /**
     * This method gets the attributes of the song that is being currently played in the player
     * @return an ArrayList of String containing the attributes of the current song
     */
    public ArrayList<String> getCurrentSongAttributes() {
        if (songs.size() > 0) {
            ArrayList<String> attributes = new ArrayList<>();
            Song s = songs.get(currentSongIndex);

            attributes.add(Integer.toString(SongManager.SONG_ID_ATTRIBUTE_INDEX, s.getId()));
            attributes.add(SongManager.SONG_TITLE_ATTRIBUTE_INDEX, s.getTitle());
            attributes.add(SongManager.SONG_ALBUM_ATTRIBUTE_INDEX, s.getAlbum());
            attributes.add(SongManager.SONG_AUTHOR_ATTRIBUTE_INDEX, s.getAuthor());
            attributes.add(SongManager.SONG_DURATION_ATTRIBUTE_INDEX, Integer.toString(s.getDuration()));
            attributes.add(SongManager.SONG_USER_ATTRIBUTE_INDEX, s.getUser());
            attributes.add(SongManager.SONG_GENRE_ATTRIBUTE_INDEX, String.valueOf(s.getGenre()));
            attributes.add(SongManager.SONG_IMAGE_ATTRIBUTE_INDEX, s.getImagePath());

            return attributes;
        }
        else {
            return null;
        }
    }

    /**
     * This method will generate the next song index based on the flags activated and the current song index
     * @return true if the index was generated correctly, false if the current song is the last of the playlist and
     * therefore no more indexes can be generated
     */
    public boolean generateNextIndex() {
        if(randomSong){
            trail.push(currentSongIndex);
            Random rand = new Random();
            int r;
            do {
                r = rand.nextInt(songs.size());
            } while (currentSongIndex == r);
            currentSongIndex = r;
        } else {
            if (repeatPlaylist && currentSongIndex >= (songs.size() - 1)) {
                trail.push(currentSongIndex);
                currentSongIndex = 0;
            } else if (currentSongIndex < (songs.size() - 1)){
                trail.push(currentSongIndex);
                currentSongIndex++;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Toggles the random song flag
     */
    public void toggleRandom() {
        randomSong = !randomSong;
    }

    /**
     * Toggles the loop playlist flag
     */
    public void toggleLoopPlaylist() {
        repeatPlaylist = !repeatPlaylist;
    }

    /**
     * This method will resume the song thread, and it's timer thread
     */
    public void resumeSong() {
        if (player != null) {
            player.start();
            songTimer.start();
        }
    }

    /**
     * This method will pause the song thread, and it's timer thread
     */
    public void pauseSong() {
        if (player != null) {
            player.stop();
            songTimer.stop();
        }
    }

    /**
     * This method will kill the current song thread and timer
     */
    public void killSong() {
        if (player != null) {
            //if (player.isRunning()) {
                player.stop();
                player.close();
                player.flush();
                player.drain();
                player = null;
                songTimer.stop();
                songTimer = null;
            //}
        }
    }

    /**
     * This method is used to set the volume of the system audio output
     * @param decibelsReduce value ranging from -80 to 6 corresponding to the dB reduce
     */
    public void setAudioControlLevel(float decibelsReduce) {
        if (audioControl != null) {
            audioControl.setValue(decibelsReduce);
        }
    }

    /**
     * This method is used to know whether a song has ended
     * @return true if song has ended false otherwise
     */
    public boolean songEnded() {
        return currentSongLength <= player.getMicrosecondPosition()/1000000;
    }

    /**
     * This method will prepare the previous song played
     * @return true if a previous song was found and loaded correctly, false otherwise
     */
    public boolean setPreviousSongIndex() {
        if (trail == null) return false;
        try {
            if (trail.size() >= 1) {
                currentSongIndex = trail.pop();
                return true;
            } else {
                return false;
            }
        } catch (IndexOutOfBoundsException | EmptyStackException e){
            return false;
        }
    }

    /**
     * This method will check if a given list of songs is exactly the same as the currently loaded in the instance.
     * @param songs list of songs
     * @return true if the lists match, false otherwise
     */
    public boolean isSamePlaylist(ArrayList<ArrayList<String>> songs) {
        if (this.songs.size() > 0 && this.songs.size() == songs.size()) {
            for (int i = 0; i < this.songs.size(); i++) {
                if (this.songs.get(i).getId() != Integer.parseInt(songs.get(i).get(SongManager.SONG_ID_ATTRIBUTE_INDEX))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is called to force a currentSongIndex
     * @param currentSongIndex new index to set the value
     */
    public void setCurrentSongIndex(int currentSongIndex) {
        trail.push(currentSongIndex);
        this.currentSongIndex = currentSongIndex;
    }

    /**
     * This method is called to clear all values from the instance from RAM
     */
    public void clearData() {
        killSong();
        this.songs = new ArrayList<>();
        this.currentSongIndex = 0;
        this.repeatPlaylist = false;
        this.randomSong = false;
        this.currentSongLength = 0;
        trail = null;
    }

    /**
     * Gets the ID of the song that's currently playing
     * @return an integer representing the song
     */
    public int getCurrentSong() {
        if (player == null) return -1;
        if (songs.isEmpty() || !player.isRunning()) {
            return -1;
        }
        return songs.get(currentSongIndex).getId();
    }

    /**
     * This method return the current song location in seconds
     * @return integer
     */
    public int getCurrentSongSecond() {
        if (player != null){
            return (int)(player.getMicrosecondPosition()/1000000L);
        }
        return 0;
    }
}