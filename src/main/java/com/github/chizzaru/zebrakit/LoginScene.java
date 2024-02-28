package com.github.chizzaru.zebrakit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginScene implements SceneInterface{
    private final Stage stage;
    private final FXMLLoader fxmlLoader;

    public LoginScene(){
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
    }
    @Override
    public void load() throws Exception {
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }
}
