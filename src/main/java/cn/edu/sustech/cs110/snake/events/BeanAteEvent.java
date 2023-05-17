package cn.edu.sustech.cs110.snake.events;

import cn.edu.sustech.cs110.snake.Context;
import cn.edu.sustech.cs110.snake.model.Game;

public class BeanAteEvent {
    private Object source;
    private int score;
    public BeanAteEvent(Object source, int score) {
        this.source = source;
        this.score = score;
    }

    public Object getSource() {
        return source;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
