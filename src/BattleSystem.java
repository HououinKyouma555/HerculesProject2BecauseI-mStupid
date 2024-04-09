import java.util.Random;
import java.util.Scanner;

public class BattleSystem{
    static Player player;
    static Enemy enemy;
    static int action;
    static Sword sword;
    static Bow bow;
    static Random random = new Random();
    public static void battle(){
    try (Scanner input = new Scanner(System.in)) {
        while (true) {
            System.out.println("The enemy's health is " + enemy.hp + "/" + enemy.maxHP);
            System.out.println("Your health is " + player.hp + "/" + player.maxHP);
            //This battle system needs to be modified to work with the GUI, so this is temporary.
            System.out.println("Choose an option, 1 (Club), 2 (Bow)");
            
            action = input.nextInt();
            
            if (action==1){
                enemy.hp -= sword.swordAttackValue;
                System.out.println("The enemy took" + sword.swordAttackValue +"!");
            }
            if (action==2){
                int randomNumber = random.nextInt(101);

                if (randomNumber > 75) {
                    System.out.println("The shot landed!");
                    enemy.hp -=bow.bowAttackValue;
                    System.out.println("The enemy took" + bow.bowAttackValue +"!");
                } else {
                    System.out.println("The shot missed...");
                }    
            }
            //Enemy attack scales with players reputation
            int randomAttack = random.nextInt(25*player.reputation);
            player.hp -= randomAttack;   
            //Check if player or enemy are dead
            if (player.hp<=0){
                System.out.println("YOU LOSE!");
            }
            if (enemy.hp<=0){
                System.out.println("YOU WIN!");
                player.reputation += 1;
            }
            
        }
    }
}

}






/*static Player player;
    private Random random = new Random();
    private Sword sword = new Sword();
    private Bow bow = new Bow();

    public void attackBow() {
        int randomNumber = random.nextInt(101);

        if (randomNumber > 70) {
            System.out.println("The shot landed!");
            dealDamage(new Player(), new Monster(), bow.bowAttackValue);
        } else {
            System.out.println("The shot missed...");
        }
    }

    public void attackClub() {
        dealDamage(new Player(), new Monster(), sword.swordAttackValue);
    }

    public void dealDamage(Player player, Monster monster, int damage) {
        monster.setEnemyHp(monster.getEnemyHp() - damage);
        System.out.println("Monster took " + damage + " damage.");
        System.out.println("The monster's health is: " + monster.getEnemyHp());
        
        if (monster.getEnemyHp() <= 0) {
            System.out.println("Monster is defeated!");
            endBattle();
        }
    }

    public void takeDamage(Player player, int damage) {
        player.setPlayerHp(player.getPlayerHp() - damage);
        System.out.println("You took " + damage + " damage.");
        System.out.println("Your current health is: " + player.getPlayerHp());
        
        if (player.getPlayerHp() <= 0) {
            System.out.println("You Died!");
        }
    }

    public void endBattle(){
        Player player = new Player();
        player.setPlayerHp(player.getPlayerHp()*player.reputation);
    }

    
*/