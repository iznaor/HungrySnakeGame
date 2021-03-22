import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;


public class MainSnakeGameFrame extends JFrame implements ActionListener {
    MainSnakeGamePanel game;
    JButton resetButton;

    MainSnakeGameFrame(){
        this.add(new MainSnakeGamePanel());
        this.setTitle("Hungry Snake Game");


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);

        game = new MainSnakeGamePanel();


        resetButton = new JButton();
        resetButton.setText("RESET");
        resetButton.setSize(100,50);
        resetButton.setLocation(0,0);
        resetButton.setBackground(Color.gray);
        resetButton.setForeground(Color.yellow);
        resetButton.addActionListener(this);


        this.add(resetButton);
        this.add(game);

        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==resetButton){
            this.remove(game);
            game = new MainSnakeGamePanel();
            this.add(game);
            game.requestFocusInWindow();
            SwingUtilities.updateComponentTreeUI(this);
        }
    }
}
