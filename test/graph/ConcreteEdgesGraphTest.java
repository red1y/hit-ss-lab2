/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteEdgesGraph.toString()
    
    /**
     * Test toString
     * add some vertices and some edges
     * 1. emptyGraph - toString
     * 2. add vertex1 - toString
     * 3. add vertex2 - toString
     * 4. add vertex3 whose sorted index is 1 - toString
     * 5. add edge(vertex1, vertex2) - toString
     * 6. add edge(vertex2, vertex3) - toString
     * 7. add edge(vertex3, vertex2) - toString
     * 
     * */
    @Test
    public void toStringTest() {
    	ConcreteEdgesGraph<String> concreteEdgesGraph = (ConcreteEdgesGraph<String>) emptyInstance();
    	assertEquals("ConcreteEdgesGraph {vertices=[], edges=[]}", concreteEdgesGraph.toString()); // 1
    	concreteEdgesGraph.add(jack); // 2
    	assertEquals("ConcreteEdgesGraph {vertices=[jack], edges=[]}", concreteEdgesGraph.toString());
    	concreteEdgesGraph.add(rose); // 3
    	assertEquals("ConcreteEdgesGraph {vertices=[jack, rose], edges=[]}", concreteEdgesGraph.toString());
    	concreteEdgesGraph.add(allen); // 4
    	assertEquals("ConcreteEdgesGraph {vertices=[allen, jack, rose], edges=[]}", concreteEdgesGraph.toString());
		concreteEdgesGraph.set(jack, rose, 1); // 5
		assertEquals("ConcreteEdgesGraph {vertices=[allen, jack, rose], edges=[[jack, rose, 1]]}", concreteEdgesGraph.toString()); 
		concreteEdgesGraph.set(rose, allen, 2); // 6
		assertEquals("ConcreteEdgesGraph {vertices=[allen, jack, rose], edges=[[jack, rose, 1], [rose, allen, 2]]}", concreteEdgesGraph.toString());
		concreteEdgesGraph.set(allen, rose, 3); // 7
		assertEquals("ConcreteEdgesGraph {vertices=[allen, jack, rose], edges=[[jack, rose, 1], [rose, allen, 2], [allen, rose, 3]]}", concreteEdgesGraph.toString());
    }
    
    /**
     * Test add
     * 
     * */
    @Test
    public void addTest() {
    	super.add();
    }
    
    /**
     * Test set
     * 
     * */
    @Test
    public void setTest() {
    	super.set();
    }
    
    /**
     * Test remove
     * 
     * */
    @Test
    public void removeTest() {
    	super.remove();
    }
    
    /**
     * Test vertices
     * 
     * */
    @Test
    public void verticesTest() {
    	super.vertices();
    }
    
    /**
     * Test sources
     * 
     * */
    @Test
    public void sourcesTest() {
    	super.sources();
    }
    
    /**
     * Test targets
     * 
     * */
    @Test
    public void targetsTest() {
    	super.targets();
    }
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   TODO
    /**
     * Test Edge Constructor 
     * use Getters to check the attributes which also check the Getters
     * 
     * */
    @Test
    public void EdgeConstructorTest() {
    	/* create instances of Edge */
    	Edge<String> edge1 = new Edge<>(jack, rose, 1);
    	Edge<String> edge2 = new Edge<>(rose, tom, 2);
    	Edge<String> edge3 = new Edge<>(tom, jack, 3);
    	/* test */
    	assertEquals(jack, edge1.getSource());
    	assertEquals(rose, edge1.getTarget());
    	assertEquals(1, edge1.getWeight());
    	assertEquals(rose, edge2.getSource());
    	assertEquals(tom, edge2.getTarget());
    	assertEquals(2, edge2.getWeight());
    	assertEquals(tom, edge3.getSource());
    	assertEquals(jack, edge3.getTarget());
    	assertEquals(3, edge3.getWeight());	
    }
    // TODO tests for operations of Edge
    /**
     * Test checkRep
     * create illegal data which violate the RI
     * 1. Edge(null, tom, 1)
     * 2. Edge(tom, null, 1)
     * 3. Edge(tom, tom, 1)
     * 4. Edge(tom, rose, 0)
     * 5. Edge(tom, rose, -1)
     * 
     * */
	@Test
    public void checkRepTest() {
    	assertThrows(RuntimeException.class, () -> {
    		new Edge<String>(null, tom, 1);
    	});
    	assertThrows(RuntimeException.class, () -> {
    		new Edge<String>(tom, null, 1);
    	});
    	assertThrows(RuntimeException.class, () -> {
    		new Edge<String>(tom, tom, 1);
    	});
    	assertThrows(RuntimeException.class, () -> {
    		new Edge<String>(tom, rose, 0);
    	});
    	assertThrows(RuntimeException.class, () -> {
    		new Edge<String>(tom, rose, -1);
    	});
    }

    /**
     * Test Edge Comparator
     * use set.contains() to see if set solve the Edge as we expected
     * 1. edges with same(source, target, weight)
     * 2. edges with same(source, target) and different weight
     * 3. edges with different source or target
     * 
     * */
    @Test
    public void EdgeComparatorTest() {
    	Edge<String> edge1 = new Edge<String>(rose, jack, 1);
    	Edge<String> edge2 = new Edge<String>(rose, jack, 1);
    	Edge<String> edge3 = new Edge<String>(rose, jack, 3);
    	Edge<String> edge4 = new Edge<String>(rose, tom, 1);
    	
    	Set<Edge<String>> set = new HashSet<>();
    	set.add(edge1);
    	
    	assertTrue(set.contains(edge2));
    	assertTrue(set.contains(edge3));
    	assertFalse(set.contains(edge4));
    }
}
