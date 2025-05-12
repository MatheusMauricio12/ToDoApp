package com.matheusiowa12.entities;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ButtonUi {

    public ButtonUi(){
    }

    public void jButtonPattern(ArrayList<JButton> jButtons) {
        for (JButton button : jButtons) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setFont(new Font("Segoe UI", Font.BOLD, 16));
            button.setPreferredSize(new Dimension(180, 40));
            button.setMaximumSize(new Dimension(200, 40));
            button.setBackground(Color.darkGray);
        }
    }

    public void jButtonMouseListener(ArrayList<JButton> jButtons){
        for(JButton jbutton : jButtons) {
            jbutton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    jbutton.setBackground(new Color(65, 105, 225));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    jbutton.setBackground(Color.darkGray);
                }
            });
        }
    }

    public void jButtonCustomColors(JButton jButton){
        jButton.setBackground(new Color(30, 144, 255));
        jButton.setForeground(Color.WHITE);
        jButton.setFocusPainted(false);
        jButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public void spacingMenuPanel(ArrayList<JButton> jButtons, JPanel menuPanel){
        for(JButton jButton : jButtons){
            menuPanel.add(jButton);
            menuPanel.add(Box.createVerticalStrut(10));
        }
    }
}
