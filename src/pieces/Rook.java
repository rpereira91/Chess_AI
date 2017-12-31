package pieces;

import helper.RowCol;

public class Rook extends Piece {
    Rook(Color color){
        setPieceType(PieceType.ROOK);
        setPieceColor(color);
    }

    @Override
    boolean properMove(RowCol start, RowCol end) {
        return false;
    }
}
