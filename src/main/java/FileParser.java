import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileParser {
    private static final String ABSOLUTE_PATH = "src/main/resources/task1.txt";
    private static final String ABSOLUTE_PATH2 = "src/main/resources/task3.txt";
    public void parserOfPhoneNumbers() {
        File file = new File(ABSOLUTE_PATH);
        checkIfFileAvailable(file);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                if (line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }
                if (line.matches("\\d{3}-\\d{3}-\\d{4}|\\(\\d{3}\\)\\s\\d{3}-\\d{4}")) {
                    System.out.println(line);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void counterOfWordsFrequency() {
        File file = new File(ABSOLUTE_PATH2);
        checkIfFileAvailable(file);

        HashMap<String, Integer> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                if (line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }
                String[] arrays = line.trim().split("\\s+");
                for (String array : arrays) {
                    if (!map.containsKey(array)) {
                        map.put(array, 1);
                    } else {
                        map.put(array, map.get(array) + 1);
                    }
                }
                line = reader.readLine();
            }

            List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
            list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            for (Map.Entry<String, Integer> entry : list) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void checkIfFileAvailable(File file) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();

            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        FileParser fileParser = new FileParser();
        System.out.println("--- Task #1 ---");
        fileParser.parserOfPhoneNumbers();
        System.out.println();
        System.out.println("--- Task #3 ---");
        fileParser.counterOfWordsFrequency();
    }


}
