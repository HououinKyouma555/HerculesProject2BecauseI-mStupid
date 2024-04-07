package main;

import entity.Entity;

public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){

        // Finds the exact pixel of various edges of the entity's hitbox.
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Finds the tile (row/col) of the entity
        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        // This is going to check whether a collision is occuring based on the direction
        // the entity is moving. It will predict the tile the entity is attempting to move
        // into and either allow or prevent that movement based on whether collision is true
        // for that tile. For example, if the entity is moving upwards, this will check the
        // boolean 'collision' for the tile above and to the right of the entity AND for the 
        // tile above and to the left of the entity. In this way, this is able to predict
        // whether a collision is about to occur and make collisionOn = true if it is, preventing
        // the entity from moving in that direction.
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player){
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++){
            if (gp.obj[i] != null){

                // Get entity's solid area position
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                // get objects's solid area position
                gp.obj[i].solidArea.x += gp.obj[i].worldX;
                gp.obj[i].solidArea.y += gp.obj[i].worldY;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)){
                            if (gp.obj[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if (player == true){
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)){
                            if (gp.obj[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if (player == true){
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)){
                            if (gp.obj[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if (player == true){
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)){
                            if (gp.obj[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if (player == true){
                                index = i;
                            }
                        }
                        break;
                    default:
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    //NPC or monster collision
    public int checkEntity(Entity entity, Entity[] target){
        int index = 999;

        for (int i = 0; i < target.length; i++){
            if (target[i] != null){

                // Get entity's solid area position
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                // get objects's solid area position
                target[i].solidArea.x += target[i].worldX;
                target[i].solidArea.y += target[i].worldY;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)){
                            
                                entity.collisionOn = true;
                            
                            
                                index = i;
                            
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)){
                            
                                entity.collisionOn = true;
                            
                            
                                index = i;
                            
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)){
                            
                                entity.collisionOn = true;
                            
                            
                                index = i;
                            
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)){
                            
                                entity.collisionOn = true;
                            
                            
                                index = i;
                            
                        }
                        break;
                    default:
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    public void checkPlayer(Entity entity){
         // Get entity's solid area position
         entity.solidArea.x += entity.worldX;
         entity.solidArea.y += entity.worldY;

         // get objects's solid area position
         gp.player.solidArea.x += gp.player.worldX;
         gp.player.solidArea.y += gp.player.worldY;

         switch (entity.direction) {
             case "up":
                 entity.solidArea.y -= entity.speed;
                 if (entity.solidArea.intersects(gp.player.solidArea)){
                     
                         entity.collisionOn = true;
                 }
                 break;
             case "down":
                 entity.solidArea.y += entity.speed;
                 if (entity.solidArea.intersects(gp.player.solidArea)){
                     
                         entity.collisionOn = true;
                 }
                 break;
             case "left":
                 entity.solidArea.x -= entity.speed;
                 if (entity.solidArea.intersects(gp.player.solidArea)){
                     
                         entity.collisionOn = true;
                 }
                 break;
             case "right":
                 entity.solidArea.x += entity.speed;
                 if (entity.solidArea.intersects(gp.player.solidArea)){
                     
                         entity.collisionOn = true;
                     
                 }
                 break;
             default:
                 break;
         }
         entity.solidArea.x = entity.solidAreaDefaultX;
         entity.solidArea.y = entity.solidAreaDefaultY;
         gp.player.solidArea.x = gp.player.solidAreaDefaultX;
         gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }

}
