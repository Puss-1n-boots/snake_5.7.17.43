package cn.edu.sustech.cs110.snake.control;

import cn.edu.sustech.cs110.snake.Context;
import cn.edu.sustech.cs110.snake.enums.GridState;
import cn.edu.sustech.cs110.snake.events.BeanAteEvent;
import cn.edu.sustech.cs110.snake.events.BoardRerenderEvent;
import cn.edu.sustech.cs110.snake.events.GameOverEvent;
import cn.edu.sustech.cs110.snake.model.Game;
import cn.edu.sustech.cs110.snake.model.Position;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameDaemonTask implements Runnable {

    @Override
    public void run() {
        Game game = Context.INSTANCE.currentGame();
        if (!game.isPlaying()) {
            return;
        }
        System.out.println(game);

        Position headFwd = game.getSnake().getBody().get(0).toward(game.getSnake().getDirection());

        Map<Position, GridState> diffs = new HashMap<>(3);

        //新增
        //键盘锁定
        if (!game.canMoveInDirection(game.getSnake().getDirection())) {
            // cannot move in current direction, use the previous direction
            game.getSnake().setDirection(game.getSnake().getDirection());
            headFwd = game.getSnake().getBody().get(0).toward(game.getSnake().getDirection());
        } else {
            game.getSnake().setDirection(game.getSnake().getDirection());
        }

        // Check if snake hits the wall
        if (headFwd.getX() < 0 || headFwd.getX() >= game.getRow() || headFwd.getY() < 0 || headFwd.getY() >= game.getCol()) {
            game.setPlaying(false);
            Platform.runLater(() -> Context.INSTANCE.eventBus().post(new GameOverEvent(this,game.getDuration())));
            return;
        }

        // Check if snake hits itself
        if (game.getSnake().getBody().stream().skip(1).anyMatch(headFwd::equals)) {
            game.setPlaying(false);
            Platform.runLater(() -> Context.INSTANCE.eventBus().post(new GameOverEvent(this,game.getDuration())));
            return;
        }

        // TODO: manage the `diffs` map, you should add the correct changes into it
        game.getSnake().getBody().add(0, headFwd);
        diffs.put(headFwd, GridState.SNAKE_ON);
        Context.INSTANCE.eventBus().post(new BoardRerenderEvent(diffs));
    }
}
