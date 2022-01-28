package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * the main controller
 */
public class MyPhotosApp extends Application {
    /**
     * stage
     */
    private Stage stage;
    /**
     * the list of users
     */
    private UserList userList;
    /**
     * the album list
     */
    private UserAlbums userAlbums;
    /**
     * the list of photos
     */
    private List<Photo> userPhotos;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.setTitle("MyPhotos");
        stage.setMinWidth(335);
        stage.setMinHeight(379);
        loadUserList();
        goto_Login();
        userPhotos = new ArrayList<Photo>();
        stage.show();
    }

    /**
     * load user list
     */
    private void loadUserList(){
        File userFile = new File("./userList");
        if (userFile.exists() && userFile.isFile()) {
            try {
                userList = Utils.readObject(userFile, UserList.class);
            } catch (IllegalArgumentException e) {
                return ;
            }
        } else {
            try {
                userFile.createNewFile();
                userList = new UserList();
                userList.addUser("stock");
                Utils.writeObject(userFile, userList);

                userAlbums = new UserAlbums();
                Album a = new Album("stock");
                File f = new File(".");
                String path1 = f.getAbsolutePath();
                String absolutePath = path1.substring(0, path1.length() - 1);
                a.addPhoto(new Photo("stockPng/p1.png", "p1"));
                a.addPhoto(new Photo("stockPng/p2.png", "p2"));
                a.addPhoto(new Photo("stockPng/p3.png","p3"));
                a.addPhoto(new Photo("stockPng/p4.png","p4"));
                a.addPhoto(new Photo("stockPng/p5.png","p5"));
                userAlbums.addAlbum(a);
                File albumsFile = new File("./stock");
                Utils.writeObject(albumsFile, userAlbums);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (userList == null)
            System.out.println("Load users failed...");
    }

    /**
     * load albums of a user
     * @param userName the user name
     */
    private void loadUserAlbums(String userName){
        File userFile = new File("./"+userName);
        if (userFile.exists() && userFile.isFile()) {
            try {
                userAlbums = Utils.readObject(userFile, UserAlbums.class);
            } catch (IllegalArgumentException e) {
                return ;
            }
        } else {
            try {
                userFile.createNewFile();
                userAlbums = new UserAlbums();
                Utils.writeObject(userFile, userAlbums);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (userAlbums == null)
            System.out.println("Load users albums failed...");
    }

    /**
     * load photos in an album
     * @param albumName the name of the album
     */
    private void loadUserPhotos(String albumName){
        this.userPhotos = this.userAlbums.getPhotos(albumName);
    }

    /**
     * transmission to login page
     */
    private void goto_Login(){
        try {
            LoginController loginController = (LoginController) replaceSceneContent("../view/login.fxml");
            loginController.setApp(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * transmission to admin page
     */
    private void goto_Admin(){
        try {
            AdminController adminController = (AdminController) replaceSceneContent("../view/admin.fxml");
            adminController.setApp(this, userList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * transmission to albums page
     */
    private void goto_Albums(String name){
        try {
            AlbumsController albumsController = (AlbumsController) replaceSceneContent("../view/albums.fxml");
            userAlbums.updateDateRange();
            albumsController.setApp(this, userAlbums, name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * transmission to photos page
     */
    private void goto_Photos(String albumName, String userName){
        try {
            PhotoController albums = (PhotoController) replaceSceneContent("../view/photos.fxml");
            albums.setApp(this, userPhotos, this.userAlbums.getAlbumNames(), albumName, userName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * handler user login
     * @param loginController the controller of login page
     * @param userName the user name
     */
    public void userLoginHandler(LoginController loginController, String userName){
        if(userName.equals("admin")) {
            goto_Admin();
        } else if(this.userList.isInUserList(userName)){
            loadUserAlbums(userName);
            goto_Albums(userName);
        } else {
            loginController.setErrTip("No such user.");
        }
    }

    /**
     * handle add user action
     * @param adminController the controller of admin page
     * @param userName the user name
     */
    public void addUserHandler(AdminController adminController, String userName){
        this.userList.addUser(userName);
        Utils.writeObject(new File("./userList"), this.userList);
        adminController.updateUserList(this.userList);
    }

    /**
     * handle logout action
     */
    public void userLogoutHandler(){
        goto_Login();
    }
    /**
     * handle remove user action
     * @param adminController the controller of admin page
     * @param userName the user name
     */
    public void removeUserHandler(AdminController adminController, String userName){
        this.userList.removeUser(userName);
        Utils.writeObject(new File("./userList"), this.userList);
        adminController.updateUserList(this.userList);
    }

    /**
     * handle add album action
     * @param albumsController the controller of tha albums page
     * @param albumName the album name
     * @param userName the user name
     */
    public void addAlbumHandler(AlbumsController albumsController, String albumName, String userName){
        this.userAlbums.addAlbum(new Album(albumName));
        Utils.writeObject(new File("./"+userName), this.userAlbums);
        albumsController.updateAlbumList(this.userAlbums);
    }
    /**
     * handle remove album action
     * @param albumsController the controller of tha albums page
     * @param albumName the album name
     * @param userName the user name
     */
    public void removeAlbumHandler(AlbumsController albumsController, String albumName, String userName){
        this.userAlbums.removeAlbum(albumName);
        Utils.writeObject(new File("./"+userName), this.userAlbums);
        albumsController.updateAlbumList(this.userAlbums);
    }
    /**
     * handle change album name action
     * @param albumsController the controller of tha albums page
     * @param oldName the old name
     * @param newName the new name
     * @param userName the user name
     */
    public void renameAlbumHandler(AlbumsController albumsController, String oldName, String newName, String userName){
        this.userAlbums.changeName(oldName, newName);
        Utils.writeObject(new File("./"+userName), this.userAlbums);
        albumsController.updateAlbumList(this.userAlbums);
    }

    /**
     * open album
     * @param albumName the album name
     * @param userName the user name
     */
    public void openAlbumHandler(String albumName, String userName){
        loadUserPhotos(albumName);
        goto_Photos(albumName,userName);
    }

    /**
     * add photo
     * @param pc the photo page controller
     * @param albumName the album's name
     * @param photoPath the photo's path
     * @param title the photo's path
     * @param userName the user name
     */
    public void addPhotoHandler(PhotoController pc, String albumName, String photoPath, String title, String userName) {
        this.userAlbums.addPhoto(albumName, photoPath, title);
        Utils.writeObject(new File("./"+userName), this.userAlbums);
        loadUserPhotos(albumName);
        pc.updatePhotoList(this.userPhotos);
    }

    /**
     * remove photo
     * @param pc the photo page controller
     * @param albumName the album's name
     * @param photoPath the photo's path
     * @param userName the usr name
     */
    public void rmPhotoHandler(PhotoController pc, String albumName, String photoPath, String userName) {
        this.userAlbums.rmPhoto(albumName, photoPath);
        Utils.writeObject(new File("./"+userName), this.userAlbums);
        loadUserPhotos(albumName);
        pc.updatePhotoList(this.userPhotos);
    }

    /**
     * change title
     * @param pc the photo page controller
     * @param albumName the album's name
     * @param photoPath the photo's path
     * @param photoTitle the new title
     * @param userName the user name
     */
    public void changeTitleHandler(PhotoController pc, String albumName, String photoPath, String photoTitle, String userName) {
        this.userAlbums.changePhotoTitle(albumName, photoPath, photoTitle);
        Utils.writeObject(new File("./"+userName), this.userAlbums);
        loadUserPhotos(albumName);
        pc.updatePhotoList(this.userPhotos);
    }

    /**
     * add tag
     * @param pc the photo page controller
     * @param albumName tha album's name
     * @param photoPath the photo's path
     * @param key the tag's kay
     * @param value the tag's value
     * @param userName the user name
     */
    public void addTagHandler(PhotoController pc, String albumName, String photoPath, String key, String value, String userName) {
        this.userAlbums.addTagToPhoto(albumName,photoPath,key,value);
        Utils.writeObject(new File("./"+userName), this.userAlbums);
        loadUserPhotos(albumName);
        pc.updatePhotoList(this.userPhotos);
    }

    /**
     * seach photos
     * @param pc the photo page controller
     * @param albumName tha album's name
     * @param date1 the start date
     * @param date2 the end date
     * @param kV1 the tag1
     * @param kV2 the tag2
     * @param andOR 'and' or 'or'
     */
    public void filterTagHandler(PhotoController pc, String albumName, Date date1, Date date2, String kV1, String kV2, String andOR) {
        pc.updatePhotoList(this.userAlbums.filterTagHandler(albumName,date1, date2, kV1, kV2, andOR));
    }

    /**
     * handler remove tag action
     * @param pc the photo page controller
     * @param albumName tha album's name
     * @param photoPath the photo's path
     * @param key the tag's kay
     * @param value the tag's value
     * @param userName the user name
     */
    public void rmTagHandler(PhotoController pc, String albumName, String photoPath, String key, String value, String userName) {
        this.userAlbums.rmTagFromPhoto(albumName,photoPath,key,value);
        Utils.writeObject(new File("./"+userName), this.userAlbums);
        loadUserPhotos(albumName);
        pc.updatePhotoList(this.userPhotos);
    }

    /**
     * handle back to album page action
     * @param userName the username
     */
    public void backToAlbumsHandler(String userName) {
        loadUserAlbums(userName);
        goto_Albums(userName);
    }

    /**
     * handle copy photo to another album action
     * @param pc the photos page controller
     * @param fromAlbum from
     * @param toAlbum to
     * @param path the photo's path
     * @param userName the user name
     */
    public void copyPhotoHandler(PhotoController pc, String fromAlbum, String toAlbum, String path, String userName) {
        this.userAlbums.copyPhoto(fromAlbum, toAlbum, path);
        Utils.writeObject(new File("./"+userName), this.userAlbums);
        loadUserPhotos(fromAlbum);
        pc.updatePhotoList(this.userPhotos);
    }

    /**
     * handle move photo to another album action
     * @param pc the photos page controller
     * @param fromAlbum from
     * @param toAlbum to
     * @param path the photo's path
     * @param userName the user name
     */
    public void movePhotoHandler(PhotoController pc, String fromAlbum, String toAlbum, String path, String userName) {
        this.userAlbums.movePhoto(fromAlbum, toAlbum, path);
        Utils.writeObject(new File("./"+userName), this.userAlbums);
        loadUserPhotos(fromAlbum);
        pc.updatePhotoList(this.userPhotos);
    }

    /**
     * load page
     * @param fxml the page
     * @return the controller
     * @throws Exception page not found
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = MyPhotosApp.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MyPhotosApp.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, page.getPrefWidth(), page.getPrefHeight());
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    /**
     * the main function
     * @param args tha args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
