package entity;

import java.util.Random;

import main.GamePanel;

public class Lion extends Entity {
    public Lion(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
    }

     public void getImage(){

        up1 = setUp("lion/LionDownLeft");
        up2 = setUp("lion/LionDownRight");
        down1 = setUp("lion/LionDownLeft");
        down2 = setUp("lion/LionDownRight");
        left1 = setUp("lion/LionLeft1");
        left2 = setUp("lion/LionLeft2");
        right1 = setUp("lion/LionRight1");
        right2 = setUp("lion/LionRight2");

    }

    public void setAction(){
        if (actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(4);

            switch (i) {
                case 0:
                    direction = "up";
                    break;
                case 1:
                    direction = "down";
                    break;
                case 2:
                    direction = "right";
                    break;
                case 3:
                    direction = "left";
                    break;
            }
            actionLockCounter = 0;
        }
        actionLockCounter++;
    }

    public void speak(){
        super.speak();
    }
}
