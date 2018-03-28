package com.example.di.miniappii;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by di on 3/25/18.
 */

public class Recipe {
    //instance variables, usually set up as private with set and get method
    public String title;
    public String imageURL;
    public String url;
    public String description;
    public String servingNumber;
    public String servingLabel;
    public String prepTime;
    public Integer prepTimeinMin;
//    public String prepTimeLabel;
    public String dietLabel;

//    public static ArrayList<String> dietLabelList;
    public static ArrayList<String> servingsLabelList;
    public static ArrayList<String> prepTimeLabelList;

    //default empty constructor

    //static method that reads JSON file in
    public static ArrayList<Recipe> getRecipeFromFile(String filename, Context context){
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

        //try to read JSON file
        //get information by using tags
        //construct a recipe object for each recipe in JSON
        //add the object to the array
        //return arrayList

        try{
            String jsonString = loadJsonFromAsset("recipes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");

            //for loop to go through each movie in the movie array
            for(int i = 0; i < recipes.length(); i++){
                Recipe recipe = new Recipe();
                recipe.title = recipes.getJSONObject(i).getString("title");
                recipe.imageURL = recipes.getJSONObject(i).getString("image");
                recipe.url = recipes.getJSONObject(i).getString("url");
                recipe.description = recipes.getJSONObject(i).getString("description");
                recipe.servingNumber = recipes.getJSONObject(i).getString("servings");

                //parse servingNumber string to servingLabel string for search's filtering in ResultActivity.class
                int servingsize = Integer.valueOf(recipe.servingNumber);
                if(servingsize < 4){
                    recipe.servingLabel = "less than 4";
                }
                else if(servingsize >= 4 && servingsize <= 6){
                    recipe.servingLabel = "4-6";
                }
                else if(servingsize >= 7 && servingsize <= 9){
                    recipe.servingLabel = "7-9";
                }
                else if(servingsize >= 10){
                    recipe.servingLabel = "more than 10";
                }
                else{
                    recipe.servingLabel = "";
                }

                //parse prepTime string to prepTime int for search's filtering in ResultActivity.class
                recipe.prepTime = recipes.getJSONObject(i).getString("prepTime");
                if(recipe.prepTime.contains("and")){
                    String[] tokens = recipe.prepTime.split("and");
                    int hours = Integer.parseInt(tokens[0].replaceAll("[\\D]", ""));
                    int hourtoMin = hours * 60;
                    int mins = Integer.parseInt(tokens[1].replaceAll("[\\D]", ""));
                    recipe.prepTimeinMin = hourtoMin + mins;
                }
                else if(recipe.prepTime.contains("min") && ! recipe.prepTime.contains("and")){
                    int mins = Integer.parseInt(recipe.prepTime.replaceAll("[\\D]", ""));
                    recipe.prepTimeinMin = mins;
                }
                else if(recipe.prepTime.contains("hour") && ! recipe.prepTime.contains("and")){
                    int hours = Integer.parseInt(recipe.prepTime.replaceAll("[\\D]", ""));
                    recipe.prepTimeinMin = hours * 60;
                }


//                //parse into prepTimeLabel part
//                //"","30 min or less", "less than 1 hour", "more than 1 hour"
//                if(recipe.prepTimeinMin <= 30){
//                    recipe.prepTimeLabel = "30 min or less";
//                }
//                else if(recipe.prepTimeinMin < )



                recipe.dietLabel = recipes.getJSONObject(i).getString("dietLabel");

//                if(! dietLabelListTemp.contains(recipe.dietLabel)){
//                    dietLabelListTemp.add("dietLabel");
//                }

                recipeList.add(recipe);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }

    public static ArrayList<String> getDietLabelFromFile(String filename, Context context){
        ArrayList<String> dietLabelList = new ArrayList<String>();

        try{
            String jsonString = loadJsonFromAsset("recipes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");

            //for loop to go through each movie in the movie array
            for(int i = 0; i < recipes.length(); i++){
                String dietLabelJson = recipes.getJSONObject(i).getString("dietLabel");
                if(!dietLabelList.contains(dietLabelJson)) {
                    dietLabelList.add(dietLabelJson);
                }
            }

        } catch (JSONException e) {
        e.printStackTrace();
    }

        return dietLabelList;
    }

//    public static ArrayList<String> getServingLabelFromFile(String filename, Context context){
//        ArrayList<String> servingLabelList = new ArrayList<String>();
//
//        try{
//            String jsonString = loadJsonFromAsset("recipes.json", context);
//            JSONObject json = new JSONObject(jsonString);
//            JSONArray recipes = json.getJSONArray("recipes");
//
//            //for loop to go through each movie in the movie array
//            for(int i = 0; i < recipes.length(); i++){
//                String servingLabelJson = recipes.getJSONObject(i).getString("servings");
//                if(!servingLabelList.contains(servingLabelJson)) {
//                    servingLabelList.add(servingLabelJson);
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return servingLabelList;
//    }
//
//    public static ArrayList<String> getTimeLabelFromFile(String filename, Context context){
//        ArrayList<String> timeLabelList = new ArrayList<String>();
//
//        try{
//            String jsonString = loadJsonFromAsset("recipes.json", context);
//            JSONObject json = new JSONObject(jsonString);
//            JSONArray recipes = json.getJSONArray("recipes");
//
//            //for loop to go through each movie in the movie array
//            for(int i = 0; i < recipes.length(); i++){
//                String timeLabelJson = recipes.getJSONObject(i).getString("prepTime");
//                if(!timeLabelList.contains(timeLabelJson)) {
//                    timeLabelList.add(timeLabelJson);
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return timeLabelList;
//    }

    //JSON helper method that loads data
    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
