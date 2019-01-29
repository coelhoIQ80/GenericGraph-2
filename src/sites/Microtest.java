package sites;

import java.util.ArrayList;

import poo.Graph;
import poo.IGraph;

public class Microtest {

	/***********************************************************
	 * @param args
	 ***********************************************************/
	public static void main(String[] args)
	{
		  IGraph<Site> telePath = new Graph<Site>();
		  
		  String[] schoolNames = {"FCT", "FCSH", "FE", "FCM", "FD", "ISEGI", "ITQB", "IHMT", "ENSP"};
		  String[] stationNames = {"Mercurio", "Venus", "Terra", "Marte", "Jupiter", "Saturno", "Urano", "Neptuno", "Plutao"};
		  
		  ArrayList<School>  schools = new ArrayList<School>(schoolNames.length+1);
		  ArrayList<Station> stations= new ArrayList<Station>(stationNames.length+1);
		  
		  for (String name: schoolNames)  schools.add(new School(name));
		  for (String name: stationNames) stations.add(new Station(name));
		  
		  for (School school: schools)    telePath.addNode(school);
		  for (Station station: stations) telePath.addNode(station);
		  
		  int i=0;
		  for (Site site1: telePath.getNodes())
			  for (Site site2: telePath.getNodes())
				  if (!site1.equals(site2))
					  switch (i++ % 30)
					  {
					  case 0:
						  telePath.bidirectionalConnect(site1, site2);
						  
						  break;
					  case 1:
						  telePath.unidirectionalConnect(site1, site2);
						  break;
					  case 2:
						  telePath.unidirectionalConnect(site2, site1);
						  break;
					  }

		  System.out.println("____________________________________________________________________________");
		  System.out.println("UNI e BIDIRECIONAIS:");
		  System.out.println("____________________________________________________________________________");
		  int uni=0, bid=0;
		  for (Site site1: telePath.getNodes())
			  for (Site site2: telePath.getNodes())
			  {
				  if (telePath.unidirectionalConnectionExists(site1, site2)) uni++;
				  if (telePath.bidirectionalConnectionExists(site1, site2)) bid++;
			  }
		  System.out.println("Unidirecionais> " + uni);
		  System.out.println("Bidirecionais> " + bid/2);
		  
		  System.out.println();
		  System.out.println("____________________________________________________________________________");
		  System.out.println("SOURCES:");
		  System.out.println("____________________________________________________________________________");
		  for (Site site: telePath.getNodes())
			  System.out.println(site + " > " + telePath.getSourceNodes(site));
		  
		  System.out.println();	
		  System.out.println("____________________________________________________________________________");
		  System.out.println("TARGETS:");
		  System.out.println("____________________________________________________________________________");
		  for (Site site: telePath.getNodes())
			  System.out.println(site + " > " + telePath.getTargetNodes(site));
		  
		  System.out.println();		  	  
		  System.out.println("____________________________________________________________________________");		  
		  System.out.println("SHORTEST PATHS:");
		  System.out.println("____________________________________________________________________________");
//		  for (Site site1: telePath.getNodes())
//			  for (Site site2: telePath.getNodes())
//				  if (!site1.equals(site2))
//				  {
//					  telePath.computeBreadthFirstSearch(site1);
//					  System.out.println(site1 + "\t" + site2 + "\t" + telePath.distanceTo(site2) + "\t" + telePath.shortestPath(site2));
//				  }
		  int n=1;
		  for (Site site1: telePath.getNodes())
			  for (Site site2: telePath.getNodes())
				  if (!site1.equals(site2))
				  {
					  telePath.computeBreadthFirstSearch(site1);
					  System.out.println("Shortest" + (n++) + " " + "(" + telePath.distanceTo(site2) + ") " + "Qual é a distância mais curta de " + site1 + " a " + site2 + "?");
				  }
		  
		  System.out.println();	
		  System.out.println("____________________________________________________________________________");		  
		  System.out.println("LONGEST PATHS:");
		  System.out.println("____________________________________________________________________________");
//		  for (Site site1: telePath.getNodes())
//			  for (Site site2: telePath.getNodes())
//				  if (!site1.equals(site2))
//				  {
//					  int length = -1;
//					  for (Site site: telePath.longestPath(site1, site2)) length++;
//					  System.out.println(site1 + "\t" + site2 + "\t" + length + "\t" + telePath.longestPath(site1, site2));
//				  }
		  
		  n=1;
		  for (Site site1: telePath.getNodes())
			  for (Site site2: telePath.getNodes())
				  if (!site1.equals(site2))
				  {
					  int length = -1;
					  for (Site site: telePath.longestPath(site1, site2)) length++;
					  System.out.println("Longest" + (n++) + " " + "(" + length + ") " + "Qual é a distância mais longa de " + site1 + " a " + site2 + "?");
				  }
		  
		  System.out.println();	
		  System.out.println("____________________________________________________________________________");		  
		  System.out.println("IN-BETWEEN FACULTIES:");
		  System.out.println("____________________________________________________________________________");
		  
		  n=1;
		  for (Station source: stations)
			  for (Station target: stations)
				  if (!source.equals(target))
				  {
					  int count = 0;
					  for (Site site: telePath.longestPath(source, target)) 
						  if (site instanceof School)
							  count++;
					  System.out.println("MidSchool" + (n++) + " " + "(" + count + ") " + "Quantas faculdades existem no caminho mais longo entre " + source + " e " + target + "?");
//					  System.out.println(source + "-" + target + ":\t" + telePath.longestPath(source, target));
				  }
		  
		  
	}

}
