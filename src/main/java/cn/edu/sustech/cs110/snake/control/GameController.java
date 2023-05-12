package cn.edu.sustech.cs110.snake.control;

import cn.edu.sustech.cs110.snake.Context;
import cn.edu.sustech.cs110.snake.enums.Direction;
import cn.edu.sustech.cs110.snake.events.*;
import cn.edu.sustech.cs110.snake.view.components.GameBoard;
import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

public class GameController implements Initializable {

    public Button playAgainButton;
    public Button quitButton;
    public Text score;
    @FXML
    private Parent root;

    @FXML
    private MenuItem menuPause;
    

    @FXML
    private Button btnPause;

    @FXML
    private Text textCurrentScore;

    @FXML
    private Text textTimeAlive;

    @FXML
    private GameBoard board;

    private static final long MOVE_DURATION = 500;

    @SuppressWarnings("AlibabaThreadPoolCreation")
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    @SuppressWarnings("java:S3077")
    volatile ScheduledFuture<?> gameDaemonTask;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scheduler.scheduleAtFixedRate(() -> {
            if (!Context.INSTANCE.currentGame().isPlaying()) {
                return;
            }
            // TODO: add some code here
        }, 0, 1000, TimeUnit.MILLISECONDS);

        setupDaemonScheduler();

        Platform.runLater(this::bindAccelerators);
        board.paint(Context.INSTANCE.currentGame());
    }

    private void bindAccelerators() {
        Scene scene = root.getScene();

        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.P), this::togglePause);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.W), this::turnUp);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.S), this::turnDown);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.A), this::turnLeft);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.D), this::turnRight);
    }

    public void togglePause() {
        // TODO: change the text in menu's pause item and button
        Context.INSTANCE.currentGame().setPlaying(!Context.INSTANCE.currentGame().isPlaying());
    }

    public void doRestart() {
        // TODO: add some code here
    }

    public void doRecover() {
        // TODO: add some code here
    }

    public void doSave() {
        // TODO: add some code here
    }

    public void doQuit() {
        // TODO: add some code here
    }

    public void toggleMusic() {
        // TODO: add some code here
    }

    public void turnLeft() {
        Context.INSTANCE.currentGame().getSnake().setDirection(Direction.LEFT);
    }

    public void turnUp() {Context.INSTANCE.currentGame().getSnake().setDirection(Direction.UP);}

    public void turnRight() {
        Context.INSTANCE.currentGame().getSnake().setDirection(Direction.RIGHT);
    }

    public void turnDown() {
        Context.INSTANCE.currentGame().getSnake().setDirection(Direction.DOWN);
    }

    public void changeDifficulty() {
        setupDaemonScheduler();
    }

    private void setupDaemonScheduler() {
        if (Objects.nonNull(gameDaemonTask)) {
            gameDaemonTask.cancel(true);
        }
        gameDaemonTask = scheduler.scheduleAtFixedRate(
                new GameDaemonTask(),
                0, MOVE_DURATION,
                TimeUnit.MILLISECONDS
        );
    }

    @Subscribe
    public void rerenderChanges(BoardRerenderEvent event) {
        board.repaint(event.getDiff());
    }

    @Subscribe
    public void beanAte(BeanAteEvent event) {
        // TODO: add some code here
    }

    @Subscribe
    public void GameOver(GameOverEvent event) {

        Context.INSTANCE.currentGame().setPlaying(false);

        // 更新最高分数和游戏分数
        int score = event.getScore();
        int highestScore = Context.INSTANCE.getHighestScore();
        if (score > highestScore) {
            Context.INSTANCE.setHighestScore(score);
            System.out.println("Congratulations! You set a new record: " + score);
        } else {
            System.out.println("Game over. Your score is: " + score);
        }

        // 打开 GameOver 界面
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/gameover.fxml"));
            Parent root = loader.load();
            //GameController controller = loader.getController();
            //controller.setScore(score);
            //controller.setHighestScore(highestScore);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // 用户选择重新开始游戏
            if (controllRestarter.is()) {
                Context.INSTANCE.currentGame().reset();
                Context.INSTANCE.currentGame().setPlaying(true);
            } else {
                // 用户选择退出游戏
                Platform.exit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: add some code here
        System.out.println("Game over");
    }

    public void playAgain() {
    }

    public void quit() {
    }
}
