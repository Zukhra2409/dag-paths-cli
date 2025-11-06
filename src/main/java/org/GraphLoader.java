package org;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class GraphLoader {
    public static class Edge { public int u, v; public Integer w; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class G {
        public boolean directed = true;
        public int n;
        public List<Edge> edges;
        public Integer source;
    }

    public static G load(String path) throws Exception {
        ObjectMapper om = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (in == null) in = GraphLoader.class.getClassLoader().getResourceAsStream(path);
        if (in == null && !path.startsWith("/")) in = GraphLoader.class.getResourceAsStream("/" + path);
        if (in != null) return om.readValue(in, G.class);
        Path p = Path.of(path);
        if (Files.exists(p)) return om.readValue(Files.readAllBytes(p), G.class);
        throw new FileNotFoundException("Not found: " + path + " (classpath и файл). WD=" + System.getProperty("user.dir"));
    }
}