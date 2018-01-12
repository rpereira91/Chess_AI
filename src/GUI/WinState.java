package GUI;
/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    A window telling the user if they won or lost.
 */
import javax.swing.*;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class WinState extends JFrame {
    public WinState(boolean playerWon){
        super("Win Message");
        //JPanel winPanel = new JPanel();
        JLabel winLabel;
        if(playerWon)
            winLabel = new JLabel("You've won!", SwingConstants.CENTER);
        else
            winLabel = new JLabel("You Lost!", SwingConstants.CENTER);
        setSize(200,100);
        add(winLabel);
        setLayout(new FlowLayout());
        add(winLabel);
        setVisible(true);
    }
}
