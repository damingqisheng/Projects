package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Album;
import model.UserAlbums;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * the controller of albums
 */
public class AlbumsController implements Initializable {
    /**
     * application
     */
    private MyPhotosApp application;
    /**
     * user name
     */
    private String userName;
    /**
     * albums
     */
    private ObservableList<Album> albums;
    /**
     * TableColumn
     */
    private TableColumn<Album, String> nameCol;
    /**
     * TableColumn
     */
    private TableColumn<Album, String> numCol;
    /**
     * TableColumn
     */
    private TableColumn<Album, String> timeCol;

    /**
     * TableView
     */
    @FXML
    private TableView<Album> albumTV;
    /**
     * Label
     */
    @FXML
    private Label userNameLB;
    /**
     * TextField
     */
    @FXML
    private TextField albumNameTF;
    /**
     * Button
     */
    @FXML
    private Button removeBtn;
    /**
     * Button
     */
    @FXML
    private Button openBtn;
    /**
     * Button
     */
    @FXML
    private Button renameBtn;
    /**
     * Label
     */
    @FXML
    private Label errTipLB;

    /**
     * setup
     * @param application MyphotosApp
     * @param userAlbums the album list
     * @param name the user name
     */
    public void setApp(MyPhotosApp application, UserAlbums userAlbums, String name){
        this.application = application;
        this.userName = name;
        this.albums = FXCollections.observableArrayList();
        this.albums.addAll(userAlbums.getAlbums());
        nameCol = new TableColumn<>("Album Name");
        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.<Album>forTableColumn());


        numCol = new TableColumn<>("Photo Num");
        numCol.setMinWidth(100);
        numCol.setCellValueFactory(new PropertyValueFactory<>("numStr"));
        numCol.setCellFactory(TextFieldTableCell.<Album>forTableColumn());

        timeCol = new TableColumn<>("Time Line");
        timeCol.setMinWidth(400);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("dateRange"));
        numCol.setCellFactory(TextFieldTableCell.<Album>forTableColumn());
        this.albumTV.setItems(albums);
        this.albumTV.getColumns().addAll(nameCol, numCol, timeCol);
        this.userNameLB.setText(name +  " has " + this.albums.size()+" albums.");
    }

    /**
     * update albums
     * @param userAlbums the albums
     */
    public void updateAlbumList(UserAlbums userAlbums) {
        this.albums.clear();
        this.albums.addAll(userAlbums.getAlbums());
        this.albumTV.setItems(this.albums);
        this.albumTV.refresh();
        this.userNameLB.setText(this.userName +  " has " + this.albums.size()+" albums.");
        this.errTipLB.setText("");
    }


    /**
     * handle create album action
     * @param event the event
     */
    @FXML
    void createAlbumHandler(ActionEvent event) {
        if(this.albumNameTF.getText().equals("")){
            this.errTipLB.setText("Name is empty!");
            return;
        } else {
            for (Album a : this.albums){
                if (a.getName().equals(this.albumNameTF.getText())){
                    this.errTipLB.setText(this.albumNameTF.getText()+" is already in the list!");
                    return;
                }
            }
            this.application.addAlbumHandler(this,this.albumNameTF.getText(), this.userName);
        }
    }
    /**
     * handle rename album action
     * @param event the event
     */
    @FXML
    void renameAlbumHandler(ActionEvent event) {
        Album albumRename = this.albumTV.getSelectionModel().getSelectedItem();
        if (albumRename.getName().equals("stock")){
            this.errTipLB.setText("can not rename stock!");
            return;
        }
        if(this.albumNameTF.getText().equals("")){
            this.errTipLB.setText("Name is empty!");
            return;
        } else {
            for (Album a : this.albums){
                if (a.getName().equals(this.albumNameTF.getText())){
                    this.errTipLB.setText(this.albumNameTF.getText()+" is already in the list!");
                    return;
                }
            }
            this.application.renameAlbumHandler(this,albumRename.getName(),this.albumNameTF.getText(), this.userName);
        }
    }
    /**
     * handle remove album action
     * @param event the event
     */
    @FXML
    void removeAlbumHandler(ActionEvent event) {
        Album albumDel = this.albumTV.getSelectionModel().getSelectedItem();
        if (albumDel.getName().equals("stock")){
            this.errTipLB.setText("can not remove stock!");
            return;
        }
        this.application.removeAlbumHandler(this, albumDel.getName(), this.userName);
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
     * handle open album action
     * @param event the event
     */
    @FXML
    void openAlbumHandler(ActionEvent event) {
        Album albumDel = this.albumTV.getSelectionModel().getSelectedItem();
        if(albumDel != null)
            this.application.openAlbumHandler(albumDel.getName(), this.userName);
        else
            this.errTipLB.setText("no album is selected.");
    }
    /**
     * initialize
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int selectedNum = this.albumTV.getSelectionModel().getSelectedItems().size();
        if (selectedNum ==0){
            this.renameBtn.setDisable(true);
            this.removeBtn.setDisable(true);
            this.openBtn.setDisable(true);
        }
        this.albumTV.getSelectionModel().selectedItemProperty().addListener((obs,old,now)->{
            this.renameBtn.setDisable(now==null);
            this.removeBtn.setDisable(now==null);
            this.openBtn.setDisable(now==null);
        });
    }
}
