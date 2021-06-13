/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
	
	/* create some vertices */
	public String jack = "jack";
	public String rose = "rose";
	public String tom = "tom";
	public String klay = "klay";
	public String booker = "booker";
	public String allen = "allen";
    
    // Testing strategy
    //   TODO
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    /**
     * Test add
     * add different data to the graph
     * 1. a new vertex which isn't in the vertices
     * 2. a vertex which is already in the vertices
     * final: check the vertices
     * 
     * */
    @Test
    public void add() {
    	Graph<String> graph = emptyInstance();
    	assertTrue(graph.add(jack));
    	assertTrue(graph.add(rose));
    	assertFalse(graph.add(jack));;
    	Set<String> vertices = graph.vertices();
    	assertTrue(vertices.size() == 2);
    	assertTrue(vertices.contains(jack));
    	assertTrue(vertices.contains(rose));
    }
    
    /**
     * Test set
     * send different data to set()
     * if an edge is new and not in the edges, we use add
     * if an edge is already in the edges, we use update
     * if the @param3 weight is 0, we use delete
     * we won't test a negative weight which has already been tested in the class Edge
     * 1. add edge1, whose source and target are both in the vertices
     * 2. update edge1
     * 3. add edge2, which has the same source and target with edge1
     * 4. add edge3, whose source is not in the vertices
     * 5. add edge4, whose target is not in the vertices
     * 6. delete edge1, which is in the edges
     * 7. delete edge1 again, which isn't in the edges
     * 8. delete edge5, whose source or target is not in the vertices
     * 9. delete edge6, whose source is not in the vertices
     * final: check the vertices
     * 
     * */
    @Test
    public void set() {
    	Graph<String> graph = emptyInstance();
    	graph.add(jack);
    	graph.add(rose);
    	assertEquals(0, graph.set(jack, rose, 1)); // 1
    	assertEquals(1, graph.set(jack, rose, 2)); // 2
    	assertEquals(2, graph.set(jack, rose, 3)); // 3
    	assertEquals(0, graph.set(tom, rose, 1)); // 4
    	assertEquals(0, graph.set(tom, klay, 1)); // 5
    	assertEquals(3, graph.set(jack, rose, 0)); // 6
    	assertEquals(0, graph.set(jack, rose, 0)); // 7
    	assertEquals(0, graph.set(klay, booker, 0)); // 8
    	assertEquals(0, graph.set(booker, klay, 0)); // 9
    	/* booker is not in the vertices */
    	Set<String> vertices = graph.vertices();
    	assertTrue(vertices.size() == 4);
    	assertTrue(vertices.contains(jack));
    	assertTrue(vertices.contains(rose));
    	assertTrue(vertices.contains(tom));
    	assertTrue(vertices.contains(klay));
    }
    
    /**
     * Test remove
     * remove the vertex which is isolated
     * remove the vertex which is pointed by one vertex
     * remove the vertex which is pointed by some vertices
     * remove the vertex which points to one vertex
     * remove the vertex which points to some vertices
     * remove the vertex which is pointed by and points to one vertex
     * remove the vertex which is pointed by and points to some vertices
     * remove the vertex which doesn't exist
     * 	1. add vertex1
     * 	2. remove vertex1 - add vertex1 after check
     * 	3. add vertex2
     * 	4. add edge1(vertex1, vertex2)
     * 	5. remove vertex2 - add vertex2 after check
     * 	6. add edge1(vertex1, vertex2)
     * 	7. add vertex 3
     * 	8. add edge2(vertex3, vertex2)
     *  9. remove vertex2 - add vertex2 after check
     * 10. add edge1(vertex1, vertex2)
     * 11. remove vertex1 - add veretex1 after check
     * 12. add edge1(vertex1, vertex2)
     * 13. add edge3(vertex1, vertex3)
     * 14. remove vertex1 - add vertex1 after check
     * 15. add edge1(vertex1, vertex2)
     * 16. add edge4(vertex2, vertex3)
     * 17. remove vertex2 - add vertex2 after check
     * 18. add edge1(vertex1, vertex2)
     * 19. add edge4(vertex2, vertex3)
     * 20. add vertex4
     * 21. add vertex5
     * 22. add edge5(vertex4, vertex2)
     * 23. add edge6(vertex2, vertex5)
     * 24. remove vertex2
     * 25. remove vertex6
     * 
     * */
    @Test
    public void remove() {
    	Graph<String> graph = emptyInstance();
    	graph.add(jack); // 1
    	graph.remove(jack); // 2
    	Set<String> set1 = graph.vertices(); // check
    	assertTrue(set1.isEmpty());
    	graph.add(jack);
    	graph.add(rose); // 3
    	graph.set(jack, rose, 1); // 4
    	graph.remove(rose); // 5
    	Set<String> set2 = graph.vertices(); // check
    	assertTrue(set2.size() == 1);
    	assertTrue(set2.contains(jack));
    	/* try to delete this edge, if return 0, symbolize this edge doesn't exist */
    	assertEquals(0, graph.set(jack, rose, 0));
    	graph.add(rose);
    	graph.set(jack, rose, 1); // 6
    	graph.add(tom); // 7
    	graph.set(tom, rose, 2); // 8
    	graph.remove(rose); // 9
    	Set<String> set3 = graph.vertices(); // check
    	assertTrue(set3.size() == 2);
    	assertTrue(set3.contains(jack));
    	assertTrue(set3.contains(tom));
    	assertEquals(0, graph.set(jack, rose, 0));
    	assertEquals(0, graph.set(tom, rose, 0));
    	graph.add(rose);
    	graph.set(jack, rose, 1); // 10
    	graph.remove(jack); // 11
    	Set<String> set4 = graph.vertices(); // check
    	assertTrue(set4.size() == 2);
    	assertTrue(set4.contains(rose));
    	assertTrue(set4.contains(tom));
    	assertEquals(0, graph.set(jack, rose, 0));
    	graph.add(jack);
    	graph.set(jack, rose, 1); // 12
    	graph.set(jack, tom, 2); // 13
    	graph.remove(jack); // 14
    	Set<String> set5 = graph.vertices(); // check
    	assertTrue(set5.size() == 2);
    	assertTrue(set5.contains(rose));
    	assertTrue(set5.contains(tom));
    	assertEquals(0, graph.set(jack, rose, 0));
    	assertEquals(0, graph.set(jack, tom, 0));
    	graph.add(jack);
    	graph.set(jack, rose, 1); // 15
    	graph.set(rose, tom, 2); // 16
    	graph.remove(rose); // 17
    	Set<String> set6 = graph.vertices(); // check
    	assertTrue(set6.size() == 2);
    	assertTrue(set6.contains(jack));
    	assertTrue(set6.contains(tom));
    	assertEquals(0, graph.set(jack, rose, 0));
    	assertEquals(0, graph.set(rose, jack, 0));
    	graph.add(rose);
    	graph.set(jack, rose, 1); // 18
    	graph.set(rose, tom, 2); // 19
    	graph.add(klay); // 20
    	graph.add(booker); // 21
    	graph.set(klay, rose, 3); // 22
    	graph.set(rose, booker, 4); // 23
    	graph.remove(rose); // 24
    	Set<String> set7 = graph.vertices(); // check
    	assertTrue(set7.size() == 4);
    	assertTrue(set7.contains(jack));
    	assertTrue(set7.contains(tom));
    	assertTrue(set7.contains(klay));
    	assertTrue(set7.contains(booker));
    	assertEquals(0, graph.set(jack, rose, 0));
    	assertEquals(0, graph.set(rose, tom, 0));
    	assertEquals(0, graph.set(klay, rose, 0));
    	assertEquals(0, graph.set(rose, booker, 0));
    	graph.add(rose);
    	assertFalse(graph.remove(allen)); // 25
    	Set<String> set8 = graph.vertices();
    	assertTrue(set8.size() == 5);
    	assertTrue(set8.contains(jack));
    	assertTrue(set8.contains(rose));
    	assertTrue(set8.contains(tom));
    	assertTrue(set8.contains(klay));
    	assertTrue(set8.contains(booker));
    }
    
    /**
     * Test vertices
     * add some vertices to the graph and check the set return by vertices()
     * 1. add vertex1
     * 2. add vertex2
     * 3. add vertex3
     * 
     * */
    @Test
    public void vertices() {
    	Graph<String> graph = emptyInstance();
    	graph.add(jack);
    	Set<String> set1 = graph.vertices();
    	assertEquals(1, set1.size());
    	assertTrue(set1.contains(jack));
    	graph.add(rose);
    	Set<String> set2 = graph.vertices();
    	assertEquals(2, set2.size());
    	assertTrue(set2.contains(rose));
    	graph.add(booker);
    	Set<String> set3 = graph.vertices();
    	assertEquals(3, set3.size());
    	assertTrue(set3.contains(booker));
    }
    
    /**
     * Test sources
     * add some vertices and edges to the graph and check the sources's performance
     * use query if we test the sources in that operation
     * 1. add vertex1
     * 2. add vertex2
     * 3. add edge1(vertex1, vertex2)
     * 4. query vertex2
     * 5. add vertex3
     * 6. add edge2(vertex3, vertex2)
     * 7. query vertex2
     * 8. query vertex1
     * 9. query vertex4
     * 
     * */
    @Test
    public void sources() {
    	Graph<String> graph = emptyInstance();
    	graph.add(jack); // 1
    	graph.add(rose); // 2
    	graph.set(jack, rose, 1); // 3
    	Map<String, Integer> sources1 = graph.sources(rose); // 4
    	assertTrue(sources1.size() == 1); 
    	assertTrue(sources1.containsKey(jack));
    	assertTrue(sources1.containsValue(1));
    	assertEquals(sources1.get(jack).intValue(), 1);
    	graph.add(tom); // 5
    	graph.set(tom, rose, 2); // 6
    	Map<String, Integer> sources2 = graph.sources(rose); // 7
    	assertTrue(sources2.size() == 2);
    	assertTrue(sources2.containsKey(jack));
    	assertTrue(sources2.containsValue(1));
    	assertEquals(sources2.get(jack).intValue(), 1);
    	assertTrue(sources2.containsKey(tom));
    	assertTrue(sources2.containsValue(2));
    	assertEquals(sources2.get(tom).intValue(), 2);
    	Map<String, Integer> sources3 = graph.sources(jack); // 8
    	assertTrue(sources3.isEmpty());
    	Map<String, Integer> sources4 = graph.sources(klay); // 9
    	assertTrue(sources4 == null);
    }
    
    /**
     * Test targets
     * add some vertices and edges to the graph and check the targets's performance
     * use query if we test the targets in that operation
     * 1. add vertex1
     * 2. add vertex2
     * 3. add edge1(vertex1, vertex2)
     * 4. query vertex1
     * 5. add vertex3
     * 6. add edge2(vertex1, vertex3)
     * 7. query vertex1
     * 8. query vertex2
     * 9. query vertex4
     * 
     * */
    @Test
    public void targets() {
    	Graph<String> graph = emptyInstance();
    	graph.add(jack); // 1
    	graph.add(rose); // 2
    	graph.set(jack, rose, 1); // 3
    	Map<String, Integer> sources1 = graph.targets(jack); // 4
    	assertTrue(sources1.size() == 1); 
    	assertTrue(sources1.containsKey(rose));
    	assertTrue(sources1.containsValue(1));
    	assertEquals(sources1.get(rose).intValue(), 1);
    	graph.add(tom); // 5
    	graph.set(jack, tom, 2); // 6
    	Map<String, Integer> sources2 = graph.targets(jack); // 7
    	assertTrue(sources2.size() == 2);
    	assertTrue(sources2.containsKey(rose));
    	assertTrue(sources2.containsValue(1));
    	assertEquals(sources2.get(rose).intValue(), 1);
    	assertTrue(sources2.containsKey(tom));
    	assertTrue(sources2.containsValue(2));
    	assertEquals(sources2.get(tom).intValue(), 2);
    	Map<String, Integer> sources3 = graph.targets(rose); // 8
    	assertTrue(sources3.isEmpty());
    	Map<String, Integer> sources4 = graph.targets(klay); // 9
    	assertTrue(sources4 == null);
    }
}
