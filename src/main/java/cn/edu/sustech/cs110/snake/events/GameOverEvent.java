package cn.edu.sustech.cs110.snake.events;

public class GameOverEvent {

    private final Object source;
    private final int score;

    public GameOverEvent(Object source, int score) {
        this.source = source;
        this.score = score;
    }

    public Object getSource() {
        return source;
    }

    public int getScore() {
        return score;
    }
}
