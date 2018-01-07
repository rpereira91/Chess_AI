package helper;

//This is a basic Coordinate class called helper.Position, makes it easier to keep track of the
// position as opposed to passing seperate rows and columns
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
    public boolean getColor(){
        return color;
    }

    public static boolean isValid(int col, int row){
        return col >= 0 && col < 8 && row >= 0 && row < 8;
    }
}
