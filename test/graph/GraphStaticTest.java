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
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {
	/*
	 * Test int and char in this test
	 * current is int
	 * and if you want to test char, follow the steps
	 * 1. replace all "Character" to "Integer" 
	 * 2. annotate the char type field(lines 51 - 56)
	 * 3. cancel annotation of the int type field(lines 40 - 45)
	 * 4. run test
	 * 
	 * */
    
	/**
	 * create some instances of immutable types
	 * 
	 * */
	/*
	 * Test int/Integer 
	 * 
	 * */
//	private int jack = 1;
//	private int rose = 2;
//	private int tom = 3;
//	private int klay = 4;
//	private int booker = 5;
//	private int allen = 6;
	
	/*
	 * Test char/Character 
	 * 
	 * */
	private char jack = 'j';
	private char rose = 'r';
	private char tom = 't';
	private char klay = 'k';
	private char booker = 'b';
	private char allen = 'a';
	
    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }
    
    // TODO test other vertex label types in Problem 3.2
    
    /**
     * Test add
     * 
     * */
    @Test
    public void addTest() {
    	Graph<Character> concreteVerticesGraph = Graph.empty();
    	assertTrue(concreteVerticesGraph.add(jack));
    	assertTrue(concreteVerticesGraph.add(rose));
    	assertFalse(concreteVerticesGraph.add(jack));
    	
    }
    
    /**
     * Test set
     * 
     * */
    @Test
    public void setTest() {
    	Graph<Character> concreteVerticesGraph = Graph.empty();
    	concreteVerticesGraph.add(jack);
    	concreteVerticesGraph.add(rose);
    	assertEquals(0, concreteVerticesGraph.set(jack, rose, 1)); // 1
    	assertEquals(1, concreteVerticesGraph.set(jack, rose, 2)); // 2
    	assertEquals(2, concreteVerticesGraph.set(jack, rose, 3)); // 3
    	assertEquals(0, concreteVerticesGraph.set(tom, rose, 1)); // 4
    	assertEquals(0, concreteVerticesGraph.set(tom, klay, 1)); // 5
    	assertEquals(3, concreteVerticesGraph.set(jack, rose, 0)); // 6
    	assertEquals(0, concreteVerticesGraph.set(jack, rose, 0)); // 7
    	assertEquals(0, concreteVerticesGraph.set(klay, booker, 0)); // 8
    	assertEquals(0, concreteVerticesGraph.set(booker, klay, 0)); // 9
    	/* booker is not in the vertices */
    	Set<Character> vertices = concreteVerticesGraph.vertices();
    	assertTrue(vertices.size() == 4);
    	assertTrue(vertices.contains(jack));
    	assertTrue(vertices.contains(rose));
    	assertTrue(vertices.contains(tom));
    	assertTrue(vertices.contains(klay));
    }
    
    /**
     * Test remove
     * 
     * */
    @Test
    public void removeTest() {
    	Graph<Character> concreteVerticesGraph = Graph.empty();
    	concreteVerticesGraph.add(jack); // 1
    	concreteVerticesGraph.remove(jack); // 2
    	Set<Character> set1 = concreteVerticesGraph.vertices(); // check
    	assertTrue(set1.isEmpty());
    	concreteVerticesGraph.add(jack);
    	concreteVerticesGraph.add(rose); // 3
    	concreteVerticesGraph.set(jack, rose, 1); // 4
    	concreteVerticesGraph.remove(rose); // 5
    	Set<Character> set2 = concreteVerticesGraph.vertices(); // check
    	assertTrue(set2.size() == 1);
    	assertTrue(set2.contains(jack));
    	/* try to delete this edge, if return 0, symbolize this edge doesn't exist */
    	assertEquals(0, concreteVerticesGraph.set(jack, rose, 0));
    	concreteVerticesGraph.add(rose);
    	concreteVerticesGraph.set(jack, rose, 1); // 6
    	concreteVerticesGraph.add(tom); // 7
    	concreteVerticesGraph.set(tom, rose, 2); // 8
    	concreteVerticesGraph.remove(rose); // 9
    	Set<Character> set3 = concreteVerticesGraph.vertices(); // check
    	assertTrue(set3.size() == 2);
    	assertTrue(set3.contains(jack));
    	assertTrue(set3.contains(tom));
    	assertEquals(0, concreteVerticesGraph.set(jack, rose, 0));
    	assertEquals(0, concreteVerticesGraph.set(tom, rose, 0));
    	concreteVerticesGraph.add(rose);
    	concreteVerticesGraph.set(jack, rose, 1); // 10
    	concreteVerticesGraph.remove(jack); // 11
    	Set<Character> set4 = concreteVerticesGraph.vertices(); // check
    	assertTrue(set4.size() == 2);
    	assertTrue(set4.contains(rose));
    	assertTrue(set4.contains(tom));
    	assertEquals(0, concreteVerticesGraph.set(jack, rose, 0));
    	concreteVerticesGraph.add(jack);
    	concreteVerticesGraph.set(jack, rose, 1); // 12
    	concreteVerticesGraph.set(jack, tom, 2); // 13
    	concreteVerticesGraph.remove(jack); // 14
    	Set<Character> set5 = concreteVerticesGraph.vertices(); // check
    	assertTrue(set5.size() == 2);
    	assertTrue(set5.contains(rose));
    	assertTrue(set5.contains(tom));
    	assertEquals(0, concreteVerticesGraph.set(jack, rose, 0));
    	assertEquals(0, concreteVerticesGraph.set(jack, tom, 0));
    	concreteVerticesGraph.add(jack);
    	concreteVerticesGraph.set(jack, rose, 1); // 15
    	concreteVerticesGraph.set(rose, tom, 2); // 16
    	concreteVerticesGraph.remove(rose); // 17
    	Set<Character> set6 = concreteVerticesGraph.vertices(); // check
    	assertTrue(set6.size() == 2);
    	assertTrue(set6.contains(jack));
    	assertTrue(set6.contains(tom));
    	assertEquals(0, concreteVerticesGraph.set(jack, rose, 0));
    	assertEquals(0, concreteVerticesGraph.set(rose, jack, 0));
    	concreteVerticesGraph.add(rose);
    	concreteVerticesGraph.set(jack, rose, 1); // 18
    	concreteVerticesGraph.set(rose, tom, 2); // 19
    	concreteVerticesGraph.add(klay); // 20
    	concreteVerticesGraph.add(booker); // 21
    	concreteVerticesGraph.set(klay, rose, 3); // 22
    	concreteVerticesGraph.set(rose, booker, 4); // 23
    	concreteVerticesGraph.remove(rose); // 24
    	Set<Character> set7 = concreteVerticesGraph.vertices(); // check
    	assertTrue(set7.size() == 4);
    	assertTrue(set7.contains(jack));
    	assertTrue(set7.contains(tom));
    	assertTrue(set7.contains(klay));
    	assertTrue(set7.contains(booker));
    	assertEquals(0, concreteVerticesGraph.set(jack, rose, 0));
    	assertEquals(0, concreteVerticesGraph.set(rose, tom, 0));
    	assertEquals(0, concreteVerticesGraph.set(klay, rose, 0));
    	assertEquals(0, concreteVerticesGraph.set(rose, booker, 0));
    	concreteVerticesGraph.add(rose);
    	assertFalse(concreteVerticesGraph.remove(allen )); // 25
    	Set<Character> set8 = concreteVerticesGraph.vertices();
    	assertTrue(set8.size() == 5);
    	assertTrue(set8.contains(jack));
    	assertTrue(set8.contains(rose));
    	assertTrue(set8.contains(tom));
    	assertTrue(set8.contains(klay));
    	assertTrue(set8.contains(booker));
    }
    
    /**
     * Test vertices
     * 
     * */
    @Test
    public void verticesTest() {
    	Graph<Character> concreteVerticesGraph = Graph.empty();
    	concreteVerticesGraph.add(jack); // 1
    	Set<Character> set1 = concreteVerticesGraph.vertices(); // check
    	assertEquals(1, set1.size());
    	assertTrue(set1.contains(jack));
    	concreteVerticesGraph.add(rose); // 2
    	Set<Character> set2 = concreteVerticesGraph.vertices(); // check
    	assertEquals(2, set2.size());
    	assertTrue(set2.contains(rose));
    	concreteVerticesGraph.add(booker); // 3
    	Set<Character> set3 = concreteVerticesGraph.vertices(); // check
    	assertEquals(3, set3.size());
    	assertTrue(set3.contains(booker));
    }
    
    /**
     * Test sources
     * 
     * */
    @Test
    public void sourcesTest() {
    	Graph<Character> concreteVerticesGraph = Graph.empty();
    	concreteVerticesGraph.add(jack); // 1
    	concreteVerticesGraph.add(rose); // 2
    	concreteVerticesGraph.set(jack, rose, 1); // 3
    	Map<Character, Integer> sources1 = concreteVerticesGraph.sources(rose); // 4
    	assertTrue(sources1.size() == 1); 
    	assertTrue(sources1.containsKey(jack));
    	assertTrue(sources1.containsValue(1));
    	assertEquals(sources1.get(jack).intValue(), 1);
    	concreteVerticesGraph.add(tom); // 5
    	concreteVerticesGraph.set(tom, rose, 2); // 6
    	Map<Character, Integer> sources2 = concreteVerticesGraph.sources(rose); // 7
    	assertTrue(sources2.size() == 2);
    	assertTrue(sources2.containsKey(jack));
    	assertTrue(sources2.containsValue(1));
    	assertEquals(sources2.get(jack).intValue(), 1);
    	assertTrue(sources2.containsKey(tom));
    	assertTrue(sources2.containsValue(2));
    	assertEquals(sources2.get(tom).intValue(), 2);
    	Map<Character, Integer> sources3 = concreteVerticesGraph.sources(jack); // 8
    	assertTrue(sources3.isEmpty());
    	Map<Character, Integer> sources4 = concreteVerticesGraph.sources(klay); // 9
    	assertTrue(sources4 == null);
    }
    
    /**
     * Test targets
     * 
     * */
    @Test
    public void targetsTest() {
    	Graph<Character> concreteVerticesGraph = Graph.empty();
    	concreteVerticesGraph.add(jack); // 1
    	concreteVerticesGraph.add(rose); // 2
    	concreteVerticesGraph.set(jack, rose, 1); // 3
    	Map<Character, Integer> sources1 = concreteVerticesGraph.targets(jack); // 4
    	assertTrue(sources1.size() == 1); 
    	assertTrue(sources1.containsKey(rose));
    	assertTrue(sources1.containsValue(1));
    	assertEquals(sources1.get(rose).intValue(), 1);
    	concreteVerticesGraph.add(tom); // 5
    	concreteVerticesGraph.set(jack, tom, 2); // 6
    	Map<Character, Integer> sources2 = concreteVerticesGraph.targets(jack); // 7
    	assertTrue(sources2.size() == 2);
    	assertTrue(sources2.containsKey(rose));
    	assertTrue(sources2.containsValue(1));
    	assertEquals(sources2.get(rose).intValue(), 1);
    	assertTrue(sources2.containsKey(tom));
    	assertTrue(sources2.containsValue(2));
    	assertEquals(sources2.get(tom).intValue(), 2);
    	Map<Character, Integer> sources3 = concreteVerticesGraph.targets(rose); // 8
    	assertTrue(sources3.isEmpty());
    	Map<Character, Integer> sources4 = concreteVerticesGraph.targets(klay); // 9
    	assertTrue(sources4 == null);
    }
}
