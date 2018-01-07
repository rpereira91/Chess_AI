package GUI;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    Dimension dimension = new Dimension(600, 600);

    public GameFrame(){
        super("Chess AI");
        setSize(dimension);
        createJMenuBar();
        add(new GameBoard(), BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createJMenuBar(){
        JMenuBar bar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem restart = new JMenuItem("Restart");
        JMenuItem exit = new JMenuItem("Exit");
        JMenu settings = new JMenu("Settings");
        JMenuItem player = new JMenuItem("Player Style");
        JMenuItem set_depth = new JMenuItem("Set Depth");


        exit.addActionListener(e -> System.exit(0));
        restart.addActionListener(e -> restartGame());

        fileMenu.add(restart);
        fileMenu.add(exit);
        settings.add(player);
        settings.add(set_depth);
        bar.add(fileMenu);
        bar.add(settings);
        setJMenuBar(bar);
    }
    private void restartGame(){
        setVisible(false);
        dispose();
        new GameFrame();
    }
}
