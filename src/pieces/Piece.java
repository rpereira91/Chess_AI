package pieces;
import helper.RowCol;

abstract class Piece {

    private PieceType pieceType; //the type of piece
    private Color pieceColor; //is the piece black or white
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

    public Color getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(Color pieceColor) {
        this.pieceColor = pieceColor;
    }

    abstract boolean properMove(RowCol start, RowCol end);
    void madeFirstMove(){
    }
    //used for the king and rook for castling and the pawn for first double jump move
    boolean firstMove(){
        return false;
    }
    void setFirstMove(){
    }
}
