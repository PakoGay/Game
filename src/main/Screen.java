package main;

import java.awt.*;

/**  Любой «экран» игры должен уметь обновляться и рисоваться. */
public interface Screen {
    void update(float dt);
    void render(Graphics2D g);
}
