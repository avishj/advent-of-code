import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SolutionD2Q1 {

    private static String RED = "red";
    private static String GREEN = "green";
    private static String BLUE = "blue";

    public static void main(String[] args) {
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 2/Input.txt"))) {
            long sum = 0;
            game: while (input.hasNextLine()) {
                String line = input.nextLine();
                int gameId = Integer.parseInt(line.split(":")[0].split(" ")[1]);
                String[] showings = line.split(":")[1].split("; ");
                for (String showing : showings) {
                    String[] countColors = showing.split(", ");
                    for (String countColor : countColors) {
                        int count = Integer.parseInt(countColor.trim().split(" ")[0]);
                        String color = countColor.trim().split(" ")[1];
                        if ((color.equals(RED) && count > 12) || (color.equals(GREEN) && count > 13)
                                || (color.equals(BLUE) && count > 14)) {
                            continue game;
                        }
                    }
                }
                sum += gameId;
            }
            System.out.println("Sum of Game ID's: " + sum);
        } catch (FileNotFoundException e) {
            System.err.println("Input file could not be opened!");
        }
    }
}
