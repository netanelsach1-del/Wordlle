package com.example.wordlle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;

public class GameActivity extends AppCompatActivity {

    private String targetWord;
    private int attempts = 0;
    private final int maxAttempts = 6;
    private final int WORD_LENGTH = 5;

    private GridLayout gridBoard;
    private EditText edtGuess;
    private TextView[][] tiles = new TextView[maxAttempts][WORD_LENGTH];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String category = getIntent().getStringExtra("CATEGORY");
        targetWord = WordRepository.getRandomWord(category);
        targetWord = targetWord.substring(0, Math.min(targetWord.length(), WORD_LENGTH)).toLowerCase();

        gridBoard = findViewById(R.id.gridBoard);
        edtGuess = findViewById(R.id.edtGuess);
        Button btnCheck = findViewById(R.id.btnCheck);

        // עדכון מספר העמודות לפי גודל המילה
        gridBoard.setColumnCount(WORD_LENGTH);

        createBoard();

        btnCheck.setOnClickListener(v -> {
            String guess = edtGuess.getText().toString().trim().toLowerCase();
            if (guess.length() != WORD_LENGTH) {
                Toast.makeText(this, "המילה חייבת להיות באורך " + WORD_LENGTH, Toast.LENGTH_SHORT).show();
                return;
            }
            checkGuess(guess);
        });
    }

    /** יוצר את הלוח של הריבועים */
    private void createBoard() {
        int size = 120; // גודל כל ריבוע (אפשר לשנות)

        for (int row = 0; row < maxAttempts; row++) {
            for (int col = 0; col < WORD_LENGTH; col++) {
                TextView tile = new TextView(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = size;
                params.height = size;
                params.setMargins(8, 8, 8, 8);
                tile.setLayoutParams(params);

                tile.setTextSize(24);
                tile.setGravity(Gravity.CENTER);
                tile.setBackgroundColor(Color.LTGRAY);
                tile.setTextColor(Color.BLACK);

                gridBoard.addView(tile);
                tiles[row][col] = tile;
            }
        }
    }

    /** בודק את הניחוש ומעדכן צבעים בלוח */
    private void checkGuess(String guess) {
        if (attempts >= maxAttempts) return;

        for (int i = 0; i < WORD_LENGTH; i++) {
            char c = guess.charAt(i);
            tiles[attempts][i].setText(String.valueOf(Character.toUpperCase(c)));

            if (c == targetWord.charAt(i)) {
                tiles[attempts][i].setBackgroundColor(Color.parseColor("#66BB6A")); // ירוק
            } else if (targetWord.contains(String.valueOf(c))) {
                tiles[attempts][i].setBackgroundColor(Color.parseColor("#FFEB3B")); // צהוב
            } else {
                tiles[attempts][i].setBackgroundColor(Color.parseColor("#BDBDBD")); // אפור
            }
        }

        attempts++;
        edtGuess.setText("");

        if (guess.equals(targetWord)) {
            Toast.makeText(this, "ניצחת! 🎉", Toast.LENGTH_LONG).show();
        } else if (attempts >= maxAttempts) {
            Toast.makeText(this, "המילה הייתה: " + targetWord, Toast.LENGTH_LONG).show();
        }
    }
}




