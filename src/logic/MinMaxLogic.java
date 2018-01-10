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
    int depth;
    public MinMaxLogic(ColorType AIColorType, int depth) {
        this.AIColorType = AIColorType;
        this.depth = depth;
    }

    //gets the color type for the AI
    public ColorType getAIColorType() {
        return AIColorType;
    }
    public Move getNextMove ( ChessBoard chessBoard) {

        ChessBoard cloneBoard = copyBoard(chessBoard);
        Max(cloneBoard, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
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
                    Position tempPosition = new Position(i,j);
                    totalPieces++;
                    if (chessBoard.getColorType(tempPosition) == AIColorType) {
                        value += chessBoard.getPieceCost(tempPosition);
                        mobility += getAllPieceMoves(chessBoard,tempPosition).toArray().length;
                    } else {
                        value -= chessBoard.getPieceCost(new Position(i, j));
                        mobility -= getAllPieceMoves(chessBoard,tempPosition).toArray().length;
                    }
                }
            }
        }
        /* The evaluation of the board is the value plus the mobility times the sum of the remaining pieces divided
        by the original number of pieces, making the mobility more important as the game progresses */
        return value + (int)Math.round(mobility*((totalPieces/32) + 0.1));
    }
    private List<Move> getAllPieceMoves(ChessBoard chessBoard, Position position){
        List<Move> pieceMoves = new LinkedList<>();
        try {
            possibleMoves = chessBoard.getTile(position).getPiece().getMoves(position);
            for (Position p : possibleMoves) {
                pieceMoves.add(new Move(position, p));
            }
        }
        catch(NullPointerException exception) {
        }
        return pieceMoves;
    }
    private List<Move> getAllMoves(ChessBoard chessBoard, ColorType colorType){
        //empty list that holds all the moves
        List<Move> playerMoves = new LinkedList<>();
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                //if that particular position has a piece that is the same color as the AI
                if (chessBoard.containsPiece(new Position(i, j)) && chessBoard.getColorType(new Position(i, j)) == colorType){
                    Position tilePosition = new Position(i,j);
                    try {
                    possibleMoves = chessBoard.getPiece(tilePosition).getMoves(tilePosition);
                    for (Position position : possibleMoves) {
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
    private int Max ( ChessBoard chessBoard, int alpha, int beta, int boardDepth ) {

        Piece piece;
        PieceType originalPiece;
        PieceType newPiece;
        boolean madeFirstMove;

        int minVal;

        //if it's at the depth get the board evulation
        if (boardDepth == 0) {
            return evaluateBoard(chessBoard);
        }
        //get the list of moves for the color of the current player
        List<Move> moves;
        if(AIColorType == ColorType.WHITE) moves = getAllMoves(chessBoard, ColorType.WHITE);
        else moves = getAllMoves(chessBoard, ColorType.BLACK);

        //if there are moves that can be made for the player
        if (moves != null) {
            //while there are still moves that the player can make
            while(moves.size() > 0) {
                // get the first move from the move list
                Move firstMove = moves.remove(0);
                if(firstMove.getStart().equals(firstMove.getEnd())){
                    continue;
                }
                //check if the first move is being made
                madeFirstMove = chessBoard.checkFirstMove(firstMove.getStart());
                // make move
                piece = chessBoard.moveSpecialPiece(firstMove);
                //get the min value by calling the min function at a depth one lower than the current depth
                minVal = Min(chessBoard, alpha, beta, boardDepth - 1);
                // if the min is greater than the beta undo the move and return the beta value
                if (minVal >= beta) {
                    chessBoard.undoSpecialMove(firstMove, piece, madeFirstMove);
                    return beta;
                }
                //the alpha value set if the min is greater than the alpha
                if (minVal > alpha) {
                    if (boardDepth == depth) {
                        nextMove = firstMove;
                    }
                    alpha = minVal;
                }
                //undo the move made
                chessBoard.undoSpecialMove(firstMove, piece, madeFirstMove);
            }
        }
        return alpha;
    }

    private int Min( ChessBoard chessBoard, int alpha, int beta, int boardDepth ) {

        Piece piece;
        PieceType originalPiece;
        PieceType newPiece;
        boolean madeFirstMove;

        int maxVal;
        //if its the bottom of the depth tree get the board value
        if (boardDepth == 0) {
            return evaluateBoard(chessBoard);
        }
        List<Move> moves;
        //get the list of moves for the opposite player
        if(AIColorType == ColorType.WHITE) moves = getAllMoves(chessBoard, ColorType.BLACK);
        else moves = getAllMoves(chessBoard, ColorType.WHITE);
        //if there are still moves that can be made
        if (moves != null) {
            while(moves.size() > 0) {
                //get the first move
                Move firstMove = moves.remove(0);
                if(firstMove.getStart().equals(firstMove.getEnd())){
                    continue;
                }
                //check to see if its the first move
                madeFirstMove = chessBoard.checkFirstMove(firstMove.getStart());
                //make the first move in the move list
                piece = chessBoard.moveSpecialPiece(firstMove);
                //get the max value by going down one more level in the depth
                maxVal = Max(chessBoard, alpha, beta, boardDepth - 1);
                if (maxVal <= alpha) {
                    chessBoard.undoSpecialMove(firstMove, piece, madeFirstMove); // undo
                    return alpha;
                }
                if (maxVal < beta) {
                    if (boardDepth == depth) {
                        nextMove = firstMove;
                    }
                    beta = maxVal;
                }
                chessBoard.undoSpecialMove(firstMove, piece, madeFirstMove); // undo
            }
        }
        return beta;
    }
}
