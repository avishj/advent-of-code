import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {

    private enum Direction {
        LEFT,
        RIGHT
    }

    public static void main(String[] args) {
        long sum = 0;
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 1/Input.txt"))) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                int leftDigit = scanForNumber(line, Direction.LEFT);
                int rightDigit = scanForNumber(line, Direction.RIGHT);
                System.out.println(leftDigit + "" + rightDigit);
                if (leftDigit != -1 && rightDigit != -1) {
                    sum += (leftDigit * 10) + rightDigit;
                }
            }
            System.out.println("Sum of Calibration Values: " + sum);
        } catch (FileNotFoundException e) {
            System.err.println("Input file could not be opened!");
        }
    }

    private static int getNumIfCharIsNum(String line, int pos) {
        int value = (line.charAt(pos)) - 0x30;
        if (value >= 0 && value <= 9) {
            return value;
        }
        return -1;
    }

    private static int scanForNumber(String line, Direction direction) {
        if (line.length() <= 0)
            return -1;
        if (direction == Direction.LEFT) {
            for (int i = 0; i < line.length(); ++i) {
                int value = getNumIfCharIsNum(line, i);
                if (value != -1)
                    return value;
            }
        } else if (direction == Direction.RIGHT) {
            for (int i = line.length() - 1; i >= 0; --i) {
                int value = getNumIfCharIsNum(line, i);
                if (value != -1)
                    return value;
            }
        }
        return -1;
    }
}