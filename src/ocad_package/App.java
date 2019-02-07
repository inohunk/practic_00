package ocad_package;

import ocad_package.interfaces.IOCADCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * @author hunkel
 * @version 0.1.0
 * Основной класс приложения, в котором будет отображаться вся получаемая информация
 */
public class App {
    private final String DEFAULT_DIRECTORY="D://Projects//OCAD";
    private JButton button_message;
    private JPanel panelMain;
    private JTextArea textArea1;
    private IOCADCreator ocadFile;

    public void so(Object obj)
    {
        System.out.println(obj.toString());
    }
    public static void main(String ... args)
    {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.pack();
        frame.setSize(900,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public App() {
        //Слушатель кнопки открытия FileChooser
        button_message.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(DEFAULT_DIRECTORY);
                fileChooser.setDialogTitle("Выбор директории");

                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int result = fileChooser.showOpenDialog(null);
                // Проверка на выбранную директорию
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        ocadFile = FabricClass.createOCADObj(fileChooser.getSelectedFile().getPath());
                        so("FileHeader info: \n");
                        so("FileType: " + ocadFile.getFileType());
                        so("File Status: " + ocadFile.getFileStatus());
                        so("Version: " + ocadFile.getVersion() +"."+ ocadFile.getSubVersion() +"."+ ocadFile.getSubSubVersion());
                        so("First Object Block: "+ocadFile.getFirstObjectBlock());
                        System.out.println(ocadFile.getNumberOfCharacters());
                        ocadFile.outSymbols();
                        //so("First Symbol Index Block: \t"+ocadFile.getFirstSymbolIndexBlock());
                        //so("Number of symbols: " + ocadFile.getNumberOfCharacters());
                        /*ocadFile.getNumberOfCharacters();
                        ocadFile.getData();*/




                    } catch (Exception ex) {
                        so(ex.toString());
                    }
                }
            }
        });
    }
}
