package pieces;

import helper.RowCol;

public class Pawn extends Piece {
    Pawn(Color color){
        setPieceType(PieceType.PAWN);
        setPieceColor(color);
    }

    @Override
    boolean properMove(RowCol start, RowCol end) {
        return false;
    }
}
