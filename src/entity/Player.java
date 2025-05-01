package entity;

import main.GameplayScreen;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GameplayScreen gp, KeyHandler keyH){
        super(gp);
        this.keyH=keyH;

        screenX = gp.screenWidth / 2-(gp.tileSize/2);
        screenY = gp.screenHeight / 2-(gp.tileSize/2);

        solidArea=new Rectangle();
        solidArea.x=20;
        solidArea.y=25;
        solidArea.width=15;
        solidArea.height=15;



        setDefaulValues();

        getPlayerImage();
    }
    public void setDefaulValues(){
        worldX = gp.tileSize*25;
        worldY = gp.tileSize*27;
        speed = 5;
        direction = "down";
        health = 20;
    }
    BufferedImage[][] frames = new BufferedImage[4][8]; // 0-UP,1-DOWN,2-LEFT,3-RIGHT

    private static final int UP=0, DOWN=1, LEFT=2, RIGHT=3;
    public void getPlayerImage(){
        try{
            for (int i = 0; i < 8; i++) {
                frames[UP][i]    = ImageIO.read(getClass().getResource("/player/up"    + (i+1) + ".png"));
                frames[DOWN][i]  = ImageIO.read(getClass().getResource("/player/down"  + (i+1) + ".png"));
                frames[LEFT][i]  = ImageIO.read(getClass().getResource("/player/left"  + (i+1) + ".png"));
                frames[RIGHT][i] = ImageIO.read(getClass().getResource("/player/right" + (i+1) + ".png"));
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void update() {

        boolean moving = keyH.upPressed || keyH.downPressed ||
                keyH.leftPressed || keyH.rightPressed;

        if (moving) {
            if (keyH.upPressed)    direction = "up";
            if (keyH.downPressed)  direction = "down";
            if (keyH.leftPressed)  direction = "left";
            if (keyH.rightPressed) direction = "right";

            collisionOn = false;
            gp.cChecker.checkTile(this);

            if (!collisionOn) {
                switch (direction) {
                    case "up"    -> worldY -= speed;
                    case "down"  -> worldY += speed;
                    case "left"  -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            // Счётчик анимации
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = spriteNum % 8 + 1;  // 1-8 по кругу
                spriteCounter = 0;
            }
            if (moving && !gp.walkSound.isRunning()) {
                gp.walkSound.setFramePosition(0);
                gp.walkSound.start();
            }
        }
    }
    public void draw(Graphics2D g2){
        int dir = switch (direction) {      // перевод строки→индекс
            case "up"    -> UP;
            case "down"  -> DOWN;
            case "left"  -> LEFT;
            default      -> RIGHT;
        };
        BufferedImage img = frames[dir][spriteNum-1];
        g2.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
