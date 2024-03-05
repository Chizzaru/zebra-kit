package com.github.chizzaru.zebrakit;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class App extends Application {

    public static final File zebraFile = new File("zebra.ini");
    @Override
    public void start(Stage stage){

        INIConfiguration iniConfiguration = new INIConfiguration();
        try(FileReader fileReader = new FileReader(zebraFile)){
            iniConfiguration.read(fileReader);
        }catch (RuntimeException | ConfigurationException | IOException ignored){
        }

        String class_name = iniConfiguration.getSection("init-class")
                .getProperty("class_name").toString();
        try{
            Class<?> initClass = Class.forName("com.github.chizzaru.zebrakit."+class_name);
            Constructor<?> constructor = initClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();
            openStage((SceneInterface) instance);

        }catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                InvocationTargetException ex){
            throw new RuntimeException(ex);
        }

    }

    public static void openStage(SceneInterface stage){
        try {
            stage.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void showAlert(Alert.AlertType type, String header, String error){
        Alert alert = new Alert(type);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(header);
        // Apply CSS to the alert dialog
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(App.class.getResource("style.css")).toExternalForm());
        alert.setContentText(error);
        alert.showAndWait();
    }



    public static void main(String[] args){
        launch(App.class);
    }
}
