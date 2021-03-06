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

/**
 * This test shows how to configure a Cypher Gremlin Server client
 * that sends Cypher queries directly to a Cypher-enabled Gremlin Server.
 */
public class GremlinServerClientPluginTest {
    private static final Logger logger = LoggerFactory.getLogger(GremlinServerClientPluginTest.class);

    /**
     * In this test Cypher to Gremlin translation happens on the target Gremlin Server.
     * <p>
     * Note that <a href="https://github.com/opencypher/cypher-for-gremlin/tree/master/tinkerpop/cypher-gremlin-server-plugin">Gremlin Server Cypher Plugin</a>
     * should be installed on Gremlin remote in order for this to work.
     */
    @Test
    public void gremlinServerClient() throws Exception {
        String config = getFile("remote.yaml");

        Cluster cluster = Cluster.open(config);
        Client gremlinClient = cluster.connect();
        CypherGremlinClient cypherGremlinClient =
            CypherGremlinClient.plugin(gremlinClient);

        String cypher = "RETURN 'test' + toString(1) as result";
        CypherResultSet resultSet = cypherGremlinClient.submit(cypher);
        List<Map<String, Object>> results = resultSet.all();

        logger.info("Result: {}", results);
    }
}
