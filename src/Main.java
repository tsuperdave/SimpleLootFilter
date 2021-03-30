import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import resources.MainWindowController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Simple Loot Filter");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWindowController.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        /*
        FilterEditor test = new FilterEditor();
        test.readFile();
        test.saveFile();
         */
        launch(args);
    }


    // TODO create method to readfile
    // TODO create method to overwrite file
    // TODO create method to handle adding/removing #'s


}
