package entity;

import main.GameplayScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Enemy extends Entity {

    // ==== настройки врага =====
    private static final int SPEED   = 2;   // шаг в тайлах/кадр
    private static final int AI_RATE = 30;  // раз в N кадров пересчёт направления
    // ==========================

    private int aiCounter = 0;

    /* 0-UP,1-DOWN,2-LEFT,3-RIGHT  ×  8 кадров */
    private final BufferedImage[][] frames = new BufferedImage[4][8];
    private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;

    public Enemy(GameplayScreen gp, int startWorldX, int startWorldY) {
        super(gp);
        this.worldX = startWorldX;
        this.worldY = startWorldY;
        this.speed  = SPEED;
        this.direction = "down";

        solidArea = new Rectangle(8, 16, 32, 32);   // хит-бокс поменьше
        loadImages();
    }

    /* ---------- ресурс-лоадер ---------- */
    private void loadImages() {
        try {
            for (int i = 0; i < 8; i++) {
                frames[UP][i]    = ImageIO.read(getClass().getResource("/player/up"    + (i + 1) + ".png"));
                frames[DOWN][i]  = ImageIO.read(getClass().getResource("/player/down"  + (i + 1) + ".png"));
                frames[LEFT][i]  = ImageIO.read(getClass().getResource("/player/left"  + (i + 1) + ".png"));
                frames[RIGHT][i] = ImageIO.read(getClass().getResource("/player/right" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ---------- простейший AI + анимация ---------- */
    public void update() {

        // раз в AI_RATE кадров пересчитать направление — «ползём к игроку»
        if (++aiCounter >= AI_RATE) {
            aiCounter = 0;

            int dx = gp.player.worldX - worldX;
            int dy = gp.player.worldY - worldY;

            if (Math.abs(dx) > Math.abs(dy)) {
                direction = (dx < 0) ? "left" : "right";
            } else {
                direction = (dy < 0) ? "up" : "down";
            }
        }

        // столкновения с тайлами
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

        // счётчик кадров анимации
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = spriteNum % 8 + 1;   // кадры 1-8 по кругу
            spriteCounter = 0;
        }
    }

    /* ---------- вывод на экран ---------- */
    public void draw(Graphics2D g2) {

        // перевод мировых координат во «внутриэкраные»
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // за пределы экрана не рисуем
        if (screenX + gp.tileSize < 0 || screenX > gp.screenWidth ||
                screenY + gp.tileSize < 0 || screenY > gp.screenHeight) {
            return;
        }

        int dir = switch (direction) {
            case "up"    -> UP;
            case "down"  -> DOWN;
            case "left"  -> LEFT;
            default      -> RIGHT;
        };
        BufferedImage img = frames[dir][spriteNum - 1];
        g2.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
    @Override
    public Enemy clone() {

        return (Enemy) super.clone();
    }
}
