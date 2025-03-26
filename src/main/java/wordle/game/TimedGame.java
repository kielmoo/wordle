package wordle.game;

import wordle.Word;
import wordle.difficulty.Difficulty;

import java.util.Timer;
import java.util.TimerTask;

public class TimedGame extends NormalGame {
    public TimedGame(Word word, Difficulty difficulty) {
        super(word, difficulty);
    }

    @Override
    public void start() throws Exception {
        super.start();
        startTimer();
        System.out.println("Timer started.");
    }

    private void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time's up!");
                timer.cancel();
                end();
            }
        }, difficulty.getTimerSeconds() * 1000L);
    }
}
