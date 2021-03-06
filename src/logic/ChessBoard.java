
/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    Main logic for the chess board is done in this class
Board eval us done using: https://chessprogramming.wikispaces.com/Evaluation
 */

package logic;

import helper.Position;
import pieces.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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
    public void createWinningGame(){
        createEmptyGameBoard();
        gameBoard[0][1].setPiece(new Rook(ColorType.BLACK, this));
        gameBoard[7][1].setPiece(new Rook(ColorType.BLACK, this));
        gameBoard[1][0].setPiece(new Knight(ColorType.BLACK, this));
        gameBoard[6][0].setPiece(new Knight(ColorType.BLACK, this));
        gameBoard[2][0].setPiece(new Bishop(ColorType.BLACK, this));
        gameBoard[5][0].setPiece(new Bishop(ColorType.BLACK, this));
        gameBoard[3][0].setPiece(new Queen(ColorType.BLACK, this));
        gameBoard[4][0].setPiece(new King(ColorType.BLACK, this));
        //set the pawns for both colors
        gameBoard[0][3].setPiece(new Pawn(ColorType.BLACK, this));
        gameBoard[0][6].setPiece(new Pawn(ColorType.WHITE, this));

        gameBoard[1][1].setPiece(new Pawn(ColorType.BLACK, this));
        gameBoard[1][6].setPiece(new Pawn(ColorType.WHITE, this));

        gameBoard[2][1].setPiece(new Pawn(ColorType.BLACK, this));
        gameBoard[2][6].setPiece(new Pawn(ColorType.WHITE, this));

        gameBoard[3][1].setPiece(new Pawn(ColorType.BLACK, this));
        gameBoard[3][6].setPiece(new Pawn(ColorType.WHITE, this));

        gameBoard[4][3].setPiece(new Pawn(ColorType.BLACK, this));
        gameBoard[4][6].setPiece(new Pawn(ColorType.WHITE, this));

        gameBoard[5][6].setPiece(new Pawn(ColorType.WHITE, this));

        gameBoard[6][1].setPiece(new Pawn(ColorType.BLACK, this));
        gameBoard[6][6].setPiece(new Pawn(ColorType.WHITE, this));

        gameBoard[7][1].setPiece(new Pawn(ColorType.BLACK, this));
        gameBoard[7][6].setPiece(new Pawn(ColorType.WHITE, this));

        gameBoard[7][3].setPiece(new Pawn(ColorType.BLACK, this));
        gameBoard[7][5].setPiece(new Pawn(ColorType.WHITE, this));

        gameBoard[0][7].setPiece(new Rook(ColorType.WHITE, this));
        gameBoard[7][7].setPiece(new Rook(ColorType.WHITE, this));
        gameBoard[1][7].setPiece(new Knight(ColorType.WHITE, this));
        gameBoard[6][7].setPiece(new Knight(ColorType.WHITE, this));
        gameBoard[2][7].setPiece(new Bishop(ColorType.WHITE, this));
        gameBoard[2][4].setPiece(new Bishop(ColorType.WHITE, this));
        gameBoard[5][1].setPiece(new Queen(ColorType.WHITE, this));
        gameBoard[4][7].setPiece(new King(ColorType.WHITE, this));

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
    public boolean validMove(ChessBoard chessBoard, Move move, ColorType colorType){
        List<Move> allMoves = getAllMoves(colorType,chessBoard);

        for (int i = 0; i < allMoves.size(); i++) {
            if(allMoves.get(i).getStart().equals(move.getStart()) && allMoves.get(i).getEnd().equals(move.getEnd())){
                return true;
            }
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
        if (getPieceType(move.getStart()) == PieceType.KING && getPieceType(move.getEnd()) == PieceType.ROOK) {
            System.out.println("Castling");
//            if (move.getEnd().getCol() == 6) movePiece(new Position(7, move.getEnd().getRow()),
//                    new Position(5, move.getEnd().getRow()));
//            else if (move.getEnd().getCol() == 2) {
//                movePiece(new Position(0, move.getEnd().getRow()),
//                        new Position(3, move.getEnd().getRow()));
//            }
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
    public List<Move> getEnemyMoves(ColorType colorType, ChessBoard chessBoard){
        List<Move> enemyMoves = new ArrayList<>();
        List<Position> possibleMoves;
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard.containsPiece(new Position(i, j)) && chessBoard.getColorType(new Position(i, j)) != colorType) {
                    Position tilePosition = new Position(i,j);
                    if (chessBoard.getPieceType(tilePosition) != PieceType.KING) {
                        possibleMoves = chessBoard.getPiece(tilePosition).getMoves(tilePosition);
                        for (Position position : possibleMoves) {
                            enemyMoves.add(new Move(tilePosition, position));
                        }
                    }
                }
            }
        }
        return enemyMoves;
    }
    public List<Move> getAllMoves(ColorType colorType, ChessBoard chessBoard){
        List<Move> enemyMoves = new ArrayList<>();
        List<Position> possibleMoves;
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard.containsPiece(new Position(i, j)) && chessBoard.getColorType(new Position(i, j)) == colorType) {
                    Position tilePosition = new Position(i,j);
                        possibleMoves = chessBoard.getPiece(tilePosition).getMoves(tilePosition);
                        for (Position position : possibleMoves) {
                            enemyMoves.add(new Move(tilePosition, position));
                        }
                }
            }
        }
        return enemyMoves;
    }
    public boolean colorCanAttackKing(ColorType pieceColorType, ChessBoard chessBoard, Position p) {
        List<Move> possibleMoves = getEnemyMoves(pieceColorType,chessBoard);
        for (Move mv: possibleMoves) {
            if(mv.getEnd().equals(p))
                return true;
        }
        return false;
    }

    public boolean kingInCheck(ChessBoard chessBoard, ColorType colorType){
        for (int i = 0 ; i < 8 ; i++)
            for (int j = 0; j < 8; j++)
                if (chessBoard.containsPiece(i, j) && chessBoard.getPieceType(new Position(i, j)) == PieceType.KING) {
                    if(colorCanAttackKing(colorType, chessBoard, new Position(i, j)))
                        return true;
                }
        return false;
    }

    public int getKingMoves(ChessBoard chessBoard, ColorType colorType) {
        for (int i = 0 ; i < 8 ; i++)
            for (int j = 0; j < 8; j++)
                if (chessBoard.containsPiece(i, j) &&
                        chessBoard.getPieceType(new Position(i, j)) == PieceType.KING &&
                        chessBoard.getColorType(new Position(i,j)) == colorType) {
                    return chessBoard.getPiece(new Position(i,j)).getMoves(new Position(i,j)).size();
                }
        return 100;
    }
}
