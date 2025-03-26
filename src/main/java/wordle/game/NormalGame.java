package wordle.game;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import wordle.Database;
import wordle.Lobby;
import wordle.Word;
import wordle.difficulty.Difficulty;

public class NormalGame implements GameMode {
    private final Word word;
    protected final Difficulty difficulty;
    private ArrayList<String[]> board = new ArrayList<>();
    private int nextRow = 0;

    public NormalGame(Word word, Difficulty difficulty) {
        this.word = word;
        this.difficulty = difficulty;
        for (int i = 0; i < 6; i++) {
            board.add(new String[] { "   ", "   ", "   ", "   ", "   " });
        }
    }

    @Override
    public void start() throws Exception {
        System.out.println("-------------------------------------");
        System.out.println("Game started.");
        showBoard();
    }

    @Override
    public void end() {
        System.out.println("-------------------------------------");
        System.out.println("Game ended");
    }

    @Override
    public void showBoard() throws Exception {
        System.out.println("-------------------------------------");
        System.out.println("Guess a word");
        System.out.println("$ - is in the word and in the correct spot");
        System.out.println("# - is in the word but in the wrong spot");
        System.out.println("Empty - isn't in the target word at all");
        System.out.println();
        for (String[] row : board) {
            System.out
                    .println("| " + row[0] + " | " + row[1] + " | " + row[2] + " | " + row[3] + " | " + row[4] + " |");
        }
        System.out.println();
        System.out.print("Insert a word: ");
        String guess = new Scanner(System.in).nextLine();
        if (guess.length() != 5) {
            System.out.println("Word must be exactly 5 letters long!");
            showBoard();
            return;
        }

        if (guess.equals(word.word)) {
            System.out.println("-------------------------------------");
            System.out.println("Congratulations! You won");
            Database.insertHistory(Lobby.username,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), "win", word.word);
            System.exit(0);
        }

        String[] guessSplit = guess.split("");
        String[] wordSplit = word.word.split("");
        for (int i = 0; i < 5; i++) {
            String guessChar = guessSplit[i];
            if (guessChar.equals(wordSplit[i])) {
                board.get(nextRow)[i] = guessChar + " $";
            } else if (word.word.contains(guessChar)) {
                board.get(nextRow)[i] = guessChar + " #";
            } else {
                board.get(nextRow)[i] = guessChar + "  ";
            }
        }

        if (nextRow == 5) {
            System.out.println("-------------------------------------");
            System.out.println("womp womp, you lost...");
            Database.insertHistory(Lobby.username,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), "loss", word.word);
            System.exit(0);
        } else {
            nextRow++;
            showBoard();
        }
    }
}
