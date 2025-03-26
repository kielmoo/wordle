package wordle;

import wordle.difficulty.Difficulty;
import wordle.difficulty.HardDifficulty;
import wordle.difficulty.InsaneDifficulty;
import wordle.difficulty.NormalDifficulty;
import wordle.game.GameMode;
import wordle.game.NormalGame;
import wordle.game.TimedGame;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Lobby {
    public static String username;
    public static Word word;

    public static void init() throws Exception {

        Path wordsFilePath = Paths.get(Lobby.class.getResource("words.json").toURI());
        String content = new String(Files.readAllBytes(wordsFilePath));
        Map<String, String[]> words = new ObjectMapper().readValue(content, new TypeReference<Map<String, String[]>>() {
        });
        int i = new Random().nextInt(words.size());
        Entry<String, String[]> wordEntry = words.entrySet().stream().skip(i).findFirst().orElse(null);
        word = new Word(wordEntry.getKey(), wordEntry.getValue());

        System.out.println("-------------------------------------");
        System.out.print("what is your name: ");
        username = new Scanner(System.in).nextLine();

        showMainMenu();
    }

    public static void showMainMenu() throws Exception {
        System.out.println("-------------------------------------");
        System.out.println("Hi " + username + "! choose an option: ");
        System.out.println("- play");
        System.out.println("- history");
        String whattodo = new Scanner(System.in).nextLine();
        if (whattodo.equals("play")) {
            play();
        } else if (whattodo.equals("history")) {
            showHistory();
        }
    }

    public static void play() throws Exception {
        System.out.println("-------------------------------------");
        System.out.println("choose difficulty: normal, hard, insane.");
        String userres = new Scanner(System.in).nextLine();
        Difficulty dif = null;
        if (userres.equals("normal")) {
            dif = new NormalDifficulty();
        } else if (userres.equals("hard")) {
            dif = new HardDifficulty();
        } else if (userres.equals("insane")) {
            dif = new InsaneDifficulty();
        }

        System.out.println("-------------------------------------");
        System.out.println("Choose game mode: normal, timed.");
        userres = new Scanner(System.in).nextLine();
        GameMode gameMode = null;
        if (userres.equals("normal")) {
            gameMode = new NormalGame(word, dif);
        } else if (userres.equals("timed")) {
            gameMode = new TimedGame(word, dif);
        }

        gameMode.start();
    }

    public static void showHistory() throws Exception {
        System.out.println("-------------------------------------");
        for (List<String> data : Database.getUserHistory(username)) {
            System.out.println("- " + data.get(0) + " - " + data.get(1) + " - " + data.get(2));
        }

        showMainMenu();
    }
}
