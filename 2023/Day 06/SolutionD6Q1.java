import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolutionD6Q1 {
    private static Map<Integer, Integer> processInput() {
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 6/Input.txt"))) {
            String timeLine = input.nextLine();
            String distanceLine = input.nextLine();
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

    private static Optional<Integer> calculateRange(Integer key, Integer value, UnaryOperator<Integer> mapFunction) {
        return IntStream.range(0, key)
                .boxed()
                .map(mapFunction)
                .filter(i -> value < i * (key - i))
                .findFirst();
    }

    public static void main(String[] args) {
        Map<Integer, Integer> inputs = processInput();
        AtomicLong result = new AtomicLong(1);
        inputs.entrySet().forEach(entry -> {
            Optional<Integer> startWin = calculateRange(entry.getKey(), entry.getValue(),
                    UnaryOperator.identity());
            Optional<Integer> endWin = calculateRange(entry.getKey(), entry.getValue(),
                    i -> entry.getKey() - i + 0 - 1); // Reversing: http://stackoverflow.com/a/24011264/2711488
            result.updateAndGet(operand -> operand * ((endWin.orElse(0) - startWin.orElse(0)) + 1));
        });
        System.out.println("The multiplied result is: " + result.get());
    }
}
