package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Photo;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * the photo page controller
 */
public class PhotoController implements Initializable {
    /**
     * application
     */
    private MyPhotosApp application;
    /**
     * album
     */
    private String albumName;
    /**
     * user
     */
    private String userName;
    /**
     * photos
     */
    private ObservableList<Photo> photos;
    /**
     * album names
     */
    private ObservableList<String> albums;
    /**
     * tableview column
     */
    private TableColumn<Photo, Image> imageCol;
    /**
     * tableview column
     */
    private TableColumn<Photo, String> titleCol;
    /**
     * tableview column
     */
    private TableColumn<Photo, String> timeCol;
    /**
     * tableview column
     */
    private TableColumn<Photo, String> tagCol;
    /**
     * options
     */
    private ObservableList<String> options = FXCollections.observableArrayList("location", "person");
    /**
     * options
     */
    private ObservableList<String> andOrOptions = FXCollections.observableArrayList("and", "or");

    /**
     * tableview
     */
    @FXML
    private TableView<Photo> photoTV;
    /**
     * combobox
     */
    @FXML
    private ComboBox<String> tagCB;

    /**
     * label
     */
    @FXML
    private Label tagLB;

    /**
     * button
     */
    @FXML
    private Button addOptionBtn;

    /**
     * button
     */
    @FXML
    private Button logoutBtn;

    /**
     * textfield
     */
    @FXML
    private TextField timeRangTF;

    /**
     * imageview
     */
    @FXML
    private ImageView photoView;

    /**
     * button
     */
    @FXML
    private Button addBtn;

    /**
     * combobox
     */
    @FXML
    private ComboBox<String> tag1CB;
    /**
     * combobox
     */
    @FXML
    private ComboBox<String> tag2CB;
    /**
     * button
     */
    @FXML
    private Button addTagBtn;
    /**
     * TextField
     */
    @FXML
    private TextField photoPathTF;
    /**
     * Label
     */
    @FXML
    private Label dateLB;
    /**
     * button
     */
    @FXML
    private Button rmTagBtn;
    /**
     * Label
     */
    @FXML
    private Label photoTitleLB;
    /**
     * button
     */
    @FXML
    private Button rmPhotoBtn;
    /**
     * Label
     */
    @FXML
    private Label errTipLB;
    /**
     * TextField
     */
    @FXML
    private TextField tagTF;
    /**
     * TextField
     */
    @FXML
    private TextField titleTF;
    /**
     * button
     */
    @FXML
    private Button changTitleBtn;
    /**
     * ComboBox
     */
    @FXML
    private ComboBox<String> andOrCB;
    /**
     * ComboBox
     */
    @FXML
    private ComboBox<String> albumCB;
    /**
     * TextField
     */
    @FXML
    private TextField optionTF;
    /**
     * TextField
     */
    @FXML
    private TextField tag1TF;
    /**
     * TextField
     */
    @FXML
    private TextField tag2TF;
    /**
     * button
     */
    @FXML
    private Button filterBtn;
    /**
     * button
     */
    @FXML
    private Button preBtn;
    /**
     * button
     */
    @FXML
    private Button nextBtn;
    /**
     * Label
     */
    @FXML
    private Label albumNameLB;
    /**
     * button
     */
    @FXML
    private Button moveToBtn;
    /**
     * button
     */
    @FXML
    private Button copyToBtn;


    /**
     * setup
     * @param application application
     * @param userPhotos photo list
     * @param albums album name's
     * @param albumName current album name
     * @param userName user name
     */
    public void setApp(MyPhotosApp application, List<Photo> userPhotos,List<String> albums,String albumName, String userName){
        this.application = application;
        this.userName = userName;
        this.photos = FXCollections.observableArrayList();
        this.photos.addAll(userPhotos);
        imageCol = new TableColumn<>("Photo");
        imageCol.setMinWidth(100);
        imageCol.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(50);
            imageview.setFitWidth(50);

            //Set up the Table
            TableCell<Photo, Image> cell = new TableCell<Photo, Image>() {
                public void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        imageview.setImage(item);
                    }
                }
            };
            // Attach the imageview to the cell
            cell.setGraphic(imageview);
            return cell;
        });
        imageCol.setCellValueFactory(new PropertyValueFactory<Photo, Image>("image"));
        titleCol = new TableColumn<>("Photo Title");
        titleCol.setMinWidth(100);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setCellFactory(TextFieldTableCell.<Photo>forTableColumn());

        timeCol = new TableColumn<>("Photo Time");
        timeCol.setMinWidth(200);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("dateStr"));
        timeCol.setCellFactory(TextFieldTableCell.<Photo>forTableColumn());


        tagCol= new TableColumn<>("Photo Tag");
        tagCol.setMinWidth(400);
        tagCol.setCellValueFactory(new PropertyValueFactory<>("tags"));
        tagCol.setCellFactory(TextFieldTableCell.<Photo>forTableColumn());

        this.photoTV.setItems(photos);
        this.photoTV.getColumns().addAll(imageCol, titleCol, timeCol, tagCol);
        this.albumNameLB.setText(albumName);
        this.albumName = albumName;
        if(this.photos.size() > 0)
            this.photoTV.getSelectionModel().select(0);
        tagCB.setItems(options);
        tag1CB.setItems(options);
        tag2CB.setItems(options);
        tagCB.getSelectionModel().select(0);
        tag1CB.getSelectionModel().select(0);
        tag2CB.getSelectionModel().select(0);
        andOrCB.setItems(andOrOptions);
        andOrCB.getSelectionModel().select(0);

        this.albums = FXCollections.observableArrayList();
        this.albums.addAll(albums);
        this.albumCB.setItems(this.albums);
        this.albumCB.getSelectionModel().select(0);
    }

    /**
     * update view
     * @param ps the new photo list
     */
    public void updatePhotoList(List<Photo> ps) {
        ObservableList<Photo> temp = this.photoTV.getItems();
        for (Photo p: temp){

        }
        this.photos.clear();
        this.photos.addAll(ps);
        this.photoTV.setItems(this.photos);
        this.photoTV.refresh();
        this.errTipLB.setText("");
    }


    /**
     * handle add photo action
     * @param event the event
     */
    @FXML
    void addPhotoHandler(ActionEvent event) {
        if(this.photoPathTF.getText().equals("")){
            this.errTipLB.setText("Name is empty!");
            return;
        } else {
            for (Photo p : photos){
                if (p.getFilePath().equals(this.photoPathTF.getText())) {
                    this.errTipLB.setText("Photo is already in the list!");
                    return;
                }
            }

            File f = new File(this.photoPathTF.getText());
            if (f.exists()) {
                this.application.addPhotoHandler(this, this.albumName, this.photoPathTF.getText(), this.titleTF.getText(), this.userName);
            } else {
                this.errTipLB.setText("photo path error! please use absolute path.");
                return;
            }
        }
    }
    /**
     * handle remove photo action
     * @param event the event
     */
    @FXML
    void rmPhotoHandler(ActionEvent event) {
        Photo photo = this.photoTV.getSelectionModel().getSelectedItem();
        this.application.rmPhotoHandler(this, this.albumName, photo.getFilePath(), this.userName);
    }

    /**
     * handle change photo title action
     * @param event the event
     */
    @FXML
    void changeBtnHandler(ActionEvent event) {
        Photo photo = this.photoTV.getSelectionModel().getSelectedItem();
        if (this.titleTF.getText().equals(photo.getTitle())){
            this.errTipLB.setText("Same title");
            return;
        }
        this.application.changeTitleHandler(this, this.albumName, photo.getFilePath(), this.titleTF.getText(), this.userName);
    }
    /**
     * handle add tag to photo action
     * @param event the event
     */
    @FXML
    void addTagHandler(ActionEvent event) {
        Photo photo = this.photoTV.getSelectionModel().getSelectedItem();
        if(tagTF.getText().equals("")) {
            this.errTipLB.setText("Empty tag.");
            return;
        }
        this.application.addTagHandler(this, this.albumName, photo.getFilePath(), this.tagCB.getSelectionModel().getSelectedItem(),this.tagTF.getText(), this.userName);

    }
    /**
     * handle add option action
     * @param event the event
     */
    @FXML
    void addOptionHandler(ActionEvent event) {
        if(this.optionTF.getText().equals("")) {
            this.errTipLB.setText("Empty option.");
            return;
        }
        this.options.add(this.optionTF.getText());
    }
    /**
     * handle search photo action
     * @param event the event
     */
    @FXML
    void fliterPhotoHandler(ActionEvent event) {
        Date date1=null, date2 = null;
        String kV1="", kV2="";
        if(!this.timeRangTF.getText().equals("")) {
            String a[] = this.timeRangTF.getText().split(" ");
            if(a[1].equals("to")) {
                DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date1 = format1.parse(a[0]);
                    date2 = format1.parse(a[2]);
                } catch (ParseException e) {
                    this.errTipLB.setText("Error time format.");
                }
            } else {
                this.errTipLB.setText("Error time format.");
            }
        }
        if (!this.tag1TF.getText().equals("")) {
            kV1 += this.tag1CB.getSelectionModel().getSelectedItem() + "7788"+this.tag1TF.getText();
        }
        if (!this.tag2TF.getText().equals("")) {
            kV2 += this.tag2CB.getSelectionModel().getSelectedItem() + "7788"+this.tag2TF.getText();
        }

        this.application.filterTagHandler(this, this.albumName, date1,date2,kV1,kV2,this.andOrCB.getSelectionModel().getSelectedItem());
    }
    /**
     * handle remove tag from photo action
     * @param event the event
     */
    @FXML
    void rmTagHandler(ActionEvent event) {
        Photo photo = this.photoTV.getSelectionModel().getSelectedItem();
        if(tagTF.getText().equals("")) {
            this.errTipLB.setText("Empty tag.");
            return;
        }
        this.application.rmTagHandler(this, this.albumName, photo.getFilePath(), this.tagCB.getSelectionModel().getSelectedItem(),this.tagTF.getText(), this.userName);
    }

    /**
     * look the pre photo
     * @param event the event
     */
    @FXML
    void prePhotoHandler(ActionEvent event) {
        Photo photo = this.photoTV.getSelectionModel().getSelectedItem();
        this.photoTV.getSelectionModel().select(photos.indexOf(photo) - 1);
    }
    /**
     * look the next photo
     * @param event the event
     */
    @FXML
    void nextPhotoHandler(ActionEvent event) {
        Photo photo = this.photoTV.getSelectionModel().getSelectedItem();
        this.photoTV.getSelectionModel().select(photos.indexOf(photo) + 1);
    }
    /**
     * move photo to another album
     * @param event the event
     */
    @FXML
    void movePhotoHandler(ActionEvent event) {
        if (this.albumCB.getSelectionModel().getSelectedItem().equals(this.albumName)) {
            this.errTipLB.setText("Can not move to own album");
            return;
        }
        Photo photo = this.photoTV.getSelectionModel().getSelectedItem();
        this.application.movePhotoHandler(this, this.albumName, this.albumCB.getSelectionModel().getSelectedItem(), photo.getFilePath(), this.userName);
    }
    /**
     * copy photo to another album
     * @param event the event
     */
    @FXML
    void copyPhotoHandler(ActionEvent event) {
        if (this.albumCB.getSelectionModel().getSelectedItem().equals(this.albumName)) {
            this.errTipLB.setText("Can not move to own album");
            return;
        }
        Photo photo = this.photoTV.getSelectionModel().getSelectedItem();
        this.application.copyPhotoHandler(this, this.albumName, this.albumCB.getSelectionModel().getSelectedItem(), photo.getFilePath(), this.userName);
    }

    /**
     * back to the album list page
     * @param event the event
     */
    @FXML
    void backToAlbumsHandler(ActionEvent event) {
        this.application.backToAlbumsHandler(this.userName);
    }
    /**
     * logout
     * @param event the event
     */
    @FXML
    void logoutHandler(ActionEvent event) {
        this.application.userLogoutHandler();
    }

    /**
     * initialize
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int selectedNum = this.photoTV.getSelectionModel().getSelectedItems().size();
        if (selectedNum ==0){
            this.changTitleBtn.setDisable(true);
            this.rmPhotoBtn.setDisable(true);
            this.addTagBtn.setDisable(true);
            this.rmTagBtn.setDisable(true);
            this.preBtn.setDisable(true);
            this.nextBtn.setDisable(true);
            this.moveToBtn.setDisable(true);
            this.copyToBtn.setDisable(true);
        }
        this.photoTV.getSelectionModel().selectedItemProperty().addListener((obs,old,now)->{
            this.rmPhotoBtn.setDisable(now==null);
            this.changTitleBtn.setDisable(now==null);
            this.addTagBtn.setDisable(now==null);
            this.rmTagBtn.setDisable(now==null);
            this.preBtn.setDisable(now==null);
            this.nextBtn.setDisable(now==null);
            this.moveToBtn.setDisable(now==null);
            this.copyToBtn.setDisable(now==null);
            if(now != null) {
                photoTitleLB.setText(now.getTitle());
                photoView.setImage(now.getImage());
                dateLB.setText(now.getDateStr());
                tagLB.setText(now.getTags());
                if(photos.indexOf(now) == 0) {
                    this.preBtn.setDisable(true);
                } else if(this.photos.indexOf(now) == this.photos.size()) {
                    this.nextBtn.setDisable(true);
                }
            } else {
                photoTitleLB.setText("");
                photoView.setImage(null);
                dateLB.setText("");
                tagLB.setText("");
            }
        });
    }
}
