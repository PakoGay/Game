package main;

import entity.Player;
import tile.TileManager;

import javax.sound.sampled.Clip;
import java.awt.*;
import main.SoundManager;
import javax.sound.sampled.Clip;

/**
 * Логика уровня; имена полей оставлены для совместимости.
 */
public class GameplayScreen implements Screen {

    // ==== прежние поля GamePanel ==========================
    public final int originalTileSize = 16;
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale;       // 48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth  = maxScreenCol * tileSize;    // 768
    public final int screenHeight = maxScreenRow * tileSize;    // 576

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public final int FPS = 60;// целевой FPS
    // внутри GameplayScreen
    public final Clip walkSound  = SoundManager.get("/sound/walk.wav");
    public final Clip musicLoop = SoundManager.get("/sound/music.wav");

            // =======================================================

    // старые ссылки (имена сохранены)
    public final TileManager tileM;
    public final CollisionChecker cChecker;
    public final Player player;
    public final KeyHandler keyH = new KeyHandler();

    public GameplayScreen() {
        tileM    = new TileManager(this);
        cChecker = new CollisionChecker(this);
        player   = new Player(this, keyH);
        musicLoop.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // --- Screen impl ---
    @Override public void update(float dt) {
        player.update();
    }

    @Override public void render(Graphics2D g) {
        tileM.draw(g);
        player.draw(g);
    }
}
