package minaGame;

import javax.swing.*;
import java.awt.*;

public class HomeWindow {

    JFrame window;
    Container con;
    JPanel titleNamePanel;
    JLabel titleNameLabel;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 28);

    public HomeWindow(int width, int height, String name) {

        window = new JFrame();
        window.setSize(width, height);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);
        window.setVisible(true);
        con = window.getContentPane();

        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100, 100, 600, 150);
        titleNamePanel.setBackground(Color.blue);
        titleNameLabel = new JLabel("ADVENTURE");
        titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);
        
        titleNamePanel.add(titleNameLabel);
        con.add(titleNamePanel);

    }
}