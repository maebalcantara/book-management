package com.example.librarymanagement;

import classes.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import source.DataSource;

import java.io.IOException;

public class AddFavorite {
    @FXML
    protected Label lblBookID;

    @FXML
    protected TextField txtPage;

    @FXML
    protected TextArea txtText;

    @FXML
    protected Button btnAdd;

    @FXML
    protected void btnAddClick() throws IOException {
        if (txtPage.getText().isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText(null);
            error.setContentText("Page shouldn't be empty.");
            error.showAndWait();
        } else if (txtText.getText().isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText(null);
            error.setContentText("Text shouldn't be empty.");
            error.showAndWait();
        } else {
            if (!DataSource.getInstance().addFavorite(Integer.parseInt(lblBookID.getText()), Integer.parseInt(txtPage.getText()), txtText.getText())) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setHeaderText(null);
                success.setContentText("Favorite added.");
                success.showAndWait();
                btnBackClick();
            } else {
                System.out.println("Unsuccessful.");
            }
        }
    }

    @FXML
    protected void btnBackClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("bookInfo.fxml"));
        Parent root = fxmlLoader.load();
        BookInfo bookInfo = fxmlLoader.getController();
        Book book = DataSource.getInstance().returnBook(Integer.parseInt(lblBookID.getText()));
        bookInfo.setItems(book.getBook_name(), book.getBook_author(), book.getBook_img_path(), book.getFavorites(), Integer.parseInt(book.getBook_ID()), book.getBook_Rating() != null ? Integer.parseInt(book.getBook_Rating()) : 0, book.getBook_description());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        scene = btnAdd.getScene();
        Window window = scene.getWindow();
        stage = (Stage) window;
        stage.close();
    }

    public void setItem(int book_id) {
        lblBookID.setText(String.valueOf(book_id));
    }
}
