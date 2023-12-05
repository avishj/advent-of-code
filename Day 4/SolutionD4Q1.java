import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class SolutionD4Q1 {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 4/Input.txt"))) {
            long sum = 0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                Set<Integer> winningNumbers = Arrays.stream(line.split(":")[1].split("\\|")[0].trim().split("\\s+"))
                        .filter(v -> !v.equals("")).map(Integer::parseInt).collect(Collectors.toSet());
                Set<Integer> ourNumbers = Arrays.stream(line.split(":")[1].split("\\|")[1].trim().split("\\s+"))
                        .filter(v -> !v.equals("")).map(Integer::parseInt).collect(Collectors.toSet());
                winningNumbers.retainAll(ourNumbers);
                sum += (int) Math.pow(2, winningNumbers.size() - 1);
            }
            System.out.println("Total Points: " + sum);
        } catch (FileNotFoundException e) {
            System.err.println("Input file could not be opened!");
        }
    }
}
