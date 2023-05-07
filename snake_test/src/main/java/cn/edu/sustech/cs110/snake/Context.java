// Generated by delombok at Tue Apr 18 17:58:31 CST 2023
package cn.edu.sustech.cs110.snake;

import cn.edu.sustech.cs110.snake.model.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import java.security.SecureRandom;
import java.util.Random;

public final class Context {
    public static final Context INSTANCE = new Context();
    private final EventBus eventBus = new EventBus();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Random random = new SecureRandom();
    private Game currentGame;

    //新增
    private int score = 0;

    //新增
    private int highestScore = 0;

    @java.lang.SuppressWarnings("all")
    public EventBus eventBus() {
        return this.eventBus;
    }

    @java.lang.SuppressWarnings("all")
    public ObjectMapper mapper() {
        return this.mapper;
    }

    @java.lang.SuppressWarnings("all")
    public Random random() {
        return this.random;
    }

    @java.lang.SuppressWarnings("all")
    public Game currentGame() {
        return this.currentGame;
    }

    @java.lang.SuppressWarnings("all")
    private Context() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Context currentGame(final Game currentGame) {
        this.currentGame = currentGame;
        return this;
    }

    //新增
    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighestScore() {
        return this.highestScore;
    }

    public void setHighestScore(int score) {
        if (score > this.highestScore) {
            this.highestScore = score;
        }
    }

}
