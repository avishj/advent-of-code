import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SolutionD1Q2 {

    private static List<String> texts = Arrays.asList(TextDigit.values()).stream().map(TextDigit::getTextFromTextDigit)
            .toList();

    private enum Direction {
        LEFT,
        RIGHT
    }

    private enum TextDigit {
        Zero("zero", 0),
        One("one", 1),
        Two("two", 2),
        Three("three", 3),
        Four("four", 4),
        Five("five", 5),
        Six("six", 6),
        Seven("seven", 7),
        Eight("eight", 8),
        Nine("nine", 9);

        private final String text;
        private final int digit;

        private TextDigit(String text, int digit) {
            this.text = text;
            this.digit = digit;
        }

        public int getDigit() {
            return this.digit;
        }

        public static String getTextFromTextDigit(TextDigit textDigit) {
            return textDigit.text;
        }

        public static int getDigitFromText(String text) {
            return Arrays.stream(values())
                    .filter(textDigit -> textDigit.text.equalsIgnoreCase(text))
                    .findFirst()
                    .map(TextDigit::getDigit)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid text: " + text));
        }
    }

    public static void main(String[] args) {
        long sum = 0;
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 1/Input.txt"))) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                int leftDigit = scanForNumber(line, Direction.LEFT);
                int rightDigit = scanForNumber(line, Direction.RIGHT);
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
            String textFirstIndex = texts.stream()
                    .filter(line::contains)
                    .min(Comparator.comparing(line::indexOf))
                    .orElse(null);
            for (int i = 0; i < line.length(); ++i) {
                int value = getNumIfCharIsNum(line, i);
                if (value != -1
                        && (textFirstIndex == null || (textFirstIndex != null && line.indexOf(textFirstIndex) > i)))
                    return value;
            }
            if (textFirstIndex != null)
                return TextDigit.getDigitFromText(textFirstIndex);
        } else if (direction == Direction.RIGHT) {
            String textLastIndex = texts.stream()
                    .filter(line::contains)
                    .max(Comparator.comparing(line::lastIndexOf))
                    .orElse(null);
            for (int i = line.length() - 1; i >= 0; --i) {
                int value = getNumIfCharIsNum(line, i);
                if (value != -1
                        && (textLastIndex == null || (textLastIndex != null && line.lastIndexOf(textLastIndex) < i)))
                    return value;
            }
            if (textLastIndex != null)
                return TextDigit.getDigitFromText(textLastIndex);
        }
        return -1;
    }
}