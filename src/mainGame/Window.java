package mainGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

class Window {

    private JFrame frame;
    private JFrame finalWindow;
    private int width, height;

    public Window(int width, int height, String name, Game game) {

        this.frame = new JFrame(name);
        this.width = width;
        this.height = height;

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void closeWindow(){
        createFinalWindow();
    }

    private void createFinalWindow(){
        this.finalWindow = new JFrame();

        this.frame = new JFrame("sd");
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


                System.out.println("final");

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

}


