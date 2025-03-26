package wordle.difficulty;

public class Difficulty {
    private final int maxAttempts;
    private final int timerSeconds;

    public Difficulty(int maxAttempts, int timerSeconds) {
        this.maxAttempts = maxAttempts;
        this.timerSeconds = timerSeconds;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getTimerSeconds() {
        return timerSeconds;
    }
}
