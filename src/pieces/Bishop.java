package pieces;

import helper.Position;
import logic.ChessBoard;

import java.util.LinkedList;
import java.util.List;

public class Bishop extends Piece {
    private int[][] directions = {
            {-1, -1},
            {1, 1},
            {1, -1},
            {-1, 1}
    };

    public Bishop(ColorType colorType, ChessBoard board){
        setPieceType(PieceType.BISHOP);
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
            for (int j=1; j < 8; j++){
                int col = current.getCol() + direction[0] * j;
                int row = current.getRow() + direction[1] * j;

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
