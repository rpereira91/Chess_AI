package pieces;
import helper.Position;

public abstract class Piece {

    private PieceType pieceType; //the type of piece
    private ColorType pieceColorType; //is the piece black or white
    private int pieceCost; //the value or cost of the piece (used for the AI)


    public int getPieceCost() {
        return pieceCost;
    }

    public void setPieceCost(int pieceCost) {
        this.pieceCost = pieceCost;
    }


    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public ColorType getPieceColorType() {
        return pieceColorType;
    }

    public void setPieceColorType(ColorType pieceColorType) {
        this.pieceColorType = pieceColorType;
    }

    public abstract boolean legalMove(Position start, Position end);
    public void madeFirstMove(){
    }
    //used for the king and rook for castling and the pawn for first double jump move
    public boolean firstMove(){
        return false;
    }

    public void setFirstMove(boolean firstMove){
    }
}
