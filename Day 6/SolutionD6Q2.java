import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class SolutionD6Q2 {
    private static Map<Long, Long> processInput() {
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 6/Input.txt"))) {
            String timeLine = input.nextLine();
            String distanceLine = input.nextLine();
            Arrays.stream(timeLine.split(":")[1].split("\\s+")).forEach(System.out::println);
            Long time = Long.parseLong(Arrays
                    .stream(timeLine.split(":")[1].split("\\s+"))
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.joining()));

            Long distance = Long.parseLong(Arrays
                    .stream(distanceLine.split(":")[1].split("\\s+"))
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.joining()));
            return Map.of(time, distance);
        } catch (FileNotFoundException e) {
            System.err.println("Input file could not be opened!");
        }
        return null;
    }

    private static Optional<Long> calculateRange(Long key, Long value, UnaryOperator<Long> mapFunction) {
        return LongStream.range(0, key)
                .boxed()
                .map(mapFunction)
                .filter(i -> value < i * (key - i))
                .findFirst();
    }

    public static void main(String[] args) {
        Map<Long, Long> inputs = processInput();
        AtomicLong result = new AtomicLong(1);
        inputs.entrySet().forEach(entry -> {
            Optional<Long> startWin = calculateRange(entry.getKey(), entry.getValue(),
                    UnaryOperator.identity());
            Optional<Long> endWin = calculateRange(entry.getKey(), entry.getValue(),
                    i -> entry.getKey() - i + 0 - 1); // Reversing: http://stackoverflow.com/a/24011264/2711488
            result.updateAndGet(operand -> operand * ((endWin.orElse(0L) - startWin.orElse(0L)) + 1));
        });
        System.out.println("The result is: " + result.get());
    }
}
