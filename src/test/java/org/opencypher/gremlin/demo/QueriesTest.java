package org.opencypher.gremlin.demo;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.junit.Before;
import org.junit.Test;
import org.opencypher.gremlin.Util;
import org.opencypher.gremlin.client.CypherGremlinClient;
import org.opencypher.gremlin.translation.translator.TranslatorFlavor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueriesTest {
    private static final Logger logger = LoggerFactory.getLogger(QueriesTest.class);
    private CypherGremlinClient cypherGremlinClient;

    @Before
    public void setUp() throws Exception {
        String config = Util.getFile("remote.yaml");

        Cluster cluster = Cluster.open(config);
        Client gremlinClient = cluster.connect();
        cypherGremlinClient = CypherGremlinClient.translating(
                gremlinClient,
                TranslatorFlavor.neptune());

        String createSnMini = new String(readAllBytes(get(Util.getFile("galaxy.cyp"))));

        for (String statement : createSnMini.split(";")) {
            cypherGremlinClient.submit(statement).all();
        }
    }

    /**
     * Count all nodes
     */
    @Test
    public void countAllNodes() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (n)" +
                        "RETURN count(n) as count").all();

        logger.info("Results: {}", results);

        assertEquals(19L, results.get(0).get("count"));
    }

    /**
     * Count all relationships
     */
    @Test
    public void countAllRelationships() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH ()-[r]->()" +
                        "RETURN count(r) as count").all();

        logger.info("Results: {}", results);

        assertEquals(19L, results.get(0).get("count"));
    }

    /**
     * Show relationship types
     */
    @Test
    public void showRelationshipTypes() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH ()-[r]-() " +
                        "RETURN DISTINCT type(r)").all();

        logger.info("Results: {}", results);

        assertEquals(3, results.size());
    }

    /**
     * Show node labels and property names
     */
    @Test
    public void showNodeLabelsAndPropertyNames() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (n) " +
                        "RETURN DISTINCT labels(n) AS label, keys(n) AS properties").all();

        logger.info("Results: {}", results);

        assertEquals(3, results.size());
    }

    /**
     * Find names of stars
     */
    @Test
    public void findNamesOfStars() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (n:Star) " +
                        "RETURN n.name").all();

        logger.info("Results: {}", results);

        assertEquals(6, results.size());
    }

    /**
     * Return all nodes with relationships
     */
    @Test
    public void returnAllNodesWithRelationships() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (n)-[r]-() " +
                        "RETURN *").all();

        logger.info("Results: {}", results);

        assertEquals(38, results.size());
    }

    /**
     * Return Alpha Centauri with all its stars
     */
    @Test
    public void returnAlphaCentauriWithAllItsStars() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (n:StarSystem {name: 'Alpha Centauri'})<-[r]-(m) " +
                        "RETURN n, r, m").all();

        logger.info("Results: {}", results);

        assertEquals(3, results.size());
    }

    /**
     * Return paths of star relationships
     */
    @Test
    public void returnPathsOfStarRelationships() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH p = (:Star)-->() " +
                        "RETURN p").all();

        logger.info("Results: {}", results);

        assertEquals(6, results.size());
    }

    /**
     * List all relationship types in the graph
     */
    @Test
    public void listAllRelationshipTypesInTheGraph() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH ()-[r]-() " +
                        "RETURN DISTINCT type(r)").all();

        logger.info("Results: {}", results);

        assertEquals(3, results.size());
    }

    /**
     * Find star systems within 5 light-years from ours
     */
    @Test
    public void findStarSystemsWithin5LightYearsFromOurs() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (ss1:StarSystem {name: 'Solar System'}) " +
                        "MATCH (ss1)-[r:DISTANCE_TO]-(ss2:StarSystem) " +
                        "WHERE r.ly <= 5 " +
                        "RETURN ss2.name as name").all();

        assertEquals("Alpha Centauri", results.get(0).get("name"));
    }

    /**
     * Find all planets in the asteroid belt
     */
    @Test
    public void findAllPlanetsInTheAsteroidBelt() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (p:Planet)-[orbit:ORBITS]->(:Star {name: 'Sun'}) " +
                        "  WHERE 2.3 < orbit.au < 3.3 " +
                        "RETURN p.name AS planet").all();

        logger.info("Results: {}", results);

        assertEquals("Ceres", results.get(0).get("planet"));
    }

    /**
     * Count stars by their spectral type
     */
    @Test
    public void countStarsByTheirSpectralType() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (s:Star) " +
                        "RETURN s.type, count(s)").all();

        logger.info("Results: {}", results);

        assertEquals(5, results.size());
    }

    /**
     * Group stars by star system
     */
    @Test
    public void groupStarsByStarSystem() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (s)-[:MEMBER_OF]->(ss) " +
                        "RETURN ss.name AS system, collect(s.name) AS stars").all();

        logger.info("Results: {}", results);

        assertEquals(3, results.size());
    }

    /**
     * Count planets by star system
     */
    @Test
    public void countPlanetsByStarSystem() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (p:Planet)-[*2]->(ss:StarSystem) " +
                        "RETURN ss.name AS system, count(p) AS planets").all();

        logger.info("Results: {}", results);

        assertEquals(2, results.size());
    }

    /**
     * Find five farthest planets in the Solar System
     * Note: omitting centaurs and the Trans-Neptunian region :-)
     */
    @Test
    public void findFiveFarthestPlanetsInTheSolarSystem() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (p:Planet)-[r:ORBITS]->(s:Star {name: 'Sun'}) " +
                        "RETURN p.name " +
                        "ORDER BY r.au DESC " +
                        "LIMIT 5").all();

        logger.info("Results: {}", results);

        assertEquals(5, results.size());
    }

    /**
     * Find all planets within 2 AU of Earth's star
     */
    @Test
    public void findAllPlanetsWithin2AUOfEarthsStar() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (earth:Planet {name: 'Earth'}) " +
                        "MATCH (earth)-->(:Star)<-[orbit:ORBITS]-(neighbour:Planet) " +
                        "WHERE orbit.au < 2 AND neighbour <> earth " +
                        "RETURN neighbour.name").all();

        logger.info("Results: {}", results);

        assertEquals(3, results.size());
    }

    /**
     * Create the two moons of Mars
     */
    @Test
    public void createTheTwoMoonsOfMars() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "UNWIND ['Phobos', 'Deimos'] AS moon " +
                        "MATCH (p:Planet {name: 'Mars'}) " +
                        "CREATE (m:Moon {name: moon})-[o:ORBITS]->(p) " +
                        "RETURN m, o, p").all();

        logger.info("Results: {}", results);

        assertEquals(2, results.size());
    }

    /**
     * Find all stars without any planets orbiting them
     */
    @Test
    public void findAllStarsWithoutAnyPlanetsOrbitingThem() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (s:Star) " +
                        "OPTIONAL MATCH (s)<-[:ORBITS]-(p:Planet) " +
                        "WITH s, p " +
                        "WHERE p IS NULL " +
                        "RETURN s.name " +
                        "ORDER BY s.name").all();

        logger.info("Results: {}", results);

        assertEquals(4, results.size());
    }

    /**
     * List names of planets that orbit M-type stars
     */
    @Test
    public void listNamesOfPlanetsThatOrbitMTypeStars() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (s:Star {type: 'M'}) " +
                        "RETURN [(s)<-[:ORBITS]-(p) WHERE p:Planet | p.name] AS planets").all();

        logger.info("Results: {}", results);

        assertEquals(asList("Proxima Centauri b"), results.get(0).get("planets"));
    }

    /**
     * Creating a black hole at given coordinates
     */
    @Test
    public void creatingABlackHoleAtGivenCoordinates() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MERGE (cygnusX1:BlackHole {name: 'Cygnus X-1', ra: '19h 58m', dec: '+35°'}) " +
                        "ON CREATE SET cygnusX1.state = 'forming' " +
                        "ON MATCH SET cygnusX1.state = 'stable' " +
                        "RETURN cygnusX1; ").all();

        logger.info("Results: {}", results);

        assertEquals(1, results.size());
    }

    /**
     * Advanced query example
     */
    @Test
    public void advancedQueryExample() {
        List<Map<String, Object>> results = cypherGremlinClient.submit(
                "MATCH (sun:Star {name: 'Sirius A'})-[*1..2]-(sys:StarSystem) " +
                        "OPTIONAL MATCH (p:Planet)-[:ORBITS]->(s:Star)-[:MEMBER_OF]->(sys) " +
                        "WHERE s.type IN ['K', 'G', 'B', 'F', 'O', 'A', 'M'] " +
                        "WITH sys.name AS system, p.name AS planet " +
                        "ORDER BY planet " +
                        "RETURN system, collect(planet) AS planets").all();

        logger.info("Results: {}", results);

        assertEquals(3, results.size());
    }
}
