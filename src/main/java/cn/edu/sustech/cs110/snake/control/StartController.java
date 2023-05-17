package cn.edu.sustech.cs110.snake.control;

import cn.edu.sustech.cs110.snake.Context;
import cn.edu.sustech.cs110.snake.model.Game;
import cn.edu.sustech.cs110.snake.view.AdvancedStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartController {

    @FXML
    public Button registerButton;
    @FXML
    public Button loginButton;
    @FXML
    public Button set;
    @FXML
    public Button playButton;

    public void startPlay() {
        ((Stage)playButton.getScene().getWindow()).close();
        Context.INSTANCE.currentGame(new Game(15, 15));
        new AdvancedStage("game.fxml")
                .withTitle("Snake")
                .shows();
    }
}
