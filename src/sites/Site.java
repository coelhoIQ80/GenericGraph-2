package sites;

abstract public class Site implements Comparable<Site>
{
	private String name;
	public Site(String hisName)				{name=hisName;}
	public String getName()					{return name;}
	public int compareTo(Site otherSite)	{return name.compareTo(otherSite.name);}
	public boolean equals(Object another)	{return name.equals(((Site)another).name);}
}

