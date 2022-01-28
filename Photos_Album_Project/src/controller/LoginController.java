package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * the controller of login page
 */
public class LoginController implements Initializable {
    /**
     * application
     */
    private MyPhotosApp application;

    /**
     * TextField
     */
    @FXML
    private TextField userNameTF;
    /**
     * Label
     */
    @FXML
    private Label errTipLB;

    /**
     * setup
     * @param application MyPhotosApp
     */
    public void setApp(MyPhotosApp application){
        this.application = application;
    }

    /**
     * handle login action
     * @param event the event
     */
    @FXML
    void userLoginHandler(ActionEvent event) {
        this.application.userLoginHandler(this, userNameTF.getText());
    }

    /**
     * set the error tip
     * @param err the error message
     */
    public void setErrTip(String err){
        this.errTipLB.setText(err);
    }

    /**
     * initialize
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
