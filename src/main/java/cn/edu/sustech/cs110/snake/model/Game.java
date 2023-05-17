package cn.edu.sustech.cs110.snake.model;

import cn.edu.sustech.cs110.snake.Context;
import cn.edu.sustech.cs110.snake.enums.Direction;
import cn.edu.sustech.cs110.snake.enums.GridState;
import cn.edu.sustech.cs110.snake.events.BoardRerenderEvent;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private final int row;
    private final int col;
    private final Snake snake;
    private Position bean;
    private int duration;
    private boolean playing;

    public Game(int row, int col) {
        this.row = row;
        this.col = col;
        this.snake = new Snake(Direction.random());
        this.snake.getBody().add(new Position(row / 2, col / 2));
        this.bean = new Position(Context.INSTANCE.random().nextInt(row), Context.INSTANCE.random().nextInt(col));
    }

    public void setBean(final Position bean) {
        this.bean = bean;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public void setPlaying(final boolean playing) {
        this.playing = playing;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public Snake getSnake() {
        return this.snake;
    }

    public Position getBean() {
        return this.bean;
    }

    public int getDuration() {
        return this.duration;
    }

    public boolean isPlaying() {
        return this.playing;
    }

    @Override
    public java.lang.String toString() {
        return "Game(row=" + this.getRow() + ", col=" + this.getCol() + ", snake=" + this.getSnake() + ", bean=" + this.getBean() + ", duration=" + this.getDuration() + ", playing=" + this.isPlaying() + ")";
    }

    //新增
    public void reset() {
        clearBoard();
        this.snake.getBody().clear();
        this.snake.getBody().add(new Position(row / 2, col / 2));
        this.bean = new Position(Context.INSTANCE.random().nextInt(row), Context.INSTANCE.random().nextInt(col));
        this.duration = 500;
        this.playing = true;
    }

    public void generateNewBean() {
        this.bean = new Position(Context.INSTANCE.random().nextInt(row), Context.INSTANCE.random().nextInt(col));
        if (this.snake.getBody().contains(this.bean)) {
            generateNewBean();
        }
    }

    public boolean canMoveInDirection(Direction dir) {
        return !dir.isOpposite(snake.getDirection());
    }

    public void clearBoard() {
        Map<Position, GridState> diffs = new HashMap<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Position pos = new Position(i, j);
                diffs.put(pos, GridState.EMPTY);
            }
        }
        Context.INSTANCE.eventBus().post(new BoardRerenderEvent(diffs));
    }

}
