package source;

import classes.Book;
import classes.Favorite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private final String default_book_img_path = "C:\\Users\\maeal\\Desktop\\java_training\\LibraryManagement\\images\\no-image.jpeg";
    private static final String DB_NAME = "books.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\maeal\\Desktop\\java_training\\LibraryManagement\\" + DB_NAME;
    private Connection conn;

    //TABLES
    private static final String TABLE_BOOKS = "books";
    private static final String TABLE_FAVORITES = "book_fave_items";

    //BOOKS LABELS
    private static final String LABEL_BOOK_ID = "book_ID";
    private static final String LABEL_BOOK_NAME = "book_name";
    private static final String LABEL_BOOK_AUTHOR = "book_author";
    private static final String LABEL_BOOK_STATUS = "book_status";
    private static final String LABEL_BOOK_READING_STATUS = "book_reading_status";
    private static final String LABEL_BOOK_READING_DATE_STARTED = "book_reading_date_started";
    private static final String LABEL_BOOK_READING_DATE_ENDED = "book_reading_date_ended";
    private static final String LABEL_BOOK_RATING = "book_rating";
    private static final String LABEL_BOOK_IMAGE_PATH = "book_img_path";

    //FAVE LABELS
    private static final String LABEL_FAVE_ID = "fave_ID";
    private static final String LABEL_FAVE_PAGE = "fave_page";
    private static final String LABEL_FAVE_TEXT = "fave_text";
    private static final String BOOK_COLUMNS = "(" + LABEL_BOOK_NAME + ", " + LABEL_BOOK_AUTHOR + ", " + LABEL_BOOK_STATUS + ", " + LABEL_BOOK_IMAGE_PATH + ")";
    private static final String FAVE_COLUMNS = "(" + LABEL_BOOK_ID + ", " + LABEL_FAVE_PAGE + ", " + LABEL_FAVE_TEXT + ")";

    private static DataSource instance = new DataSource();

    private final ObservableList<Book> books = FXCollections.observableArrayList();

    public static DataSource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }


    //generic methods
    //insert into
    private boolean insertInto(String tableName, String columns, String values) {
        String command = "INSERT INTO " + tableName + columns + " VALUES " + values;
        try (Statement st = conn.createStatement()) {
            return st.execute(command);
        } catch (SQLException e) {
            System.out.println("SQLException occurred: " + e);
        }
        return true;
    }

    //update
    private boolean updateItem(String tableName, String newItemLabel, String newItemValue, String title, String author) {
        String command = "UPDATE " + tableName +
                " SET " + newItemLabel + " = '" +
                newItemValue + "' WHERE " + LABEL_BOOK_NAME + " = '" + title + "' COLLATE NOCASE AND " +
                LABEL_BOOK_AUTHOR + " = '" + author + "' COLLATE NOCASE";
        try (Statement st = conn.createStatement()) {
            return st.execute(command);
        } catch (SQLException e) {
            System.out.println("SQLException occurred: " + e);
        }
        return true;
    }

    //methods
    public boolean addBook(String book_name, String book_author, String book_status, String book_img_path) {
        if (returnBook(book_name, book_author) == null) {
            String values = "('" + book_name + "', '" + book_author + "', '" + book_status + "', '" + book_img_path + "')";
            return insertInto(TABLE_BOOKS, BOOK_COLUMNS, values);
        } else {
            return true;
        }
    }
    //false if successful, true if not
    public boolean addFavorite(int book_id, int page, String text){
        String values = "(" + book_id + ", " + page + ", '" + text + "')";
        return insertInto(TABLE_FAVORITES, FAVE_COLUMNS, values);
    }
    //editable 0 - book status; 1 - book_reading_status ; 2 - reading_date_started; 3 - reading_date_ended; 4 - book rating
    public boolean updateBook(String title, String author, String[] editable) {
        Book book = returnBook(title, author);
        if (book != null) {
            String book_status = editable[0];
            String book_reading_status = editable[1];
            String reading_date_started = editable[2];
            String reading_date_ended = editable[3];
            String book_rating = editable[4];

            //add yung sa image?
            //book status
            if (!book_status.equalsIgnoreCase(book.getBook_status())) {
                if (updateItem(TABLE_BOOKS, LABEL_BOOK_STATUS, book_status, title, author)) {
                    return true;
                }
            }
            //book reading_status
            if (book_reading_status != null && !book_reading_status.equalsIgnoreCase(book.getBook_Reading_status())) {
                if (updateItem(TABLE_BOOKS, LABEL_BOOK_READING_STATUS, book_reading_status, title, author)) {
                    return true;
                }
            }
            //date_started
            if (reading_date_started != null && !reading_date_started.equalsIgnoreCase(book.getBook_Reading_date_started())) {
                if (updateItem(TABLE_BOOKS, LABEL_BOOK_READING_DATE_STARTED, reading_date_started, title, author)) {
                    return true;
                }
            }
            //date_ended
            if (reading_date_ended != null && !reading_date_ended.equalsIgnoreCase(book.getBook_Reading_date_ended())) {
                if (updateItem(TABLE_BOOKS, LABEL_BOOK_READING_DATE_ENDED, reading_date_ended, title, author)) {
                    return true;
                }
            }
            //book_rating
            if (book_rating != null && !book_rating.equalsIgnoreCase(book.getBook_Rating())) {
                if (updateItem(TABLE_BOOKS, LABEL_BOOK_RATING, book_rating, title, author)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String testHello(String text) {
        return text;
    }

    //create an overloaded returnBook?
    public Book returnBook(int book_id) {
        String command = "SELECT * from " + TABLE_BOOKS +
                " WHERE " + LABEL_BOOK_ID + " = " + book_id;
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(command)) {
            while(results.next()){
//                System.out.println("hello");
                return returnBook(results.getString(LABEL_BOOK_NAME), results.getString(LABEL_BOOK_AUTHOR));
            }
        }catch(SQLException e ) {
            System.out.println("SQLException occurred: " + e);
        }
        return null;
    }
    private Book returnBook(String book_name, String book_author) {
        String command = "SELECT * from " + TABLE_BOOKS +
                " WHERE " + LABEL_BOOK_NAME + " = '" + book_name + "' COLLATE NOCASE AND " +
                LABEL_BOOK_AUTHOR + " = '" + book_author + "' COLLATE NOCASE";
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(command)) {
            while (results.next()) {
                String favoriteCommand = "SELECT * from " + TABLE_FAVORITES + " WHERE " + LABEL_BOOK_ID + " = " + results.getInt(LABEL_BOOK_ID);
                if (book_name.toLowerCase().equalsIgnoreCase(results.getString(LABEL_BOOK_NAME)) && book_author.toLowerCase().equalsIgnoreCase(results.getString(LABEL_BOOK_AUTHOR))) {
                    Book book = new Book(book_name, book_author, results.getString(LABEL_BOOK_STATUS), results.getString(LABEL_BOOK_IMAGE_PATH) != null ? results.getString(LABEL_BOOK_IMAGE_PATH) : default_book_img_path);

                    book.setBook_ID(results.getString(LABEL_BOOK_ID));

                    try (Statement statement2 = conn.createStatement();
                         ResultSet favoriteResult = statement2.executeQuery(favoriteCommand)) {
                        while (favoriteResult.next()) {
                            Favorite newFave = new Favorite(favoriteResult.getInt(LABEL_BOOK_ID), favoriteResult.getInt(LABEL_FAVE_ID), favoriteResult.getInt(LABEL_FAVE_PAGE), favoriteResult.getString(LABEL_FAVE_TEXT));
                            book.addFavorite(newFave);
                        }
                    } catch (SQLException e) {
                        System.out.println("SQLException occurred: " + e);
                    }
                    return book;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException occurred: " + e);
        }
        return null;
    }
    public ObservableList<Book> queryBooks() {
        String command = "SELECT * from " + TABLE_BOOKS + " ORDER BY " + LABEL_BOOK_NAME + " COLLATE NOCASE";

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(command)) {
            books.clear();
            while (results.next()) {
                String favoriteCommand = "SELECT * from " + TABLE_FAVORITES + " WHERE " + LABEL_BOOK_ID + " = " + results.getInt(LABEL_BOOK_ID);
                Book book = new Book(results.getString(LABEL_BOOK_NAME), results.getString(LABEL_BOOK_AUTHOR), results.getString(LABEL_BOOK_STATUS), results.getString(LABEL_BOOK_IMAGE_PATH) != null ? results.getString(LABEL_BOOK_IMAGE_PATH) : default_book_img_path);

                try (Statement statement2 = conn.createStatement();
                     ResultSet favoriteResult = statement2.executeQuery(favoriteCommand)) {
                    while (favoriteResult.next()) {
                        Favorite newFave = new Favorite(favoriteResult.getInt(LABEL_BOOK_ID), favoriteResult.getInt(LABEL_FAVE_ID), favoriteResult.getInt(LABEL_FAVE_PAGE), favoriteResult.getString(LABEL_FAVE_TEXT));
                        book.addFavorite(newFave);
                    }
                } catch (SQLException e) {
                    System.out.println("SQLException occurred: " + e);
                }
                //add book_ID
                book.setBook_ID(results.getString(LABEL_BOOK_ID));
                book.setBook_reading_status(results.getString(LABEL_BOOK_READING_STATUS) != null ? results.getString(LABEL_BOOK_READING_STATUS) : null);
                book.setBook_reading_date_started(results.getString(LABEL_BOOK_READING_DATE_STARTED) != null ? results.getString(LABEL_BOOK_READING_DATE_STARTED) : null);
                book.setBook_reading_date_ended(results.getString(LABEL_BOOK_READING_DATE_ENDED) != null ? results.getString(LABEL_BOOK_READING_DATE_ENDED) : null);
                book.setBook_rating(results.getString(LABEL_BOOK_RATING) != null ? results.getString(LABEL_BOOK_RATING) : null);
                if (!books.contains(book)) {
                    books.add(book);
                }
            }
            return books;
        } catch (SQLException e) {
            System.out.println("SQLException occurred: " + e);
        }
        return null;
    }
}
//before adding check if the book already exists
//pag successful mag show ng information na book was added, clear yung mga fields,
//i save ko nalang kaya as all small letters?


