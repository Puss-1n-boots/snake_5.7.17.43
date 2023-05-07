package cn.edu.sustech.cs110.snake.model;

import cn.edu.sustech.cs110.snake.enums.Direction;

public class Position {
    private int x;
    private int y;

    public Position toward(Direction direction) {
        return new Position(x + direction.getXDiff(), y + direction.getYDiff());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "Position(x=" + this.getX() + ", y=" + this.getY() + ")";
    }

    @Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Position)) return false;
        final Position other = (Position) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getX() != other.getX()) return false;
        if (this.getY() != other.getY()) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Position;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getX();
        result = result * PRIME + this.getY();
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
}
