package pieces;

import helper.RowCol;

public class King extends Piece {
    King(Color color){
        setPieceType(PieceType.KING);
        setPieceColor(color);
    }

    @Override
    boolean properMove(RowCol start, RowCol end) {
        return false;
    }
}
