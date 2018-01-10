package GUI;

import javax.swing.*;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class StartUpFrame extends JFrame {

    public StartUpFrame(){
        super("Chess Start Up");
        setSize(400,200);
        JPanel gameModePanel = new JPanel();

        //"Select Game Mode" Component
        JLabel gameModeLabel = new JLabel("Select the game mode");
        JPanel gameModeRadioButtons = new JPanel(new FlowLayout());
        ButtonGroup gameButtons = new ButtonGroup();
        JRadioButton PVP = new JRadioButton("PVP");
        JRadioButton PVAI = new JRadioButton("PVAI");
        gameButtons.add(PVP);
        gameButtons.add(PVAI);
        gameModeRadioButtons.add(PVP);
        gameModeRadioButtons.add(PVAI);
        gameModePanel.add(gameModeLabel, BorderLayout.NORTH);
        gameModePanel.add(gameModeRadioButtons, BorderLayout.SOUTH);
        add(gameModePanel, BorderLayout.NORTH);

        //"Ply Depth Selection" Component
        JPanel plyDepthPanel = new JPanel();
        JLabel plyLabel = new JLabel("Select Ply Depth");
        JTextField plyDepth = new JTextField(5);
        plyDepthPanel.add(plyLabel);
        plyDepthPanel.add(plyDepth);
        add(plyDepthPanel,BorderLayout.CENTER);

        //Submit Button Component
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();

        setVisible(true);

    }

}
