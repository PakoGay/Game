package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    public int worldX,worldY;
    public int speed;
    public String direction;
    public int spriteCounter=0;
    public int spriteNum=4;
    public Rectangle solidArea;
    public boolean collisionOn = false;
}
