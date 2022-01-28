package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.UserList;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * the controller of admin page
 */
public class AdminController implements Initializable {
    /**
     * application
     */
    private MyPhotosApp application;
    /**
     * user list
     */
    private ObservableList<String> users;

    /**
     * Button
     */
    @FXML
    private Button logoutBtn;
    /**
     * Label
     */
    @FXML
    private Label userCountLB;
    /**
     * TextField
     */
    @FXML
    private TextField userNameTF;
    /**
     * Button
     */
    @FXML
    private Button removeBtn;
    /**
     * ListView
     */
    @FXML
    private ListView<String> userListLV;
    /**
     * Label
     */
    @FXML
    private Label errTipLB;
    /**
     * Button
     */
    @FXML
    private Button addBtn;


    /**
     * setup
     * @param application MyPhotosApp
     * @param users the user list
     */
    public void setApp(MyPhotosApp application, UserList users){
        this.application = application;
        this.users = FXCollections.observableArrayList();
        this.users.addAll(users.getList());
        this.userCountLB.setText("Total "+ users.size() + " users.");
        this.userListLV.setItems(this.users);
        this.errTipLB.setText("");
    }

    /**
     * update the page
     * @param users the new user list
     */
    public void updateUserList(UserList users) {
        this.users.clear();
        this.users.addAll(users.getList());
        this.userListLV.setItems(this.users);
        this.userListLV.refresh();
        this.userCountLB.setText("Total "+ users.size() + " users.");
        this.errTipLB.setText("");
    }

    /**
     * handle add user action
     * @param event the event
     */
    @FXML
    void addUserHandler(ActionEvent event) {
        if (this.userNameTF.getText().equals("")){
            this.errTipLB.setText("Name is empty!");
            return;
        } else if(this.users.contains(this.userNameTF.getText())){
            this.errTipLB.setText(this.userNameTF.getText()+" is already in the list!");
            return;
        }
        this.application.addUserHandler(this, this.userNameTF.getText());
    }
    /**
     * handle logout action
     * @param event the event
     */
    @FXML
    void logoutHandler(ActionEvent event) {
        this.application.userLogoutHandler();
    }
    /**
     * handle remove user action
     * @param event the event
     */
    @FXML
    void removeUserHandler(ActionEvent event) {
        String nameDel = this.userListLV.getSelectionModel().getSelectedItem();
        this.application.removeUserHandler(this, nameDel);
    }


    /**
     * initialize
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int selectedNum = userListLV.getSelectionModel().getSelectedItems().size();
        if (selectedNum ==0){
            this.removeBtn.setDisable(true);
        }
        userListLV.getSelectionModel().selectedItemProperty().addListener((obs,old,now)->{
            this.removeBtn.setDisable(now==null);
        });

        this.userListLV.getItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
                userCountLB.setText("Total: "+users.size());
            }

        });
    }
}
