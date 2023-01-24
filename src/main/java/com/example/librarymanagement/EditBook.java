package com.example.librarymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import source.DataSource;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditBook implements Initializable {
    private final ObservableList<String> readingStatusChoices = FXCollections.observableArrayList("Reading", "Done Reading");
    private final ObservableList<Integer> ratingChoices = FXCollections.observableArrayList(5,4,3,2,1);

    //Text Fields
    @FXML
    protected TextField txtBookTitle;

    @FXML
    protected TextField txtBookAuthor;

    //Choice boxes
    @FXML
    protected ChoiceBox<String> choiceEditReadingStatus;

    @FXML
    protected ChoiceBox<Integer> choiceEditRating;

    //Radio buttons
    @FXML
    protected RadioButton rbEditBookAtHome;

    @FXML
    protected RadioButton rbEditBookLent;

    //Date Pickers
    @FXML
    protected DatePicker dateStarted;

    @FXML
    protected DatePicker dateEnded;

    //Buttons
    @FXML
    protected Button btnBack;

    //Event handlers
    @FXML
    protected void rbEditBookAtHomeClicked(){
        rbEditBookLent.setSelected(false);
    }

    @FXML
    protected void rbEditBookLentClicked(){
        rbEditBookAtHome.setSelected(false);
    }

    @FXML
    protected void btnUpdateClicked() throws IOException {
        Alert verify = new Alert(Alert.AlertType.CONFIRMATION);
        verify.setHeaderText(null);
        verify.setContentText("Are you sure you want to continue?");
        Optional<ButtonType> option = verify.showAndWait();
        if(ButtonType.OK.equals(option.get())){
            String date_started = dateStarted.getValue() != null ? dateStarted.getValue().toString() : null;
            String date_ended = dateEnded.getValue() != null ? dateEnded.getValue().toString() : null;
            String book_status = rbEditBookAtHome.isSelected() ? rbEditBookAtHome.getText() : rbEditBookLent.getText();
            String[] editable = {book_status, choiceEditReadingStatus.getValue(), date_started, date_ended, choiceEditRating.getValue() != null ? String.valueOf(choiceEditRating.getValue()) : null};
            if (!DataSource.getInstance().updateBook(txtBookTitle.getText(), txtBookAuthor.getText(), editable)) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setHeaderText(null);
                success.setContentText("Updating info successful!");
                success.showAndWait();
                btnBackClicked();
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText(null);
                error.setContentText("Updating info unsuccessful, kindly check your inputs.");
                error.showAndWait();
            }
        }
    }

    @FXML
    protected void btnClearClicked(){
        choiceEditReadingStatus.setValue(null);
        rbEditBookAtHome.setSelected(false);
        rbEditBookLent.setSelected(false);
        dateStarted.setValue(null);
        dateEnded.setValue(null);
        choiceEditRating.setValue(null);
    }

    @FXML
    protected void btnBackClicked() throws IOException {
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
    }
    //methods
    public void setItems(String bookTitle, String bookAuthor, String bookStatus, String reading_status, String date_started, String date_ended, String rating){
        txtBookTitle.setText(bookTitle);
        txtBookAuthor.setText(bookAuthor);
        if(bookStatus.equalsIgnoreCase("lent")){
            rbEditBookLent.setSelected(true);
            rbEditBookAtHome.setSelected(false);
        } else if (bookStatus.equalsIgnoreCase("at home")) {
            rbEditBookAtHome.setSelected(true);
            rbEditBookLent.setSelected(false);
        }
        choiceEditReadingStatus.setValue(reading_status);
        dateStarted.setValue(date_started != null ? LocalDate.parse(date_started) : null);
        dateEnded.setValue(date_ended != null ? LocalDate.parse(date_ended) : null);
        choiceEditRating.setValue(rating != null ? Integer.valueOf(rating) : null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceEditReadingStatus.setItems(readingStatusChoices);
        choiceEditRating.setItems(ratingChoices);
    }
}
