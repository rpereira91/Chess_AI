package pieces;

import helper.Position;

public class Bishop extends Piece {
    public Bishop(ColorType colorType){
        setPieceType(PieceType.BISHOP);
        setPieceColorType(colorType);
        setPieceCost(3);
    }
    //the start and end are on a diagonal it is a valid move
    //the absolute value is used to account for movement in both directions
    @Override
    public boolean legalMove(Position start, Position end) {
        if (Math.abs(start.getCol() - end.getCol()) == Math.abs(start.getRow()-end.getRow()))
            return true;
        return false;
    }
}
