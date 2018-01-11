package pieces;

import helper.Position;
import logic.ChessBoard;

import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {
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

        for (int i=0; i < directions.length; i++){
            int[] direction = directions[i];
            int col = current.getCol() + direction[0];
            int row = current.getRow() + direction[1];
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
