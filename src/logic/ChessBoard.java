package logic;

import helper.RowCol;
import pieces.*;

public class ChessBoard {
    //the game board
    private Tile[][] gameBoard;
    //the size of the game board, it's normally 8x8
    public final int SIZE = 8;
    ChessBoard(){
        gameBoard = new Tile[SIZE][SIZE];
    }
    //creates an empty game board with no pieces on it
    public void createEmptyGameBoard(){
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                gameBoard[x][y] = new Tile();
            }

        }
    }
    //create a game board with pieces on it
    public void initilizeGameBoard(){
        createEmptyGameBoard();
        //set the black pieces
        gameBoard[0][0].setPiece(new Rook(ColorType.BLACK));
        gameBoard[7][0].setPiece(new Rook(ColorType.BLACK));
        gameBoard[1][0].setPiece(new Knight(ColorType.BLACK));
        gameBoard[6][0].setPiece(new Knight(ColorType.BLACK));
        gameBoard[2][0].setPiece(new Bishop(ColorType.BLACK));
        gameBoard[5][0].setPiece(new Bishop(ColorType.BLACK));
        gameBoard[3][0].setPiece(new Queen(ColorType.BLACK));
        gameBoard[4][0].setPiece(new King(ColorType.BLACK));
        //set up the white pieces
        gameBoard[0][7].setPiece(new Rook(ColorType.WHITE));
        gameBoard[7][7].setPiece(new Rook(ColorType.WHITE));
        gameBoard[1][7].setPiece(new Knight(ColorType.WHITE));
        gameBoard[6][7].setPiece(new Knight(ColorType.WHITE));
        gameBoard[2][7].setPiece(new Bishop(ColorType.WHITE));
        gameBoard[5][7].setPiece(new Bishop(ColorType.WHITE));
        gameBoard[3][7].setPiece(new Queen(ColorType.WHITE));
        gameBoard[4][7].setPiece(new King(ColorType.WHITE));
        //set the pawns for both colors
        for (int i = 0; i < 8; i++) {
            gameBoard[i][1].setPiece(new Pawn(ColorType.BLACK));
            gameBoard[i][6].setPiece(new Pawn(ColorType.WHITE));

        }
    }
    //checks to see if a particular move from one position to another is legal
    public boolean isLegalMove(RowCol start, RowCol end){
        if(gameBoard[start.getCol()][start.getRow()].tileOccupied())
            if(gameBoard[start.getCol()][start.getRow()].getPiece().legalMove(start,end))
                return true;
        return false;
    }
    //checks the path for each piece type
    public boolean pawnPath(RowCol start, RowCol end){
        //check to see if the pawn is moving in the same column
        if(start.getCol() == end.getCol()){
            //if the piece is moving down and it's the first move check if the destination and the tile in the middle are occpied
            if(start.getRow() - end.getRow() == -2)
                if (!gameBoard[start.getCol()][start.getRow() + 1].tileOccupied()
                        && gameBoard[end.getCol()][end.getRow()].tileOccupied())
                    return true;
            //if the piece is moving up and it's the first move, same logic as above
            else if(start.getRow() - end.getRow() == 2)
                if (!gameBoard[start.getCol()][start.getRow() - 1].tileOccupied()
                        && gameBoard[end.getCol()][end.getRow()].tileOccupied())
                    return true;
            else if(Math.abs(start.getRow()-end.getRow()) == 1)
                if (!gameBoard[end.getCol()][end.getRow()].tileOccupied())
                    return true;
        }
        //then check the diagonal, if the piece is attempting to capture a piece
        else if(Math.abs(start.getCol() - end.getCol()) == 1 && Math.abs(start.getRow() - end.getRow()) == 1){
            if(gameBoard[end.getCol()][end.getRow()].tileOccupied()) return true;
        }
        //if none of these conditions are met, return false
        return false;
    }
    //the knight can jump over pieces so if the move is legal it can move there regardless of pieces in the way
    public boolean knightPath(RowCol start, RowCol end){

        return true;
    }
    public boolean bishopPath(RowCol start, RowCol end){

        return false;
    }
    public boolean rookPath(RowCol start, RowCol end){

        return false;
    }
    public boolean queenPath(RowCol start, RowCol end){

        return false;
    }
    public boolean kingPath(RowCol start, RowCol end){

        return false;
    }
}
