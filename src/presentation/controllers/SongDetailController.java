package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Playlist;
import business.entities.Song;
import persistence.PlaylistDAOException;
import presentation.views.SongDetailView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SongDetailController implements ActionListener {
    private PlayerViewListener listener;
    private SongDetailView songDetailView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;
    private Song currentSong;
    ArrayList<Playlist> allPlaylists;

    public SongDetailController(PlayerViewListener listener, SongDetailView songDetailView, UserManager userManager,
                             SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.songDetailView = songDetailView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    /**
     * Method that initializes the songListView by getting all current songs of the system
     * and passing them to the JTable of all songs in the system
     */
    public void initView(int songNum) {
        // Get song selected
        currentSong = songManager.getAllSongs().get(songNum);
        songDetailView.fillTable(currentSong);

        // Get all playlists
        try {
            allPlaylists = playlistManager.getAllPlaylists();
        } catch (PlaylistDAOException e) {
        }
        songDetailView.showPlaylists(allPlaylists);

        // Get lyrics
        System.out.println(currentSong.getTitle());
        System.out.println(currentSong.getAuthor());
        //String songLyrics = songManager.getLyrics(currentSong.getTitle(), currentSong.getAuthor());
        String songLyrics = "Everyday is drama (qué guapo tío)\n" +
                "I'm a devoted fan\n" +
                "La oscuridad se cierne sobre mi cabeza\n" +
                "Plumas de un cuervo hallé bajo mi almohada\n" +
                "Réquiem de quien desciende y no se defiende\n" +
                "Y del que no encuentra ya el mínimo placer en nada\n" +
                "Bella durmiente sin beso que la despierte\n" +
                "Soy un columpio oxidado por falta de niños\n" +
                "Los fuegos fatuos del sufrimiento perpetuo\n" +
                "Son las débiles luces con que mi verso ilumino\n" +
                "Si la escalera es el símbolo de la paciencia\n" +
                "Yo os escribo desde el primer escalón\n" +
                "Ecos de mi adolescencia, recuerdos de lluvia joven\n" +
                "Lavan un poco el polvo de mi corazón\n" +
                "Todo me pasa a mí\n" +
                "Estoy en cada crío que llora\n" +
                "Y en el abuelo que se cae de la banqueta en un bar\n" +
                "No importa la hora\n" +
                "Sé que camino de un nuevo desatino, me hallo\n" +
                "Fallo, si no me domino y estallo\n" +
                "Everyday is drama\n" +
                "I'm a devoted fan\n" +
                "Everyday is drama\n" +
                "I'm a devoted fan\n" +
                "Nací en el 80\n" +
                "Vi los 90 pasar como un tranvía\n" +
                "Hacia el futuro y el futuro era un muro\n" +
                "Pienso: ¿por qué ya no hay momentos de tranquilidad?\n" +
                "Dime, ¿cómo hemos llegado a esto?\n" +
                "Las oscuras golondrinas de Bécquer\n" +
                "Cruzaron frente al Chevrolet con su augurio funesto\n" +
                "Quiero que mis palabras expresen\n" +
                "Ese silencio y lo solos que se quedan los muertos\n" +
                "Lo que un día fueron gritos idealistas\n" +
                "Tornáronse, hoy, en murmullos lejanos\n" +
                "Me llegan como vagos, rayos de luz ente la lluvia\n" +
                "Soy como un campesino atravesando la penuria\n" +
                "Que le jodan a la fama, me hizo creerme invencible\n" +
                "En un carro de vanidad que solo era heno\n" +
                "Soy un soñador y una persona sensible\n" +
                "Eso no quiere decir que sea inteligente o bueno\n" +
                "Every day is drama\n" +
                "I'm a devoted fan\n" +
                "Every day is drama\n" +
                "I'm a devoted fan\n" +
                "Tengo atracción por el drama. Soy un devoto fan\n" +
                "Y puede que estas aficiones algo malo traigan\n" +
                "Porque ¡jodo, cómo duele en el alma\n" +
                "Cada grito que metía si perdía la calma!\n" +
                "Memorias de un niñato depresivo y blandengue\n" +
                "Relatan la dictadura de una mente estresada\n" +
                "Mi mente salvaje, no supe educarla\n" +
                "Paz para los que llenan su vida de causas, ah";

        //System.out.println(songLyrics);

        //if (songLyrics != null) {
            songDetailView.setSongLyrics(songLyrics);
        //}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SongDetailView.BTN_ADD_PLAYLIST:
                System.out.println("ADD PLAYLIST CLICKED");

                int playlistIndex = songDetailView.getPlaylistIndexSelected();

                if (playlistIndex != -1) {
                    Playlist playlist = allPlaylists.get(playlistIndex);

                    try {
                        if (playlistManager.addSongToPlaylist(playlist.getId(), currentSong.getId())) {
                            System.out.println("THE SONG HAS CORRECTLY ADDED");
                        }

                    } catch (PlaylistDAOException ex) {
                    }
                }
                break;

            case SongDetailView.BTN_PLAY_IMAGE:
                System.out.println("REPRODUCE MUSICCC");
                break;
        }
    }
}
