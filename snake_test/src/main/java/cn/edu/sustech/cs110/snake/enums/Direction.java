package cn.edu.sustech.cs110.snake.enums;

import cn.edu.sustech.cs110.snake.Context;

public enum Direction {

    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1)
    ;

    private final int xDiff;
    private final int yDiff;

    public static Direction random() {
        return Direction.values()[Context.INSTANCE.random().nextInt(Direction.values().length)];
    }

    public int getXDiff() {
        return this.xDiff;
    }

    public int getYDiff() {
        return this.yDiff;
    }

    private Direction(final int xDiff, final int yDiff) {
        this.xDiff = xDiff;
        this.yDiff = yDiff;
    }

    //新增
    public boolean isOpposite(Direction other) {
        return this == UP && other == DOWN ||
                this == DOWN && other == UP ||
                this == LEFT && other == RIGHT ||
                this == RIGHT && other == LEFT;
    }
}
