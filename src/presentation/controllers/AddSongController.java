package presentation.controllers;

import business.SongManager;
import business.UserManager;
import presentation.views.AddSongView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSongController implements ActionListener {

    private PlayerViewListener listener;
    private AddSongView addSongView;
    private UserManager userManager;
    private SongManager songManager;

    public AddSongController(PlayerViewListener listener, AddSongView addSongView, UserManager userManager,
                          SongManager songManager) {
        this.listener = listener;
        this.addSongView = addSongView;
        this.userManager = userManager;
        this.songManager = songManager;
    }

    private final String BLANK_FIELD_ERROR = "You must fill all the fields";
    private final String WRONG_NEW_AUTHOR = "Author already exists!";
    private final String WRONG_SONG_TITLE = "Song already exists!";

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case AddSongView.BTN_ADD_SONG:
                // check song info
                if (allSongInfoCorrect()) {
                    if (addSongView.newAuthorSelected()) {
                        addSongView.addAuthor(addSongView.getAuthorField());
                    }

                    addSongView.resetView();
                    // saveSong
                    /*songManager.addSong(
                            addSongView.getTitle(),
                            addSongView.getAlbum(),
                            addSongView.getGenre(),
                            addSongView.getAuthor(),
                            addSongView.getPath(),
                            userManager.getCurrentUser());*/
                } else if (addSongView.newAuthorSelected() && !songManager.newAuthorIsValid(addSongView.getAuthorField())) {
                    // TODO: Implement method newAuthorIsValid in SongManager
                    if (addSongView.getAuthorField().isBlank()) {
                        addSongView.incorrectAuthorField(BLANK_FIELD_ERROR);
                    } else {
                        addSongView.incorrectAuthorField(WRONG_NEW_AUTHOR);
                    }
                    addSongView.restoreTitleField();
                    addSongView.restoreAlbumField();
                    addSongView.restoreAuthorSelector();
                    addSongView.revalidate();
                } else if (!addSongView.authorSelected() && !addSongView.newAuthorSelected()) {
                    addSongView.restoreTitleField();
                    addSongView.restoreAlbumField();
                    addSongView.restoreAuthorField();
                    addSongView.incorrectAuthorSelection();
                    addSongView.revalidate();
                } else {
                    if (addSongView.getTitle().isBlank()) {
                        addSongView.incorrectTitleField(BLANK_FIELD_ERROR);
                        addSongView.restoreAlbumField();
                    } else if (addSongView.getAlbum().isBlank()) {
                        addSongView.incorrectAlbumField(BLANK_FIELD_ERROR);
                        addSongView.restoreTitleField();
                    } else {
                        addSongView.incorrectTitleField(WRONG_SONG_TITLE);
                        addSongView.restoreAlbumField();
                    }
                    addSongView.restoreAuthorSelector();
                    addSongView.restoreAuthorField();
                    addSongView.revalidate();
                }

                break;
            case AddSongView.COMBOBOX_AUTHOR:
                if (addSongView.newAuthorSelected()) {
                    addSongView.showAuthorField();
                } else {
                    addSongView.hideAuthorField();
                }

                break;
        }
    }

    private boolean allSongInfoCorrect() {
        return songManager.newSongInfoIsCorrect(addSongView.getTitle(), addSongView.getAlbum()) &&
                (addSongView.authorSelected() || (addSongView.newAuthorSelected() &&
                songManager.newAuthorIsValid(addSongView.getAuthorField())));
    }
}