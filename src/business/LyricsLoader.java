package business;

import persistence.config.APILyrics;
import presentation.controllers.LyricsListener;


public class LyricsLoader extends Thread {
    private LyricsListener lyricsListener;
    private String author;
    private String songName;

    public LyricsLoader(LyricsListener lyricsListener, String songName, String author){
        this.lyricsListener = lyricsListener;
        this.author = author;
        this.songName = songName;
    }

    @Override
    public void run() {
        super.run();

        try {
            String lyrics = APILyrics.makeLyricsRequest(songName, author);
            lyricsListener.notifyLyricsDone(lyrics);

        } catch (Exception e) {
            lyricsListener.notifyError(e.getMessage());
        }
    }
}
