package GUI;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    Dimension dimension = new Dimension(600, 600);

    public GameFrame(){
        super("Chess AI");
        setSize(dimension);
        createJMenuBar();
        setVisible(true);
    }

    private void createJMenuBar(){
        JMenuBar bar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        fileMenu.add(exit);
        bar.add(fileMenu);
        setJMenuBar(bar);
    }
}
