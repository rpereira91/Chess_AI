package GUI;
/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    The game frame, it starts up with the params passed into it from the start up frame
 */
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    Dimension dimension = new Dimension(600, 600);
    boolean PVP;
    int depth;

    public GameFrame(boolean PVP, int depth){
        super("Chess AI");
        setSize(dimension);
        createJMenuBar();
        add(new GameBoard(depth, PVP), BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.PVP = PVP;
        this.depth = depth;
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
        StartUpFrame startUpFrame = new StartUpFrame();
    }
}
