package wordle.game;

public interface GameMode {
    void start() throws Exception;

    void end();

    void showBoard() throws Exception;
}
