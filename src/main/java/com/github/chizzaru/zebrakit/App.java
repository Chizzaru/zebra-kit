package com.github.chizzaru.zebrakit;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class App extends Application {

    private final File zebraFile = new File("zebra.ini");
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



    public static void main(String[] args){
        launch(App.class);
    }
}
