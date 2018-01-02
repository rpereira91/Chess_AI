package helper;

//This is a basic Coordinate class called helper.Position, makes it easier to keep track of the
// position as opposed to passing seperate rows and columns
public class Position {
    private int row;
    private int col;
    //default constructor
    Position(){
    }
    //set the row and col for the Coordinate
    Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
