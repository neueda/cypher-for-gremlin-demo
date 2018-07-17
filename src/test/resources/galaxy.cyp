// Clear database
MATCH (n)
DETACH DELETE n;

// Create sample graph
CREATE (ss1:StarSystem {name: 'Solar System'})
CREATE (ss2:StarSystem {name: 'Alpha Centauri'})
CREATE (ss3:StarSystem {name: 'Sirius'})
CREATE (ss1)-[:DISTANCE_TO {ly: 8.59}]->(ss3)
CREATE (ss2)-[:DISTANCE_TO {ly: 4.38}]->(ss1)
CREATE (ss2)-[:DISTANCE_TO {ly: 9.54}]->(ss3)
CREATE (ss1s1:Star {name: 'Sun', type: 'G'})-[:MEMBER_OF]->(ss1)
CREATE (ss2s1:Star {name: 'Alpha Centauri A', type: 'G'})-[:MEMBER_OF]->(ss2)
CREATE (ss2s2:Star {name: 'Alpha Centauri B', type: 'K'})-[:MEMBER_OF]->(ss2)
CREATE (ss2s3:Star {name: 'Proxima Centauri', type: 'M'})-[:MEMBER_OF]->(ss2)
CREATE (ss3s1:Star {name: 'Sirius A', type: 'A'})-[:MEMBER_OF]->(ss3)
CREATE (ss3s2:Star {name: 'Sirius B', type: 'D'})-[:MEMBER_OF]->(ss3)
CREATE (ss1s1p1:Planet {name: 'Mercury'})-[:ORBITS {au: 0.4}]->(ss1s1)
CREATE (ss1s1p2:Planet {name: 'Venus'})-[:ORBITS {au: 0.7}]->(ss1s1)
CREATE (ss1s1p3:Planet {name: 'Earth'})-[:ORBITS {au: 1.0}]->(ss1s1)
CREATE (ss1s1p4:Planet {name: 'Mars'})-[:ORBITS {au: 1.5}]->(ss1s1)
CREATE (ss1s1p5:Planet {name: 'Ceres'})-[:ORBITS {au: 2.77}]->(ss1s1)
CREATE (ss1s1p6:Planet {name: 'Jupiter'})-[:ORBITS {au: 5.2}]->(ss1s1)
CREATE (ss1s1p7:Planet {name: 'Saturn'})-[:ORBITS {au: 9.5}]->(ss1s1)
CREATE (ss1s1p8:Planet {name: 'Uranus'})-[:ORBITS {au: 19.2}]->(ss1s1)
CREATE (ss1s1p9:Planet {name: 'Neptune'})-[:ORBITS {au: 30.1}]->(ss1s1)
CREATE (ss2s3p1:Planet {name: 'Proxima Centauri b'})-[:ORBITS {au: 0.05}]->(ss2s3);