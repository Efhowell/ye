import java.util.Scanner;

public class HitoriTextDriver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the filename of the Hitori puzzle:");
        String filename = scanner.nextLine();
        
        Hitori puzzle = new Hitori(filename);
        
        if (puzzle.solve()) {
            System.out.println("Solved Puzzle:\n" + puzzle.toString());
        } else {
            System.out.println("No solution exists for this puzzle.");
        }
    }
}
