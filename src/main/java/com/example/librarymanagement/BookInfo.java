package com.example.librarymanagement;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.Book;
import classes.Favorite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import source.DataSource;

public class BookInfo {
    private int bookID;
    private ObservableList<Book> books = DataSource.getInstance().queryBooks();

    protected FileChooser fc = new FileChooser();
    @FXML
    protected TableView<Favorite> tbl_FavoriteParts;

    //TableColumn<Person, Number> ageColumn = new TableColumn<Person, Number>("Age");
    @FXML
    protected TableColumn<Favorite, Integer> colPage;

    @FXML
    protected TableColumn<Favorite, String> colText;

    @FXML
    protected Label lblBookTitle;
    @FXML
    protected Label lblAuthor;

    @FXML
    protected Label lblRating;

    @FXML
    protected Label lblBookDesc; //add to set items
    @FXML
    protected ImageView bookImg;

    @FXML
    protected Button btnBack;

    @FXML
    protected void btnAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("addFavorite.fxml"));
            Parent root = fxmlLoader.load();
            AddFavorite addFave = fxmlLoader.getController();
            addFave.setItem(this.bookID);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            scene = btnBack.getScene();
            Window window = scene.getWindow();
            stage = (Stage) window;
            stage.close();
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e);
        }
    }

    @FXML
    protected void btnBackOnClick() {
        try {
            //generic: fxml file, Button,
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("viewBooks.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            scene = btnBack.getScene();
            Window window = scene.getWindow();
            stage = (Stage) window;
            stage.close();
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e);
        }
    }

    //	@FXML
//	public void btnUploadClicked() throws IOException {
//		FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("home.fxml"));
//        Parent root = fxmlLoader.load();
//
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        
//		File selectedFile = fc.showOpenDialog(stage);
//
//		bookImg.setImage(new Image(selectedFile.toString()));
//	}
    public void setItems(String bookTitle, String bookAuthor, String bookImgPath, ObservableList<Favorite> faves, int bookID, int rating, String desc) {
        try {
            this.bookID = bookID;
            lblBookTitle.setText(bookTitle);
            lblAuthor.setText(bookAuthor);
            lblRating.setText(String.valueOf(rating));
            lblBookDesc.setText(desc);
            colPage.setCellValueFactory(cellData -> cellData.getValue().fave_page_property().asObject());
            colText.setCellValueFactory(cellData -> cellData.getValue().fave_text_property());
            tbl_FavoriteParts.setItems(faves);

            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("bookInfo.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

//			File selectedFile = fc.showOpenDialog(stage); ggamitin to kapag mag add ng new book, saving for later

            bookImg.setImage(new Image(bookImgPath));

        } catch (IOException e) {
            System.out.println("IOException occurred: " + e);
        }
    }
}
