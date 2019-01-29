package poo;
import java.util.ArrayList;

public class GraphAcyclic<N extends Comparable<? super N>> extends Graph<N>
{
//    private int stack = 0;

    /***********************************************************
     * @param x the number of the node to be visited
     ***********************************************************/
    protected void visit(int x)
    {
//	stack++;
	path.push(nodes.get(x));
//	System.out.println(stack + " ---" + path + (x == destination ? "***" : ""));
	if (x == destination)
	    paths.add(new ArrayList<N>(path).iterator());
	else
	    for (int y : this.adjacencies.get(x))
		visit(y);
	path.pop();
//	stack--;
    }

}
