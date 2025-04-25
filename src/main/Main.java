package main;


import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // 1. Создаём первый уровень (бывший GamePanel с логикой)
        GameplayScreen level1 = new GameplayScreen();

        // 2. Оборачиваем его Canvas-ом, где крутится цикл
        GameCanvas game = new GameCanvas(level1);

        // 3. Swing-окно — то же, что раньше
        JFrame window = new JFrame("Final game");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.add(game);                       // добавляем Canvas
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // 4. Регистрируем клавиатуру и запускаем игровой поток
        game.addKeyListener(level1.keyH);     // keyH — то же имя
        game.start();                           // вместо startGameThread()
    }
}
