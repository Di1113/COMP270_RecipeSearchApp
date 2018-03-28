package com.example.di.miniappii;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by di on 3/25/18.
 */

public class ResultActivity extends AppCompatActivity{

    private ListView mListView;
    private TextView noRecipesFound;
    private Context mContext;
    private int recipesFound = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mContext = this;
        setTitle("Result");

        //data to display
        final ArrayList<Recipe> recipeArrayList = Recipe.getRecipeFromFile("recipes.json",this);

        //get string tags from intent
        String dietFilter = this.getIntent().getExtras().getString("diet_label");
        String servingFilter = this.getIntent().getExtras().getString("serving_label");
        String prepTimeFilter = this.getIntent().getExtras().getString("prepTime_label");

        //filter by the string tags
        ArrayList<Recipe> filteredRecipesList = new ArrayList<Recipe>();
        for(int i = 0; i < recipeArrayList.size(); i++){

            Boolean dietLabelMatches = recipeArrayList.get(i).dietLabel.equals(dietFilter);
            if(dietFilter.equals("")) {
                dietLabelMatches = true;
            }

            Boolean servingLabelMatches = recipeArrayList.get(i).servingLabel.equals(servingFilter);
            if(servingFilter.equals("")) {
                servingLabelMatches = true;
            }

            Boolean prepLabelMatches = false;
            if(prepTimeFilter.equals("")){
                prepLabelMatches = true;
            }
            else if(prepTimeFilter.equals("30 min or less")){
                if(recipeArrayList.get(i).prepTimeinMin <= 30){
                    prepLabelMatches = true;
                }
                else {
                    prepLabelMatches = false;
                }
            }
            else if(prepTimeFilter.equals("less than 1 hour")){
                if(recipeArrayList.get(i).prepTimeinMin <= 60){
                    prepLabelMatches = true;
                }
                else {
                    prepLabelMatches = false;
                }
            }
            else if(prepTimeFilter.equals("more than 1 hour")){
                if(recipeArrayList.get(i).prepTimeinMin > 60){
                    prepLabelMatches = true;
                }
                else {
                    prepLabelMatches = false;
                }
            }

            if(dietLabelMatches && servingLabelMatches && prepLabelMatches){
                filteredRecipesList.add(recipeArrayList.get(i));
                recipesFound++ ;
            }
        }

        //create the adapter
        RecipeAdapter adapter = new RecipeAdapter(this, filteredRecipesList);

        //find the list view, set adapter to it
        mListView = findViewById(R.id.recipe_list);
        noRecipesFound = findViewById(R.id.no_recipes_found);
        noRecipesFound.setText(recipesFound + " recipes found");

        mListView.setAdapter(adapter);


    }
}
