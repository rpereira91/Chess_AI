package helper;
/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    Class to help make moves
 */

public class Move {
    private Position start;
    private Position end;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }
    //gets the start and end position of the move
    public Position getStart() {
        return start;
    }
    public Position getEnd() {
        return end;
    }

}
