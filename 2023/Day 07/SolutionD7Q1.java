import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SolutionD7Q1 {

    private enum Type {
        FiveOfAKind(1),
        FourOfAKind(2),
        FullHouse(3),
        ThreeOfAKind(4),
        TwoPair(5),
        OnePair(6),
        HighCard(7);

        private final Integer priority;

        private Type(Integer priority) {
            this.priority = priority;
        }
    }

    private enum Card {
        Ace('A', 1),
        King('K', 2),
        Queen('Q', 3),
        Joker('J', 4),
        Ten('T', 5),
        Nine('9', 6),
        Eight('8', 7),
        Seven('7', 8),
        Six('6', 9),
        Five('5', 10),
        Four('4', 11),
        Three('3', 12),
        Two('2', 13);

        private final Character value;
        private final Integer priority;

        private Card(Character value, Integer priority) {
            this.value = value;
            this.priority = priority;
        }

        public static Card getCardFromValue(Character value) {
            for (Card card : Card.values()) {
                if (card.value.equals(value)) {
                    return card;
                }
            }
            throw new IllegalArgumentException("No such card with value: " + value);
        }
    }

    private static Map<String, Integer> processInput() {
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 7/Input.txt"))) {
            Map<String, Integer> inputs = new HashMap<>();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                inputs.put(line.split("\\s+")[0], Integer.parseInt(line.split("\\s+")[1]));
            }
            return inputs;
        } catch (FileNotFoundException e) {
            System.err.println("Input file could not be opened!");
        }
        return null;
    }

    private static Map<Character, Long> getCharacterCounts(String hand) {
        return hand.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private static Map<String, Type> getSortedHandTypes(Map<String, Integer> inputs) {
        Map<String, Type> handTypes = new HashMap<>();
        inputs.entrySet().forEach(entry -> {
            Map<Character, Long> counts = getCharacterCounts(entry.getKey());
            if (counts.size() == 1) {
                handTypes.put(entry.getKey(), Type.FiveOfAKind);
            } else if (counts.size() == 5) {
                handTypes.put(entry.getKey(), Type.HighCard);
            } else if (counts.size() == 4) {
                handTypes.put(entry.getKey(), Type.OnePair);
            } else if (counts.size() == 2) {
                if (counts.values().contains(4L)) {
                    handTypes.put(entry.getKey(), Type.FourOfAKind);
                } else {
                    handTypes.put(entry.getKey(), Type.FullHouse);
                }
            } else {
                if (counts.values().contains(3L)) {
                    handTypes.put(entry.getKey(), Type.ThreeOfAKind);
                } else {
                    handTypes.put(entry.getKey(), Type.TwoPair);
                }
            }
        });
        return handTypes.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().priority))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private static List<String> sortSingleTypeEntriesByPriority(List<String> singleTypeHands) {
        return singleTypeHands.stream()
                .sorted(Comparator.comparing(str -> {
                    int length = Math.min(str.length(), Card.values().length);
                    int[] priorities = new int[length];
                    for (int i = 0; i < length; i++) {
                        priorities[i] = Card.getCardFromValue(str.charAt(i)).priority;
                    }
                    return Arrays.stream(priorities)
                            .mapToObj(priority -> String.format("%02d", priority))
                            .collect(Collectors.joining(","));
                }))
                .collect(Collectors.toList());
    }

    private static Map<String, Integer> getSecondSortedHandRanks(Map<String, Type> sortedHandTypes) {
        Map<Type, List<Map.Entry<String, Type>>> typeGroupedHands = sortedHandTypes.entrySet()
                .stream()
                .collect(Collectors.groupingBy(
                        Map.Entry<String, Type>::getValue,
                        LinkedHashMap::new,
                        Collectors.toList()));
        Map<String, Integer> secondSortedHandRanks = new LinkedHashMap<>();
        AtomicInteger count = new AtomicInteger(sortedHandTypes.size());
        typeGroupedHands.entrySet().forEach(entry -> {
            sortSingleTypeEntriesByPriority(entry.getValue()
                    .stream()
                    .map(e -> e.getKey())
                    .toList())
                    .forEach(str -> secondSortedHandRanks.put(str, count.getAndDecrement()));
        });
        return secondSortedHandRanks;
    }

    public static void main(String[] args) {
        Map<String, Integer> handValues = processInput();
        Map<String, Type> sortedHandTypes = getSortedHandTypes(handValues);
        Map<String, Integer> dualSortedHandRanks = getSecondSortedHandRanks(sortedHandTypes);
        AtomicLong sum = new AtomicLong(0);
        dualSortedHandRanks.forEach((k, v) -> {
            sum.getAndAdd(handValues.getOrDefault(k, 0) * v);
        });
        System.out.println("The sum is: " + sum);
    }
}
