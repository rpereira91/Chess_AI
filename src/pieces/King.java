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
        if(firstMove){
            int row = getPieceColorType() == ColorType.BLACK ? 0 : 7;
            if(!board.containsPiece(1,row) &&
                    !board.containsPiece(2,row) &&
                    !board.containsPiece(3,row) &&
                    board.getPieceType(new Position(0,row)) == PieceType.ROOK){
                moves.add(new Position(0,row));
            }
            if(!board.containsPiece(5,row) &&
                    !board.containsPiece(6,row) &&
                    board.getPieceType(new Position(7,row)) == PieceType.ROOK){
                moves.add(new Position(7,row));
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
}
