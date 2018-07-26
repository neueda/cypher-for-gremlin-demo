# Cypher for Gremlin Demo

Demo of [Cypher for Gremlin](https://github.com/opencypher/cypher-for-gremlin) clients and API.

## Usage

1. Build the project:
   ```
   ./gradlew build -x test
   ```
1. Set Gremlin credentials in [remote.yaml](src/test/resources/remote.yaml)
    - Note that [Gremlin Server Cypher Plugin](https://github.com/opencypher/cypher-for-gremlin/tree/master/tinkerpop/cypher-gremlin-server-plugin) is required *only* for [GremlinServerClientPluginTest](src/test/java/org/opencypher/gremlin/examples/GremlinServerClientPluginTest.java)
    - Note that [Gremlin Cypher Extensions](https://github.com/opencypher/cypher-for-gremlin/tree/master/tinkerpop/cypher-gremlin-extensions) are required *only* for [GremlinServerClientExtensionsTest](src/test/java/org/opencypher/gremlin/examples/GremlinServerClientExtensionsTest.java) (and should be added to Gremlin Server classpath)
1. Change the `FLAVOR` constant if your target is something other than the TinkerPop Gremlin Server (e.g. Amazon Neptune or Azure Cosmos DB)
1. Run any of the tests that interest you.
1. Translated queries and results will show up in logs

## More

For documentation refer to the [Cypher for Gremlin](https://github.com/opencypher/cypher-for-gremlin) README. You can also start experimenting with Cypher for Gremlin without working with the API directly, by installing the [Gremlin Console Cypher plugin](tinkerpop/cypher-gremlin-console-plugin).
