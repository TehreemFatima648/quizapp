package com.example.projject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;
public class MainActivity2 extends AppCompatActivity {
    private static final int QUIZ = 1;
    public static final String CATEGORY_ID = "extraCategoryID";
    public static final String CATEGORY_NAME = "CategoryName";
    public static final String DIFFICULTY = "Difficulty";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String HIGHSCORE = "keyHighscore";
    //public static final String EXTRA_SCORE = "extraScore";
    private TextView textViewHighscore;
    private Spinner spinnerCategory;
    int a=0;
    private Spinner spinnerDifficulty;
    private int highscore,scoree;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //textViewHighscore = findViewById(R.id.text_view_highscore);
        spinnerCategory = findViewById(R.id.spinner_category);
        spinnerDifficulty = findViewById(R.id.spinner_difficulty);
        loadCategories();
        loadDifficultyLevels();
        //loadHighscore();
        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
    }
    private void startQuiz() {
        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();
        int categoryID = selectedCategory.getId();
        String categoryName = selectedCategory.getName();
        String difficulty = spinnerDifficulty.getSelectedItem().toString();
        Intent intent = new Intent(MainActivity2.this, Quiz.class);
        intent.putExtra(CATEGORY_ID, categoryID);
        intent.putExtra(CATEGORY_NAME, categoryName);
        intent.putExtra(DIFFICULTY, difficulty);
        startActivityForResult(intent, QUIZ);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(Quiz.EXTRA_SCORE, 0);
                if (score > highscore) {
                    updateHighscore(score);
                }
            }
        }
    }
    private void loadCategories() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Category> categories = dbHelper.getAllCategories();
        ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategories);
    }
    private void loadDifficultyLevels() {
        String[] difficultyLevels = Question.getAllDifficultyLevels();
        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
    }
    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt("extraScore", 0);

        textViewHighscore.setText(" Level up your Highscore: " + highscore );


    }
    private void updateHighscore(int highscoreNew) {

        highscore = highscoreNew;
        textViewHighscore.setText(" Level up your Highscore: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
       SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("extraScore",highscore);
        editor.apply();
    }
}