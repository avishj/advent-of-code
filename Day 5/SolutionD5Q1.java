import java.io.File;
import java.io.FileNotFoundException;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SolutionD5Q1 {

    private static class MapLine {
        long destinationStart;
        long sourceStart;
        long count;

        public MapLine(long destinationStart, long sourceStart, long count) {
            this.destinationStart = destinationStart;
            this.sourceStart = sourceStart;
            this.count = count;
        }

        @Override
        public String toString() {
            return "MapLine [destinationStart=" + destinationStart + ", sourceStart=" + sourceStart + ", count=" + count
                    + "]";
        }
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 5/Input.txt"))) {
            List<Long> seeds;
            Map<String, List<MapLine>> maps = new LinkedHashMap<>();
            List<MapLine> currentMap = new ArrayList<>();
            List<MapLine> previousMap = new ArrayList<>();
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.contains("seeds:")) {
                    seeds = Arrays.stream(line.split(":")[1].trim().split("\\s+")).map(Long::parseLong).toList();
                } else if (line.contains("map:")) {
                    String mapName = line.split("map:")[0];
                    if (!currentMap.isEmpty()) {
                        maps.put(mapName, currentMap);
                        currentMap = new ArrayList<>();
                    }
                } else if (!line.isEmpty()) {
                    long[] numbers = Arrays.stream(line.split("\\s+"))
                            .mapToLong(Long::parseLong)
                            .toArray();
                    if (numbers.length != 3) {
                        throw new IllegalStateException("Got too many numbers to parse within map!");
                    }
                    currentMap.add(new MapLine(numbers[0], numbers[1], numbers[2]));
                }
            }
            List<String> reversedKeysOfMaps = maps.keySet().stream().collect(Collectors.toList());
            Collections.reverse(reversedKeysOfMaps);
            for (int i = 0; i < reversedKeysOfMaps.size() - 1; ++i) {
                currentMap = maps.get(reversedKeysOfMaps.get(i));
                previousMap = maps.get(reversedKeysOfMaps.get(i + 1));
                for (MapLine mapLine : currentMap) {
                    ValueRange currentMapDestinationRange = ValueRange.of(mapLine.destinationStart,
                            mapLine.destinationStart + mapLine.count - 1);
                    ValueRange currentMapSourceRange = ValueRange.of(mapLine.destinationStart,
                            mapLine.destinationStart + mapLine.count - 1);

                }
            }
            System.out.println(maps);
        } catch (FileNotFoundException e) {
            System.err.println("Input file could not be opened!");
        }
    }
}