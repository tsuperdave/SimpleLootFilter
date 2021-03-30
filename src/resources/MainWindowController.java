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
        // saveFile();
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
        if(string.startsWith("#") || string.startsWith("#Show") || string.startsWith("#Hide")) {
            return string.substring(1);
        }
        return "#" + string;
    }

    public void saveFile() {
        try {
            FileWriter fw = new FileWriter(selectedFile);
            for(String line: linesInFile) {
                fw.write(line + System.lineSeparator());
            }
            fw.close();
        } catch(IOException e) {
            System.out.println("File not saved");
        }
    }

    public void wisdomCheckBoxStateChanged(ActionEvent actionEvent) {
        String result = "";
        if (wisdomCheckBox.isSelected()) {
            for (int i = 0; i < findItemInList.size(); i++) {
                result = changeShowHide(linesInFile.get(findItemInList.get(i)));
                linesInFile.set(findItemInList.get(i), result);
            }
        }
    }
}
