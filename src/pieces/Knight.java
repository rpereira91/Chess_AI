package pieces;

import helper.RowCol;

public class Knight extends Piece {
    Knight(Color color){
        setPieceType(PieceType.KNIGHT);
        setPieceColor(color);
    }

    @Override
    boolean properMove(RowCol start, RowCol end) {
        return false;
    }
}
