/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    Move Logic with positions
*/
package logic;

import helper.Position;

public class Move {
    private Position start;
    private Position end;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    @Override
    public String toString(){
        return "Start: "+start.getCol() + "," + start.getRow() + "\tEnd: " + end.getCol() + "," +end.getRow();
    }
}
