package pieces;

import helper.RowCol;

public class Queen extends Piece {
    Queen(Color color){
        setPieceType(PieceType.QUEEN);
        setPieceColor(color);
    }

    @Override
    boolean properMove(RowCol start, RowCol end) {
        return false;
    }
}
