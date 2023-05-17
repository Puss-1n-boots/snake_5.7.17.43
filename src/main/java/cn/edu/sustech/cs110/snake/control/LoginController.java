package cn.edu.sustech.cs110.snake.control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import cn.edu.sustech.cs110.snake.Context;
import cn.edu.sustech.cs110.snake.model.Game;
import cn.edu.sustech.cs110.snake.view.AdvancedStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static javafx.application.Application.launch;


public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public Button LoginInButton;

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // 处理登录逻辑
        if (checkUserExist(username)) {
            if (checkPasswordCorrect(username, password)) {
                System.out.println("登录成功");

                ((Stage)LoginInButton.getScene().getWindow()).close();
                Context.INSTANCE.currentGame(new Game(15, 15));
                new AdvancedStage("Start.fxml")
                        .withTitle("Start")
                        .shows();

            } else {
                System.out.println("密码错误");


            }
        } else {
            createUser(username, password);
            System.out.println("创建用户成功");


        }
    }

    // 检查用户是否存在
    private boolean checkUserExist(String username) {
        File file = new File("users.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields.length >= 1 && fields[0].equals(username)) {
                    return true;
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 检查密码是否正确
    private boolean checkPasswordCorrect(String username, String password) {
        File file = new File("users.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields[0].equals(username) && fields[1].equals(password)) {
                    return true;
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 创建新用户
    private void createUser(String username, String password) {
        try {
            FileWriter writer = new FileWriter("users.txt", true);
            writer.write(username + "," + password + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
