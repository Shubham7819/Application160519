package com.example.ideal48.application160519;

import android.text.TextUtils;
import android.util.Log;

import com.example.ideal48.application160519.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils(){

    }

    public static List<Anime> fetchAnimeData(String requestUrl){
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the Http request.", e);
        }

//        Log.i(LOG_TAG,"== response: "+jsonResponse);

        List<Anime> animeList = extractResultsFromJson(jsonResponse);

        return animeList;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "problem creating url : ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON result.", e);
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();

        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Anime> extractResultsFromJson(String animeJSON) {

        if (TextUtils.isEmpty(animeJSON)){
            return null;
        }

        List<Anime> anime = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(animeJSON);

            JSONArray animeArray = baseJsonResponse.getJSONArray("top");

            for (int i = 0; i < animeArray.length(); i++){
                JSONObject currentAnimeJSON = animeArray.getJSONObject(i);

                String title = currentAnimeJSON.optString("title");
                String type = currentAnimeJSON.optString("type");
                int episodes = currentAnimeJSON.optInt("episodes");
                double score = currentAnimeJSON.optDouble("score");
                String imageUrl = currentAnimeJSON.optString("image_url");

//                Anime currentAnime = new Anime(title, type, episodes, score, imageUrl);
//                anime.add(currentAnime);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "problem parsing the news json results", e);
        }
        return anime;
    }

}
