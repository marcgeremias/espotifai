package presentation.controllers;

import business.SongManager;
import business.UserManager;
import persistence.SongDAOException;
import presentation.views.AddSongView;
import presentation.views.PlayerView;

import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Public controller to control the creation of new songs
 */
public class AddSongController implements ActionListener {

    private PlayerViewListener listener;
    private AddSongView addSongView;
    private UserManager userManager;
    private SongManager songManager;
    private File songFile;
    private File imageFile;

    /**
     * Creates an instance of AddSongController
     * @param listener an instance of PlayerViewListener
     * @param addSongView an instance of AddSongView
     * @param userManager an instance of UserManager
     * @param songManager an instance of SongManager
     */
    public AddSongController(PlayerViewListener listener, AddSongView addSongView, UserManager userManager,
                          SongManager songManager) {
        this.listener = listener;
        this.addSongView = addSongView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.songFile = null;
    }

    private final String BLANK_FIELD_ERROR = "You must fill all the fields";
    private final String WRONG_NEW_AUTHOR = "Author already exists!";
    private final String WRONG_SONG_TITLE = "Song already exists!";
    private final String NO_FILE_ERROR = "No file has been selected";
    private final String UNSUPPORTED_AUDIO_FILE_ERROR = "Unsupported audio file";
    private final String CREATE_SONG_ERROR = "Error creating song";

    /**
     * Decides which action to execute
     * @param e an instance of ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case AddSongView.BTN_ADD_SONG:
                // check song info
                if (allSongInfoCorrect()) {
                    addSong();
                } else {
                    manageErrors();
                }

                break;
            case AddSongView.COMBOBOX_AUTHOR:
                if (addSongView.newAuthorSelected()) {
                    addSongView.showAuthorField();
                } else {
                    addSongView.hideAuthorField();
                }

                break;
            case AddSongView.BTN_SELECT_FILE:
                songFile = addSongView.selectFile();
                break;
            case AddSongView.BTN_SELECT_IMAGE:
                imageFile = addSongView.selectImage();
                break;
        }
    }

    private boolean allSongInfoCorrect() {
        return songManager.newSongInfoIsCorrect(addSongView.getTitle(), addSongView.getAlbum()) &&
                (addSongView.authorSelected() || (addSongView.newAuthorSelected() &&
                songManager.newAuthorIsValid(addSongView.getAuthorField()))) && songFile != null;
    }

    private void addSong() {
        String author = addSongView.getAuthorItem();
        String path;

        if (imageFile == null) {
            path = "no path";
        } else {
            path = imageFile.getPath();
        }

        if (addSongView.newAuthorSelected()) {
            //addSongView.addAuthor(addSongView.getAuthorField());
            author = addSongView.getAuthorField();
        }

        try {
            songManager.addSong(
                    songFile,
                    imageFile,
                    addSongView.getTitle(),
                    addSongView.getAlbum(),
                    addSongView.getGenre(),
                    author,
                    path,
                    userManager.getCurrentUser());
                listener.changeView(PlayerView.SONG_LIST_VIEW);
        } catch (SongDAOException ex) {
            addSongView.showErrorDialog(CREATE_SONG_ERROR);
        } catch (UnsupportedAudioFileException ex) {
            addSongView.showErrorDialog(UNSUPPORTED_AUDIO_FILE_ERROR);
        } catch (IOException ex) {
            addSongView.showErrorDialog(ex.getMessage());
        }

        addSongView.resetView();
    }

    private void manageErrors() {
        if (addSongView.newAuthorSelected() && !songManager.newAuthorIsValid(addSongView.getAuthorField())) {
            if (addSongView.getAuthorField().isBlank()) {
                addSongView.incorrectAuthorField(BLANK_FIELD_ERROR);
            } else {
                addSongView.incorrectAuthorField(WRONG_NEW_AUTHOR);
            }

            addSongView.restoreTitleField();
            addSongView.restoreAlbumField();
            addSongView.restoreAuthorSelector();
            addSongView.restoreFileButton();
            addSongView.revalidate();
        } else if (!addSongView.authorSelected() && !addSongView.newAuthorSelected()) {
            addSongView.restoreTitleField();
            addSongView.restoreAlbumField();
            addSongView.restoreAuthorField();
            addSongView.restoreFileButton();
            addSongView.incorrectAuthorSelection();
            addSongView.revalidate();
        } else {
            if (addSongView.getTitle().isBlank()) {
                addSongView.restoreAlbumField();
                addSongView.restoreFileButton();
                addSongView.incorrectTitleField(BLANK_FIELD_ERROR);
            } else if (addSongView.getAlbum().isBlank()) {
                addSongView.restoreTitleField();
                addSongView.restoreFileButton();
                addSongView.incorrectAlbumField(BLANK_FIELD_ERROR);
            } else if (songFile == null) {
                addSongView.restoreTitleField();
                addSongView.restoreAlbumField();
                addSongView.incorrectFile(NO_FILE_ERROR);
            } else {
                addSongView.restoreAlbumField();
                addSongView.restoreFileButton();
                addSongView.incorrectTitleField(WRONG_SONG_TITLE);
            }

            addSongView.restoreAuthorSelector();
            addSongView.restoreAuthorField();
            addSongView.revalidate();
        }
    }

    /**
     * Initializes the view
     */
    public void initView(){
        addSongView.setAuthors(songManager.getAuthors());
    }
}