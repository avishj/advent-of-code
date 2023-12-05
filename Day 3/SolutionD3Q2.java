import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolutionD3Q2 {

    private static Pattern NUMBER_PATTERN = Pattern.compile("\\b\\d+\\b");
    private static Pattern SYMBOL_PATTERN = Pattern.compile("[^0-9.]");

    private static class Number {
        int xStart;
        int xEnd;
        int y;
        int value;

        public Number(int xStart, int xEnd, int y, int value) {
            this.xStart = xStart;
            this.xEnd = xEnd;
            this.y = y;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Number [xStart=" + xStart + ", xEnd=" + xEnd + ", y=" + y + ", value=" + value + "]";
        }
    }

    private static class Symbol {
        int x;
        int y;
        String value;

        public Symbol(int x, int y, String value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Symbol [x=" + x + ", y=" + y + ", value=" + value + "]";
        }
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 3/Input.txt"))) {
            long sum = 0;
            int lineCount = 0;
            List<Number> numbers = new ArrayList<>();
            List<Symbol> symbols = new ArrayList<>();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                Matcher matcher = NUMBER_PATTERN.matcher(line);
                while (matcher.find()) {
                    numbers.add(new Number(matcher.start(), matcher.end() - 1, lineCount,
                            Integer.parseInt(matcher.group())));
                }
                matcher = SYMBOL_PATTERN.matcher(line);
                while (matcher.find()) {
                    symbols.add(new Symbol(matcher.start(), lineCount, matcher.group()));
                }
                ++lineCount;
            }
            for (Symbol symbol : symbols) {
                if (!symbol.value.equals("*")) {
                    continue;
                }
                long topNumberCount = numbers.stream()
                        .filter(number -> (number.y == symbol.y - 1) && (number.xEnd == symbol.x - 1
                                || number.xEnd == symbol.x || number.xStart == symbol.x - 1 || number.xStart == symbol.x
                                || number.xStart == symbol.x + 1))
                        .count();
                long leftNumberCount = numbers.stream()
                        .filter(number -> (number.y == symbol.y) && (number.xEnd == symbol.x - 1)).count();
                long rightNumberCount = numbers.stream()
                        .filter(number -> (number.y == symbol.y) && (number.xStart == symbol.x + 1)).count();
                long bottomNumberCount = numbers.stream()
                        .filter(number -> (number.y == symbol.y + 1) && (number.xEnd == symbol.x - 1
                                || number.xEnd == symbol.x || number.xStart == symbol.x - 1 || number.xStart == symbol.x
                                || number.xStart == symbol.x + 1))
                        .count();
                List<Number> tempo = numbers.stream()
                        .filter(number -> (number.y == symbol.y + 1) && (number.xEnd == symbol.x - 1
                                || number.xEnd == symbol.x || number.xStart == symbol.x - 1 || number.xStart == symbol.x
                                || number.xStart == symbol.x + 1))
                        .toList();
                tempo.stream().forEach(System.out::println);
                if (topNumberCount + leftNumberCount + rightNumberCount + bottomNumberCount == 2) {
                    List<Number> validNums = numbers.stream().filter(
                            number -> ((number.y == symbol.y - 1) && (number.xEnd == symbol.x - 1
                                    || number.xEnd == symbol.x || number.xStart == symbol.x - 1
                                    || number.xStart == symbol.x
                                    || number.xStart == symbol.x + 1))
                                    || ((number.y == symbol.y) && (number.xEnd == symbol.x
                                            - 1))
                                    || ((number.y == symbol.y) && (number.xStart == symbol.x
                                            + 1))
                                    || ((number.y == symbol.y + 1) && (number.xEnd == symbol.x - 1
                                            || number.xEnd == symbol.x || number.xStart == symbol.x - 1
                                            || number.xStart == symbol.x
                                            || number.xStart == symbol.x + 1)))
                            .toList();
                    sum += validNums.stream().filter(v -> v.value != 0).mapToInt(number -> number.value).reduce(1,
                            (a, b) -> a * b);
                }
            }
            System.out.println("Total Part Number Sum: " + sum);
        } catch (FileNotFoundException e) {
            System.err.println("Input file could not be opened!");
        }
    }
}
