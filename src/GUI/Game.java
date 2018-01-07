package GUI;
import helper.Position;
import logic.*;
import pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Game {
    //create frame for the whole game
    private JFrame game;
    private JBoard gameBoard;
    //Dimensions for the game
    static final Dimension FRAME_DIM = (new Dimension(600,600));
    static final Dimension PANNEL_DIM = (new Dimension(400,350));
    static final Dimension TILE_DIM = (new Dimension(10,10));
    //the chess board
    static ChessBoard chessBoard;


    public Game(){
        //init a new chess board
        chessBoard = new ChessBoard();
        this.chessBoard.initilizeGameBoard();
        this.game = new JFrame("Chess AI");
        //set the layout for the chess board
        this.game.setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        fillMenuBar(menuBar);
        this.game.setJMenuBar(menuBar);
        //set the frame dimensions
        this.game.setSize(FRAME_DIM);
        this.gameBoard = new JBoard();
        this.game.add(this.gameBoard, BorderLayout.CENTER);
        this.game.setVisible(true);
    }

    private void fillMenuBar(JMenuBar menuBar) {
        menuBar.add(fillFileMenu());
    }

    private JMenu fillFileMenu() {
        JMenu fileMenu = new JMenu("File");


        JMenuItem openFile = new JMenuItem("Settings");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Butts");
            }
        });
        fileMenu.add(openFile);
        fileMenu.add(exit);
        return fileMenu;
    }
    private class JBoard extends JPanel{
        List<JTile> tiles;

        public JBoard() {
            super(new GridLayout(8,8));
            this.tiles = new ArrayList<>();
            for(int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++) {
                    JTile tile = new JTile(this,new Position(j,i));
                    this.tiles.add(tile);
                    add(tile);
                }
            }
            setPreferredSize(PANNEL_DIM);
//            validate();
        }
        public void redrawJboard(ChessBoard chessBoard){
            //remove all the
//            removeAll();
//            //for each tile on the board
//            for(JTile tile: tiles){
//                tile.redrawJTile(chessBoard);
//                add(tile);
//            }
//            validate();
//            repaint();
        }
    }
    private class JTile extends JPanel{
        Position position;
        Position startTile = null;
        Position endTile = null;
        public JTile(JBoard board, Position position){
            this.position = position;
            setPreferredSize(TILE_DIM);
            setTileColor();
            setImage(chessBoard);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(isRightMouseButton(e)){
                        clearTile();
                    }else if(isLeftMouseButton(e)){
                        if(startTile == null){
                            startTile = position;
                        }
                        else{
                            endTile = position;
                            if(chessBoard.isLegalMove(startTile,endTile)){
                                chessBoard.movePiece(startTile,endTile);
                            }
                        }
                        gameBoard.redrawJboard(chessBoard);
//                        board.redrawJboard(chessBoard);
//                        SwingUtilities.invokeLater(() -> board.redrawJboard(chessBoard));
                    }
                }
            });
            validate();
        }
        public void clearTile(){
            startTile = null;
            endTile = null;
        }
        /*Re-draw the tile*/
        public void redrawJTile(ChessBoard b){
            setTileColor();
            setImage(b);
            validate();
            repaint();
        }

        private void setTileColor() {
            if(position.getColor())
                setBackground(Color.GRAY);
            else
                setBackground(Color.WHITE);
        }
        private void setImage(ChessBoard board){
            //clear out the tile
            this.removeAll();
            if(board.containsPiece(position)){
                try {
                    BufferedImage img = ImageIO.read(new File("Images/" + board.getImage(position) +".png"));
                    this.add(new JLabel(new ImageIcon(img)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
