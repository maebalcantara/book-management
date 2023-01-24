package com.example.librarymanagement;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import source.DataSource;

import java.io.File;
import java.io.IOException;

public class AddBookController {
    private static DataSource ds = new DataSource();
	protected FileChooser fc = new FileChooser();

    //Textboxes
    @FXML
    protected TextField txtBookTitle;

    @FXML
    protected TextField txtBookAuthor;
    
    @FXML
    protected TextField txtBookImagePath;

    //Labels
    @FXML
    protected Label lblForTesting;
    //Buttons
    @FXML
    protected void addBookSubmitButton() {
        addBook();
    }
    
    @FXML 
    protected void btnUploadImage() throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("home.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        
		File selectedFile = fc.showOpenDialog(stage); //to save sa database
		txtBookImagePath.setText(selectedFile.toString());
//		imgTest.setImage(new Image(selectedFile.toString()));
    }
    @FXML
    protected Button btnClear;


    //Radio Buttons
    @FXML
    protected RadioButton rbAddNewBookLent;

    @FXML
    protected RadioButton rbAddNewBookAtHome;

    //event handlers
    @FXML
    protected void rbAddNewBookAtHomeWhenClicked(){
        rbAddNewBookLent.setSelected(false);
    }
    @FXML
    protected void rbAddNewBookLentWhenClicked(){
        rbAddNewBookAtHome.setSelected(false);
    }

    //methods
    private void addBook(){
        String book_status = "";
        if(rbAddNewBookLent.isSelected()){
            book_status = rbAddNewBookLent.getText();
        } else if(rbAddNewBookAtHome.isSelected()){
            book_status = rbAddNewBookAtHome.getText();
        }

        if (txtBookTitle.getText().isEmpty()) {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setHeaderText(null);
            warning.setContentText("Required book title field is empty.");
            warning.show();
        } else if (txtBookAuthor.getText().isEmpty()) {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setHeaderText(null);
            warning.setContentText("Required book author field is empty.");
            warning.show();
        } else if (book_status.isEmpty()) {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setHeaderText(null);
            warning.setContentText("Required book status field is empty.");
            warning.show();
        } else if (txtBookImagePath.getText().isEmpty()) {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setHeaderText(null);
            warning.setContentText("Required book iamge path field is empty.");
            warning.show();
        } else {
        	//to-do: add sa db ung image path
            if(!DataSource.getInstance().addBook(txtBookTitle.getText(), txtBookAuthor.getText(), book_status, txtBookImagePath.getText())){
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setHeaderText(null);
                success.setTitle("Successful");
                success.setContentText("Book " + txtBookTitle.getText() + " added!");
                success.showAndWait();
                clearFields();
            } else {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setHeaderText(null);
                warning.setContentText("Book already exists!");
                warning.showAndWait();
                clearFields();
            }
        }
    }

    private void clearFields(){
        txtBookTitle.clear();
        txtBookAuthor.clear();
        rbAddNewBookLent.setSelected(false);
        rbAddNewBookAtHome.setSelected(false);
    }

    @FXML
    private void btnClearClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("viewBooks.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        scene = btnClear.getScene();
        Window window = scene.getWindow();
        stage = (Stage) window;
        stage.hide();

    }
}

class EmptyFieldsException extends Exception {
    EmptyFieldsException(String str) {
        super(str);
    }
}