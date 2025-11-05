package com.example.wordlle;

public class WordRepository {

    public static String[] animals = {
            "lion", "tiger", "zebra", "horse", "mouse", "eagle", "shark", "snake", "dog", "cat"
    };

    public static String[] sports = {
            "tennis", "soccer", "boxing", "rugby", "golf", "cricket", "hockey", "karate"
    };

    public static String[] countries = {
            "israel", "france", "japan", "brazil", "canada", "spain", "egypt", "sweden"
    };

    public static String getRandomWord(String category) {
        String[] words;
        switch (category) {
            case "animals":
                words = animals;
                break;
            case "sports":
                words = sports;
                break;
            case "countries":
                words = countries;
                break;
            default:
                words = animals;
        }
        int index = (int) (Math.random() * words.length);
        return words[index];
    }
}
