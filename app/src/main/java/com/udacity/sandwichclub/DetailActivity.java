package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Arrays;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        String title = sandwich.getMainName();
        setTitle(title);
        populateUI();

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView alsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);
        TextView alsoKnownAsLabel = (TextView) findViewById(R.id.also_known_as_label);
        TextView originTextView = (TextView) findViewById(R.id.origin_tv);
        TextView placeOfOriginLabel = (TextView) findViewById(R.id.place_of_origin_label);
        TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);
        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);

        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.image_not_avail)
                .into(ingredientsIv);

        if (sandwich.getAlsoKnownAs().toString() == "[]") {
            alsoKnownAsLabel.setVisibility(View.GONE);
            alsoKnownAsTextView.setVisibility(View.GONE);
        } else {

            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {

                alsoKnownAsTextView.append(sandwich.getAlsoKnownAs().get(i));
                alsoKnownAsTextView.append(" \n ");
            }
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originTextView.setVisibility(View.GONE);
            placeOfOriginLabel.setVisibility(View.GONE);
        } else {
            originTextView.setText(sandwich.getPlaceOfOrigin());
        }

        descriptionTextView.setText(sandwich.getDescription());

        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            ingredientsTextView.append(sandwich.getIngredients().get(i));
            ingredientsTextView.append(" \n ");
        }
    }
}
