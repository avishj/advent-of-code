import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SolutionD5Q1 {

    private static enum MapType {
        SEED_TO_SOIL,
        SOIL_TO_FERTILIZER,
        FERTILIZER_TO_WATER,
        WATER_TO_LIGHT,
        LIGHT_TO_TEMPERATURE,
        TEMPERATURE_TO_HUMIDITY,
        HUMIDITY_TO_LOCATION
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(new File("/Users/aj/Desktop/Projects/advent-of-code-2023/Day 4/Input.txt"))) {
            List<Integer> seeds = new ArrayList<>();
            Map<String, Map<Integer, Integer>> maps = new LinkedHashMap<>();
            Map<Integer, Integer> seedToSoil = new HashMap<>();
            Map<Integer, Integer> soilToFertilizer = new HashMap<>();
            Map<Integer, Integer> fertilizerToWater = new HashMap<>();
            Map<Integer, Integer> waterToLight = new HashMap<>();
            Map<Integer, Integer> lightToTemperature = new HashMap<>();
            Map<Integer, Integer> temperatureToHumidity = new HashMap<>();
            Map<Integer, Integer> humidityToLocation = new HashMap<>();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (line.contains("seeds:")) {
                    seeds = Arrays.stream(line.split(":")[1].trim().split("\\s+")).map(Integer::parseInt).toList();
                } else if (line.contains("seed-to-soil map:"))
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file could not be opened!");
        }
    }
}