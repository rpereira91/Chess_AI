package logic;

import helper.RowCol;

public class Move {
    private RowCol start;
    private RowCol end;

    Move(RowCol start, RowCol end){
        this.start = start;
        this.end = end;
    }

    public RowCol getStart() {
        return start;
    }

    public RowCol getEnd() {
        return end;
    }
}
