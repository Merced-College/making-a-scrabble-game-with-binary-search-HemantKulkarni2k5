import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ScrabbleGame {
    private List<Word> words;

    // Improvement Idea:
    // Allow users to earn points based on the length of the valid word they input.
    // Points: 1 point for 1 letter, 2 points for 2 letters, ..., up to 10 points for 10 letters or more.
    // Additionally, add functionality to allow the user to exchange one of their letters if they can't form a valid word.

    public ScrabbleGame() {
        words = new ArrayList<>();
        loadWords("CollinsScrabbleWords_2019.txt");
        Collections.sort(words);
    }

    private void loadWords(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                words.add(new Word(line.trim()));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public boolean isWordGood(String wordToSearch) {
        int left = 0;
        int right = words.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Word midWord = words.get(mid);

            if (midWord.getWord().equals(wordToSearch)) {
                return true;
            }

            if (midWord.getWord().compareTo(wordToSearch) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    public void startGame() {
        Random rand = new Random();
        char[] letters = new char[4];
        for (int i = 0; i < 4; i++) {
            letters[i] = (char) ('A' + rand.nextInt(26)); // Random letters A-Z
        }

        System.out.println("Your letters are: " + Arrays.toString(letters));
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a word made from these letters: ");
        String userWord = scanner.nextLine().toUpperCase();

        if (isWordGood(userWord)) {
            int points = userWord.length(); // Simple scoring based on word length
            System.out.println("Valid word! You earned " + points + " points.");
        } else {
            System.out.println("Invalid word.");
            // New feature: Ask the user if they want to exchange a letter
            System.out.print("Would you like to exchange one of your letters? (yes/no): ");
            String exchangeResponse = scanner.nextLine().toLowerCase();

            if (exchangeResponse.equals("yes")) {
                System.out.print("Enter the letter you want to exchange: ");
                char letterToExchange = scanner.nextLine().toUpperCase().charAt(0);
                // Find the index of the letter to exchange
                int index = -1;
                for (int i = 0; i < letters.length; i++) {
                    if (letters[i] == letterToExchange) {
                        index = i;
                        break;
                    }
                }

                // If the letter is found, replace it with a new random letter
                if (index != -1) {
                    letters[index] = (char) ('A' + rand.nextInt(26)); // New random letter
                    System.out.println("You exchanged your letter! Your new letters are: " + Arrays.toString(letters));
                } else {
                    System.out.println("Letter not found in your letters.");
                }
            }
        }
    }

    public static void main(String[] args) {
        ScrabbleGame game = new ScrabbleGame();
        game.startGame();
    }
}
