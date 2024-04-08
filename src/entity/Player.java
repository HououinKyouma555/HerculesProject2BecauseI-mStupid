package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {
        
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    int boostCount = 0;

    // player constructor setting default values
    public Player (GamePanel gp, KeyHandler keyH){

        super(gp);

        this.gp = gp;
        this.keyH = keyH;

        // sets the screen
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        // hitbox dimensions
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getImage();
    }

    // initial placement of character on map
    public void setDefaultValues(){
        worldX = 23 * gp.tileSize;
        worldY = 21 * gp.tileSize;
        speed = 5;
        direction = "down";
    }
    
    // Various player images for animation
    public void getImage(){

        up1 = setUp("player/boy_up_1");
        up2 = setUp("player/boy_up_2");
        down1 = setUp("player/boy_down_1");
        down2 = setUp("player/boy_down_2");
        left1 = setUp("player/boy_left_1");
        left2 = setUp("player/boy_left_2");
        right1 = setUp("player/boy_right_1");
        right2 = setUp("player/boy_right_2");

    }

    public BufferedImage setUp(String filePath){
        
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/" + filePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
        
    }


    public void update(){
        if (keyH.downPressed == true || keyH.upPressed == true 
        || keyH.rightPressed == true || keyH.leftPressed == true){
            // When WASD is pressed, the direction of the player changes
            if (keyH.upPressed == true){
                direction = "up";
                
            }
            else if (keyH.downPressed == true){
                direction = "down";
                
            }
            else if (keyH.rightPressed == true){
                direction = "right";
                
            }
            else if (keyH.leftPressed == true){
                direction = "left";
                
            }

            // If running is true, double speed. Else, walk
            if (keyH.running == true){
                speed = 10;
                boostCount++;
                if (boostCount == 1){
                    gp.playSE(3);
                }
            }
            else if (keyH.running == false){
                speed = 5;
                boostCount = 0;
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //IF COLLISIONON IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false){
                switch (direction){
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                }
            }
            
            // spriteCounter increases every time the frame is updated (60 times per second). When
            // spriteCounter exceeds 10, the spriteNum of the character changes and spriteCounter is
            // refreshed. The result is that 6 times per second, the character changes costume to create
            // the walking animation.
            spriteCounter++;
            if (spriteCounter >= 10){
                if (spriteNum == 1){
                    spriteNum = 2;
                }
                else if (spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        
    }

    public void pickUpObject(int i){
        if (i != 999){
        }
    }

    public void interactNPC(int i){
        if (i != 999){
            if (gp.keyH.enterPressed == true){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
        gp.keyH.enterPressed = false;
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;

        // spriteNum corresponds to which costume/image the player has
        switch(direction){
        case "up":
            if (spriteNum == 1){
                image = up1;
            }
            if (spriteNum == 2){
                image = up2;
            }
            break;
        case "down":
            if (spriteNum == 1){
                image = down1;
            }
            if (spriteNum == 2){
                image = down2;
            }
            break;
        case "right":
            if (spriteNum == 1){
                image = right1;
            }
            if (spriteNum == 2){
                image = right2;
            }
            break;
        case "left":
            if (spriteNum == 1){
                image = left1;
            }
            if (spriteNum == 2){
                image = left2;
            }
            break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
