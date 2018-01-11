package GUI;

import helper.Position;
import logic.ChessBoard;
import logic.MinMaxLogic;
import logic.Move;
import pieces.ColorType;
import pieces.PieceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GameBoard extends JPanel {
    int size = 8;
    Dimension dimension = new Dimension(400, 350);
    ChessBoard chessBoard;
    GameTile[][] tiles;
    boolean playerTurn = true;
    GameTile previousTile;
    List<Position> possiblePositions;
    MinMaxLogic computer;
    ColorType humanColor;

    boolean PVP;
    int depth;

    public GameBoard(int depth, boolean PVP) {
        setLayout(new GridLayout(size, size));
        setPreferredSize(dimension);
        tiles = new GameTile[size][size];
        chessBoard = new ChessBoard();
        chessBoard.initilizeGameBoard();
        computer = new MinMaxLogic(ColorType.BLACK, depth);
        humanColor = ColorType.WHITE;
        this.PVP = PVP;
        this.depth = depth;
        initTiles();
        setVisible(true);

    }
    private void playGame(){
        if(!playerTurn){
            Move move = computer.getNextMove(chessBoard);
            movePieces(move.getStart(),move.getEnd());
//            if(chessBoard.containsPiece(move.getEnd()))
//                attackMove(move.getStart(),move.getEnd());
//            else
//                movePieces(move.getStart(),move.getEnd());
            playerTurn = true;
        }
        drawBoard();
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
        if (chessBoard.containsPiece(tilePosition) && chessBoard.getColorType(tilePosition) != computer.getAIColorType()){
            // We just clicked on a non empty tile and last click was on a non empty tile
            if (previousTile != null){
                // Clicked on the same tile, deselect it
                if (previousTile == tile){
                    resetSelectedTilesBackground();
                    previousTile = null;
                    return;
                }else{
                    if(chessBoard.getColorType(tilePosition) != chessBoard.getColorType(previousTile.position) &&
                            chessBoard.isLegalMove(previousTile.position, tilePosition)) {
                        movePieces(previousTile.position,tilePosition);
                        playerTurn = false;
                        return;
                    }

                }

            // We just clicked on a non empty tile and it's our first click
            }else{
                // Selecting a piece
                previousTile = tile;
                possiblePositions = chessBoard.getTile(tilePosition).getPiece().getMoves(tilePosition);
                for (Position position : possiblePositions){
                    positionToGameTile(position).selectTile();
                }

                tile.selectTile();
            }
        }else{
            // We clicked on an empty tile and our last click was on a tile that has a piece
            if (previousTile != null){
                // If this click is legal, we move and reset previousTile.
                movePieces(previousTile.position, tilePosition);
                playerTurn = false;
                // Reset select state
                resetSelectedTilesBackground();
                previousTile = null;
            }
        }
        playGame();
    }
    public void movePieces(Position start, Position end){
        if (chessBoard.isLegalMove(start, end)) {
            chessBoard.moveSpecialPiece(new Move(start, end));
            if (end.getRow() == 0 || end.getRow() == 7) {
                if (chessBoard.getPieceType(end) == PieceType.PAWN) {
                    if(playerTurn)
                        pawnPromo(end);
                    else
                        chessBoard.replacePiece(end, PieceType.QUEEN);
                }
            }
            drawBoard();
        }
        drawBoard();
        checkCheckMate();

    }

    private void resetSelectedTilesBackground(){
        previousTile.resetBackground();
        for (Position position : possiblePositions){
            positionToGameTile(position).resetBackground();
        }
    }
    //handles the pawn promotion
    public void pawnPromo(Position tilePosition){
        JDialog.setDefaultLookAndFeelDecorated(true);
        String[] pieces = {"Queen", "Rook","Bishop","Knight"};
        String defaultPiece = "Queen";
        String pieceSelected = (String) JOptionPane.showInputDialog(null, "What Piece do you want?", "Pawn Promote", JOptionPane.QUESTION_MESSAGE, null, pieces, defaultPiece);
        if(pieceSelected == null || pieceSelected.equals("Queen"))
            chessBoard.replacePiece(tilePosition, PieceType.QUEEN);
        else if(pieceSelected.equals("Rook"))
            chessBoard.replacePiece(tilePosition, PieceType.ROOK);
        else if(pieceSelected.equals("Bishop"))
            chessBoard.replacePiece(tilePosition, PieceType.BISHOP);
        else if(pieceSelected.equals("Knight"))
            chessBoard.replacePiece(tilePosition, PieceType.KNIGHT);

    }

    public GameTile positionToGameTile(Position position){
        return tiles[position.getCol()][position.getRow()];
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


    public void checkCheckMate(){
        if(kingInCheck(humanColor)) {
            new Check(true);
            if (chessBoard.getKingMoves(chessBoard, humanColor) < 1) {
                new WinState(false);
            }
        }
        if(kingInCheck(computer.getAIColorType())) {
            new Check(false);
            if (chessBoard.getKingMoves(chessBoard, computer.getAIColorType()) < 1) {
                new WinState(false);
            }
        }
    }
    public boolean kingInCheck(ColorType colorType){
        if(chessBoard.kingInCheck(chessBoard,colorType)) return true;
        return false;
    }
}
