/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    This is the first frame the user sees, it lets the user pick between Player vs AI
    or player vs player. It also lets the player select the ply depth, the deeper the search tree goes
    the harder it will be as it will think more moves into the future. Once the user selects those, it moves
    on to the game frame which starts up the game
 */
package GUI;

import javax.swing.*;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class StartUpFrame extends JFrame implements ItemListener,ActionListener {
    GameFrame gameFrame;
    JComboBox plyDepthsList;
    //radio buttons
    JRadioButton PVP;
    JRadioButton PVAI;

    public StartUpFrame(){
        super("Chess Start Up");
        setSize(400,200);
        JPanel gameModePanel = new JPanel();
        String[] list = {"1","2","3","4","5","6"};
        //"Select Game Mode" Component
        JLabel gameModeLabel = new JLabel("Select the game mode: ");
        JPanel gameModeRadioButtons = new JPanel(new FlowLayout());
        ButtonGroup gameButtons = new ButtonGroup();
        //add the radio buttons
        PVP = new JRadioButton("PVP");
        PVAI = new JRadioButton("PVAI");
        gameButtons.add(PVP);
        gameButtons.add(PVAI);
        gameModeRadioButtons.add(PVP);
        gameModeRadioButtons.add(PVAI);
        gameModePanel.add(gameModeLabel, BorderLayout.NORTH);
        gameModePanel.add(gameModeRadioButtons, BorderLayout.SOUTH);
        add(gameModePanel, BorderLayout.NORTH);

        //"Ply Depth Selection" Component
        JPanel plyDepthPanel = new JPanel();
        JLabel plyLabel = new JLabel("Select Ply Depth: ");
        plyDepthsList = new JComboBox(list);
        plyDepthsList.setBounds(100,50,100,20);
        plyDepthPanel.add(plyLabel);
        plyDepthPanel.add(plyDepthsList);
        add(plyDepthPanel,BorderLayout.CENTER);

        //Submit Button Component
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    //How does this work rip cry
    //TO DO: Delete
    //http://roughrecord.blogspot.ca/2012/04/create-combobox-using-jframe.html
    public void itemStateChanged(ItemEvent e){
    }
    //once the user selects start the game takes off based on the user specs
    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
        if(PVP.isSelected())
            gameFrame = new GameFrame(true, plyDepthsList.getSelectedIndex() + 1);
        else
            gameFrame = new GameFrame(false, plyDepthsList.getSelectedIndex() + 1);
    }
}
