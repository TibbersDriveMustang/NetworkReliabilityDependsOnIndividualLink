package TEST;
import NetworkElements.*;
import java.util.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.EdgeType;

public class test {
	int numNodes;
	myGraph<Node,Edge> graph;
	ArrayList<Integer> indexList;
	List<Node> nodes;
	List<Edge> edges;
	
	public test(int num){
		numNodes = num;
		graph = new myGraph<Node, Edge>();
		indexList = new ArrayList<Integer>();
		indexList.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10));  //index from 1 to 10
		Collections.shuffle(indexList);
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		this.addNumberOfNodes();
		this.addEdges();
		System.out.println(this.graph.getEdgeCount() + " Undirected Edges Created");
	}
	
	public int randomIndex(){
		int index = this.indexList.remove(this.indexList.size() - 1);
		return index;
	}
	
	//start from 0
	public void addNumberOfNodes(){
		for(int i = 0; i < this.numNodes; i++){
			Node temp = new Node(i);
			this.graph.addVertex(temp);
			this.nodes.add(temp);
		}
		System.out.println(this.numNodes + " Nodes Created");
	}
	
	public void addOneEdge( Node nodeOne, Node nodeTwo){
		Edge temp = new Edge(this.randomIndex(),nodeOne,nodeTwo);
		this.edges.add(temp);
		this.graph.addEdge(temp, nodeOne, nodeTwo, EdgeType.UNDIRECTED);
	}
	
	public void addEdges(){
		for(int i = 0; i < this.numNodes; i++){
			for(int j = i + 1; j < this.numNodes; j++){
				this.addOneEdge(this.nodes.get(i), this.nodes.get(j));
			}
		}
	}
	
	
	public void setReliability(double p){
		
	}
	
	public static void main(String args[]){
		test test1 = new test(5);
	}
}
