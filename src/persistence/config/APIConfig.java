package persistence.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Public singleton class that contains all the API information to access the song storage system with Dropbox
 */
public class APIConfig {

    private static APIConfig sAPIConfig;
    private static DbxClientV2 client;

    private static final String PATH = "./res/api_config.json";
    private static final String API_PROVIDER = "dropbox";
    public static final String SONGS_ROOT_FOLDER = "songs";
    public static final String COVERS_ROOT_FOLDER = "covers";

    private static String ACCESS_TOKEN;

    private static String REQUEST_TOKEN_URL;
    private static String CLIENT_ID;
    private static String CLIENT_SECRET;
    private static String REFRESH_TOKEN;
    private static String API_NAME;

    /**
     * Public method that returns singleton instance if exists or creates it if it doesn't.
     * @return instance of APIConfig
     */
    public static APIConfig getInstance(){
        if (sAPIConfig == null){
            try {
                sAPIConfig = new APIConfig();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return sAPIConfig;
    }

    /*
    Private constructor only called when instance of singleton hasn't been instantiated before.
     */
    private APIConfig() throws IOException{
        FileReader fr = new FileReader(PATH);
        /*
            Next line reads JSON file and creates an array, we then access the first position, and
            we convert it to a JSON object to retrieve the data
        */
        JsonObject data = JsonParser.parseReader(fr).getAsJsonArray().get(0).getAsJsonObject();

        REQUEST_TOKEN_URL = data.get("api_url").getAsString();
        REFRESH_TOKEN = data.get("refresh_token").getAsString();
        CLIENT_ID = data.get("client_id").getAsString();
        CLIENT_SECRET = data.get("client_secret").getAsString();
        API_NAME = data.get("api_name").getAsString();

        setAccessToken();
        DbxRequestConfig config = DbxRequestConfig.newBuilder(API_PROVIDER+"/"+API_NAME).build();
        client = new DbxClientV2(config, ACCESS_TOKEN);
    }

    /**
     * This method requests a valid access token to the API and sets the ACCESS_TOKEN variable so it can be used
     * later by all the program.
     */
    public void setAccessToken(){
        // Required parameters to get a successful response @see https://developers.dropbox.com/es-es/oauth-guide
        // I'm aware that this code is very 'hardcoded' but it's very difficult to abstract all these parameters
        String params = "?grant_type=refresh_token&refresh_token="+REFRESH_TOKEN+
                "&client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(REQUEST_TOKEN_URL + params))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            //TODO: change sync request to async request for thread safe purposes.
            // Here we send the request and wait for the response, this method is currently blocking all the threads
            // and there is a better alternative with an async function, for now this works
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject object = new JSONObject(response.body());
            ACCESS_TOKEN = object.getString("access_token");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for API client info to perform requests.
     * @return instance of the API client.
     */
    public DbxClientV2 getClient() {
        return client;
    }

}
