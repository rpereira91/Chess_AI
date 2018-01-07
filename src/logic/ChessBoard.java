/*
Board eval us done using: https://chessprogramming.wikispaces.com/Evaluation
 */

package logic;

import helper.Position;
import pieces.*;

import java.util.LinkedList;
import java.util.List;

public class ChessBoard {
    //the game gameBoard
    private Tile[][] gameBoard;
    //the size of the game gameBoard, it's normally 8x8
    public final int SIZE = 8;
    public ChessBoard(){
        gameBoard = new Tile[SIZE][SIZE];
    }
    //creates an empty game gameBoard with no pieces on it
    public void createEmptyGameBoard(){
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                gameBoard[x][y] = new Tile();
            }

        }
    }
    //create a game gameBoard with pieces on it
    public void initilizeGameBoard(){
        createEmptyGameBoard();
        //set the black pieces
        gameBoard[0][0].setPiece(new Rook(ColorType.BLACK));
        gameBoard[7][0].setPiece(new Rook(ColorType.BLACK));
        gameBoard[1][0].setPiece(new Knight(ColorType.BLACK));
        gameBoard[6][0].setPiece(new Knight(ColorType.BLACK));
        gameBoard[2][0].setPiece(new Bishop(ColorType.BLACK));
        gameBoard[5][0].setPiece(new Bishop(ColorType.BLACK));
        gameBoard[3][0].setPiece(new Queen(ColorType.BLACK));
        gameBoard[4][0].setPiece(new King(ColorType.BLACK));
        //set up the white pieces
        gameBoard[0][7].setPiece(new Rook(ColorType.WHITE));
        gameBoard[7][7].setPiece(new Rook(ColorType.WHITE));
        gameBoard[1][7].setPiece(new Knight(ColorType.WHITE));
        gameBoard[6][7].setPiece(new Knight(ColorType.WHITE));
        gameBoard[2][7].setPiece(new Bishop(ColorType.WHITE));
        gameBoard[5][7].setPiece(new Bishop(ColorType.WHITE));
        gameBoard[3][7].setPiece(new Queen(ColorType.WHITE));
        gameBoard[4][7].setPiece(new King(ColorType.WHITE));
        //set the pawns for both colors
        for (int i = 0; i < 8; i++) {
            gameBoard[i][1].setPiece(new Pawn(ColorType.BLACK));
            gameBoard[i][6].setPiece(new Pawn(ColorType.WHITE));

        }
    }
    //checks if the move in the Position is on it's first move
    boolean checkFirstMove(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()].getPiece().firstMove();
        }
        return false;
    }
    //gets the cost of the piece
    int getPieceCost(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()].getPiece().getPieceCost();
        }
        return 0;
    }
    //gets the type of piece at the Position
    public PieceType getPieceType(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()].getPiece().getPieceType();
        }
        return null;
    }
    //gets the color of the piece at the Position
    public ColorType getColorType(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()].getPiece().getPieceColorType();
        }
        return null;
    }

    public String getImage(Position position){
        if(getPieceType(position) == PieceType.PAWN){
            if(getColorType(position) == ColorType.WHITE)
                return "WP";
            else
                return "BP";
        }
        if(getPieceType(position) == PieceType.KNIGHT){
            if(getColorType(position) == ColorType.WHITE)
                return "WN";
            else
                return "BN";
        }
        if(getPieceType(position) == PieceType.BISHOP){
            if(getColorType(position) == ColorType.WHITE)
                return "WB";
            else
                return "BB";
        }
        if(getPieceType(position) == PieceType.ROOK){
            if(getColorType(position) == ColorType.WHITE)
                return "WR";
            else
                return "BR";
        }
        if(getPieceType(position) == PieceType.KING){
            if(getColorType(position) == ColorType.WHITE)
                return "WK";
            else
                return "BK";
        }
        if(getPieceType(position) == PieceType.QUEEN){
            if(getColorType(position) == ColorType.WHITE)
                return "WQ";
            else
                return "BQ";
        }
        return null;
    }
    //takes the piece off the tile
    public Piece removePiece(Position position){
        if(containsPiece(position)){
            return gameBoard[position.getCol()][position.getRow()].takePiece();
        }
        return null;
    }
    //checks the passed Position value to see if it has a piece
    public boolean containsPiece(Position position){
        return gameBoard[position.getCol()][position.getRow()].tileOccupied();
    }
    //if the tile is not occupied it can place down the piece, if it's occupied it doesn't place down the piece
    boolean setPiece(Position position, Piece piece){
        if(!containsPiece(position)){
            gameBoard[position.getCol()][position.getRow()].setPiece(piece);
            return true;
        }
        return false;
    }
    //move a piece from the start to the end, it takes the piece at the start position and moves it to ene end position
    public void movePiece(Position start, Position end){
        gameBoard[end.getCol()][end.getRow()].setPiece(gameBoard[start.getCol()][start.getRow()].takePiece());
    }
    //replaces the passed position with a new piece
    public void replacePiece(Position p, PieceType pieceType){
        if(containsPiece(p)){
            ColorType colorType = gameBoard[p.getCol()][p.getRow()].takePiece().getPieceColorType();
            if(pieceType == PieceType.PAWN){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Pawn(colorType));
            }else if(pieceType == PieceType.BISHOP){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Bishop(colorType));
            }else if(pieceType == PieceType.KNIGHT){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Knight(colorType));
            }else if(pieceType == PieceType.ROOK){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Rook(colorType));
            }else if(pieceType == PieceType.QUEEN){
                gameBoard[p.getCol()][p.getRow()].setPiece(new Queen(colorType));
            }
        }
    }

    public List<Position> getAllMoves(Position position){
        List<Position> allMoves = new LinkedList<>();
        if (containsPiece(position)){
            for (int i=0; i < 8; i++){
                for (int j=0; j < 8; j++){
                    Position newPosition = new Position(i, j);
                    if (isLegalMove(position, newPosition)){
                        allMoves.add(newPosition);
                    }
                }
            }
        }
        return allMoves;
    }

    // the move for a given piece
    public List<Position> getAllMoves(ColorType currentColor, Position p) {
        //a list of all the possible moves
        List<Position> allMoves = new LinkedList<>();
        //if the current position has a piece and it's the same color as the passed color check the entire gameBoard
        if (containsPiece(p) && currentColor == getColorType(p)) {
            for (int i = 0 ; i < 8 ; i++) {
                for (int j = 0 ; j < 8 ; j++) {
                    /*
                    if it's a leal move for the piece on the tile, and if that piece is able to move freely alon that path
                    and if the tile doesn't have a piece that's not the same color as the current piece
                    check if that move leaves the King open to attack, if not, add that move to the list
                    */
                    if (isLegalMove(p, new Position(i, j)) &&
                            checkPiecePath(p, new Position(i, j), getPieceType(p)) &&
                            (!containsPiece(new Position(i, j)) || getColorType(new Position(i, j)) != currentColor)) {
                        if (!allPiecesToKing(p, new Position(i, j))) {
                            allMoves.add(new Position(i, j));
                        }
                    }
                }
            }
        }
        return allMoves;
    }
    //if the passed move was made, will the king be open to attack
    boolean allPiecesToKing ( Position start, Position end ) {

        Piece piece;
        Piece dest = null;
        Piece castlingPiece = null;

        boolean canAttack;
        //take the piece from the starting position
        piece = removePiece(start);
        //if the destination tile has a piece
        if (containsPiece(end)) {
            //the piece at the destination is not the same color as the piece at the start make the dest piece the end piece
            if (getColorType(end) != piece.getPieceColorType()) dest = removePiece(end);
            else return false; //if they are the same color we know the move won't be made and its false
        }
        // move the piece to the destination
        setPiece(end, piece);
        // if the piece is a king and it's the first move the king has made
        if (piece.getPieceType() == PieceType.KING && piece.firstMove()) {
            //check for a castling move, if the destination is a rook on the right side or the left side
            if (start.getCol() - end.getCol() == -2 && start.getCol() == 4 &&
                    containsPiece(new Position(end.getCol() + 1, end.getRow())) &&
                    getPieceType(new Position(end.getCol() + 1, end.getRow())) == PieceType.ROOK) {
                //check if it's the rook's first move
                if (!(castlingPiece = removePiece(new Position(end.getCol() + 1, end.getRow()))).firstMove())
                    setPiece(new Position(end.getCol() + 1, end.getRow()), castlingPiece);
                else setPiece(new Position(end.getCol() - 1, end.getRow()), castlingPiece);
            }
            else if (start.getCol() - end.getCol() == 2 && start.getCol() == 4 &&
                    containsPiece(new Position(end.getCol() - 2, end.getRow())) &&
                    getPieceType(new Position(end.getCol() - 2, end.getRow())) == PieceType.ROOK) {
                if ((castlingPiece = removePiece(new Position(end.getCol() - 2, end.getRow()))).firstMove()) {
                    setPiece(new Position(end.getCol() + 1, end.getRow()), castlingPiece);
                } else {
                    setPiece(new Position(end.getCol() - 2, end.getRow()), castlingPiece);
                }
            }
        }
        //check if any of the enemy pieces can attack the king if the castling has been completed
        if (getColorType(end) == ColorType.WHITE) {
            canAttack = colorCanAttackKing(ColorType.BLACK);
        } else {
            canAttack = colorCanAttackKing(ColorType.WHITE);
        }
        piece = removePiece(end);
        //undo the castling if it did happen
        if (piece.getPieceType() == PieceType.KING && piece.firstMove()) { // put castle back
            if (start.getCol() - end.getCol() == -2 && castlingPiece != null && castlingPiece.firstMove()) {
                setPiece(new Position(end.getCol() + 1, end.getRow()), removePiece(new Position(end.getCol() - 1, end.getRow())));
            } else if (start.getCol() - end.getCol() == 2 && castlingPiece != null && castlingPiece.firstMove()) {
                setPiece(new Position(end.getCol() - 2, end.getRow()), removePiece(new Position(end.getCol() + 1, end.getRow())));
            }
        }
        //reset the pieces
        if (dest != null) {
            setPiece(end, dest);
        }
        setPiece(start, piece);
        //confirm the attack possibilities
        return canAttack;
    }
    //returns true if any of the passed through color type can attack the king
    boolean colorCanAttackKing(ColorType colorType ) {
        //run through the entire gameBoard
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                //if that position has a piece that is the same color as the passed by value
                if (containsPiece(new Position(i, j)) && getColorType(new Position(i, j)) == colorType) {
                    if (positionCanAttackKing(new Position(i, j))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    // true if the passed position can can attack the king
    boolean positionCanAttackKing ( Position position ) {

        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                //if the current location has a piece that is a king of the opposite color as the piece passed to it
                if (containsPiece(new Position(i, j)) &&
                        getPieceType(new Position(i, j)) == PieceType.KING &&
                        getColorType(new Position(i, j)) != getColorType(position)) {
                    //if the move from the passed position to the new one is legal that position can attack the king
                    if (isLegalMove(position, new Position(i, j)) &&
                            checkPiecePath(position, new Position(i, j), getPieceType(position))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //checks to see if a particular move from one position to another is legal
    public boolean isLegalMove(Position start, Position end){
        if(containsPiece(start)) {
            if(gameBoard[start.getCol()][start.getRow()].getPiece().legalMove(start,end))
                return true;
        }
        return false;
    }
    //checks the path for each piece type
    public boolean pawnPath(Position start, Position end){
        //check to see if the pawn is moving in the same column
        if(start.getCol() == end.getCol()){
            //if the piece is moving down and it's the first move check if the destination and the tile in the middle are occpied
            if(start.getRow() - end.getRow() == -2)
                if (!gameBoard[start.getCol()][start.getRow() + 1].tileOccupied()
                        && gameBoard[end.getCol()][end.getRow()].tileOccupied())
                    return true;
            //if the piece is moving up and it's the first move, same logic as above
            else if(start.getRow() - end.getRow() == 2)
                if (!gameBoard[start.getCol()][start.getRow() - 1].tileOccupied()
                        && gameBoard[end.getCol()][end.getRow()].tileOccupied())
                    return true;
            else if(Math.abs(start.getRow()-end.getRow()) == 1)
                if (!containsPiece(end))
                    return true;
        }
        //then check the diagonal, if the piece is attempting to capture a piece
        else if(Math.abs(start.getCol() - end.getCol()) == 1 && Math.abs(start.getRow() - end.getRow()) == 1){
            if(containsPiece(end)) return true;
        }
        //if none of these conditions are met, return false
        return false;
    }
    //the bishop's path is checked here
    public boolean bishopPath(Position start, Position end){
        //if the piece is moving upwards in the right direction check the path for occupied tiles
        if(start.getCol() < end.getCol() && start.getRow() > end.getRow()) {
            for (int i = start.getCol() + 1; i < end.getCol(); i++) {
                for (int j = start.getRow() - 1; j > end.getRow(); j--) {
                    if(gameBoard[i][j].tileOccupied()) return false;
                }
            }
        }
        //if the piece is moving upwards in the left direction check the path for occupied tiles
        else if (start.getCol() > end.getCol() && start.getRow() > end.getRow()){
            for (int i = start.getCol() - 1; i > end.getCol(); i--) {
                for (int j = start.getRow() - 1; j > end.getRow(); j--) {
                    if(gameBoard[i][j].tileOccupied()) return false;
                }
            }
        }
        //if the piece is moving down and to the right check the path for occupied tiles
        else if(start.getCol() < end.getCol() && start.getRow() < end.getRow()){
            for (int i = start.getCol() + 1; i < end.getCol(); i++) {
                for (int j = start.getRow()+1; j < end.getRow(); j++) {
                    if(gameBoard[i][j].tileOccupied()) return false;
                }
            }
        }
        //if the piece is moving down and to the left check the path for occupied tiles
        else if(start.getCol() > end.getCol() && start.getRow() < end.getRow()){
            for (int i = start.getCol() - 1; i > end.getCol() ; i--) {
                for (int j = start.getRow() + 1; j < end.getRow(); j++) {
                    if(gameBoard[i][j].tileOccupied()) return false;
                }

            }
        }
        //if there aren't any tiles in the path return true
        return true;
    }
    //the rooks path is checked here
    public boolean rookPath(Position start, Position end){
        //if the rook is moving in the same row
        if(start.getRow() == end.getRow() && start.getCol() != end.getCol()){
            //check the right or the left depending on how the piece is moving
            if(start.getCol() < end.getCol()){
                for (int i = start.getCol() + 1; i < end.getCol(); i++) {
                    if(gameBoard[i][start.getRow()].tileOccupied()) return false;
                }
            }else{
                for (int i = start.getCol() - 1; i > end.getCol() ; i--) {
                    if(gameBoard[i][end.getRow()].tileOccupied()) return false;
                }
            }
        }
        //if the rook is moving in the same column
        else if(start.getRow() != end.getRow() && start.getCol() == end.getCol()){
            if(start.getRow() < end.getRow()){
                for (int i = start.getRow() + 1; i < end.getRow(); i++) {
                    if(gameBoard[start.getCol()][i].tileOccupied()) return false;
                }
            }else{
                for (int i = start.getRow() - 1; i > end.getRow() ; i--) {
                    if(gameBoard[start.getCol()][i].tileOccupied()) return false;
                }
            }
        }
        return false;
    }
    //if both the rook's path and bishop's path are clear the path for the queen is also clear
    //since the queen mimics both the rook's and the bishop's path
    public boolean queenPath(Position start, Position end){
        if(rookPath(start,end) && bishopPath(start,end))
            return true;
        return false;
    }
    public boolean kingPath(Position start, Position end){
        //if its the king's first move
        if (gameBoard[start.getCol()][start.getRow()].getPiece().firstMove()) {
            // if the king is castling on the left side or the right side of the king
            if (start.getCol() == 4 && start.getCol() - end.getCol() == -2 &&
                    !gameBoard[start.getCol() - 1][start.getRow()].tileOccupied() &&
                    !gameBoard[start.getCol() - 2][start.getRow()].tileOccupied() &&
                    !gameBoard[start.getCol() - 3][start.getRow()].tileOccupied()) {

                if (gameBoard[start.getCol() - 4][start.getRow()].tileOccupied())
                    if (gameBoard[start.getCol() - 4][start.getRow()].getPiece().firstMove())
                        if (gameBoard[start.getCol() - 4][start.getRow()].getPiece().getPieceType() == PieceType.ROOK)
                            return true;
                return false;
            } else if (start.getCol() == 4 && start.getCol() - end.getCol() == 2 &&
                    !gameBoard[start.getCol() + 1][start.getCol()].tileOccupied() &&
                    !gameBoard[start.getCol() + 2][start.getRow()].tileOccupied()) {
                if (gameBoard[start.getCol() + 3][start.getRow()].tileOccupied())
                    if (gameBoard[start.getCol() + 3][start.getRow()].getPiece().firstMove())
                        if (gameBoard[start.getCol() + 3][start.getRow()].getPiece().getPieceType() == PieceType.ROOK)
                            return true;
                return false;
            }
        }
        if ((Math.abs(start.getCol() - end.getCol()) == 1 && Math.abs(start.getRow() - end.getRow()) == 0) ||
                (Math.abs(start.getCol() - end.getCol()) == 0 && Math.abs(start.getRow() - end.getRow()) == 1) ||
                (Math.abs(start.getCol() - end.getCol()) == 1 && Math.abs(start.getRow() - end.getRow()) == 1))
            return true;
        else return false;

    }
    //universal check for path based on the piece type passed
    public boolean checkPiecePath(Position start, Position end, PieceType pieceType){

        if(pieceType == PieceType.PAWN){
            return pawnPath(start, end);
        }else if(pieceType == PieceType.BISHOP){
            return bishopPath(start, end);
        }else if(pieceType == PieceType.KNIGHT){
            //since the knight can jump over pieces the path doesn't really matter
            return true;
        }else if(pieceType == PieceType.ROOK){
            return rookPath(start, end);
        }else if(pieceType == PieceType.QUEEN){
            return queenPath(start, end);
        }else if(pieceType == PieceType.KING){
            return kingPath(start, end);
        }
        return false;
    }

    //this handles castling and pawn moving for the first time
    public Piece moveSpecialPiece ( Move move) {
        //position variables to make it a bit easier when looking up moves
        Position moveStart = move.getStart();
        Position moveEnd = move.getEnd();
        //tiles from the start to the end
        Tile startTile = gameBoard[moveStart.getCol()][moveStart.getRow()];
        Tile endTile = gameBoard[moveEnd.getCol()][moveEnd.getRow()];
        //pieces from the start to the end
        Piece startPiece = startTile.getPiece();
        Piece endPiece = null;
        //if the start piece is null, there's no way for a move to be made
        if (startPiece == null) {
            return null;
        }
        //if the pawn is moving for the first time and its moving twice set the first move to true so it can't move twice again
        if (startPiece.getPieceType() == PieceType.PAWN) {
            startPiece.setFirstMove(false);
            //if the rook or king is moving for the first time set that piece to already moved, so it can't be used for castling
        } else if (startPiece.getPieceType() == PieceType.ROOK || startPiece.getPieceType() == PieceType.KING) {
            if (startPiece.firstMove()) startPiece.setFirstMove(false);

        }
        //if the end piece is occupied get the piece
        if (endTile.tileOccupied()) {
            endPiece = endTile.takePiece();
        }
        endTile.setPiece(startTile.takePiece());

        //castle the pieces if if's a castling move
        if (getPieceType(move.getEnd()) == PieceType.KING &&
                Math.abs(move.getEnd().getCol()-move.getStart().getCol()) == 2) {
            if (move.getEnd().getCol() == 6) movePiece(new Position(7, move.getEnd().getRow()),
                    new Position(5, move.getEnd().getRow()));
            else if (move.getEnd().getCol() == 2) {
                movePiece(new Position(0, move.getEnd().getRow()),
                        new Position(3, move.getEnd().getRow()));
            }
        }
        return endPiece;
    }
    // undoes the passed move and any special moves
    public void undoSpecialMove ( Move move, Piece piece, boolean firstMove, boolean pawnPromote ) {
        //reverse the move
        movePiece(move.getEnd(), move.getStart());
        //if there is a piece place the piece down at the destination of the move
        if (piece != null) setPiece(move.getEnd(), piece);
        //set the first move if there is a piece at the start position
        if (firstMove && containsPiece(move.getStart()))
            gameBoard[move.getStart().getCol()][move.getStart().getRow()].getPiece().madeFirstMove();

        // undo castling if castling has happened
        if (getPieceType(move.getStart()) == PieceType.KING &&
                gameBoard[move.getStart().getCol()][move.getStart().getRow()].getPiece().firstMove() &&
                move.getStart().getCol() == 4) {
            if (move.getStart().getCol() - move.getEnd().getCol() == -2 &&
                    containsPiece(new Position(move.getEnd().getCol() - 1, move.getEnd().getRow())) &&
                    getPieceType(new Position(move.getEnd().getCol() - 1, move.getEnd().getRow())) == PieceType.ROOK &&
                    gameBoard[move.getEnd().getCol() - 1][move.getStart().getRow()].getPiece().firstMove()) {
                setPiece(new Position(move.getEnd().getCol() + 1, move.getEnd().getRow()),
                        removePiece(new Position(move.getEnd().getCol() - 1, move.getEnd().getRow())));
            } else if (move.getStart().getCol() - move.getEnd().getCol() == 2 &&
                    containsPiece(new Position(move.getEnd().getCol() + 1, move.getEnd().getRow())) &&
                    getPieceType(new Position(move.getEnd().getCol() + 1, move.getEnd().getRow())) == PieceType.ROOK&&
                    gameBoard[move.getEnd().getCol() + 1][move.getStart().getRow()].getPiece().firstMove()) {
                setPiece(new Position(move.getEnd().getCol() - 2, move.getEnd().getRow()),
                        removePiece(new Position(move.getEnd().getCol() + 1, move.getEnd().getRow())));
            }
        }

        // replace pawn if promotion has happened
        if (pawnPromote) {
            replacePiece(move.getStart(), PieceType.PAWN);
        }
    }

}
