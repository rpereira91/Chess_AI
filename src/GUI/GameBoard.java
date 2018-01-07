package GUI;

import helper.Position;
import logic.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameBoard extends JPanel {
    int size = 8;
    Dimension dimension = new Dimension(400, 350);
    ChessBoard chessBoard;
    GameTile[][] tiles;

    GameTile previousTile;

    public GameBoard() {
        setLayout(new GridLayout(size, size));
        setPreferredSize(dimension);
        tiles = new GameTile[size][size];
        chessBoard = new ChessBoard();
        chessBoard.initilizeGameBoard();
        initTiles();

        setVisible(true);
    }

    private void initTiles(){
        for (int i=0; i < tiles.length; i++){
            for (int j=0; j < tiles[i].length; j++){
                tiles[j][i] = new GameTile(new Position(j, i));
                final GameTile tile = tiles[j][i];
                tiles[j][i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        tileClickHandler(tile);
                    }
                });

                add(tile);
            }
        }
        drawBoard();
    }

    public void tileClickHandler(GameTile tile){
        Position tilePosition = tile.position;
        if (chessBoard.containsPiece(tilePosition)){
            // We just clicked on a non empty tile and last click was on a non empty tile
            if (previousTile != null){
                // Clicked on the same tile, deselect it
                if (previousTile == tile) return;
                // Logic for attacking

            // We just clicked on a non empty tile and it's our first click
            }else{
                // Selecting a piece
                previousTile = tile;
                tile.selectTile();
            }
        }else{
            // We clicked on an empty tile and our last click was on a tile that has a piece
            if (previousTile != null){
                // If this click is legal, we move and reset previousTile.
                if (chessBoard.isLegalMove(previousTile.position, tilePosition)){
                    chessBoard.movePiece(previousTile.position, tilePosition);
                }

                // Reset select state
                previousTile.resetBackground();

                previousTile = null;
            }
        }
        drawBoard();
    }


    public void drawBoard(){
        for (int i=0; i < tiles.length; i++){
            for (int j=0; j < tiles[i].length; j++){
                GameTile current = tiles[j][i];
                if (chessBoard.containsPiece(current.position)){
                    current.updateTile(chessBoard.getImage(current.position));
                }else{
                    current.updateTile();
                }
            }
        }
        repaint();
    }
}
