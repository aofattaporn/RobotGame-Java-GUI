package net;


import mainGame.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class MainWindow {

    private JFrame frame;
    private Game client;


    public MainWindow(String name, int width, int height) {

        this.frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(Color.BLACK);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        JLabel label = new JLabel("ROBOT GAME");
        label.setForeground(Color.ORANGE);
        label.setFont(new Font(label.getName(), Font.BOLD, 50));
        label.setBounds(230, 0, 600, 300);
        frame.add(label);
        label.setVisible(true);

        JButton button = ButtonStart();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Socket socket = null;
                try {
                    socket = new Socket("localhost", 9999);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    client = new Game(socket);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                client.listenForMessage();

                frame.setVisible(false);
                frame.dispose();

            }
        });


    }

    private JButton ButtonStart() {

        // button
        JButton button = new JButton("Start game");
        button.setBounds(300, 300, 200, 50);
        frame.add(button);
        button.setVisible(true);

        return button;

    }

    private void ButtonEx() {

        // button
        JButton buttonEx = new JButton("Exit game");
        buttonEx.setBounds(300, 500, 200, 50);
        frame.add(buttonEx);
        buttonEx.setVisible(true);

    }

    public static void main(String[] args) {
        new MainWindow("Main window", 800, 650);
    }
}
