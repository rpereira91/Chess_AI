package pieces;

import helper.Position;
import logic.ChessBoard;

import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {
    private int[][] directions = {
            {1, 2},
            {-1, 2},
            {2, -1},
            {-2, -1},
            {-1, -2},
            {1, -2},
            {-2, 1},
            {2, 1}
    };
    public Knight(ColorType colorType, ChessBoard board){
        setPieceType(PieceType.KNIGHT);
        setPieceColorType(colorType);
        setPieceCost(3);
        setBoard(board);
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

    @Override
    public List<Position> getMoves(Position current) {
        List<Position> moves = new LinkedList<>();
        ChessBoard board = getBoard();
        for (int i=0; i < directions.length; i++){
            int[] direction = directions[i];
            int col = current.getCol() + direction[0];
            int row = current.getRow() + direction[1];
            if (Position.isValid(col, row)){
                if (board.containsPiece(col, row)){
                    if (board.getColorType(col, row) != getPieceColorType()){
                        moves.add(new Position(col, row));
                    }
                }else{
                    moves.add(new Position(col, row));
                }
            }
        }
        return moves;
    }
}
