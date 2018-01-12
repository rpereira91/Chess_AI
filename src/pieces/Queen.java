package pieces;
/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    Piece class for queen containing the logic for queen movements
 */
import helper.Position;
import logic.ChessBoard;

import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece {
    //directions, basically a combination of rook and bishop
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
    public List<Position> getMoves(Position current) {
        List<Position> moves = new LinkedList<>();
        ChessBoard board = getBoard();
        moves.add(current);

        for (int i=0; i < directions.length; i++){
            int[] direction = directions[i];
            for (int j=1; j < 8; j++){
                int col = current.getCol() + direction[0] * j;
                int row = current.getRow() + direction[1] * j;
                //if the next possible position is valid check if the piece can move there
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
