package logic;
import pieces.Piece;

public class Tile {
    private Piece piece;

    Tile(){
        piece = null;
    }
    //checks to see if the current tile is occupied
    boolean tileOccupied(){
        if (piece != null)
            return true;
        return false;
    }
    //sets the current tiles piece the passed piece if there isn't already a piece in there
    public void setPiece(Piece piece) {
        if(!tileOccupied())
            this.piece = piece;
    }
    //returns the tiles piece, if there isn't one it returns null
    public Piece getPiece() {
        if(tileOccupied())
            return piece;
        return null;
    }
    //makes the current piece null and returns the tiles piece
    public Piece takePiece(){
        if(tileOccupied()){
            Piece holder = piece;
            piece = null;
            return holder;
        }
        return null;
    }
}
