package persistence.config;

import com.sun.security.auth.UnixNumericGroupPrincipal;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

/**
 * Public class that calls lyrics.ovh for song lyrics
 */
public class APILyrics {
    private static final String URL = "https://api.lyrics.ovh/v1/";
    private static final char separator = '/';

    /**
     * Sends a GET request for a specific song
     * @param songTitle: a String containing the title of the song
     * @param songAuthor: a String containing the author of the song
     * @return a String with the song lyrics, null in case there aren't lyrics for this song
     */
    public static String makeLyricsRequest(String songTitle, String songAuthor) {
        String stringBuilder = URL +
                songAuthor.replace(" ", "_") +
                separator +
                songTitle.replace(" ", "_");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(stringBuilder))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = null;
        String lyrics = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            org.json.JSONObject object = new JSONObject(response.body());
            lyrics = object.getString("lyrics");
        } catch (IOException | InterruptedException | JSONException ignored) {
        }
        return lyrics;
    }


}
