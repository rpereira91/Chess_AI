package pieces;

import helper.Position;
import logic.ChessBoard;

import java.util.LinkedList;
import java.util.List;

public class Pawn extends Piece {
    private boolean firstMove;
    public Pawn(ColorType colorType, ChessBoard board){
        setPieceType(PieceType.PAWN);
        setPieceColorType(colorType);
        setFirstMove(true);
        setPieceCost(1);
        setBoard(board);
    }

    //Logic used for the first move jump
    @Override
    public boolean firstMove(){
        return firstMove;
    }

    @Override
    public List<Position> getMoves(Position current) {
        List<Position> moves = new LinkedList<>();
        ChessBoard board = getBoard();
        int forward = getPieceColorType() == ColorType.BLACK ? 1 : -1;

        // forward
        for (int i=1; i <= 2; i++){
            int col = current.getCol();
            int row = current.getRow() + forward * i;

            if (Position.isValid(col, row)){
                if (board.containsPiece(col, row) || (i == 2 && !firstMove())){
                    break;
                }
                moves.add(new Position(col, row));
            }
        }

        // diagonal attack
        int col = current.getCol() + 1;
        int row = current.getRow() + forward;
        Position pos = new Position(col, row);
        // If within board bounds, contains a piece and it's enemy
        if (Position.isValid(col, row) && board.containsPiece(pos) && board.getColorType(pos) != getPieceColorType()){
            moves.add(pos);
        }
        col = current.getCol() - 1;
        pos = new Position(col, row);
        if (Position.isValid(col, row) && board.containsPiece(pos) && board.getColorType(pos) != getPieceColorType()){
            moves.add(pos);
        }

        // TODO: Implement Enpassant

        return moves;
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
