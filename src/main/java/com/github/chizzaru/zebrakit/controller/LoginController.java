package com.github.chizzaru.zebrakit.controller;

import com.github.chizzaru.zebrakit.MmsConnect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.github.chizzaru.zebrakit.App.zebraFile;
import static com.github.chizzaru.zebrakit.App.showAlert;

public class LoginController implements Initializable {
    @FXML private Button btnCancel, btnLogin;
    @FXML private TextField txtUsername, txtPassword;
    @FXML private RadioButton rbDS, rbSMR;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup tg = new ToggleGroup();
        rbDS.setToggleGroup(tg);
        rbSMR.setToggleGroup(tg);
        rbDS.setSelected(true);


        INIConfiguration iniConfiguration = new INIConfiguration();
        try(FileReader fileReader = new FileReader(zebraFile)){
            iniConfiguration.read(fileReader);
        }catch (RuntimeException | ConfigurationException | IOException ignored){
        }


        btnCancel.setOnMouseClicked(mouseEvent -> {
            System.exit(0);
        });

        btnLogin.setOnAction(actionEvent -> {
            String user = txtUsername.getText();
            String pw = txtPassword.getText();
            String server = null;

            RadioButton rb = (RadioButton) tg.getSelectedToggle();
            String sbu = rb.getText();

            if(sbu.equals("DS")){
                server = iniConfiguration.getSection("ds")
                        .getProperty("host").toString();
            }else if(sbu.equals("SMR")){
                server = iniConfiguration.getSection("smr")
                        .getProperty("host").toString();
            }

            MmsConnect mmsConnect = new MmsConnect(server, user, pw);
            mmsConnect.mms_makeConnection();
            if(mmsConnect.error==null){
                System.out.println("Successfully Connected.");
            }else{
                showAlert(Alert.AlertType.WARNING,"Warning", mmsConnect.error);
            }
        });
    }
}
