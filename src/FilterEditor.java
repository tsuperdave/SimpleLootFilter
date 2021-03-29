import javax.swing.*;
import java.io.*;
import java.util.*;

public class FilterEditor extends JFrame{

    private JFileChooser chooseFile;
    Scanner sc;
    List<String> linesInFile;

    public void filterEditor() {

        // initComponents();
        /*
        chooseFile = new JFileChooser();
        chooseFile.setCurrentDirectory(new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\My Games\\Path of Exile"));
        chooseFile.setFileFilter(new FileNameExtensionFilter(".filter", "filter"));
         */

    }

    public void readFile() {

        // File file = chooseFile.getSelectedFile();
        // int fileChosen = chooseFile.showOpenDialog(chooseFile.getParent());
        //if(fileChosen == JFileChooser.APPROVE_OPTION)
        try {
            File file = new File("C:\\Users\\Dave\\Documents\\My Games\\Path of Exile\\wisdomTest.txt");
            linesInFile = new ArrayList<>();
            sc = new Scanner(new FileReader(file));
            while(sc.hasNext()) {
                linesInFile.add(sc.nextLine());
            }
            findItem("Wisdom");
            // System.out.println(linesInFile);
            sc.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        try {
            FileWriter fw = new FileWriter("C:\\Users\\Dave\\Documents\\My Games\\Path of Exile\\wisdomTest.txt");
            for(String line: linesInFile) {
                fw.write(line + System.lineSeparator());
            }
            fw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String changeShowHide(String string) {
        if(string.startsWith("#") || string.startsWith("#Show") || string.startsWith("#Hide")) {
                return string.substring(1);
        }
        return "#" + string;
    }

    /*
    will find items and add index to new list
    item also has to be on same line as 'BaseType'
     */
    public void findItem(String item) {
        ArrayList<Integer> itemList = new ArrayList<>();

        for (String line: linesInFile) {
            if(line.contains(item) && line.contains("BaseType")) {
                itemList.add(linesInFile.indexOf(line));
            }
        }

        for(int i = 0; i < itemList.size(); i++) {
            System.out.println("For loop " + linesInFile.get(itemList.get(i)));
            for(int j = itemList.get(i); j >= 0; j--) {
                String newLine = "";
                if (linesInFile.get(j).contains("Hide") || linesInFile.get(j).contains("Show")) {
                    System.out.println("-----");
                    System.out.println("Before change: " + linesInFile.get(j));
                    newLine = changeShowHide(linesInFile.get(j));
                    linesInFile.set(j, newLine);
                    System.out.println("After change: " + linesInFile.get(j));
                    System.out.println("-----");
                    break;
                }

            }
        }

        // System.out.println(item + " has been found " + itemList.size() + " number of times");
        System.out.println(itemList);
        ///System.out.println(linesInFile.get(itemList.get(0)));
        ///System.out.println(linesInFile.get(itemList.get(1)));
        ///System.out.println(linesInFile.get(itemList.get(2)));
    }

    /*
    Will swap visibility of items
     */

    private void initComponents() {

        // TODO setup labels/checkbox/buttons here
        JButton loadFilter = new JButton();
        loadFilter.setText("Load Filter");
        // loadFilter.addActionListener(ActionListener);

    }
}
