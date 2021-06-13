/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    /* the poems built from the poemsWordsList */
    private StringBuilder poems = null;
    /* record the number of poems */
    private int poemsNum = 0;
    
    /**
     * only for [TEST]
     * @note: delete or annotate after Test
     * 
     * */
    public Graph<String> getGraph() {
		return graph;
	}
    
    // Abstraction function:
    //   TODO
    /**
     * Abstraction function
     * Graph of words -> linear sentences
     * 
     * */
    // Representation invariant:
    //   TODO
    /**
     * Representation
     * vertices of the GraphPoet are String and only consists of a-z
     * 
     * */
    // Safety from rep exposure:
    //   TODO
    /**
     * Safety from rep exposure
     * poem is the only method provided for the client and it return a String which can't reach the internal
     * all other methods are changed to be private when @Test is done
     * all fields are private and no Getters or Setters
     * 
     * */
	
	
	/**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
    	/* initial the field */
    	poems = new StringBuilder();
    	poemsNum = 0;
    	/* read the file and get words list of each line */
    	List<List<String>> poemLines = getPoemLines(corpus);
    	String preWord = null;
    	for(List<String> line : poemLines) {
    		for(String word : line) {
    			if(line.indexOf(word) != 0) {
    				graph.set(preWord.toLowerCase(), word.toLowerCase(), graph.set(preWord.toLowerCase(), word.toLowerCase(), 0) + 1);
    			}
    			preWord = word;
    		}
    	}
    	if(!checkRep()) {
    		throw new RuntimeException("RI error");
    	}
    }
    
    // TODO checkRep
    /**
     * checkRep
     * @note public -> private after Test
     * 
     * @return false when a vertex in the GraphPoem contains the character out of [a-z]
     * 
     * */
    public boolean checkRep() {
		Set<String> vertices = graph.vertices();
		for(String vertex : vertices) {
			for(int i = 0; i < vertex.length(); i++) {
				if(!(vertex.charAt(i) >= 'a' && vertex.charAt(i) <= 'z')) {
					return false;
				}
			}
		}
    	return true;
	}
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     * 
     */
    public String poem(String input) {
    	/* the words list for poems */
    	List<Object> poemsWordsList = new ArrayList<>();
    	/* clear the field */
    	poems = new StringBuilder();
    	poemsNum = 0;
    	
    	input = input.replace(".", "");
    	input = input.replace(",", "");
    	/* get the input words list */
    	List<String> inputWords = Arrays.asList(input.split(" "));
    	/* get the poemsWordsList */
    	poemsWordsList = getPoemsWordsList(inputWords);
    	/* use depth first search to get all the poems */
    	dfsPoems(poemsWordsList, 0, new StringBuilder());
    	
    	/* return poems */
    	return poems.toString();
    }
    
    /**
     * getPoemLine
     * put every line in a list, and every word of each line in a list of which
     * @note public -> private after @Test
     * 
     * @param File to be read
     * @return a two dimension list 
     * 
     * */
    public List<List<String>> getPoemLines(File corpus) throws IOException {
    	List<List<String>> poemLine = new ArrayList<>();
    	try (BufferedReader bufferedReader = new BufferedReader(new FileReader(corpus))) {
			String line;
			while((line = bufferedReader.readLine()) != null) {
				line = line.replace(".", "");
				line = line.replace(",", "");
				List<String> lineWords = Arrays.asList(line.split(" "));
				poemLine.add(lineWords);
			}
		}
    	return poemLine;
    }
    /**
     * dfsPoems
     * get all poems by DFS
     * @note public -> private after @Test
     * 
     * @param1 the poem words list
     * @param2 the index of the list
     * @param3 a StringBuilder which record the path(words list)
     * @effect add new poem every time to the poems(StringBuilder)
     * 
     * */
    public void dfsPoems(List<Object> poemsWordsList, int index ,StringBuilder poemBuilder) {
    	/* finish and create one poem */
    	if(index >= poemsWordsList.size()) {
    		/* add it to the poems with no last whitespace */
    		poems.append((1 + poemsNum++) + ". " + poemBuilder.substring(0, poemBuilder.length() - 1) + "\n");
    		return ;
    	}
    	/* check the type of this object */
    	if(poemsWordsList.get(index).getClass() == String.class) {
    		String word = (String)poemsWordsList.get(index);
    		/* append this word to the poem builder */
    		poemBuilder.append(word + " ");
    		dfsPoems(poemsWordsList, index + 1, poemBuilder);
    		/* delete this word from the poem builder */
    		poemBuilder.delete(poemBuilder.length() - word.length() - 1, poemBuilder.length());
    	} else { /* this is a list */
			@SuppressWarnings("unchecked")
			List<String> bridges = (List<String>) poemsWordsList.get(index);
			for(String bridge : bridges) {
				/* add this bridge to the poem */
				poemBuilder.append(bridge + " ");
				dfsPoems(poemsWordsList, index + 1, poemBuilder);
				/* delete this bridge from the poem */
				poemBuilder.delete(poemBuilder.length() - bridge.length() - 1, poemBuilder.length());
			}
		}
	}
    
    /**
     * getPoemsWordsList
     * insert bridges between two words in the words list, the bridges are consisted in a list
     * @note public -> private after @Test
     * 
     * @param list of input words
     * @return a list of object which insert some lists of bridges between two words
     * @example
     * 	inputWords: [carrie, is, a, girl]
     * 	return: [carrie, is, a, [beautiful, sweet], girl], which insert bridges(beautiful, sweet) as a list
     * 
     * */
    public List<Object> getPoemsWordsList(List<String> inputWords) {
    	List<Object> poemsWordsList = new ArrayList<>();
    	String preWord = null;
    	
    	Map<String, Integer> preWordTargets = null;
    	Map<String, Integer> wordSources = null;
    	
    	/* record the maxWeight */
    	int maxWeight = 0;
    	/* record the weight of each bridge */
    	int tmpWeight = 0;
    	/* the bridge between two words */
    	String bridge = null;
    	
    	for(String word : inputWords) {
    		if(inputWords.indexOf(word) != 0) {
    			/* 
    			 * maxWeightBridgeList
    			 * create a new instance for every loop!!!!!
    			 * store the bridges with max weight
    			 * 
    			 * */
    			List<String> maxWeightBridgeList = new ArrayList<>();
    			/* reset the maxWeight */
    			maxWeight = 0;
    			/* get the targets of preWord */
    			preWordTargets = graph.targets(preWord.toLowerCase());
    			/* get the sources of this word */
    			wordSources = graph.sources(word.toLowerCase());
    			/* if targets and sources are both not null */
    			if(preWordTargets != null && wordSources != null) {
    				/* step through the targets of preWrod */
        			for(Map.Entry<String, Integer> entry : preWordTargets.entrySet()) {
        				/* this world can be a bridge between preWord and this word */
        				if(wordSources.containsKey((bridge = entry.getKey()))) {
        					/* compute the weight of this bridge */
        					tmpWeight = entry.getValue() + wordSources.get(bridge).intValue();
        					/* it is a new max weight */
        					if(tmpWeight > maxWeight) {
        						/* update the max weight */
        						maxWeight = tmpWeight;
        						/* clear the list and add it */
        						maxWeightBridgeList.clear();
        						maxWeightBridgeList.add(bridge);
        					} else if(tmpWeight == maxWeight) { /* it is another max weight */
        						/* add it to the list */
        						maxWeightBridgeList.add(bridge);
        					} else { /* not use this bridge */
        						/* nothing to do */
        					}
        				}
        			}
        			/* there are some bridges can be added */
        			if(!maxWeightBridgeList.isEmpty()) {
        				/* add the max weight bridges to the path of the previous word */
        				poemsWordsList.add(maxWeightBridgeList);
        			}
    			}
    		}
    		poemsWordsList.add(word);
    		preWord = word;
    	}
    	return poemsWordsList;
	}
    
    // TODO toString()
	@Override
	public String toString() {
		return "GraphPoet [graph=" + graph + "]";
	}
	
	/**
	 * getPoems
	 * @used for test only
	 * @delete after test
	 * 
	 * @return a String of poems
	 * 
	 * */
	public String getPoems() {
		// TODO Auto-generated method stub
		return this.poems.toString();
	}
}