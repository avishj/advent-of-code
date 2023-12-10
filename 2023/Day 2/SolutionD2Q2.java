import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SolutionD2Q2 {

    private static String RED = "red";
    private static String GREEN = "green";
    private static String BLUE = "blue";

    public static void main(String[] args) {
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 2/Input.txt"))) {
            long sum = 0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                int minRed = 0, minGreen = 0, minBlue = 0;
                String[] showings = line.split(":")[1].split("; ");
                for (String showing : showings) {
                    String[] countColors = showing.split(", ");
                    for (String countColor : countColors) {
                        int count = Integer.parseInt(countColor.trim().split(" ")[0]);
                        String color = countColor.trim().split(" ")[1];
                        if (color.equals(RED) && count > minRed) {
                            minRed = count;
                        } else if (color.equals(GREEN) && count > minGreen) {
                            minGreen = count;
                        } else if (color.equals(BLUE) && count > minBlue) {
                            minBlue = count;
                        }
                    }
                }
                sum += minRed * minGreen * minBlue;
            }
            System.out.println("Sum of Power's: " + sum);
        } catch (FileNotFoundException e) {
            System.err.println("Input file could not be opened!");
        }
    }
}
