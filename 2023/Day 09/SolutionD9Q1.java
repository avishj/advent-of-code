import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SolutionD9Q1 {

    private static List<Integer> solve(List<Integer> arr) {
        if (arr.stream().filter(v -> v != 0).count() == 0) {
            arr.add(0);
            return arr;
        }
        List<Integer> diff = new ArrayList<>();
        for (int i = 0; i < arr.size() - 1; ++i) {
            diff.add(arr.get(i + 1) - arr.get(i));
        }
        diff = solve(diff);
        arr.add(arr.getLast() + diff.getLast());
        return arr;
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 9/Input.txt"))) {
            int sum = 0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                List<Integer> nums = solve(Arrays.stream(line.split("\\s+"))
                        .filter(s -> !s.isEmpty())
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()));
                sum += nums.getLast();
            }
            System.out.println("The sum is: " + sum);
        } catch (FileNotFoundException e) {
            System.err.println("Input file could not be opened!");
        }
    }
}
