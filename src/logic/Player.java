package logic;

import helper.Position;
import pieces.*;

import java.util.LinkedList;
import java.util.List;

public class Player {
    private ColorType colorType = null;
    private PlayerType playerType = null;

    private boolean playerInCheck;
    private int numMove = 0;
    private int depth = 4;
    public Move move;

    Player (ColorType colorType){
        this.colorType = colorType;
        playerType = PlayerType.COMPUTER;
    }
    //gets the color type
    public ColorType getColorType() {
        return colorType;
    }
    //gets the player type
    public PlayerType getPlayerType() {
        return playerType;
    }
    //sets the player type
    public void setPlayerType(PlayerType playerType){
        this.playerType = playerType;
    }
    // returns all the moves of the color passed to into it
    private List<Move> getAllMoves (ChessBoard b, ColorType colour) {
        //empty list that holds all the moves
        List<Move> playerMoves = new LinkedList<>();

        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                //if the current position has a piece that has the same color as the player passed to it
                //add all the moves to the list
                if (b.containsPiece(new Position(i, j)) && b.getColorType(new Position(i, j)) == colour)
                    playerMoves.addAll(mergeLists(new Position(i, j), b.getAllMoves(colour, new Position(i, j))));
            }
        }
        return playerMoves;
    }
    //create a list of moves from the passed position to the list of positions
    private List<Move> mergeLists(Position position, List<Position> positions ) {

        List<Move> mergedList = new LinkedList<>();
        //for every position in the list, add that move to the list of moves
        for (int i = 0; i < positions.size(); i++) {
            Position pos = positions.get(i);
            mergedList.add(new Move(position, pos));
        }
        return mergedList;
    }
    //creates a copy of the game board passed to it
    ChessBoard copyBoard (ChessBoard board) {
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
                        gamePiece = new Pawn(board.getColorType(position));

                    } else if (tempPiece == PieceType.ROOK) {
                        gamePiece = new Rook(board.getColorType(position));

                    } else if (tempPiece == PieceType.KNIGHT) {
                        gamePiece = new Knight(board.getColorType(position));

                    } else if (tempPiece == PieceType.BISHOP) {
                        gamePiece = new Bishop(board.getColorType(position));

                    } else if (tempPiece == PieceType.KING) {
                        gamePiece = new King(board.getColorType(position));

                    } else if (tempPiece == PieceType.QUEEN) {
                        gamePiece = new Queen(board.getColorType(position));

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
                    if (chessBoard.getColorType(new Position(i, j)) == colorType) {
                        value += chessBoard.getPieceCost(new Position(i, j));
                        mobility += chessBoard.getAllMoves(colorType, new Position(i, j)).toArray().length;
                    } else {
                        value -= chessBoard.getPieceCost(new Position(i, j));
                        mobility -= chessBoard.getAllMoves(colorType, new Position(i, j)).toArray().length;
                    }
                }
            }
        }
        /* The evaluation of the board is the value plus the mobility times the sum of the remaining pieces divided
        by the original number of pieces, making the mobility more important as the game progresses */
        return value + (int)Math.round(mobility*((totalPieces/32) + 0.1));
    }

    public Move getNextMove ( ChessBoard chessBoard, boolean playerInCheck ) {

        ChessBoard cloneBoard = copyBoard(chessBoard);
        this.playerInCheck = playerInCheck;
        Max(cloneBoard, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
        return move;
    }
    private int Max ( ChessBoard chessBoard, int alpha, int beta, int boardDepth ) {

        Piece piece;
        PieceType originalPiece;
        PieceType newPiece;
        boolean madeFirstMove;
        boolean pawnPromo = false;

        int minVal;

        //if it's at the depth get the board evulation
        if (boardDepth == 0) {
            return evaluateBoard(chessBoard);
        }
        //get the list of moves for the color of the current player
        List<Move> moves;
        if(colorType == ColorType.WHITE) moves = getAllMoves(chessBoard, ColorType.WHITE);
        else moves = getAllMoves(chessBoard, ColorType.BLACK);

        //if there are moves that can be made for the player
        if (moves != null) {
            //while there are still moves that the player can make
            while(moves.size() > 0) {
                // get the first move from the move list
                Move firstMove = moves.remove(0);
                //check if the first move is being made
                madeFirstMove = chessBoard.checkFirstMove(firstMove.getStart());
                //get the starting piece
                originalPiece = chessBoard.getPieceType(firstMove.getStart());
                // make move
                piece = chessBoard.moveSpecialPiece(firstMove);
                //get the ending piece
                newPiece = chessBoard.getPieceType(firstMove.getEnd());
                //check to see if the piece has been promoted
                if (originalPiece != newPiece) pawnPromo = true;
                numMove++;
                //get the min value by calling the min function at a depth one lower than the current depth
                minVal = Min(chessBoard, alpha, beta, boardDepth - 1);
                // if the min is greater than the beta undo the move and return the beta value
                if (minVal >= beta) {
                    chessBoard.undoSpecialMove(firstMove, piece, madeFirstMove, pawnPromo);
                    numMove--;
                    return beta;
                }
                //the alpha value set if the min is greater than the alpha
                if (minVal > alpha) {
                    if (boardDepth == depth) {
                        move = firstMove;
                    }
                    alpha = minVal;
                }
                //undo the move made
                chessBoard.undoSpecialMove(firstMove, piece, madeFirstMove, pawnPromo);
                numMove--;
            }
        }
        return alpha;
    }

    private int Min( ChessBoard chessBoard, int alpha, int beta, int boardDepth ) {

        Piece piece;
        PieceType originalPiece;
        PieceType newPiece;
        boolean madeFirstMove;
        boolean pawnPromo = false;

        int maxVal;
        //if its the bottom of the depth tree get the board value
        if (boardDepth == 0) {
            return evaluateBoard(chessBoard);
        }
        List<Move> moves;
        //get the list of moves for the opposite player
        if(colorType == ColorType.WHITE) moves = getAllMoves(chessBoard, ColorType.BLACK);
        else moves = getAllMoves(chessBoard, ColorType.WHITE);
        //if there are still moves that can be made
        if (moves != null) {
            while(moves.size() > 0) {
                //get the first move
                Move firstMove = moves.remove(0);
                //check to see if its the first move
                madeFirstMove = chessBoard.checkFirstMove(firstMove.getStart());
                //set the starting piece
                originalPiece = chessBoard.getPieceType(firstMove.getStart());
                //make the first move in the move list
                piece = chessBoard.moveSpecialPiece(firstMove);
                newPiece = chessBoard.getPieceType(firstMove.getEnd());
                //check to see if the pawn has been promoted
                if (originalPiece != newPiece) pawnPromo = true;
                numMove++;
                //get the max value by going down one more level in the depth
                maxVal = Max(chessBoard, alpha, beta, boardDepth - 1);
                if (maxVal <= alpha) {
                    chessBoard.undoSpecialMove(firstMove, piece, madeFirstMove, pawnPromo); // undo
                    numMove--;
                    return alpha;
                }
                if (maxVal < beta) {
                    if (boardDepth == boardDepth) {
                        move = firstMove;
                    }
                    beta = maxVal;
                }
                chessBoard.undoSpecialMove(firstMove, piece, madeFirstMove,pawnPromo); // undo
                numMove--;
            }
        }
        return beta;
    }

    }
