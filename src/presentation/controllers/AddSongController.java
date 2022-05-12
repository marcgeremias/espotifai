package presentation.controllers;

import business.SongManager;
import business.UserManager;
import persistence.SongDAOException;
import presentation.views.AddSongView;

import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class AddSongController implements ActionListener {

    private PlayerViewListener listener;
    private AddSongView addSongView;
    private UserManager userManager;
    private SongManager songManager;
    private File songFile;

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
        }
    }

    private boolean allSongInfoCorrect() {
        return songManager.newSongInfoIsCorrect(addSongView.getTitle(), addSongView.getAlbum()) &&
                (addSongView.authorSelected() || (addSongView.newAuthorSelected() &&
                songManager.newAuthorIsValid(addSongView.getAuthorField()))) && songFile != null;
    }

    private void addSong() {
        String author = addSongView.getAuthorItem();

        if (addSongView.newAuthorSelected()) {
            addSongView.addAuthor(addSongView.getAuthorField());
            author = addSongView.getAuthorField();
        }

        try {
            songManager.addSong(
                    songFile,
                    addSongView.getTitle(),
                    addSongView.getAlbum(),
                    addSongView.getGenre(),
                    author,
                    addSongView.getAlbumCover(),
                    userManager.getCurrentUser());
        } catch (SongDAOException ex) {
            addSongView.showErrorDialog(CREATE_SONG_ERROR);
        } catch (UnsupportedAudioFileException ex) {
            addSongView.showErrorDialog(UNSUPPORTED_AUDIO_FILE_ERROR);
        } catch (IOException ex) {
            // TODO: Handle exception
        }

        addSongView.resetView();
    }

    private void manageErrors() {
        // TODO: Implement method newAuthorIsValid in SongManager
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
}