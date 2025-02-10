package Entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int hasKey = 0;


    public Player(GamePanel gp, KeyHandler kh) {
        this.gp = gp;
        this.keyH = kh;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8,16,32,32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try {
           up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
           up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
           down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
           down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
           left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
           left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
           right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
           right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void update(){
        if(keyH.up || keyH.down || keyH.left || keyH.right) {
            if (keyH.up) {

                direction = "up";

            } else if (keyH.down) {

                direction = "down";

            } else if (keyH.left) {

                direction = "left";

            } else if (keyH.right) {

                direction = "right";


            }
            //check tile collision
            collision = false;
            gp.cChecker.checkTile(this);

            //check object collision

           int objIndex = gp.cChecker.checkObject(this,true);
           pickUpObject(objIndex);




            if (!collision){
                switch (direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }



            //check world bounds
            if(worldX < 0){
                worldX = 0;
            }
            if(worldY < 0){
                worldY = 0;
            }
            if(worldX > gp.worldWidth - gp.tileSize){
                worldX = gp.worldWidth - gp.tileSize;
            }
            if(worldY > gp.worldHeight - gp.tileSize){
                worldY = gp.worldHeight - gp.tileSize;
            }



                spriteCounter++;
                if (spriteCounter > 14) {
                    if (spriteNumber == 1) {
                        spriteNumber = 2;
                    } else {
                        spriteNumber = 1;
                    }
                    spriteCounter = 0;
                }
            }

    }

    public void pickUpObject(int i){
        if ( i != 999){
           String objName = gp.obj[i].name;
           switch (objName){
                case "key":
                     hasKey++;
                     gp.obj[i] = null;
                    System.out.printf("You have %d keys left\n", hasKey);

                    break;
                case "door", "chest":
                    if (hasKey > 0){
                        gp.obj[i] = null;
                        hasKey--;
                        System.out.printf("You have %d keys left\n", hasKey);
                    }
                     break;

               case "boots":
                   speed += 3;
                     gp.obj[i] = null;
                        System.out.printf("You have picked up boots, your speed is now %d\n", speed);
                        break;


           }
        }
    }


    public void draw (Graphics2D g2){

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNumber == 1) {
                    image = up1;

                }
                if (spriteNumber == 2) {
                    image = up2;

                }

                break;
            case "down":
                if (spriteNumber == 1) {
                    image = down1;

                }
                if (spriteNumber == 2) {
                    image = down2;

                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = left1;

                }
                if (spriteNumber == 2) {
                    image = left2;

                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = right1;

                }
                if (spriteNumber == 2) {
                    image = right2;

                }
                break;
        }


        int drawX = screenX;
        int drawY = screenY;

        if (worldX < gp.screenWidth / 2 - gp.tileSize / 2) {
            drawX = worldX;
        }
        if (worldY < gp.screenHeight / 2 - gp.tileSize / 2) {
            drawY = worldY;
        }
        if (worldX > gp.worldWidth - gp.screenWidth / 2 - gp.tileSize / 2) {
            drawX = gp.screenWidth - (gp.worldWidth - worldX);
        }
        if (worldY > gp.worldHeight - gp.screenHeight / 2 - gp.tileSize / 2) {
            drawY = gp.screenHeight - (gp.worldHeight - worldY);
        }
        g2.drawImage(image, drawX, drawY, gp.tileSize, gp.tileSize, null);
    }

}

