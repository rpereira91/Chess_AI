package GUI;
import javax.swing.*;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    Check Logic
 */
public class Check extends JFrame {
    public Check(boolean human){
        super("Check");
        //JPanel winPanel = new JPanel();
        JLabel winLabel;
        //if the human is in check it displays a message, same for if it's the ai that's in check
        if(human)
            winLabel = new JLabel("You are in check", SwingConstants.CENTER);
        else
            winLabel = new JLabel("AI in check", SwingConstants.CENTER);
        setSize(200,100);
        add(winLabel);
        setLayout(new FlowLayout());
        add(winLabel);
        setVisible(true);
    }
}
