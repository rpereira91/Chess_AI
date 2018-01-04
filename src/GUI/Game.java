package GUI;
import helper.Position;
import logic.*;
import pieces.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JPanel implements ActionListener {
    JFrame gameWindow;
    static JFrame chessGame;
    JPanel gameBoard;
    JPanel optionPanel;
    static JPanel newGamePannel;
    static JPanel plyOptionPannel;
    static JPanel gameModePannel;
    //this will represent the tiles, uses buttons
    private JButton[][] tileBoard;
    //lable declarations
    JLabel lblCurrentTurn;
    JLabel lblMoveNumber;
    JLabel lblCheck;
    //buttons to start the game
    static private JButton start;
    static private JButton quit;

    ChessBoard game;

    private Player whitePlayer;
    private Player blackPlayer;
    private Player currentPlayer;
    private int moves = 1;
    private boolean inCheck = false;
    private Position from = null, to;
    private boolean getPiece = true;

    public Game(){
        tileBoard = new JButton[8][8];


        start.addActionListener(this);
        quit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }
}
