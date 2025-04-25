package tile;


import main.GameplayScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GameplayScreen gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GameplayScreen gp) {
        this.gp = gp;
        tile = new Tile[30];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/map.txt");
    }

    public void getTileImage() {

        // пути к PNG-файлам (индекс совпадает с номером tile[])
        String[] tex = {
                "/tiles/grass.png",              // 0
                "/tiles/road.png",               // 1
                "/tiles/tree.png",               // 2
                "/tiles/house.png",              // 3
                "/tiles/workshop.png",           // 4
                "/tiles/house2.png",             // 5
                "/tiles/house3.png",             // 6
                "/tiles/road2.png",              // 7
                "/tiles/vertical_road_gate.png", // 8
                "/tiles/hor_road_gate.png",      // 9
                "/tiles/tower_door.png",         //10
                "/tiles/tower2.png",             //11
                "/tiles/tower3.png",             //12
                "/tiles/tower4.png",             //13
                "/tiles/tower5.png",             //14
                "/tiles/knight1.png",            //15
                "/tiles/knightleft.png",         //16
                "/tiles/knightup.png",           //17
                "/tiles/lnightright.png",        //18
                "/tiles/1castle.png",            //19
                "/tiles/2castle.png",            //20
                "/tiles/3castle.png",            //21
                "/tiles/4castle.png",            //22
                "/tiles/barr.png",               //23
                "/tiles/ack.png"                 //24
        };

        // флаги коллизии (true, если по тайлу нельзя пройти)
        boolean[] col = {
                false, // 0 grass
                false, // 1 road
                true,  // 2 tree
                true,  // 3 house
                true,  // 4 workshop
                true,  // 5 house2
                true,  // 6 house3
                false, // 7 road2
                true,  // 8 vertical gate
                true,  // 9 horizontal gate
                true,  //10 tower door
                true,  //11-14 towers
                true,
                true,
                true,
                true,  //15 knight1
                true,  //16 knight left
                true,  //17 knight up
                true,  //18 knight right
                true,  //19-22 castles
                true,
                true,
                true,
                true,  //23 barr
                true   //24 ack
        };

        // заполняем массив tile[]
        for (int i = 0; i < tex.length; i++) {
            tile[i] = new Tile();
            tile[i].image = Assets.get(tex[i]);
            tile[i].collision = col[i];
        }
    }
    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col=0;
            int row=0;

            while(col< gp.maxWorldCol&&row<gp.maxWorldRow){
                String line=br.readLine();
                while(col<gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row]=num;
                    col++;
                }if(col==gp.maxWorldCol){
                    col =0;
                    row++;
                }
            }br.close();

        }catch (Exception e){

        }
    }
    public void draw(Graphics2D g2) {

        int firstCol = Math.max(0, (gp.player.worldX - gp.player.screenX) / gp.tileSize);
        int firstRow = Math.max(0, (gp.player.worldY - gp.player.screenY) / gp.tileSize);
        int lastCol  = Math.min(gp.maxWorldCol,
                (gp.player.worldX + gp.player.screenX + gp.tileSize) / gp.tileSize + 1);
        int lastRow  = Math.min(gp.maxWorldRow,
                (gp.player.worldY + gp.player.screenY + gp.tileSize) / gp.tileSize + 1);

        for (int col = firstCol; col < lastCol; col++) {
            for (int row = firstRow; row < lastRow; row++) {
                int tileNum = mapTileNum[col][row];
                int worldX  = col * gp.tileSize;
                int worldY  = row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                g2.drawImage(tile[tileNum].image, screenX, screenY,
                        gp.tileSize, gp.tileSize, null);
            }
        }
    }
}
