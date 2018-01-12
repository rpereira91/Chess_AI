package helper;

/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    This is a basic Position class called helper. Position, makes it easier to keep track of the
    position as opposed to passing seperate rows and columns
*/
public class Position {
    private int row;
    private int col;
    private boolean color;

    //set the row and col for the Coordinate
    public Position(int col, int row){
        this.col = col;
        this.row = row;
        //set the color for the position(used for the JTile in the GUI), if it's a white its false, if its a black square its true
        if(row%2==0){
            if(col%2==0)
                color = false;
            else
                color = true;
        }
        if(row%2!=0){
            if(col%2==0)
                color = true;
            else
                color = false;
        }
    }
    //getters
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public boolean getColor(){
        return color;
    }
    //checks if the row and column are outside the board
    public static boolean isValid(int col, int row){
        return col >= 0 && col < 8 && row >= 0 && row < 8;
    }
<<<<<<< Updated upstream

    @Override
    public boolean equals(Object obj){
        if (obj == this) {
=======
    //compares two positions
    public boolean equals(Position po){
        if(this.getCol() == po.getCol() && this.getRow() == po.getRow())
>>>>>>> Stashed changes
            return true;
        }

        if (!(obj instanceof Position)) {
            return false;
        }

        Position other = (Position) obj;

        return getRow() == other.getRow() && getCol() == other.getCol();
    }
}
