package cn.edu.sustech.cs110.snake.control;

import cn.edu.sustech.cs110.snake.Context;
import cn.edu.sustech.cs110.snake.enums.Direction;
import cn.edu.sustech.cs110.snake.enums.GridState;
import cn.edu.sustech.cs110.snake.events.*;
import cn.edu.sustech.cs110.snake.model.Game;
import cn.edu.sustech.cs110.snake.model.Position;
import cn.edu.sustech.cs110.snake.view.AdvancedStage;
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

import static cn.edu.sustech.cs110.snake.view.components.GameBoard.SNAKE_COLOR;

public class GameController implements Initializable {

    public Button playAgainButton;
    public Button quitButton;
    public Text score;
    public long gameStartTime;
    public Button registerButton;
    public Button loginButton;
    public Button set;
    public Button playButton;
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

        if(!Context.INSTANCE.currentGame().isPlaying())
            btnPause.setText("Pause");
        else
            btnPause.setText("Play");
        gameStartTime = System.currentTimeMillis();

        // TODO: change the text in menu's pause item and button
        Context.INSTANCE.currentGame().setPlaying(!Context.INSTANCE.currentGame().isPlaying());
    }

    public void doRestart() {

        Context.INSTANCE.currentGame().reset();
        Context.INSTANCE.currentGame().setPlaying(false);
        Game game = Context.INSTANCE.currentGame();
        Context.INSTANCE.currentGame().getSnake().getBody().forEach(position -> board.grids[position.getX()][position.getY()].setFill(SNAKE_COLOR));
        game.generateNewBean();
        System.out.print("game.generateNewBean()");
        Map<Position, GridState> diffs = new HashMap<>();
        diffs.put(game.getBean(), GridState.BEAN_ON);
        Context.INSTANCE.eventBus().post(new BoardRerenderEvent(diffs));

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
        updateGameTime();
        board.repaint(event.getDiff());
    }

    @Subscribe
    public void beanAte(BeanAteEvent event) {

        Game game = Context.INSTANCE.currentGame();
        //增加分数
        int score = Context.INSTANCE.getScore();
        Context.INSTANCE.setScore(score + 10);
        textCurrentScore.setText("Current score: " + Context.INSTANCE.getScore());

        // 生成新豆子
        game.generateNewBean();
        System.out.print("game.generateNewBean()");
        Map<Position, GridState> diffs = new HashMap<>();
        diffs.put(game.getBean(), GridState.BEAN_ON);
        Context.INSTANCE.eventBus().post(new BoardRerenderEvent(diffs));

        // TODO: add some code here
    }

    @Subscribe
    public void GameOver(GameOverEvent event) {

        Context.INSTANCE.currentGame().setPlaying(false);

        // 更新最高分数和游戏分数
        int score = Context.INSTANCE.getScore();
        int highestScore = Context.INSTANCE.getHighestScore();
        if (score > highestScore) {
            Context.INSTANCE.setHighestScore(score);
            System.out.println("Congratulations! You set a new record: " + score);
        } else {
            System.out.println("Game over. Your score is: " + score);
        }

        // 打开 GameOver 界面（无效）

        /*new AdvancedStage("gameover.fxml")
                .withTitle("SnakeEnd")
                .shows();*/

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("..\\view/gameover.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println(getClass().getResource("..\\view/gameover.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: add some code here
        System.out.println(getClass().getResource("..\\view/gameover.fxml"));
        System.out.println("Game over");
    }

    public void playAgain() {
        Context.INSTANCE.currentGame().reset();
        Context.INSTANCE.currentGame().setPlaying(true);
    }

    public void quit() {
        Platform.exit();
    }

    //更新时间
    public void updateGameTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - gameStartTime;
        long seconds = elapsedTime / 1000;
        textTimeAlive.setText("Time alive: " + seconds + "s");
    }

    public void startPlay(){
        /*Context.INSTANCE.currentGame(new Game(15, 15));
        new AdvancedStage("game.fxml")
                .withTitle("Snake")
                .shows();*/
    }

}
