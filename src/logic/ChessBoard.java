package logic;

import helper.Position;
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
    //checks if the move in the Position is on it's first move
    boolean checkFirstMove(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()].getPiece().firstMove();
        }
        return false;
    }
    //gets the cost of the piece
    int getPieceCost(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()].getPiece().getPieceCost();
        }
        return 0;
    }
    //gets the type of piece at the Position
    PieceType getPieceType(Position position){
        if(gameBoard[position.getCol()][position.getRow()].tileOccupied()){
            return gameBoard[position.getCol()][position.getRow()].getPiece().getPieceType();
        }
        return null;
    }
    //gets the color of the piece at the Position
    ColorType getColorType(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()].getPiece().getPieceColorType();
        }
        return null;
    }
    //checks the passed Position value to see if it has a piece
    boolean containsPiece(Position position){
        return gameBoard[position.getCol()][position.getRow()].tileOccupied();
    }
    //if the tile is not occupied it can place down the piece, if it's occupied it doesn't place down the piece
    boolean setPiece(Position position, Piece piece){
        if(!containsPiece(position)){
            gameBoard[position.getCol()][position.getRow()].setPiece(piece);
            return true;
        }
        return false;
    }
    //move a piece from the start to the end, it takes the piece at the start position and moves it to ene end position
    void movePiece(Position start, Position end){
        gameBoard[end.getCol()][end.getRow()].setPiece(gameBoard[start.getCol()][end.getRow()].takePiece());
    }
    //replaces the passed position with a new piece
    void replacePiece(Position p, PieceType pieceType){
        if(containsPiece(p)){
            ColorType colorType = gameBoard[p.getCol()][p.getRow()].takePiece().getPieceColorType();
            if(pieceType == PieceType.PAWN){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Pawn(colorType));
            }else if(pieceType == PieceType.BISHOP){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Bishop(colorType));
            }else if(pieceType == PieceType.KNIGHT){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Knight(colorType));
            }else if(pieceType == PieceType.ROOK){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Rook(colorType));
            }else if(pieceType == PieceType.QUEEN){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Queen(colorType));
            }
        }
    }
    //checks to see if a particular move from one position to another is legal
    public boolean isLegalMove(Position start, Position end){
        if(containsPiece(start))
            if(gameBoard[start.getCol()][start.getRow()].getPiece().legalMove(start,end))
                return true;
        return false;
    }
    //checks the path for each piece type
    public boolean pawnPath(Position start, Position end){
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
                if (!containsPiece(end))
                    return true;
        }
        //then check the diagonal, if the piece is attempting to capture a piece
        else if(Math.abs(start.getCol() - end.getCol()) == 1 && Math.abs(start.getRow() - end.getRow()) == 1){
            if(containsPiece(end)) return true;
        }
        //if none of these conditions are met, return false
        return false;
    }
    //the bishop's path is checked here
    public boolean bishopPath(Position start, Position end){
        //if the piece is moving upwards in the right direction check the path for occupied tiles
        if(start.getCol() < end.getCol() && start.getRow() > end.getRow()) {
            for (int i = start.getCol() + 1; i < end.getCol(); i++) {
                for (int j = start.getRow() - 1; j > end.getRow(); j--) {
                    if(gameBoard[i][j].tileOccupied()) return false;
                }
            }
        }
        //if the piece is moving upwards in the left direction check the path for occupied tiles
        else if (start.getCol() > end.getCol() && start.getRow() > end.getRow()){
            for (int i = start.getCol() - 1; i > end.getCol(); i--) {
                for (int j = start.getRow() - 1; j > end.getRow(); j--) {
                    if(gameBoard[i][j].tileOccupied()) return false;
                }
            }
        }
        //if the piece is moving down and to the right check the path for occupied tiles
        else if(start.getCol() < end.getCol() && start.getRow() < end.getRow()){
            for (int i = start.getCol() + 1; i < end.getCol(); i++) {
                for (int j = start.getRow()+1; j < end.getRow(); j++) {
                    if(gameBoard[i][j].tileOccupied()) return false;
                }
            }
        }
        //if the piece is moving down and to the left check the path for occupied tiles
        else if(start.getCol() > end.getCol() && start.getRow() < end.getRow()){
            for (int i = start.getCol() - 1; i > end.getCol() ; i--) {
                for (int j = start.getRow() + 1; j < end.getRow(); j++) {
                    if(gameBoard[i][j].tileOccupied()) return false;
                }

            }
        }
        //if there aren't any tiles in the path return true
        return true;
    }
    //the rooks path is checked here
    public boolean rookPath(Position start, Position end){
        //if the rook is moving in the same row
        if(start.getRow() == end.getRow() && start.getCol() != end.getCol()){
            //check the right or the left depending on how the piece is moving
            if(start.getCol() < end.getCol()){
                for (int i = start.getCol() + 1; i < end.getCol(); i++) {
                    if(gameBoard[i][start.getRow()].tileOccupied()) return false;
                }
            }else{
                for (int i = start.getCol() - 1; i > end.getCol() ; i--) {
                    if(gameBoard[i][end.getRow()].tileOccupied()) return false;
                }
            }
        }
        //if the rook is moving in the same column
        else if(start.getRow() != end.getRow() && start.getCol() == end.getCol()){
            if(start.getRow() < end.getRow()){
                for (int i = start.getRow() + 1; i < end.getRow(); i++) {
                    if(gameBoard[start.getCol()][i].tileOccupied()) return false;
                }
            }else{
                for (int i = start.getRow() - 1; i > end.getRow() ; i--) {
                    if(gameBoard[start.getCol()][i].tileOccupied()) return false;
                }
            }
        }
        return false;
    }
    //if both the rook's path and bishop's path are clear the path for the queen is also clear
    //since the queen mimics both the rook's and the bishop's path
    public boolean queenPath(Position start, Position end){
        if(rookPath(start,end) && bishopPath(start,end))
            return true;
        return false;
    }
    public boolean kingPath(Position start, Position end){

        return false;
    }
    //universal check for path based on the piece type passed
    boolean checkPiecePath(Position start, Position end, PieceType pieceType){

        if(pieceType == PieceType.PAWN){
            return pawnPath(start, end);
        }else if(pieceType == PieceType.BISHOP){
            return bishopPath(start, end);
        }else if(pieceType == PieceType.KNIGHT){
            //since the knight can jump over pieces the path doesn't really matter
            return true;
        }else if(pieceType == PieceType.ROOK){
            return rookPath(start, end);
        }else if(pieceType == PieceType.QUEEN){
            return queenPath(start, end);
        }else if(pieceType == PieceType.KING){
            return kingPath(start, end);
        }
        return false;
    }
}
