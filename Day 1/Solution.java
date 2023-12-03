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

    private static int getNumIfCharIsNum(String line, char pos) {
        int value = (line.charAt(pos)) - 0x30;
        if (value >= 0 && value <= 9) {
            return value;
        }
        return -1;
    }

    private static int scanForNumber(String line, Direction direction) {
        if (line.length() <= 0)
            return -1;
        int start = -1;
        int end = -1;
        int delta;
        if (direction == Direction.LEFT) {
            for (int i = 0; i < line.length(); i++)
        } else if (direction == Direction.RIGHT) {
            start = line.length() - 1;
            end = 0;
            delta = -1;
        } else {
            return -1;
        }
        while (start != end) {
            int value = (line.charAt(start)) - 0x30;
            if (value >= 0 && value <= 9) {
                return value;
            }
            start += delta;
        }
        return -1;
    }
}