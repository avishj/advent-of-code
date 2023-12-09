import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
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
        AtomicLong result = new AtomicLong(1);
        inputs.entrySet().forEach(entry -> {
            Optional<Integer> startWin = IntStream.range(0, entry.getKey())
                    .boxed()
                    .filter(i -> entry.getValue() < i * (entry.getKey() - i))
                    .findFirst();
            Optional<Integer> endWin = IntStream.range(0, entry.getKey())
                    .boxed()
                    .map(i -> entry.getKey() - i + 0 - 1) // Reversing: http://stackoverflow.com/a/24011264/2711488
                    .filter(i -> entry.getValue() < i * (entry.getKey() - i))
                    .findFirst();
            if (startWin.isPresent() && endWin.isPresent()) {
                result.updateAndGet(operand -> operand * ((endWin.get() - startWin.get()) + 1));
            }
        });
        System.out.println("The multiplied result is: " + result.get());
    }
}
