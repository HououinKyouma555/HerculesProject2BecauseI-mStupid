package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity{
    
    // position and speed
    public int worldX, worldY;
    public int speed;
    GamePanel gp;

    // Each of these is an image corresponding to direction for animation's sake
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;

    // Used for animation
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Used for NPC/monster movement
    public int actionLockCounter;

    // Hitbox (or the "solidArea" of the entity). collisionOn will prevent movement.
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn;

    public String dialogues[] = new String[20];
    int dialogueIndex = 0;
    
    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){

    }

    public void speak(){
        if (dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "right":
                direction = "left";
                break;
            case "left":
                direction = "right";
                break;
        }
    }

    public void update(){
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

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
    


    public void draw(Graphics2D g2){
        
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

    
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY
            ){
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

   
}
