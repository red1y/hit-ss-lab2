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
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    /**
     * Abstraction function
     * Graph -> a list of vertices which include the targets, sources and weight
     * vertices(): return  a new copy of set of all the vertices in the graph
     * sources(target): return a new Map which include the (source, weight) of the edge of all edges whose target is the @param1
     * target(source): return a new Map which include the (target, weight) of the edge of all edges whose source is the @param1  
     * 
     * */
    
    // Representation invariant:
    //   TODO
    /**
     * Representation invariant
     * vertices is not null
     * if a vertex1's targets include vertex2, then vertex2's sources must include vertex1.
     * it's the same the other way round 
     * 
     * */
    // Safety from rep exposure:
    //   TODO
    /**
     * use private to protect vertices
     * the fields of Class Vertex are all private, only offers method to be used by Graph
     * never return a mutable field directly
     * 
     * */
    
    // TODO checkRep
    /**
     * checkRep
     * follow the RI
     * 
     * */
    private boolean checkRep() {
    	if(vertices == null) {
    		return false;
    	}
    	for(Vertex<L> vertex : vertices) {
    		Map<L, Integer> targetsMap = vertex.getTargets();
    		for(L target : targetsMap.keySet()) {
    			if(!(vertices.get(vertices.indexOf(new Vertex<L>(target))).containsSource(vertex.getName()))) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    /**
     * add
     * 
     * */
    @Override public boolean add(L vertex) {
    	/* create an instance of vertex using this name */
    	Vertex<L> vertexInstance = new Vertex<L>(vertex);
    	if(vertices.contains(vertexInstance)) {
    		return false;
    	}
    	vertices.add(vertexInstance);
    	return true;
    }
    
    /**
     * set
     * 
     * */
    @Override public int set(L source, L target, int weight) {
    	/* the return value */
    	int ret = 0;
    	/* create vertex instances of source and target */
    	Vertex<L> sourceVertex = new Vertex<L>(source);
    	Vertex<L> targetVertex = new Vertex<L>(target);
    	/* check if the source and target are in the vertices first */
    	/* only add into the vertices when weight > 0 (when not delete an edge) */
    	if(weight > 0) {
        	if(!vertices.contains(sourceVertex)) {
        		add(source);
        	}
        	if(!vertices.contains(targetVertex)) {
        		add(target);
        	}
    	}
    	/* check if this edge already in the edges */
    	int index;
    	boolean edgeExists;
    	if(((index = vertices.indexOf(sourceVertex)) != -1) && (vertices.indexOf(targetVertex) != -1)) {
    		edgeExists = vertices.get(index).containsTarget(target);
    	} else {
    		edgeExists = false;
    	}
    	/* get the instances of source an target except when client request to delete a doesn't exist edge */
    	if(!(weight == 0 && !edgeExists)) {
    		/* get the instance of the source vertex */
    		sourceVertex = vertices.get(vertices.indexOf(sourceVertex));
    		/* get the instance of the target vertex */
    		targetVertex = vertices.get(vertices.indexOf(targetVertex));
    	}
    	/* delete this edge */
    	if(weight == 0) {
			/* but this edge is not in the edges */
			if(!edgeExists) {
				/* return 0 by the specification */
				ret = 0;
			} else {
				/* return the previous weight of this edge */
				ret = sourceVertex.getTarget(target);
				/* delete the edge from the edges */
				sourceVertex.deleteTarget(target);
				targetVertex.deleteSource(source);
			}
		} else {
			/* check if this is a new edge */
			if(!edgeExists) {
				/* return 0 by the specification */
				ret = 0;
				/* add this edge to the edges */
				sourceVertex.setTarget(target, weight);
				targetVertex.setSource(source, weight);
			} else {
				/* return the previous weight of this edge */
				ret = sourceVertex.getTarget(target);
				/* update the weight of this edge */
				sourceVertex.setTarget(target, weight);
				targetVertex.setSource(source, weight);
			}
		}
    	if(!checkRep()) {
    		throw new RuntimeException("RI error");
    	}
    	return ret;
    }
    
    /**
     * remove
     * 
     * */
    @Override public boolean remove(L vertex) {
    	/* create instance of this vertex */
    	Vertex<L> vertexInstace = new Vertex<L>(vertex);
    	/* check if this vertex in the vertices */
    	if(!vertices.contains(vertexInstace)) {
    		return false;
    	}
    	/* get the instance of this vertex in the vertices */
    	vertexInstace = vertices.get(vertices.indexOf(vertexInstace));
    	/* get the targets of this vertex */
    	Map<L, Integer> targetsMap = targets(vertex);
    	/* step through the targets of this vertex */
    	for(L target : targetsMap.keySet()) {
    		/* delete the target in this vertex */
    		vertexInstace.deleteTarget(target);
    		/* delete the source in the target vertex */
    		vertices.get(vertices.indexOf(new Vertex<L>(target))).deleteSource(vertex);
    	}
    	/* get the sources of this vertex */
    	Map<L, Integer> sourcesMap = sources(vertex);
    	/* step through the sources of this vertex */
    	for(L source : sourcesMap.keySet()) {
    		/* delete the source in this vertex */
    		vertexInstace.deleteSource(source);
    		/* delete the target in the source vertex */
    		vertices.get(vertices.indexOf(new Vertex<L>(source))).deleteTarget(vertex);
    	}
    	vertices.remove(vertexInstace);
    	/* return true */
		return true;
    }
    
    /**
     * vertices
     * 
     * */
    @Override public Set<L> vertices() {
    	/* get a new copy of the vertices and return it */
    	Set<L> newVertices = new HashSet<>();
    	for(Vertex<L> vertex : vertices) {
    		newVertices.add(vertex.getName());
    	}
    	return newVertices;
    }
    
    /**
     * sources
     * 
     * */
    @Override public Map<L, Integer> sources(L target) {
    	/* create the instance of the target */
    	Vertex<L> targetVertex = new Vertex<L>(target);
    	/* check if the target is in the vertices */
    	if(!vertices.contains(targetVertex)) {
    		/* return null if not in the vertices */
    		return null;
    	}
    	/* get the instance of target in the vertices */
    	targetVertex = vertices.get(vertices.indexOf(targetVertex));
    	/* use Vertex.getSources to get the result directly */
    	Map<L, Integer> sourcesMap = targetVertex.getSources();
    	/* if no sources, the sourcesMap is not null but empty */
    	return sourcesMap;
    }
    
    /**
     * targets
     * 
     * */
    @Override public Map<L, Integer> targets(L source) {/* create the instance of the target */
    	Vertex<L> sourceVertex = new Vertex<L>(source);
    	/* check if the source is in the vertices */
    	if(!vertices.contains(sourceVertex)) {
    		/* return null if not in the vertices */
    		return null;
    	}
    	/* get the instance of source in the vertices */
    	sourceVertex = vertices.get(vertices.indexOf(sourceVertex));
    	/* use Vertex.getTargets to get the result directly */
    	Map<L, Integer> targetsMap = sourceVertex.getTargets();
    	/* if no sources, the sourcesMap is not null but empty */
    	return targetsMap;
    }
    
    // TODO toString()
    /**
     * toString
     * 
     * */
	@Override
	public String toString() {
		return "ConcreteVerticesGraph [vertices=" + vertices + "]";
	}
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
	
    // TODO fields
	/* the name of this vertex */
	private L name;
	/* the targets that this vertex could reach */
	private Map<L, Integer> targets = new HashMap<>();
	/* the sources that could reach this vertex */
	private Map<L, Integer> sources = new HashMap<>();
	
    // Abstraction function:
    //   TODO
	/**
	 * Abstraction function
	 * Edge -> 
	 * 	vertices that could get here (one step): Map(target -> weight)
	 * 	vertices where could reach (one step): Map(source -> weight)
	 * 
	 * */
	
    // Representation invariant:
    //   TODO
	/**
	 * Representation invariant
	 * targets is not null and doesn't have two or more elements which have the same target
	 * sources is not null and doesn't have two or more elements which have the same source
	 * use map to keep above RI
	 * weight of targets and sources are positive 
	 * 
	 * */
	
    // Safety from rep exposure:
    //   TODO
	/**
	 * Safety from rep exposure
	 * name field is private and only Getter
	 * use private to protect the map
	 * and provide method to change the map indirectly when need
	 * when call getTargets or getSources, create a new map and return it
	 * 
	 * */
    
    // TODO constructor
	/**
	 * constructor
	 * 
	 * */
	public Vertex(L name) {
		this.name = name;
		if(!checkRep()) {
			throw new RuntimeException("RI error");
		}
	}
    /**
     * getName
     * get the name of this vertex
     * 
     * */
    public L getName() {
		return name;
	}

	// TODO checkRep
	/**
	 * checkRep
	 * follow the RI
	 * 
	 * */
	private boolean checkRep() {
		if(targets == null) {
			return false;
		}
		if(sources == null) {
			return false;
		}
		for(Integer weight : targets.values()) {
			if(weight <= 0) {
				return false;
			}
		}
		return true;
	}
    
    // TODO methods
	/**
	 * setTarget 
	 * set the target and weight of an edge from this vertex
	 * @param1 the target of an edge
	 * @param2 the weight of an edge
	 * @return true if set successfully
	 * 		   false if the weight isn't positive and the targets isn't modified
	 * */
    boolean setTarget(L target, int weight) {
    	if(weight <= 0) {
    		return false;
    	}
    	targets.put(target, weight);
    	return true;
    }
    /**
     * deleteTarget
     * delete the target and weight of an edge from this vertex
     * @param the target of an edge
     * @return true if delete successfully
     * 		   false if the target isn't in the targets and the targets isn't modified
     * 
     * */
    boolean deleteTarget(L target) {
    	if(!targets.containsKey(target)) {
    		return false;
    	}
    	targets.remove(target);
    	return true;
    }
    /**
     * containsTarget
     * see if the target is in the targets
     * @param the target of an edge
     * @return true if it is in the targets
     * 		   false if not
     * 
     * */
    boolean containsTarget(L target) {
    	return targets.containsKey(target);
    }
    /**
     * getTarget
     * return the weight of the edge from this vertex to the target
     * @param the target of an edge
     * @return the weight of an edge if it exists
     * 		   0 if it doesn't exist
     * 
     * */
    int getTarget(L target) {
    	if(!targets.containsKey(target)) {
    		return 0;
    	}
    	return targets.get(target).intValue();
    }
    /**
     * getTargets
     * @return a copy of the map targets
     * 
     * */
    Map<L, Integer> getTargets() {
    	Map<L, Integer> newTargets = new HashMap<>();
    	for(Map.Entry<L, Integer> entry : targets.entrySet()) {
    		newTargets.put(entry.getKey(), entry.getValue());
    	}
    	return newTargets;
    }
    /**
     * setSource
     * set the source and weight of an edge to this vertex
     * @param1 the source of an edge
     * @param2 the weight of an edge
     * @return true if set successfully
     * 		   false if the weight isn't positive and the sources isn't modified
     * 
     * */
    boolean setSource(L source, int weight) {
    	if(weight <= 0) {
    		return false;
    	}
    	sources.put(source, weight);
    	return true;
    }
    /**
     * deleteSource
     * delete the source and weight of an edge to this vertex
     * @param the source of an edge
     * @return true if delete successfully
     * 		   false if the source isn't in the sources and the sources isn't modified
     * 
     * */
    boolean deleteSource(L source) {
    	if(!sources.containsKey(source)) {
    		return false;
    	}
    	sources.remove(source);
    	return true;
    }
    /**
     * containsSource
     * see if the source is in the sources
     * @param the source of an edge
     * @return true if it is in the sources
     * 		   false if not
     * 
     * */
    boolean containsSource(L source) {
    	return sources.containsKey(source);
    }
    /**
     * getSource
     * return the weight of the edge from the source to this vertex
     * @param the source of an edge
     * @return the weight of an edge if it exists
     * 		   0 if it doesn't exist
     * 
     * */
    int getSource(L source) {
    	if(!sources.containsKey(source)) {
    		return 0;
    	}
    	return sources.get(source).intValue();
    }
    /**
     * getSources
     * @return a copy of the map sources
     * 
     * */
    Map<L, Integer> getSources() {
    	Map<L, Integer> newSources = new HashMap<>();
    	for(Map.Entry<L, Integer> entry : sources.entrySet()) {
    		newSources.put(entry.getKey(), entry.getValue());
    	}
    	return newSources;
    }
    
    /**
     * Comparator
     * if the name of two vertices are same, they are considered as same
     * 
     * */
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Vertex<?> other = (Vertex<?>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	// TODO toString()
    /**
     * toString
     * only STRING the name and targets but no sources
     * output the vertex as 
     * @return [vertexName, {key1=value1, key2=value2...}]
     * 
     * */
	@Override
	public String toString() {
		return "[" + name + ", " + targets + "]";
	}
}
