package resources;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainWindowController {

    public Button loadFilterButton;
    public Label loadFilterLabel;
    public CheckBox wisdomCheckBox;
    public Label wisdomLabel;
    public Button saveFilterButton;
    public Label saveFilterLabel;
    int fontSize = 12;
    Font poeFont = Font.loadFont(getClass().getResourceAsStream("/resources/Fontin-Regular.ttf"), fontSize);

    Scanner sc;
    File selectedFile;
    List<String> linesInFile;
    ArrayList<Integer> findItemInList;

    private void initComponents() {

        // TODO setup labels/checkbox/buttons here
        loadFilterButton.setFont(poeFont);

    }

    @FXML
    void loadFilterFile(ActionEvent actionEvent) {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\My Games\\Path of Exile"));
        fc.setFileFilter(new FileNameExtensionFilter(".filter", "filter"));

        int returnValue = fc.showOpenDialog(fc.getParent());
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                selectedFile = fc.getSelectedFile();
                linesInFile = new ArrayList<>();
                sc = new Scanner(new FileReader(selectedFile));

                while(sc.hasNext()) {
                    linesInFile.add(sc.nextLine());
                }

                findItem("Wisdom");

                // --------- TESTING
                //sSystem.out.println(linesInFile);
                //System.out.println(findItemInList);
                // ---------
                sc.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to Read File");
            }
            loadFilterLabel.setVisible(true);
            loadFilterLabel.setText("Filter Loaded");
        } else {
            loadFilterLabel.setVisible(true);
            loadFilterLabel.setText("No Filter Chosen");
        }
    }

    @FXML
    public void saveFilterFile(ActionEvent actionEvent) {
        try {
            FileWriter fw = new FileWriter(selectedFile);
            for(String line: linesInFile) {
                fw.write(line + System.lineSeparator());
            }
            System.out.println("File Saved");
            fw.close();
        } catch(IOException e) {
            System.out.println("File not saved");
        }
        saveFilterLabel.setVisible(true);
        saveFilterLabel.setText("Filter Saved!");
    }

    public void findItem(String item) {
        /*
        Create empty arr list to store index of item in main List
        Loop over new list and if any items show 'Show', change state of checkBox to true
         */
        findItemInList = new ArrayList<>();
        for (String line : linesInFile) {
            if (line.contains(item) && line.contains("BaseType")) {
                findItemInList.add(linesInFile.indexOf(line));
            }
        }

        for (int i = 0; i < findItemInList.size(); i++) {
            for (int j = findItemInList.get(i); j >= 0; j--) {
                if (linesInFile.get(j).contains("Show") && !linesInFile.get(j).startsWith("#")) {
                    wisdomCheckBox.setSelected(true);
                }
            }
        }
    }

    public String changeShowHide(String string) {
        switch(string) {
            case "#Show":
                return "Hide" + string.substring(5);
            case "#Hide":
                return "Show" + string.substring(5);
            case "Hide":
                return "Show" + string.substring(4);
            case "Show":
                return "Hide" + string.substring(4);
            default:
                return "Hide " + string.substring(5);
        }
    }
/*
    public String changeShowHide(String string) {
        if(string.startsWith("#") || string.startsWith("#Show") || string.startsWith("#Hide")) {
            return string.substring(1);
        }
        return "#" + string;
    }

 */

    // TODO # is ignore, Show or Hide works
    // if line starts with #, ignore else change show to hide
    public void wisdomCheckBoxStateChanged(ActionEvent actionEvent) {
        String result = "";
            for (int i = 0; i < findItemInList.size(); i++) {
                for(int j = findItemInList.get(i); j >= 0; j--) {
                    if (linesInFile.get(j).contains("Show") || linesInFile.get(j).contains("Hide")) {
                        result = changeShowHide(linesInFile.get(j));
                        linesInFile.set(j, result);
                        break;
                    }
                }
            }
    }
}
