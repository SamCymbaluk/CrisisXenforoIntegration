package net.ultimatemc.xenforointegration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public class XenApiWrapper {

    private final String WEBSITE;
    private final String API_KEY;

    public XenApiWrapper(String website, String apiKey) {
        this.WEBSITE = website;
        this.API_KEY = apiKey;
    }

    public JsonObject executeQuery(String action, Map<String, String> params) {
        URL url;

        //Build REST url from param map
        String urlStr = "?action=" + action + "&hash=" + API_KEY;
        for (Entry<String, String> entry : params.entrySet()) {
            urlStr = urlStr + "&" + entry.getKey();
            if (entry.getValue() != null) urlStr = urlStr + "=" + entry.getValue();
        }

        try {
            url = new URL(WEBSITE + urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        return readFromUrl(url);
    }

    private JsonObject readFromUrl(URL url) {
        String jsonStr = "";
        try {
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) jsonStr = jsonStr + inputLine;

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        System.out.println("Executed XenApi Query: " + jsonStr);

        return (new JsonParser()).parse(jsonStr).getAsJsonObject();
    }


}