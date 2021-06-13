package friendshipgraph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import graph.Graph;

public class FriendshipGraph {
	
	/* create an instance of graph */
	private Graph<Person> graph = Graph.empty();
	
	public FriendshipGraph() {
		/* nothing to do */
	}	
	
	/** 
	 * methods create for Test 
	 * Test which uses the methods above have a notation of @use methodName
	 * 
	 * */
	/**
	 * getPersons
	 * @implement Graph.vertices()
	 * 
	 * @return all persons in the friendship
	 * 
	 * */
	public Set<Person> getPersons() {
		return graph.vertices();
	}
	/**
	 * getTargets
	 * @implement Graph.targets()
	 * 
	 * @param the source person
	 * @return all targets of one person
	 * 
	 * */
	public Map<Person, Integer> getTargets(Person source) {
		return graph.targets(source);
	}
	/**
	 * getSources
	 * @implement Graph.sources()
	 * 
	 * @param the target person
	 * @return all sources of one person
	 * 
	 * */
	public Map<Person, Integer> getSources(Person target) {
		return graph.sources(target);
	}
	
	/**
	 * addVertex, use Graph.add()
	 * 
	 * */
	public void addVertex(Person person) {
		graph.add(person);
	}
	
	/**
	 * addEdge, use Graph.set()
	 * 
	 * */
	public void addEdge(Person source, Person target) {
		graph.set(source, target, 1);
	}
	
	/**
	 * getDistance
	 * breadth first search
	 * 
	 * */
	public int getDistance(Person source, Person target) {
		/* get all vertices in this graph, use Graph.vertices() */
		Set<Person> persons = graph.vertices();
		/* check if the source and target are both in the friendship */
		if(!persons.contains(source) || !persons.contains(target)) {
			/* one of them are not exist, return -1 */
			return -1;
		}
		/* source and target are same, use Person.equals() */
		if(source.equals(target)) {
			return 0;
		}
		/* map person to an integer of distance from the source */
		Map<Person, Integer> distanceTo = new HashMap<>();
		/* initial the map */
		for(Person person : persons) {
			/* -1 means not reach */
			distanceTo.put(person, -1);
		}
		/* distanceTo(source) is zero */
		distanceTo.put(source, 0);
		/* create a queue */
		Queue<Person> queue = new LinkedList<>();
		/* push the source */
		queue.offer(source);
		/* breadth first search */
		while(!queue.isEmpty()) {
			Person current = queue.poll();
			/* get all targets of the current person, use Graph.targets() */
			Map<Person, Integer> targets = graph.targets(current);
			/* step through the targets and update the distance */
			for(Map.Entry<Person, Integer> entry : targets.entrySet()) {
				/* first reach this person */
				if(distanceTo.get(entry.getKey()) == -1) {
					/* update the distance */
					distanceTo.put(entry.getKey(), distanceTo.get(current) + entry.getValue());
					/* get the target */
					if(entry.getKey().equals(target)) {
						break;
					}
					/* push this person into the queue */
					queue.offer(entry.getKey());
				}
			}
		}
		/* return the distance to target */
		return distanceTo.get(target);
	}
}