/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    /**
     * Abstraction function
     * Graph -> Set of vertices and Map of edges
     * vertices(): return  a new copy of set of all the vertices in the graph
     * sources(target): return a new Map which include the (source, weight) of the edge of all edges whose target is the @param
     * target(source): return a new Map which include the (target, weight) of the edge of all edges whose source is the @param  
     * 
     * */
    // Representation invariant:
    /**
     * Representation invariant
     * the set of vertices is not null, no two same vertex, Set and L.equals guarantee that
     * the list of edges is not null, and no two edges have the same (source, target), use a set of map to check that
     * 
     * */
    // Safety from rep exposure:
    //   TODO
    /**
     * Safety from rep exposure
     * the field are all private
     * vertices() return a new copy of the vertices
     * sources() and target() both return a new map but not the edges-list
     * which means the client can't reach the internal structure
     * 
     * */
    
    // TODO constructor
    /**
     * constructor
     * 
     * */
    public ConcreteEdgesGraph() {
    	if(!checkRep()) {
    		throw new RuntimeException("RI error");
    	}
	}
    
    // TODO checkRep
    /**
     * checkRep
     * 
     * */
    public boolean checkRep() {
    	if(vertices == null || edges == null) {
    		return false;
    	}
    	/* use a set of map to prove that there aren't two edges have same(source, target) */
    	Set<Map<L, L>> edgesSet = new HashSet<>();
    	for(Edge<L> edge : edges) {
    		Map<L, L> edgeMap = new HashMap<>();
    		edgeMap.put(edge.getSource(), edge.getTarget());
    		if(edgesSet.contains(edgeMap)) {
    			return false;
    		}
    		edgesSet.add(edgeMap);
    	}
    	return true;
    }

	@Override public boolean add(L vertex) {
    	/* check if the vertex already exists */
    	if(vertices.contains(vertex)) {
    		return false;
    	}
    	/* add the vertex to the vertices */
    	vertices.add(vertex);
		return true;
    }
    
    @Override 
    public int set(L source, L target, int weight) {
    	/* the return value */
    	int ret = 0;
    	/* check if the source and target are in the vertices first */
    	/* only add into the vertices when weight > 0 (not delete an edge) */
    	if(weight > 0) {
    		if(!vertices.contains(source)) {
        		add(source);
        	}
    		if(!vertices.contains(target)) {
        		add(target);
        	}
    	}
		/* create a new instance of Edge, if aim to delete, set 0xf as the default value of this instance */
		Edge<L> edge = new Edge<>(source, target, weight > 0 ? weight : 0xf);
		/* check if this edge already in the edges */
		boolean edgeExists = edges.contains(edge);
		/* check if delete this edge */
		if(weight == 0) {
			/* but this edge is not in the edges */
			if(!edgeExists) {
				/* return 0 by the specification */
				ret = 0;
			} else {
				/* return the previous weight of this edge */
				ret = edges.get(edges.indexOf(edge)).getWeight();
				/* delete the edge from the edges */
				edges.remove(edges.indexOf(edge));
			}
		} else {
			/* check if this is a new edge */
			if(!edgeExists) {
				/* return 0 by the specification */
				ret = 0;
				/* add this edge to the edges */
				edges.add(edge);
			} else {
				/* return the previous weight of this edge */
				ret = edges.get(edges.indexOf(edge)).getWeight();
				/* update the weight of this edge */
				Edge<L> newEdge = new Edge<>(edge.getSource(), edge.getTarget(), weight);
				edges.remove(edge);
				edges.add(newEdge);
			}
		}
    	return ret;
    }
    
    @Override public boolean remove(L vertex) {
    	/* check if this vertex in the vertices */
    	if(!vertices.contains(vertex)) {
    		return false;
    	}
    	/* get the targets of this vertex */
    	Map<L, Integer> targetsMap = targets(vertex);
    	/* step through the map and delete all the edges */
    	for(Map.Entry<L, Integer> entry: targetsMap.entrySet()) {
    		/* delete the edge */
    		set(vertex, entry.getKey(), 0);
    	}
    	/* get the sources of this vertex */
    	Map<L, Integer> sourcesMap = sources(vertex);
    	/* step through the map and delete all the edges */
    	for(Map.Entry<L, Integer> entry: sourcesMap.entrySet()) {
    		/* delete the edge */
    		set(entry.getKey(), vertex, 0);
    	}
    	/* delete this vertex from the vertices */
    	vertices.remove(vertex);
    	/* return true */
		return true;
    }
    
    @Override public Set<L> vertices() {
    	/* get a new copy of the vertices and return it */
    	Set<L> verticesBakeSet = new HashSet<L>();
    	for(L vertex : vertices) {
    		verticesBakeSet.add(vertex);
    	}
    	return verticesBakeSet;
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	/* check if the target is in the vertices */
    	if(!vertices.contains(target)) {
    		/* return null if not in the vertices */
    		return null;
    	}
    	/* the result map */
    	Map<L, Integer> sourcesMap = new HashMap<>();
    	/* step through the edges */
    	for(Edge<L> edge : edges) {
    		/* get the edges which contains $target as target */
    		if(edge.getTarget() == target) {
    			sourcesMap.put(edge.getSource(), edge.getWeight());
    		}
    	}
    	/* if no sources, the sourcesMap is not null but empty */
    	return sourcesMap;
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	/* check if the source is in the vertices */
    	if(!vertices.contains(source)) {
    		/* return null if not in the vertices */
    		return null;
    	}
    	/* the result map */
    	Map<L, Integer> targetsMap = new HashMap<>();
    	/* step through the edges */
    	for(Edge<L> edge : edges) {
    		/* get the edges which contains $source as source */
    		if(edge.getSource() == source) {
    			targetsMap.put(edge.getTarget(), edge.getWeight());
    		}
    	}
    	/* if no targets, the targetsMap is not null but empty */
    	return targetsMap;
    }
    
    // TODO toString()
    /**
     * toSring
     * add all the vertices from the set to a list
     * and use list.sort to make the list in order
     * finally make the sorted a String
     * 
     * */
	@Override
	public String toString() {
		List<L> verticesList = new ArrayList<>();
		for(L vertex : vertices) {
			verticesList.add(vertex);
		}
		verticesList.sort(null);
		return "ConcreteEdgesGraph {vertices=" + verticesList + ", edges=" + edges + "}";
	}
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    // TODO fields
	/* the source of the edge */
    private final L source;
    /* the target of the edge */
    private final L target;
    /* the weight of the edge */
    private final int weight;
    
    // Abstraction function:
    //   TODO
    
    /**
     * Abstraction function
     * Edge -> (source, target, weight)
     * getSource(): get the source of this edge
     * getTarget(): get the target of this edge
     * getWeight(): get the weight of this edge
     * 
     * */
	public L getSource() {
		return source;
	}
	public L getTarget() {
		return target;
	}
	public int getWeight() {
		return weight;
	}
	
	// Representation invariant:
    //   TODO
	
	/**
	 * Representation invariant:
	 * source is not null
	 * target is not null
	 * source and target is not the same
	 * weight > 0
	 * 
	 * */
	
    // Safety from rep exposure:
    //   TODO
	
	/**
	 * Safety from rep exposure
	 * the fields are private and final, and only Getters 
	 * 
	 * */
	
    // TODO constructor
	/**
	 * Constructor
	 * @param1 the source of the new edge
	 * @param2 the target of the new edge
	 * @param3 the weight of the new edge 
	 * 
	 */
	public Edge(L source2, L target2, int weight) {
		this.source = source2;
		this.target = target2;
		this.weight = weight;
		/* check if the data violate the RI */
		if(!checkRep()) {
			throw new RuntimeException("RI error");
		}
	}
    
    // TODO checkRep
	/**
	 * checkRep
	 * 
	 * */
    private boolean checkRep()
    {
    	if(source == null) {
    		return false;
    	}
    	if(target == null) {
    		return false;
    	}
    	if(source == target) {
    		return false;
    	}
    	if(weight <= 0) {
    		return false;
    	}
    	return true;
    }
    
    // TODO methods
    
    /**
     * Edge Comparator
     * according to our work, two edges are considered as the same if they have the same source and target
     * but the weight doesn't matter
     * 
     * */
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge<?> other = (Edge<?>) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}
	
	// TODO toString()
	/**
	 * toString
	 * output Edge like:
	 * @return [source, target, weight]
	 * 
	 */
	@Override
	public String toString() {
		return "[" + source + ", " + target + ", " + weight + "]";
	}
}