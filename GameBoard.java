import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameBoard extends JFrame {
    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;

    public GameBoard(){
        //size of the game board
        Dimension boardSize = new Dimension(600, 600);

        //  Use a Layered Pane for this this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);

        //Add a chess board to the Layered Pane

        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout( new GridLayout(8, 8) );
        chessBoard.setPreferredSize( boardSize );
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
        //populate the board with black and white tiles
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel( new BorderLayout() );
            chessBoard.add( square );

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? Color.black : new Color(255, 248, 211) );
            else
                square.setBackground( i % 2 == 0 ? new Color(255, 248, 211) : Color.black );
        }
        
        for (int i = 8; i <= 15; i++){
            JLabel piece = new JLabel( new ImageIcon("Images/White_Pawn.png") );
            JPanel panel = (JPanel)chessBoard.getComponent(i);
            panel.add(piece);
        }


    }

    public static void main(String[] args) {
        JFrame frame = new GameBoard();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }
}


