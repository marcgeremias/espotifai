package presentation.controllers;

public interface LyricsListener {
    /**
     * Notifies that the lyrics finished loading
     * @param lyrics a String containing the lyrics
     */
    void notifyLyricsDone(String lyrics);

    /**
     * Display an error message
     */
    void notifyError();
}
