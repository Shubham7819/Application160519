package com.example.ideal48.application160519;

import android.util.Log;

import com.example.ideal48.application160519.model.Anime;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AnimesJsonDeserializer implements JsonDeserializer {

    private static String TAG = AnimesJsonDeserializer.class.getSimpleName();

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<Anime> animes = null;
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray animesJsonArray = jsonObject.getAsJsonArray("top");
            animes = new ArrayList<>(animesJsonArray.size());
            for (int i = 0; i < animesJsonArray.size(); i++) {
                // adding the converted wrapper to our container
                Anime dematerialized = context.deserialize(animesJsonArray.get(i), Anime.class);
                animes.add(dematerialized);
            }
        } catch (JsonParseException e) {
            Log.e(TAG, String.format("Could not deserialize Anime element: %s", json.toString()));
        }
        return animes;
    }
}
