package friendshipgraph;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class FriendshipGraphTest {
	
	/**
	 * create some instances of Person
	 * 
	 * */
	Person jack = new Person("jack");
	Person rose = new Person("rose");
	Person tom = new Person("tom");
	Person klay = new Person("klay");
	Person booker = new Person("booker");
	
	public FriendshipGraph getInstance() {
		return new FriendshipGraph();
	}

	/**
	 * Test addVertex
	 * @use FriendShipGraph.getPersons()
	 * 
	 * */
	@Test
	public void testAddVertex() {
		FriendshipGraph friendshipGraph = getInstance();
		friendshipGraph.addVertex(jack);
		Set<Person> persons1 = friendshipGraph.getPersons();
		assertTrue(persons1.size() == 1);
		assertTrue(persons1.contains(jack));
		friendshipGraph.addVertex(rose);
		Set<Person> persons2 = friendshipGraph.getPersons();
		assertTrue(persons2.size() == 2);
		assertTrue(persons2.contains(jack));
		assertTrue(persons2.contains(rose));
	}
	
	/**
	 * Test addEdge
	 * @use FriendShip.getTargets()
	 * @use FriendShip.getSources()
	 * 
	 * */
	@Test
	public void testAddEdge() {
		FriendshipGraph friendshipGraph = getInstance();
		friendshipGraph.addEdge(jack, rose);
		Map<Person, Integer> jackTargets1 = friendshipGraph.getTargets(jack);
		Map<Person, Integer> roseSources1 = friendshipGraph.getSources(rose);
		assertTrue(jackTargets1.size() == 1);
		assertTrue(jackTargets1.containsKey(rose));
		assertTrue(roseSources1.size() == 1);
		assertTrue(roseSources1.containsKey(jack));
		friendshipGraph.addEdge(jack, tom);
		Map<Person, Integer> jackTargets2 = friendshipGraph.getTargets(jack);
		Map<Person, Integer> tomSources1 = friendshipGraph.getSources(tom);
		assertTrue(jackTargets2.size() == 2);
		assertTrue(jackTargets2.containsKey(rose));
		assertTrue(jackTargets2.containsKey(tom));
		assertTrue(tomSources1.size() == 1);
		assertTrue(tomSources1.containsKey(jack));
		friendshipGraph.addEdge(tom, rose);
		Map<Person, Integer> tomTargets1 = friendshipGraph.getTargets(tom);
		Map<Person, Integer> roseSources2 = friendshipGraph.getSources(rose);
		assertTrue(tomTargets1.size() == 1);
		assertTrue(tomTargets1.containsKey(rose));
		assertTrue(roseSources2.size() == 2);
		assertTrue(roseSources2.containsKey(jack));
		assertTrue(roseSources2.containsKey(tom));
	}

	/**
	 * Test getDistance
	 * 
	 * */
	@Test
	public void testGetDistance() {
		FriendshipGraph friendshipGraph = getInstance();
		assertTrue(friendshipGraph.getDistance(jack, jack) == -1);
		friendshipGraph.addVertex(jack);
		assertTrue(friendshipGraph.getDistance(jack, jack) == 0);
		friendshipGraph.addEdge(jack, rose);
		assertTrue(friendshipGraph.getDistance(jack, rose) == 1);
		friendshipGraph.addEdge(rose, tom);
		assertTrue(friendshipGraph.getDistance(jack, tom) == 2);
	}
}
