package org.opencypher.gremlin.examples;

import static org.opencypher.gremlin.Util.getFile;

import java.util.List;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.Tokens;
import org.apache.tinkerpop.gremlin.driver.message.RequestMessage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This test shows how to use the official Gremlin-Java driver
 * to send Cypher queries to a Cypher-enabled Gremlin Server.
 */
public class GremlinDriverPluginTest {
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

        String cypher = "CREATE (n:L {prop: 1}) RETURN n";

        RequestMessage requestMessage = RequestMessage.build(Tokens.OPS_EVAL)
                .processor("cypher")
                .add(Tokens.ARGS_GREMLIN, cypher)
                .create();

        List<Result> results = gremlinClient.submitAsync(requestMessage).get().all().get();

        logger.info("Result: {}", results);
    }
}
