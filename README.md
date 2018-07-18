# Cypher for Gremlin Demo

Demo of [Cypher for Gremlin](https://github.com/opencypher/cypher-for-gremlin) clients and API.

## Usage

1. Set Gremlin credentials in [remote.yaml](src/test/resources/remote.yaml)
    - Note that [Gremlin Server Cypher Plugin](https://github.com/opencypher/cypher-for-gremlin/tree/master/tinkerpop/cypher-gremlin-server-plugin) is required *only* for [GremlinServerClientPluginTest](src/test/java/org/opencypher/gremlin/examples/GremlinServerClientPluginTest.java)
    - Note that [Gremlin Cypher Extensions](https://github.com/opencypher/cypher-for-gremlin/tree/master/tinkerpop/cypher-gremlin-extensions) are required *only* for [GremlinServerClientExtensionsTest](src/test/java/org/opencypher/gremlin/examples/GremlinServerClientExtensionsTest.java) (and should be added to Gremlin Server classpath)
2. Change the `FLAVOR` constant if your target is something other than the TinkerPop Gremlin Server (e.g. Amazon Neptune or Azure Cosmos DB)
3. Run tests, start with:
    - [QueriesTest](src/test/java/org/opencypher/gremlin/demo/QueriesTest.java)
    - [GremlinServerClientTest](src/test/java/org/opencypher/gremlin/examples/GremlinServerClientTest.java)
    - [Neo4jDriverTest](src/test/java/org/opencypher/gremlin/examples/Neo4jDriverTest.java)
4. Translated queries and results will show up in logs
5. For documentation refer to the [Cypher for Gremlin](https://github.com/opencypher/cypher-for-gremlin) README
