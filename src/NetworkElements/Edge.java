package NetworkElements;

public class Edge {  //undirected
	private int index;
	private double weight;
	private double p;  //reliability
	private boolean state;
	private Node nodeOne;
	private Node nodeTwo;
	
	public Edge(int index, Node nodeOne, Node nodeTwo){
		this.index = index;
		this.nodeOne = nodeOne;
		this.nodeTwo = nodeTwo;
		this.state = true;
	}
	
	public Edge(Node nodeOne, Node nodeTwo){
		this.nodeOne = nodeOne;
		this.nodeTwo = nodeTwo;
		this.state = true;
	}
	
	public Edge(int index, Node nodeOne, Node nodeTwo, double p){
		this.index = index;
		this.nodeOne = nodeOne;
		this.nodeTwo = nodeTwo;
		this.p = p;
		this.state = true;
	}
	public Edge(int index){
		this.index = index;
	}
	
	public void changeReliability(double p){
		this.p = p;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public Node getNodeOne(){
		return this.nodeOne;
	}
	
	public Node getNodeTwo(){
		return this.nodeTwo;
	}
	
	public void flipState(){
		this.state = !this.state;
	}
	
	
	public String toString(){
		return "" + this.index + "[" + this.nodeOne + "," + this.nodeTwo + "]"; 
	}
	
}
