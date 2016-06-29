package NetworkElements;

public class Edge {  //undirected
	private int index;
	private double weight;
	private double p;  //reliability
	private Node nodeOne;
	private Node nodeTwo;
	
	public Edge(int index, Node nodeOne, Node nodeTwo){
		this.index = index;
		this.nodeOne = nodeOne;
		this.nodeTwo = nodeTwo;
	}
	
	public Edge(Node nodeOne, Node nodeTwo){
		this.nodeOne = nodeOne;
		this.nodeTwo = nodeTwo;
	}
	
	public Edge(int index, Node nodeOne, Node nodeTwo, double p){
		this.index = index;
		this.nodeOne = nodeOne;
		this.nodeTwo = nodeTwo;
		this.p = p;
	}
	
	public void changeReliability(double p){
		this.p = p;
	}
	
	public String toString(){
		return "Edge: ( " + this.index + " ) "+ "( " + this.nodeOne + " --- " + this.nodeTwo + ")" + " P = " + this.p; 
	}
	
}
