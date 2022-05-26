package presentation.controllers;

public interface LyricsListener {

    void notifyLyricsDone(String lyrics);
    void notifyError(String message);
}
