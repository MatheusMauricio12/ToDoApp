package com.matheusiowa12.entities;

import javax.swing.*;
import java.awt.*;

public class JbuttonConfigs {

    public JbuttonConfigs(){
    }

    public void jButtonPattern(JButton jButton){
        jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jButton.setPreferredSize(new Dimension(180, 40));
        jButton.setMaximumSize(new Dimension(200, 40));
        jButton.setBackground(Color.darkGray);
    }

    public void jButtonMouseListener(JButton jButton){
        jButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton.setBackground(new Color(65, 105, 225));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton.setBackground(Color.darkGray);
            }
        });
    }

    public void jButtonCustomColors(JButton jButton){
        jButton.setBackground(new Color(30, 144, 255));
        jButton.setForeground(Color.WHITE);
        jButton.setFocusPainted(false);
        jButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
}
