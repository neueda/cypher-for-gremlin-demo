package org.opencypher.gremlin;

import java.net.URL;

public class Util {
    public static String getFile(String resourceName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(resourceName);
        return url.getFile();
    }
}
