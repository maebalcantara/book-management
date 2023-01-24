package com.example.librarymanagement;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import source.DataSource;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private static final DataSource ds = new DataSource();
    @Override
    public void start(Stage stage) throws IOException {
    	//add try catch here
        //add book
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("viewBooks.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        if(!DataSource.getInstance().open()) {
            System.out.println("FATAL ERROR: Couldn't connect to database");
            Platform.exit();
        }
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        DataSource.getInstance().close();
    }

    public static void main(String[] args) {
        launch();
    }
}

//1/19 to-do: add the driver for jdbc