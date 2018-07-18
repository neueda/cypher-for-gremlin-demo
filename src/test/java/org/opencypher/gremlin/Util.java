package org.opencypher.gremlin;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class Util {
    public static String getFile(String resourceName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(resourceName);
        return url.getFile();
    }

    public static String format(List<Map<String, Object>> results) {
        StringBuilder builder = new StringBuilder("\n\nResults:\n");
        results.forEach(r -> builder.append(r.toString()).append("\n"));
        return builder.append("\n").toString();
    }
}
