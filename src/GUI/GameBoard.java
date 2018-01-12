/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    This class contains the main logic for the gui implementation of the game
 */
package GUI;

import helper.Position;
import logic.ChessBoard;
import logic.MinMaxLogic;
import logic.Move;
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
    //used for selection
    GameTile previousTile;
    List<Position> possiblePositions;
    //used to identify the human or computer player
    boolean playerTurn = true;
    MinMaxLogic computer;
    ColorType humanColor;
    //user settings
    boolean PVP;
    int depth;

    public GameBoard(int depth, boolean PVP) {
        setLayout(new GridLayout(size, size));
        setPreferredSize(dimension);
        tiles = new GameTile[size][size];
        chessBoard = new ChessBoard();
        chessBoard.initilizeGameBoard();
        //used for testing a winning game
//        chessBoard.createWinningGame();
        computer = new MinMaxLogic(ColorType.BLACK, depth);
        humanColor = ColorType.WHITE;
        this.PVP = PVP;
        this.depth = depth;
        initTiles();
        setVisible(true);

    }
    //this is the AI component for the game, if it's not the players turn it can decide the move it wants to make
    private void playGame(){
        if(!playerTurn){
            //gets the move from the Min Max logic class
            Move move = computer.getNextMove(chessBoard);
            movePieces(move.getStart(),move.getEnd());
            //makes the move and sets the turn to true
            playerTurn = true;
        }
        drawBoard();
    }
    //starts off the tiles and adds a mouse listener to each tile
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
<<<<<<< Updated upstream

    public void tileClickHandler(GameTile tile) {
        Position tilePosition = tile.position;
<<<<<<< HEAD
//        if(kingInCheck(humanColor) && (chessBoard.getPieceType(tilePosition) != PieceType.KING) || chessBoard.getPieceType(previousTile.position) != PieceType.KING){
//            resetSelectedTilesBackground();
//            previousTile = null;
//            return;
//        }
        if (chessBoard.containsPiece(tilePosition) && (chessBoard.getColorType(tilePosition) != computer.getAIColorType()
=======
//Handles tile clicks
    public void tileClickHandler(GameTile tile){
        Position tilePosition = tile.position;
        if (chessBoard.containsPiece(tilePosition) && (chessBoard.getColorType(tilePosition) == humanColor
>>>>>>> Stashed changes
                || chessBoard.getPieceType(tilePosition) == PieceType.ROOK)){
            // We just clicked on a non empty tile and last click was on a non empty tile
            if (previousTile != null){
                // Clicked on the same tile, deselect it
                if (previousTile == tile){
                    resetSelectedTilesBackground();
                    previousTile = null;
                    return;
                }else{
                    if(chessBoard.getColorType(tilePosition) != chessBoard.getColorType(previousTile.position)&&
                            chessBoard.validMove(chessBoard,new Move(previousTile.position,tilePosition),humanColor)) {
                        movePieces(previousTile.position,tilePosition);
                        playerTurn = false;
                        return;
                    }

<<<<<<< Updated upstream
=======
        if (chessBoard.containsPiece(tilePosition)) {
            // We are selecting tile for first time and it's our color
            if (previousTile == null && chessBoard.getColorType(tilePosition) != computer.getAIColorType()) {
=======
                }

            // We just clicked on a non empty tile and it's our first click
            }else{
                if(chessBoard.getPieceType(tilePosition) == PieceType.ROOK){
                    resetSelectedTilesBackground();
                    previousTile = null;
                    return;
                }
                // Selecting a piece
>>>>>>> Stashed changes
                previousTile = tile;
                possiblePositions = chessBoard.getTile(tilePosition).getPiece().getMoves(tilePosition);
                for (Position position : possiblePositions) {
                    positionToGameTile(position).selectTile();
                }
                tile.selectTile();
            } else {
                // we had something selected previously
                if (tile == previousTile){
                    resetSelectedTilesBackground();
                    previousTile = null;
                    return;
>>>>>>> bug_fixes_movement
                }

                if (chessBoard.getColorType(tilePosition) != computer.getAIColorType()){
                    // attack
                    if (validMove(previousTile.position, tilePosition)){
                        ColorType currentPieceColor = chessBoard.getColorType(tilePosition);
                        ColorType prevPieceColor = chessBoard.getColorType(previousTile.position);
                        if (prevPieceColor != currentPieceColor) {
                            playerMovePieces(previousTile.position, tilePosition);
                        }else{
                            // TODO: Castling move would be called here, right now the king just eats up rook
                        }
                    }
                }
            }
        }else{
<<<<<<< HEAD
            // We clicked on an empty tile and our last click was on a tile that has a piece
            if (previousTile != null && chessBoard.validMove(chessBoard,new Move(previousTile.position,tilePosition),humanColor)){
                // If this click is legal, we move and reset previousTile.
                movePieces(previousTile.position, tilePosition);
                playerTurn = false;
                // Reset select state
                resetSelectedTilesBackground();
                previousTile = null;
=======
            if (previousTile != null && validMove(previousTile.position, tilePosition)){
                playerMovePieces(previousTile.position, tilePosition);
>>>>>>> bug_fixes_movement
            }
        }
<<<<<<< Updated upstream
    }

    private boolean validMove(Position start, Position end){
        return chessBoard.getTile(start).getPiece().getMoves(start).contains(end);
    }

    public void movePieces(Position start, Position end){
        chessBoard.moveSpecialPiece(new Move(start, end));
        if (end.getRow() == 0 || end.getRow() == 7) {
            if (chessBoard.getPieceType(end) == PieceType.PAWN) {
                if (playerTurn)
                    pawnPromo(end);
                else
                    chessBoard.replacePiece(end, PieceType.QUEEN);
            }
        }
        drawBoard();
=======
        //if it's pvp switch players if its not let the computer go
        if(PVP){
            switchPlayers();
        }else{
            playGame();
        }
    }
    //logic behind moving the pieces
    public void movePieces(Position start, Position end){
            chessBoard.moveSpecialPiece(new Move(start, end));
            //pawn promo, ask the user what piece they want to update to, if its the AI simply promote it to a queen
            if (end.getRow() == 0 || end.getRow() == 7) {
                if (chessBoard.getPieceType(end) == PieceType.PAWN) {
                    if(playerTurn)
                        pawnPromo(end);
                    else
                        chessBoard.replacePiece(end, PieceType.QUEEN);
                }
            }
            drawBoard();
            //check if the move was a check mate move
>>>>>>> Stashed changes
        checkCheckMate();
    }

    private void playerMovePieces(Position start, Position end){
        movePieces(start, end);
        resetSelectedTilesBackground();
        previousTile = null;
        playerTurn = false;
        playGame();
    }
    //switches players for PVP
    public void switchPlayers(){
        humanColor = humanColor == ColorType.WHITE ? ColorType.BLACK : ColorType.WHITE;
    }
    //resets the selected tiles
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
    //gets the game tile based on a position passed
    public GameTile positionToGameTile(Position position){
        return tiles[position.getCol()][position.getRow()];
    }
    //redraws the board
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

    //check mate logic
    public void checkCheckMate(){
        //if the king is in check and there is less than one move for the king
        //display the win or lose prompt
        if(kingInCheck(humanColor)) {
            new Check(true);
            if (chessBoard.getKingMoves(chessBoard, humanColor) < 1) {
                new WinState(false);
            }
        }
        if(kingInCheck(computer.getAIColorType())) {
            new Check(false);
            if (chessBoard.getKingMoves(chessBoard, computer.getAIColorType()) < 1) {
                new WinState(true);
            }
        }
    }
    //checks if the king is in check
    public boolean kingInCheck(ColorType colorType){
        if(chessBoard.kingInCheck(chessBoard,colorType)) return true;
        return false;
    }
}
