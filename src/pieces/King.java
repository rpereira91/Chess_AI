package pieces;

import helper.Position;
import logic.ChessBoard;

import java.util.LinkedList;
import java.util.List;

public class King extends Piece {
    private boolean firstMove;
    private int[][] directions = {
            {-1, -1},
            {1, 1},
            {1, -1},
            {-1, 1},
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };

    public King(ColorType colorType, ChessBoard board){
        setPieceType(PieceType.KING);
        setPieceColorType(colorType);
        setFirstMove(true);
        setPieceCost(200);
        setBoard(board);
    }

    @Override
    public boolean firstMove(){
        return firstMove;
    }

    // TODO: Implement
    @Override
    public List<Position> getMoves(Position current) {
        List<Position> moves = new LinkedList<>();
        ChessBoard board = getBoard();
        moves.add(current);

        for (int i=0; i < directions.length; i++){
            int[] direction = directions[i];
                int col = current.getCol() + direction[0];
                int row = current.getRow() + direction[1];
                if (Position.isValid(col, row)) {
                    if(!board.colorCanAttackKing(getPieceColorType(),board, new Position(col,row))) {
                        if (!board.containsPiece(col, row)) {
                            moves.add(new Position(col, row));
                        } else {
                            if (board.getColorType(col, row) != getPieceColorType()) {
                                moves.add(new Position(col, row));
                            }
                        }
                    }
                }
        }
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
        //if its the king's first move it can jump 2 places on the same row
        if (firstMove() && start.getRow() == end.getRow() && Math.abs(start.getCol() - end.getCol()) == 2) return true;
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
