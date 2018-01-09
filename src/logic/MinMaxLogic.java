package logic;

import helper.Position;
import pieces.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class MinMaxLogic {
    private ColorType AIColorType;
    List<Position> possibleMoves = null;
    Move nextMove;
    public MinMaxLogic(ColorType AIColorType) {
        this.AIColorType = AIColorType;
    }
    //gets the color type for the AI
    public ColorType getAIColorType() {
        return AIColorType;
    }
    public Move getNextMove ( ChessBoard chessBoard, boolean playerInCheck ) {

        ChessBoard cloneBoard = copyBoard(chessBoard);
        //Max(cloneBoard, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
        nextMove = getAllMoves(chessBoard).get(0);
        return nextMove;
    }
    //creates a copy of the game board passed to it
    public ChessBoard copyBoard (ChessBoard board) {
        //new blank chess board that's been initialized to a blank board
        ChessBoard newChessBoard = new ChessBoard();
        newChessBoard.createEmptyGameBoard();
        //temp piece and positions
        Position position;
        Piece gamePiece = null;
        //go through each tile and based on what was in the passed game board sets the new game board to that piece
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                position = new Position(i, j);
                if (board.containsPiece(position)) {
                    PieceType tempPiece = board.getPieceType(position);
                    if (tempPiece == PieceType.PAWN) {
                        gamePiece = new Pawn(board.getColorType(position), newChessBoard);

                    } else if (tempPiece == PieceType.ROOK) {
                        gamePiece = new Rook(board.getColorType(position), newChessBoard);

                    } else if (tempPiece == PieceType.KNIGHT) {
                        gamePiece = new Knight(board.getColorType(position), newChessBoard);

                    } else if (tempPiece == PieceType.BISHOP) {
                        gamePiece = new Bishop(board.getColorType(position), newChessBoard);

                    } else if (tempPiece == PieceType.KING) {
                        gamePiece = new King(board.getColorType(position), newChessBoard);

                    } else if (tempPiece == PieceType.QUEEN) {
                        gamePiece = new Queen(board.getColorType(position), newChessBoard);

                    }
                    newChessBoard.setPiece(position, gamePiece);
                }
            }
        }
        return newChessBoard;
    }
    public int evaluateBoard (ChessBoard chessBoard){
        int value = 0;
        double mobility = 0.0;
        int totalPieces = 0;
        /* go through the entire board to get the cost of the pieces,
         * this gives us the material value of */
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                /* If the piece on this position is the same as the color of the current player, increase the
                value of the board, if its not decrease the value
                the mobility is calculated by getting all the moves available for the current player
                as the game progresses the mobility will be worth more */
                if(chessBoard.containsPiece(new Position(i, j))) {
                    totalPieces++;
                    if (chessBoard.getColorType(new Position(i, j)) == AIColorType) {
                        value += chessBoard.getPieceCost(new Position(i, j));
                        mobility += chessBoard.getAllMoves(AIColorType, new Position(i, j)).toArray().length;
                    } else {
                        value -= chessBoard.getPieceCost(new Position(i, j));
                        mobility -= chessBoard.getAllMoves(AIColorType, new Position(i, j)).toArray().length;
                    }
                }
            }
        }
        /* The evaluation of the board is the value plus the mobility times the sum of the remaining pieces divided
        by the original number of pieces, making the mobility more important as the game progresses */
        return value + (int)Math.round(mobility*((totalPieces/32) + 0.1));
    }

    private List<Move> getAllMoves(ChessBoard chessBoard){
        //empty list that holds all the moves
        List<Move> playerMoves = new LinkedList<>();
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                //if that particular position has a piece that is the same color as the AI
                if (chessBoard.containsPiece(new Position(i, j)) && chessBoard.getColorType(new Position(i, j)) == AIColorType){
                    Position tilePosition = new Position(i,j);
                    try {
                    possibleMoves = chessBoard.getTile(tilePosition).getPiece().getMoves(tilePosition);
                    for (Position position : possibleMoves) {
                        System.out.println(position.getCol() + " " + position.getRow());
                            playerMoves.add(new Move(tilePosition, position));
                        }
                    }
                        catch(NullPointerException exception) {
                        }
                }
            }
        }
        return playerMoves;
    }
}
