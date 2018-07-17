package org.opencypher.gremlin.examples;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Test;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.opencypher.gremlin.client.CypherGremlinClient;
import org.opencypher.gremlin.neo4j.driver.GremlinDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryTest {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryTest.class);

    @Test
    public void inMemory() {
        TinkerGraph graph = TinkerFactory.createModern();
        GraphTraversalSource traversal = graph.traversal();

        // Gremlin Server client
        CypherGremlinClient cypherGremlinClient =
                CypherGremlinClient.inMemory(traversal);

        cypherGremlinClient.submit("CREATE (n:Test {name: 'demo'})").all();

        // Neo4j driver
        Driver driver = GremlinDatabase.driver(traversal);

        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH (n:Test) RETURN n.name as name");
            String name = result.single().get("name").asString();

            logger.info("Results {}", name);
        }
    }
}
