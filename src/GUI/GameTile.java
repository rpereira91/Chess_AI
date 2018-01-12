/*
Class:
    COSC 3P71
Author:
    Ralph Pereira - 4554879
    Sammi Mak - 5931464
Description:
    This class has the logic for one tile
 */
package GUI;

import helper.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameTile extends JPanel {
    Dimension dimension = new Dimension(10, 10);
    Position position;

    public GameTile(Position position){
        this.position = position;
        setPreferredSize(dimension);
        setBackground();
        setVisible(true);
    }
    //this is used to highlight moves, the colors are different for white and black squares
    public void selectTile(){
        setBackground(position.getColor() ? new Color(200,155,20) : new Color(255,220,135));
    }

    public void updateTile(){
        removeAll();
    }

    public void resetBackground(){
        setBackground();
    }
    //sets the white and black tiles
    private void setBackground(){
        setBackground(position.getColor() ? Color.GRAY : Color.WHITE);
    }
    //sets the image for the piece based on the name passed to it
    public void updateTile(String pieceType){
        try {
            removeAll();
            BufferedImage img = ImageIO.read(new File("Images/" + pieceType +".png"));
            this.add(new JLabel(new ImageIcon(img)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        revalidate();
    }
}
