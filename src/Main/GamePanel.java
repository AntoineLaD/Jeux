package Main;

import Entity.Player;
import Tile.TileManager;
import object.SuperObject;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //screen settings
    final int originalTileSize = 16; //16x16 tile for every character
    final int scale = 3;

    public  final int tileSize = originalTileSize * scale; // 48 x 48 tile
    public final int maxScreenColumns = 16;
    public final int maxScreenRows = 12;
    public final int screenWidth = tileSize * maxScreenColumns; // 768 px
    public final int screenHeight = tileSize * maxScreenRows; // 576 px

    //world settings

    public final int maxWorldColumns = 50;
    public final int maxWorldRows = 50;
    public final int worldWidth = tileSize * maxWorldColumns;
    public final int worldHeight = tileSize * maxWorldRows;
    // FPS
    int fps = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler KeyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, KeyH);
    public SuperObject[] obj = new SuperObject[10];


    // player default position
    int playerX= 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.green);
        this.setDoubleBuffered(true);
        this.addKeyListener(KeyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();

    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {

        double drawInterval = 1000000000 / fps; // 0.0166 seconds
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int fpsCounter = 0;

            while(gameThread != null){

                currentTime = System.nanoTime();
                deltaTime += (currentTime - lastTime) / drawInterval;
                timer += currentTime - lastTime;
                lastTime = currentTime;

                if (deltaTime >= 1) {
                    update();
                    repaint();
                    deltaTime--;
                    fpsCounter++;

                }
                if(timer >= 1000000000){
                    timer = 0;
                    System.out.println("FPS: " + fpsCounter);
                    fpsCounter = 0;
                }

            }
    }
    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //tile
        tileManager.draw(g2);
        //object
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }
        //player
        player.draw(g2);

        g2.dispose();
    }
}
