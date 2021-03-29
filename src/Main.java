import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        FilterEditor test = new FilterEditor();
        test.readFile();
        test.saveFile();
    }

    // TODO create method to readfile
    // TODO create method to overwrite file
    // TODO create method to handle adding/removing #'s


}
