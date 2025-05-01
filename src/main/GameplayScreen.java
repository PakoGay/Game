package main;

import entity.Player;
import tile.TileManager;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import entity.Enemy;

import main.SoundManager;
import javax.sound.sampled.Clip;

//Логика уровня; имена полей оставлены для совместимости.

public class GameplayScreen implements Screen {
    public final int originalTileSize = 16;
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale;       // 48
    public final int maxScreenCol = 32;
    public final int maxScreenRow = 21;
    public final int screenWidth  = maxScreenCol * tileSize;    // 1635
    public final int screenHeight = maxScreenRow * tileSize;    // 1024

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public final int FPS = 60;// целевой FPS

    public final Clip walkSound  = SoundManager.get("/sound/walk.wav");
    public final Clip musicLoop = SoundManager.get("/sound/music.wav");

    public final TileManager tileM;
    public final CollisionChecker cChecker;
    public final Player player;
    public final Enemy enemy;
    public final KeyHandler keyH = new KeyHandler();
    public final List<Enemy> enemies = new ArrayList<>();

    public GameplayScreen() {
        tileM    = new TileManager(this);
        cChecker = new CollisionChecker(this);
        player   = new Player(this, keyH);
        musicLoop.loop(Clip.LOOP_CONTINUOUSLY);
        enemy = new Enemy(this, tileSize * 10, tileSize * 10);
        Enemy clone = (Enemy) enemy.clone();
        clone.worldX = tileSize * 12;   // можно менять стартовую позицию
        clone.worldY = tileSize * 12;
        enemies.add(enemy);
        enemies.add(clone);

    }

    @Override public void update(float dt) {
        player.update();
        for (Enemy e : enemies) e.update();
    }

    @Override public void render(Graphics2D g) {
        tileM.draw(g);
        for (Enemy e : enemies) e.draw(g);
        player.draw(g);
        g.setColor(Color.WHITE);
        g.drawString("Health: " + player.health, screenWidth - 100, 25);
    }
}