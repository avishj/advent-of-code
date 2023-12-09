import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolutionD6Q1 {
    private static Map<Integer, Integer> processInput() {
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 6/Input.txt"))) {
            String timeLine = input.nextLine();
            String distanceLine = input.nextLine();
            Arrays.stream(timeLine.split(":")[1].split("\\s+")).forEach(System.out::println);
            List<Integer> times = Arrays.stream(timeLine.split(":")[1].split("\\s+"))
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt).toList();
            List<Integer> distances = Arrays.stream(distanceLine.split(":")[1].split("\\s+"))
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .toList();
            return IntStream.range(0, times.size())
                    .boxed()
                    .collect(Collectors.toMap(i -> times.get(i), i -> distances.get(i)));
        } catch (FileNotFoundException e) {
            System.err.println("Input file could not be opened!");
        }
        return null;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> inputs = processInput();
        long result = 1;
        for (Map.Entry<Integer, Integer> input : inputs.entrySet()) {
            int time = input.getKey();
            int distanceToBeat = input.getValue();
            int startWin = -1, endWin = -1;
            for (int i = 0; i < time; ++i) {
                if ((time - i) * i > distanceToBeat) {
                    startWin = i;
                    break;
                }
            }
            for (int i = time - 1; i >= 0; --i) {
                if ((time - i) * i > distanceToBeat) {
                    endWin = i;
                    break;
                }
            }
            if (startWin != -1 && endWin != -1) {
                result *= (endWin - startWin) + 1;
            }
        }
        System.out.println("The multiplied result is: " + result);
    }
}
