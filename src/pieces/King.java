package pieces;

import helper.Position;

public class King extends Piece {
    private boolean firstMove;
    public King(ColorType colorType){
        setPieceType(PieceType.KING);
        setPieceColorType(colorType);
        setFirstMove(true);
        setPieceCost(200);
    }

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
    @Override
    public boolean legalMove(Position start, Position end) {
        //if it's a diagonal move that is one unit away its valid
        if(Math.abs(start.getCol() - end.getCol()) == 1 && Math.abs(start.getRow() - end.getRow()) == 1)
            return true;
        //if the movement is on the same row with a one unit move in either direction
        else if(Math.abs(start.getCol() - end.getCol()) == 1 && start.getRow() == end.getRow())
            return true;
        //if the movement is on the same column and the move is one unit in either direction
        else if(Math.abs(start.getRow() - end.getRow()) == 1 && start.getCol() == end.getCol())
            return true;
        return false;
    }
}
