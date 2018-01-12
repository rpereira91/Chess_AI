package pieces;
/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    Knight piece logic
 */
import helper.Position;
import logic.ChessBoard;

import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {
    //the directions a rook can move in

    private int[][] directions = {
            {1, 2},
            {-1, 2},
            {2, -1},
            {-2, -1},
            {-1, -2},
            {1, -2},
            {-2, 1},
            {2, 1}
    };
    public Knight(ColorType colorType, ChessBoard board){
        setPieceType(PieceType.KNIGHT);
        setPieceColorType(colorType);
        setPieceCost(3);
        setBoard(board);
    }

    @Override
    public List<Position> getMoves(Position current) {
        List<Position> moves = new LinkedList<>();
        ChessBoard board = getBoard();
        moves.add(current);
        //checks if there are any pieces in the path for the rook based on directions
        for (int i=0; i < directions.length; i++){
            int[] direction = directions[i];
            int col = current.getCol() + direction[0];
            int row = current.getRow() + direction[1];
            //if the possible position is valid check the next positions
            if (Position.isValid(col, row) ){
                if (board.containsPiece(col, row)){
                    if (board.getColorType(col, row) != getPieceColorType()){
                        moves.add(new Position(col, row));
                    }
                }else{
                    moves.add(new Position(col, row));
                }
            }
        }
        return moves;
    }
}
