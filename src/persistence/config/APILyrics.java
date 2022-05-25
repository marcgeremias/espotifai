package persistence.config;

import com.sun.security.auth.UnixNumericGroupPrincipal;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Public class that calls lyrics.ovh for song lyrics
 */
public class APILyrics {
    private static final String URL = "https://api.lyrics.ovh/v1/";
    private static final char separator = '/';
    private String lyrics;

    /**
     * Sends a GET request for a specific song
     * @param songTitle: a String containing the title of the song
     * @param songAuthor: a String containing the author of the song
     * @return a String with the song lyrics, null in case there aren't lyrics for this song
     * @throws Exception In case there is an error on the HTTP Connection
     */
    public String makeLyricsRequest(String songTitle, String songAuthor) throws Exception {
        String stringBuilder = URL +
                songAuthor.replace(" ", "_") +
                separator +
                songTitle.replace(" ", "_");

        URL url = new URL(stringBuilder);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();

        if(responseCode != 200){
            lyrics = null;
        }else{
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            scanner.close();
            JSONParser parse = new JSONParser();
            org.json.simple.JSONObject data_obj = (JSONObject) parse.parse(inline);

            lyrics = (String) data_obj.get("lyrics");
        }

        return lyrics;
    }


}
