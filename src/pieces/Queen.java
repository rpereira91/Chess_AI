package pieces;

import helper.Position;

public class Queen extends Piece {
    public Queen(ColorType colorType){
        setPieceType(PieceType.QUEEN);
        setPieceColorType(colorType);
    }
    //since the queens movements mimics the rooks and the bishops the move is basically a combination of the two of them
    @Override
    public boolean legalMove(Position start, Position end) {
        if(start.getRow() - end.getRow() == 0
                || start.getCol() - end.getCol() == 0
                || Math.abs(start.getCol() - end.getCol()) == Math.abs(start.getRow()-end.getRow()))
            return true;
        return false;
    }
}
