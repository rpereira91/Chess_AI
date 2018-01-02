package pieces;

import helper.Position;

public class Rook extends Piece {
    private boolean firstMove;
    public Rook(ColorType colorType){
        setPieceType(PieceType.ROOK);
        setPieceColorType(colorType);
        setPieceCost(5);
        setFirstMove(true);
    }
    //logic used for castling
    @Override
    public boolean firstMove(){
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
    //if the piece is staying in the same row or column it's a valid move
    @Override
    public boolean legalMove(Position start, Position end) {
        if(start.getRow() - end.getRow() == 0 || start.getCol() - end.getCol() == 0)
            return true;
        return false;
    }
}
