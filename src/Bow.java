public class Bow {
    static Player player;
    //Scales damage with player reputation
    public int baseBowAttack=60;
    public int bowAttackValue=baseBowAttack*player.reputation;
}
