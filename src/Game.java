import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    private int numberToGuess;

    // stores highest score for each level
    private int[] highScores = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};

    public void displayMenu() {
        System.out.println("""
                \nWelcome to the Number Guessing Game!
                I'm thinking of a number between 1 and 100.
                You have a limited number of chances to guess the correct number.

                Please select the difficulty level:
                1. Easy (10 chances)
                2. Medium (5 chances)
                3. Hard (3 chances)
                """);
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
                scanner.next(); // vider le buffer
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
        if (score < highScores[level - 1])  {
            highScores[level - 1] = score;
        }
    }

    public void displayHighScores() {
        System.out.println("\n\uD83C\uDFC6 HIGH SCORES:");
        for (int i = 0; i < highScores.length; i++) {
            if (highScores[i] == Integer.MAX_VALUE) {
                System.out.println(getLevelName(i+1) + ": Not set yet");
            } else {
                System.out.println(getLevelName(i+1) + ": " + highScores[i] + " attempts.");
            }
        }
    }

    public void playGame() {
        numberToGuess = random.nextInt(100) + 1;

        int level = getLevel();
        int maxAttempts = getNbOfChances(level);
        String levelName = getLevelName(level);

        System.out.println("\nGreat! You have selected the " + levelName + " difficulty level.");
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
                    System.out.println("Incorrect! The number is greater than " + userGuess + ".");
                } else if (userGuess > numberToGuess) {
                    System.out.println("Incorrect! The number is less than " + userGuess + ".");
                } else {
                    hasWon = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter a number between 1 and 100.");
                scanner.next();
            }
        }

        if (hasWon) {
            System.out.println("Congratulations! You guessed the correct number in " + attempts + "/" + maxAttempts + " attempts");
            updateHighScores(level, attempts);
        } else {
            System.out.println("Game over! The correct number was: " + numberToGuess);
        }

        long endTime = System.currentTimeMillis();
        long timeInSeconds = (endTime - startTime) / 1000;
        System.out.println("Time taken: " + timeInSeconds + " seconds");

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
        System.out.println("\nThank you for playing! Goodbye!");
    }
}
