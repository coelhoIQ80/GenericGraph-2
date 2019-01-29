package slides_pg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import poo.Graph;
import poo.GraphAcyclic;
import poo.GraphInstrumented;
import poo.IGraph;

public class TestPG
{
    /***********************************************************
     * @param args
     ***********************************************************/
    public static void main(String[] args)
    {
//	IGraph<Integer> g = new Graph<Integer>();
//	IGraph<Integer> g = new GraphAcyclic<Integer>();
	IGraph<Integer> g = new GraphInstrumented<Integer>();

	for (int i = 0; i < 8; i++)
	    g.addNode(i);

	g.unidirectionalConnect(0, 7);
	g.unidirectionalConnect(1, 0);
	g.unidirectionalConnect(1, 6);
	g.unidirectionalConnect(2, 1);
	g.unidirectionalConnect(2, 6);
	g.unidirectionalConnect(3, 4);
	g.unidirectionalConnect(3, 5);
	//g.unidirectionalConnect(4, 3);
	g.unidirectionalConnect(4, 5);
	g.unidirectionalConnect(5, 2);
	g.unidirectionalConnect(5, 6);
	g.unidirectionalConnect(6, 7);
	//g.unidirectionalConnect(7, 1);

	System.out.println(g);

	Scanner in = new Scanner(System.in);
	while (in.hasNext())
	{
	    int x = in.nextInt();
	    int y = in.nextInt();
	    int n = 0;
	    for (Iterator<Integer> i : g.allPaths(x, y)) n++;
	    System.out.println("----------(" + n + " caminhos)-----------");
	    n = 0;
	    for (Iterator<Integer> i : g.allPaths(y, x)) n++;
	    System.out.println("----------(" + n + " caminhos)-----------");
	}
    }

}
