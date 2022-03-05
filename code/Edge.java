package homeworkSixB;

public class Edge {
	public Vertex dest;
	public double cost;
	
	public Edge(Vertex theDest, double theCost) {
		this.dest = theDest;
		this.cost = theCost;
	}
}
