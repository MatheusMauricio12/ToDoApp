import com.formdev.flatlaf.FlatLightLaf;
import com.matheusiowa12.entities.TodoItem;
import javax.swing.*;
import java.awt.*;

public static void main(String[] args) {
    FlatLightLaf.setup();
    ArrayList<TodoItem> todoList = new ArrayList<>();

    JFrame frame = new JFrame("To-Do Application");
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    ImageIcon backgroundIcon = new ImageIcon("C:src\\com\\matheusiowa12\\resources\\task_image.png");
    Image backgroundImage = backgroundIcon.getImage();

    JPanel mainPanel = new JPanel(new BorderLayout()) {
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
    frame.setResizable(true);
    frame.setMinimumSize(new Dimension(600, 400));

    addTaskItem.addActionListener(e -> {
        String description = JOptionPane.showInputDialog("Please, type a task: ");
        if (description != null && !description.trim().isEmpty()) {
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

        for (TodoItem task : todoList) {
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
            todoList.removeAll(toRemove);
        }
    });

    saveTaskItem.addActionListener(e -> {
        String newFileName = JOptionPane.showInputDialog("Please, type a name for the save file: ");
        File directory = new File("src/com/matheusiowa12/savefiles");

        boolean fileExists = false;
        FilenameFilter txtFilter = (dir, name) -> name.toLowerCase().endsWith(".txt");

        File[] files = directory.listFiles(txtFilter);

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
            if(result == JOptionPane.YES_OPTION){
                try {
                    FileWriter writeTxt = new FileWriter(new File(directory, newFileName + ".txt"));
                    for (TodoItem task : todoList) {
                        writeTxt.write(task.toString());
                    }
                    writeTxt.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "⚠ An error's occurred!: " + ex.getMessage());
                }
            }
        } else {
            if (newFileName != null && !newFileName.trim().isEmpty()) {
                try {
                    FileWriter writeTxt = new FileWriter(new File(directory, newFileName + ".txt"));
                    for (TodoItem task : todoList) {
                        writeTxt.write(task.toString());
                    }
                    writeTxt.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "⚠ An error's occurred!: " + ex.getMessage());
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
                    todoList.clear();
                    while ((line = br.readLine()) != null) {
                        TodoItem task = new TodoItem(line);
                        todoList.add(task);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "⚠ An error's occurred!: " + ex.getMessage());
                }
            }
        } catch (NoSuchElementException elementEx){
            JOptionPane.showMessageDialog(null, "⚠ You must select at least one file!");
        }
    });

    exitItem.addActionListener(e -> System.exit(0));
}

