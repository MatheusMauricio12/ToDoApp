import com.matheusiowa12.entities.TodoItem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
    ArrayList<TodoItem> todoList = new ArrayList<>();

    boolean running = true;

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

    JButton AddTaskItem = new JButton("Add task(s)");
    JButton ListTaskItem = new JButton("List task(s)");
    JButton SaveTaskItem = new JButton("Save task(s)");
    JButton LoadTaskItem = new JButton("Load task(s)");
    JButton ExitItem = new JButton("Exit");

    AddTaskItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    ListTaskItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    SaveTaskItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    LoadTaskItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    ExitItem.setAlignmentX(Component.CENTER_ALIGNMENT);

    menuPanel.add(Box.createVerticalGlue()); // pushes content to vertical center
    menuPanel.add(AddTaskItem);
    menuPanel.add(Box.createVerticalStrut(10));
    menuPanel.add(ListTaskItem);
    menuPanel.add(Box.createVerticalStrut(10));
    menuPanel.add(SaveTaskItem);
    menuPanel.add(Box.createVerticalStrut(10));
    menuPanel.add(LoadTaskItem);
    menuPanel.add(Box.createVerticalStrut(10));
    menuPanel.add(ExitItem);
    menuPanel.add(Box.createVerticalGlue()); // balances bottom spacing


    mainPanel.add(menuPanel, BorderLayout.CENTER);

    frame.setContentPane(mainPanel);
    frame.setVisible(true);

    AddTaskItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String description = JOptionPane.showInputDialog("Please, type a task: ");
            if (description != null && !description.trim().isEmpty()){
                TodoItem task = new TodoItem(description);
                todoList.add(task);
            }
        }
    });


    ListTaskItem.addActionListener(e -> {
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

    SaveTaskItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fileName = JOptionPane.showInputDialog("Please, type a name for the save file: ");
            File directory = new File("src/com/matheusiowa12/savefiles");

            FilenameFilter txtFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    String lowercaseName = name.toLowerCase();
                    return lowercaseName.endsWith(".txt");
                }
            };

            File[] files = directory.listFiles(txtFilter);

            if (fileName != null && !fileName.trim().isEmpty()){
                try {
                    FileWriter writeTxt = new FileWriter(new File(directory, fileName + ".txt"));
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

    LoadTaskItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel filesPanel = new JPanel();
            filesPanel.setLayout(new BoxLayout(filesPanel, BoxLayout.Y_AXIS));

            JScrollPane scrollPane = new JScrollPane(filesPanel);
            scrollPane.setPreferredSize(new Dimension(300, 200));

            Map<JCheckBox, File> checkBoxFileMap = new LinkedHashMap<>();

            File directory = new File("src/com/matheusiowa12/savefiles");


            FilenameFilter txtFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    String lowercaseName = name.toLowerCase();
                    return lowercaseName.endsWith(".txt");
                }
            };

            File[] files = directory.listFiles(txtFilter);

            if(files != null){
                for(File file : files){
                    JCheckBox checkBox = new JCheckBox(file.getName());
                    checkBox.setFont(new Font("Arial", Font.PLAIN, 14 ));
                    checkBoxFileMap.put(checkBox, file);
                    filesPanel.add(checkBox);
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
                System.out.println("Wabalabadoobdoob");
            }
        }
    });



    ExitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            System.exit(0);
            }
        });
    }
}

