package Functions;

import javax.swing.*;
import java.io.File;

public class ChoseElement {

    public static File choseElement (){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select a image");

        int result = chooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) return null;

        File file = chooser.getSelectedFile();

        if (file == null) {
            System.out.println("Invalid file.");
            return null;
        }
        return file;

    }

}
