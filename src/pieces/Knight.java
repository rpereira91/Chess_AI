package pieces;

import helper.Position;

public class Knight extends Piece {
    public Knight(ColorType colorType){
        setPieceType(PieceType.KNIGHT);
        setPieceColorType(colorType);
        setPieceCost(3);
    }

    //if the knight can move in an L shape in either direction it's a legal move
    @Override
    public boolean legalMove(Position start, Position end) {
        if(Math.abs(start.getCol() - end.getCol()) == 1 && Math.abs(start.getRow() - end.getRow()) == 2)
            return true;
        if (Math.abs(start.getCol() - end.getCol()) == 2 && Math.abs(start.getRow() - end.getRow()) == 1)
            return true;
        return false;
    }
}
