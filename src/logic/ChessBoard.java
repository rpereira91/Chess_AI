/*
Board eval us done using: https://chessprogramming.wikispaces.com/Evaluation
 */

package logic;

import helper.Position;
import pieces.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChessBoard {
    //the game gameBoard
    private Tile[][] gameBoard;
    //the size of the game gameBoard, it's normally 8x8
    public final int SIZE = 8;
    public ChessBoard(){
        gameBoard = new Tile[SIZE][SIZE];
    }
    //creates an empty game gameBoard with no pieces on it
    public void createEmptyGameBoard(){
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                gameBoard[x][y] = new Tile();
            }

        }
    }
    //create a game gameBoard with pieces on it
    public void initilizeGameBoard(){
        createEmptyGameBoard();
        //set the black pieces
        gameBoard[0][0].setPiece(new Rook(ColorType.BLACK, this));
        gameBoard[7][0].setPiece(new Rook(ColorType.BLACK, this));
        gameBoard[1][0].setPiece(new Knight(ColorType.BLACK, this));
        gameBoard[6][0].setPiece(new Knight(ColorType.BLACK, this));
        gameBoard[2][0].setPiece(new Bishop(ColorType.BLACK, this));
        gameBoard[5][0].setPiece(new Bishop(ColorType.BLACK, this));
        gameBoard[3][0].setPiece(new Queen(ColorType.BLACK, this));
        gameBoard[4][0].setPiece(new King(ColorType.BLACK, this));
        //set up the white pieces
        gameBoard[0][7].setPiece(new Rook(ColorType.WHITE, this));
        gameBoard[7][7].setPiece(new Rook(ColorType.WHITE, this));
        gameBoard[1][7].setPiece(new Knight(ColorType.WHITE, this));
        gameBoard[6][7].setPiece(new Knight(ColorType.WHITE, this));
        gameBoard[2][7].setPiece(new Bishop(ColorType.WHITE, this));
        gameBoard[5][7].setPiece(new Bishop(ColorType.WHITE, this));
        gameBoard[3][7].setPiece(new Queen(ColorType.WHITE, this));
        gameBoard[4][7].setPiece(new King(ColorType.WHITE, this));
        //set the pawns for both colors
        for (int i = 0; i < 8; i++) {
            gameBoard[i][1].setPiece(new Pawn(ColorType.BLACK, this));
            gameBoard[i][6].setPiece(new Pawn(ColorType.WHITE, this));

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
    public PieceType getPieceType(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()].getPiece().getPieceType();
        }
        return null;
    }
    public Piece getPiece(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()].getPiece();
        }
        return null;
    }
    //gets the color of the piece at the Position
    public ColorType getColorType(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()].getPiece().getPieceColorType();
        }
        return null;
    }

    public ColorType getColorType(int col, int row){
        if (containsPiece(col, row)){
            return gameBoard[col][row].getPiece().getPieceColorType();
        }
        return null;
    }

    public Tile getTile(int col, int row){
        return gameBoard[col][row];
    }

    public Tile getTile(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()];
        }
        return null;
    }

    public String getImage(Position position){
        if(getPieceType(position) == PieceType.PAWN){
            if(getColorType(position) == ColorType.WHITE)
                return "WP";
            else
                return "BP";
        }
        if(getPieceType(position) == PieceType.KNIGHT){
            if(getColorType(position) == ColorType.WHITE)
                return "WN";
            else
                return "BN";
        }
        if(getPieceType(position) == PieceType.BISHOP){
            if(getColorType(position) == ColorType.WHITE)
                return "WB";
            else
                return "BB";
        }
        if(getPieceType(position) == PieceType.ROOK){
            if(getColorType(position) == ColorType.WHITE)
                return "WR";
            else
                return "BR";
        }
        if(getPieceType(position) == PieceType.KING){
            if(getColorType(position) == ColorType.WHITE)
                return "WK";
            else
                return "BK";
        }
        if(getPieceType(position) == PieceType.QUEEN){
            if(getColorType(position) == ColorType.WHITE)
                return "WQ";
            else
                return "BQ";
        }
        return null;
    }
    //takes the piece off the tile
    public Piece removePiece(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()].takePiece();
        }
        return null;
    }
    //checks the passed Position value to see if it has a piece
    public boolean containsPiece(Position position){
        return gameBoard[position.getCol()][position.getRow()].tileOccupied();
    }

    public boolean containsPiece(int col, int row){
        return gameBoard[col][row].tileOccupied();
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
    public void movePiece(Position start, Position end){
        gameBoard[end.getCol()][end.getRow()].setPiece(gameBoard[start.getCol()][start.getRow()].takePiece());
    }
    //replaces the passed position with a new piece
    public void replacePiece(Position p, PieceType pieceType){
        if(containsPiece(p)){
            ColorType colorType = gameBoard[p.getCol()][p.getRow()].takePiece().getPieceColorType();
            if(pieceType == PieceType.PAWN){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Pawn(colorType, this));
            }else if(pieceType == PieceType.BISHOP){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Bishop(colorType, this));
            }else if(pieceType == PieceType.KNIGHT){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Knight(colorType, this));
            }else if(pieceType == PieceType.ROOK){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Rook(colorType, this));
            }else if(pieceType == PieceType.QUEEN){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Queen(colorType, this));
            }
        }
    }




    public boolean colorCanAttackKing(ColorType colorType, Position kingPosition) {
        //run through the entire gameBoard
        List<Position> allMoves;
        Position tempPosition;
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                if(containsPiece(i,j) && getColorType(i,j) != colorType){
                    tempPosition = new Position(i,j);
                    allMoves = getPiece(tempPosition).getMoves(tempPosition);
                    for (Position pos: allMoves){
                        if(pos.equals(kingPosition)){
                            System.out.println("Yo");
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }

    //checks to see if a particular move from one position to another is legal
    public boolean isLegalMove(Position start, Position end){
        if(containsPiece(start)) {
            if(gameBoard[start.getCol()][start.getRow()].getPiece().legalMove(start,end))
                return true;
        }
        return false;
    }

    //this handles castling and pawn moving for the first time
    public Piece moveSpecialPiece ( Move move) {
        //position variables to make it a bit easier when looking up moves
        Position moveStart = move.getStart();
        Position moveEnd = move.getEnd();
        //tiles from the start to the end
        Tile startTile = gameBoard[moveStart.getCol()][moveStart.getRow()];
        Tile endTile = gameBoard[moveEnd.getCol()][moveEnd.getRow()];
        //pieces from the start to the end
        Piece startPiece = startTile.getPiece();
        Piece endPiece = null;
        //if the start piece is null, there's no way for a move to be made
        if (startPiece == null) {
            return null;
        }
        //if the pawn is moving for the first time and its moving twice set the first move to true so it can't move twice again
        if (startPiece.getPieceType() == PieceType.PAWN) {
            startPiece.setFirstMove(false);
            //if the rook or king is moving for the first time set that piece to already moved, so it can't be used for castling
        } else if (startPiece.getPieceType() == PieceType.ROOK || startPiece.getPieceType() == PieceType.KING) {
            if (startPiece.firstMove()) startPiece.setFirstMove(false);

        }
        //if the end piece is occupied get the piece
        if (endTile.tileOccupied()) {
            endPiece = endTile.takePiece();
        }
        endTile.setPiece(startTile.takePiece());

        //castle the pieces if if's a castling move
        if (getPieceType(move.getEnd()) == PieceType.KING &&
                Math.abs(move.getEnd().getCol()-move.getStart().getCol()) == 2) {
            if (move.getEnd().getCol() == 6) movePiece(new Position(7, move.getEnd().getRow()),
                    new Position(5, move.getEnd().getRow()));
            else if (move.getEnd().getCol() == 2) {
                movePiece(new Position(0, move.getEnd().getRow()),
                        new Position(3, move.getEnd().getRow()));
            }
        }
        return endPiece;
    }
    // undoes the passed move and any special moves
    public void undoSpecialMove ( Move move, Piece piece, boolean firstMove) {
        //reverse the move
        movePiece(move.getEnd(), move.getStart());
        //if there is a piece place the piece down at the destination of the move
        if (piece != null) setPiece(move.getEnd(), piece);
        //set the first move if there is a piece at the start position
        if (firstMove && containsPiece(move.getStart()))
            gameBoard[move.getStart().getCol()][move.getStart().getRow()].getPiece().madeFirstMove();

        // undo castling if castling has happened
        if (getPieceType(move.getStart()) == PieceType.KING &&
                gameBoard[move.getStart().getCol()][move.getStart().getRow()].getPiece().firstMove() &&
                move.getStart().getCol() == 4) {
            if (move.getStart().getCol() - move.getEnd().getCol() == -2 &&
                    containsPiece(new Position(move.getEnd().getCol() - 1, move.getEnd().getRow())) &&
                    getPieceType(new Position(move.getEnd().getCol() - 1, move.getEnd().getRow())) == PieceType.ROOK &&
                    gameBoard[move.getEnd().getCol() - 1][move.getStart().getRow()].getPiece().firstMove()) {
                setPiece(new Position(move.getEnd().getCol() + 1, move.getEnd().getRow()),
                        removePiece(new Position(move.getEnd().getCol() - 1, move.getEnd().getRow())));
            } else if (move.getStart().getCol() - move.getEnd().getCol() == 2 &&
                    containsPiece(new Position(move.getEnd().getCol() + 1, move.getEnd().getRow())) &&
                    getPieceType(new Position(move.getEnd().getCol() + 1, move.getEnd().getRow())) == PieceType.ROOK&&
                    gameBoard[move.getEnd().getCol() + 1][move.getStart().getRow()].getPiece().firstMove()) {
                setPiece(new Position(move.getEnd().getCol() - 2, move.getEnd().getRow()),
                        removePiece(new Position(move.getEnd().getCol() + 1, move.getEnd().getRow())));
            }
        }

    }

}
