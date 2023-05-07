package cn.edu.sustech.cs110.snake;

import cn.edu.sustech.cs110.snake.model.Game;
import cn.edu.sustech.cs110.snake.view.AdvancedStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Context.INSTANCE.currentGame(new Game(15, 15));
        new AdvancedStage("game.fxml")
                .withTitle("Snake")
                .shows();
    }
}
