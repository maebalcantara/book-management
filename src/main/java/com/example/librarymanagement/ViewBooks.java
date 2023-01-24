package com.example.librarymanagement;

import classes.Book;
import classes.Favorite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;
import source.DataSource;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewBooks implements Initializable {
    private ObservableList<Book> books = DataSource.getInstance().queryBooks();

    @FXML
    protected Button btnEdit;
    @FXML
    protected TableView<Book> booksTable;

    @FXML
    protected TableColumn<Book, String> colBookName;

    @FXML
    protected TableColumn<Book, String> colBookAuthor;

    @FXML
    protected TableColumn<Book, String> colBookStatus;

    @FXML
    protected TableColumn<Book, String> colReadingStatus;

    @FXML
    protected TableColumn<Book, String> colReadingDateStarted;

    @FXML
    protected TableColumn<Book, String> colReadingDateEnded;

    @FXML
    protected TableColumn<Book, String> colRating;
    
    //to-do
    @FXML
    protected void btnBookInfo(){
    	try {
    		String bookTitle = booksTable.getSelectionModel().getSelectedItem().getBook_name();
            String bookAuthor = booksTable.getSelectionModel().getSelectedItem().getBook_author();
            String bookImgPath = booksTable.getSelectionModel().getSelectedItem().getBook_img_path();

            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("bookInfo.fxml"));
            Parent root = fxmlLoader.load();
            BookInfo bookInfo = fxmlLoader.getController();
            for(Book b : books) {
                if(b.getBook_name().equalsIgnoreCase(bookTitle) && b.getBook_author().equalsIgnoreCase(bookAuthor)){
                    bookInfo.setItems(bookTitle, bookAuthor, bookImgPath, b.getFavorites(), Integer.parseInt(b.getBook_ID()));
                    break;
                }
            }
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            scene = btnEdit.getScene();
            Window window = scene.getWindow();
            stage = (Stage) window;
            stage.close();
            
    	} catch (IOException e) {
    		System.out.println("IOException occurred: " + e);
    	}
    }

    @FXML
    protected void btnEditClicked(){
    	try {
    		String bookTitle = booksTable.getSelectionModel().getSelectedItem().getBook_name();
            String bookAuthor = booksTable.getSelectionModel().getSelectedItem().getBook_author();
            String bookStatus = booksTable.getSelectionModel().getSelectedItem().getBook_status();
            String reading_status = booksTable.getSelectionModel().getSelectedItem().getBook_Reading_status() != null ? booksTable.getSelectionModel().getSelectedItem().getBook_Reading_status() : null;
            String reading_date_started = booksTable.getSelectionModel().getSelectedItem().getBook_Reading_date_started() != null ? booksTable.getSelectionModel().getSelectedItem().getBook_Reading_date_started() : null;
            String reading_date_ended = booksTable.getSelectionModel().getSelectedItem().getBook_Reading_date_ended() != null ? booksTable.getSelectionModel().getSelectedItem().getBook_Reading_date_ended() : null;
            String book_rating = booksTable.getSelectionModel().getSelectedItem().getBook_Rating() != null ? booksTable.getSelectionModel().getSelectedItem().getBook_Rating() : null;

            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("editBook.fxml"));
            Parent root = fxmlLoader.load();
            EditBook editBook = fxmlLoader.getController();
            editBook.setItems(bookTitle, bookAuthor, bookStatus, reading_status, reading_date_started, reading_date_ended, book_rating);

            //create a method for this? repetitive eh
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            scene = btnEdit.getScene();
            Window window = scene.getWindow();
            stage = (Stage) window;
            stage.close();
			
		} catch (NullPointerException e) {
			Alert warning = new Alert(AlertType.WARNING);
			warning.setHeaderText(null);
			warning.setTitle("NullPointerException Occurred");
			warning.setContentText("Please select an item from the table to edit.");
			warning.showAndWait();
		} catch (IOException e) {
			System.out.println("IOException occurred: " + e);
		}
        
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        booksTable.setItems(null);
        colBookName.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        colBookAuthor.setCellValueFactory(cellData -> cellData.getValue().bookAuthorProperty());
        colBookStatus.setCellValueFactory(cellData -> cellData.getValue().bookStatusProperty());
        colReadingStatus.setCellValueFactory(cellData -> cellData.getValue().bookReadingStatusProperty());
        colReadingDateStarted.setCellValueFactory(cellData -> cellData.getValue().bookReadingDateStartedStatusProperty());
        colReadingDateEnded.setCellValueFactory(cellData -> cellData.getValue().bookReadingDateEndedStatusProperty());
        colRating.setCellValueFactory(cellData -> cellData.getValue().bookRatingStatusProperty());

        booksTable.setItems(books);
    }

//    @FXML
//    protected Button btnClear;
//
//    @FXML
//    protected void btnClearClicked() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("addBook.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();
//
//        scene = btnClear.getScene();
//        Window window = scene.getWindow();
//        stage = (Stage) window;
//        stage.hide();
//    }

}

