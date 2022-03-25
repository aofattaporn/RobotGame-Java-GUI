package mainGame;

import javax.swing.*;
import java.awt.*;

public class Window {

    public Window(int width, int height, String name, Game game) {

        JFrame frame = new JFrame(name);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

//        JFrame x = new JFrame(name);
//        x.setPreferredSize(new Dimension(width, height));
//        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        x.setVisible(true);


    }

}
