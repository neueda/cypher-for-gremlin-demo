# cypher-for-gremlin-demo

Demo of [Cypher for Gremlin](https://github.com/opencypher/cypher-for-gremlin) clients

## Quick start

1. Set Gremlin credentials in [remote.yaml](src/test/resources/remote.yaml)
    - Note that *only* for [GremlinServerClientPluginTest](src/test/java/org/opencypher/gremlin/examples/GremlinServerClientPluginTest.java), [Gremlin Server Cypher Plugin](https://github.com/opencypher/cypher-for-gremlin/tree/master/tinkerpop/cypher-gremlin-server-plugin) should be installed 
    - Note that *only* for [GremlinServerClientExtensionsTest](src/test/java/org/opencypher/gremlin/examples/GremlinServerClientExtensionsTest.java), [Gremlin Cypher Extensions](https://github.com/opencypher/cypher-for-gremlin/tree/master/tinkerpop/cypher-gremlin-extensions) should be added to Gremlin Server classpath 
2. Set `FLAVOR` constant depending on remote type, where applicable
3. Run tests, start with:
    - [QueriesTest](src/test/java/org/opencypher/gremlin/demo/QueriesTest.java)
    - [GremlinServerClientTest](src/test/java/org/opencypher/gremlin/examples/GremlinServerClientTest.java)
    - [Neo4jDriverTest](src/test/java/org/opencypher/gremlin/examples/Neo4jDriverTest.java)
4. Translated queries and results will show up in logs
5. For documentation refer to [Cypher for Gremlin](https://github.com/opencypher/cypher-for-gremlin)
