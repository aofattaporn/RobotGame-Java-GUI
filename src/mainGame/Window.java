package mainGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    }

}

class MainWinddow{


//    public MainWinddow(String name, int width, int height) {
//
//
//        JFrame frame = new JFrame(name);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//
//        frame.getContentPane().setBackground(Color.BLACK);
//        frame.setPreferredSize(new Dimension(width, height));
//        frame.setMinimumSize(new Dimension(width, height));
//        frame.setMinimumSize(new Dimension(width, height));
//
//        frame.setResizable(false);
//        frame.setLocationRelativeTo(null);
//        frame.pack();
//        frame.setVisible(true);
//
//        // label
//        JLabel label = new JLabel("ROBOT GAME");
//        label.setForeground(Color.ORANGE);
//        label.setFont(new Font(label.getName(), Font.BOLD, 50));
//        label.setBounds(230, 0, 600, 300);
//        frame.add(label);
//        label.setVisible(true);
//
//
//
//
////        // button
//        JButton button = new JButton("Start game");
//        button.setBounds(300, 300, 200, 50);
//        frame.add(button);
//        button.setVisible(true);
//
//        // button
//        JButton buttonEx = new JButton("Exit game");
//        buttonEx.setBounds(300, 500, 200, 50);
//        frame.add(buttonEx);
//        buttonEx.setVisible(true);
//    }
}

class example {

    public static void main (String[] args){
        JFrame frame = new JFrame("Test");
        frame.setVisible(true);
        frame.setSize(500,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        JButton button = new JButton("hello agin1");
        panel.add(button);
        button.addActionListener (new Action1());

        JButton button2 = new JButton("hello agin2");
        panel.add(button2);
        button.addActionListener (new Action2());
    }


    static class Action1 implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            JFrame frame2 = new JFrame("Clicked");
            frame2.setVisible(true);
            frame2.setSize(200,200);
            JLabel label = new JLabel("you clicked me");
            JPanel panel = new JPanel();
            frame2.add(panel);
            panel.add(label);
        }
    }

    static class Action2 implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            JFrame frame3 = new JFrame("OKNO 3");
            frame3.setVisible(true);
            frame3.setSize(200,200);

            JLabel label = new JLabel("kliknales");
            JPanel panel = new JPanel();
            frame3.add(panel);
            panel.add(label);
        }
    }
}
