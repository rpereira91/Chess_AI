package GUI;
/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    The game board class contains the bulk of the logic used to display the game
 */
import helper.Position;
import logic.ChessBoard;
import logic.MinMaxLogic;
import helper.Move;
import pieces.ColorType;
import pieces.Piece;
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
    //starts a new game
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
    //the ai makes the move here
    private void playGame(){
        //if it's not the players turn it makes a move
        if(!playerTurn){
            Move move = computer.getNextMove(chessBoard);
            movePieces(move.getStart(),move.getEnd());
            playerTurn = true;
        }
        drawBoard();
    }
    //starts the tiles with click handlers for each tile
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
    //click handler for each tile
    public void tileClickHandler(GameTile tile) {
        Position tilePosition = tile.position;
        if (chessBoard.containsPiece(tilePosition)) {
            // We are selecting tile for first time and it's our color
            if (previousTile == null) {
                if (chessBoard.getColorType(tilePosition) != computer.getAIColorType()) {
                    previousTile = tile;
                    possiblePositions = chessBoard.getTile(tilePosition).getPiece().getMoves(tilePosition);
                    for (Position position : possiblePositions) {
                        positionToGameTile(position).selectTile();
                    }
                    tile.selectTile();
                }
            } else {
                // we had something selected previously
                if (tile == previousTile){
                    resetSelectedTilesBackground();
                    previousTile = null;
                    return;
                }

                // attack
                if (chessBoard.getColorType(tilePosition) == computer.getAIColorType()){
                    playerMovePieces(previousTile.position, tilePosition);
                }else{
                    Piece prevPiece = chessBoard.getTile(previousTile.position).getPiece();
                    Piece currPiece = chessBoard.getTile(tilePosition).getPiece();
                    //castling
                    if (prevPiece.getPieceType() == PieceType.KING && currPiece.getPieceType() == PieceType.ROOK) {
//                        chessBoard.swapPieces(previousTile.position,tilePosition,chessBoard);
                    }
                }
            }
        }else{
            if (previousTile != null && validMove(previousTile.position, tilePosition)){
                playerMovePieces(previousTile.position, tilePosition);
            }
        }
    }
    //checks if a move is valid
    private boolean validMove(Position start, Position end){
        return chessBoard.getTile(start).getPiece().getMoves(start).contains(end);
    }
    //moves the piece
    public void movePieces(Position start, Position end){
        chessBoard.moveSpecialPiece(new Move(start, end));
        //checks for pawn promo
        if (end.getRow() == 0 || end.getRow() == 7) {
            if (chessBoard.getPieceType(end) == PieceType.PAWN) {
                if (playerTurn)
                    pawnPromo(end);
                else
                    chessBoard.replacePiece(end, PieceType.QUEEN);
            }
        }
        //re-draws the board
        drawBoard();
        checkCheckMate();
    }
    //moves the piece if it's the players turn
    private void playerMovePieces(Position start, Position end){
        movePieces(start, end);
        resetSelectedTilesBackground();
        previousTile = null;
        playerTurn = false;
        playGame();
    }
    //resets the tile background
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
    //redraw the board
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

    //checks to see if the player or AI is in check
    public void checkCheckMate(){
        if(kingInCheck(humanColor)) {
            new Check(true);
            if (chessBoard.getKingMoves(chessBoard, humanColor) < 2) {
                new WinState(false);
            }
        }
        if(kingInCheck(computer.getAIColorType())) {
            new Check(false);
            if (chessBoard.getKingMoves(chessBoard, computer.getAIColorType()) < 2) {
                new WinState(false);
            }
        }
    }
    public boolean kingInCheck(ColorType colorType){
        if(chessBoard.kingInCheck(chessBoard,colorType)) return true;
        return false;
    }
}
