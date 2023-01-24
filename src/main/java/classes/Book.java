package classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private final SimpleStringProperty book_name;
    private final SimpleStringProperty book_author;
    private final SimpleStringProperty book_status;
    private final SimpleStringProperty book_img_path; //to add sa constructor
    private SimpleStringProperty book_reading_status;
    private SimpleStringProperty book_reading_date_started;
    private SimpleStringProperty book_reading_date_ended;
    private SimpleStringProperty book_rating;
    private SimpleStringProperty book_ID;
    private SimpleStringProperty book_description;
    //to-do: create a list of favourites?
    private ObservableList<Favorite> favorites = FXCollections.observableArrayList();

    public Book(String book_name, String book_author, String book_status, String book_img_path, String book_description) {
        this.book_name = new SimpleStringProperty(book_name);
        this.book_author = new SimpleStringProperty(book_author);
        this.book_status = new SimpleStringProperty(book_status);
        this.book_img_path = new SimpleStringProperty(book_img_path);
        this.book_description = new SimpleStringProperty(book_description);
    }

    //setters
    public void setBook_reading_status(String book_reading_status) {
        this.book_reading_status = new SimpleStringProperty(book_reading_status);
    }

    public void setBook_reading_date_started(String book_reading_date_started) {
        this.book_reading_date_started = new SimpleStringProperty(book_reading_date_started);
    }

    public void setBook_reading_date_ended(String book_reading_date_ended) {
        this.book_reading_date_ended = new SimpleStringProperty(book_reading_date_ended);
    }

    public void setBook_rating(String book_rating) {
        this.book_rating = new SimpleStringProperty(book_rating);
    }

    public void setBook_ID(String book_ID) {
        this.book_ID = new SimpleStringProperty(book_ID);
    }

    //getters
    public String getBook_name() {
        return book_name.get();
    }

    public String getBook_author() {
        return book_author.get();
    }

    public String getBook_status() {
        return book_status.get();
    }

    public String getBook_img_path() {
        return book_img_path.get();
    }

    public String getBook_description() {
        return book_description.get();
    }

    public String getBook_Reading_status() {
        return book_reading_status != null ? book_reading_status.get() : null;
    }

    public String getBook_Reading_date_started() {
        return book_reading_date_started != null ? book_reading_date_started.get() : null;
    }

    public String getBook_Reading_date_ended() {
        return book_reading_date_ended != null ? book_reading_date_ended.get() : null;
    }

    public String getBook_Rating() {
        return book_rating != null ? book_rating.get() : null;
    }

    public String getBook_ID() {
        return book_ID != null ? book_ID.get() : null;
    }

    public ObservableList<Favorite> getFavorites() {
        return favorites;
    }

    //String Properties
    public StringProperty bookNameProperty() {
        return book_name;
    }

    public StringProperty bookAuthorProperty() {
        return book_author;
    }

    public StringProperty bookStatusProperty() {
        return book_status;
    }

    public StringProperty bookReadingStatusProperty() {
        return book_reading_status;
    }

    public StringProperty bookReadingDateStartedStatusProperty() {
        return book_reading_date_started;
    }

    public StringProperty bookReadingDateEndedStatusProperty() {
        return book_reading_date_ended;
    }

    public StringProperty bookRatingStatusProperty() {
        return book_rating;
    }

    //methods
    public void addFavorite(Favorite newFave) {
        favorites.add(newFave);
    }
}
