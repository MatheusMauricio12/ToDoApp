package com.matheusiowa12.entities;

import javax.swing.*;
import java.awt.*;

public class FrameUiConfig {
    public FrameUiConfig(){
    }

    public void frameConfigs(JFrame frame){
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setMinimumSize(new Dimension(600, 400));
    }
}
