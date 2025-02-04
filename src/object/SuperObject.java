package object;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if (screenX + gp.tileSize>= 0 && screenX + gp.tileSize < gp.worldWidth && screenY + gp.tileSize >= 0 && screenY < gp.worldHeight) {


            g.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
