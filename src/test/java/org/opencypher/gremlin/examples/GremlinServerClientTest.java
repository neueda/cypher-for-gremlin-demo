package org.opencypher.gremlin.examples;

import static org.opencypher.gremlin.Util.getFile;

import java.util.List;
import java.util.Map;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.junit.Test;
import org.opencypher.gremlin.client.CypherGremlinClient;
import org.opencypher.gremlin.client.CypherResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GremlinServerClientTest {
    private static final Logger logger = LoggerFactory.getLogger(GremlinServerClientTest.class);

    @Test
    public void gremlinServerClient() throws Exception {
        String config = getFile("remote.yaml");

        Cluster cluster = Cluster.open(config);
        Client gremlinClient = cluster.connect();
        CypherGremlinClient cypherGremlinClient =
                CypherGremlinClient.translating(gremlinClient);

        String cypher = "MATCH (n) RETURN count(n) as count";
        CypherResultSet resultSet = cypherGremlinClient.submit(cypher);
        List<Map<String, Object>> results = resultSet.all();

        logger.info("Count: {}", results);
    }
}
