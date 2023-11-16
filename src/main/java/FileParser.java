import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class FileParser {
    public void parsePhoneNumbers() {
        File file = new File("src/main/resources/task1.txt");
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

    public void createJsonFile() {
        File fileFrom = new File("src/main/resources/task2.txt");
        checkIfFileAvailable(fileFrom);
        List<User> userArrays = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileFrom))) {
            String line = reader.readLine();

            int i = 0;
            while (line != null) {
                if (i == 0) {
                    i++;
                    line = reader.readLine();
                    continue;
                }
                if (line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }

                String userName = line.split("\\s+")[0];
                int userAge = Integer.parseInt(line.split("\\s+")[1]);
                userArrays.add(new User(userName, userAge));

                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(userArrays);

        File fileTo = new File("src/main/resources/user.json");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileTo))) {
            writer.write(jsonString);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void countWordsFrequency() {
        File file = new File("src/main/resources/task3.txt");
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
        fileParser.parsePhoneNumbers();
        System.out.println();
        System.out.println("--- Task #2 ---");
        fileParser.createJsonFile();
        System.out.println("Create new data in json file");
        System.out.println();
        System.out.println("--- Task #3 ---");
        fileParser.countWordsFrequency();
    }
}

class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
