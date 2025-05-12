package com.matheusiowa12.entities;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ButtonMethods {
    public ButtonMethods(){
    }

    public void addItemMethod(JButton jbutton, ArrayList<TodoItem> todoItems){
        jbutton.addActionListener(e -> {
            String description = JOptionPane.showInputDialog("Please, type a task: ");
            if (description != null && !description.trim().isEmpty()) {
                TodoItem task = new TodoItem(description);
                todoItems.add(task);
            }
        });
    }

    public void listItemMethod(JButton jButton, ArrayList<TodoItem> todoItems, JFrame frame){
            jButton.addActionListener(e -> {
            JPanel tasksPanel = new JPanel();
            tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));

            JScrollPane scrollPane = new JScrollPane(tasksPanel);
            scrollPane.setPreferredSize(new Dimension(300, 200));

            Map<JCheckBox, TodoItem> checkboxTaskMap = new LinkedHashMap<>();

            for (TodoItem task : todoItems) {
                JCheckBox checkBox = new JCheckBox(" - " + task.getDescription());
                checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
                checkboxTaskMap.put(checkBox, task);
                tasksPanel.add(checkBox);
            }

            int result = JOptionPane.showConfirmDialog(
                    frame,
                    scrollPane,
                    "Select task(s) to complete:",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                ArrayList<TodoItem> toRemove = new ArrayList<>();
                for (Map.Entry<JCheckBox, TodoItem> entry : checkboxTaskMap.entrySet()) {
                    if (entry.getKey().isSelected()) {
                        toRemove.add(entry.getValue());
                    }
                }
                todoItems.removeAll(toRemove);
            }
        });
    }

    public void saveItemMethod(JButton jButton, ArrayList<TodoItem> todoItems, JFrame frame){
        jButton.addActionListener(e -> {
            String newFileName = JOptionPane.showInputDialog("Please, type a name for the save file: ");
            File directory = new File("src/com/matheusiowa12/savefiles");

            boolean fileExists = false;
            FilenameFilter txtFilter = (dir, name) -> name.toLowerCase().endsWith(".txt");

            File[] files = directory.listFiles(txtFilter);

            try {
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().equalsIgnoreCase(newFileName + ".txt")) {
                            fileExists = true;
                            break;
                        }
                    }
                }
                if (fileExists) {
                    int result = JOptionPane.showConfirmDialog(frame, "⚠ File's name's already taken! Do you want to overwrite it ?");
                    if (result == JOptionPane.YES_OPTION) {
                        FileWriter writeTxt = new FileWriter(new File(directory, newFileName + ".txt"));
                        for (TodoItem task : todoItems) {
                            writeTxt.write(task.toString());
                        }
                        writeTxt.close();
                    }
                } else {
                    if (newFileName != null && !newFileName.trim().isEmpty()) {
                        FileWriter writeTxt = new FileWriter(new File(directory, newFileName + ".txt"));
                        for (TodoItem task : todoItems) {
                            writeTxt.write(task.toString());
                        }
                        writeTxt.close();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "⚠ An error's occurred!: " + ex.getMessage());
            }
        });
    }

    public void loadItemMethod(JButton jButton, ArrayList<TodoItem> todoItems, JFrame frame){
        jButton.addActionListener(e -> {
            JPanel filesPanel = new JPanel();
            filesPanel.setLayout(new BoxLayout(filesPanel, BoxLayout.Y_AXIS));

            JScrollPane scrollPane = new JScrollPane(filesPanel);
            scrollPane.setPreferredSize(new Dimension(300, 200));

            Map<JRadioButton, File> radioButtonFileMap = new LinkedHashMap<>();

            File directory = new File("src/com/matheusiowa12/savefiles");

            FilenameFilter txtFilter = (dir, name) -> name.toLowerCase().endsWith(".txt");

            File[] files = directory.listFiles(txtFilter);

            ButtonGroup buttonGroup = new ButtonGroup();

            if (files != null) {
                for (File file : files) {
                    JRadioButton radioButton = new JRadioButton(file.getName());
                    radioButton.setFont(new Font("Arial", Font.PLAIN, 14));
                    radioButtonFileMap.put(radioButton, file);
                    buttonGroup.add(radioButton);
                    filesPanel.add(radioButton);
                }
            }

            int result = JOptionPane.showConfirmDialog(
                    frame,
                    scrollPane,
                    "Please, select a save in order to load it:",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            try {
                if (result == JOptionPane.OK_OPTION) {
                    ArrayList<File> toLoad = new ArrayList<>();
                    for (Map.Entry<JRadioButton, File> entry : radioButtonFileMap.entrySet()) {
                        if (entry.getKey().isSelected()) {
                            toLoad.add(entry.getValue());
                        }
                    }
                    String filePath = toLoad.getFirst().getAbsolutePath();

                    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                        String line;
                        todoItems.clear();
                        while ((line = br.readLine()) != null) {
                            TodoItem task = new TodoItem(line);
                            todoItems.add(task);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "⚠ An error's occurred!: " + ex.getMessage());
                    }
                }
            } catch (NoSuchElementException elementEx){
                JOptionPane.showMessageDialog(null, "⚠ You must select at least one file!");
            }
        });
    }
}
