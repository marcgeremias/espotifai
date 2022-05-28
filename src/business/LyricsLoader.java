package business;

import persistence.config.APILyrics;
import presentation.controllers.LyricsListener;

/**
 * Public class that runs a thread to fetch the lyrics so the main thread is not interrupted while
 * the request is being processed
 */
public class LyricsLoader extends Thread {
    private LyricsListener lyricsListener;
    private String author;
    private String songName;

    /**
     * Public constructor for the lyrics' loader thread class
     * @param lyricsListener lyrics listener
     * @param songName String containing song name
     * @param author String containing author name
     */
    public LyricsLoader(LyricsListener lyricsListener, String songName, String author){
        this.lyricsListener = lyricsListener;
        this.author = author;
        this.songName = songName;
    }

    /**
     * Override method for the thread implementation
     */
    @Override
    public void run() {
        super.run();
        String lyrics = APILyrics.makeLyricsRequest(songName, author);
        if (lyrics != null) {
            lyricsListener.notifyLyricsDone(lyrics);
        } else {
            lyricsListener.notifyError();
        }
    }
}
