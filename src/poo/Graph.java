package poo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

/***********************************************************
* @author Fernando Brito e Abreu
* @since 2008/05/01 (v1.0)
* @since 2008/05/08 (v2.0)
* @since 2009/06/04 (v2.1)
* 
* @param <N> the type of the elements in the graph
***********************************************************/

public class Graph <N extends Comparable<? super N>> implements IGraph<N>
{
	protected ArrayList<N> nodes;
	protected ArrayList<ArrayList<Integer>> adjacencies;
  
 
	/***********************************************************
	* This is the constructor for the graph
	* @param numberNodes the expected number of nodes in the graph 
	***********************************************************/
	public Graph()
	{
		this.nodes = new ArrayList<N>();
		this.adjacencies = new ArrayList<ArrayList<Integer>>();
	}
  
	/* (non-Javadoc)
	 * @see poo.IGraph#numberNodes()
	 */
//	public int numberNodes()
//	{
//		return nodes.size();
//	}

	/* (non-Javadoc)
	 * @see poo.IGraph#numberEdges()
	 */
	public int numberEdges()
	{
		int result = 0;
		for (ArrayList<Integer> connections: adjacencies)
			result+= connections.size();
		return result;
	}
  
	/* (non-Javadoc)
	 * @see poo.IGraph#getNodes()
	 */
	public Iterable<N> getNodes()
	{
		return nodes;
	}

	/* (non-Javadoc)
	 * @see poo.IGraph#getTargetNodes(java.lang.Comparable)
	 */
	public Iterable<N> getTargetNodes(N sourceNode)
	{
		ArrayList<N> result = new ArrayList<N>();
		for (Integer targetNodeId: adjacencies.get(nodes.indexOf(sourceNode)))
			  result.add(nodes.get(targetNodeId));
		  return result;
	}

	/* (non-Javadoc)
	 * @see poo.IGraph#getSourceNodes(java.lang.Comparable)
	 */
	public Iterable<N> getSourceNodes(N targetNode)
	{
		ArrayList<N> result = new ArrayList<N>();
		for (int sourceNodeId = 0; sourceNodeId < adjacencies.size(); sourceNodeId++)
			if (adjacencies.get(sourceNodeId).contains(nodes.indexOf(targetNode)))
				result.add(nodes.get(sourceNodeId));
		return result;
	}

	/* (non-Javadoc)
	 * @see poo.IGraph#nodeExists(N)
	 */
	public boolean nodeExists(N aNode)
	{
		return nodes.contains(aNode);
	}

	/* (non-Javadoc)
	 * @see poo.IGraph#getNode(int)
	 */
	public N getNode(int i)
	{
		assert i>=0 && i<nodes.size();
		return nodes.get(i);
	}
  
	/* (non-Javadoc)
	 * @see poo.IGraph#addNode(N)
	 */
	public boolean addNode(N aNode)
	{
		if (nodeExists(aNode))
			return false;
		else
			return nodes.add(aNode) && adjacencies.add(new ArrayList<Integer>());   
	}
  
	/* (non-Javadoc)
	 * @see poo.IGraph#removeNode(N)
	 */
	public void removeNode(N aNode)
	  {
		  assert nodeExists(aNode);
		  
		  int nodeId = nodes.indexOf(aNode);
		  nodes.remove(nodeId);
		  adjacencies.remove(nodeId);
		  for (ArrayList<Integer> connections : adjacencies)
		  {
			  connections.remove(new Integer(nodeId));
			  // Note that connections.remove(nodeId) would remove by index;
			  for (int i = 0; i< connections.size(); i++)
			  {
				  int previous = connections.get(i);
				  if (previous>nodeId) connections.set(i, --previous);
			  // this is to guarantee that adjacencies values 
			  // match the indexes in the nodes array
			  }
		  }
	  }

	/* (non-Javadoc)
	 * @see poo.IGraph#unidirectionalConnect(N, N)
	 */
	public boolean unidirectionalConnect(N source, N target)
	  {
		  assert nodeExists(source) && nodeExists(target);
	
		  int sourceId = nodes.indexOf(source);
		  int targetId = nodes.indexOf(target);
		  if (adjacencies.get(sourceId).contains(targetId))
			  return false;
		  else
		  {
			  adjacencies.get(sourceId).add(targetId);
			  Collections.sort(adjacencies.get(sourceId));
			  return true;
		  }
	  }
	
	/* (non-Javadoc)
	 * @see poo.IGraph#bidirectionalConnect(N, N)
	 */
	public boolean bidirectionalConnect(N nodeA, N nodeB)
	  {
		 boolean c1 = unidirectionalConnect(nodeA, nodeB);
		 boolean c2 = unidirectionalConnect(nodeB, nodeA);
		 return c1 && c2;
	  }
	
	/* (non-Javadoc)
	 * @see poo.IGraph#unidirectionalConnectionExists(N, N)
	 */
	public boolean unidirectionalConnectionExists(N source, N target)
	  {
		  assert nodeExists(source) && nodeExists(target);
				 
		  int sourceId = nodes.indexOf(source);
		  int targetId = nodes.indexOf(target);
		  return adjacencies.get(sourceId).contains(targetId);
	  }
	
	/* (non-Javadoc)
	 * @see poo.IGraph#bidirectionalConnectionExists(N, N)
	 */
	public boolean bidirectionalConnectionExists(N nodeA, N nodeB)
	{
		return unidirectionalConnectionExists(nodeA, nodeB) 
		    && unidirectionalConnectionExists(nodeB, nodeA);
	}
	
	/* (non-Javadoc)
	 * @see poo.IGraph#unidirectionalDisconnect(N, N)
	 */
	public boolean unidirectionalDisconnect(N source, N target)
	  {
		  assert nodeExists(source) && nodeExists(target);
		  
		  int sourceId = nodes.indexOf(source);
		  int targetId = nodes.indexOf(target);
		  assert sourceId!=-1 && targetId!=-1;
		  return adjacencies.get(sourceId).remove(new Integer(targetId));
	  }
	
	/* (non-Javadoc)
	 * @see poo.IGraph#bidirectionalDisconnect(N, N)
	 */
	public boolean bidirectionalDisconnect(N nodeA, N nodeB)
	  {
			boolean d1 = unidirectionalDisconnect(nodeA, nodeB);
		    boolean d2 = unidirectionalDisconnect(nodeB, nodeA);
			return d1 && d2;
	  }
	 
	  
	  /*--------------------------------------------------------------------------------------------------------
	  | Breadth First Algorithm
	  ---------------------------------------------------------------------------------------------------------*/
	  private ArrayList<ArrayList<Integer>> visited;
	  private int [] distances;
	  private int [] predecessors;
	  private int start = -1;
	  
	
	/* (non-Javadoc)
	 * @see poo.IGraph#computeBreadthFirstSearch(N)
	 */
	public void computeBreadthFirstSearch(N startNode)
	  {
	    assert nodeExists(startNode);
		  
	    visited = null;
	    distances = null;
	    predecessors = null;
	    int n = this.numberNodes();
	
	    this.start = nodes.indexOf(startNode);
	    visited = new ArrayList<ArrayList<Integer>>();
	    distances = new int[n];
	    Arrays.fill(distances, -1);
	    predecessors = new int[n];
	    Arrays.fill(predecessors, -1);
	
	    visited.add(new ArrayList<Integer>());
	    visited.get(0).add(start);
	    
	    distances[start] = 0;
	    int t = 0;   
	    while (!visited.get(t).isEmpty())
	    {
	      t++;
	      visited.add(new ArrayList<Integer>());
	      for (int x: visited.get(t-1))
	        for (int y: this.adjacencies.get(x))
	          if(distances[y] == -1)
	          {
	            visited.get(t).add(y);
	            distances[y] = t;
	            predecessors[y] = x;
	          }
	    }
	  }
	  
	/* (non-Javadoc)
	 * @see poo.IGraph#shortestPath(N)
	 */
	public Iterable<N> shortestPath(N endNode)
	  {
		assert nodeExists(endNode);
		
		int y = nodes.indexOf(endNode);
		ArrayList<N> result = new ArrayList<N>();
		if (predecessors[y] != -1) result.add(0, endNode);
	
		while (predecessors[y] != -1)
		{
		  y = predecessors[y];
		  result.add(0, nodes.get(y));
	    }
	    return result;
	  }
	
	/* (non-Javadoc)
	 * @see poo.IGraph#distanceTo(N)
	 */
	public int distanceTo(N endNode)
	  {
		assert nodeExists(endNode);
	
		return distances[nodes.indexOf(endNode)];
	  }
	  
	  /*--------------------------------------------------------------------------------------------------------
	  | Depth First Algorithm
	  ---------------------------------------------------------------------------------------------------------*/
	   
	  protected int origin = -1;
	  protected int destination = -1;
	  protected ArrayList<Iterator<N>> paths;
	  
	  protected Stack<N> path;
	  protected boolean [] beenVisited;
	  
	/* (non-Javadoc)
	 * @see poo.IGraph#allPaths(N, N)
	 */
	public Iterable<Iterator<N>> allPaths(N startNode, N endNode)
	  {
		assert nodeExists(startNode) && nodeExists(endNode);
		
		if (startNode.equals(endNode))
			return new ArrayList<Iterator<N>>();
		
	    this.origin = nodes.indexOf(startNode);
	    this.destination = nodes.indexOf(endNode);
	    paths = new ArrayList<Iterator<N>>();
	    path = new Stack<N>();
	    beenVisited = new boolean [this.numberNodes()];
	    visit(origin);
	    return paths;
	  }
	
	/***********************************************************
	* @param x the number of the node to be visited
	***********************************************************/
	protected void visit(int x)
	  {
	    path.push(nodes.get(x));
	    beenVisited[x] = true;
	    if (x == destination)
	    	paths.add(new ArrayList<N>(path).iterator());
	    else
	      for (int y: this.adjacencies.get(x))
	//        if (!path.contains(nodes.get(y)))
	    	  if (!beenVisited[y])
	    		  visit(y);
	    beenVisited[x] = false;
	    path.pop();
	  }
	
	/* (non-Javadoc)
	 * @see poo.IGraph#longestPath(java.lang.Comparable, java.lang.Comparable)
	 */
	public Iterable<N> longestPath(N startNode, N endNode)
	{
		int max=0;
		ArrayList<N> result = new ArrayList<N>();
		for (Iterator<N> aPathIt: this.allPaths(startNode, endNode))
		{
			ArrayList<N> aPath = new ArrayList<N>();
			while (aPathIt.hasNext()) aPath.add(aPathIt.next());
			if (aPath.size() > max)
			{
				max = aPath.size();
				result = aPath;
			}
		}		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see poo.IGraph#toIterable(java.util.Iterator)
	 */
	public Iterable<N> toIterable(Iterator<N> iterator)
	{
		ArrayList<N> result = new ArrayList<N>();
		while (iterator.hasNext()) 
			result.add(iterator.next());
		return result;
	}
	
	private boolean containsNode(Iterable<N> path, N node)
	{
		for (N pathNode: path)
			if (pathNode.equals(node))
					return true;
		return false;
	}
	
	/* (non-Javadoc)
	 * @see poo.IGraph#containsNodes(java.lang.Iterable, java.lang.Iterable)
	 */
	public boolean containsNodes(Iterable<N> path, Iterable<N> nodes)
	{
		for (N aNode: nodes) 
			if (!containsNode(path, aNode))
				return false;
		return true;		
	}
	
	/* (non-Javadoc)
	 * @see poo.IGraph#toString()
	 */
	public String toString()
	  {
		  String nodesStr = "", 
		  		 edgesByNumber = "",
		  		 edgesByValue = "";
		  for (int i=0; i<nodes.size(); i++)
		  {
			  nodesStr+= "(" + i + ")" + nodes.get(i) + " ";
			  edgesByNumber+= "(" + i + ")" + adjacencies.get(i) + ", ";
			  edgesByValue+= "("+ nodes.get(i) + ")[";
			  for (int j : adjacencies.get(i))
				   edgesByValue+= nodes.get(j) + ", ";
			  if (!adjacencies.get(i).isEmpty())
			  {
				  int pos = edgesByValue.lastIndexOf(',');
				  if (pos>0) edgesByValue = edgesByValue.substring(0, pos);
			  }
			  edgesByValue+= "], ";
		  }
		  
		  return "Nodes(" + numberNodes() + "): " + nodesStr + "\n"
	           + "Edges by number(" + numberEdges() + "): " + edgesByNumber + "\n"
	           + "Edges by value (" + numberEdges() + "): " + edgesByValue;
	  }

	@Override
	public int numberNodes() {

		return nodes.size();
	}
	  
}
