package pieces;

import helper.Position;

public class Pawn extends Piece {
    private boolean firstMove;
    public Pawn(ColorType colorType){
        setPieceType(PieceType.PAWN);
        setPieceColorType(colorType);
        setFirstMove(true);
        setPieceCost(1);
    }

    //Logic used for the first move jump
    @Override
    public boolean firstMove(){
        return firstMove;
    }
    @Override
    public void madeFirstMove(){
        firstMove = false;
    }
    @Override
    public void setFirstMove(boolean move){
        firstMove = move;
    }
    @Override
    public boolean legalMove(Position start, Position end) {
        //the white pieces move up
        if(getPieceColorType() == ColorType.WHITE){
            //if it's a forward move in the same column its legal
            if(start.getCol() == end.getCol() && start.getRow()-end.getRow() == 1)
                return true;
            //if it's a diagonal move one piece away it;s legal move
            if(Math.abs(start.getCol()-end.getCol()) == 1 && start.getRow() - end.getRow() == 1)
                return true;
            //the first move it's possible to move the piece two spaces
            if(firstMove()){
                if(start.getCol() == end.getCol() && start.getRow() - end.getRow() == 2)
                    return true;
            }
            return false;
        }
        //the black pieces move down so the logic for row checking is reversed
        if(getPieceColorType() == ColorType.BLACK){
            if(start.getCol() == end.getCol() && start.getRow() - end.getRow() == -1)
                return  true;
            if (Math.abs(start.getCol() - end.getCol()) == 1 && start.getRow() - end.getRow() == -1)
                return true;
            if(firstMove()){
                if(start.getCol() == end.getCol() && start.getRow() - end.getRow() == -2)
                    return true;
            }
            return false;
        }
        return false;
    }
}
