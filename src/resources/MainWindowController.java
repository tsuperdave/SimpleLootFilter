package resources;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MainWindowController {

    public Button loadFilterButton;
    public Label loadFilterLabel;
    public CheckBox wisdomCheckBox;
    public Label wisdomLabel;
    public Button saveFilterButton;
    public Label saveFilterLabel;
    public AnchorPane mainWindow;
    public CheckBox portalCheckBox;
    public Label portalLabel;
    int fontSize = 12;
    Font poeFont = Font.loadFont(getClass().getResourceAsStream("/resources/Fontin-Regular.ttf"), fontSize);

    Scanner sc;
    File selectedFile;
    List<String> linesInFile;
    HashMap<String, ArrayList<Integer>> itemMap = new HashMap<>();

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
                findItem("Portal Scroll");

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
            System.out.println("Filter Saved");
            fw.close();
        } catch(IOException e) {
            System.out.println("File not saved");
        }
        saveFilterLabel.setVisible(true);
        saveFilterLabel.setText("Filter Saved!");
    }

    public void findItem(String item) {

        ArrayList<Integer> findItemList = new ArrayList<>();

        for (String line : linesInFile) {
            if (line.contains(item) && line.contains("BaseType")) {
                findItemList.add(linesInFile.indexOf(line));
                itemMap.putIfAbsent(item, findItemList);
            }
        }

        for (int i = 0; i < findItemList.size(); i++) {
            for (int j = findItemList.get(i); j >= 0; j--) {
                if (linesInFile.get(j).contains("Show") && !linesInFile.get(j).startsWith("#")) {
                    wisdomCheckBox.setSelected(true);
                    portalCheckBox.setSelected(true);
                }
            }
        }
    }

    public void setCheckBoxState () {

    }

    public String changeShowToHide(String string) {
        if(string.startsWith("#")) {
            return "Hide" + string.substring(5);
        }
        return "Hide" + string.substring(4);
    }

    public String changeHideToShow(String string) {
        if(string.startsWith("#")) {
            return "Show" + string.substring(5);
        }
        return "Show" + string.substring(4);
    }

    public void wisdomCheckBoxStateChanged(ActionEvent actionEvent) {
        String result = "";
        for (int i = 0; i < itemMap.get("Wisdom").size(); i++) {
            for(int j = itemMap.get("Wisdom").get(i); j >= 0; j--) {
                if (linesInFile.get(j).contains("Show") && !wisdomCheckBox.isSelected()) {
                    result = changeShowToHide(linesInFile.get(j));
                    linesInFile.set(j, result);
                    break;
                } else if(linesInFile.get(j).contains("Hide") && wisdomCheckBox.isSelected()) {
                    result = changeHideToShow(linesInFile.get(j));
                    linesInFile.set(j, result);
                    break;
                }
            }
        }
    }

    public void portalCheckBoxStateChanged(ActionEvent actionEvent) {
        String result = "";
        for (int i = 0; i < itemMap.get("Portal Scroll").size(); i++) {
            for(int j = itemMap.get("Portal Scroll").get(i); j >= 0; j--) {
                if (linesInFile.get(j).contains("Show") && !portalCheckBox.isSelected()) {
                    result = changeShowToHide(linesInFile.get(j));
                    linesInFile.set(j, result);
                    break;
                } else if(linesInFile.get(j).contains("Hide") && portalCheckBox.isSelected()) {
                    result = changeHideToShow(linesInFile.get(j));
                    linesInFile.set(j, result);
                    break;
                }
            }
        }
    }
}