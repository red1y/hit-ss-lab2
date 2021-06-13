/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import graph.Graph;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
	
	/**
	 * create some file instance
	 * 
	 * */
	public File l1w5 = new File("test/poet/lines1words5.txt");
	public File l2w5 = new File("test/poet/lines2words5.txt");
	public File l1w6 = new File("test/poet/lines1words6.txt");
	
	/**
	 * create some constant string value 
	 * 
	 * */
	public String carrie = "carrie";
	public String is = "is";
	public String a = "a";
	public String beautiful = "beautiful";
	public String girl = "girl";
	public String so = "so";
	public String such = "such";
	private String sweet = "sweet";
    
	/**
	 * get an instance of GraphPoet
	 * 
	 * */
	public GraphPoet getInstace(File corpus) throws IOException {
		return new GraphPoet(corpus);
	}
	/**
	 * compare if the two lists are equal
	 * 
	 * @param1 list1
	 * @param2 list2
	 * @return true if the have the same size and same value in order, false if not
	 * 
	 * */
	public <E> boolean isEqualList(List<E> list1, List<E> list2) {
		int i;
		if(!(list1.size() == list2.size())) {
			return false;
		}
    	for(i = 0; i < list1.size(); i++) {
    		if(!(list1.get(i).equals(list2.get(i)))) {
    			return false;
    		}
    	}
    	return true;
	}
	
    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO tests
    
    /**
     * Test Constructor
     * test if the graph constructed correctly
     * specifically, check the vertices and edges add and update correctly
     * @note cancel the annotation of getGraph() in the GraphPoem when test 
     * 
     * 1. test txt1: "carrie is a beautiful girl"
     * 2. test txt2: "carrie is a beautiful girl\ncarrie is a sweet girl"
     * 
     * */
    @Test
    public void GraphPoetConstructorTest() throws IOException {
    	/* test1 */
    	GraphPoet graphPoet = getInstace(l1w5); // 0
    	Graph<String> graph = graphPoet.getGraph();
    	/* test the vertices */
    	Set<String> vertices = graph.vertices();
    	assertTrue(vertices.size() == 5);
    	assertTrue(vertices.contains(carrie));
    	assertTrue(vertices.contains(is));
    	assertTrue(vertices.contains(a));
    	assertTrue(vertices.contains(beautiful));
    	assertTrue(vertices.contains(girl));
    	/* test the edges */
    	assertEquals(1, graph.targets(carrie).get(is).intValue());
    	assertEquals(1, graph.targets(is).get(a).intValue());
    	assertEquals(1, graph.targets(a).get(beautiful).intValue());
    	assertEquals(1, graph.targets(beautiful).get(girl).intValue());
    	/* test2 */
    	GraphPoet graphPoet2 = getInstace(l2w5);
    	Graph<String> graph2 = graphPoet2.getGraph();
    	/* test the vertices */
    	Set<String> vertices2 = graph2.vertices();
    	assertTrue(vertices2.size() == 6);
    	assertTrue(vertices2.contains(carrie));
    	assertTrue(vertices2.contains(is));
    	assertTrue(vertices2.contains(a));
    	assertTrue(vertices2.contains(beautiful));
    	assertTrue(vertices2.contains(sweet));
    	assertTrue(vertices2.contains(girl));
    	/* test the edges */
    	assertEquals(2, graph2.targets(carrie).get(is).intValue());
    	assertEquals(2, graph2.targets(is).get(a).intValue());
    	assertEquals(1, graph2.targets(a).get(beautiful).intValue());
    	assertEquals(1, graph2.targets(a).get(sweet).intValue());
    	assertEquals(1, graph2.targets(beautiful).get(girl).intValue());
    	assertEquals(1, graph2.targets(sweet).get(girl).intValue());
    }
    
    /**
     * Test Poem 
     * 
     * 0: test txt1: "carrie is a beautiful girl"
     * 1. test input1: "carrie a"
     * 2. test input2: "A girl"
     * 3. test input3: "Crystal a girl"
     * 00: test txt2: "carrie is a beautiful girl\ncarrie is a sweet girl"
     * 4. test input4: "Carrie a girl"
     * 
     * */
    @Test
    public void poemTest() throws IOException {
    	GraphPoet graphPoet = getInstace(l1w5);
    	String sentence1 = "carrie a"; // 1
    	assertEquals("1. carrie is a\n", graphPoet.poem(sentence1));
    	String sentence2 = "A girl"; // 2
    	assertEquals("1. A beautiful girl\n", graphPoet.poem(sentence2));
    	String sentence3 = "Crystal a girl"; // 3
    	assertEquals("1. Crystal a beautiful girl\n", graphPoet.poem(sentence3));
    	
    	GraphPoet graphPoet2 = getInstace(l2w5);
    	String sentence4 = "Carrie a girl"; // 4
    	assertEquals("1. Carrie is a beautiful girl\n2. Carrie is a sweet girl\n", graphPoet2.poem(sentence4));
    }
    
    /**
     * Test getPoemLines
     * @note change private -> public of method getPoemsLines in GraphPoem when test
     * 
     * 1. "carrie is a beautiful girl"
     * 2. "carrie is a beautiful girl\ncarrie is a sweet girl"
     * 
     * */
    @Test
    public void getPoemLinesTest() throws IOException {
    	GraphPoet graphPoet = getInstace(l1w5);
    	int i;
    	List<List<String>> listExpected = new ArrayList<>();
    	List<List<String>> listActual = graphPoet.getPoemLines(l1w5);
    	
    	listExpected.add(new ArrayList<>());
    	listExpected.get(0).add("carrie");
    	listExpected.get(0).add("is");
    	listExpected.get(0).add("a");
    	listExpected.get(0).add("beautiful");
    	listExpected.get(0).add("girl");
    	assertTrue(listExpected.size() == listActual.size());
    	for(i = 0; i < listExpected.size(); i++) {
    		assertTrue(isEqualList(listExpected.get(i), listActual.get(i)));
    	}
    	
    	listActual = graphPoet.getPoemLines(l2w5);
    	listExpected.add(new ArrayList<>());
    	listExpected.get(1).add("carrie");
    	listExpected.get(1).add("is");
    	listExpected.get(1).add("a");
    	listExpected.get(1).add("sweet");
    	listExpected.get(1).add("girl");
    	assertTrue(listExpected.size() == listActual.size());
    	for(i = 0; i < listExpected.size(); i++) {
    		assertTrue(isEqualList(listExpected.get(i), listActual.get(i)));
    	}
    }
    /**
     * Test dfsPoems
     * check from print
     * @use getPoems() to get the private field poems in GraphPoet, delete that method after test 
     * @note change private -> public of method dfsPoems in GraphPoem when test
     * 
     * 1. [carrie, is, [so, such], a, [beautiful, sweet], girl]
     * @expected
     * 	1. carrie is so a beautiful girl
     * 	2. carrie is so a sweet girl
     * 	3. carrie is such a beautiful girl
     * 	4. carrie is such a sweet girl
     * 
     * */
    @Test
    public void dfsPoemsTest() throws IOException {
    	GraphPoet graphPoet = getInstace(l1w5);
    	List<Object> poemsWordsList = new ArrayList<>();
    	poemsWordsList.add(carrie);
    	poemsWordsList.add(is);
    	/* bridges1 */
    	List<String> bridges1 = new ArrayList<>();
    	bridges1.add(so);
    	bridges1.add(such);
    	poemsWordsList.add(bridges1);
    	poemsWordsList.add(a);
    	/* bridges 2 */
    	List<String> bridges2 = new ArrayList<>();
    	bridges2.add(beautiful);
    	bridges2.add(sweet );
    	poemsWordsList.add(bridges2);
    	poemsWordsList.add(girl);
    	StringBuilder poemBuilder = new StringBuilder();
    	graphPoet.dfsPoems(poemsWordsList, 0, poemBuilder);
    	System.out.println(graphPoet.getPoems());
    }
    
    /**
     * Test getPoemsWordsList
     * @note: change private -> public of method getPoemsWordsList in GraphPoem when test
     * 
     * 0. test txt1: "carrie is a beautiful girl"
     * 00. test txt2: "carrie is a so beautiful girl"
     * 000. test txt3: "carrie is a beautiful girl\ncarrie is a sweet girl"
     * 1. txt1[carrie, a, girl] - @expected [carrie, [is], a, [beautiful], girl]
     * 2. txt2[carrie, a, girl] - @expected [carrie, [is], a, girl]
     * 3. txt3[carrie, a, girl] - @expected [carrie, [is], a, [beautiful, sweet], girl]
     * 
     * */
    @Test
    public void getPoemsWordsListTest() throws IOException {
    	/* Test 1 */
    	GraphPoet graphPoet = getInstace(l1w5);
    	List<String> inputWords = new ArrayList<>();
    	inputWords.add(carrie);
    	inputWords.add(a);
    	inputWords.add(girl);
    	List<Object> poemsWordsList = graphPoet.getPoemsWordsList(inputWords);
    	/* check the length */
    	assertTrue(poemsWordsList.size() == 5);
    	/* check the type */
    	assertTrue(poemsWordsList.get(0).getClass() == String.class);
    	assertTrue(poemsWordsList.get(1).getClass() == ArrayList.class);
    	assertTrue(poemsWordsList.get(2).getClass() == String.class);
    	assertTrue(poemsWordsList.get(3).getClass() == ArrayList.class);
    	assertTrue(poemsWordsList.get(4).getClass() == String.class);
    	/* check the value */
    	List<String> bridges1 = new ArrayList<>(); bridges1.add(is);
    	List<String> bridges2 = new ArrayList<>(); bridges2.add(beautiful);
    	assertEquals(carrie, poemsWordsList.get(0));
    	assertEquals(bridges1.get(0), ((List<?>) poemsWordsList.get(1)).get(0));
    	assertEquals(a, poemsWordsList.get(2));
    	assertEquals(bridges2.get(0), ((List<?>) poemsWordsList.get(3)).get(0));
    	assertEquals(girl, poemsWordsList.get(4));
    	
    	/* Test 2 */
    	GraphPoet graphPoet2 = getInstace(l1w6);
    	List<String> inputWords2 = new ArrayList<>();
    	inputWords2.add(carrie);
    	inputWords2.add(a);
    	inputWords2.add(girl);
    	List<Object> poemsWordsList2 = graphPoet2.getPoemsWordsList(inputWords2);
    	/* check the length */
    	assertTrue(poemsWordsList2.size() == 4);
    	/* check the type */
    	assertTrue(poemsWordsList2.get(0).getClass() == String.class);
    	assertTrue(poemsWordsList2.get(1).getClass() == ArrayList.class);
    	assertTrue(poemsWordsList2.get(2).getClass() == String.class);
    	assertTrue(poemsWordsList2.get(3).getClass() == String.class);
    	/* check the value */
    	List<String> bridges21 = new ArrayList<>(); bridges21.add(is);
    	assertEquals(carrie, poemsWordsList2.get(0));
    	assertEquals(bridges21.get(0), ((List<?>) poemsWordsList2.get(1)).get(0));
    	assertEquals(a, poemsWordsList2.get(2));
    	assertEquals(girl, poemsWordsList2.get(3));
    	
    	/* Test 3 */
    	GraphPoet graphPoet3 = getInstace(l2w5);
    	List<String> inputWords3 = new ArrayList<>();
    	inputWords3.add(carrie);
    	inputWords3.add(a);
    	inputWords3.add(girl);
    	List<Object> poemsWordsList3 = graphPoet3.getPoemsWordsList(inputWords3);
    	/* check the length */
    	assertTrue(poemsWordsList3.size() == 5);
    	/* check the type */
    	assertTrue(poemsWordsList3.get(0).getClass() == String.class);
    	assertTrue(poemsWordsList3.get(1).getClass() == ArrayList.class);
    	assertTrue(poemsWordsList3.get(2).getClass() == String.class);
    	assertTrue(poemsWordsList3.get(3).getClass() == ArrayList.class);
    	assertTrue(poemsWordsList3.get(4).getClass() == String.class);
    	/* check the value */
    	List<String> bridges31 = new ArrayList<>(); bridges31.add(is);
    	List<String> bridges32 = new ArrayList<>(); bridges32.add(beautiful); bridges32.add(sweet);
    	assertEquals(carrie, poemsWordsList3.get(0));
    	assertEquals(bridges31.get(0), ((List<?>) poemsWordsList3.get(1)).get(0));
    	assertEquals(a, poemsWordsList3.get(2));
    	assertEquals(bridges32.get(0), ((List<?>) poemsWordsList3.get(3)).get(0));
    	assertEquals(bridges32.get(1), ((List<?>) poemsWordsList3.get(3)).get(1));
    	assertEquals(girl, poemsWordsList3.get(4));
    }
}