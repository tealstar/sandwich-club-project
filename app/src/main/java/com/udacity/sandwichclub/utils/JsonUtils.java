package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject sandwichDetails = jsonObject.getJSONObject("name");
                String mainName = sandwichDetails.getString("mainName");

                String image = jsonObject.getString("image");

                JSONArray alsoKnownAsArray = sandwichDetails.getJSONArray("alsoKnownAs");
                ArrayList<String> alsoKnownAsList = new ArrayList<>();

                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsArray.getString(i));
                }

                String placeOfOrigin = jsonObject.getString("placeOfOrigin");
                String description = jsonObject.getString("description");

                JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
                ArrayList<String> ingredientsList = new ArrayList<>();

                for (int i = 0; i < ingredientsArray.length(); i++) {
                    ingredientsList.add(ingredientsArray.getString(i));
                }

                return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
