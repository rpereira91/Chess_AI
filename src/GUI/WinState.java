package GUI;
import javax.swing.*;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class WinState extends JFrame {
    public WinState(){
        super("Win Message");
        //JPanel winPanel = new JPanel();
        JLabel winLabel =  new JLabel("You've won!", SwingConstants.CENTER);
        setSize(200,100);
        add(winLabel);
        setLayout(new FlowLayout());
        add(winLabel);
        setVisible(true);
    }
}
