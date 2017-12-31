package pieces;

import helper.RowCol;

public class Bishop extends Piece {
    Bishop(ColorType colorType){
        setPieceType(PieceType.BISHOP);
        setPieceColorType(colorType);
        setPieceCost(3);
    }
    //the start and end are on a diagonal it is a valid move
    //the absolute value is used to account for movement in both directions
    @Override
    boolean legalMove(RowCol start, RowCol end) {
        if (Math.abs(start.getCol() - end.getCol()) == Math.abs(start.getCol()-end.getCol()))
            return true;
        return false;
    }
}
