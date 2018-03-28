package com.example.di.miniappii;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by di on 3/25/18.
 */

public class SearchActivity extends AppCompatActivity{

    private Context mContext;
    private Spinner dietSpinner;
    private Spinner servingSpinner;
    private Spinner prepTimeSpinner;
    private Button searchButton;
    private String dietLabelSelected;
    private String servingLabelSelected;
    private String prepTimeLabelSelected;
    Recipe recipe = new Recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;

        setTitle("Search");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dietSpinner = findViewById(R.id.diet_spinner);
        ArrayList<String> dietLabelList = recipe.getDietLabelFromFile("recipes.json", this);
        dietLabelList.add(0, "");
        ArrayAdapter<String> dietSpinnerdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, dietLabelList);
        dietSpinner.setAdapter(dietSpinnerdapter);
        dietSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dietLabelSelected = dietSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                dietLabelSelected = "";
            }
        });

        servingSpinner = findViewById(R.id.serving_spinner);
        String[] servingLabelList = new String[]{"", "less than 4", "4-6", "7-9", "more than 10"};
        ArrayAdapter<String> servingSpinnerdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, servingLabelList);
        servingSpinner.setAdapter(servingSpinnerdapter);
        servingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                servingLabelSelected = servingSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                servingLabelSelected = "";
            }
        });

        prepTimeSpinner = findViewById(R.id.prepTime_spinner);
        String[] timeLabelList = new String[]{"","30 min or less", "less than 1 hour", "more than 1 hour"};
        ArrayAdapter<String> timeSpinnerdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, timeLabelList);
        prepTimeSpinner.setAdapter(timeSpinnerdapter);
        prepTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prepTimeLabelSelected = prepTimeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                prepTimeLabelSelected = "";
            }
        });

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultAcitivityIntent = new Intent(mContext, ResultActivity.class);

                //2.
                //add data of title, poster and description to the activity
                resultAcitivityIntent.putExtra("diet_label", dietLabelSelected);
                resultAcitivityIntent.putExtra("serving_label", servingLabelSelected);
                resultAcitivityIntent.putExtra("prepTime_label", prepTimeLabelSelected);

                //3.
                //start the activity
                startActivity(resultAcitivityIntent);
            }
        });

    }
}
