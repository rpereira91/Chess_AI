package pieces;

import helper.Position;
import logic.ChessBoard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    Rook piece logic
 */
public class Rook extends Piece {
    private boolean firstMove;
    //the directions a rook can move in
    private int[][] directions = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };

    public Rook(ColorType colorType, ChessBoard board){
        setPieceType(PieceType.ROOK);
        setPieceColorType(colorType);
        setPieceCost(5);
        setFirstMove(true);
        setBoard(board);
    }
    //logic used for castling
    @Override
    public boolean firstMove(){
        return firstMove;
    }
    @Override
    public void madeFirstMove(){
        firstMove = false;
    }
    @Override
    public void setFirstMove(boolean move){
        firstMove = move;
    }
    //if the piece is staying in the same row or column it's a valid move
    // TODO: Implement castling
    @Override
    public List<Position> getMoves(Position current){
        List<Position> moves = new LinkedList<>();
        ChessBoard board = getBoard();
        moves.add(current);
        //checks if there are any pieces in the path for the rook based on directions
        for (int i=0; i < directions.length; i++){
            int[] direction = directions[i];
            for (int j=1; j < 8; j++){
                int col = current.getCol() + direction[0] * j;
                int row = current.getRow() + direction[1] * j;
                //if the possible position is valid check the next positions
                if (Position.isValid(col, row)) {
                    if (!board.containsPiece(col, row)) {
                        moves.add(new Position(col, row));
                    } else {
                        if (board.getColorType(col, row) != getPieceColorType()) {
                            moves.add(new Position(col, row));
                        }
                        break;
                    }
                }else{
                    break;
                }
            }
        }
        return moves;
    }
}
