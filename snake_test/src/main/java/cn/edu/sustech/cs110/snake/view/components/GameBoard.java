package cn.edu.sustech.cs110.snake.view.components;

import cn.edu.sustech.cs110.snake.enums.GridState;
import cn.edu.sustech.cs110.snake.model.Game;
import cn.edu.sustech.cs110.snake.model.Position;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class GameBoard extends GridPane {

    private static final Color SNAKE_COLOR = Color.ORANGE;

    private static final Color BEAN_COLOR = Color.RED;

    private static final Color BACKGROUND_COLOR = Color.LIGHTYELLOW;

    private Rectangle[][] grids;

    public void paint(Game game) {
        getChildren().removeAll();

        for (int i = 0; i < game.getCol(); i++) {
            ColumnConstraints ccs = new ColumnConstraints();
            ccs.setPercentWidth(100. / game.getCol());
            ccs.setHgrow(Priority.ALWAYS);
            ccs.setFillWidth(true);
            getColumnConstraints().add(ccs);
        }

        for (int i = 0; i < game.getRow(); i++) {
            RowConstraints rcs = new RowConstraints();
            rcs.setPercentHeight(100. / game.getRow());
            rcs.setVgrow(Priority.ALWAYS);
            rcs.setFillHeight(true);
            getRowConstraints().add(rcs);
        }

        grids = new Rectangle[game.getRow()][game.getCol()];

        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j] = new Rectangle(10, 10);
                grids[i][j].setFill(BACKGROUND_COLOR);
                StackPane wrapper = new StackPane();
                grids[i][j].widthProperty().bind(wrapper.widthProperty());
                grids[i][j].heightProperty().bind(wrapper.heightProperty());
                wrapper.getChildren().add(grids[i][j]);
                add(wrapper, j, i);
            }
        }
        grids[game.getBean().getX()][game.getBean().getY()].setFill(BEAN_COLOR);
        game.getSnake().getBody().forEach(pos -> grids[pos.getX()][pos.getY()].setFill(SNAKE_COLOR));
    }

    public void repaint(Map<Position, GridState> diff) {
        diff.forEach((pos, state) -> {
            Color color;
            switch (state) {
                case SNAKE_ON:
                    color = SNAKE_COLOR;
                    break;
                case BEAN_ON:
                    color = BEAN_COLOR;
                    break;
                default:
                    color = BACKGROUND_COLOR;
            }
            grids[pos.getX()][pos.getY()].setFill(color);
        });
    }
}
