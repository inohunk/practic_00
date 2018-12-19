package ru.hunkel;

import OCAD_package.OCADCreator;
import OCAD_package.OCADOpener;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class App {
    private final String DEFAULT_DIRECTORY="D://#0cad";
    private JButton button_message;
    private JPanel panelMain;
    private JTextArea textArea1;
    private OCADCreator ocadFile;

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
                        ocadFile = OCADOpener.createOCADObj(fileChooser.getSelectedFile().getPath());
                        so(ocadFile.getVersion());

                    } catch (Exception ex) {
                        errorMessage(ex.toString());
                    }
                }
            }
        });
    }
    private void errorMessage(String str) {
        textArea1.setText(str);
    }

}
