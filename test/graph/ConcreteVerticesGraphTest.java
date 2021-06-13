/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
	
	/**
	 * create some vertices
	 * 
	 * */
	private Vertex<String> vJack = new Vertex<String>(jack);
	private Vertex<String> vRose = new Vertex<String>(rose);
	
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    
    /**
     * Test toString
     * add some vertices and some edges
     * 1. emptyGraph - toString
     * 2. add vertex1 - toString
     * 3. add vertex2 - toString
     * 4. add vertex3 whose sorted index is 1 - toString
     * 5. add edge1(vertex1, vertex2) - toString
     * 6. add edge2(vertex2, vertex3) - toString
     * 7. add edge3(vertex3, vertex2) - toString
     * 8. add edge4(vertex1, vertex3) - toString
     * 
     * */
    @Test
    public void toStringTest() {
    	ConcreteVerticesGraph<String> concreteVerticesGraph = (ConcreteVerticesGraph<String>) emptyInstance();
    	assertEquals("ConcreteVerticesGraph [vertices=[]]", concreteVerticesGraph.toString()); // 1
    	concreteVerticesGraph.add(jack); // 2
    	assertEquals("ConcreteVerticesGraph [vertices=[[jack, {}]]]", concreteVerticesGraph.toString());
    	concreteVerticesGraph.add(rose); // 3
    	assertEquals("ConcreteVerticesGraph [vertices=[[jack, {}], [rose, {}]]]", concreteVerticesGraph.toString());
    	concreteVerticesGraph.add(allen); // 4
    	assertEquals("ConcreteVerticesGraph [vertices=[[jack, {}], [rose, {}], [allen, {}]]]", concreteVerticesGraph.toString());
    	concreteVerticesGraph.set(jack, rose, 1); // 5
		assertEquals("ConcreteVerticesGraph [vertices=[[jack, {rose=1}], [rose, {}], [allen, {}]]]", concreteVerticesGraph.toString()); 
		concreteVerticesGraph.set(rose, allen, 2); // 6
		assertEquals("ConcreteVerticesGraph [vertices=[[jack, {rose=1}], [rose, {allen=2}], [allen, {}]]]", concreteVerticesGraph.toString());
		concreteVerticesGraph.set(allen, rose, 3); // 7
		assertEquals("ConcreteVerticesGraph [vertices=[[jack, {rose=1}], [rose, {allen=2}], [allen, {rose=3}]]]", concreteVerticesGraph.toString());
		concreteVerticesGraph.set(jack, allen, 4); // 7
		assertEquals("ConcreteVerticesGraph [vertices=[[jack, {allen=4, rose=1}], [rose, {allen=2}], [allen, {rose=3}]]]", concreteVerticesGraph.toString());
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
    
    // Testing strategy for Vertex
    //   TODO
    
    // TODO tests for operations of Vertex
    /**
     * Test Constructor and Getter
     * create some vertices and check
     * 
     * */
    @Test
    public void VertexConstructorTest() {
    	Vertex<String> tJack = new Vertex<String>("jack");
    	assertEquals("jack", tJack.getName());
    	Vertex<String> tRose = new Vertex<String>("rose");
    	assertEquals("rose", tRose.getName());
    }
    /**
     * Test setTarget
     * add some edges in the vertices
     * 
     * */
    @Test
    public void setTargetTest() {
    	assertTrue(vJack.setTarget(rose, 1));
    	Map<String, Integer> map1 = vJack.getTargets();
    	assertTrue(map1.containsKey(rose));
    	assertEquals(1, map1.get(rose).intValue());
    	assertTrue(vJack.setTarget(rose, 2));
    	Map<String, Integer> map2 = vJack.getTargets();
    	assertTrue(map2.containsKey(rose));
    	assertEquals(2, map2.get(rose).intValue());
    	assertFalse(vJack.setTarget(rose, -1));
    	
    }
    /**
     * Test deleteTarget
     * add some edges of one vertex(as the source), then delete some of them
     * 
     * */
    @Test
    public void deleteTargetTest() {
    	assertFalse(vJack.containsTarget(rose));
    	vJack.setTarget(rose, 1);
    	assertTrue(vJack.containsTarget(rose));
    	vJack.setTarget(tom, 2);
    	assertTrue(vJack.containsTarget(tom));
    	vJack.deleteTarget(tom);
    	assertFalse(vJack.containsTarget(tom));
    }
    /**
     * Test containsTarget
     * add two edges of one vertex(as the source)
     * 
     * */
    @Test
    public void containsTargetTest() {
    	vJack.setTarget(rose, 1);
    	assertTrue(vJack.containsTarget(rose));
    	vJack.setTarget(tom, 2);
    	assertTrue(vJack.containsTarget(tom));
    }
    /**
     * Test getTarget
     * add two edges of one vertex(as the source), change one of the two
     * 
     * */
    @Test
    public void getTargetTest() {
    	vJack.setTarget(rose, 1);
    	assertEquals(1, vJack.getTarget(rose));
    	vJack.setTarget(tom, 3);
    	assertEquals(3, vJack.getTarget(tom));
    	vJack.setTarget(rose, 2);
    	assertEquals(2, vJack.getTarget(rose));
    }
    /**
     * Test getTargets 
     * add two edges of one vertex(as the source)
     * 
     * */
    @Test
    public void getTargetsTest() {
    	vJack.setTarget(rose, 1);
    	Map<String, Integer> map1 = vJack.getTargets();
    	assertTrue(map1.size() == 1);
    	assertTrue(map1.containsKey(rose));
    	assertTrue(map1.containsValue(1));
    	vJack.setTarget(tom, 2);
    	Map<String, Integer> map2 = vJack.getTargets();
    	assertTrue(map2.size() == 2);
    	assertTrue(map2.containsKey(rose));
    	assertTrue(map2.containsValue(1));
    	assertEquals(1, map1.get(rose).intValue());
    	assertTrue(map2.containsKey(tom));
    	assertTrue(map2.containsValue(2));
    	assertEquals(2, map2.get(tom).intValue());
    }
    
    /**
     * Test setSource
     * add some edges of one vertex(as the target)
     * 
     * */
    @Test
    public void setSourceTest() {
    	assertTrue(vJack.setSource(rose, 1));
    	Map<String, Integer> map1 = vJack.getSources();
    	assertTrue(map1.containsKey(rose));
    	assertEquals(1, map1.get(rose).intValue());
    	assertTrue(vJack.setSource(rose, 2));
    	Map<String, Integer> map2 = vJack.getSources();
    	assertTrue(map2.containsKey(rose));
    	assertEquals(2, map2.get(rose).intValue());
    	assertFalse(vJack.setSource(rose, -1));
    	
    }
    /**
     * Test deleteSource
     * add some edges of one vertex(as the target), then delete some of them
     * 
     * */
    @Test
    public void deleteSourceTest() {
    	assertFalse(vJack.containsSource(rose));
    	vJack.setSource(rose, 1);
    	assertTrue(vJack.containsSource(rose));
    	vJack.setSource(tom, 2);
    	assertTrue(vJack.containsSource(tom));
    	vJack.deleteSource(tom);
    	assertFalse(vJack.containsSource(tom));
    }
    /**
     * Test containsSource
     * add two edges of one vertex(as the target)
     * 
     * */
    @Test
    public void containsSourceTest() {
    	vJack.setSource(rose, 1);
    	assertTrue(vJack.containsSource(rose));
    	vJack.setSource(tom, 2);
    	assertTrue(vJack.containsSource(tom));
    }
    /**
     * Test getSource
     * add two edges of one vertex(as the target), change one of the two
     * 
     * */
    @Test
    public void getSourceTest() {
    	vJack.setSource(rose, 1);
    	assertEquals(1, vJack.getSource(rose));
    	vJack.setSource(tom, 3);
    	assertEquals(3, vJack.getSource(tom));
    	vJack.setSource(rose, 2);
    	assertEquals(2, vJack.getSource(rose));
    }
    /**
     * Test getSources 
     * add two edges of one vertex(as the target)
     * 
     * */
    @Test
    public void getSourcesTest() {
    	vJack.setSource(rose, 1);
    	Map<String, Integer> map1 = vJack.getSources();
    	assertTrue(map1.size() == 1);
    	assertTrue(map1.containsKey(rose));
    	assertTrue(map1.containsValue(1));
    	vJack.setSource(tom, 2);
    	Map<String, Integer> map2 = vJack.getSources();
    	assertTrue(map2.size() == 2);
    	assertTrue(map2.containsKey(rose));
    	assertTrue(map2.containsValue(1));
    	assertEquals(1, map1.get(rose).intValue());
    	assertTrue(map2.containsKey(tom));
    	assertTrue(map2.containsValue(2));
    	assertEquals(2, map2.get(tom).intValue());
    }
    /**
     * Test Vertex Comparator
     * use list.contains() to check the comparator
     * two vertices are considered as the same only when they have the same name
     * 
     * */
    @Test
    public void vertexComparatorTest() {
    	List<Vertex<String>> list = new ArrayList<>();
    	list.add(vJack);
    	Vertex<String> vJack2 = new Vertex<String>(jack);
    	assertTrue(list.contains(vJack2));
    	vJack2.setTarget(rose, 1);
    	assertTrue(list.contains(vJack2));
    	assertFalse(list.contains(vRose));
    }
}
