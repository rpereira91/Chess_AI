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

    public void selectTile(){
        setBackground(position.getColor() ? new Color(200,155,20) : new Color(255,220,135));
    }

    public void updateTile(){
        removeAll();
    }

    public void resetBackground(){
        setBackground();
    }

    private void setBackground(){
        setBackground(position.getColor() ? Color.GRAY : Color.WHITE);
    }

    public void updateTile(String pieceType){
        try {
            BufferedImage img = ImageIO.read(new File("Images/" + pieceType +".png"));
            this.add(new JLabel(new ImageIcon(img)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        revalidate();
    }
}
