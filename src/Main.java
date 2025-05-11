import com.matheusiowa12.entities.TodoItem;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
    ArrayList<TodoItem> todoList = new ArrayList<>();

    JFrame frame = new JFrame("To-Do Application");
    frame.setSize(700, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    ImageIcon backgroundIcon = new ImageIcon("C:src\\com\\matheusiowa12\\resources\\task_image.png");
    Image backgroundImage = backgroundIcon.getImage();

    JPanel mainPanel = new JPanel(new BorderLayout()){
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    };

    mainPanel.setOpaque(false);

    JPanel menuPanel = new JPanel();
    menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
    menuPanel.setOpaque(false);

    JButton addTaskItem = new JButton("Add task(s)");
    JButton listTaskItem = new JButton("List task(s)");
    JButton saveTaskItem = new JButton("Save task(s)");
    JButton loadTaskItem = new JButton("Load task(s)");
    JButton exitItem = new JButton("Exit");

    addTaskItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    listTaskItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    saveTaskItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    loadTaskItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    exitItem.setAlignmentX(Component.CENTER_ALIGNMENT);

    menuPanel.add(Box.createVerticalGlue());
    menuPanel.add(addTaskItem);
    menuPanel.add(Box.createVerticalStrut(10));
    menuPanel.add(listTaskItem);
    menuPanel.add(Box.createVerticalStrut(10));
    menuPanel.add(saveTaskItem);
    menuPanel.add(Box.createVerticalStrut(10));
    menuPanel.add(loadTaskItem);
    menuPanel.add(Box.createVerticalStrut(10));
    menuPanel.add(exitItem);
    menuPanel.add(Box.createVerticalGlue());


    mainPanel.add(menuPanel, BorderLayout.CENTER);

    frame.setContentPane(mainPanel);
    frame.setVisible(true);

    addTaskItem.addActionListener(e -> {
            String description = JOptionPane.showInputDialog("Please, type a task: ");
            if (description != null && !description.trim().isEmpty()){
                TodoItem task = new TodoItem(description);
                todoList.add(task);
        }
    });


    listTaskItem.addActionListener(e -> {
        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(tasksPanel);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        Map<JCheckBox, TodoItem> checkboxTaskMap = new LinkedHashMap<>();

        for (TodoItem task: todoList){
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

        if(result == JOptionPane.OK_OPTION){
            ArrayList<TodoItem> toRemove = new ArrayList<>();
            for(Map.Entry<JCheckBox, TodoItem> entry : checkboxTaskMap.entrySet()){
                if(entry.getKey().isSelected()){
                    toRemove.add(entry.getValue());
                }
            }
            todoList.removeAll(toRemove);
        }
     });

    saveTaskItem.addActionListener(e -> {
            String newFileName = JOptionPane.showInputDialog("Please, type a name for the save file: ");
            File directory = new File("src/com/matheusiowa12/savefiles");
            boolean fileExists = false;

            FilenameFilter txtFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".txt");
                }
            };

            File[] files = directory.listFiles(txtFilter);

            if (files != null) {
                for (File file : files) {
                    if (file.getName().equalsIgnoreCase(newFileName + ".txt")) {
                        fileExists = true;
                        break;
                    }
                }
            }
            if(fileExists){
                JOptionPane.showMessageDialog(null, "⚠ File's name's already taken! Please, try again!");
                newFileName = JOptionPane.showInputDialog("Please, type a name for the save file:");
            } else {
                if (newFileName != null && !newFileName.trim().isEmpty()){
                    try {
                        FileWriter writeTxt = new FileWriter(new File(directory, newFileName + ".txt"));
                        for(TodoItem task : todoList){
                            writeTxt.write(task.toString());
                        }
                        writeTxt.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
    });

    loadTaskItem.addActionListener(e -> {
            JPanel filesPanel = new JPanel();
            filesPanel.setLayout(new BoxLayout(filesPanel, BoxLayout.Y_AXIS));

            JScrollPane scrollPane = new JScrollPane(filesPanel);
            scrollPane.setPreferredSize(new Dimension(300, 200));

            Map<JRadioButton, File> radioButtonFileMap = new LinkedHashMap<>();

            File directory = new File("src/com/matheusiowa12/savefiles");

            FilenameFilter txtFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".txt");
                }
            };

            File[] files = directory.listFiles(txtFilter);

            ButtonGroup buttonGroup = new ButtonGroup();

            if(files != null){
                for(File file : files){
                    JRadioButton radioButton = new JRadioButton(file.getName());
                    radioButton.setFont(new Font("Arial", Font.PLAIN, 14 ));
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

            if(result == JOptionPane.OK_OPTION){
                ArrayList<File> toLoad = new ArrayList<>();
                for(Map.Entry<JRadioButton, File> entry : radioButtonFileMap.entrySet()){
                    if(entry.getKey().isSelected()){
                        toLoad.add(entry.getValue());
                    }
                }
                String filePath = toLoad.get(0).getAbsolutePath();

                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    todoList.clear();
                    while ((line = br.readLine()) != null) {
                       TodoItem task = new TodoItem(line);
                       todoList.add(task);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
    });

    exitItem.addActionListener(e -> {
         System.exit(0);
        });
    }
}

