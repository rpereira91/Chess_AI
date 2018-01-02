package logic;

import helper.Position;

public class Move {
    private Position start;
    private Position end;

    Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }
}
