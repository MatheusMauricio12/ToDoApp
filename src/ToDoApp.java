import com.formdev.flatlaf.FlatDarkLaf;
import com.matheusiowa12.entities.ButtonMethods;
import com.matheusiowa12.entities.ButtonUi;
import com.matheusiowa12.entities.FrameUiConfig;
import com.matheusiowa12.entities.TodoItem;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ToDoApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf Dark Theme");
        }
        ArrayList<TodoItem> todoList = new ArrayList<>();

        ButtonMethods methods = new ButtonMethods();
        ButtonUi jbuttonUi = new ButtonUi();
        FrameUiConfig jframeUiConfig = new FrameUiConfig();

        JFrame frame = new JFrame("To-Do Application");
        jframeUiConfig.frameConfigs(frame);

        ImageIcon backgroundIcon = new ImageIcon(
                ToDoApp.class.getResource("/com/matheusiowa12/resources/task_image.png")
        );

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

        ArrayList<JButton> jButtons = new ArrayList<>();

        JButton addTaskItem = new JButton("Add task(s)");
        JButton listTaskItem = new JButton("List task(s)");
        JButton saveTaskItem = new JButton("Save task(s)");
        JButton loadTaskItem = new JButton("Load task(s)");
        JButton exitItem = new JButton("Exit");

        jButtons.add(addTaskItem);
        jButtons.add(listTaskItem);
        jButtons.add(saveTaskItem);
        jButtons.add(loadTaskItem);
        jButtons.add(exitItem);

        jbuttonUi.jButtonMouseListener(jButtons);
        jbuttonUi.jButtonPattern(jButtons);

        menuPanel.add(Box.createVerticalGlue());
        jbuttonUi.spacingMenuPanel(jButtons, menuPanel);
        menuPanel.add(Box.createVerticalGlue());


        mainPanel.add(menuPanel, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);

        methods.addItemMethod(addTaskItem, todoList);

        methods.listItemMethod(listTaskItem, todoList, frame);

        methods.saveItemMethod(saveTaskItem, todoList, frame);

        methods.loadItemMethod(loadTaskItem, todoList, frame);

        exitItem.addActionListener(e -> System.exit(0));
    }
}