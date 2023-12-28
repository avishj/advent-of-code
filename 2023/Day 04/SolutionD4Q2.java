import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class SolutionD4Q2 {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 4/Input.txt"))) {
            long sum = 0;
            Map<Integer, Integer> futureCards = new HashMap<>();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                Integer gameId = Integer.parseInt(line.split(":")[0].split("\\s+")[1]);
                Integer currentGameFutureCount = futureCards.getOrDefault(gameId, 0);
                Set<Integer> winningNumbers = Arrays.stream(line.split(":")[1].split("\\|")[0].trim().split("\\s+"))
                        .filter(v -> !v.equals("")).map(Integer::parseInt).collect(Collectors.toSet());
                Set<Integer> ourNumbers = Arrays.stream(line.split(":")[1].split("\\|")[1].trim().split("\\s+"))
                        .filter(v -> !v.equals("")).map(Integer::parseInt).collect(Collectors.toSet());
                winningNumbers.retainAll(ourNumbers);
                for (int i = 0; i < 1 + currentGameFutureCount; ++i) {
                    for (int j = gameId + 1; j <= gameId + winningNumbers.size(); ++j) {
                        futureCards.put(j, futureCards.getOrDefault(j, 0) + 1);
                    }
                }
                sum += 1 + currentGameFutureCount;
            }
            System.out.println("Total Scratchcards: " + sum);
        } catch (FileNotFoundException e) {
            System.err.println("Input file could not be opened!");
        }
    }
}
