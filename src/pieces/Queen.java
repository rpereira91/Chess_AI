package pieces;

import helper.Position;
import logic.ChessBoard;

import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece {
    private int[][] directions = {
            {-1, -1},
            {1, 1},
            {1, -1},
            {-1, 1},
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };

    public Queen(ColorType colorType, ChessBoard board){
        setPieceType(PieceType.QUEEN);
        setPieceColorType(colorType);
        setBoard(board);
    }
    //since the queens movements mimics the rooks and the bishops the move is basically a combination of the two of them
    @Override
    public boolean legalMove(Position start, Position end) {
        if(start.getRow() - end.getRow() == 0
                || start.getCol() - end.getCol() == 0
                || Math.abs(start.getCol() - end.getCol()) == Math.abs(start.getRow()-end.getRow()))
            return true;
        return false;
    }

    @Override
    public List<Position> getMoves(Position current) {
        List<Position> moves = new LinkedList<>();
        ChessBoard board = getBoard();

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
