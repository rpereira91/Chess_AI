package pieces;

import helper.RowCol;
public class King extends Piece {
    private boolean firstMove;
    King(ColorType colorType){
        setPieceType(PieceType.KING);
        setPieceColorType(colorType);
        setFirstMove(true);
        setPieceCost(200);
    }

    @Override
    boolean firstMove(){
        return firstMove;
    }
    @Override
    void madeFirstMove(){
        firstMove = false;
    }
    @Override
    void setFirstMove(boolean move){
        firstMove = move;
    }
    @Override
    boolean legalMove(RowCol start, RowCol end) {
        return false;
    }
}
