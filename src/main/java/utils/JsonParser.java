package utils;

import java.util.HashMap;
import java.util.Map;

public class JsonParser {
    public static Map<String, String> parse(String json) {

        Map<String, String> map = new HashMap<>();

        // remove braces and quotes
        json = json.trim().replaceAll("[{}\"]", "");

        String[] entries = json.split(",");

        for (String entry : entries) {
            String[] pair = entry.split(":", 2);
            if (pair.length == 2) {
                map.put(pair[0].trim(), pair[1].trim());
            }
        }
        return map;
    }
}
