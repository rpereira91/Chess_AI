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
    private int depth = -4;
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

}
