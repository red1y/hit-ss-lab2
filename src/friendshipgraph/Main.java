package friendshipgraph;

public class Main {

	public static void main(String[] args) {
		FriendshipGraph graph = new FriendshipGraph();
		
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person krammer = new Person("Krammer");
		
		graph.addVertex(ross);
		graph.addVertex(rachel);
		graph.addVertex(ben);
		graph.addVertex(krammer);
		
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		
		
		System.out.println(graph.getDistance(rachel, ross));
		System.out.println(graph.getDistance(rachel, ben));
		System.out.println(graph.getDistance(rachel, rachel));
		System.out.println(graph.getDistance(rachel, krammer));
		
	}

}
