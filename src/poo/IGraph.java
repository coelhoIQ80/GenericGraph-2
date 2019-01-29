package poo;

import java.util.Iterator;

public interface IGraph<N extends Comparable<? super N>> {

	/***********************************************************
	 * @return the number of nodes in the graph
	 ***********************************************************/
	public int numberNodes();

	/***********************************************************
	 * @return the number of unidirectional edges in the graph;
	 * if you have an undirected graph, divide this number by two
	 ***********************************************************/
	public int numberEdges();
	
	/***********************************************************
	 * @return an iterable collection of all nodes in the graph 
	 ***********************************************************/
	public Iterable<N> getNodes();
	
	/***********************************************************
	 * @param sourceNode the node whose target nodes we request
	 * @return an iterable collection of all target nodes connected 
	 * to the given source node 
	 ***********************************************************/
	public Iterable<N> getTargetNodes(N sourceNode);

	/***********************************************************
	 * @param targetNode the node whose source nodes we request
	 * @return an iterable collection of all source nodes connected 
	 * to the given target node 
	 ***********************************************************/
	public Iterable<N> getSourceNodes(N targetNode);

	/***********************************************************
	 * @param aNode the node whose existence we want to confirm
	 * @return true if the node exists
	 ***********************************************************/
	public boolean nodeExists(N aNode);

	/***********************************************************
	 * @param i the node number that must be in [0, numberNodes()[
	 * @return the node element
	 ***********************************************************/
	public N getNode(int i);

	/***********************************************************
	 * @param aNode the node to add to the graph
	 * @return true if the node was added to the graph
	 ***********************************************************/
	public boolean addNode(N aNode);

	/***********************************************************
	 * This method removes all references to a given node
	 * @param aNode the node to remove from the graph
	 ***********************************************************/
	public void removeNode(N aNode);

	/***********************************************************
	 * @param source the source node to connect
	 * @param target the target node to connect
	 * @return true if a new unidirectional connection from source 
	 * node to target node was established
	 ***********************************************************/
	public boolean unidirectionalConnect(N source, N target);

	/***********************************************************
	 * @param nodeA one of the two nodes to connect bidirectionally
	 * @param nodeB one of the two nodes to connect bidirectionally
	 * @return true if a new bidirectional connection between nodes 
	 * A and B was established
	 ***********************************************************/
	public boolean bidirectionalConnect(N nodeA, N nodeB);

	/***********************************************************
	 * @param source the source node
	 * @param target the target node
	 * @return true if an unidirectional connection from source 
	 * node to target node exists
	 ***********************************************************/
	public boolean unidirectionalConnectionExists(N source, N target);

	/***********************************************************
	 * @param nodeA one of the two nodes
	 * @param nodeB one of the two nodes
	 * @return true if a bidirectional connection between nodes 
	 * A and B exists
	 ***********************************************************/
	public boolean bidirectionalConnectionExists(N nodeA, N nodeB);

	/***********************************************************
	 * @param source the source node to disconnect
	 * @param target the target node to disconnect
	 * @return true if the connection from source to target was deleted
	 ***********************************************************/
	public boolean unidirectionalDisconnect(N source, N target);

	/***********************************************************
	 * @param nodeA one of the two nodes to disconnect bidirectionally
	 * @param nodeB one of the two nodes to disconnect bidirectionally
	 * @return true if the bidirectional connection between nodes A 
	 * and B was deleted 
	 ***********************************************************/
	public boolean bidirectionalDisconnect(N nodeA, N nodeB);

	/***********************************************************
	 * This method applies the Breadth First algorithm and we should
	 * call it before using the "shortestPath" and the "distanceTo" methods.
	 * @param startNode the starting node from where the paths are calculated 
	 ***********************************************************/
	public void computeBreadthFirstSearch(N startNode);

	/***********************************************************
	 * @param endNode the node to which we want to get the shortest path 
	 * @return an ordered collection of nodes corresponding to a minimum 
	 * sized path (in terms of number of hops) between the start node
	 * (given when calling "computeBreadthFirstSearch") and the endNode
	 ***********************************************************/
	public Iterable<N> shortestPath(N endNode);

	/***********************************************************
	 * @param endNode the node to which we want to know the distance to 
	 * @return the minimum number of hops required to go from the start node
	 * (given when calling "computeBreadthFirstSearch") and the endNode;
	 * if the end node is unreachable, the returned value is -1
	 ***********************************************************/
	public int distanceTo(N endNode);

	/***********************************************************
	 * @param startNode the starting node
	 * @param endNode the ending node
	 * @return an iterable collection of iterators; each iterator allows 
	 * transversing a possible path between the start and end nodes; 
	 * this method uses Depth First Search
	 ***********************************************************/
	public Iterable<Iterator<N>> allPaths(N startNode, N endNode);

	/***********************************************************
	 * @param startNode the starting node
	 * @param endNode the ending node
	 * @return an ordered collection of nodes corresponding to the maximum 
	 * sized path (in terms of number of hops) between the start and end 
	 * nodes; this method uses Depth First Search
	 ***********************************************************/
	public Iterable<N> longestPath(N startNode, N endNode);
	
	/***********************************************************
	 * @param iterator an iterator of type N elements
	 * @return an iterable collection of type N elements
	 ***********************************************************/
	public Iterable<N> toIterable(Iterator<N> iterator);
	
	/***********************************************************
	* @param path a path
	* @param nodes an iterable collection of nodes
	* @return true if the path contains the indicated nodes
	***********************************************************/
	public boolean containsNodes(Iterable<N> path, Iterable<N> nodes);
	
	/***********************************************************
	 * @return a formated string containing information on all 
	 * nodes and edges in the graph 
	 * @see java.lang.Object#toString()
	 ***********************************************************/
	public String toString();
}