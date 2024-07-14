import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

/**
 * @author Limon
 *         Main purpose of this program is managing startup apps and power modes
 *         with a basic start menu.
 *         Can be configured with start.txt.
 */
public class AppNodes {

    public AppNodes() throws FileNotFoundException {

        Linkedbag<JRadioButton> launchOptions;
        Linkedbag<String> launchOptionsFilePaths;
        Linkedbag<JRadioButton> powerOptions;
        Linkedbag<String> powerOptionsUUID;

        ButtonGroup powerOptionsGroup;
        Lister<JRadioButton> iteratorButtons;
        JRadioButton buttonForPanel;
        Color backgrounColor;

        int programsPanelRows;
        int programsPanelCols;

        int windowX;
        int windowY;

        // File read variables
        Scanner inputFile;
        String col;
        String row[];

        backgrounColor = new Color(0, 0, 0);
        powerOptionsGroup = new ButtonGroup();
        powerOptionsUUID = new Linkedbag<String>();
        powerOptions = new Linkedbag<JRadioButton>();
        launchOptionsFilePaths = new Linkedbag<String>();
        launchOptions = new Linkedbag<JRadioButton>();

        JFrame frame = new JFrame("Start Menu");
        frame.setBackground(new Color(100, 100, 100));

        // reading from file
        inputFile = new Scanner(new File("start.txt"));
        while (inputFile.hasNext()) {

            col = inputFile.nextLine();
            row = col.split("=");

            if (row[0].equalsIgnoreCase("(S)")) {
                launchOptions.add(new JRadioButton(row[1]));
                launchOptionsFilePaths.add(row[2]);

            }
            if (row[0].equalsIgnoreCase("(PM)")) {
                powerOptions.add(new JRadioButton(row[1]));
                powerOptionsUUID.add(row[2]);
            }
            if (row[0].equalsIgnoreCase("(CBG)")) {
                System.out.println("COLOR FOR BACKGROUND:" + row[1]);

                backgrounColor = new Color(Integer.parseInt(row[2]), Integer.parseInt(row[3]),
                        Integer.parseInt(row[4]));

            }
        }
        inputFile.close();

        JButton launch = new JButton("Launch Selected Apps");
        launch.setBorder(BorderFactory.createBevelBorder(1, new Color(333), new Color(222)));
        launch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Adding Launch Options about programs
                Lister<JRadioButton> iteratorButtonsListener = launchOptions.iterator();
                Lister<String> iteratorFilesListener = launchOptionsFilePaths.iterator();
                JRadioButton button;
                String filePath;
                while (iteratorButtonsListener.hasNext()) {
                    button = iteratorButtonsListener.next();
                    filePath = iteratorFilesListener.next();
                    if (button.isSelected()) {
                        execute(filePath);
                    }
                }
                iteratorButtonsListener = powerOptions.iterator();
                iteratorFilesListener = powerOptionsUUID.iterator();
                while (iteratorButtonsListener.hasNext()) {
                    button = iteratorButtonsListener.next();
                    filePath = iteratorFilesListener.next();
                    if (button.isSelected()) {
                        filePath = "cmd /C" + "powercfg /s " + filePath;
                        execute(filePath);
                    }

                }

            }

        });

        // This feature is under development. The main idea is to selecting startup apps
        // easier with grouping. For example, I want it to be configured like this:
        // when I click on low power mode it should enable power saver mode and open up
        // my browser. Something like this for High power mode too.

        /*
         * JButton mobile = new JButton("Low Power Mode");
         * mobile.setBorder(BorderFactory.createBevelBorder(1, new Color(0, 130, 0), new
         * Color(0, 70, 0)));
         * mobile.addActionListener(new ActionListener() {
         * public void actionPerformed(ActionEvent e) {
         * JOptionPane.showMessageDialog(null, "Configurable Low Power Mode Under De");
         * 
         * }
         * });
         * 
         * JButton gamer = new JButton("High Power Mode");
         * gamer.setBorder(BorderFactory.createBevelBorder(0, new Color(130, 0, 0), new
         * Color(70, 0, 0)));
         * gamer.addActionListener(new ActionListener() {
         * public void actionPerformed(ActionEvent e) {
         * 
         * }
         * });
         */

        programsPanelCols = (int) Math.ceil(Math.sqrt(launchOptions.getNumElement()));
        programsPanelRows = (int) Math.floor(Math.sqrt(launchOptions.getNumElement()));

        JPanel programsPanel = new JPanel();
        programsPanel.setBackground(backgrounColor);
        programsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        programsPanel.setLayout(new GridLayout(programsPanelRows, programsPanelCols));

        JPanel setupsPanel = new JPanel();
        setupsPanel.setBackground(backgrounColor);
        setupsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        setupsPanel.setLayout(new GridLayout(1, 3));

        JPanel powerModesPanel = new JPanel();
        powerModesPanel.setBackground(backgrounColor);
        powerModesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        powerModesPanel.setLayout(new GridLayout(1, 5));

        iteratorButtons = launchOptions.iterator();
        while (iteratorButtons.hasNext()) {
            buttonForPanel = iteratorButtons.next();
            buttonForPanel.setBackground(backgrounColor);
            buttonForPanel.setForeground(new Color(255, 255, 255));
            programsPanel.add(buttonForPanel);
        }

        iteratorButtons = powerOptions.iterator();
        while (iteratorButtons.hasNext()) {
            buttonForPanel = iteratorButtons.next();
            buttonForPanel.setBackground(backgrounColor);
            buttonForPanel.setForeground(new Color(255, 255, 255));
            powerModesPanel.add(buttonForPanel);
            powerOptionsGroup.add(buttonForPanel);
        }

        windowX = (programsPanelCols) * 100;
        windowY = (programsPanelRows) * 100;

        if (windowX < 300) {
            windowX = 500;
            windowY = 400;

        }

        // setupsPanel.add(mobile);
        setupsPanel.add(launch);
        // setupsPanel.add(gamer);

        frame.add(programsPanel, BorderLayout.CENTER);
        frame.add(setupsPanel, BorderLayout.SOUTH);
        frame.add(powerModesPanel, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Start Menu");

        frame.pack();
        frame.setVisible(true);
        frame.setSize(windowX, windowY);

    }

    public void execute(String filePath) {
        try {
            Runtime.getRuntime().exec(filePath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        new AppNodes();
    }

}
