package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseFromFile {
    public static Map<String, String> byBufferedReader(String filePath) {
        HashMap<String, String> map = new HashMap<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                String[] keyValuePair = line.split(":", 2);
                if (keyValuePair.length > 1) {
                    String key = keyValuePair[0];
                    String value = keyValuePair[1];

                    map.put(key, value);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}