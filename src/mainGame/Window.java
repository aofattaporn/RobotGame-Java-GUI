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
        frame.dispose();
        frame.setVisible(false);
    }
}


