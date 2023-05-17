package cn.edu.sustech.cs110.snake;

import cn.edu.sustech.cs110.snake.model.Game;
import cn.edu.sustech.cs110.snake.view.AdvancedStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
/*
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        Scene scene = new Scene(root, 640, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }*/


    @Override
    public void start(Stage stage) {

        Context.INSTANCE.currentGame(new Game(15, 15));
        new AdvancedStage("login.fxml")
                .withTitle("Snake")
                .shows();
    }




    public static void main(String[] args) {
        launch(args);
    }

}
