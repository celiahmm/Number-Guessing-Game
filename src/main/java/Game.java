import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONObject;
import java.io.FileWriter;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    private HighScores highScores;
    private final String fileName = "highScores.json";

    public Game() {
        loadHighScores();
    }

    public void displayMenu() {

        System.out.println(Colors.BOLD + Colors.YELLOW);
        System.out.println(
                """
                \n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                *               Welcome to the Number Guessing Game!                 *
                *          I'm thinking of a number between 1 and 100.               *
                *  You have a limited number of chances to guess the correct number  *
                *                                                                    *
                *               Please select the difficulty level:                  *
                *                   1. Easy (10 chances)                             *
                *                   2. Medium (5 chances)                            *
                *                   3. Hard (3 chances)                              *
                * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                """
                );
        System.out.println(Colors.RESET);

    }

    private int getLevel() {
        int level = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter your choice: ");
                level = scanner.nextInt();
                if (level >= 1 && level <= 3) {
                    validInput = true;
                } else {
                    System.out.println("Invalid choice. Please enter 1, 2 or 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                scanner.next();
            }
        }
        return level;
    }

    String getLevelName(int level) {
        return switch (level) {
            case 1 -> "Easy";
            case 2 -> "Medium";
            case 3 -> "Hard";
            default -> "Unknown";
        };
    }

    private int getNbOfChances(int level) {
        return switch (level) {
            case 1 -> 10;
            case 2 -> 5;
            case 3 -> 3;
            default -> 0;
        };
    }

    private void updateHighScores(int level, int score) {
        int currentScore = highScores.getScore(level);
        // met à jour si c'est le premier score OU si le nouveau score est meilleur
        if (currentScore == Integer.MAX_VALUE || score < currentScore) {
            highScores.setScore(level, score);
        }
    }

    private void loadHighScores() {
        File file = new File(fileName);

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                StringBuilder content = new StringBuilder(); // construit une chaîne avec StringBuilder
                int c;
                while ((c = reader.read()) != -1) { // lit caractère par caractère (read() retourne -1 à la fin)
                    content.append((char) c);
                }
                // parsing du JSON
                JSONObject json = new JSONObject(content.toString());
                JSONObject scores = json.getJSONObject("scores");

                highScores = new HighScores(
                        scores.optInt("easy", Integer.MAX_VALUE),
                        scores.optInt("medium", Integer.MAX_VALUE),
                        scores.optInt("hard", Integer.MAX_VALUE)
                );
            } catch (IOException e) {
                System.out.println("Erreur lors du chargement des scores : " + e.getMessage());
                highScores = new HighScores();
            }
        } else {
            highScores = new HighScores();
        }
    }

    private void saveHighScores() {
        JSONObject scores = new JSONObject();
        scores.put("easy", highScores.getEasy());
        scores.put("medium", highScores.getMedium());
        scores.put("hard", highScores.getHard());

        JSONObject json = new JSONObject();
        json.put("scores", scores);

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(json.toString(2)); // 2 = indentation pour la lisibilité
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    public void displayHighScores() {
        System.out.println(Colors.BLUE + Colors.BOLD + "\n* * * * * * * * * * * *" + Colors.RESET );
        System.out.println(Colors.BOLD + "🏆 HIGH SCORES:     " + Colors.RESET);
        for (int i = 1; i <= 3; i++) {
            int score = highScores.getScore(i);
            if (score == Integer.MAX_VALUE) {
                System.out.println(getLevelName(i) + ": Not set yet");
            } else {
                System.out.println(getLevelName(i) + ": " + score + " attempts.");
            }
        }
        System.out.println(Colors.BLUE + Colors.BOLD + "* * * * * * * * * * * *" + Colors.RESET);
    }


    public void playGame() {

        int numberToGuess = random.nextInt(100) + 1;

        int level = getLevel();
        int maxAttempts = getNbOfChances(level);
        String levelName = getLevelName(level);

        System.out.println("\nGreat! You have selected the " + Colors.BOLD + Colors.GREEN + levelName.toLowerCase() + Colors.RESET + " difficulty level.");
        System.out.println("Let's start the game!");

        int attempts = 0;
        boolean hasWon = false;

        long startTime = System.currentTimeMillis();

        while (attempts < maxAttempts && !hasWon) {
            try {
                System.out.print("\nEnter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess < numberToGuess) {
                    System.out.println("Incorrect! The number is " + Colors.BOLD + Colors.CYAN + "greater " + Colors.RESET+ "than "  + userGuess + ".");
                } else if (userGuess > numberToGuess) {
                    System.out.println("Incorrect! The number is " + Colors.BOLD + Colors.CYAN + "less " +Colors.RESET+ "than "  + userGuess + ".");
                } else {
                    hasWon = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid choice. Please enter a number between 1 and 100.");
                scanner.next();
            }
        }

        long endTime = System.currentTimeMillis();
        long timeInSeconds = (endTime - startTime) / 1000;

        if (hasWon) {
            System.out.println(Colors.BOLD + Colors.YELLOW + "\nCongratulations!" +Colors.RESET+ " You guessed the correct number in " + attempts + "/" + maxAttempts + " attempts");
            System.out.println("Time taken: " + timeInSeconds + " seconds");
            updateHighScores(level, attempts);
            saveHighScores();
        } else {
            System.out.println(Colors.BOLD + Colors.RED + "\nGame over! " +Colors.RESET+ "The correct number was: " +Colors.RESET+ numberToGuess);
        }

        displayHighScores();
    }

    public void start() {
        boolean playAgain = true;

        while (playAgain) {
            displayMenu();
            playGame();
            System.out.print("\nPlay again? (yes/no): ");
            String response = scanner.next().toLowerCase();
            playAgain = response.equals("yes") || response.equals("y");
        }
        scanner.close();
        System.out.println("\nThank you for playing! Goodbye!\n");
    }
}
