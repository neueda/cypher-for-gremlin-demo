package org.opencypher.gremlin.examples;

import static org.opencypher.gremlin.Util.getFile;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.junit.Test;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.opencypher.gremlin.neo4j.driver.Config;
import org.opencypher.gremlin.neo4j.driver.GremlinDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Neo4jDriverTest {
    private static final Logger logger = LoggerFactory.getLogger(Neo4jDriverTest.class);

    @Test
    public void neo4jDriver() throws Exception {
        String gremlinConfig = getFile("remote.yaml");
        Cluster cluster = Cluster.open(gremlinConfig);

        Config config = Config.build()
                .withTranslation()
                .toConfig();
        Driver driver = GremlinDatabase.driver(cluster, config);

        String cypher = "MATCH (n) RETURN count(n) as count";
        try (Session session = driver.session()) {
            StatementResult result = session.run(cypher);
            int count = result.single().get("count").asInt();

            logger.info("Count: {}", count);
        }
    }
}
