package pieces;

import helper.RowCol;

public class Bishop extends Piece {
    Bishop(Color color){
        setPieceType(PieceType.BISHOP);
        setPieceColor(color);
    }

    @Override
    boolean properMove(RowCol start, RowCol end) {
        return false;
    }
}
