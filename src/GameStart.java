import java.util.Scanner;

public class GameStart {
    static Player player;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name + "!");
        //Sets player name and HP
        player = new Player(name,100,100);
        scanner.close();
    }
}